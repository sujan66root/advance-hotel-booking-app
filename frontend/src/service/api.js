import axios from "axios";

const BASE_URL = "http://localhost:8000";

const getHeader = () => {
    const token = localStorage.getItem("token");
    return {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
    };
};

/** AUTH **/

// Register a new user
export const registerUser = async (registration) => {
    const response = await axios.post(`${BASE_URL}/auth/register`, registration);
    return response.data;
};

// Login a registered user
export const loginUser = async (loginDetails) => {
    const response = await axios.post(`${BASE_URL}/auth/login`, loginDetails);
    return response.data;
};

/** USERS **/

// Get the list of all users
export const getAllUsers = async () => {
    const response = await axios.get(`${BASE_URL}/users/all`, {
        headers: getHeader(),
    });
    return response.data;
};

// Get the profile of the logged-in user
export const getUserProfile = async () => {
    const response = await axios.get(`${BASE_URL}/users/get-logged-in-profile-info`, {
        headers: getHeader(),
    });
    return response.data;
};

// Get a user by their ID
export const getUser = async (userId) => {
    const response = await axios.get(`${BASE_URL}/users/get-by-id/${userId}`, {
        headers: getHeader(),
    });
    return response.data;
};

// Get user bookings by user ID
export const getUserBookings = async (userId) => {
    const response = await axios.get(`${BASE_URL}/users/get-user-bookings/${userId}`, {
        headers: getHeader(),
    });
    return response.data;
};

// Delete a user by their ID
export const deleteUser = async (userId) => {
    const response = await axios.delete(`${BASE_URL}/users/delete/${userId}`, {
        headers: getHeader(),
    });
    return response.data;
};

/** ROOMS **/

// Add a new room
export const addRoom = async (formData) => {
    const result = await axios.post(`${BASE_URL}/rooms/add`, formData, {
        headers: {
            ...getHeader(),
            "Content-Type": "multipart/form-data",
        },
    });
    return result.data;
};

// Get all available rooms
export const getAllAvailableRooms = async () => {
    const result = await axios.get(`${BASE_URL}/rooms/all-available-rooms`);
    return result.data;
};

// Get available rooms by date and room type
export const getAvailableRoomsByDateAndType = async (checkInDate, checkOutDate, roomType) => {
    const result = await axios.get(
        `${BASE_URL}/rooms/available-rooms-by-date-and-type?checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${roomType}`
    );
    return result.data;
};

// Get all room types
export const getRoomTypes = async () => {
    const response = await axios.get(`${BASE_URL}/rooms/types`);
    return response.data;
};

// Get all rooms
export const getAllRooms = async () => {
    const result = await axios.get(`${BASE_URL}/rooms/all`);
    return result.data;
};

// Get a room by ID
export const getRoomById = async (roomId) => {
    const result = await axios.get(`${BASE_URL}/rooms/room-by-id/${roomId}`);
    return result.data;
};

// Delete a room by ID
export const deleteRoom = async (roomId) => {
    const result = await axios.delete(`${BASE_URL}/rooms/delete/${roomId}`, {
        headers: getHeader(),
    });
    return result.data;
};

// Update a room
export const updateRoom = async (roomId, formData) => {
    const result = await axios.put(`${BASE_URL}/rooms/update/${roomId}`, formData, {
        headers: {
            ...getHeader(),
            "Content-Type": "multipart/form-data",
        },
    });
    return result.data;
};

/** BOOKINGS **/

// Book a room
export const bookRoom = async (roomId, userId, booking) => {
    try {
        const response = await axios.post(
            `${BASE_URL}/bookings/book-room/${roomId}/${userId}`,
            booking,
            { headers: getHeader() }
        );
        return response.data;
    } catch (error) {
        console.error("Error booking room:", error.response?.data || error.message);
        throw error; // Rethrow if necessary
    }
};

// Get all bookings
export const getAllBookings = async () => {
    const result = await axios.get(`${BASE_URL}/bookings/all`, {
        headers: getHeader(),
    });
    return result.data;
};

// Get booking by confirmation code
export const getBookingByConfirmationCode = async (bookingCode) => {
    const result = await axios.get(`${BASE_URL}/bookings/get-by-confirmation-code/${bookingCode}`);
    return result.data;
};

// Cancel a booking
export const cancelBooking = async (bookingId) => {
    const result = await axios.delete(`${BASE_URL}/bookings/cancel/${bookingId}`, {
        headers: getHeader(),
    });
    return result.data;
};

/** AUTHENTICATION CHECKER **/

export const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
};

export const isAuthenticated = () => {
    const token = localStorage.getItem("token");
    return !!token;
};

export const isAdmin = () => {
    const role = localStorage.getItem("role");
    return role === "ADMIN";
};

export const isUser = () => {
    const role = localStorage.getItem("role");
    return role === "USER";
};

