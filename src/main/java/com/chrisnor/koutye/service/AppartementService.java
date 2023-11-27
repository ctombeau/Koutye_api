package com.chrisnor.koutye.service;

import org.springframework.stereotype.Service;

import com.chrisnor.koutye.dto.AppartementDto;
import com.chrisnor.koutye.model.Appartement;


public interface AppartementService {
	public Appartement PostAppartement(Appartement app);
}
