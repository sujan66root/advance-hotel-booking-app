package com.sujan.hotelbooking.service.serviceimplementation;

import com.sujan.hotelbooking.dto.BookingDto;
import com.sujan.hotelbooking.dto.ResponseDto;
import com.sujan.hotelbooking.entity.Booking;
import com.sujan.hotelbooking.entity.Room;
import com.sujan.hotelbooking.entity.Users;
import com.sujan.hotelbooking.repository.BookingRepository;
import com.sujan.hotelbooking.repository.RoomRepository;
import com.sujan.hotelbooking.repository.UserRepository;
import com.sujan.hotelbooking.service.serviceinterface.IBookingService;
import com.sujan.hotelbooking.utils.ConfirmationCode;
import com.sujan.hotelbooking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override

    /**
     * This method saves a new booking for a user and room.
     * - Checks if the room is available and if the dates are valid.
     * - Generates a unique confirmation code for the booking.
     */

    public ResponseDto saveBooking(Booking booking, Long userId, Long roomId) {
        ResponseDto responseObj = new ResponseDto();
        try {
            if (booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
                throw new IllegalArgumentException("Check in date must come after check out date");
            }
            Optional<Users> usersOptional = userRepository.findById(userId);
            Optional<Room> roomOptional = roomRepository.findById(roomId);

            List<Booking> existingBookings = roomOptional.get().getBookings();

            if (!roomIsAvailable(booking, existingBookings)) {
                throw new RuntimeException("Room not Available for selected date range");
            }

            booking.setRoom(roomOptional.get());
            booking.setUsers(usersOptional.get());
            String bookingConfirmationCode = ConfirmationCode.generateRandomConfirmationCode(10);
            booking.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(booking);
            responseObj.setStatusCode(200);
            responseObj.setMessage("successful");
            responseObj.setBookingConfirmationCode(bookingConfirmationCode);
        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage("Error Saving a booking: " + e.getMessage());
        }
        return responseObj;
    }

//  This method retrieves all bookings in descending order of their IDs.

    @Override
    public ResponseDto getAllBooking() {
        ResponseDto responseObj = new ResponseDto();
        try {
            // Fetch all bookings sorted by ID in descending order
            List<Booking> bookings = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            // Convert the list of Booking entities into DTOs
            responseObj.setBookingList(Utils.mapBookingListEntityToBookingListDTO(bookings));
            responseObj.setStatusCode(200);
            responseObj.setMessage("Success");
        } catch (Exception e) {
            responseObj.setMessage(e.getMessage());
            responseObj.setStatusCode(500);
        }
        return responseObj;
    }

    //    This method cancels a booking by its ID.
    @Override
    public ResponseDto cancelBooking(Long id) {
        ResponseDto responseObj = new ResponseDto();

        try {
            bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking Does Not Exist"));
            bookingRepository.deleteById(id);
            responseObj.setStatusCode(200);
            responseObj.setMessage("successful");

        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage("Error Cancelling a booking: " + e.getMessage());
        }
        return responseObj;
    }

    //    This method finds a booking by its confirmation code.
    @Override
    public ResponseDto findBookingByConfirmationCode(String confirmationCode) {
        ResponseDto responseObj = new ResponseDto();
        try {
            Optional<Booking> bookings = bookingRepository.findByBookingConfirmationCode(confirmationCode);
            // Map the booking entity to a DTO, including room details
            responseObj.setBooking(Utils.mapBookingEntityToBookingDTOPlusBookedRooms(bookings.get(), true));
            responseObj.setStatusCode(200);
            responseObj.setMessage("successful");
        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage(e.getMessage());
        }
        return responseObj;
    }


    //    This method checks if a room is available for the selected date range.
    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {

        // Check for date conflicts with existing bookings
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        // Condition 1: Check-in date is the same as another booking
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                // Condition 2: Check-out date is before another booking's check-out date
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                // Condition 3: Check-in date is between another booking's date range
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                // Condition 4: Entire booking range overlaps another booking's range
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
                );
    }
}
