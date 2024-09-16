import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../utils/api';
import useAuth from '../hooks/useAuth';

const OtpForm = () => {
  const [otp, setOtp] = useState('');
  const [error, setError] = useState(null);
  const { authState } = useAuth(); // Access the email from context
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const payload = {
      email: authState.email,  // Use the email from the context
      verificationCode: otp,   // OTP entered by the user
    };

    try {
      const response = await api.verifyOtp(payload);
      if (response.ok) {
        // On successful OTP verification, navigate to the login page
        navigate('/login');
      } else {
        setError('Invalid OTP. Please try again.');
      }
    } catch (err) {
      setError('An error occurred. Please try again.');
    }
  };

  return (
    <div className="flex justify-center">
      <form className="w-full max-w-sm" onSubmit={handleSubmit}>
        <label className="block text-gray-700 text-sm font-bold mb-2">OTP</label>
        <input
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          type="number"
          placeholder="OTP"
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
        />
        <button
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit"
        >
          Verify
        </button>
        {error && <p className="text-red-500 text-xs italic mt-2">{error}</p>}
      </form>
    </div>
  );
};

export default OtpForm;
