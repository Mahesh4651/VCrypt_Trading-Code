// Wallet.jsx
import { Box, Button, Paper, Typography, Modal } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { fetchUserData } from '../Redux/Auth/authSlice';
import { AttachMoney, TrendingUp } from '@mui/icons-material';
import AddMoney from './AddMoney';

const Wallet = () => {
    const dispatch = useDispatch();
    const { user, error, isAuthenticated } = useSelector((state) => state.auth);
    const [openAddMoney, setOpenAddMoney] = useState(false);

    useEffect(() => {
        if (isAuthenticated && !user) {
            dispatch(fetchUserData());
        }
    }, [dispatch, isAuthenticated, user]);

    const handleOpenAddMoney = () => setOpenAddMoney(true);
    const handleCloseAddMoney = () => setOpenAddMoney(false);

    if (error) return <Typography color="error" align="center">Error: {error.message || error}</Typography>;
    if (!user) return <Typography color="textSecondary" align="center">No user data available</Typography>;

    return (
        <Box sx={{ width: "100%", height: "100vh", display: "flex", alignItems: "center", justifyContent: "center" }}>
            <Paper sx={{ height: "35vh", width: "50%", p: 4, display: "flex", flexDirection: "column", gap: 4, bgcolor: "#121314" }}>
                <Typography variant='h5' sx={{ fontWeight: "600", textAlign: "center", color: "whitesmoke" }}>Wallet</Typography>

                <Box sx={{ display: "flex", gap: 2, justifyContent: "space-between" }}>
                    <Typography display="flex" alignItems="center" gap={1} color="success.main">
                        <AttachMoney /> <strong>Demo Balance:</strong> ${user.demoBalance.toFixed(2)}
                    </Typography>
                    <Typography display="flex" alignItems="center" gap={1} color="warning.main">
                        <TrendingUp /> <strong>Portfolio Value:</strong> ${user.portfolioValue.toFixed(2)}
                    </Typography>
                </Box>
                <Button 
                    variant='contained' 
                    sx={{ color: "whitesmoke" }}
                    onClick={handleOpenAddMoney}
                >
                    Add Money
                </Button>
            </Paper>

            <Modal
                open={openAddMoney}
                onClose={handleCloseAddMoney}
                aria-labelledby="add-money-modal"
                aria-describedby="add-money-form"
            >
                <Box sx={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    width: 400,
                    bgcolor: '#121314',
                    border: '2px solid #000',
                    boxShadow: 24,
                    p: 4,
                }}>
                    <AddMoney 
                        currentBalance={user.demoBalance} 
                        onClose={handleCloseAddMoney}
                    />
                </Box>
            </Modal>
        </Box>
    )
}

export default Wallet






// import { Box, Button, Paper, Typography } from '@mui/material'
// import React, { useEffect } from 'react'
// import { useDispatch, useSelector } from 'react-redux';
// import { fetchUserData } from '../Redux/Auth/authSlice';
// import { AttachMoney, TrendingUp } from '@mui/icons-material';

// const Wallet = () => {
//     const dispatch = useDispatch();
//     const { user, error, isAuthenticated } = useSelector((state) => state.auth);
//     useEffect(() => {
//         if (isAuthenticated && !user) {
//             dispatch(fetchUserData());
//         }
//     }, [dispatch, isAuthenticated, user]);
//     if (error) return <Typography color="error" align="center">Error: {error.message || error}</Typography>;
//     if (!user) return <Typography color="textSecondary" align="center">No user data available</Typography>;
//     return (
//         <Box sx={{ width: "100%", height: "100vh", display: "flex", alignItems: "center", justifyContent: "center" }}>
//             <Paper sx={{ height: "35vh", width: "50%", p: 4, display: "flex", flexDirection: "column", gap: 4, bgcolor: "#121314" }}>
//                 <Typography variant='h5' sx={{ fontWeight: "600", textAlign: "center", color: "whitesmoke" }}>Wallet</Typography>

//                 {/* Show current Money */}
//                 <Box sx={{ display: "flex", gap: 2, justifyContent: "space-between" }}>
//                     <Typography display="flex" alignItems="center" gap={1} color="success.main">
//                         <AttachMoney /> <strong>Demo Balance:</strong> ${user.demoBalance.toFixed(2)}
//                     </Typography>
//                     <Typography display="flex" alignItems="center" gap={1} color="warning.main">
//                         <TrendingUp /> <strong>Portfolio Value:</strong> ${user.portfolioValue.toFixed(2)}
//                     </Typography>
//                 </Box>
//                 <Button variant='contained' sx={{ color: "whitesmoke" }}>Add Money</Button>
//             </Paper>
//         </Box>
//     )
// }

// export default Wallet