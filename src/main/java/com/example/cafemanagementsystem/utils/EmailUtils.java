package com.example.cafemanagementsystem.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailUtils {
    private final JavaMailSender emailSender;
    Logger log = LoggerFactory.getLogger(EmailUtils.class);

    public void sendSimpleMessage(String to, String subject, String text, List<String> list) {
        log.info("Inside sendSimpleMessage()");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("badsnastha@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        if (list != null && list.size() > 0)
            message.setCc(getCcArray(list));

        emailSender.send(message);
    }

    private String[] getCcArray(List<String> ccList) {
        String[] cc = new String[ccList.size()];
        for (int i = 0; i < ccList.size(); i++) {
            cc[i] = ccList.get(i);
        }
        return cc;
    }

    public void forgotMail(String to, String subject, String token) throws MessagingException {
        log.info("Inside forgotMail()");

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("badsnastha@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String resetPasswordLink = "http://localhost:4200/cafe/resetPassword?token=" + token;

        String htmlMsg = "<p><b>Your Login details for Cafe Management System</b>" +
                "<br>" +
                "<b>Email: </b>" +
                to +
                "<br><b>Token: </b>" +
                token +
                "<br> Use this to change your password" +
                "<br><a href=\"" + resetPasswordLink + "\">Click here to reset Password</a></p>";
        message.setContent(htmlMsg, "text/html");

        emailSender.send(message);

    }
}
