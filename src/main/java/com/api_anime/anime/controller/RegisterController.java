package com.api_anime.anime.controller;


import com.api_anime.anime.entity.User;
import com.api_anime.anime.entity.VerificationToken;
import com.api_anime.anime.event.RegistrationCompleteEvent;
import com.api_anime.anime.model.UserModel;
import com.api_anime.anime.service.EmailService;
import com.api_anime.anime.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.UUID;


@RestController()
@Slf4j

public class RegisterController {

    private static final String URL_API_PUBLIC = "/api/v1";
    @Autowired(required = false)
    private UserService userService;


    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicationEventPublisher publisher; // create other thread

    @PostMapping(URL_API_PUBLIC + "/register")
    public String  registerUser(@RequestPart("user") String dataString, @RequestPart("image")MultipartFile image,  final HttpServletRequest request) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserModel userModel = objectMapper.readValue(dataString, UserModel.class);
            System.out.println(userModel.toString());
            User user = userService.registerUser(userModel, image);
            publisher.publishEvent( new RegistrationCompleteEvent(
                    user,
                    applicationUrl(request)
            ));
        } catch (Exception e) {
            log.info("error", e);
        }

        return "Register successfully. Please check email !";
    }

    @GetMapping(URL_API_PUBLIC + "/verifyRegistration")
    public String verificationToken(@RequestParam("token") String token) {

        String result = userService.verificationToken(token);
        if(result.equalsIgnoreCase("valid"))  return "User verified successfully";
        else if(result.equalsIgnoreCase("")) return "Token is expired";
        return "Bad user";
    }
    @GetMapping(URL_API_PUBLIC + "/resendVerifyToken")
    public String resendVerifyToken(@RequestParam("token") String oldToken, HttpServletRequest request) {

        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerifyTokenMail(user, applicationUrl(request), verificationToken);
        return "Bad user";
    }

    // change password
    @GetMapping(URL_API_PUBLIC + "/resetPassword")
    public String resetPassword(@RequestParam("email") String email, HttpServletRequest request) {
        User user = userService.findUserByEmail(email).get();
        if(user == null) return "Email incorrect";

        // create token
        String token = UUID.randomUUID().toString();
        userService.savePasswordResetToken(user, token);

        // send to email
        sendMaiChangePasswordToken(user, applicationUrl(request), token);
        return "Please check mail !";

    }

    private void sendMaiChangePasswordToken(User user, String applicationUrl, String token) {
        String url = applicationUrl
                + "/api/v1/verifyTokenChangePassword?token="
                + token;
        // sendVerificationEmail()
        log.info("Click the link to change your password {}",
                url);
    }


    private void resendVerifyTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl
                + "/api/v1/verifyRegistration?token="
                + verificationToken.getToken();
        // sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath();
    }
}
