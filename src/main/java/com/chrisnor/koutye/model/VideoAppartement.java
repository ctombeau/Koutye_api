package com.chrisnor.koutye.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.Blob;

import org.antlr.v4.runtime.misc.NotNull;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
public class VideoAppartement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_video")
	private Long idVideo;
	
	//@Lob
    @Basic(fetch = FetchType.LAZY)
	@NotNull
	private String video;
	
	@NotNull
	@Column(name="description_video")
	private String descriptionVideo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="appartement_id")
	private Appartement appartement;
}
