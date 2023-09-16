package com.api_anime.anime.controller;


import com.api_anime.anime.entity.User;
import com.api_anime.anime.event.RegistrationCompleteEvent;
import com.api_anime.anime.model.UserModel;
import com.api_anime.anime.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;


@RestController()
@Slf4j

public class RegisterController {

    private static final String URL_API_PUBLIC = "/api/v1";
    @Autowired(required = false)
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher; // create other thread




    @PostMapping(URL_API_PUBLIC + "/register")
    public UserModel  registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {


        return userModel;
//
//        User user = userService.registerUser(userModel);
//        publisher.publishEvent( new RegistrationCompleteEvent(
//                user,
//                applicationUrl(request)
//        ));
//
//        return "resgiter succesfully";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath();
    }

}
