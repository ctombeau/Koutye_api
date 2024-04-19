package com.chrisnor.koutye.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
public class Appartement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="appartement_id")
	private Long AppartementId;
	
	@NotNull
	private String description;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="utilisateur_id")
	private Utilisateur utilisateur;
	
	@JsonBackReference
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="adresse_id")
	private Adresse adresse;
	
	@OneToOne(mappedBy="appartement")
	private Ferme ferme;
	
	 @JsonManagedReference
	@OneToMany(mappedBy="appartement", fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
	private List<ImageAppartement> imageAppartements;
	
	@JsonManagedReference
	@OneToMany(mappedBy="appartement", fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
	private List<VideoAppartement> videoAppartements;
}
