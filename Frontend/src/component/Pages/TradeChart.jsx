import { Box } from '@mui/system'
import React from 'react'
import TradingViewWidget from '../Home/TradingViewWidget'

const TradeChart = () => {
  return (
    <Box sx={{height:"100vh" , width:"100%"}}>
        <TradingViewWidget/>
    </Box>
  )
}

export default TradeChart