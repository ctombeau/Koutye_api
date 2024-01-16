package com.chrisnor.koutye.service.serviceimpl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.chrisnor.koutye.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Component
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendEmail(String toEmail, String subject, String body) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("tombeauc@gmail.com");
		mailMessage.setTo(toEmail);
		mailMessage.setText(body);
		mailMessage.setSubject(subject);
		
		mailSender.send(mailMessage);
		System.out.println("Mail envoye avec succes");
	}

	@Override
	public void sendEmailWithAttachment(String toEmail, String subject, String body, String attachment)
			throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
		mimeMessageHelper.setFrom("tombeauc@gmail.com");
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);
		
		FileSystemResource fileSystem = new FileSystemResource(new File(attachment));
		
		mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);
		mailSender.send(mimeMessage);
		
		System.out.println("Mail attachement envoye avec succes");
	}
}
