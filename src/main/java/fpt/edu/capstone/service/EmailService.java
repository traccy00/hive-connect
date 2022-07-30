package fpt.edu.capstone.service;

import fpt.edu.capstone.common.EmailDetails;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details) throws Exception;

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details) throws Exception;

    void sendResetPasswordEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
}
