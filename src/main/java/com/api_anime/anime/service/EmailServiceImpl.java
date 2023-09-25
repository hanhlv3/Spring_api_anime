package com.api_anime.anime.service;


// Annotation


import java.io.File;

import com.api_anime.anime.model.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

// Class
// Implementing EmailService interface
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendEmail(EmailDetails emailDetails) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailDetails.getRecipient());
        helper.setSubject(emailDetails.getSubject());
        helper.setText(emailDetails.getMsgBody(), true); // Enable HTML content
        javaMailSender.send(message);
        return null;
    }
}
