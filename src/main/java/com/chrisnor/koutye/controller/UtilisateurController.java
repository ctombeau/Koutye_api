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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.chrisnor.koutye.dto.LoginDto;
import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.file.FileUpload;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.response.Response;
import com.chrisnor.koutye.service.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	private AuthenticationManager authenticationManager;

	@PostMapping("/user/add")
	@PreAuthorize("hasAuthority('SCOPE_USER')")
	public ResponseEntity<Response> AjouterUtilisateur(@RequestParam MultipartFile photo, @RequestParam String model)
			throws Exception {
		String retour;
		ObjectMapper mapper = new ObjectMapper();
		UtilisateurDto utilisateurDto = mapper.readValue(model, UtilisateurDto.class);

		retour = new FileUpload().UploadFiles(photo, userFolder + utilisateurDto.getUsername());
		if (retour != null)
			utilisateurDto.setPhoto(retour.replace("\\", "/"));

		UtilisateurDto util = utilService.PostUtilisateur(utilisateurDto);
		if (util != null) {
			Response res = new Response();
			res.setObject(util);
			res.setMessage("Utilisateur créé avec succès");
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} else {
			Response res = new Response();
			res.setMessage("Erreur lors de l'insertion de l'utilisateur");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}

	/*
	@PostMapping("/login")
	public ResponseEntity<Response> Login(@RequestBody LoginDto loginDto) {
		UtilisateurDto utilDto = new UtilisateurDto();
		utilDto = utilService.Login(loginDto.getUsername(), loginDto.getPassword());

		if (utilDto != null) {
			Response res = new Response();
			res.setObject(utilDto);
			res.setMessage("Utilisateur loggé avec succès");
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		} else {
			Response res = new Response();
			res.setMessage("Le nom d'utilisateur et/ou le mot de passe est incorrect.");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}
	}
	
	*/
	
	@PostMapping("/login")
	public Map<String, String> Login(@RequestBody LoginDto loginDto)
	{
  
		Authentication authentication = authenticationManager.authenticate(
				 new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
				);
		
		return utilService.GenerateToken(loginDto.getUsername(),authentication);
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

	@GetMapping("/users")
	public ResponseEntity<Response> ListUtilisateurs() {
		List<UtilisateurDto> utilisateurs = utilService.getAllUtilisateurs();
		Response res = new Response();
		res.setMessage("Liste des utilisateurs");
		res.setObject(utilisateurs);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
