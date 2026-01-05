import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

// Use Vite environment variable
const API_URL = `${import.meta.env.VITE_API_URL}/api/trades`;

// Async thunk to fetch portfolio summary
export const fetchPortfolioSummary = createAsyncThunk(
  'portfolio/fetchPortfolioSummary',
  async (_, { rejectWithValue }) => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        throw new Error('No authentication token found');
      }

      const response = await axios.get(`${API_URL}/portfolio`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      console.log('Portfolio API Response:', response.data);

      if (!Array.isArray(response.data.holdings)) {
        throw new Error('Holdings data is not an array');
      }

      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || error.message);
    }
  }
);

const portfolioSlice = createSlice({
  name: 'portfolio',
  initialState: {
    holdings: [],
    demoBalance: 0,
    totalPortfolioValue: 0,
    status: 'idle',
    error: null,
  },
  reducers: {
    updatePortfolioValues: (state, action) => {
      const { symbol, currentPrice } = action.payload;
      if (!Array.isArray(state.holdings)) {
        console.warn('Holdings is not an array, skipping update:', state.holdings);
        return;
      }

      const portfolioItem = state.holdings.find((item) => item.coinSymbol === symbol);
      if (portfolioItem) {
        portfolioItem.currentPrice = currentPrice;
        portfolioItem.totalValue = portfolioItem.quantity * currentPrice;
        portfolioItem.unrealizedPL = (currentPrice - portfolioItem.averageBuyPrice) * portfolioItem.quantity;
        state.totalPortfolioValue = state.holdings.reduce((sum, item) => sum + item.totalValue, 0);
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchPortfolioSummary.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchPortfolioSummary.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.demoBalance = action.payload.demoBalance || 0;
        state.holdings = action.payload.holdings || [];
        state.totalPortfolioValue = action.payload.totalPortfolioValue || 0;
      })
      .addCase(fetchPortfolioSummary.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export const { updatePortfolioValues } = portfolioSlice.actions;
export default portfolioSlice.reducer;
