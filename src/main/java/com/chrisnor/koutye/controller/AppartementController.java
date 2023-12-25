package com.chrisnor.koutye.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chrisnor.koutye.dto.AppartementDto;
import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.response.Response;
import com.chrisnor.koutye.response.ResponseGenerator;
import com.chrisnor.koutye.service.AppartementService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AppartementController {
	
	private AppartementService appService;
	private ResponseGenerator responseGenerator;
	
	@PostMapping("appartement/add")
	@PreAuthorize("hasAuthority('SCOPE_Proprietaire')")
	@ResponseBody
	public ResponseEntity<Response> AddAppartement(@RequestPart(required=true) Appartement appartement)
	{
		System.out.println(appartement);
		/*
		if(appartement != null)
		{
			AppartementDto appDto=appService.PostAppartement(appartement);
			return responseGenerator.SuccessResponse(H ttpStatus.CREATED,appDto);
		}
		else
		{
			return responseGenerator.ErrorResponse(HttpStatus.NO_CONTENT, "aucune donnee trouvee");
		}
		*/
		return null;
		
	}
}
