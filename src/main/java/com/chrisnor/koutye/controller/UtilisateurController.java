package com.chrisnor.koutye.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.dto.LoginDto;
import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.response.Response;
import com.chrisnor.koutye.service.UtilisateurService;

@RestController
@RequestMapping("/api")
public class UtilisateurController {
	
	@Autowired
	UtilisateurService utilService;
	
	@PostMapping("/user/add")
	public ResponseEntity<Response> AjouterUtilisateur(@RequestBody UtilisateurDto utilisateurDto)
	{

			UtilisateurDto util = utilService.PostUtilisateur(utilisateurDto);
			if(util != null)
			{
				Response res = new Response();
				res.setObject(util);
				res.setMessage("Utilisateur créé avec succès");
				return new ResponseEntity<>(res,HttpStatus.CREATED);
			}
			else
			{
				Response res = new Response();
				res.setMessage("Erreur lors de l'insertion de l'utilisateur");
				return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
			}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> Login(@RequestBody LoginDto loginDto)
	{
		UtilisateurDto utilDto = new UtilisateurDto();
		utilDto = utilService.Login(loginDto.getUsername(),loginDto.getPassword());
		
		if(utilDto != null)
		{
			Response res = new Response();
			res.setObject(utilDto);
			res.setMessage("Utilisateur loggé avec succès");
			return new ResponseEntity<>(res,HttpStatus.CREATED);
		}
		else
		{
			Response res = new Response();
			res.setMessage("Erreur lors de la connexion de l'utilisateur");
			return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
		}
	}
}
