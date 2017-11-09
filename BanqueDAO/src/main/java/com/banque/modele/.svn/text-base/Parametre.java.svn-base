package com.banque.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "parametre", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = { "code" }))
public class Parametre {
	
	/** Nombre de points a partir duquel on affiche le graphique **/
	public static final String NBPTAFFGRA = "NBPTAFFGRA";
	
	/** Nombre de point maximum courbe graphique **/
	public static final String NBPTMAXCOUGRA = "NBPTMAXCOUGRA";
	
	/** Identifiant du parametre **/
	@Id
	@Column(name = "code", nullable = false, unique = true)
	private String code;
	
	@Column(name = "label", nullable = false, unique = false)
	private String label;

	@Column(name = "description", nullable = true, unique = false)
	private String description;

	@Column(name = "valeur", nullable = false, unique = false)
	private Integer valeur;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getValeur() {
		return valeur;
	}

	public void setValeur(Integer valeur) {
		this.valeur = valeur;
	}
	
	

}
