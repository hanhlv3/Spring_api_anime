package com.api_anime.anime.service;

import com.api_anime.anime.model.EmailDetails;
import org.springframework.stereotype.Service;


@Service
public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
