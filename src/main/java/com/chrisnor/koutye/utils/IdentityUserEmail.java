package com.chrisnor.koutye.utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;

public class IdentityUserEmail {
	
	public static Map<String, Object> getIdentityUserEmail(UtilisateurDto util)
	{
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("nom", util.getNom());
		obj.put("prenom", util.getPrenom());
		obj.put("username", util.getUsername());
		//obj.put("urlActivation", activationUrl);
		obj.put("copyRightDate", String.valueOf(LocalDate.now().getYear()));
		return obj;
	}
	
	public static Map<String, Object> getIdentityUserEmailPassword(UtilisateurDto user, String password) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("nom", user.getNom());
		obj.put("prenom", user.getPrenom());
		obj.put("username", user.getUsername());
		obj.put("password", password);
		obj.put("copyRightDate", String.valueOf(LocalDate.now().getYear()));
		return obj;
	}
	
	public static Map<String, Object> getIdentityUserEmailAttach(UtilisateurDto userPro, UtilisateurDto userCour) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("nomProprietaire", userPro.getNom());
		obj.put("prenomProprietaire", userPro.getPrenom());
		obj.put("usernameProprietaire", userPro.getUsername());
		
		obj.put("nomCourtier", userCour.getNom());
		obj.put("prenomCourtier", userCour.getPrenom());
		obj.put("usernameCourtier", userCour.getUsername());
	
		obj.put("copyRightDate", String.valueOf(LocalDate.now().getYear()));
		return obj;
	}
}
