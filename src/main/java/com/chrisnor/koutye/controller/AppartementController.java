package com.chrisnor.koutye.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/appartement/show-by-username")
	public ResponseEntity<?> showAppartementByUsername(@RequestParam String username)
	{
		List<Appartement> apps = appService.getAppartementByUsername(username);
		if(apps != null)
			return responseGenerator.SuccessResponse(HttpStatus.OK,apps);
		else
			return responseGenerator.ErrorResponse(HttpStatus.NO_CONTENT, "aucune donnee trouvee");
	}
	
	@GetMapping("/appartement/show-by-commune")
	public ResponseEntity<?> showAppartementByCommune(@RequestParam String commune)
	{
		List<Appartement> apps = appService.getAppartementByCommune(commune);
		if(apps != null)
			return responseGenerator.SuccessResponse(HttpStatus.OK,apps);
		else
			return responseGenerator.ErrorResponse(HttpStatus.NO_CONTENT, "aucune donnee trouvee");
	}
}
