package com.example.cafemanagementsystem.controller;

import com.example.cafemanagementsystem.constants.CafeConstants;
import com.example.cafemanagementsystem.model.Bill;
import com.example.cafemanagementsystem.service.BillService;
import com.example.cafemanagementsystem.utils.CafeUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor

public class BillController {
    private final BillService billService;

    @PostMapping("/generateReport")
    ResponseEntity<String> generateReport(@RequestBody Map<String, Object> billRequestDto) {
        try {
            return billService.generateReport(billRequestDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getBills")
    ResponseEntity<List<Bill>> getBills(){
        try {
            return billService.getBills();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    @PostMapping("/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String,Object> pdfRequestDto){
        try{
            return billService.getPdf(pdfRequestDto);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable Integer id ){
        try {
            return billService.deleteBill(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
