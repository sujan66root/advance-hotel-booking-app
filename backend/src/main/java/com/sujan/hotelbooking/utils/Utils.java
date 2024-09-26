package com.sujan.hotelbooking.utils;

import com.sujan.hotelbooking.dto.BookingDto;
import com.sujan.hotelbooking.dto.RoomDto;
import com.sujan.hotelbooking.dto.UserDto;
import com.sujan.hotelbooking.entity.Booking;
import com.sujan.hotelbooking.entity.Room;
import com.sujan.hotelbooking.entity.Users;

import java.util.List;
import java.util.stream.Collectors;

// for sending response to client
public class Utils {

    // Converts a Users entity to a UserDto
    public static UserDto mapUsersEntityToUserDTO(Users user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    // Converts a Room entity to a RoomDto
    public static RoomDto mapRoomEntityToRoomDTO(Room room) {
        RoomDto roomDTO = new RoomDto();

        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());
        return roomDTO;
    }

    // Converts a Booking entity to a BookingDto
    public static BookingDto mapBookingEntityToBookingDTO(Booking booking) {
        BookingDto bookingDTO = new BookingDto();
        // Map simple fields from Booking to BookingDto
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDTO;                             // Return the mapped BookingDto
    }

    // Converts a Room entity to a RoomDto, including its bookings
    public static RoomDto mapRoomEntityToRoomDTOPlusBookings(Room room) {
        RoomDto roomDTO = new RoomDto();

        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());

        // If the room has bookings, map them to BookingDto
        if (room.getBookings() != null) {
            roomDTO.setBookings(room.getBookings().stream()
                    .map(Utils::mapBookingEntityToBookingDTO) // Map each Booking to BookingDto
                    .collect(Collectors.toList()));            // Collect and set the list of BookingDto
        }
        return roomDTO;                                // Return the mapped RoomDto with bookings
    }

    // Converts a Booking entity to a BookingDto with booked room details
    public static BookingDto mapBookingEntityToBookingDTOPlusBookedRooms(Booking booking, boolean mapUser) {
        BookingDto bookingDTO = new BookingDto();
        // Map simple fields from Booking to BookingDto
        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        // If requested, map the associated User entity to UserDto
        if (mapUser) {
            bookingDTO.setUser(Utils.mapUsersEntityToUserDTO(booking.getUsers()));
        }

        // If the Booking has a Room associated, map it to RoomDto
        if (booking.getRoom() != null) {
            RoomDto roomDTO = new RoomDto();

            roomDTO.setId(booking.getRoom().getId());
            roomDTO.setRoomType(booking.getRoom().getRoomType());
            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDTO.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
            roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());
            bookingDTO.setRoom(roomDTO);                  // Set the RoomDto in BookingDto
        }
        return bookingDTO;                              // Return the mapped BookingDto with room details
    }

    // Converts a Users entity to a UserDto with bookings and their rooms
    public static UserDto mapUserEntityToUserDTOPlusUserBookingsAndRoom(Users user) {
        UserDto userDTO = new UserDto();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        // If the user has bookings, map each Booking to BookingDto with room details
        if (!user.getBookings().isEmpty()) {
            userDTO.setBookings(user.getBookings().stream()
                    .map(booking -> mapBookingEntityToBookingDTOPlusBookedRooms(booking, false)) // Map each Booking
                    .collect(Collectors.toList()));            // Collect and set the list of BookingDto
        }
        return userDTO;                                 // Return the mapped UserDto with bookings
    }

    // Converts a list of Users entities to a list of UserDto
    public static List<UserDto> mapUserListEntityToUserListDTO(List<Users> userList) {
        return userList.stream()
                .map(Utils::mapUsersEntityToUserDTO)     // Map each Users to UserDto
                .collect(Collectors.toList());            // Collect and return the list of UserDto
    }

    // Converts a list of Room entities to a list of RoomDto
    public static List<RoomDto> mapRoomListEntityToRoomListDTO(List<Room> roomList) {
        return roomList.stream()
                .map(Utils::mapRoomEntityToRoomDTO)      // Map each Room to RoomDto
                .collect(Collectors.toList());            // Collect and return the list of RoomDto
    }

    // Converts a list of Booking entities to a list of BookingDto
    public static List<BookingDto> mapBookingListEntityToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream()
                .map(Utils::mapBookingEntityToBookingDTO) // Map each Booking to BookingDto
                .collect(Collectors.toList());            // Collect and return the list of BookingDto
    }
}
