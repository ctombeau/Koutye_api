package com.chrisnor.koutye.service;

import java.util.List;
import java.util.Optional;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;

public interface UtilisateurService {
	public UtilisateurDto PostUtilisateur(UtilisateurDto utilisateurDto);
	public UtilisateurDto PutUtilisateur(Long id,UtilisateurDto utilDto);
	public List<UtilisateurDto> getAllUtilisateurs();
	public Optional<UtilisateurDto> getUtilisateur(String username);
	public UtilisateurDto Login(String username, String password);
	public long FindIdTypeByNomType(String nomType);
}
