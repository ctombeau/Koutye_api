package com.chrisnor.koutye.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.Attachement;

@Repository
public interface AttachementRepository extends JpaRepository<Attachement, Long>{
    
	@Query("Select a.courtierId from Attachement a where a.proprietaireId = :idProprietaire")
	public List<Long> findIdCourtiersByIdProprietaire(@Param("idProprietaire") Long idProprietaire);
	
	@Modifying
	@Query("delete from Attachement a where a.proprietaireId=:idProprietaire and a.courtierId=:idCourtier")
	public int deleteAttachUser(@Param("idProprietaire") Long idProprietaire, @Param("idCourtier") Long idCourtier);
}
