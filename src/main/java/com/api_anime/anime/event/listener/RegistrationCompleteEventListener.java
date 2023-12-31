package com.api_anime.anime.event.listener;

import com.api_anime.anime.entity.User;
import com.api_anime.anime.event.RegistrationCompleteEvent;
import com.api_anime.anime.model.EmailDetails;
import com.api_anime.anime.service.EmailService;
import com.api_anime.anime.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@Slf4j
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create the Verification Token for the User with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        // Send Mail to user
        String url = event.getApplicationUrl()
                    + "/api/v1/verifyRegistration?token="
                    + token;

        // send VerificationEmail()
        EmailDetails emailDetails = new EmailDetails(user.getEmail(), "Click to link : " + url, "Verification account");

        try {
            emailService.sendEmail(emailDetails);
        } catch (Exception exception)  {
            throw  new RuntimeException(exception.getMessage());
        }
        log.info("Click the link to verify your account: {}", url);


    }
}
