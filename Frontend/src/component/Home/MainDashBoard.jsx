// src/components/MainDashBoard.js
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, useMediaQuery, useTheme, TableSortLabel, Pagination } from "@mui/material";
import React, { useCallback, useEffect, useMemo, useState } from "react";
import { useDispatch, useSelector } from 'react-redux';
import { initializeWebSocket, disconnectWebSocket, setNavigating } from '../Redux/Trade/tradeSlice';
import TradingViewWidget from "./TradingViewWidget";
import { useLocation, useNavigate } from "react-router-dom";

const MainDashBoard = () => {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("md"));
  const screenWidth = window.screen.width;
  const screenHeight = window.screen.height;
  const navigate = useNavigate();
  const location = useLocation();

  let rowsPerPageOptions;
  let initialRowsPerPage;

  if (screenWidth >= 2560 || screenHeight >= 1600) {
    rowsPerPageOptions = [12, 15, 20];
    initialRowsPerPage = 14;
  } else if (screenWidth >= 1920 || screenHeight >= 1080) {
    rowsPerPageOptions = [10, 12, 15];
    initialRowsPerPage = 12;
  } else {
    rowsPerPageOptions = [5, 8, 12];
    initialRowsPerPage = 8;
  }

  const dispatch = useDispatch();
  const cryptoList = useSelector((state) => state.trade.cryptoPrices || []);
  const searchQuery = useSelector((state) => state.trade.searchQuery); // Get search query from Redux
  const isNavigating = useSelector((state) => state.trade.isNavigating); // Get navigation status

  // Changed initial order to "desc" and orderBy to "quoteVolume" for highest volume first
  const [order, setOrder] = useState("desc");
  const [orderBy, setOrderBy] = useState("quoteVolume");
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(initialRowsPerPage);

  // Initialize WebSocket and cleanup
  useEffect(() => {
    if (!isNavigating) {
      dispatch(initializeWebSocket());
    }

    return () => {
      dispatch(disconnectWebSocket());
    };
  }, [dispatch, isNavigating]);

  // Detect route changes and toggle isNavigating
  useEffect(() => {
    dispatch(setNavigating(true));
    const timer = setTimeout(() => {
      dispatch(setNavigating(false));
    }, 500); // Adjust delay based on navigation speed
    return () => clearTimeout(timer);
  }, [location, dispatch]);


  const handleSort = useCallback((property) => {
    const isAsc = orderBy === property && order === "asc";
    setOrder(isAsc ? "desc" : "asc");
    setOrderBy(property);
  }, [order, orderBy]);

  // Filter cryptoList based on searchQuery
  const filteredCryptoList = useMemo(() => {
    if (!searchQuery) return cryptoList;
    return cryptoList.filter((coin) =>
      coin.symbol.toLowerCase().includes(searchQuery.toLowerCase())
    );
  }, [cryptoList, searchQuery]);

  // const sortedCryptoList = [...cryptoList].sort((a, b) => {
  //   if (orderBy === "priceChange24h") {
  //     return order === "asc" ? a.priceChange24h - b.priceChange24h : b.priceChange24h - a.priceChange24h;
  //   }
  //   if (orderBy === "price") {
  //     return order === "asc" ? a.price - b.price : b.price - a.price;
  //   }
  //   if (orderBy === "quoteVolume") {
  //     return order === "asc" ? a.quoteVolume - b.quoteVolume : b.quoteVolume - a.quoteVolume;
  //   }
  //   return 0;
  // });

  const handleNavigate = (path) => {
    dispatch(setNavigating(true)); // Set navigating to true before navigation
    navigate(path);
  };

  const sortedCryptoList = useMemo(() => {
    return [...filteredCryptoList].sort((a, b) => {
      if (orderBy === "priceChange24h") {
        return order === "asc" ? a.priceChange24h - b.priceChange24h : b.priceChange24h - a.priceChange24h;
      }
      if (orderBy === "price") {
        return order === "asc" ? a.price - b.price : b.price - a.price;
      }
      if (orderBy === "quoteVolume") {
        return order === "asc" ? a.quoteVolume - b.quoteVolume : b.quoteVolume - a.quoteVolume;
      }
      return 0;
    });
  }, [filteredCryptoList, order, orderBy]);

  return (
    <Box sx={{ width: "100%", minHeight: "100vh", display: "flex", flexDirection: isMobile ? "column" : "row", p: 1 }}>
      <Box sx={{ width: isMobile ? "100%" : "50%", height: "auto", overflowX: "auto" }}>
        <TableContainer component={Box}>
          <Table >
            <TableHead>
              <TableRow >
                <TableCell sx={{ color: "whitesmoke" }}><b>Sr No</b></TableCell>
                <TableCell sx={{ color: "whitesmoke" }}><b>Coin</b></TableCell>
                <TableCell sx={{ color: "whitesmoke" }}><b>Symbol</b></TableCell>
                <TableCell>
                  <TableSortLabel sx={{ color: "whitesmoke" }} active={orderBy === "price"} direction={orderBy === "price" ? order : "asc"} onClick={() => handleSort("price")}>
                    <b>Price (USD)</b>
                  </TableSortLabel>
                </TableCell>
                <TableCell>
                  <TableSortLabel sx={{ color: "whitesmoke" }} active={orderBy === "priceChange24h"} direction={orderBy === "priceChange24h" ? order : "asc"} onClick={() => handleSort("priceChange24h")}>
                    <b>Change (24h)</b>
                  </TableSortLabel>
                </TableCell>
                <TableCell>
                  <TableSortLabel style={{ color: "whitesmoke" }} active={orderBy === "quoteVolume"} direction={orderBy === "quoteVolume" ? order : "asc"} onClick={() => handleSort("quoteVolume")}>
                    <b sx={{ color: "whitesmoke" }} >Market Volume</b>
                  </TableSortLabel>
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {sortedCryptoList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((coin, index) => (
                <TableRow key={coin.symbol}>
                  <TableCell style={{ color: "whitesmoke" }} >{page * rowsPerPage + index + 1}</TableCell>
                  <TableCell style={{ color: "whitesmoke" }} ><img src={coin.image} alt={coin.symbol} width="30" /></TableCell>
                  <TableCell style={{ color: "whitesmoke" }} >{coin.symbol}</TableCell>
                  <TableCell style={{ color: "whitesmoke" }} >${parseFloat(coin.price).toFixed(5)}</TableCell>
                  <TableCell style={{ color: coin.priceChange24h >= 0 ? "green" : "red" }}>{parseFloat(coin.priceChange24h).toFixed(2)}%</TableCell>
                  <TableCell style={{ color: "whitesmoke" }} >${parseFloat(coin.quoteVolume).toFixed(2)}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>

        <Box sx={{
          width: "97%", display: "flex", justifyContent: "center", alignItems: "center", p: 1, color: "white", background: "rgba(255, 255, 255, 0.1)", // Subtle glass effect
          backdropFilter: "blur(10px)",
          borderRadius: "8px",
          boxShadow: "0 4px 10px rgba(255, 255, 255, 0.1)"
        }}>
          <Pagination
            count={Math.ceil(filteredCryptoList.length / rowsPerPage)}
            color="primary"
            page={page + 1}
            onChange={(event, value) => setPage(value - 1)}
            style={{color:"white"}}
          />
        </Box>
      </Box>

      <Box sx={{ width: isMobile ? "100%" : "50%", height: { xs: "60vh", sm: "80vh", md: "100vh" } }}>
        <TradingViewWidget />
      </Box>
    </Box>
  );
};

export default MainDashBoard;
