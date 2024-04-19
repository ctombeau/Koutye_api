package com.chrisnor.koutye.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.Adresse;

@Repository
public interface AdresseRepository extends CrudRepository<Adresse, Long> {
   public List<Adresse> findByCommune(String commune);
}
