package com.sujan.hotelbooking.controller;

import com.sujan.hotelbooking.dto.ResponseDto;
import com.sujan.hotelbooking.entity.Booking;
import com.sujan.hotelbooking.service.serviceinterface.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing booking-related operations.
 */
@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    // Saves a new booking for a room by a user
    @PostMapping("/book-room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDto> saveBookings(@PathVariable Long roomId,
                                                    @PathVariable Long userId,
                                                    @RequestBody Booking bookingRequest) {
        ResponseDto response = bookingService.saveBooking(bookingRequest, roomId, userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Retrieves all bookings (admin access only)
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> getAllBookings() {
        ResponseDto response = bookingService.getAllBooking();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Retrieves a booking by its confirmation code
    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<ResponseDto> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        ResponseDto response = bookingService.findBookingByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Cancels a booking by its ID
    @DeleteMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<ResponseDto> cancelBooking(@PathVariable Long bookingId) {
        ResponseDto response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
