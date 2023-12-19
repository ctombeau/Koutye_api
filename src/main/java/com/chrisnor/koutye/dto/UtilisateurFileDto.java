package com.chrisnor.koutye.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.model.TypeUtilisateur;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurFileDto {
	 
	  private Long utilisateurId;
	  
	  private String nom;
	  
	  private String prenom;
	  
	  private String username;
	 
	  private String email;
	  
	  private String password;
	  
	  private String phone;
	  
	  private MultipartFile photo;
	  
	  private TypeUtilisateur typeUtilisateur;
	  
}
