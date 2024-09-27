package com.sujan.hotelbooking.service.serviceinterface;

import com.sujan.hotelbooking.dto.LoginReqDto;
import com.sujan.hotelbooking.dto.ResponseDto;
import com.sujan.hotelbooking.dto.UserDto;
import com.sujan.hotelbooking.entity.Users;

public interface IUserService {
    /*Register
       Login
       getAllUser // admin
       deleteUser  // admin
       getBookingDetails //user //admin
       getUserById // user
       getProfile //
       */
    ResponseDto register(Users user);

    ResponseDto login(LoginReqDto loginReqDto);

    ResponseDto getALlUsers();

    ResponseDto getUserBookingHistory(String id);

    ResponseDto deleteUser(String id);

    ResponseDto getUserById(String id);

    ResponseDto getMyInfo(String email);
}
