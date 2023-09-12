package com.example.cafemanagementsystem.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ResetTokenService {
    public void deleteByToken(String token);
    ResponseEntity<String> forgotPassword(Map<String, String> userRequestDto);

    ResponseEntity<String> validateResetToken(String token);

    ResponseEntity<String> updatePassword(Map<String, String> request);
}
