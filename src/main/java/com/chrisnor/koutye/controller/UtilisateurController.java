package com.chrisnor.koutye.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.commons.io.IOExceptionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.chrisnor.koutye.dto.LoginDto;
import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.dto.UtilisateurFileDto;
import com.chrisnor.koutye.file.FileUpload;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.repository.UtilisateurRepository;
import com.chrisnor.koutye.response.Response;
import com.chrisnor.koutye.response.ResponseGenerator;
import com.chrisnor.koutye.service.EmailService;
import com.chrisnor.koutye.service.UtilisateurService;
import com.chrisnor.koutye.utils.IdentityUserEmail;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UtilisateurController {

	@Value("${pathUser}")
	private String userFolder;

	@Autowired
	private UtilisateurService utilService; 
	
	@Autowired
	private ResponseGenerator responseGenerator;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UtilisateurRepository utilisateurRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SpringTemplateEngine templateEngine;

	@PostMapping(value="/user/add")
	public ResponseEntity<Response> AjouterUtilisateur(@RequestParam MultipartFile photo,@Valid @RequestParam String model)
			throws Exception {
		  
		String retour;
		ObjectMapper mapper = new ObjectMapper();
		UtilisateurDto utilisateurDto = mapper.readValue(model, UtilisateurDto.class);
		
		if(utilService.getUtilisateur(utilisateurDto.getUsername()) == null
				&& utilService.getUtilisateurByEmail(utilisateurDto.getEmail()) == null)
		{
			System.out.println("on est dans le save du controlleur");
			retour = new FileUpload().UploadFiles(photo, userFolder + utilisateurDto.getUsername());
			if (retour != null)
				utilisateurDto.setPhoto(retour.replace("\\", "/"));

			UtilisateurDto util = utilService.PostUtilisateur(utilisateurDto);
			return responseGenerator.SuccessResponse(HttpStatus.CREATED, util);
		}
		else
		{
			return responseGenerator.ErrorResponse(HttpStatus.CONFLICT, "Utilisateur existe deja");
		}
		
	}
	
	@PostMapping("/login")
	public Map<String, String> Login(@RequestBody LoginDto loginDto)
	{
  
		Authentication authentication = authenticationManager.authenticate(
				 new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
				);
		
		if(authentication.isAuthenticated())
		{
			utilService.Login(loginDto.getUsername(), loginDto.getPassword());
			return utilService.GenerateToken(loginDto.getUsername(),authentication);
		}
		return Map.of("message", "username ou password incorrect");
	}

	@GetMapping("/user")
	public ResponseEntity<Response> ShowUser(@RequestParam String username) {
		// System.out.println(username);
		Optional<UtilisateurDto> utilDto = utilService.getUtilisateur(username);

		if (utilDto != null) {
			Response res = new Response();
			res.setObject(utilDto);
			res.setMessage("Utilisateur trouve.");
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			Response res = new Response();
			res.setMessage("Utilisateur non trouve.");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/user/update/{id}")
	public ResponseEntity<Response> UpdateUser(@PathVariable Long id, @RequestParam MultipartFile photo,
			@RequestParam String model) {
		String retour;
		UtilisateurDto util = new UtilisateurDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			UtilisateurDto utilDto = mapper.readValue(model, UtilisateurDto.class);
			retour = new FileUpload().UploadFiles(photo, userFolder + utilDto.getUsername());
			if (retour != null)
				utilDto.setPhoto(retour.replace("\\", "/"));

			util = utilService.PutUtilisateur(id, utilDto);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (util != null) {
			Response res = new Response();
			res.setObject(util);
			res.setMessage("Utilisateur modifié avec succès");
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} else {
			Response res = new Response();
			res.setMessage("Erreur lors de la mofification de l'utilisateur");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
   
	/*
	@GetMapping("/users-page")
	//@PreAuthorize("hasAuthority('SCOPE_BackAdmin')")
	public ResponseEntity<Response> ListUtilisateurs(@RequestParam int pageNo, @RequestParam int pageSize) {
		Page<Utilisateur> utilisateurs = utilService.getAllUtilisateurs(pageNo,pageSize);
		if(utilisateurs.isEmpty())
		   return responseGenerator.SuccessResponse(HttpStatus.NO_CONTENT, utilisateurs);
		else
			return responseGenerator.SuccessResponse(HttpStatus.OK, utilisateurs);
	}
	*/
	
	@GetMapping("/users")
	//@PreAuthorize("hasAuthority('SCOPE_BackAdmin')")
	public ResponseEntity<Response> ListUtilisateurs() {
		List<UtilisateurDto> utilisateurs = utilService.getUtilisateurs();
		if(utilisateurs.isEmpty())
		   return responseGenerator.SuccessResponse(HttpStatus.NO_CONTENT, utilisateurs);
		else
			return responseGenerator.SuccessResponse(HttpStatus.OK, utilisateurs);
	}
	
	@GetMapping("/send-email")
	public ResponseEntity<Response> getEmail(@RequestParam String emailTo, Model model) throws MessagingException
	{
		String subject = "Changement de mot de passe.";
		UtilisateurDto util = new UtilisateurDto();
		//Context thymeleafContext = new Context();
		 
		
		if(utilService.verifyEmail(emailTo))
		{
			 String defaultPassword = utilService.generateDefaultPassword();

			 //thymeleafContext.setVariable("defaultPassword",defaultPassword);
			 //String result = templateEngine.process("email", thymeleafContext);
			 model.addAttribute("defaultPassword", defaultPassword);
			 util = utilService.getUtilisateurByEmail(emailTo).get();
			 
			 emailService.sendMessageUsingThymeleafTemplate(emailTo, subject, IdentityUserEmail.getIdentityUserEmail(util));
			return responseGenerator.SuccessResponse(HttpStatus.OK, "Mail envoyé avec succès...");
		}
		else
			return responseGenerator.SuccessResponse(HttpStatus.NOT_FOUND, "Aucun email ne correspond...");
	}
	
	@GetMapping("/default-password")
	public String getDefaultPassword()
	{
		return utilService.generateDefaultPassword();
	}
}