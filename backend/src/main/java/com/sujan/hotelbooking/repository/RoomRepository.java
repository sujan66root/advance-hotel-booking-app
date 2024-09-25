package com.sujan.hotelbooking.repository;

import com.sujan.hotelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // Method to retrieve distinct room types from the database
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    // Method to find available rooms based on check-in and check-out dates, and a specific room type
    @Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% " +
            "AND r.id NOT IN (SELECT bk.room.id FROM Booking bk WHERE" +
            "(bk.checkInDate <= :checkOutDate) AND (bk.checkOutDate >= :checkInDate))")
    List<Room> findAvailableRoomsByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    // Method to get all rooms that are currently available (not booked)
    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
    List<Room> getAllAvailableRooms();
}
