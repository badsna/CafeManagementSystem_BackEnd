package com.example.cafemanagementsystem.serviceImpl;

import com.example.cafemanagementsystem.constants.CafeConstants;
import com.example.cafemanagementsystem.model.ResetToken;
import com.example.cafemanagementsystem.model.Users;
import com.example.cafemanagementsystem.repo.ResetTokenRepo;
import com.example.cafemanagementsystem.repo.UserRepo;
import com.example.cafemanagementsystem.service.ResetTokenService;
import com.example.cafemanagementsystem.utils.CafeUtils;
import com.example.cafemanagementsystem.utils.EmailUtils;
import com.google.common.base.Strings;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResetTokenServiceImpl implements ResetTokenService {

    private final ResetTokenRepo resetTokenRepo;
    private final UserRepo userRepo;
    private final EmailUtils emailUtils;
    private final PasswordEncoder passwordEncoder;

    Logger log = LoggerFactory.getLogger(ResetTokenServiceImpl.class);


    @Override
    @Transactional
    public ResponseEntity<String> forgotPassword(Map<String, String> userRequestDto) {
        log.info("Inside forgotPassword()");

        try {
            Users user = userRepo.findByEmail(userRequestDto.get("email"));


            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {

                String token = UUID.randomUUID().toString();
                log.info("NEW TOKEN GENERATED " + token);

                createResetToken(token, user.getEmail(), user.getId());

                emailUtils.forgotMail(user.getEmail(), "Credentials by Cafe Management System", token);

                return CafeUtils.getResponseEntity("Check your mail for credentials.", HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity("User Not Found", HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> validateResetToken(String token) {
        log.info("Validate the reset token");

        ResetToken resetToken = resetTokenRepo.findByToken(token);
        Date date = resetToken.getExpirationDate();

        if (resetToken == null || resetToken.getExpirationDate().before(new Date())) {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        }
        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updatePassword(Map<String, String> request) {
        try {
            log.info("Validating the token()");

            ResponseEntity<String> response = validateResetToken(request.get("token"));
            if (response.getStatusCode() != HttpStatus.OK) {
                return response;
            }
            ResetToken resetToken = resetTokenRepo.findByToken(request.get("token"));

            log.info("Updating the user's password");
            Users user = userRepo.findById(resetToken.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
            user.setPassword(passwordEncoder.encode(request.get("newPassword")));
            userRepo.save(user);

           log.info("Deleting the used reset token");
            deleteByToken(request.get("token"));

            return CafeUtils.getResponseEntity("Password updated successfully.", HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public void createResetToken(String token, String email, int userId) {
        log.info("Inside createRestToken()");
        ResetToken resetToken = resetTokenRepo.findByEmail(email);

        if (resetToken == null) {
            log.info("Inside if");
            resetToken = new ResetToken();
            resetToken.setEmail(email);
            resetToken.setUserId(userId);
        }

        resetToken.setToken(token);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 20);
        resetToken.setExpirationDate(cal.getTime());
        resetTokenRepo.save(resetToken);

    }

    public void deleteByToken(String token) {
        log.info("Inside deleteByToken()");
        resetTokenRepo.deleteByToken(token);
    }

}
