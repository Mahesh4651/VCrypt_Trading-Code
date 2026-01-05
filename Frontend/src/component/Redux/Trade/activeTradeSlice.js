import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

// Use Vite environment variable for API base URL
const API_BASE_URL = import.meta.env.VITE_API_URL;

// Async thunk to fetch active trades
export const fetchActiveTrades = createAsyncThunk(
  'activeTrades/fetchActiveTrades',
  async (_, { rejectWithValue }) => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        throw new Error('No authentication token found');
      }

      const response = await axios.get(`${API_BASE_URL}/api/trades/active`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message);
    }
  }
);

// Complete a trade
export const completeTrade = createAsyncThunk(
  'activeTrades/completeTrade',
  async (tradeId, { rejectWithValue }) => {
    try {
      const token = localStorage.getItem('token');
      if (!token) throw new Error('No authentication token found');

      const response = await axios.post(
        `${API_BASE_URL}/api/trades/${tradeId}/complete`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      return { tradeId, completedTrade: response.data.trade };
    } catch (error) {
      return rejectWithValue(error.response?.data || error.message);
    }
  }
);

const activeTradesSlice = createSlice({
  name: 'activeTrades',
  initialState: {
    trades: [],
    status: 'idle',
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchActiveTrades.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchActiveTrades.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.trades = action.payload;
      })
      .addCase(fetchActiveTrades.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      })
      .addCase(completeTrade.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(completeTrade.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.trades = state.trades.filter((trade) => trade.tradeId !== action.payload.tradeId);
      })
      .addCase(completeTrade.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export default activeTradesSlice.reducer;
