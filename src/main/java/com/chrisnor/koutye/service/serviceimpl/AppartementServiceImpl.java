package com.chrisnor.koutye.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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
		
		appartement.setImageAppartements(app.getImageAppartements());
		appartement.setVideoAppartements(app.getVideoAppartements());
		
		appRepo.save(appartement);
		
		System.out.println(utilisateur.getNom());
		
		
		return null;
	}
	
	

}
