// src/Auth/ProtectedRoute.js
import React from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }) => {
  const isAuthenticated = localStorage.getItem("token"); // or use context/auth state

  return isAuthenticated ? children : <Navigate to="/signin" replace />;
};

export default ProtectedRoute;
