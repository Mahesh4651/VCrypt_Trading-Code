// Footer.js
import React from 'react';
import { Box, Grid, Typography, Link, IconButton } from '@mui/material';
import FacebookIcon from '@mui/icons-material/Facebook';
import TwitterIcon from '@mui/icons-material/Twitter';
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import GitHubIcon from '@mui/icons-material/GitHub';

const Footer = () => {
  return (
    <Box
      sx={{
        backgroundColor: '#0d0d0d',
        color: '#c9d1d9',
        p: 4,
        mt: 5,
        borderTop: '2px solid #21262d',
      }}
    >
      <Grid container spacing={4} justifyContent="space-between">
        {/* Project Info */}
        <Grid item xs={12} sm={6} md={3}>
          <Typography variant="h6" sx={{ color: '#58a6ff' }}>
            Vcrypt
          </Typography>
          <Typography variant="body2" sx={{ mt: 1 }}>
            Secure your crypto. Trade smart. Stay ahead.
          </Typography>
        </Grid>

        {/* Links */}
        <Grid item xs={12} sm={6} md={3}>
          <Typography variant="h6">Quick Links</Typography>
          <Box sx={{ display: 'flex', flexDirection: 'column', mt: 1 }}>
            <Link href="/" underline="hover" color="inherit">Home</Link>
            <Link href="/dashboard/aboutus" underline="hover" color="inherit">About</Link>
            {/* <Link href="/contact" underline="hover" color="inherit">Contact</Link> */}
            {/* <Link href="/privacy" underline="hover" color="inherit">Privacy Policy</Link> */}
          </Box>
        </Grid>

        {/* Social Media */}
        <Grid item xs={12} sm={6} md={3}>
          <Typography variant="h6">Connect with us</Typography>
          <Box sx={{ mt: 1 }}>
            <IconButton href="https://facebook.com" target="_blank" sx={{ color: '#3b5998' }}>
              <FacebookIcon />
            </IconButton>
            <IconButton href="https://twitter.com" target="_blank" sx={{ color: '#1DA1F2' }}>
              <TwitterIcon />
            </IconButton>
            <IconButton href="https://linkedin.com" target="_blank" sx={{ color: '#0077b5' }}>
              <LinkedInIcon />
            </IconButton>
            <IconButton href="https://github.com" target="_blank" sx={{ color: '#fff' }}>
              <GitHubIcon />
            </IconButton>
          </Box>
        </Grid>

        {/* Bitcoin TradingView Widget */}
        <Grid item xs={12} sm={6} md={3}>
          <Typography variant="h6">BTC Price</Typography>
          <Box sx={{ mt: 1 }}>
            <iframe
              title="BTC TradingView"
              src="https://s.tradingview.com/embed-widget/mini-symbol-overview/?symbol=BINANCE:BTCUSDT&width=100%25&height=150&locale=en&dateRange=1D&colorTheme=dark&trendLineColor=rgba(41,%2098,%20255,%201)&underLineColor=rgba(41,%2098,%20255,%200.3)&isTransparent=true&autosize=true"
              width="100%"
              height="150"
              frameBorder="0"
              allowtransparency="true"
              scrolling="no"
              style={{ borderRadius: 6 }}
            ></iframe>
          </Box>
        </Grid>
      </Grid>

      {/* Footer Bottom */}
      <Box sx={{ textAlign: 'center', mt: 4, fontSize: 13, color: '#8b949e' }}>
        © {new Date().getFullYear()} Vcrypt. All rights reserved.
      </Box>
    </Box>
  );
};

export default Footer;
