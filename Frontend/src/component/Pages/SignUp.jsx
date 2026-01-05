import {
    Box,
    Paper,
    Typography,
    TextField,
    Button,
    InputAdornment,
    IconButton,
    CircularProgress,
} from '@mui/material';
import {
    Person,
    Email,
    Lock,
    Visibility,
    VisibilityOff,
} from '@mui/icons-material';
import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
//   import { registerUser } from '../store/authSlice';
import { useNavigate } from 'react-router-dom';
import { registerUser } from '../Redux/Auth/authSlice';
import FullScreenSpinner from '../FullScreenSpinner';
import Logo from '../../assets/bg.jpg'

const SignUp = () => {
    const [formData, setFormData] = useState({
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
    });
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [spinnerVisible, setSpinnerVisible] = useState(false); // 👈 Spinner state


    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { loading, error, isAuthenticated } = useSelector((state) => state.auth);

    // Move navigation into useEffect
    useEffect(() => {
        if (isAuthenticated) {
            navigate('/');
        }
    }, [isAuthenticated, navigate]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };


    const handleSubmit = async (e) => {
        e.preventDefault();

        if (formData.password !== formData.confirmPassword) {
            alert('Passwords do not match!');
            return;
        }

        setSpinnerVisible(true); // 👈 Show spinner before async logic

        setTimeout(async () => {
            try {
                const userData = {
                    username: formData.username,
                    firstName: formData.firstName,
                    lastName: formData.lastName,
                    email: formData.email,
                    password: formData.password,
                };
                await dispatch(registerUser(userData)).unwrap();
                console.log('Registration successful');
                // useEffect will navigate after isAuthenticated becomes true
            } catch (err) {
                console.error('Registration error:', err);
            } finally {
                setSpinnerVisible(false); // 👈 Hide spinner after timeout
            }
        }, 3000); // You can change this to 5000 for 5 seconds
    };


    const togglePasswordVisibility = () => setShowPassword((prev) => !prev);
    const toggleConfirmPasswordVisibility = () => setShowConfirmPassword((prev) => !prev);

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

            {spinnerVisible && <FullScreenSpinner />}
            <Paper
                elevation={3}
                sx={{
                    width: { xs: '90%', sm: '45%' },
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
                <Typography variant="h4" align="center" sx={{ fontWeight: "700", color: "whitesmoke" }} gutterBottom>
                    Sign Up
                </Typography>

                {error && (
                    <Typography color="error" align="center">
                        {typeof error === 'string' ? error : 'Registration failed'}
                    </Typography>
                )}

                <form onSubmit={handleSubmit}>

                    <Box sx={{ display: "flex", gap: 3, flexDirection: { xs: "column", sm: "row" } }}>
                        <TextField
                            fullWidth
                            label="Username"
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
                                input: { color: "white" }, // Text color
                                label: { color: "gray" }, // Label color
                                "& label.Mui-focused": { color: "whitesmoke" }, // Label color when focused
                                "& .MuiOutlinedInput-root": {
                                    "& fieldset": { borderColor: "white" }, // Default border color
                                    "&:hover fieldset": { borderColor: "whitesmoke" }, // Hover border color
                                    "&.Mui-focused fieldset": { borderColor: "#00ffcc" }, // Focus border color
                                },
                                "& .MuiInputBase-root": {
                                    backgroundColor: "transparent", // Dark input background
                                },
                                "& .MuiFormHelperText-root": { color: "#ff4c4c" }, // Error message color
                            }}

                        />
                        <TextField
                            fullWidth
                            label="Email"
                            name="email"
                            type="email"
                            value={formData.email}
                            onChange={handleChange}
                            margin="normal"
                            required
                            variant="outlined"
                            disabled={loading}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <Email sx={{ color: 'white' }} />
                                    </InputAdornment>
                                ),
                            }}
                            sx={{
                                input: { color: "white" }, // Text color
                                label: { color: "gray" }, // Label color
                                "& label.Mui-focused": { color: "whitesmoke" }, // Label color when focused
                                "& .MuiOutlinedInput-root": {
                                    "& fieldset": { borderColor: "white" }, // Default border color
                                    "&:hover fieldset": { borderColor: "whitesmoke" }, // Hover border color
                                    "&.Mui-focused fieldset": { borderColor: "#00ffcc" }, // Focus border color
                                },
                                "& .MuiInputBase-root": {
                                    backgroundColor: "transparent", // Dark input background
                                },
                                "& .MuiFormHelperText-root": { color: "#ff4c4c" }, // Error message color
                            }}
                        />
                    </Box>

                    <Box sx={{ display: "flex", gap: 3, flexDirection: { xs: "column", sm: "row" } }}>
                        <TextField
                            fullWidth
                            label="First Name"
                            name="firstName"
                            value={formData.firstName}
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
                                input: { color: "white" }, // Text color
                                label: { color: "gray" }, // Label color
                                "& label.Mui-focused": { color: "whitesmoke" }, // Label color when focused
                                "& .MuiOutlinedInput-root": {
                                    "& fieldset": { borderColor: "white" }, // Default border color
                                    "&:hover fieldset": { borderColor: "whitesmoke" }, // Hover border color
                                    "&.Mui-focused fieldset": { borderColor: "#00ffcc" }, // Focus border color
                                },
                                "& .MuiInputBase-root": {
                                    backgroundColor: "transparent", // Dark input background
                                },
                                "& .MuiFormHelperText-root": { color: "#ff4c4c" }, // Error message color
                            }}
                        />

                        <TextField
                            fullWidth
                            label="Last Name"
                            name="lastName"
                            value={formData.lastName}
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
                                input: { color: "white" }, // Text color
                                label: { color: "gray" }, // Label color
                                "& label.Mui-focused": { color: "whitesmoke" }, // Label color when focused
                                "& .MuiOutlinedInput-root": {
                                    "& fieldset": { borderColor: "white" }, // Default border color
                                    "&:hover fieldset": { borderColor: "whitesmoke" }, // Hover border color
                                    "&.Mui-focused fieldset": { borderColor: "#00ffcc" }, // Focus border color
                                },
                                "& .MuiInputBase-root": {
                                    backgroundColor: "transparent", // Dark input background
                                },
                                "& .MuiFormHelperText-root": { color: "#ff4c4c" }, // Error message color
                            }}
                        />

                    </Box>
                    <Box sx={{ display: "flex", gap: 3, flexDirection: { xs: "column", sm: "row" } }}>
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
                                            {showPassword ? <Visibility sx={{ color: 'white' }} /> : <VisibilityOff sx={{ color: 'white' }} />}
                                        </IconButton>
                                    </InputAdornment>
                                ),
                            }}
                            sx={{
                                input: { color: "white" }, // Text color
                                label: { color: "gray" }, // Label color
                                "& label.Mui-focused": { color: "whitesmoke" }, // Label color when focused
                                "& .MuiOutlinedInput-root": {
                                    "& fieldset": { borderColor: "white" }, // Default border color
                                    "&:hover fieldset": { borderColor: "whitesmoke" }, // Hover border color
                                    "&.Mui-focused fieldset": { borderColor: "#00ffcc" }, // Focus border color
                                },
                                "& .MuiInputBase-root": {
                                    backgroundColor: "transparent", // Dark input background
                                },
                                "& .MuiFormHelperText-root": { color: "#ff4c4c" }, // Error message color
                            }}
                        />

                        <TextField
                            fullWidth
                            label="Confirm Password"
                            name="confirmPassword"
                            type={showConfirmPassword ? 'text' : 'password'}
                            value={formData.confirmPassword}
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
                                    <InputAdornment position="end" >
                                        <IconButton onClick={toggleConfirmPasswordVisibility} edge="end">
                                            {showConfirmPassword ? <Visibility sx={{ color: 'white' }} /> : <VisibilityOff sx={{ color: 'white' }} />}
                                        </IconButton>
                                    </InputAdornment>
                                ),
                            }}
                            sx={{
                                input: { color: "white" }, // Text color
                                label: { color: "gray" }, // Label color
                                "& label.Mui-focused": { color: "whitesmoke" }, // Label color when focused
                                "& .MuiOutlinedInput-root": {
                                    "& fieldset": { borderColor: "white" }, // Default border color
                                    "&:hover fieldset": { borderColor: "whitesmoke" }, // Hover border color
                                    "&.Mui-focused fieldset": { borderColor: "#00ffcc" }, // Focus border color
                                },
                                "& .MuiInputBase-root": {
                                    backgroundColor: "transparent", // Dark input background
                                },
                                "& .MuiFormHelperText-root": { color: "#ff4c4c" }, // Error message color
                            }}
                        />
                    </Box>





                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        fullWidth
                        sx={{ mt: 2, py: 1.5 }}
                        disabled={loading}
                    >
                        {loading ? <CircularProgress size={24} /> : 'Sign Up'}
                    </Button>
                </form>

                <Typography variant="body2" align="center" color='white'>
                    Already have an account?{' '}
                    <Button variant="text" onClick={() => navigate('/')}>
                        Sign In
                    </Button>
                </Typography>
            </Paper>
        </Box>
    );
};

export default SignUp;