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
import org.springframework.web.multipart.MultipartFile;

import com.chrisnor.koutye.dto.AppartementDto;
import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.repository.AppartementRepository;
import com.chrisnor.koutye.response.Response;
import com.chrisnor.koutye.response.ResponseGenerator;
import com.chrisnor.koutye.service.AppartementService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AppartementController {
	
	private AppartementService appService;
	private AppartementRepository appRepo;
	private ResponseGenerator responseGenerator;
	
	@PostMapping("/appartement/add")
	@PreAuthorize("hasAuthority('SCOPE_Proprietaire')")
	@ResponseBody
	public ResponseEntity<Response> AddAppartement(@RequestBody AppartementDto app)
	{
		
		AppartementDto appDto = appService.addAppartement(app);
		
		if(appDto != null)
		{
			return responseGenerator.SuccessResponse(HttpStatus.CREATED,appDto);
		}
		else
		{
			return responseGenerator.ErrorResponse(HttpStatus.NO_CONTENT, "aucune donnee trouvee");
		}
		
	}
}
