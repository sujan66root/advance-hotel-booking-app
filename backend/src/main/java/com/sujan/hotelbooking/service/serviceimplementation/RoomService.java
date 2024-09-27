package com.sujan.hotelbooking.service.serviceimplementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sujan.hotelbooking.dto.ResponseDto;
import com.sujan.hotelbooking.dto.RoomDto;
import com.sujan.hotelbooking.entity.Room;
import com.sujan.hotelbooking.repository.RoomRepository;
import com.sujan.hotelbooking.service.serviceinterface.IRoomService;
import com.sujan.hotelbooking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ResponseDto addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        ResponseDto responseObj = new ResponseDto();
        try {
//            TODO: handle the photo upload later
            // Upload image to Cloudinary
            Map<String, Object> uploadResult =
                    cloudinary.uploader().upload(photo.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            // Extract the URL of the uploaded image
            String imageURL = (String) uploadResult.get("url");
            System.out.println(imageURL);
            Room room = new Room();
            room.setRoomDescription(description);
            room.setRoomPrice(roomPrice);
            room.setRoomType(roomType);
            room.setRoomPhotoUrl(imageURL);
            Room savedRoom = roomRepository.save(room);
            responseObj.setRoom(Utils.mapRoomEntityToRoomDTO(savedRoom));
            responseObj.setStatusCode(200);
            responseObj.setMessage("Success");
        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage(e.getMessage());
        }
        return responseObj;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public ResponseDto getAllRooms() {
        ResponseDto responseObj = new ResponseDto();
        try {
            List<Room> allRooms = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            responseObj.setRoomList(Utils.mapRoomListEntityToRoomListDTO(allRooms));
            responseObj.setStatusCode(200);
            responseObj.setMessage("Success");
        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage(e.getMessage());
        }
        return responseObj;
    }

    @Override
    public ResponseDto deleteRoom(Long roomId) {
        ResponseDto responseObj = new ResponseDto();
        try {
            roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room Not Found"));
            roomRepository.deleteById(roomId);
            responseObj.setStatusCode(200);
            responseObj.setMessage("Success");
        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage(e.getMessage());
        }
        return responseObj;
    }

    @Override
    public ResponseDto updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile newPhoto) {
        ResponseDto responseObj = new ResponseDto();
        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room Not Found"));
            String newImageUrl = null;
            // If a new photo is uploaded, upload it to Cloudinary and replace the old photo URL
            if (newPhoto != null && !newPhoto.isEmpty()) {
                // Upload new image to Cloudinary
                Map<String, Object> uploadResult =
                        cloudinary.uploader().upload(newPhoto.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                // Extract the new URL of the uploaded image
                newImageUrl = (String) uploadResult.get("url");
                // Set the new image URL to the room
                if (newImageUrl != null) room.setRoomPhotoUrl(newImageUrl);
            }
            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (description != null) room.setRoomDescription(description);
            Room updatedRoom = roomRepository.save(room);
            responseObj.setRoom(Utils.mapRoomEntityToRoomDTO(updatedRoom));
            responseObj.setStatusCode(200);
            responseObj.setMessage("Success");
        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage(e.getMessage());
        }
        return responseObj;
    }

    @Override
    public ResponseDto getRoomById(Long roomId) {
        ResponseDto responseObj = new ResponseDto();
        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room Not Found"));
            responseObj.setRoom(Utils.mapRoomEntityToRoomDTO(room));
            responseObj.setStatusCode(200);
            responseObj.setMessage("Success");
        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage(e.getMessage());
        }
        return responseObj;
    }

    @Override
    public ResponseDto getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        ResponseDto responseObj = new ResponseDto();

        try {
            List<Room> availableRooms = roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);
            responseObj.setRoomList(Utils.mapRoomListEntityToRoomListDTO(availableRooms));
            responseObj.setStatusCode(200);
            responseObj.setMessage("successful");

        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage(e.getMessage());
        }
        return responseObj;
    }

    @Override
    public ResponseDto getAllAvailableRooms() {
        ResponseDto responseObj = new ResponseDto();

        try {
            List<Room> allAvailableRooms = roomRepository.getAllAvailableRooms();
            responseObj.setRoomList(Utils.mapRoomListEntityToRoomListDTO(allAvailableRooms));
            responseObj.setStatusCode(200);
            responseObj.setMessage("successful");

        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage(e.getMessage());
        }
        return responseObj;
    }
}
