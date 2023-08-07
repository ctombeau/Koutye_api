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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name="type_utilisateur")
public class TypeUtilisateur implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


   @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="id_type")
   private Long idType;
   
 
   @Column(name="nom_type",unique=true, nullable=false)
   private String nomType;
   
   @OneToMany(mappedBy="typeUtilisateur", fetch=FetchType.LAZY)
   private List<Utilisateur> utilisateurs;
}
