package com.chrisnor.koutye.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.Appartement;

@Repository
public interface AppartementRepository extends CrudRepository<Appartement, Long> {
	
	@Modifying
	@Query(
			  value = "INSERT INTO appartement (adresse_id, utilisateur_id,description) "
			  		+ "VALUES(?1,?2,?3)", 
			  nativeQuery = true)
	public int saveAppartement(@Param("adresse_id") Long adresse_id, @Param("utilisateur_id") Long utilisateur_id, @Param("description") String description);

}
