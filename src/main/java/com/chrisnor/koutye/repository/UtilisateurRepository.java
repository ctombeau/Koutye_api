package com.chrisnor.koutye.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;

import jakarta.transaction.Transactional;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur,Long>{
	public Utilisateur findUtilisateurByUsername(String username);
	public Utilisateur findUtilisateurByEmail(String email);
	public Utilisateur findUtilisateurByUsernameAndPassword(String username, String password);

}
