import { Box, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, TextField, MenuItem, Select, InputLabel, FormControl } from '@mui/material';
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const History = () => {
  const [trades, setTrades] = useState([]);
  const [filteredTrades, setFilteredTrades] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [symbolFilter, setSymbolFilter] = useState('');
  const [typeFilter, setTypeFilter] = useState('');

  // Base URL for your Spring Boot backend
  const API_BASE_URL = import.meta.env.VITE_API_URL; // Use VITE_API_URL from env file
  const API_URL = `${API_BASE_URL}/api/trades`;

  // Fetch completed trade data when the component mounts
  useEffect(() => {
    const fetchCompletedTrades = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          throw new Error('No authentication token found. Please log in.');
        }

        const headers = { Authorization: `Bearer ${token}` };

        // Fetch only completed trades
        const response = await axios.get(`${API_URL}/completed`, { headers });

        // Set trades with status COMPLETED
        const completedTrades = response.data.map(trade => ({ ...trade, status: 'COMPLETED' }));

        setTrades(completedTrades);
        setFilteredTrades(completedTrades);
        setLoading(false);
      } catch (err) {
        setError(err.message || 'Failed to fetch completed trade data');
        setLoading(false);
      }
    };

    fetchCompletedTrades();
  }, [API_URL]); // Depend on API_URL to ensure correct URL is used

  // Apply filters whenever filter values or trades change
  useEffect(() => {
    let filtered = [...trades];

    if (symbolFilter) {
      filtered = filtered.filter(trade =>
        trade.symbol.toLowerCase().includes(symbolFilter.toLowerCase())
      );
    }

    if (typeFilter) {
      filtered = filtered.filter(trade => trade.type === typeFilter);
    }

    setFilteredTrades(filtered);
  }, [symbolFilter, typeFilter, trades]);

  // Render loading state
  if (loading) {
    return <Box>Loading completed trade history...</Box>;
  }

  // Render error state
  if (error) {
    return <Box color="error.main">Error: {error}</Box>;
  }

  // console.log(trade);
  console.log(filteredTrades);

  return (
    <Box sx={{ p: 3, height: "100vh" }}>
      <Typography variant="h4" gutterBottom sx={{ color: "whitesmoke" }}>
        Completed Trade History
      </Typography>

      {/* Filter Controls */}
      <Box sx={{ mb: 3, display: 'flex', gap: 2 }}>
        <TextField
          label="Filter by Symbol"
          value={symbolFilter}
          onChange={(e) => setSymbolFilter(e.target.value)}
          variant="outlined"
          size="small"
          sx={{
            input: { color: "white" },
            label: { color: "gray" },
            "& label.Mui-focused": { color: "whitesmoke" },
            "& .MuiOutlinedInput-root": {
              "& fieldset": { borderColor: "white" },
              "&:hover fieldset": { borderColor: "whitesmoke" },
              "&.Mui-focused fieldset": { borderColor: "#00ffcc" },
            },
            "& .MuiInputBase-root": {
              backgroundColor: "#1a1a1a",
            },
            "& .MuiFormHelperText-root": { color: "#ff4c4c" },
          }}
        />
      </Box>

      {/* Completed Trades Table */}
      <TableContainer component={Paper} sx={{ backgroundColor: "#121314" }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ color: "whitesmoke" }}>Symbol</TableCell>
              <TableCell style={{ color: "whitesmoke" }}>Type</TableCell>
              <TableCell style={{ color: "whitesmoke" }}>Amount</TableCell>
              <TableCell style={{ color: "whitesmoke" }}>Price</TableCell>
              <TableCell style={{ color: "whitesmoke" }}>Total Cost</TableCell>
              <TableCell style={{ color: "whitesmoke" }}>Profit/Loss</TableCell>
              <TableCell style={{ color: "whitesmoke" }}>Executed At</TableCell>
              <TableCell style={{ color: "whitesmoke" }}>Closed At</TableCell>
              <TableCell style={{ color: "whitesmoke" }}>Status</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredTrades.length > 0 ? (
              filteredTrades.map((trade) => (
                <TableRow key={trade.tradeId}>
                  <TableCell style={{ color: "whitesmoke" }}>{trade.symbol}</TableCell>
                  <TableCell style={{ color: "whitesmoke" }}>{trade.type}</TableCell>
                  <TableCell style={{ color: "whitesmoke" }}>{trade.amount}</TableCell>
                  <TableCell style={{ color: "whitesmoke" }}>{trade.price}</TableCell>
                  <TableCell style={{ color: "whitesmoke" }}>{trade.totalCost}</TableCell>
                  <TableCell
                    sx={{
                      color:
                        trade.profitLoss === null || trade.profitLoss === undefined
                          ? 'whitesmoke'
                          : trade.profitLoss >= 0
                            ? 'green'
                            : 'red',
                    }}
                  >
                    {trade.profitLoss !== null && trade.profitLoss !== undefined
                      ? trade.profitLoss.toFixed(2)
                      : 'N/A'}
                  </TableCell>
                  <TableCell style={{ color: "whitesmoke" }}>
                    {trade.executedAt ? new Date(trade.executedAt).toLocaleString() : 'N/A'}
                  </TableCell>
                  <TableCell style={{ color: "whitesmoke" }}>
                    {trade.closedAt ? new Date(trade.closedAt).toLocaleString() : 'N/A'}
                  </TableCell>
                  <TableCell style={{ color: "whitesmoke" }}>{trade.status}</TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={10} align="center" style={{ color: "whitesmoke" }}>
                  No completed trades found.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default History;
