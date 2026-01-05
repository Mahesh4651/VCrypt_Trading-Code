// import React from 'react';
// import Box from '@mui/material/Box';
// import Drawer from '@mui/material/Drawer';
// import Button from '@mui/material/Button';
// import List from '@mui/material/List';
// import Divider from '@mui/material/Divider';
// import ListItem from '@mui/material/ListItem';
// import ListItemButton from '@mui/material/ListItemButton';
// import ListItemIcon from '@mui/material/ListItemIcon';
// import ListItemText from '@mui/material/ListItemText';
// import HomeIcon from '@mui/icons-material/Home';
// import ShowChartIcon from '@mui/icons-material/ShowChart';
// import HistoryIcon from '@mui/icons-material/History';
// import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';
// import InfoIcon from '@mui/icons-material/Info';
// import LogoutIcon from '@mui/icons-material/Logout';

// const MainDashSideBar = () => {
//   const [open, setOpen] = React.useState(false);

//   const toggleDrawer = (newOpen) => () => {
//     setOpen(newOpen);
//   };

//   const DrawerList = (
//     <Box sx={{ width: 250 }} role="presentation" onClick={toggleDrawer(false)}>
//       <List>
//         {[
//           { text: 'Home', icon: <HomeIcon /> },
//           { text: 'Trade Chart', icon: <ShowChartIcon /> },
//           { text: 'History (Activity)', icon: <HistoryIcon /> },
//           { text: 'Portfolio', icon: <AccountBalanceWalletIcon /> },
//           { text: 'About', icon: <InfoIcon /> },
//         ].map((item, index) => (
//           <ListItem key={item.text} disablePadding>
//             <ListItemButton>
//               <ListItemIcon>{item.icon}</ListItemIcon>
//               <ListItemText primary={item.text} />
//             </ListItemButton>
//           </ListItem>
//         ))}
//       </List>
//       <Divider />
//       <List>
//         <ListItem disablePadding>
//           <ListItemButton>
//             <ListItemIcon>
//               <LogoutIcon />
//             </ListItemIcon>
//             <ListItemText primary="Logout" />
//           </ListItemButton>
//         </ListItem>
//       </List>
//     </Box>
//   );

//   return (
//     <div className="mt-4">
//       <Button onClick={toggleDrawer(true)}>Open Drawer</Button>
//       <Drawer open={open} onClose={toggleDrawer(false)}>
//         {DrawerList}
//       </Drawer>
//     </div>
//   );
// };

// export default MainDashSideBar;


import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import HomeIcon from '@mui/icons-material/Home';
import HistoryIcon from '@mui/icons-material/History';
import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';
import InfoIcon from '@mui/icons-material/Info';
import LogoutIcon from '@mui/icons-material/Logout';
import EqualizerIcon from '@mui/icons-material/Equalizer';
import SpeedIcon from '@mui/icons-material/Speed';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import WalletIcon from '@mui/icons-material/Wallet';
import { Link, useNavigate } from 'react-router-dom';
import { Typography } from '@mui/material';
import { logout } from '../Redux/Auth/authSlice';
import { useDispatch } from 'react-redux';
import { setNavigating } from '../Redux/Trade/tradeSlice';
import Logo from '../../assets/logo1.png'



const MainDashSideBar = ({ open, toggleDrawer }) => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleLogout = () => {
        dispatch(logout());
        navigate('/');
    };

    const handleNavigate = (path) => {
        dispatch(setNavigating(true)); // Set navigating to true
        navigate(path);
        setTimeout(() => {
            dispatch(setNavigating(false)); // Reset after navigation
        }, 500); // Adjust delay as needed
    };

    const DrawerList = (
        <Box sx={{ width: 250, bgcolor: "#0d0d0d", height: "100vh", color: "whitesmoke" }} role="presentation" onClick={toggleDrawer(false)}>
            <Box component="img" src={Logo} sx={{ width:"60px", height:"60px"}}/>
            <List>
                {[
                    { text: 'Home', icon: <HomeIcon sx={{ color: "whitesmoke" }} />, path: '/dashboard' },
                    { text: 'Trade Chart', icon: <EqualizerIcon sx={{ color: "whitesmoke" }} />, path: '/dashboard/tradechart' },
                    { text: 'Active Trade', icon: <SpeedIcon sx={{ color: "whitesmoke" }} />, path: '/dashboard/activetrade' },
                    { text: 'History (Activity)', icon: <HistoryIcon sx={{ color: "whitesmoke" }} />, path: '/dashboard/history' },
                    { text: 'Portfolio', icon: <AccountBalanceWalletIcon sx={{ color: "whitesmoke" }} />, path: '/dashboard/portfolio' },
                    { text: 'Wallet', icon: <WalletIcon sx={{ color: "whitesmoke" }} />, path: '/dashboard/wallet' },
                    { text: 'About', icon: <InfoIcon sx={{ color: "whitesmoke" }} />, path: '/dashboard/aboutus' },
                ].map((item) => (
                    <ListItem key={item.text} disablePadding>
                        <ListItemButton onClick={() => handleNavigate(item.path)}>
                            <ListItemIcon>{item.icon}</ListItemIcon>
                            <ListItemText primary={item.text} />
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
            <Divider sx={{ backgroundColor: "whitesmoke" }} />
            <List>
                <ListItem disablePadding>
                    <ListItemButton onClick={() => handleNavigate('/dashboard/userprofile')} >
                        <ListItemIcon><AccountCircleIcon sx={{ color: "whitesmoke" }} /></ListItemIcon>
                        <ListItemText primary="Profile" />
                    </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                    <ListItemButton onClick={handleLogout}>
                        <ListItemIcon><LogoutIcon sx={{ color: "whitesmoke" }} /></ListItemIcon>
                        <ListItemText primary="Logout" />
                    </ListItemButton>
                </ListItem>
            </List>
        </Box>
    );

    return (
        <Drawer open={open} onClose={toggleDrawer(false)}>
            {DrawerList}
        </Drawer>
    );
};

export default MainDashSideBar;