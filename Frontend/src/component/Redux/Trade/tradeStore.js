// src/store.js
import { configureStore } from '@reduxjs/toolkit';
import tradeReducer from '../Trade/tradeSlice';
// import tradeReducer from './features/trade/tradeSlice';
// Import other reducers as needed
// import authReducer from './features/auth/authSlice'; // Assuming you have this

export const tradestore = configureStore({
  reducer: {
    trade: tradeReducer,
    // auth: authReducer, // Assuming you have auth slice
  },
});