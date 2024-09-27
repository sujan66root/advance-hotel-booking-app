package com.sujan.hotelbooking.service.serviceimplementation;

import com.sujan.hotelbooking.dto.LoginReqDto;
import com.sujan.hotelbooking.dto.ResponseDto;
import com.sujan.hotelbooking.dto.UserDto;
import com.sujan.hotelbooking.entity.Users;
import com.sujan.hotelbooking.repository.UserRepository;
import com.sujan.hotelbooking.service.serviceinterface.IUserService;
import com.sujan.hotelbooking.utils.JWTUtils;
import com.sujan.hotelbooking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public ResponseDto register(Users user) {
        ResponseDto responseObj = new ResponseDto();
        try {
            // Set default role if none provided
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            // Check if the email already exists in the database
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new Exception(user.getEmail() + " Already Exists");
            }
            // Encrypt user password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // Save user entity to the database
            Users savedUser = userRepository.save(user);
            // Map saved user entity to UserDto for response
            UserDto userDto = Utils.mapUsersEntityToUserDTO(savedUser);
            responseObj.setStatusCode(200);
            responseObj.setUser(userDto);
        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage("Error Occurred During USer Registration " + e.getMessage());
        }
        return responseObj;
    }

    @Override
    public ResponseDto login(LoginReqDto loginReqDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword()));
            Optional<Users> usersOptional = userRepository.findByEmail(loginReqDto.getEmail());
            // Check if the user exists, if not, throw an exception
            if (!usersOptional.isPresent()) {
                responseDto.setStatusCode(404);
                responseDto.setMessage("User not found");
                return responseDto;
            }
            // Generate JWT token for the authenticated user
            String token = jwtUtils.generateToken(loginReqDto.getEmail());
//            send the response to the client
            responseDto.setToken(token);
            responseDto.setStatusCode(200);
            responseDto.setMessage("Successful");
            responseDto.setExpirationTime("7 Days");
            responseDto.setRole(usersOptional.get().getRole());
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setMessage(e.getMessage());
        }
        return responseDto;
    }

    @Override
    public ResponseDto getALlUsers() {
        ResponseDto responseDto = new ResponseDto();
        try {
            List<Users> result = userRepository.findAll();
            // Map user entities to DTOs for response
            responseDto.setUserList(Utils.mapUserListEntityToUserListDTO(result));
            responseDto.setStatusCode(200);
            responseDto.setMessage("List Fetched Successfully");
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setMessage(e.getMessage());
        }
        return responseDto;
    }

    @Override
    public ResponseDto getUserBookingHistory(String id) {
        ResponseDto responseObj = new ResponseDto();
        try {
            Users user = userRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new RuntimeException("User Not Found"));
            // Map user entity to DTO, including booking and room details
            responseObj.setUser(Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user));
            responseObj.setStatusCode(200);
            responseObj.setMessage("successful");
        } catch (Exception e) {
            responseObj.setStatusCode(500);
            responseObj.setMessage("Error getting all users " + e.getMessage());
        }
        return responseObj;
    }

    @Override
    public ResponseDto deleteUser(String id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            // Verify if the user exists before deletion
            userRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new RuntimeException("User Not Found"));
            // Delete user by ID
            userRepository.deleteById(Long.valueOf(id));
            responseDto.setStatusCode(200);
            responseDto.setMessage("successful");
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }

    @Override
    public ResponseDto getUserById(String id) {
        ResponseDto responseDto = new ResponseDto();
        try {
            Users user = userRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new RuntimeException("User Not Found"));
            // Map user entity to DTO for response
            responseDto.setUser(Utils.mapUsersEntityToUserDTO(user));
            responseDto.setStatusCode(200);
            responseDto.setMessage("successful");
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }

    @Override
    public ResponseDto getMyInfo(String email) {
        ResponseDto responseDto = new ResponseDto();
        try {
            // Fetch user by email and handle if not found
            Users user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Email Not Found"));
            // Map user entity to DTO for response
            responseDto.setUser(Utils.mapUsersEntityToUserDTO(user));
            responseDto.setStatusCode(200);
            responseDto.setMessage("successful");
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatusCode(500);
        }
        return responseDto;
    }

}
