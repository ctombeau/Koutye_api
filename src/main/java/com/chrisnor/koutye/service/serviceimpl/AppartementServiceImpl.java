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
//@AllArgsConstructor
@Transactional
public class AppartementServiceImpl implements AppartementService{
	@Autowired
	private AppartementRepository appRepo;
	@Autowired
	private AdresseRepository adrRepo;
	@Autowired
	private ImageAppartementRepository imgRepo;
	@Autowired
	private VideoAppartementRepository videoRepo;
	@Autowired
	private UtilisateurService utilService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private EntityManager em;
	
	@Override
	public AppartementDto PostAppartement(Appartement app) {
		Appartement appartement = new Appartement();
		ImageAppartement imgApp = new ImageAppartement();
		VideoAppartement videoApp = new VideoAppartement();
		
		try
		{
			appartement.setDescription(app.getDescription());
			appartement.setAdresse(app.getAdresse());
		
			Optional<UtilisateurDto> util = utilService.getUtilisateur(app.getUtilisateur().getUsername());
			Utilisateur utilisateur = modelMapper.map(util, Utilisateur.class);
		
			appartement.setUtilisateur(utilisateur);
		
			app.getImageAppartements().stream().forEach(img-> {
				img.setAppartement(appartement);

			});
			imgRepo.saveAll(app.getImageAppartements());
			
			app.getVideoAppartements().stream().forEach(video->{
				video.setAppartement(appartement);
			});
			videoRepo.saveAll(app.getVideoAppartements());
				appRepo.save(appartement);
		   return modelMapper.map(app, AppartementDto.class);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return null;
		}
	}

	@Override
	public Optional<Appartement> getAppartementByMaxId(Long id) {
		
		return Optional.empty();
	}

	@Override
	public AppartementDto addAppartement(AppartementDto app) {
		Optional<UtilisateurDto> util = utilService.getUtilisateur(app.getUsername());
		Utilisateur utilisateur = modelMapper.map(util, Utilisateur.class);
		
		Adresse adr = adrRepo.save(app.getAdresse());
		
		Long adresse_id = adr.getIdAdresse();
		Long utilisateur_id = utilisateur.getUtilisateurId();
		String description = app.getDescription();
		
		int response = appRepo.saveAppartement( adresse_id, utilisateur_id, description);
		if(response > 0)
			return modelMapper.map(app, AppartementDto.class);
		else
			return null;
	}

	@Override
	public List<Appartement> getAppartementByUsername(String username) {
		Optional<UtilisateurDto> utilDto = utilService.getUtilisateur(username);
		if(utilDto.isPresent() && !utilDto.get().getNomType().equals("Locataire"))
		{
			Utilisateur util = modelMapper.map(utilDto, Utilisateur.class);
			List<Appartement> apps = appRepo.findByUtilisateur(util);
			return apps;
		}
		return null;
	}

	@Override
	public List<Appartement> getAppartementByCommune(String commune) {
		List<Adresse> adr = adrRepo.findByCommune(commune);
		List<Appartement> apps = new ArrayList<>();
		adr.forEach(a->{
			Appartement app = appRepo.findByAdresse(a);
			apps.add(app);
			System.out.println(app.getAppartementId());
		});
		
		return apps;
	}

	@Override
	public List<ImageAppartement> postImageAppartement(Long idApp, List<String> paths) {
		List<ImageAppartement> imgApps = new ArrayList<>();
		Optional<Appartement> app = appRepo.findById(idApp);
		
		if(app.isPresent())
		{
			paths.forEach(path->{
				ImageAppartement imgApp = new ImageAppartement();
				imgApp.setAppartement(app.get());
				imgApp.setImage(path);
				ImageAppartement imgAppSave = imgRepo.save(imgApp);
				imgApps.add(imgAppSave);
			});
		}
		
		return imgApps;
	}

	@Override
	public List<VideoAppartement> postVideoAppartement(Long idApp, List<String> path) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
