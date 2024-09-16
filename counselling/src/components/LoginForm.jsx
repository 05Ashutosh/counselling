import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../utils/api';

const LoginForm = () => {
  const [credentials, setCredentials] = useState({ email: '', password: '' });
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      const response = await api.login(credentials);
      const data = await response.json();
      
      if (response.ok) {
        // Store token in localStorage or other secure storage
        localStorage.setItem('token', data.token);
        // Optionally, navigate to a protected route (e.g., dashboard)
        navigate('/dashboard');
      } else {
        setError('Login failed. Try again.');
      }
    } catch (err) {
      setError('An error occurred. Please try again.');
    }
  };

  return (
    <div className="flex justify-center">
      <form className="w-full max-w-sm" onSubmit={handleSubmit}>
        <label className="block text-gray-700 text-sm font-bold mb-2">Email</label>
        <input
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          type="email"
          placeholder="Email"
          value={credentials.email}
          onChange={(e) => setCredentials({ ...credentials, email: e.target.value })}
        />
        <label className="block text-gray-700 text-sm font-bold mb-2">Password</label>
        <input
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          type="password"
          placeholder="Password"
          value={credentials.password}
          onChange={(e) => setCredentials({ ...credentials, password: e.target.value })}
        />
        <button
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit"
        >
          Login
        </button>
        {error && <p className="text-red-500 text-xs italic mt-2">{error}</p>}
      </form>
    </div>
  );
};

export default LoginForm;
