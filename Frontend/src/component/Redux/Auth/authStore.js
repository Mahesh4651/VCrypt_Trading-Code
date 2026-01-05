import { configureStore } from '@reduxjs/toolkit';
import authReducer from './authSlice';
import portfolioReducer from './portfolioSlice';
import tradeReducer from '../Trade/tradeSlice';
import activeTradesReducer from '../Trade/activeTradeSlice';
import completedTradesReducer from '../Trade/completedTradesSlice';

export const authStore = configureStore({
  reducer: {
    auth: authReducer,
    trade: tradeReducer,
    activeTrades: activeTradesReducer,
    portfolio: portfolioReducer,
    completedTrades: completedTradesReducer,
  },
});