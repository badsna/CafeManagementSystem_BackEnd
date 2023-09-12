package com.example.cafemanagementsystem.service;

import com.example.cafemanagementsystem.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> signup(Map<String, String> userRequestDto);

    ResponseEntity<String> login(Map<String, String> userRequestDto);

    ResponseEntity<List<UserWrapper>> getAllUser();

    ResponseEntity<String> updateUser(Map<String, String> userRequestDto);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(Map<String, String> userRequestDto);



    ResponseEntity<UserWrapper> getUserDetails();
}
