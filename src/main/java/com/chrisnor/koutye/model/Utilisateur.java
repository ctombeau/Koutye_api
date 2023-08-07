package com.chrisnor.koutye.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity

public class Utilisateur implements Serializable{
	
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="utilisateur_id")
  private Long utilisateurId;
  
  @Column(nullable=false)
  private String nom;
  
  @Column(nullable=false)
  private String prenom;
  
  
  @Column(unique=true, nullable=false)
  private String username;
  
  
  @Column(unique=true, nullable=false)
  private String email;
  
  @Column(nullable=false)
  private String password;
  
  @Column(nullable=false)
  private String phone;
  
  @Column(nullable=false)
  private String photo;
  
  @ManyToOne(cascade=CascadeType.ALL)
  @JoinColumn(name="id_type")
  private TypeUtilisateur typeUtilisateur;
  
  @OneToMany (mappedBy="utilisateur", fetch=FetchType.LAZY)
  private List<Appartement> appartements;
}
