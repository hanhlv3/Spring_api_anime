package com.api_anime.anime.controller;

import com.api_anime.anime.entity.User;
import com.api_anime.anime.service.UserService;
import com.api_anime.anime.utils.ApplicationUrl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    // get a user
    @GetMapping("/api/v1/private/user")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Optional<User> data = userService.findUserByEmail(userEmail);
        if (data.isEmpty()) return (ResponseEntity<?>) ResponseEntity.notFound();
        User user = data.get();
        user.setAvatar(ApplicationUrl.getUrlImage(user.getAvatar(), request));
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
