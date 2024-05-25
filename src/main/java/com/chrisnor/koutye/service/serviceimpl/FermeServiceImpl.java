package com.chrisnor.koutye.service.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisnor.koutye.dto.FermeDto;
import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.model.Ferme;
import com.chrisnor.koutye.repository.AppartementRepository;
import com.chrisnor.koutye.repository.FermeRepository;
import com.chrisnor.koutye.service.FermeService;

@Service
public class FermeServiceImpl implements FermeService{
    @Autowired
	private AppartementRepository appRepo;
    
    @Autowired
    private FermeRepository fermeRepo;
    
	@Override
	public Ferme EnFermerAppartement(FermeDto fermeDto) {
		Optional<Appartement> app = appRepo.findById(fermeDto.getAppartementId());
		Ferme ferme = new Ferme();
		if(app.isPresent())
		{
			ferme.setAppartement(app.get());
			ferme.setDebut(fermeDto.getDebut());
			ferme.setFin(fermeDto.getFin());
			ferme.setMontant(fermeDto.getMontant());
			fermeRepo.save(ferme);
			return ferme;
		}
		
		return null;
	}

}
