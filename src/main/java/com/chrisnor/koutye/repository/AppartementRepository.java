package com.chrisnor.koutye.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.Adresse;
import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.model.Utilisateur;

@Repository
public interface AppartementRepository extends CrudRepository<Appartement, Long> {
	public Appartement findByAdresse(Adresse adr);
	public List<Appartement> findByUtilisateur(Utilisateur util);
	
	@Modifying
	@Query(
			  value = "INSERT INTO appartement (adresse_id, utilisateur_id,description) "
			  		+ "VALUES(?1,?2,?3)", 
			  nativeQuery = true)
	public int saveAppartement(@Param("adresse_id") Long adresse_id, @Param("utilisateur_id") Long utilisateur_id, @Param("description") String description);

    @Query(value="SELECT a.appartement_id,a.description,adr.numero, adr.commune, adr.departement, adr.pays, adr.rue,\r\n"
    		+ "  u.nom,u.prenom,u.username,u.email from appartement a\r\n"
    		+ "  inner join utilisateur u \r\n"
    		+ "  on a.utilisateur_id = u.utilisateur_id\r\n"
    		+ "  inner join adresse adr\r\n"
    		+ "  on adr.id_adresse=a.adresse_id",nativeQuery=true)
	public Appartement getAppartement(@Param("commune") String commune);

}
