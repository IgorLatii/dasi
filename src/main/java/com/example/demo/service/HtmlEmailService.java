package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

@Service
public class HtmlEmailService {

    private static final Logger logger = LoggerFactory.getLogger(HtmlEmailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.mail.from-name}")
    private String fromName;

    public HtmlEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send HTML formatted welcome email with better styling
     */
    public void sendHtmlWelcomeEmail(String toEmail, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail, fromName);
            helper.setTo(toEmail);
            helper.setSubject("Welcome to DASI Application!");
            
            String htmlBody = buildHtmlWelcomeEmailBody(userName, toEmail);
            helper.setText(htmlBody, true); // true = HTML content
            
            mailSender.send(message);
            logger.info("HTML welcome email sent successfully to: {}", toEmail);
        } catch (MessagingException e) {
            logger.error("Failed to send HTML welcome email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send welcome email: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Build a professional HTML email template
     */
    private String buildHtmlWelcomeEmailBody(String userName, String email) {
        String displayName = userName != null && !userName.isEmpty() ? userName : "User";
        
        return "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <style>\n" +
            "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }\n" +
            "        .container { max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); overflow: hidden; }\n" +
            "        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; }\n" +
            "        .content { padding: 30px; }\n" +
            "        .footer { background-color: #f4f4f4; padding: 20px; text-align: center; color: #666; font-size: 12px; border-top: 1px solid #ddd; }\n" +
            "        h1 { margin: 0; font-size: 28px; }\n" +
            "        .welcome-text { color: #333; font-size: 16px; line-height: 1.6; }\n" +
            "        .info-box { background-color: #f9f9f9; border-left: 4px solid #667eea; padding: 15px; margin: 20px 0; }\n" +
            "        .info-label { font-weight: bold; color: #667eea; }\n" +
            "        .button { display: inline-block; background-color: #667eea; color: white; padding: 12px 30px; text-decoration: none; border-radius: 4px; margin-top: 20px; }\n" +
            "        .button:hover { background-color: #764ba2; }\n" +
            "        .security-notice { background-color: #fff3cd; border: 1px solid #ffc107; padding: 15px; border-radius: 4px; margin-top: 20px; font-size: 14px; color: #856404; }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"container\">\n" +
            "        <div class=\"header\">\n" +
            "            <h1>Welcome! 🎉</h1>\n" +
            "        </div>\n" +
            "        <div class=\"content\">\n" +
            "            <p class=\"welcome-text\">Hello <strong>" + escapeHtml(displayName) + "</strong>,</p>\n" +
            "            <p class=\"welcome-text\">\n" +
            "                Thank you for creating an account with <strong>DASI Application</strong>! We're excited to have you join our community.\n" +
            "            </p>\n" +
            "            \n" +
            "            <div class=\"info-box\">\n" +
            "                <p><span class=\"info-label\">Account Email:</span><br/>" + escapeHtml(email) + "</p>\n" +
            "                <p><span class=\"info-label\">Account Status:</span><br/>Active ✓</p>\n" +
            "                <p><span class=\"info-label\">Date Created:</span><br/>" + getCurrentDate() + "</p>\n" +
            "            </div>\n" +
            "            \n" +
            "            <p class=\"welcome-text\">\n" +
            "                You can now log in to your account and start exploring our platform.\n" +
            "            </p>\n" +
            "            \n" +
            "            <div style=\"text-align: center;\">\n" +
            "                <a href=\"http://localhost:3000/login\" class=\"button\">Log In to Your Account</a>\n" +
            "            </div>\n" +
            "            \n" +
            "            <div class=\"security-notice\">\n" +
            "                <strong>⚠️ Security Notice:</strong><br/>\n" +
            "                If you did not create this account, please contact our support team immediately at support@dasi-app.com\n" +
            "            </div>\n" +
            "            \n" +
            "            <p class=\"welcome-text\" style=\"margin-top: 30px;\">\n" +
            "                Best regards,<br/>\n" +
            "                <strong>The DASI Application Team</strong>\n" +
            "            </p>\n" +
            "        </div>\n" +
            "        <div class=\"footer\">\n" +
            "            <p>© 2026 DASI Application. All rights reserved.</p>\n" +
            "            <p>This is an automated message, please do not reply to this email.</p>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";
    }

    /**
     * Escape HTML special characters to prevent injection
     */
    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }

    /**
     * Get current date in a formatted way
     */
    private String getCurrentDate() {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return today.format(formatter);
    }
}


