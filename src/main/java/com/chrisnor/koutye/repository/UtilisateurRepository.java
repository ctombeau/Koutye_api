package com.chrisnor.koutye.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import com.chrisnor.koutye.dto.UtilisateurDto;
import com.chrisnor.koutye.model.Utilisateur;

import jakarta.transaction.Transactional;

//@Transactional (propagation = Propagation.REQUIRES_NEW)
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long>{
	public Utilisateur findUtilisateurByUsername(String username);
	public Utilisateur findUtilisateurByEmail(String email);
	public Utilisateur findUtilisateurByUsernameAndPassword(String username, String password);
	
	
	@Modifying
	@Query("update Utilisateur u set u.loginDate = :login_date, u.password=:password, u.actif=:actif where u.email = :email")
	public void firstLoginAfterForgetPassword(@Param("email") String email,@Param("password") String password, @Param("login_date") LocalDateTime login_date, @Param("actif") boolean actif);
 
	@Query(value="SELECT u,r.nomType FROM Utilisateur u left join u.typeUtilisateur r")
    public Page<Utilisateur> findByType(Pageable paging);
}
