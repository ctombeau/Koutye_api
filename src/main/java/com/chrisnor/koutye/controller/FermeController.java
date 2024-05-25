package com.chrisnor.koutye.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.dto.FermeDto;
import com.chrisnor.koutye.model.Ferme;
import com.chrisnor.koutye.response.ResponseGenerator;
import com.chrisnor.koutye.service.FermeService;

@RestController
@RequestMapping("/api")
public class FermeController {
	
	@Autowired
	private FermeService fermeService;
	
	@Autowired
	private ResponseGenerator responseGenerator;
    
	@PostMapping("/ferme/add")
	public ResponseEntity<?> AddFerme(@RequestBody FermeDto fermeDto)
	{
		Ferme ferme = fermeService.EnFermerAppartement(fermeDto);
		if(ferme != null)
			return responseGenerator.SuccessResponse(HttpStatus.CREATED, ferme);
		else
			return responseGenerator.ErrorResponse(HttpStatus.NOT_FOUND, "Cet appartement n'existe pas");
	}
}
