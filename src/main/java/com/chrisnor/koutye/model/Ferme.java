package com.chrisnor.koutye.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.Date;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
public class Ferme implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ferme")
	private Long idFerme;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date debut;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date fin;
	
	@NotNull
	private double montant;
	
	@OneToOne
	@JoinColumn(name="appartement_id")
	private Appartement appartement;
	
}
