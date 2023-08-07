package com.chrisnor.koutye.dto;

import java.sql.Blob;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.chrisnor.koutye.model.Appartement;
import com.chrisnor.koutye.model.TypeUtilisateur;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	  @Column(nullable=false)
	  private String nom;
	  
	  @Column(nullable=false)
	  private String prenom;
	  
	  @Column(unique=true, nullable=false)
	  private String username;
	  
	  @Column(unique=true, nullable=false)
	  private String email;
	  
	  @JsonProperty(access = Access.WRITE_ONLY)
	  @Column(nullable=false)
	  private String password;
	  
	  @Column(nullable=false)
	  private String phone;
	  
	  @Column(nullable=false)
	  private String photo;
	  
	  @Column(nullable=false)
	  private String nomType;
	  
//	  @ManyToOne(cascade=CascadeType.ALL)
//	  @JoinColumn(name="id_type")
//	  private TypeUtilisateur typeUtilisateur;
//	  
//	  @OneToMany (mappedBy="utilisateur", fetch=FetchType.LAZY)
//	  private List<Appartement> appartements;
}
