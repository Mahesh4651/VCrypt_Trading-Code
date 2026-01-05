import React, { useEffect } from 'react'
import { Outlet, useLocation } from 'react-router-dom';
import Navbar from './Home/Navbar';
import MainDashBoard from './Home/MainDashBoard';
import Footer from './Pages/Footer';

const Vcrypt = () => {
    const location = useLocation();
    useEffect(() => {
        window.scrollTo({ top: 0, behavior: "smooth" })
    }, [])
    return (
        <div>
            <Navbar />
            {location.pathname === '/dashboard' && (
                <>
                    <MainDashBoard />
                </>
            )}
            <Outlet />
            <Footer/>
        </div>
    );
}

export default Vcrypt



// import React from 'react'

// const Vcrypt = () => {
//   return (
//     <div>Vcrypt</div>
//   )
// }

// export default Vcrypt