import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { updatePortfolioValues } from '../Auth/portfolioSlice';

const API_URL = `${import.meta.env.VITE_API_URL}/api/trades`; // ✅ Vite env variable
const WS_URL = `${import.meta.env.VITE_API_URL}/ws`; // ✅ WebSocket URL from Vite

let stompClient = null;

export const fetchCoinPrice = createAsyncThunk(
  'trade/fetchCoinPrice',
  async (symbol, { rejectWithValue }) => {
    try {
      const response = await axios.get(`${API_URL}/price/${symbol}`);
      return response.data;
    } catch (error) {
      const status = error.response?.status;

      // ✅ Suppress error if it's 403
      if (status === 403) {
        console.debug('403 Forbidden - Ignored silently');
        return rejectWithValue(null); // or use a specific flag like "__403__" if needed
      }

      // ✅ For other errors, return the message
      return rejectWithValue(error.response?.data || error.message);
    }
  }
);


export const createTrade = createAsyncThunk(
  'trade/createTrade',
  async (tradeData, { getState, rejectWithValue }) => {
    try {
      const { auth } = getState();
      const response = await axios.post(
        API_URL,
        {
          symbol: tradeData.symbol,
          type: tradeData.type,
          totalCost: tradeData.totalCost.toString(),
        },
        {
          headers: {
            Authorization: `Bearer ${auth.token}`,
            'Content-Type': 'application/json',
          },
        }
      );
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data || 'Failed to create trade');
    }
  }
);

// export const initializeWebSocket = () => (dispatch) => {
//   if (stompClient && stompClient.connected) return;

//   const socket = new SockJS(WS_URL);
//   stompClient = new Client({
//     webSocketFactory: () => socket,
//     reconnectDelay: 5000,
//     onConnect: (frame) => {
//       console.log('✅ Connected to WebSocket:', frame);
//       stompClient.subscribe('/topic/cryptoPrices', (message) => {
//         try {
//           const cryptoData = JSON.parse(message.body);
//       //    console.log('WebSocket message received:', cryptoData);
//           dispatch(updateCryptoPrices(cryptoData));
//           dispatch(
//             updatePortfolioValues({
//               symbol: cryptoData.symbol,
//               currentPrice: parseFloat(cryptoData.price),
//             })
//           );
//           dispatch(updateCryptoPricesForSell(cryptoData));
//         } catch (error) {
//           console.error('❌ Error parsing WebSocket message:', error);
//         }
//       });
//     },
//     onStompError: (frame) => console.error('❌ STOMP Connection Error:', frame),
//     onDisconnect: () => console.log('⚠️ Disconnected from WebSocket.'),
//   });

//   stompClient.activate();
// };



export const initializeWebSocket = () => (dispatch) => {
  if (stompClient && stompClient.connected) return;

  const socket = new SockJS(WS_URL);
  stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    onConnect: (frame) => {
      console.log('✅ Connected to WebSocket:', frame);

      // Only subscribe if connected
      if (stompClient.connected) {
        stompClient.subscribe('/topic/cryptoPrices', (message) => {
          try {
            const cryptoData = JSON.parse(message.body);
            console.log('WebSocket message received:', cryptoData);
            dispatch(updateCryptoPrices(cryptoData));
            dispatch(
              updatePortfolioValues({
                symbol: cryptoData.symbol,
                currentPrice: parseFloat(cryptoData.price),
              })
            );
            dispatch(updateCryptoPricesForSell(cryptoData));
          } catch (error) {
            console.error('❌ Error parsing WebSocket message:', error);
          }
        });
      } else {
        console.error('❌ WebSocket not connected');
      }
    },
    onStompError: (frame) => {
      console.error('❌ STOMP Connection Error:', frame);
    },
    onDisconnect: () => {
      console.log('⚠️ Disconnected from WebSocket.');
    },
  });

  stompClient.activate();
};


export const disconnectWebSocket = () => () => {
  if (stompClient) {
    stompClient.deactivate();
    stompClient = null;
  }
};


const tradeSlice = createSlice({
  name: 'trade',
  initialState: {
    coin: '',
    price: 0,
    amount: 0,
    totalCoins: 0,
    status: 'idle',
    error: null,
    suggestions: [],
    userBalance: 0,
    cryptoPrices: [],
    searchQuery: '',
    isNavigating: false,
  },
  reducers: {
    setCoin: (state, action) => {
      state.coin = action.payload;
      const cryptoList = state.cryptoPrices.map((crypto) => crypto.symbol);
      state.suggestions = cryptoList.filter((crypto) =>
        crypto.toLowerCase().includes(action.payload.toLowerCase())
      );

      const selectedCrypto = state.cryptoPrices.find(
        (crypto) => crypto.symbol.toLowerCase() === action.payload.toLowerCase()
      );
      if (selectedCrypto) {
        state.price = parseFloat(selectedCrypto.price);
        if (state.amount > 0) {
          state.totalCoins = state.amount / state.price;
        }
      } else {
        state.price = 0;
        state.totalCoins = 0;
      }
    },
    setSearchQuery: (state, action) => {
      state.searchQuery = action.payload;
    },
    setAmount: (state, action) => {
      state.amount = Number(action.payload);
      if (state.price > 0) {
        state.totalCoins = state.amount / state.price;
      }
    },
    setPrice: (state, action) => {
      state.price = Number(action.payload);
      if (state.amount > 0) {
        state.totalCoins = state.amount / state.price;
      }
    },
    setTotalCoins: (state, action) => {
      state.totalCoins = Number(action.payload);
      if (state.price > 0) {
        state.amount = state.totalCoins * state.price;
      }
    },
    clearTrade: (state) => {
      state.coin = '';
      state.price = 0;
      state.amount = 0;
      state.totalCoins = 0;
      state.suggestions = [];
    },
    setUserBalance: (state, action) => {
      state.userBalance = action.payload;
    },
    updateCryptoPrices: (state, action) => {
      if (state.isNavigating) return;
      const crypto = action.payload;
      if (!crypto || !crypto.symbol || !crypto.price || !crypto.image) {
        console.warn('⚠️ Invalid crypto data received:', crypto);
        return;
      }

      const priceChange24h =
        ((parseFloat(crypto.coinClose || crypto.price) -
          parseFloat(crypto.coinOpen || crypto.price)) /
          parseFloat(crypto.coinOpen || crypto.price)) *
        100;

      const updatedCrypto = { ...crypto, priceChange24h };

      const existingIndex = state.cryptoPrices.findIndex(
        (item) => item.symbol === crypto.symbol
      );
      if (existingIndex !== -1) {
        state.cryptoPrices[existingIndex] = updatedCrypto;
      } else {
        state.cryptoPrices.push(updatedCrypto);
      }

      // Update price only for non-SELL contexts (e.g., dashboard, BUY)
      if (state.coin === crypto.symbol) {
        state.price = parseFloat(crypto.price);
        if (state.amount > 0) {
          state.totalCoins = state.amount / state.price;
        }
      }
    },
    updateCryptoPricesForSell: (state, action) => {
      if (state.isNavigating) return;
      const crypto = action.payload;
      if (!crypto || !crypto.symbol || !crypto.price || !crypto.image) {
        console.warn('⚠️ Invalid crypto data received for SELL:', crypto);
        return;
      }

      // Update cryptoPrices array (shared with other components)
      const priceChange24h =
        ((parseFloat(crypto.coinClose || crypto.price) -
          parseFloat(crypto.coinOpen || crypto.price)) /
          parseFloat(crypto.coinOpen || crypto.price)) *
        100;

      const updatedCrypto = { ...crypto, priceChange24h };

      const existingIndex = state.cryptoPrices.findIndex(
        (item) => item.symbol === crypto.symbol
      );
      if (existingIndex !== -1) {
        state.cryptoPrices[existingIndex] = updatedCrypto;
      } else {
        state.cryptoPrices.push(updatedCrypto);
      }
    },
    setNavigating: (state, action) => {
      state.isNavigating = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchCoinPrice.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchCoinPrice.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.price = action.payload.price;
        if (state.amount > 0) {
          state.totalCoins = state.amount / state.price;
        }
      })
      .addCase(fetchCoinPrice.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      })
      .addCase(createTrade.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(createTrade.fulfilled, (state) => {
        state.status = 'succeeded';
        state.coin = '';
        state.price = 0;
        state.amount = 0;
        state.totalCoins = 0;
      })
      .addCase(createTrade.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export const {
  setCoin,
  setAmount,
  setPrice,
  setTotalCoins,
  clearTrade,
  setUserBalance,
  updateCryptoPrices,
  updateCryptoPricesForSell,
  setSearchQuery,
  setNavigating,
} = tradeSlice.actions;

export default tradeSlice.reducer;