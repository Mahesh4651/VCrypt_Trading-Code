import React, { Suspense, lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute"; // Assuming this isn't lazy-loaded

// Lazy-loaded components
const SignIn = lazy(() => import("../Pages/SignIn"));
const SignUp = lazy(() => import("../Pages/SignUp"));
const Vcrypt = lazy(() => import("../Vcrypt"));
const UserProfile = lazy(() => import("../Pages/UserProfile"));
const PlaceTrade = lazy(() => import("../Trading/PlaceTrade"));
const ActiveTrade = lazy(() => import("../Trading/ActiveTrade"));
const TradeChart = lazy(() => import("../Pages/TradeChart"));
const Portfolio = lazy(() => import("../Pages/Portfolio"));
const Wallet = lazy(() => import("../Trading/Wallet"));
const Histroy = lazy(() => import("../Pages/Histroy"));
const About = lazy(() => import("../Pages/About"));
const MainDashSideBar = lazy(() => import("../Home/MainDashSideBar"));
import { Spiral } from 'ldrs/react'
import 'ldrs/react/Spiral.css'
import { Box } from "@mui/material";

// Default values shown


// Fallback loader (you can customize this)
const Loader = () => <Box sx={{width:"100%", height:"100vh", display:"flex", alignItems:"center", justifyContent:"center"}}><Spiral size="40" speed="0.9"  color="#01FFC3" /></Box>;

export const routingPage = createBrowserRouter([
  {
    path: "/",
    element: (
      <Suspense fallback={<Loader />}>
        <SignIn />
      </Suspense>
    )
  },
  {
    path: "/signup",
    element: (
      <Suspense fallback={<Loader />}>
        <SignUp />
      </Suspense>
    )
  },
  {
    path: "/dashboard",
    element: (
      <ProtectedRoute>
        <Suspense fallback={<Loader />}>
          <Vcrypt />
        </Suspense>
      </ProtectedRoute>
    ),
    children: [
      {
        path: "/dashboard/userprofile",
        element: (
          <Suspense fallback={<Loader />}>
            <UserProfile />
          </Suspense>
        )
      },
      {
        path: "/dashboard/placetrade",
        element: (
          <Suspense fallback={<Loader />}>
            <PlaceTrade />
          </Suspense>
        )
      },
      {
        path: "/dashboard/activetrade",
        element: (
          <Suspense fallback={<Loader />}>
            <ActiveTrade />
          </Suspense>
        )
      },
      {
        path: "/dashboard/tradechart",
        element: (
          <Suspense fallback={<Loader />}>
            <TradeChart />
          </Suspense>
        )
      },
      {
        path: "/dashboard/portfolio",
        element: (
          <Suspense fallback={<Loader />}>
            <Portfolio />
          </Suspense>
        )
      },
      {
        path: "/dashboard/wallet",
        element: (
          <Suspense fallback={<Loader />}>
            <Wallet />
          </Suspense>
        )
      },
      {
        path: "/dashboard/history",
        element: (
          <Suspense fallback={<Loader />}>
            <Histroy />
          </Suspense>
        )
      },
      {
        path: "/dashboard/aboutus",
        element: (
          <Suspense fallback={<Loader />}>
            <About />
          </Suspense>
        )
      }
    ]
  },
  {
    path: "/sidebar",
    element: (
      <ProtectedRoute>
        <Suspense fallback={<Loader />}>
          <MainDashSideBar />
        </Suspense>
      </ProtectedRoute>
    )
  }
]);



// export const routingPage = createBrowserRouter([
//   {
//     path: "/",
//     element: <SignIn />, // initial route shows SignIn
//   },
//   {
//     path: "/signup",
//     element: <SignUp />
//   },
//   {
//     path: "/dashboard",
//     element: (
//       <ProtectedRoute>
//         <Vcrypt />
//       </ProtectedRoute>
//     ),
//     children: [
//       {
//         path: "/dashboard/userprofile",
//         element: <UserProfile />
//       },
//       {
//         path: "/dashboard/placetrade",
//         element: <PlaceTrade />
//       },
//       {
//         path: "/dashboard/activetrade",
//         element: <ActiveTrade />
//       },
//       {
//         path: "/dashboard/tradechart",
//         element: <TradeChart />
//       },
//       {
//         path: "/dashboard/portfolio",
//         element: <Portfolio />
//       },
//       {
//         path: "/dashboard/wallet",
//         element: <Wallet />
//       },
//       {
//         path: "/dashboard/history",
//         element: <Histroy />
//       },
//       {
//         path: "/dashboard/aboutus",
//         element: <About />
//       }
//     ]
//   },
//   {
//     path: "/sidebar",
//     element: (
//       <ProtectedRoute>
//         <MainDashSideBar />
//       </ProtectedRoute>
//     )
//   }
// ]);
