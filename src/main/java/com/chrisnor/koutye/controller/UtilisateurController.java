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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.chrisnor.koutye.dto.LoginForgetPasswordDto;
import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.dto.UtilisateurFileDto;
import com.chrisnor.koutye.exception.FileNotFoundException;
import com.chrisnor.koutye.file.FileUpload;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.repository.UtilisateurRepository;
import com.chrisnor.koutye.response.Response;
import com.chrisnor.koutye.response.ResponseGenerator;
import com.chrisnor.koutye.service.EmailService;
import com.chrisnor.koutye.service.UtilisateurService;
import com.chrisnor.koutye.service.serviceimpl.UserDetailServiceImpl;
import com.chrisnor.koutye.utils.IdentityUserEmail;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Locale;
import java.util.Map;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@EnableMethodSecurity(prePostEnabled=true)
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
	public ResponseEntity<Response> AjouterUtilisateur(@RequestBody UtilisateurDto utilisateurDto)
	 {
		
		if(utilService.getUtilisateur(utilisateurDto.getUsername()) == null
				&& utilService.getUtilisateurByEmail(utilisateurDto.getEmail()) == null)
		{
			UtilisateurDto util = utilService.PostUtilisateur(utilisateurDto);
			return responseGenerator.SuccessResponse(HttpStatus.CREATED, util);
		}
		else
		{
			return responseGenerator.ErrorResponse(HttpStatus.CONFLICT, "Utilisateur existe deja");
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> Login(@RequestBody LoginDto loginDto)
	{
		Authentication authentication = authenticationManager.authenticate(
				 new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
				);
		Optional<UtilisateurDto> util = utilService.getUtilisateur(loginDto.getUsername());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		if(authentication.isAuthenticated() && util.get().isActif()==true)	
		{
			utilService.Login(loginDto.getUsername(), loginDto.getPassword());
			return responseGenerator.SuccessResponse(HttpStatus.OK,utilService.GenerateToken(loginDto.getUsername(),authentication));
		}
		else
			return responseGenerator.ErrorResponse(HttpStatus.UNAUTHORIZED, "username et/ou password incorrect");
		
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<Response> resetPassword(@RequestBody LoginForgetPasswordDto loginDto)
	{
		boolean result = utilService
	               .firstLoginAfterForgetPassword(loginDto.getEmail(),loginDto.getOldPassword(),loginDto.getNewPassword());
		if(result == true)
			return responseGenerator.SuccessResponse(HttpStatus.OK, null);
		else
			return responseGenerator.ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "utilisateur non modifie");
	}
	
	@GetMapping("/user")
	public ResponseEntity<Response> ShowUser(@RequestParam String username) {
		// System.out.println(username);
		Optional<UtilisateurDto> utilDto = utilService.getUtilisateur(username);

		if (utilDto != null) {
	
			return responseGenerator.SuccessResponse(HttpStatus.OK, utilDto);
		} else {
			
			return responseGenerator.ErrorResponse(HttpStatus.NO_CONTENT, "Utilisateur non trouve");
		}
	}

	@PutMapping("/user/update/{id}")
	public ResponseEntity<Response> UpdateUser(@PathVariable Long id, @RequestBody UtilisateurDto utilDto) {
		UtilisateurDto util = new UtilisateurDto();
		Utilisateur utilBase = new Utilisateur();
		
		utilBase = utilisateurRepo.getById(id);
		
		if(utilBase.getEmail().equals(utilDto.getEmail()) && utilBase.getUsername().equalsIgnoreCase(utilDto.getUsername()))
		{
			util = utilService.PutUtilisateur(id, utilDto);
			return responseGenerator.SuccessResponse(HttpStatus.OK, util);
		}
		else
		{
			
			if(utilService.getUtilisateur(utilDto.getUsername()) != null || utilService.getUtilisateurByEmail(utilDto.getEmail()) != null)
			{
				return responseGenerator.ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la mofification de l'utilisateur");
			}
			else
			{
				util = utilService.PutUtilisateur(id, utilDto);
				return responseGenerator.SuccessResponse(HttpStatus.OK, util);
			}
			
		}
		
		
	}
   
	
	@GetMapping("/users-page")
	//@PreAuthorize("hasAuthority('SCOPE_BackAdmin')")
	public ResponseEntity<Response> ListUtilisateurs(@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="3") int pageSize) {
		try
		{
			Page<Utilisateur> utilisateurs = utilService.getAllUtilisateurs(pageNo,pageSize);
		
			if(utilisateurs.isEmpty())
		       return responseGenerator.SuccessResponse(HttpStatus.NO_CONTENT, utilisateurs);
		   else
			 return responseGenerator.SuccessResponse(HttpStatus.OK, utilisateurs);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
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
	public ResponseEntity<Response> getEmail(@RequestParam String emailTo) throws MessagingException
	{
		String subject = "Changement de mot de passe.";
		UtilisateurDto util = new UtilisateurDto();
		
		if(utilService.verifyEmail(emailTo))
		{
			 String defaultPassword = utilService.generateDefaultPassword();
			 util = utilService.getUtilisateurByEmail(emailTo).get();
			 utilService.setPassword(defaultPassword, emailTo);
			 emailService.sendMessageUsingThymeleafTemplate(emailTo, subject, IdentityUserEmail.getIdentityUserEmailPassword(util,defaultPassword));
			return responseGenerator.SuccessResponse(HttpStatus.OK, "Mail envoyé avec succès...");
		}
		else
			return responseGenerator.ErrorResponse(HttpStatus.NOT_FOUND, "Aucun email ne correspond...");
	}
	
	@GetMapping("/send-email-attachment") 
	public ResponseEntity<Response> getEmailAttachment(@RequestParam String emailFrom, @RequestParam String emailTo) throws MessagingException
	{
		String subject = "Demande d'Autorisation Courtier.";
		UtilisateurDto utilFrom = new UtilisateurDto();
		UtilisateurDto utilTo = new UtilisateurDto();
		
		if(utilService.verifyEmail(emailTo) && utilService.verifyEmail(emailFrom))
		{
			 utilFrom = utilService.getUtilisateurByEmail(emailFrom).get();
			 utilTo = utilService.getUtilisateurByEmail(emailTo).get();
			 
			 if(utilFrom.getNomType().equals("Proprietaire") && utilTo.getNomType().equals("Courtier"))
			 {
			   emailService.sendMessageUsingThymeleafTemplateAttach(emailTo, subject, IdentityUserEmail.getIdentityUserEmailAttach(utilFrom, utilTo));
			   return responseGenerator.SuccessResponse(HttpStatus.OK, "Mail envoyé avec succès...");
		
			 }
			 else
				 return responseGenerator.ErrorResponse(HttpStatus.NOT_FOUND, "L'un d'entre vous n'a pas le droit...");
		}
		else
			return responseGenerator.ErrorResponse(HttpStatus.NOT_FOUND, "Aucun email ne correspond...");
	}
	
	@GetMapping("/default-password")
	public String getDefaultPassword()
	{
		return utilService.generateDefaultPassword();
	}
	
	@PostMapping("/update-picture")
	public ResponseEntity<?> setProfilePicture(@RequestParam String username, @RequestParam MultipartFile photo) throws FileNotFoundException
	{
		String retour;
		retour = new FileUpload().UploadFiles(photo, userFolder +"\\"+ username);
		if (retour != null && !retour.equals(""))
		{
			retour = retour.replace("\\", "/");
			utilService.updateProfilePicture(username,retour );
			return responseGenerator.SuccessResponse(HttpStatus.CREATED, retour);
		}
		else
			throw new FileNotFoundException();
	}
	
	@PostMapping("/attach-user")
	public ResponseEntity<?> attachUsers(@RequestParam String usernamePro, @RequestParam String usernameCour)
	{
		System.out.println("Test de submit via email");
		boolean result = utilService.postAttachUsers(usernamePro, usernameCour);
		if(result == true)
			return responseGenerator.SuccessResponse(HttpStatus.OK, "Utilisateurs attachés avec succès");
		else
			return responseGenerator.ErrorResponse(HttpStatus.BAD_REQUEST, "Les utilisateurs ne sont pas attachés.");
	}
	
	@GetMapping("/show-attach-users")
	public ResponseEntity<?> showAttachUsers(@RequestParam String username)
	{
		List<UtilisateurDto> utilDto = utilService.getUserAttachment(username);
		if(utilDto != null)
			return responseGenerator.SuccessResponse(HttpStatus.OK, utilDto);
		else
			return responseGenerator.ErrorResponse(HttpStatus.BAD_REQUEST, "Les utilisateurs ne sont pas attaches.");
	}
	
	@GetMapping("detach-users")
	public ResponseEntity<?> detachUsers(@RequestParam String usernamePro, @RequestParam String usernameCour)
	{
		boolean result = utilService.getdetachUsers(usernamePro, usernameCour);
		if(result == true)
			return responseGenerator.SuccessResponse(HttpStatus.OK, "Utilisateurs detachés avec succès");
		else
			return responseGenerator.ErrorResponse(HttpStatus.BAD_REQUEST, "Vous ne pouvez pas détacher ces utilisateurs..");
	}
}