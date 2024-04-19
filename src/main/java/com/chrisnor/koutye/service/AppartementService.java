package com.chrisnor.koutye.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chrisnor.koutye.dto.AppartementDto;
import com.chrisnor.koutye.model.Appartement;


public interface AppartementService {
	public AppartementDto PostAppartement(Appartement app);
	public Optional<Appartement> getAppartementByMaxId(Long id);
	public AppartementDto addAppartement(AppartementDto appDto);
	public List<Appartement> getAppartementByUsername(String username);
	public List<Appartement> getAppartementByCommune(String commune);
}
