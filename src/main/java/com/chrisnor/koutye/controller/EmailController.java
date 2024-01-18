package com.chrisnor.koutye.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.service.EmailService;
import com.chrisnor.koutye.service.UtilisateurService;
import com.chrisnor.koutye.utils.IdentityUserEmail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mail")
//@RequiredArgsConstructor
public class EmailController {
	@Autowired
	private EmailService emailService; 
	
	@Autowired
	private UtilisateurService utilService;
	
	@GetMapping("/change-password")
	public ResponseEntity<?> sendMail(@RequestParam String emailTo) throws MessagingException
	{
		 UtilisateurDto util = new UtilisateurDto();
		 util = utilService.getUtilisateurByEmail(emailTo).get();
		 System.out.println("on test....");
		 System.out.println(util.getNom());
		 System.out.println(util.getPrenom());
		 System.out.println(util.getUsername());
		 System.out.println(util.getEmail());
		 String subject = "Changement de mot de passe.";
		 emailService.sendMessageUsingThymeleafTemplate(emailTo, subject, IdentityUserEmail.getIdentityUserEmail(util));
		 return null;
	}

}
