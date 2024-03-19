package com.chrisnor.koutye.service;

import java.util.Map;

import jakarta.mail.MessagingException;

public interface EmailService {
     public void sendEmail(String toEmail, String subject, String body);
     public void sendEmailWithAttachment(String toEmail, String subject, String body, String attachment) throws MessagingException ;
     public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException;
     void sendMessageUsingThymeleafTemplate(
 			String emailTo, String subject, Map<String, Object> templateModel)
 			throws MessagingException;
     void sendMessageUsingThymeleafTemplateAttach(
  			String emailTo, String subject, Map<String, Object> templateModel)
  			throws MessagingException;
}
