package com.chrisnor.koutye.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
public class Adresse implements Serializable{
   @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="id_adresse")
   private Long idAdresse;
   
   @NotNull
   private int numero;
   
   @NotNull
   private String rue;
   
   @NotNull
   private String commune;
   
   @NotNull
   private String departement;
   
   @NotNull
   private String pays;
   
   @OneToMany(mappedBy="adresse", fetch=FetchType.LAZY)
   private List<Appartement> appartements;
}
