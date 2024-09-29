// src/App.js
import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './Component/commons/Navbar';
import { Footer } from './Component/commons/Footer';
import HomePage from './Component/pages/Homepage';
import Login from './Component/pages/Login';
import Register from './Component/pages/Register';
import AllRoomsPage from './Component/pages/booking_rooms/AllRoomsPage';
import RoomDetailsBookingPage from './Component/pages/booking_rooms/RoomDetailsPage';
import FindBookingPage from './Component/pages/booking_rooms/FindBookingPage';
import AdminPage from './Component/pages/admin/AdminPage';
import ManageRoomPage from './Component/pages/admin/ManageRoomPage';
import EditRoomPage from './Component/pages/admin/EditRoomPage';
import AddRoomPage from './Component/pages/admin/AddRoomPage';
import ManageBookingsPage from './Component/pages/admin/ManageBookingsPage';
import EditBookingPage from './Component/pages/admin/EditBookingPage';
import Profile from './Component/pages/Profile';
import EditProfile from './Component/pages/EditProfile';
import { ProtectedRoute, AdminRoute } from './Component/commons/RouteGuard';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            <Route exact path="/home" element={<HomePage />} />
            <Route exact path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            <Route path="/rooms" element={<AllRoomsPage />} />
            <Route path="/find-booking" element={<FindBookingPage />} />

            <Route path="/room-details-book/:roomId"
              element={<ProtectedRoute element={<RoomDetailsBookingPage />} />}
            />
            <Route path="/profile"
              element={<ProtectedRoute element={<Profile />} />}
            />
            <Route path="/edit-profile"
              element={<ProtectedRoute element={<EditProfile />} />}
            />

            <Route path="/admin"
              element={<AdminRoute element={<AdminPage />} />}
            />
            <Route path="/admin/manage-rooms"
              element={<AdminRoute element={<ManageRoomPage />} />}
            />
            <Route path="/admin/edit-room/:roomId"
              element={<AdminRoute element={<EditRoomPage />} />}
            />
            <Route path="/admin/add-room"
              element={<AdminRoute element={<AddRoomPage />} />}
            />
            <Route path="/admin/manage-bookings"
              element={<AdminRoute element={<ManageBookingsPage />} />}
            />
            <Route path="/admin/edit-booking/:bookingCode"
              element={<AdminRoute element={<EditBookingPage />} />}
            />

            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;
