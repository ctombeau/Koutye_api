package com.chrisnor.koutye.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.ImageAppartement;

@Repository
public interface ImageAppartementRepository extends CrudRepository<ImageAppartement, Long> {
     
	 @Modifying
	 @Query(value="delete from image_appartement where id_image=:id",nativeQuery = true)
	 public void deleteImageById(@Param("id") Long id);
}
