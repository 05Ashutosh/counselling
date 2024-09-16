import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignupForm from './components/SignupForm';
import LoginForm from './components/LoginForm';
import OtpForm from './components/OtpForm';
import './style/global.css'
import Dashboard from './components/Dashboard';


const App = () => {
  return (
    <Router>
      <div className="flex justify-center h-screen">
        <Routes>
          <Route path="/signup" element={<SignupForm />} />
          <Route path="/otp" element={<OtpForm />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path='/dashboard' element={<Dashboard/>}/>
          <Route path="/" element={<SignupForm />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
