import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

// Use Vite environment variable for the API base URL
const API_URL = `${import.meta.env.VITE_API_URL}/api/trades`;

// Async thunk to fetch completed trades
export const fetchCompletedTrades = createAsyncThunk(
  'completedTrades/fetchCompletedTrades',
  async (_, { rejectWithValue }) => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        throw new Error('No authentication token found');
      }

      const response = await axios.get(`${API_URL}/completed`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data?.message || error.message);
    }
  }
);

const completedTradesSlice = createSlice({
  name: 'completedTrades',
  initialState: {
    trades: [],
    status: 'idle',
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCompletedTrades.pending, (state) => {
        state.status = 'loading';
        state.error = null;
      })
      .addCase(fetchCompletedTrades.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.trades = action.payload || [];
      })
      .addCase(fetchCompletedTrades.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload;
      });
  },
});

export default completedTradesSlice.reducer;
