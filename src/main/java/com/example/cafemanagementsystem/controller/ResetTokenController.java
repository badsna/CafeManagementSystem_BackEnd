package com.example.cafemanagementsystem.controller;

import com.example.cafemanagementsystem.constants.CafeConstants;
import com.example.cafemanagementsystem.repo.UserRepo;
import com.example.cafemanagementsystem.service.ResetTokenService;
import com.example.cafemanagementsystem.utils.CafeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/resetToken")
@RequiredArgsConstructor
public class ResetTokenController {
    private final ResetTokenService resetTokenService;
    private final UserRepo userRepo ;

    @PostMapping("/generate")
    ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> userRequestDto){
        try{
            return resetTokenService.forgotPassword(userRequestDto);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/validate/{token}")
    public ResponseEntity<String> validateResetToken(@PathVariable String token) {
        try{
            return resetTokenService.validateResetToken(token);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/update")
    public ResponseEntity<String> updatePassword(@RequestBody  Map<String, String> request) {
        try{
            return resetTokenService.updatePassword(request);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
