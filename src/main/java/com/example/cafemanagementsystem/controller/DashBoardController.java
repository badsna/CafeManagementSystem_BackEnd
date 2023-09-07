package com.example.cafemanagementsystem.controller;

import com.example.cafemanagementsystem.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor

public class DashBoardController {
    private final DashBoardService dashBoardService;

    @GetMapping("/details")
    ResponseEntity<Map<String, Object>> getCount() {
        return dashBoardService.getCount();
    }
}
