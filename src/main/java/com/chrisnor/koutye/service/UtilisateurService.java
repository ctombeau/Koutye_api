package com.chrisnor.koutye.service;

import java.util.List;
import java.util.Optional;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;

public interface UtilisateurService {
	public UtilisateurDto PostUtilisateur(UtilisateurDto utilisateurDto);
	public void PutUtilisateur(Utilisateur utilisateur);
	public List<UtilisateurDto> getAllUtilisateurs();
	public Optional<Utilisateur> getUtilisateur(String username);
	public UtilisateurDto Login(String username, String password);
	public long FindIdTypeByNomType(String nomType);
}
