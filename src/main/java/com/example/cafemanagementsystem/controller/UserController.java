package com.example.cafemanagementsystem.controller;

import com.example.cafemanagementsystem.constants.CafeConstants;
import com.example.cafemanagementsystem.service.UserService;
import com.example.cafemanagementsystem.utils.CafeUtils;
import com.example.cafemanagementsystem.wrapper.UserWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
        @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),
        @ApiResponse(responseCode = "200", ref = "successAPI"),
        @ApiResponse(responseCode = "403", ref = "forbiddenAPI"),
        @ApiResponse(responseCode = "401", ref = "unAuthorizedAPI")
})

public class UserController {
    private final UserService userService;
    Logger log= LoggerFactory.getLogger(UserController.class);


    @PostMapping("/signup")
    @Operation(description = "Signup Service")
    @Tag(name = "userPostApi")
    public ResponseEntity<String> signup(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\"name\": \"testName\", \"contactNumber\": \"1234567890\", \"email\": \"testName@mailinator.com\",\"password\": \"test123\"}"
                                    )
                            }

                    ))
            @RequestBody(required = true) Map<String, String> userRequestDto) {
        try {
            return userService.signup(userRequestDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    @Operation(description = "Login Service")
    @Tag(name = "userPostApi")
    public ResponseEntity<String> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\"email\": \"testName@mailinator.com\", \"password\": \"test123\"}"
                                    )
                            }

                    ))
            @RequestBody(required = true) Map<String, String> userRequestDto) {
        try {
            return userService.login(userRequestDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/getAll")
    @Operation(
            description = "Getting all Users",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            return userService.getAllUser();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @PostMapping("/update")
    @Operation(
            description = "Updating User Status",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "userPostApi")
    public ResponseEntity<String> updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\"id\": 1, \"status\": \"true\"}"
                                    )
                            }

                    ))
            @RequestBody(required = true) Map<String, String> userRequestDto) {
        try {
            return userService.updateUser(userRequestDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/checkToken")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Checking Token"
    )
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/changePassword")
    @Operation(
            description = "Changing Password",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Tag(name = "userPostApi")
    public ResponseEntity<String> changePassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\"oldPassword\": \"test123\", \"newPassword\": \"test1234\"}"
                                    )
                            }

                    ))
            @RequestBody Map<String, String> userRequestDto) {
        try {
            return userService.changePassword(userRequestDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/get")
    @Operation(
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Getting Current User Details"
    )
    public ResponseEntity<UserWrapper> getUserDetails() {
        try {
            return userService.getUserDetails();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new UserWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
