import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Box, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import { fetchPortfolioSummary } from '../Redux/Auth/portfolioSlice';

const Portfolio = () => {
    const dispatch = useDispatch();
    const { holdings, demoBalance, totalPortfolioValue, status, error } = useSelector((state) => state.portfolio);
    const { isAuthenticated } = useSelector((state) => state.auth);

    useEffect(() => {
        if (isAuthenticated) {
            dispatch(fetchPortfolioSummary());
        }
    }, [dispatch, isAuthenticated]);

    if (status === 'loading') {
        return <Typography>Loading portfolio...</Typography>;
    }

    if (error) {
        return <Typography color="error">Error: {error}</Typography>;
    }

    return (
        <Box sx={{ padding: 3, color: "white" }}>
            <Typography variant="h4" gutterBottom>
                Portfolio Summary
            </Typography>
            <Typography variant="h6" component="div">
                Demo Balance:{' '}
                <Typography component="span" sx={{ color: '#1976D2' }} variant="h6">
                    ${demoBalance.toFixed(2)}
                </Typography>
            </Typography>
            <Typography variant="h6" component="div">
                Total Portfolio Value:{' '}
                <Typography component="span" sx={{ color: 'success.main' }} variant="h6">
                    ${totalPortfolioValue.toFixed(2)}
                </Typography>
            </Typography>
            <Typography variant="h6" component="div" gutterBottom>
                Total Asset Value:{' '}
                <Typography component="span" sx={{ color: 'warning.main' }} variant="h6">
                    ${(demoBalance + totalPortfolioValue).toFixed(2)}
                </Typography>
            </Typography>

            <TableContainer component={Paper} sx={{ marginTop: 2 }}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Symbol</TableCell>
                            <TableCell align="right">Quantity</TableCell>
                            <TableCell align="right">Average Buy Price</TableCell>
                            <TableCell align="right">Current Price</TableCell>
                            <TableCell align="right">Total Value</TableCell>
                            <TableCell align="right">Unrealized P/L</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {holdings.map((item, index) => (
                            <TableRow key={index}>
                                <TableCell>{item.coinSymbol}</TableCell>
                                <TableCell align="right">{item.quantity.toFixed(8)}</TableCell>
                                <TableCell align="right">${item.averageBuyPrice.toFixed(2)}</TableCell>
                                <TableCell align="right">${item.currentPrice.toFixed(2)}</TableCell>
                                <TableCell align="right">${item.totalValue.toFixed(2)}</TableCell>
                                <TableCell align="right" sx={{ color: item.unrealizedPL >= 0 ? 'green' : 'red' }}>
                                    ${item.unrealizedPL.toFixed(2)}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default Portfolio;