package com.chrisnor.koutye.service;

import jakarta.mail.MessagingException;

public interface EmailService {
     public void sendEmail(String toEmail, String subject, String body);
     public void sendEmailWithAttachment(String toEmail, String subject, String body, String attachment) throws MessagingException ;
}
