


import React, { useEffect, useState, useMemo, useCallback } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
  setCoin,
  setAmount,
  setPrice,
  fetchCoinPrice,
  createTrade,
  setUserBalance,
  initializeWebSocket,
  updateCryptoPricesForSell,
} from '../Redux/Trade/tradeSlice';
import { Box, Modal, Typography, Button, TextField, Grid, Autocomplete, CircularProgress } from '@mui/material';
import axios from 'axios';
import _ from 'lodash'; // Import lodash for debouncing SELL updates


const API_BASE_URL = import.meta.env.VITE_API_URL;

const PlaceTrade = ({ open, handleClosetrade }) => {
  const dispatch = useDispatch();

  const {
    coin = '',
    price = 0,
    amount = 0,
    status = 'idle',
    suggestions = [],
    userBalance = 0,
    error = null,
    cryptoPrices = [],
  } = useSelector((state) => state.trade || {});

  const [loading, setLoading] = useState(false);
  const [tradeType, setTradeType] = useState('BUY');
  const [totalCoins, setTotalCoins] = useState('');
  const [activeTrades, setActiveTrades] = useState([]);
  const [fetchingActiveTrades, setFetchingActiveTrades] = useState(false);

  // Debounced price update for SELL trades
  const debouncedUpdatePriceForSell = useCallback(
    _.debounce((cryptoData) => {
      // console.log('Debounced price update for SELL:', cryptoData); // Debug log
      if (coin && cryptoData.symbol.toUpperCase() === coin.toUpperCase()) {
        dispatch(setPrice(parseFloat(cryptoData.price)));
      }
    }, 500),
    [dispatch, coin]
  );

  // Handle WebSocket updates for SELL trades
  useEffect(() => {
    if (tradeType === 'SELL' && open) {
      const handleCryptoUpdate = (cryptoData) => {
        debouncedUpdatePriceForSell(cryptoData);
      };
      // Simulate listening to updateCryptoPricesForSell
      cryptoPrices.forEach((crypto) => {
        if (crypto.symbol && crypto.price) {
          handleCryptoUpdate(crypto);
        }
      });
    }
    return () => {
      debouncedUpdatePriceForSell.cancel(); // Cleanup debounce
    };
  }, [tradeType, open, cryptoPrices, debouncedUpdatePriceForSell, coin]);

  // Fetch active trades for SELL trades
  useEffect(() => {
    const fetchActiveTrades = async () => {
      setFetchingActiveTrades(true);
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
        // console.log('Active trades response:', response.data); // Debug log

        const trades = Array.isArray(response.data) ? response.data : [];
        // console.log('Trades array:', trades); // Debug log

        trades.forEach((trade, index) => {
          console.log(`Trade ${index}:`, {
            symbol: trade.symbol,
            type: trade.type,
            amount: trade.amount,
            status: trade.status,
            parsedAmount: parseFloat(trade.amount),
          });
        });

        const validTrades = trades;
        

        console.log('Valid active trades:', validTrades); // Debug log
        if (validTrades.length === 0) {
          console.log('No valid trades found. Possible reasons:', {
            hasTrades: trades.length > 0,
            tradeTypes: trades.map((t) => t.type),
            tradeStatuses: trades.map((t) => t.status),
            tradeAmounts: trades.map((t) => t.amount),
          });
        }

        setActiveTrades(validTrades);
      } catch (err) {
        console.error('Failed to fetch active trades:', err);
        alert('Failed to load active trades. Please ensure you are logged in and try again.');
      } finally {
        setFetchingActiveTrades(false);
      }
    };

    if (open && tradeType === 'SELL') {
      fetchActiveTrades();
    }
  }, [open, tradeType]);

  // Fetch balance and initialize WebSocket
  useEffect(() => {
    dispatch(initializeWebSocket());

    const fetchBalance = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          throw new Error('No authentication token found');
        }
        const response = await axios.get(`${API_BASE_URL}/api/trades/portfolio`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        // console.log('Balance response:', response.data.demoBalance); // Debug log
        dispatch(setUserBalance(response.data.demoBalance || 0));
      } catch (err) {
        console.error('Failed to fetch balance:', err);
      }
    };

    fetchBalance();
  }, [dispatch]);

  // Fetch price when coin changes
  useEffect(() => {
    if (coin && coin !== '' && tradeType === 'BUY') {
      // console.log('Fetching price for coin (BUY):', coin); // Debug log
      dispatch(fetchCoinPrice(coin));
    }
  }, [coin, dispatch, tradeType]);

  // Calculate total USD amount
  const totalAmount = useMemo(() => {
    const coins = parseFloat(totalCoins) || 0;
    const total = coins * parseFloat(price);
    return isNaN(total) ? 0 : total;
  }, [totalCoins, price]);

  // Get available coins for SELL
  const availableCoins = useMemo(() => {
    if (tradeType === 'SELL' && coin) {
      const totalOwned = activeTrades
        .filter((trade) => trade.symbol.toUpperCase() === coin.toUpperCase())
        .reduce((sum, trade) => sum + parseFloat(trade.amount), 0);
      console.log(`Available coins for ${coin}:`, totalOwned); // Debug log
      return totalOwned;
    }
    return Infinity; // For BUY, no coin limit
  }, [activeTrades, coin, tradeType]);

  // Options for Autocomplete
  const autocompleteOptions = useMemo(() => {
    if (tradeType === 'SELL') {
      const options = [...new Set(activeTrades.map((trade) => trade.symbol.toUpperCase()))];
      console.log('SELL Autocomplete options:', options); // Debug log
      return options;
    }
    console.log('BUY Autocomplete options:', suggestions); // Debug log
    return suggestions;
  }, [tradeType, activeTrades, suggestions]);

  const handleTradeSubmit = async (type) => {
    const coins = parseFloat(totalCoins);
    if (!coin || price <= 0 || !coins || coins <= 0) {
      alert('Please select a coin and enter a valid number of coins');
      return;
    }

    if (type === 'BUY' && totalAmount > userBalance) {
      alert('Insufficient funds');
      return;
    }

    if (type === 'SELL' && coins > availableCoins) {
      alert(`Insufficient ${coin} balance. Available: ${availableCoins.toFixed(8)}`);
      return;
    }

    setLoading(true);

    const tradeData = {
      symbol: coin.toUpperCase(),
      type: type.toUpperCase(),
      totalCost: totalAmount.toFixed(8),
      amount: coins.toFixed(8),
    };
    // console.log('Submitting trade:', tradeData); // Debug log

    try {
      const result = await dispatch(createTrade(tradeData)).unwrap();
      if (result) {
        if (type === 'BUY') {
          dispatch(setUserBalance(userBalance - totalAmount));
        } else {
          dispatch(setUserBalance(userBalance + totalAmount));
        }
        setTotalCoins('');
        dispatch(setCoin(''));
        handleClosetrade();
        alert('Trade executed successfully!');
      }
    } catch (error) {
      console.error('Trade failed:', error);
      alert(error.message || 'Trade failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Modal open={open} onClose={handleClosetrade} aria-labelledby="trade-modal">
      <Box
        sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: { xs: '90%', sm: 400 },
          bgcolor: '#0d0d0d',
          color: 'whitesmoke',
          boxShadow: 24,
          p: 3,
          borderRadius: 2,
        }}
      >
        <Typography variant="h6" sx={{ mb: 2, textAlign: 'center' }}>
          Place Trade (Balance: ${userBalance.toFixed(2)})
        </Typography>

        <Box sx={{ mb: 2, display: 'flex', justifyContent: 'space-between' }}>
          <Button
            variant={tradeType === 'BUY' ? 'contained' : 'outlined'}
            onClick={() => {
              setTradeType('BUY');
              setTotalCoins('');
              dispatch(setCoin(''));
            }}
            sx={{
              bgcolor: tradeType === 'BUY' ? 'green' : 'transparent',
              color: 'white',
              '&:hover': { bgcolor: tradeType === 'BUY' ? '#00cc00' : '#333' },
            }}
          >
            Buy
          </Button>
          <Button
            variant={tradeType === 'SELL' ? 'contained' : 'outlined'}
            onClick={() => {
              setTradeType('SELL');
              setTotalCoins('');
              dispatch(setCoin(''));
            }}
            sx={{
              bgcolor: tradeType === 'SELL' ? 'red' : 'transparent',
              color: 'white',
              '&:hover': { bgcolor: tradeType === 'SELL' ? '#cc0000' : '#333' },
            }}
          >
            Sell
          </Button>
        </Box>

        <Grid container spacing={2}>
          <Grid item xs={12}>
            <Autocomplete
              options={autocompleteOptions}
              value={coin || null}
              onChange={(e, newValue) => {
                console.log('Selected coin:', newValue); // Debug log
                dispatch(setCoin(newValue || ''));
                if (newValue && tradeType === 'SELL') {
                  const totalOwned = activeTrades
                    .filter((trade) => trade.symbol.toUpperCase() === newValue.toUpperCase())
                    .reduce((sum, trade) => sum + parseFloat(trade.amount), 0);
                  if (totalOwned > 0) {
                    setTotalCoins((totalOwned / 2).toFixed(8));
                  } else {
                    setTotalCoins('');
                  }
                } else if (newValue && tradeType === 'BUY') {
                  setTotalCoins('0.001');
                } else {
                  setTotalCoins('');
                }
              }}
              renderInput={(params) => (
                <TextField
                  {...params}
                  fullWidth
                  label="Coin (e.g., BTC)"
                  variant="outlined"
                  onChange={(e) => {
                    if (tradeType === 'BUY') {
                      const newCoin = e.target.value.toUpperCase();
                      console.log('BUY coin input:', newCoin); // Debug log
                      dispatch(setCoin(newCoin));
                    }
                  }}
                  helperText={
                    tradeType === 'SELL' && !autocompleteOptions.length && !fetchingActiveTrades
                      ? 'No coins available to sell. Please buy coins first.'
                      : ''
                  }
                  sx={{
                    input: { color: 'white' },
                    label: { color: 'gray' },
                    '& label.Mui-focused': { color: 'white' },
                    '& .MuiOutlinedInput-root': {
                      '& fieldset': { borderColor: 'gray' },
                      '&:hover fieldset': { borderColor: 'white' },
                      '&.Mui-focused fieldset': { borderColor: '#00ffcc' },
                    },
                    '& .MuiInputBase-root': {
                      backgroundColor: '#1a1a1a',
                    },
                    '& .MuiFormHelperText-root': { color: '#ff4c4c' },
                  }}
                />
              )}
              disabled={tradeType === 'SELL' && (fetchingActiveTrades || !autocompleteOptions.length)}
              getOptionLabel={(option) => option || ''}
              isOptionEqualToValue={(option, value) => option.toUpperCase() === value.toUpperCase()}
            />
          </Grid>

          <Grid item xs={12}>
          <TextField
  fullWidth
  label="Coin Price (USD)"
  variant="outlined"
  value={price ? price.toFixed(5) : 'Loading...'}
  disabled
  sx={{
    // ✅ Input text color
    input: {
      color: 'white',
      WebkitTextFillColor: 'white',
    },

    // ✅ Label color in all states
    '& .MuiInputLabel-root': {
      color: 'white',
    },
    '& .MuiInputLabel-root.Mui-focused': {
      color: 'white',
    },
    '& .MuiInputLabel-root.Mui-disabled': {
      color: 'white',
    },

    // ✅ Outlined border behavior
    '& .MuiOutlinedInput-root': {
      '& fieldset': { borderColor: 'gray' },
      '&:hover fieldset': { borderColor: 'white' },
      '&.Mui-focused fieldset': { borderColor: '#00ffcc' },
    },

    // ✅ Background color
    '& .MuiInputBase-root': {
      backgroundColor: '#1a1a1a',
      opacity: 1,
    },

    // ✅ Disabled input text color
    '& .Mui-disabled': {
      '& input': {
        color: 'white',
        WebkitTextFillColor: 'white',
      },
    },
  }}
/>

          </Grid>

          {tradeType === 'SELL' && coin && availableCoins > 0 && (
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="Available Coins"
                variant="outlined"
                value={availableCoins.toFixed(8)}
                disabled
                sx={{
                  input: { color: 'white' },
                  label: { color: 'gray' },
                  '& label.Mui-focused': { color: 'white' },
                  '& .MuiOutlinedInput-root': {
                    '& fieldset': { borderColor: 'gray' },
                    '&:hover fieldset': { borderColor: 'white' },
                    '&.Mui-focused fieldset': { borderColor: '#00ffcc' },
                  },
                  '& .MuiInputBase-root': {
                    backgroundColor: '#1a1a1a',
                  },
                }}
              />
            </Grid>
          )}

          <Grid item xs={12}>
          <TextField
  fullWidth
  label={`Number of ${coin || 'Coins'}`}
  variant="outlined"
  type="number"
  value={totalCoins}
  onChange={(e) => {
    const value = e.target.value;
    console.log('Quantity input:', value); // Debug log
    if (value === '' || (parseFloat(value) >= 0 && !isNaN(value))) {
      setTotalCoins(value);
    }
  }}
  error={
    (tradeType === 'BUY' && totalAmount > userBalance) ||
    (tradeType === 'SELL' && parseFloat(totalCoins) > availableCoins)
  }
  helperText={
    tradeType === 'BUY' && totalAmount > userBalance
      ? 'Insufficient funds'
      : tradeType === 'SELL' && parseFloat(totalCoins) > availableCoins
      ? `Insufficient ${coin} balance. Available: ${availableCoins.toFixed(8)}`
      : ''
  }
  sx={{
    // ✅ Input text color
    input: {
      color: 'white',
      WebkitTextFillColor: 'white',
    },

    // ✅ Label colors
    '& .MuiInputLabel-root': {
      color: 'white',
    },
    '& .MuiInputLabel-root.Mui-focused': {
      color: '#00ffcc',
    },

    // ✅ Border styling
    '& .MuiOutlinedInput-root': {
      '& fieldset': { borderColor: 'gray' },
      '&:hover fieldset': { borderColor: 'white' },
      '&.Mui-focused fieldset': { borderColor: '#00ffcc' },
    },

    // ✅ Input background
    '& .MuiInputBase-root': {
      backgroundColor: '#1a1a1a',
    },

    // ✅ Error message styling
    '& .MuiFormHelperText-root': {
      color: '#ff4c4c',
    },
  }}
/>

          </Grid>

          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Total Amount (USD)"
              variant="outlined"
              value={totalAmount.toFixed(2)}
              disabled
              sx={{
                input: { color: 'white', WebkitTextFillColor: 'white' },
                label: { color: 'white' },
                '& label.Mui-focused': { color: 'white' },
                '& .MuiOutlinedInput-root': {
                  '& fieldset': { borderColor: 'gray' },
                  '&:hover fieldset': { borderColor: 'white' },
                  '&.Mui-focused fieldset': { borderColor: '#00ffcc' },
                },
                '& .MuiInputBase-root': {
                  backgroundColor: '#1a1a1a',
                  opacity: 1,
                },
                '& .Mui-disabled': {
                  opacity: 1,
                  color: 'white',
                  WebkitTextFillColor: 'white',
                },
              }}
            />
          </Grid>
        </Grid>

        {error && (
          <Typography color="error" sx={{ mt: 2, textAlign: 'center' }}>
            {typeof error === 'string' ? error : error.message}
          </Typography>
        )}

        {fetchingActiveTrades && tradeType === 'SELL' && (
          <Typography sx={{ mt: 2, textAlign: 'center' }}>
            Loading active trades...
          </Typography>
        )}

        <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', mt: 3 }}>
          <Box sx={{ display: 'flex', justifyContent: 'center' }}>
            {loading ? (
              <CircularProgress color="secondary" />
            ) : (
              <Button
                variant="contained"
                color={tradeType === 'BUY' ? 'success' : 'error'}
                onClick={() => handleTradeSubmit(tradeType)}
                disabled={
                  status === 'loading' ||
                  !coin ||
                  price <= 0 ||
                  !totalCoins ||
                  parseFloat(totalCoins) <= 0 ||
                  (tradeType === 'BUY' && totalAmount > userBalance) ||
                  (tradeType === 'SELL' && parseFloat(totalCoins) > availableCoins) ||
                  loading
                }
                sx={{
                  bgcolor: tradeType === 'BUY' ? 'green' : 'red',
                  color: 'white',
                  fontWeight: 'bold',
                  borderRadius: '8px',
                  padding: '10px 20px',
                  '&:hover': {
                    bgcolor: tradeType === 'BUY' ? '#00cc00' : '#cc0000',
                  },
                  '&.Mui-disabled': {
                    bgcolor: tradeType === 'BUY' ? 'rgba(0, 255, 0, 0.3)' : 'rgba(255, 0, 0, 0.3)',
                    color: 'whitesmoke',
                  },
                }}
              >
                {tradeType}
              </Button>
            )}
          </Box>
          <Box>
            <Button variant="outlined" onClick={handleClosetrade} sx={{ color: 'white', borderColor: 'gray' }}>
              Cancel
            </Button>
          </Box>
        </Box>
      </Box>
    </Modal>
  );
};

export default PlaceTrade;