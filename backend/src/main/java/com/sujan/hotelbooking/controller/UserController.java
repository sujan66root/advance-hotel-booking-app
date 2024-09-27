package com.sujan.hotelbooking.controller;

import com.sujan.hotelbooking.dto.ResponseDto;
import com.sujan.hotelbooking.service.serviceinterface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user-related operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService iUserService;

    // Retrieves all users (admin access only).
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> getAllUsers() {
        ResponseDto response = iUserService.getALlUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //Retrieves a user by their ID
    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<ResponseDto> getUserById(@PathVariable("userId") String userId) {
        ResponseDto response = iUserService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Deletes a user by their ID (admin access only).

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable("userId") String userId) {
        ResponseDto response = iUserService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //Retrieves the profile info of the logged-in user.
    @GetMapping("/get-logged-in-profile-info")
    public ResponseEntity<ResponseDto> getLoggedInUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ResponseDto response = iUserService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //Retrieves booking history for a user by their ID.
    @GetMapping("/get-user-bookings/{userId}")
    public ResponseEntity<ResponseDto> getUserBookingHistory(@PathVariable("userId") String userId) {
        ResponseDto response = iUserService.getUserBookingHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
