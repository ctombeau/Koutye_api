package com.chrisnor.koutye.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisnor.koutye.dto.AppartementDto;
import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Adresse;
import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.model.Ferme;
import com.chrisnor.koutye.model.ImageAppartement;
import com.chrisnor.koutye.model.Utilisateur;
import com.chrisnor.koutye.model.VideoAppartement;
import com.chrisnor.koutye.repository.AdresseRepository;
import com.chrisnor.koutye.repository.AppartementRepository;
import com.chrisnor.koutye.repository.ImageAppartementRepository;
import com.chrisnor.koutye.repository.VideoAppartementRepository;
import com.chrisnor.koutye.service.AppartementService;
import com.chrisnor.koutye.service.UtilisateurService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AppartementServiceImpl implements AppartementService{
	@Autowired
	private AppartementRepository appRepo;
	private AdresseRepository adrRepo;
	private ImageAppartementRepository imgRepo;
	private VideoAppartementRepository videoRepo;
	private UtilisateurService utilService;
	ModelMapper modelMapper;
	private EntityManager em;
	
	@Override
	public Appartement PostAppartement(Appartement app) {
		Appartement appartement = new Appartement();
		ImageAppartement imgApp = new ImageAppartement();
		VideoAppartement videoApp = new VideoAppartement();
		
		
		appartement.setDescription(app.getDescription());
		appartement.setAdresse(app.getAdresse());
		
		Optional<UtilisateurDto> util = utilService.getUtilisateur(app.getUtilisateur().getUsername());
		Utilisateur utilisateur = modelMapper.map(util, Utilisateur.class);
		
		appartement.setUtilisateur(utilisateur);
		
		System.out.println(app.getImageAppartements());
		
		//appartement.setImageAppartements(app.getImageAppartements());
		//appartement.setVideoAppartements(app.getVideoAppartements());
		
		
		app.getImageAppartements().stream().forEach(img-> {
			img.setAppartement(appartement);
//			imgApp.setAppartement(appartement);
//			imgApp.setDescriptionImage(img.getDescriptionImage());
//			imgApp.setImage(img.getImage());
//			System.out.println(imgApp);
			//imgRepo.save(imgApp);
		});
		imgRepo.saveAll(app.getImageAppartements());
		app.getVideoAppartements().stream().forEach(video->{
			video.setAppartement(appartement);
//			videoApp.setAppartement(appartement);
//			videoApp.setDescriptionVideo(video.getDescriptionVideo());
//			videoApp.setVideo(video.getVideo());
//			System.out.println(videoApp);
//			videoRepo.save(videoApp);
		});
		videoRepo.saveAll(app.getVideoAppartements());
		appRepo.save(appartement);
		
		/*
		System.out.println(utilisateur.getNom());
		
		*/
		return null;
	}

	@Override
	public Optional<Appartement> getAppartementByMaxId(Long id) {
		
		return Optional.empty();
	}
	
	

}
