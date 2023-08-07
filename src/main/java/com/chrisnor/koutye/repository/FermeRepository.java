package com.chrisnor.koutye.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.Ferme;

@Repository
public interface FermeRepository extends CrudRepository<Ferme, Long>{

}
