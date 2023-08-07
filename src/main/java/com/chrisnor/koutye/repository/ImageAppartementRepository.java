package com.chrisnor.koutye.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.ImageAppartement;

@Repository
public interface ImageAppartementRepository extends CrudRepository<ImageAppartement, Long> {

}
