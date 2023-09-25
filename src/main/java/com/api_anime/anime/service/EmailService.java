package com.api_anime.anime.service;

import com.api_anime.anime.model.EmailDetails;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;


@Service
public interface EmailService {


    String  sendEmail(EmailDetails emailDetails) throws MessagingException;
}
