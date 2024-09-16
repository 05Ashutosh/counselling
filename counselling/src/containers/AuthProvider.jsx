import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [authState, setAuthState] = useState({
    isAuthenticated: false,
    user: null,
    email: null, // Store email from signup here
  });

  const login = (user) => setAuthState({ ...authState, isAuthenticated: true, user });
  
  const logout = () => setAuthState({ isAuthenticated: false, user: null, email: null });
  
  const setEmail = (email) => setAuthState({ ...authState, email });

  return (
    <AuthContext.Provider value={{ authState, login, logout, setEmail }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
