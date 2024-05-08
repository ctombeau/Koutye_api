package com.chrisnor.koutye.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chrisnor.koutye.dto.AppartementDto;
import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.model.ImageAppartement;
import com.chrisnor.koutye.model.VideoAppartement;


public interface AppartementService {
	public AppartementDto PostAppartement(Appartement app);
	public Optional<Appartement> getAppartementByMaxId(Long id);
	public AppartementDto addAppartement(AppartementDto appDto);
	public List<Appartement> getAppartementByUsername(String username);
	public List<Appartement> getAppartementByCommune(String commune);
	public List<ImageAppartement> postImageAppartement(Long idApp,List<String> paths);
	public List<VideoAppartement> postVideoAppartement(Long idApp,List<String> paths);
	public boolean deleteImage(Long id);
	public boolean deleteVideo(Long id);
}
