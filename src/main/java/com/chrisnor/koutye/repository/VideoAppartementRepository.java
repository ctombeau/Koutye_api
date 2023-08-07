package com.chrisnor.koutye.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.VideoAppartement;

@Repository
public interface VideoAppartementRepository extends CrudRepository<VideoAppartement, Long> {

}
