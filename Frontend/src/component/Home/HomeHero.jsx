import { Box, Typography, Button } from '@mui/material';
import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
// import { logout } from '../store/authSlice';
import { useNavigate } from 'react-router-dom';
import { logout } from '../Redux/Auth/authSlice';

const HomeHero = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { isAuthenticated, token } = useSelector((state) => state.auth);

  const handleLogout = () => {
    dispatch(logout());
    navigate('/signin'); // Redirect to sign-in page after logout
  };

  // Optional: Redirect to sign-in if not authenticated
  React.useEffect(() => {
    if (!isAuthenticated) {
      navigate('/signin');
    }
  }, [isAuthenticated, navigate]);

  return (
    <Box
      sx={{
        height: '97vh',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: '#f5f5f5',
      }}
    >
      <Typography variant="h4" gutterBottom>
        Welcome to Your Dashboard
      </Typography>
      <Typography variant="body1" gutterBottom>
        You are authenticated! JWT Token: {token ? token.substring(0, 10) + '...' : 'N/A'}
      </Typography>
      <Button
        variant="contained"
        color="secondary"
        onClick={handleLogout}
        sx={{ mt: 2, py: 1.5 }}
      >
        Logout
      </Button>

      <Button
        variant="contained"
        color="secondary"
        // onClick={}
        sx={{ mt: 2, py: 1.5 }}
      >
        SignUp
      </Button>
    </Box>
  );
};



export default HomeHero