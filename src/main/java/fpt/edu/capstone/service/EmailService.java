package fpt.edu.capstone.service;

import fpt.edu.capstone.common.user.EmailDetails;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendResetPasswordEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
}
