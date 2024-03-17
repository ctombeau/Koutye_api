package com.chrisnor.koutye.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AttachementCompositeId.class)
public class Attachement {
	@Id
	@Column(name="proprietaire_id")
    private Long proprietaireId;
	
	@Id
	@Column(name="courtier_id")
    private Long courtierId;
}
