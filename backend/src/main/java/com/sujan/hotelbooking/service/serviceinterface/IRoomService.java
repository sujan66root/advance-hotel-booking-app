package com.sujan.hotelbooking.service.serviceinterface;

import com.sujan.hotelbooking.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    ResponseDto addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();

    ResponseDto getAllRooms();

    ResponseDto deleteRoom(Long roomId);

    ResponseDto updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo);

    ResponseDto getRoomById(Long roomId);

    ResponseDto getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    ResponseDto getAllAvailableRooms();
}
