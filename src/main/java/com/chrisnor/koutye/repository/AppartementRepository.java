package com.chrisnor.koutye.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.Appartement;

@Repository
public interface AppartementRepository extends CrudRepository<Appartement, Long> {

}
