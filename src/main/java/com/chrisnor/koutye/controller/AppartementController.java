package com.chrisnor.koutye.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.response.Response;
import com.chrisnor.koutye.service.AppartementService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AppartementController {
	
	private AppartementService appService;
	
	@PostMapping("appartement/add")
	@PreAuthorize("hasAuthority('SCOPE_Proprietaire')")
	public ResponseEntity<Response> AddAppartement(@RequestBody Appartement appartement)
	{
		System.out.println(appartement);
		
		Appartement app=appService.PostAppartement(appartement);
		
		Response res = new Response();
		res.setObject(app);
		return new ResponseEntity<>(res, HttpStatus.CREATED );
	}
}
