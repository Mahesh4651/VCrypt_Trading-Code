import { Box, Button, TextField, Typography } from '@mui/material'
import React, { useState } from 'react'
import axios from 'axios'
import { useDispatch } from 'react-redux'
import { fetchUserData } from '../Redux/Auth/authSlice'

const AddMoney = ({ currentBalance, onClose }) => {
    const [amount, setAmount] = useState('')
    const [error, setError] = useState('')
    const dispatch = useDispatch()
    const MAX_AMOUNT = 100000

    const validateInput = (value) => {
        if (!value || isNaN(value) || value.includes('e')) {
            return 'Please enter a valid number'
        }
        
        const numValue = Number(value)
        if (numValue <= 0) {
            return 'Amount must be greater than 0'
        }
        
        if (numValue > MAX_AMOUNT) {
            return `Maximum amount allowed is $${MAX_AMOUNT}`
        }
        
        return ''
    }

    const handleAmountChange = (e) => {
        const value = e.target.value
        setAmount(value)
        setError(validateInput(value))
    }

    const handleAddMoney = async () => {
        const validationError = validateInput(amount)
        if (validationError) {
            setError(validationError)
            return
        }

        try {
            const token = localStorage.getItem('token')
            const response = await axios.post(
                `${import.meta.env.VITE_API_URL}/api/trades/add-funds`,
                { amount: Number(amount) },
                {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                }
            )

            if (response.status === 200) {
                dispatch(fetchUserData())
                onClose()
            }
        } catch (err) {
            setError(err?.response?.data?.message || 'Failed to add funds')
        }
    }

    return (
        <Box sx={{ 
            display: 'flex', 
            flexDirection: 'column', 
            gap: 3,
            color: 'whitesmoke'
        }}>
            <Typography variant="h6" sx={{ fontWeight: '600', textAlign: 'center' }}>
                Add Demo Money
            </Typography>

            <Typography>
                Current Demo Balance: ${currentBalance.toFixed(2)}
            </Typography>

            <TextField
                label="Amount to Add"
                variant="outlined"
                type="number"
                value={amount}
                onChange={handleAmountChange}
                error={!!error}
                helperText={error}
                inputProps={{ 
                    min: 0,
                    step: '0.01',
                    style: { color: 'whitesmoke' }
                }}
                sx={{
                    '& .MuiOutlinedInput-root': {
                        '& fieldset': { borderColor: 'gray' },
                        '&:hover fieldset': { borderColor: 'white' },
                        '&.Mui-focused fieldset': { borderColor: 'white' },
                    },
                    '& .MuiInputLabel-root': { color: 'gray' },
                    '& .MuiInputLabel-root.Mui-focused': { color: 'white' },
                }}
            />

            <Box sx={{ display: 'flex', gap: 2, justifyContent: 'space-between' }}>
                <Button
                    variant="contained"
                    onClick={handleAddMoney}
                    disabled={!!error || !amount}
                    sx={{
                        bgcolor: "green",
                        color: "white",
                        fontWeight: "bold",
                        borderRadius: "8px",
                        padding: "10px 20px",
                        "&:hover": {
                          bgcolor: "#00cc00",
                        },
                        "&.Mui-disabled": {
                          bgcolor: "rgba(0, 255, 0, 0.3)",
                          color: "whitesmoke",
                        },
                    }}
                >
                    Add Money
                </Button>
                <Button
                    variant="outlined"
                    onClick={onClose}
                    sx={{ color: 'whitesmoke', borderColor: 'gray', '&:hover': { borderColor: 'white' } }}
                >
                    Cancel
                </Button>
            </Box>
        </Box>
    )
}

export default AddMoney
