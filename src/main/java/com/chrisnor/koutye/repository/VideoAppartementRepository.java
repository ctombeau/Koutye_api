package com.chrisnor.koutye.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.VideoAppartement;

@Repository
public interface VideoAppartementRepository extends CrudRepository<VideoAppartement, Long> {
	 @Modifying
	 @Query(value="delete from video_appartement where id_video=:id",nativeQuery = true)
	 public void deleteVideoById(@Param("id") Long id);
}
