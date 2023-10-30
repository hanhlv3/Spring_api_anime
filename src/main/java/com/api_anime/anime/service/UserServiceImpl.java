package com.api_anime.anime.service;

import com.api_anime.anime.entity.PasswordResetToken;
import com.api_anime.anime.entity.User;
import com.api_anime.anime.entity.VerificationToken;
import com.api_anime.anime.model.Role;
import com.api_anime.anime.model.UserModel;
import com.api_anime.anime.repository.PasswordResetTokenRepository;
import com.api_anime.anime.repository.UserRepository;
import com.api_anime.anime.repository.VerificationTokenRepository;
import com.api_anime.anime.utils.CheckFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private final Path storageFolder = Paths.get("uploads");

    public UserServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException exception) {
            throw new RuntimeException("Cannot initialize storage", exception);
        }
    }

    @Override
    public User registerUser(UserModel userModel, MultipartFile file) throws IOException {

        boolean checkFileImage = CheckFile.isImageFile(file);
        if (!checkFileImage) throw new RuntimeException("File must have image file");
        // rename file
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generatedFileName = UUID.randomUUID().toString().replace("-", "");
        generatedFileName = generatedFileName + "." + fileExtension;

        Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
        Files.write(destinationFilePath, file.getBytes());


        User user = new User();
        user.setUserName(userModel.getUserName());
        user.setEmail(userModel.getUserEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(Calendar.getInstance().getTime());
        user.setAvatar(generatedFileName);

        userRepository.save(user);
        return user;
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
    public  Optional<User> findUserByEmail(String email) {
       Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public void savePasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }


}
