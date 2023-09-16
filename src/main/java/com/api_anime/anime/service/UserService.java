package com.api_anime.anime.service;

import com.api_anime.anime.entity.User;
import com.api_anime.anime.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User registerUser(User userModel);

    void saveVerificationTokenForUser(String token, User user);
}
