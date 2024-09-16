import React from 'react';

const Dashboard = () => {
  return (
    <div className="dashboard-container flex flex-col h-screen">
      <nav className="dashboard-nav bg-gray-800 text-white p-4 flex justify-between h-20vh w-80vw mx-aut">
        <div className="nav-left flex items-center">
          <img
            src="https://example.com/profile-icon.png"
            alt="Profile Icon"
            className="w-8 h-8 rounded-full mr-4"
          />
          <span className="nav-text font-bold">Student Name</span>
        </div>
        <div className="nav-right flex items-center">
          <button className="btn bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
            Enquire
          </button>
          <button className="btn bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded ml-4">
            Messages
          </button>
        </div>
      </nav>
      <main className="dashboard-content flex-1 p-4 overflow-y-auto w-80vw mx-auto">
        {/* */}
      </main>
    </div>
  );
};

export default Dashboard;