package com.chrisnor.koutye.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chrisnor.koutye.dto.AppartementDto;
import com.chrisnor.koutye.file.FileUpload;
import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.model.ImageAppartement;
import com.chrisnor.koutye.model.VideoAppartement;
import com.chrisnor.koutye.repository.AppartementRepository;
import com.chrisnor.koutye.response.Response;
import com.chrisnor.koutye.response.ResponseGenerator;
import com.chrisnor.koutye.service.AppartementService;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@EnableMethodSecurity
public class AppartementController {
	
	private AppartementService appService;
	private AppartementRepository appRepo;
	private ResponseGenerator responseGenerator;
	
	@PostMapping("/appartement/add")
	@PreAuthorize("hasAuthority('SCOPE_Proprietaire')")
	//@ResponseBody
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
	
	@PostMapping("/appartement/add-image")
	public ResponseEntity<?> addImageAppartement(@RequestParam Long idApp, @RequestParam List<MultipartFile> images)
	{
		String directory = "C:/Koutye_Folder/ImageApp/"+idApp;
		System.out.println(directory);
		List<String> paths = new FileUpload().UploadAllFiles(images, directory);
		
		// paths.forEach(p->p.replace("\\", "/"));
		
		List<ImageAppartement> imgSave = appService.postImageAppartement(idApp, paths);
		if(imgSave != null)
			return responseGenerator.SuccessResponse(HttpStatus.OK,imgSave);
		else
			return responseGenerator.ErrorResponse(HttpStatus.NO_CONTENT, "Path non insere");
	
	}
	
	@PostMapping("/appartement/add-video")
	public ResponseEntity<?> addVideoAppartement(@RequestParam Long idApp, @RequestParam List<MultipartFile> videos)
	{
		String directory = "C:/Koutye_Folder/VideoApp/"+idApp;
		System.out.println(directory);
		List<String> paths = new FileUpload().UploadAllFiles(videos, directory);
		
		// paths.forEach(p->p.replace("\\", "/"));
		
		List<VideoAppartement> videoSave = appService.postVideoAppartement(idApp, paths);
		if(videoSave != null)
			return responseGenerator.SuccessResponse(HttpStatus.OK,videoSave);
		else
			return responseGenerator.ErrorResponse(HttpStatus.NO_CONTENT, "Path non insere");
	}
	
	@GetMapping("/appartement/delete-image/{id}")
	public ResponseEntity<?> removeImage(@PathVariable Long id)
	{	
		boolean result = appService.deleteImage(id);
		if(result)
			return responseGenerator.SuccessResponse(HttpStatus.OK,result);
		else
			return responseGenerator.ErrorResponse(HttpStatus.NO_CONTENT, "Image non supprimee");
		
	}
	
	@GetMapping("/appartement/delete-video/{id}")
	public ResponseEntity<?> removeVideo(@PathVariable Long id)
	{
		boolean result = appService.deleteVideo(id);
		if(result)
			return responseGenerator.SuccessResponse(HttpStatus.OK,result);
		else
			return responseGenerator.ErrorResponse(HttpStatus.NO_CONTENT, "Video non supprimee");
	}
}
