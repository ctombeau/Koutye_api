package com.chrisnor.koutye.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;

import jakarta.transaction.Transactional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long>{
	public Utilisateur findUtilisateurByUsername(String username);
	public Utilisateur findUtilisateurByEmail(String email);
	public Utilisateur findUtilisateurByUsernameAndPassword(String username, String password);

}
