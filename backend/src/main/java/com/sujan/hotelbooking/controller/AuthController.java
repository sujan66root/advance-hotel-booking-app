package com.sujan.hotelbooking.controller;

import com.sujan.hotelbooking.dto.LoginReqDto;
import com.sujan.hotelbooking.dto.ResponseDto;
import com.sujan.hotelbooking.entity.Users;
import com.sujan.hotelbooking.service.serviceinterface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody Users users) {
        ResponseDto response = iUserService.register(users);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginReqDto loginRequest) {
        ResponseDto response = iUserService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
