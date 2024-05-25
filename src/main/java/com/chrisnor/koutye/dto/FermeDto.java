package com.chrisnor.koutye.dto;

import java.sql.Date;

import org.antlr.v4.runtime.misc.NotNull;

import com.chrisnor.koutye.model.Appartement;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FermeDto {
	
	private Long idFerme;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date debut;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date fin;
	
	@NotNull
	private double montant;
	
	private Long appartementId;
}
