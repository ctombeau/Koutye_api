package com.chrisnor.koutye.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisnor.koutye.model.Attachement;

@Repository
public interface AttachementRepository extends JpaRepository<Attachement, Long>{

}
