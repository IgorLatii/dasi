package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.mail.from-name}")
    private String fromName;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(String toEmail, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Welcome to DASI Application!");
            
            String body = buildWelcomeEmailBody(userName, toEmail);
            message.setText(body);
            
            mailSender.send(message);
            logger.info("Welcome email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send welcome email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send welcome email: " + e.getMessage());
        }
    }

    private String buildWelcomeEmailBody(String userName, String email) {
        return String.format(
            "Hello %s,\n\n" +
            "Welcome to DASI Application!\n\n" +
            "Your account has been successfully created with the following details:\n" +
            "Email: %s\n\n" +
            "You can now log in to your account and start using our platform.\n\n" +
            "If you did not create this account, please contact our support team immediately.\n\n" +
            "Best regards,\n" +
            "DASI Application Team",
            userName != null && !userName.isEmpty() ? userName : "User",
            email
        );
    }
}

