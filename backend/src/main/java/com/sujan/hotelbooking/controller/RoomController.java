package com.sujan.hotelbooking.controller;

import com.sujan.hotelbooking.dto.ResponseDto;
import com.sujan.hotelbooking.service.serviceinterface.IBookingService;
import com.sujan.hotelbooking.service.serviceinterface.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IBookingService iBookingService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> addNewRoom(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "roomType", required = false) String roomType,
            @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
            @RequestParam(value = "roomDescription", required = false) String roomDescription
    ) {
        // Check for required fields and handle invalid input
        if (photo == null || photo.isEmpty() || roomType == null || roomType.isBlank() || roomPrice == null || roomType.isBlank()) {
            ResponseDto response = new ResponseDto();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(photo, roomType,roomPrice)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        // Call service method to add the new room
        ResponseDto response = roomService.addNewRoom(photo, roomType, roomPrice, roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllRooms() {
        // Retrieve all rooms through the service
        ResponseDto response = roomService.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getRoomTypes() {
        // Fetch list of room types from service
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<ResponseDto> getRoomById(@PathVariable Long roomId) {
        // Get room details by ID
        ResponseDto response = roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all-available-rooms")
    public ResponseEntity<ResponseDto> getAvailableRooms() {
        // Retrieve all available rooms
        ResponseDto response = roomService.getAllAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<ResponseDto> getAvailableRoomsByDateAndType(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(required = false) String roomType
    ) {
        // Validate check-in and check-out dates along with room type
        if (checkInDate == null || roomType == null || roomType.isBlank() || checkOutDate == null) {
            ResponseDto response = new ResponseDto();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(checkInDate, roomType,checkOutDate)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        // Get available rooms based on date and type criteria
        ResponseDto response = roomService.getAvailableRoomsByDataAndType(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> updateRoom(@PathVariable Long roomId,
                                                  @RequestParam(value = "photo", required = false) MultipartFile photo,
                                                  @RequestParam(value = "roomType", required = false) String roomType,
                                                  @RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
                                                  @RequestParam(value = "roomDescription", required = false) String roomDescription
    ) {
        // Call service to update room details
        ResponseDto response = roomService.updateRoom(roomId, roomDescription, roomType, roomPrice, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> deleteRoom(@PathVariable Long roomId) {
        // Delete room by ID
        ResponseDto response = roomService.deleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
