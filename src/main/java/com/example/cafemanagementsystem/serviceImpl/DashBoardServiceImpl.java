package com.example.cafemanagementsystem.serviceImpl;

import com.example.cafemanagementsystem.repo.BillRepo;
import com.example.cafemanagementsystem.repo.CategoryRepo;
import com.example.cafemanagementsystem.repo.ProductRepo;
import com.example.cafemanagementsystem.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final CategoryRepo categoryRepo;
    private final BillRepo billRepo;
    private final ProductRepo productRepo;
    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String ,Object> map=new HashMap<>();
        map.put("category",categoryRepo.count());
        map.put("product",productRepo.count());
        map.put("bill",billRepo.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
