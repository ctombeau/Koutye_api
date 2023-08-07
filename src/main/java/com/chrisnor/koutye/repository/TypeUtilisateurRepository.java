package com.chrisnor.koutye.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.TypeUtilisateur;

@Repository
public interface TypeUtilisateurRepository extends CrudRepository<TypeUtilisateur, Long> {
	TypeUtilisateur findByNomType(String nomType);
}
