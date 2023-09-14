package com.example.cafemanagementsystem.serviceImpl;

import com.example.cafemanagementsystem.constants.CafeConstants;
import com.example.cafemanagementsystem.jwt.ApplicationConfig;
import com.example.cafemanagementsystem.jwt.JwtAuthenticationFilter;
import com.example.cafemanagementsystem.jwt.JwtUtil;
import com.example.cafemanagementsystem.model.Users;
import com.example.cafemanagementsystem.repo.UserRepo;
import com.example.cafemanagementsystem.service.UserService;
import com.example.cafemanagementsystem.utils.CafeUtils;
import com.example.cafemanagementsystem.utils.EmailUtils;
import com.example.cafemanagementsystem.wrapper.UserWrapper;
import com.google.common.base.Strings;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmailUtils emailUtils;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ApplicationConfig applicationConfig;
    private final PasswordEncoder passwordEncoder;

    Logger log= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntity<String> signup(Map<String, String> userRequestDto) {
        try {
            log.info("Inside signup()", userRequestDto);

            if (validateSignUpMap(userRequestDto)) {

                Users user = userRepo.findByEmail(userRequestDto.get("email"));

                if (Objects.isNull(user)) {

                    userRepo.save(getUserFromMap(userRequestDto));
                    return CafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);

                } else {
                    return CafeUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> userRequestDto) {
        log.info("Inside validateSignUpMap()");

        return userRequestDto.containsKey("name")
                && userRequestDto.containsKey("contactNumber")
                && userRequestDto.containsKey("email")
                && userRequestDto.containsKey("password");
    }

    private Users getUserFromMap(Map<String, String> userRequestDto) {
        log.info("Inside getUserFromMap()");

        Users user = new Users();
        user.setName(userRequestDto.get("name"));
        user.setEmail(userRequestDto.get("email"));
        user.setContactNumber(userRequestDto.get("contactNumber"));
        user.setPassword(passwordEncoder.encode(userRequestDto.get("password")));
        user.setStatus("false");
        user.setRole("user");

        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> userRequestDto) {
        log.info("Inside login");

        try {
            log.info("Authenticating user");

            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            userRequestDto.get("email")
                            , userRequestDto.get("password"))
            );

            if (auth.isAuthenticated()) {
                if (applicationConfig.getUserDetail().getStatus().equalsIgnoreCase("true")) {

                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(applicationConfig.getUserDetail().getEmail(), applicationConfig.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK);

                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "You cannot login right now. Wait for admin approval." + "\"}",
                            HttpStatus.BAD_REQUEST);
                }

            }
        } catch (Exception ex) {
            log.error("{}", ex);
        }

        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials" + "\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            log.info("Inside getAllUser()");

            if (jwtAuthenticationFilter.isAdmin()) {
                List<Users> usersList = userRepo.findAll();

                return new ResponseEntity<>(mapToUserWrapper(usersList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private List<UserWrapper> mapToUserWrapper(List<Users> usersList) {
        log.info("Inside mapToUserWrapper()");

        List<UserWrapper> userWrapperList = new ArrayList<>();

        for (Users users : usersList) {

            UserWrapper userWrapper = new UserWrapper();
            userWrapper.setName(users.getName());
            userWrapper.setStatus(users.getStatus());
            userWrapper.setEmail(users.getEmail());
            userWrapper.setContactNumber(users.getContactNumber());
            userWrapper.setId(users.getId());
            userWrapperList.add(userWrapper);

        }

        return userWrapperList;
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> userRequestDto) {
        log.info("Inside updateUser()");
        try {

            if (jwtAuthenticationFilter.isAdmin()) {
                Optional<Users> users = userRepo.findById(Integer.parseInt(userRequestDto.get("id")));

                if (users.isPresent()) {

                    userRepo.updateStatus(userRequestDto.get("status"), Integer.parseInt(userRequestDto.get("id")));

                    sendMailToAllAdmin(userRequestDto.get("status"), users.get().getEmail(), userRepo.findAllByRole());

                    return CafeUtils.getResponseEntity("User Status updated successfully", HttpStatus.OK);

                } else {
                    return CafeUtils.getResponseEntity("User id doesn't exists", HttpStatus.OK);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String status, String user, List<String> admin) {
        log.info("Inside sendMailToAllAdmin()");

        admin.remove(jwtAuthenticationFilter.getCurrentUser());

        if (status != null && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMessage(jwtAuthenticationFilter.getCurrentUser(), "Account Approved", "USER:- " + user + "\n is approved by \nADMIN:-" + jwtAuthenticationFilter.getCurrentUser(), admin);
        } else {
            emailUtils.sendSimpleMessage(jwtAuthenticationFilter.getCurrentUser(), "Account Disabled", "USER:- " + user + "\n is disabled by \nADMIN:-" + jwtAuthenticationFilter.getCurrentUser(), admin);
        }
    }

    @Override
    public ResponseEntity<String> checkToken() {
        log.info("Inside checkToken()");

        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> userRequestDto) {
        log.info("Inside changePassword()");

        try {
            Users users = userRepo.findByEmail(jwtAuthenticationFilter.getCurrentUser());

            if (!users.equals(null)) {

                if (passwordEncoder.matches(userRequestDto.get("oldPassword"), users.getPassword())) {

                    users.setPassword(passwordEncoder.encode(userRequestDto.get("newPassword")));

                    userRepo.save(users);

                    return CafeUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<UserWrapper> getUserDetails() {
        try{
           String email= jwtAuthenticationFilter.getCurrentUser();
            Users users=userRepo.findByEmail(email);
            UserWrapper userWrapper=new UserWrapper();
            userWrapper.setId(users.getId());
            userWrapper.setName(users.getName());
            userWrapper.setEmail(users.getEmail());
            userWrapper.setContactNumber(users.getContactNumber());
            userWrapper.setStatus(users.getStatus());
            return new ResponseEntity<>(userWrapper, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new UserWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
