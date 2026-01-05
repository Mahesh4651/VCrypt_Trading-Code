import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchUserData, updateUserData } from '../Redux/Auth/authSlice'; // Assume updateUserData is an action
import {
  Box,
  Card,
  CardContent,
  CardHeader,
  Typography,
  TextField,
  Button,
  Alert,
} from '@mui/material';
import Skeleton from '@mui/material/Skeleton';
import { AccountCircle, AttachMoney, TrendingUp } from '@mui/icons-material';
import axios from 'axios';

const UserProfile = () => {
  const dispatch = useDispatch();
  const { user, loading, error, isAuthenticated } = useSelector((state) => state.auth);

  // Local state for form inputs and feedback
  const [formData, setFormData] = useState({
    password: '',
    newUsername: '',
  });
  const [formError, setFormError] = useState(null);
  const [formSuccess, setFormSuccess] = useState(null);

  // Base URL for your Spring Boot backend
  const API_BASE_URL = import.meta.env.VITE_API_URL ; // Use VITE_API_URL from env file
  const API_URL = `${API_BASE_URL}/api/auth/updateusername`;

  useEffect(() => {
    if (isAuthenticated && !user) {
      dispatch(fetchUserData());
    }
  }, [dispatch, isAuthenticated, user]);

  // Handle form input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // Handle form submission for username update
  const handleUsernameUpdate = async (e) => {
    e.preventDefault();
    setFormError(null);
    setFormSuccess(null);

    try {
      const token = localStorage.getItem('token'); // Assuming JWT is stored in localStorage
      const response = await axios.post(
        API_URL,
        {
          password: formData.password,
          newUsername: formData.newUsername,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      // On success, update Redux store and local state
      const newToken = response.data; // Backend returns new JWT
      localStorage.setItem('token', newToken); // Update token in localStorage
      dispatch(updateUserData({ ...user, username: formData.newUsername })); // Update Redux store
      setFormSuccess('Username updated successfully!');
      setFormData({ password: '', newUsername: '' }); // Reset form
    } catch (err) {
      setFormError(
        err.response?.data || 'Failed to update username. Please check your password and try again.'
      );
    }
  };

  if (loading)
    return <Skeleton variant="rectangular" width="100%" height={240} sx={{ bgcolor: 'grey.800' }} />;
  if (error) return <Typography color="error" align="center">Error: {error.message || error}</Typography>;
  if (!user) return <Typography color="textSecondary" align="center">No user data available</Typography>;

  return (
    <Box sx={{ backgroundColor: '#0d0d0d', height: '90vh' }}>
      <div
        style={{
          maxWidth: '600px',
          margin: 'auto',
          padding: '24px',
          backgroundColor: '#121212',
          color: '#fff',
          borderRadius: '8px',
          boxShadow: '0 4px 12px rgba(0,0,0,0.3)',
          marginTop: '0px',
        }}
      >
        <Card sx={{ backgroundColor: '#1e1e1e', boxShadow: 3, borderRadius: 2 }}>
          <CardHeader
            title={
              <Box sx={{ display: 'flex', gap: 1 }}>
                <Typography variant="h5" component="div" display="flex" alignItems="center" gap={1} color="primary">
                  <AccountCircle />
                </Typography>
                <Typography sx={{ color: 'white' }}>
                  {user.firstName} {user.lastName}
                </Typography>
              </Box>
            }
          />
          <CardContent>
            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px', color: '#ccc' }}>
              <Typography>
                <strong>Username:</strong> {user.username}
              </Typography>
              <Typography>
                <strong>Email:</strong> {user.email}
              </Typography>
              <Typography display="flex" alignItems="center" gap={1} color="success.main">
                <AttachMoney /> <strong>Demo Balance:</strong> ${user.demoBalance.toFixed(2)}
              </Typography>
              <Typography display="flex" alignItems="center" gap={1} color="warning.main">
                <TrendingUp /> <strong>Portfolio Value:</strong> ${user.portfolioValue.toFixed(2)}
              </Typography>
              <Typography>
                <strong>Total Asset Value:</strong> ${user.totalAssetValue.toFixed(2)}
              </Typography>
            </div>

            {/* Username Update Form */}
            <Box component="form" onSubmit={handleUsernameUpdate} sx={{ mt: 3 }}>
              <Typography variant="h6" gutterBottom>
                Update Username
              </Typography>
              {formSuccess && <Alert severity="success" sx={{ mb: 2 }}>{formSuccess}</Alert>}
              {formError && <Alert severity="error" sx={{ mb: 2 }}>{formError}</Alert>}
              <TextField
                label="Current Password"
                name="password"
                type="password"
                value={formData.password}
                onChange={handleInputChange}
                fullWidth
                required
                sx={{ mb: 2, input: { color: '#fff' }, label: { color: '#ccc' }, fieldset: { borderColor: '#555' } }}
              />
              <TextField
                label="New Username"
                name="newUsername"
                value={formData.newUsername}
                onChange={handleInputChange}
                fullWidth
                required
                sx={{ mb: 2, input: { color: '#fff' }, label: { color: '#ccc' }, fieldset: { borderColor: '#555' } }}
              />
              <Button
                type="submit"
                variant="contained"
                color="primary"
                fullWidth
                sx={{ backgroundColor: '#1976d2', '&:hover': { backgroundColor: '#115293' } }}
              >
                Update Username
              </Button>
            </Box>
          </CardContent>
        </Card>
      </div>
    </Box>
  );
};

export default UserProfile;
