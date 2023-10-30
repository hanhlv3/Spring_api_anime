package com.api_anime.anime.service;

import com.api_anime.anime.entity.User;
import com.api_anime.anime.entity.VerificationToken;
import com.api_anime.anime.model.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public interface UserService {

    User registerUser(UserModel userModel, MultipartFile image) throws IOException;

    void saveVerificationTokenForUser(String token, User user);

    String verificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);


    Optional<User> findUserByEmail(String email);

    void savePasswordResetToken(User user, String token);

}
