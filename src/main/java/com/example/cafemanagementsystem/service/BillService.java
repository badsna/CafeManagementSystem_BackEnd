package com.example.cafemanagementsystem.service;

import com.example.cafemanagementsystem.model.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {
    ResponseEntity<String> generateReport(Map<String, Object> billRequestDto);

    ResponseEntity<List<Bill>> getBills();

    ResponseEntity<byte[]> getPdf(Map<String, Object> pdfRequestDto);

    ResponseEntity<String> deleteBill(Integer id);
}
