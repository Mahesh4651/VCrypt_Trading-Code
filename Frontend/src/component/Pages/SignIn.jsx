// src/components/SignIn.js
import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  CircularProgress,
  InputAdornment,
  IconButton
} from '@mui/material';
import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { loginUser, fetchUserData } from '../Redux/Auth/authSlice';
import { Lock, Person, Visibility, VisibilityOff } from '@mui/icons-material';
import FullScreenSpinner from '../FullScreenSpinner';
import Logo from '../../assets/bg.jpg'


const SignIn = () => {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });
  const [showPassword, setShowPassword] = useState(false);
  const [spinnerVisible, setSpinnerVisible] = useState(false); // 👈 Spinner state

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { loading, error, isAuthenticated } = useSelector((state) => state.auth);

  useEffect(() => {
    if (isAuthenticated) {
      dispatch(fetchUserData())
        .unwrap()
        .then(() => {
          navigate('/dashboard');
        })
        .catch((err) => {
          console.error('Failed to fetch user data:', err);
        });
    }
  }, [isAuthenticated, dispatch, navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSpinnerVisible(true); // 👈 Show spinner

    setTimeout(async () => {
      try {
        const result = await dispatch(loginUser(formData)).unwrap();
        console.log('Login successful, JWT token:', result.token);
      } catch (err) {
        console.error('Login error:', err);
      } finally {
        setSpinnerVisible(false); // 👈 Hide spinner after 5 sec
      }
    }, 3000);
  };

  const togglePasswordVisibility = () => setShowPassword((prev) => !prev);

  return (
    <Box
      sx={{
        height: '100vh',
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundImage: `url(${Logo})`,
        backgroundSize: 'cover', // Optional: makes image cover the whole box
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center',
        // backgroundColor: '#0d0d0d',
        marginTop: { xs: "100px", sm: "0px" }
      }}
    >
      {spinnerVisible && <FullScreenSpinner />} {/* 👈 Render spinner */}

      <Paper
        elevation={3}
        sx={{
          width: { xs: '90%', sm: '30%' },

          borderRadius: '20px',
          padding: 4,
          display: 'flex',
          flexDirection: 'column',
          gap: 2,
          backgroundColor: 'rgba(18, 19, 20, 0.5)', // Transparent background
          backdropFilter: 'blur(10px)',             // Frosted glass effect
          boxShadow: '0 8px 32px 0 rgba(31, 38, 135, 0.37)', // Optional: soft shadow
          border: '1px solid rgba(255, 255, 255, 0.18)', // Optional: subtle border
        }}
      >
        <Typography variant="h4" align="center" gutterBottom sx={{ color: "whitesmoke",  fontFamily: 'sans', }}>
          Sign In
        </Typography>

        {error && (
          <Typography color="error" align="center">
            {typeof error === 'string' ? error : 'Invalid credentials'}
          </Typography>
        )}

        <form onSubmit={handleSubmit}>
          <TextField
            fullWidth
            label="Username or Email"
            name="username"
            value={formData.username}
            onChange={handleChange}
            margin="normal"
            required
            variant="outlined"
            disabled={loading}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <Person sx={{ color: 'white' }} />
                </InputAdornment>
              ),
            }}
            sx={{
              "& .MuiInputBase-root": {
                backgroundColor: "transparent",
              },
              '& .MuiOutlinedInput-root': {
                '& fieldset': {
                  borderColor: 'white',
                },
                '&:hover fieldset': {
                  borderColor: 'white',
                },
                '&.Mui-focused fieldset': {
                  borderColor: 'white',
                },
                color: 'white',
              },
              '& .MuiInputLabel-root': {
                color: 'white',
              },
              '& .MuiInputLabel-root.Mui-focused': {
                color: 'white',
              },
            }}
          />

          <TextField
            fullWidth
            label="Password"
            name="password"
            type={showPassword ? 'text' : 'password'}
            value={formData.password}
            onChange={handleChange}
            margin="normal"
            required
            variant="outlined"
            disabled={loading}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <Lock sx={{ color: 'white' }} />
                </InputAdornment>
              ),
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton onClick={togglePasswordVisibility} edge="end">
                    {showPassword
                      ? <Visibility sx={{ color: 'white' }} />
                      : <VisibilityOff sx={{ color: 'white' }} />}
                  </IconButton>
                </InputAdornment>
              ),
            }}
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
                backgroundColor: "transparent",
              },
              "& .MuiFormHelperText-root": { color: "#ff4c4c" },
            }}
          />

          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            sx={{ mt: 2, py: 1.5 }}
            disabled={loading}
          >
            {loading ? <CircularProgress size={24} /> : 'Sign In'}
          </Button>
        </form>

        <Typography variant="body2" align="center" sx={{ color: "white" }}>
          Don't have an account?{' '}
          <Button variant="text" onClick={() => navigate('/signup')}>
            Sign Up
          </Button>
        </Typography>
      </Paper>
    </Box>
  );
};

export default SignIn;
