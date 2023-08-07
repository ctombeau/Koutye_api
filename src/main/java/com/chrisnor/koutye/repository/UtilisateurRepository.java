package com.chrisnor.koutye.repository;

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
//	@Transactional
//	//@Modifying
//	@Query(value = "insert into utilisateur(nom,prenom,email,username,password,phone,photo,id_type)values"
//			+ "(?,?,?,?,?,?,?,?)",
//			nativeQuery = true)
//	Utilisateur saveUtilisateur(String nom, String prenom,String email, String username, String passwword, String phone, String photo,Long idType);

}
