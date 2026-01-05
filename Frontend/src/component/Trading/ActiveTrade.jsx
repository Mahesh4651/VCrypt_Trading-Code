







import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Box, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button } from '@mui/material';
import { completeTrade, fetchActiveTrades } from '../Redux/Trade/activeTradeSlice';
import { useLocation, useNavigate } from 'react-router-dom';
import { setNavigating, initializeWebSocket } from '../Redux/Trade/tradeSlice';

const ActiveTrade = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const { trades, status, error } = useSelector((state) => state.activeTrades);
  const { cryptoPrices } = useSelector((state) => state.trade);
  const isNavigating = useSelector((state) => state.trade.isNavigating);

  useEffect(() => {
    dispatch(initializeWebSocket());
    console.log('WebSocket initialized');
    dispatch(fetchActiveTrades()).then((response) => {
      console.log('Fetch Active Trades Response:', response.payload);
    }).catch((err) => {
      console.error('Fetch Active Trades Error:', err);
    });
  }, [dispatch]);

  useEffect(() => {
    dispatch(setNavigating(true));
    const timer = setTimeout(() => {
      dispatch(setNavigating(false));
    }, 500);
    return () => clearTimeout(timer);
  }, [location, dispatch]);

  const handleNavigate = (path) => {
    dispatch(setNavigating(true));
    navigate(path);
  };

  const handleStopTrade = (tradeId) => {
    dispatch(completeTrade(tradeId))
      .unwrap()
      .then(() => {
        console.log(`Trade ${tradeId} completed successfully`);
      })
      .catch((err) => {
        console.error(`Failed to complete trade ${tradeId}:`, err);
      });
  };

  const renderContent = () => {
    if (status === 'loading') {
      console.log('Status is loading');
      return <Typography>Loading...</Typography>;
    }

    if (status === 'failed') {
      console.log('Status is failed, Error:', error);
      return <Typography color="error">Error: {error}</Typography>;
    }

    if (trades.length === 0) {
      console.log('No trades found, Trades:', trades);
      return <Typography sx={{ color: 'whitesmoke' }}>No active trades found.</Typography>;
    }

    console.log('Rendering table with trades:', trades);
    console.log('Crypto Prices:', cryptoPrices);

    const rows = trades.map((trade) => {
      const currentPrice = cryptoPrices.find((priceData) => priceData.symbol.toUpperCase() === trade.symbol.toUpperCase())?.price;
      const profitLoss = currentPrice && trade.price ? ((parseFloat(currentPrice) - parseFloat(trade.price)) * parseFloat(trade.amount)).toFixed(2) : 'N/A';
      return (
        <TableRow key={trade.tradeId}>
          <TableCell style={{ color: 'whitesmoke' }}>{trade.symbol}</TableCell>
          <TableCell style={{ color: 'whitesmoke' }}>{trade.type}</TableCell>
          <TableCell style={{ color: 'whitesmoke' }}>{trade.amount}</TableCell>
          <TableCell style={{ color: 'whitesmoke' }}>{trade.price}</TableCell>
          <TableCell style={{ color: 'whitesmoke' }}>{trade.totalCost}</TableCell>
          <TableCell style={{ color: 'whitesmoke' }}>{currentPrice || 'N/A'}</TableCell>
          <TableCell sx={{ color: profitLoss !== 'N/A' && parseFloat(profitLoss) >= 0 ? 'green' : 'red' }}>
            {profitLoss}
          </TableCell>
          <TableCell>
            <Button
              variant="contained"
              sx={{
                backgroundColor: profitLoss !== 'N/A' && parseFloat(profitLoss) >= 0 ? 'LimeGreen' : 'red',
                color: 'whitesmoke',
                '&:hover': {
                  backgroundColor: profitLoss !== 'N/A' && parseFloat(profitLoss) >= 0 ? 'darkgreen' : 'darkred',
                },
              }}
              onClick={() => handleStopTrade(trade.tradeId)}
            >
              Stop
            </Button>
          </TableCell>
        </TableRow>
      );
    });

    return (
      <TableContainer component={Paper} style={{ backgroundColor: '#0d0d0d' }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ color: 'whitesmoke' }}>Symbol</TableCell>
              <TableCell style={{ color: 'whitesmoke' }}>Type</TableCell>
              <TableCell style={{ color: 'whitesmoke' }}>Quantity</TableCell>
              <TableCell style={{ color: 'whitesmoke' }}>Price</TableCell>
              <TableCell style={{ color: 'whitesmoke' }}>Total Cost</TableCell>
              <TableCell style={{ color: 'whitesmoke' }}>Current Price</TableCell>
              <TableCell style={{ color: 'whitesmoke' }}>Profit/Loss</TableCell>
              <TableCell style={{ color: 'whitesmoke' }}>Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>{rows}</TableBody>
        </Table>
      </TableContainer>
    );
  };

  return (
    <Box sx={{ width: '100%', height: '90vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      <Box sx={{ width: '90%', maxHeight: '100vh', overflow: 'auto', p: 2, bgcolor: '#0d0d0d' }}>
        <Typography variant="h5" gutterBottom sx={{ color: 'white', textAlign: 'center' }}>
          Active Trades
        </Typography>
        {renderContent()}
      </Box>
    </Box>
  );
};

export default ActiveTrade;