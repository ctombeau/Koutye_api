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
}
