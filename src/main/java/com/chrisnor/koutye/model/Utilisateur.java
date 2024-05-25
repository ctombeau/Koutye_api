package com.chrisnor.koutye.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Nullable;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Builder
public class Utilisateur implements Serializable{
	
  private static final long serialVersionUID = 1L;

  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="utilisateur_id")
  private Long utilisateurId;
  
  @Column(nullable=false)
  @NotEmpty
  private String nom;
  
  @Column(nullable=false)
  @NotEmpty
  private String prenom;
  
  
  @Column(unique=true, nullable=false)
  @NotEmpty
  private String username;
  
  
  @Column(unique=true, nullable=false)
  @Email(message="veuillez fournir un email correct")
  @NotEmpty
  private String email;
  
  @Column(nullable=false)
  @NotEmpty
  private String password;
  
  @Column(nullable=false)
  @NotEmpty
  private String phone;
  
  @Column(nullable=true, length=64)
  //@Lob
  @Basic(fetch = FetchType.LAZY)
  private String photo;
  
  @Column(nullable=false)
  private boolean actif;
  
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
  
  
  //@JsonIgnore
  @ManyToOne(cascade=CascadeType.ALL)
  @JoinColumn(name="id_type")
  private TypeUtilisateur typeUtilisateur;
  
  
  //@JsonIgnore
  @OneToMany (mappedBy="utilisateur", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<Appartement> appartements;
  
  
}
