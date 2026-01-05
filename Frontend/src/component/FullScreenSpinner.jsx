// src/components/FullScreenSpinner.jsx
import React from 'react';
import { Box } from '@mui/material';
import { DotSpinner } from 'ldrs/react';
import 'ldrs/react/DotSpinner.css'

const FullScreenSpinner = () => {
  return (
    <Box
      sx={{
        position: 'fixed',
        top: 0,
        left: 0,
        width: '100vw',
        height: '100vh',
        backdropFilter: 'blur(2px)', // 👈 blur effect restored
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        zIndex: 9999,
      }}
    >
      <DotSpinner size="60" speed="0.9" color="#01FFC3"  />
    </Box>
  );
};

export default FullScreenSpinner;
