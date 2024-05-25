package com.chrisnor.koutye.service;

import com.chrisnor.koutye.dto.FermeDto;
import com.chrisnor.koutye.model.Ferme;

public interface FermeService {
    Ferme EnFermerAppartement(FermeDto fermeDto);
}
