package com.api_anime.anime.controller;


import com.api_anime.anime.model.EmailDetails;
import com.api_anime.anime.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired(required = false)
    private EmailService emailService;

    @GetMapping("/test")
    public String test() {
        EmailDetails emailDetails = new EmailDetails("hanhdev2k3@gmail.com", "hello", "test");
        String result = "";
        try {
            result = emailService.sendEmail(emailDetails);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
