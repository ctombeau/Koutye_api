package com.chrisnor.koutye.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;

public interface UtilisateurService {
	public UtilisateurDto PostUtilisateur(UtilisateurDto utilisateurDto);
	public UtilisateurDto PutUtilisateur(Long id,UtilisateurDto utilDto);
	public List<UtilisateurDto> getAllUtilisateurs();
	public Optional<UtilisateurDto> getUtilisateur(String username);
	public UtilisateurDto Login(String username, String password);
	public long FindIdTypeByNomType(String nomType);
	public Map<String, String> GenerateToken(String username, Authentication authentication);
}
