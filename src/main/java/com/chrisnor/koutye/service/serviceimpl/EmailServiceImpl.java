package com.chrisnor.koutye.service.serviceimpl;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.chrisnor.koutye.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Component
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	private String logoFile = "/static/logo-header.png";

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
	@Override
	public void sendHtmlMessage(String emailTo, String subject, String htmlBody) throws MessagingException {
	    jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true); // Active le mode multipart
	    //helper.addAttachment("logo-header.png", new ClassPathResource(logoFile));
	    helper.setFrom("tombeauc@gmail.com");
	    helper.setTo(emailTo);
	    helper.setSubject(subject);
	    helper.setText(htmlBody, true);
	    mailSender.send(message);
	}

	@Override
	public void sendMessageUsingThymeleafTemplate(String emailTo, String subject,
			Map<String, Object> templateModel) throws MessagingException {
		   Context thymeleafContext = new Context();
		   thymeleafContext.setVariables(templateModel);
		   String htmlBody = templateEngine.process("email", thymeleafContext);
		   sendHtmlMessage(emailTo, subject, htmlBody);	
	}

	@Override
	public void sendMessageUsingThymeleafTemplateAttach(String emailTo, String subject,
			Map<String, Object> templateModel) throws MessagingException {
			   Context thymeleafContext = new Context();
			   thymeleafContext.setVariables(templateModel);
			   String htmlBody = templateEngine.process("attachementMail", thymeleafContext);
			   sendHtmlMessage(emailTo, subject, htmlBody);	
		
	}
	
}
