package com.chrisnor.koutye.dto;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.model.TypeUtilisateur;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.annotation.Nullable;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurDto {
	
	  private Long utilisateurId;
	  
	  @Column(nullable=false)
	  private String nom;
	  
	  @Column(nullable=false)
	  private String prenom;
	  
	  @Column(unique=true, nullable=false)
	  private String username;
	  
	  @Column(unique=true, nullable=false)
	  @Email(message="veuillez fournir un email correct")
	  private String email;
	  
	  @JsonProperty(access = Access.WRITE_ONLY)
	  @Column(nullable=false)
	  private String password;
	  
	  @Column(nullable=false)
	  private String phone;
	  
	  @Column(nullable=false)
	  private boolean actif;
	  
	  //@Basic(fetch = FetchType.LAZY)
	  @Column(nullable=true)
	  private String photo;
	  
	  @Column(nullable=false)
	  private String nomType;
	  
	  @Temporal(TemporalType.TIMESTAMP)
	  @Column(name="creation_date",nullable=true)
	  private LocalDateTime creationDate;
	  
	  @Temporal(TemporalType.TIMESTAMP)
	  @Column(name="modification_date",nullable=true)
	  private LocalDateTime modificationDate;
	  
	  @Temporal(TemporalType.TIMESTAMP)
	  @Column(name="login_date",nullable=true)
	  private LocalDateTime loginDate;
	  
	  @Temporal(TemporalType.TIMESTAMP)
	  @Column(name="logout_date",nullable=true)
	  private LocalDateTime logoutDate;
	  
//	  @ManyToOne(cascade=CascadeType.ALL)
//	  @JoinColumn(name="id_type")
//	  private TypeUtilisateur typeUtilisateur;
//	  
//	  @OneToMany (mappedBy="utilisateur", fetch=FetchType.LAZY)
//	  private List<Appartement> appartements;
}
