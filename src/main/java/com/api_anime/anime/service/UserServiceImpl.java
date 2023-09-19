package com.api_anime.anime.service;

import com.api_anime.anime.entity.PasswordResetToken;
import com.api_anime.anime.entity.User;
import com.api_anime.anime.entity.VerificationToken;
import com.api_anime.anime.model.UserModel;
import com.api_anime.anime.repository.PasswordResetTokenRepository;
import com.api_anime.anime.repository.UserRepository;
import com.api_anime.anime.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(User userModel) {

        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel.setCreatedAt(Calendar.getInstance().getTime());
        userModel.setRole("user");
        userRepository.save(userModel);
        return userModel;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String verificationToken(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if(verificationToken == null) {
            return "invalid";
        }



        User user = verificationToken.getUser();

        Calendar cal = Calendar.getInstance();

        if(0 >= (verificationToken.getExpirationTime().getTime() - cal.getTime().getTime())) {

            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";

    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).get();
        return user;
    }

    @Override
    public void savePasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }
}
