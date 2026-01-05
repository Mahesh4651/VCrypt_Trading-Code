import {
    alpha,
    AppBar,
    Avatar,
    Box,
    Button,
    IconButton,
    InputBase,
    Menu,
    MenuItem,
    Toolbar,
    Typography
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import ShowChartIcon from '@mui/icons-material/ShowChart';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import LogoutIcon from '@mui/icons-material/Logout';
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { logout } from '../Redux/Auth/authSlice';
import PlaceTrade from '../Trading/PlaceTrade';
import MainDashSideBar from './MainDashSideBar';
import SpeedIcon from '@mui/icons-material/Speed';
import { Person } from '@mui/icons-material';
import { setNavigating, setSearchQuery } from '../Redux/Trade/tradeSlice';
import CloseIcon from '@mui/icons-material/Close';
import Logo from '../../assets/logo1.png'

const Navbar = () => {
    const [anchorEl, setAnchorEl] = useState(null);
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [openTradeModal, setOpenTradeModal] = useState(false);
    const [searchValue, setSearchValue] = useState('');

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { isAuthenticated, token } = useSelector((state) => state.auth);
    const user = useSelector((state) => state.auth.user);

    const userInitial = user?.username ? user.username.charAt(0).toUpperCase() : <Person />;

    const handleLogout = () => {
        dispatch(logout());
        navigate('/');
        handleClose();
    };

    const handleClick = (event) => setAnchorEl(event.currentTarget);
    const handleClose = () => setAnchorEl(null);
    const toggleDrawer = (newOpen) => () => setDrawerOpen(newOpen);

    // Handle search input change
    const handleSearchChange = (event) => {
        const value = event.target.value;
        setSearchValue(value); // Update local state
        dispatch(setSearchQuery(value)); // Dispatch to Redux store
    };
    const handleClearSearch = () => {
        setSearchValue(''); // Clear local state
        dispatch(setSearchQuery('')); // Clear Redux store
    };


    const handleNavigate = (path) => {
        dispatch(setNavigating(true)); // Set navigating to true
        navigate(path);
        setTimeout(() => {
            dispatch(setNavigating(false)); // Reset after navigation
        }, 500); // Adjust delay as needed
    };

    return (
        <>
            <AppBar
                position="sticky"
                sx={{
                    background: "rgba(0, 0, 0, 0.4)",
                    backdropFilter: "blur(10px)",
                    boxShadow: "0 4px 10px rgba(0, 0, 0, 0.3)"
                }}
            >
                <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
                    <Box sx={{ display: "flex", alignItems: "center" }}>
                        <IconButton
                            onClick={toggleDrawer(true)}
                            size="large"
                            edge="start"
                            color="inherit"
                            aria-label="menu"
                        >
                            <MenuIcon />
                        </IconButton>

                       <Box sx={{display:"flex"}}>
                       <Link to="/dashboard" >  <Box component="img" src={Logo}  sx={{width:"60px", height:"60px"}} /></Link> 
                       </Box>
                    </Box>
                    <Box
                        sx={{
                            flexGrow: 1,
                            display: { xs: 'none', md: 'flex' },
                            justifyContent: 'center',
                            position: 'relative',
                            maxWidth: '50%', // Optional: Limits the width for better control
                        }}
                    >
                        <InputBase
                            placeholder="Search…"
                            value={searchValue} // Bind to local state
                            sx={{
                                backgroundColor: alpha("#ffffff", 0.15),
                                padding: "8px 40px 8px 16px", // Increased right padding for clear icon space
                                borderRadius: "15px",
                                height: "40px",
                                width: { xs: "60%", sm: "65%" }, // Adjusted width for consistency
                                '&:hover': { backgroundColor: alpha("#ffffff", 0.25) },
                                color: "white",
                                position: 'relative', // Ensure InputBase is the reference for absolute positioning
                            }}
                            onChange={handleSearchChange}
                        />
                        {searchValue && (
                            <IconButton
                                size="small"
                                onClick={handleClearSearch}
                                sx={{
                                    position: 'absolute',
                                    right: '110px', // Distance from the right edge of InputBase
                                    top: '50%',
                                    transform: 'translateY(-50%)',
                                    color: 'wheat', // Matches your CloseIcon color
                                    padding: '4px', // Smaller padding for a compact look
                                }}
                            >
                                <CloseIcon fontSize="small" />
                            </IconButton>
                        )}
                    </Box>
                    <Box sx={{ display: "flex", gap: "6px", alignItems: "center" }}>
                        {/* <Link to="/dashboard/activetrade"> */}
                            <Button
                                variant="contained"
                                endIcon={<SpeedIcon />}
                                sx={{ bgcolor: "black", height: "40px", display: { xs: 'none', md: 'flex' } }}
                                onClick={() => handleNavigate('/dashboard/activetrade')} // Use handleNavigate
                            >
                                Active Trade
                            </Button>
                        {/* </Link> */}

                        <Button
                            variant="contained"
                            endIcon={<ShowChartIcon />}
                            sx={{ bgcolor: "black", height: "40px", }}
                            onClick={() => setOpenTradeModal(true)}
                        >
                            Trade
                        </Button>

                        {/* Avatar: show menu if authenticated, else navigate to sign in */}
                        {isAuthenticated ? (
                            <>
                                <IconButton onClick={handleClick} >
                                    <Avatar sx={{ backgroundColor: "#769F60" }}>{userInitial}</Avatar>
                                </IconButton>
                                <Menu
                                    anchorEl={anchorEl}
                                    open={Boolean(anchorEl)}
                                    onClose={handleClose}
                                >
                                    <MenuItem
                                        component={Link}
                                        to="/dashboard/userprofile"
                                        onClick={handleClose}
                                    >
                                        <AccountCircleIcon sx={{ mr: 1 }} />
                                        Profile
                                    </MenuItem>
                                    <MenuItem onClick={handleLogout}>
                                        <LogoutIcon sx={{ mr: 1 }} />
                                        Logout
                                    </MenuItem>
                                </Menu>
                            </>
                        ) : (
                            <IconButton onClick={() => navigate('/signin')}>
                                <Avatar>{userInitial}</Avatar>
                            </IconButton>
                        )}
                    </Box>
                </Toolbar>
            </AppBar>

            <MainDashSideBar
                open={drawerOpen}
                toggleDrawer={toggleDrawer}
                handleLogout={handleLogout}
            />

            <PlaceTrade
                open={openTradeModal}
                handleClosetrade={() => setOpenTradeModal(false)}
            />
        </>
    );
};

export default Navbar;
