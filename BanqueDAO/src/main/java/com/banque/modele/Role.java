package com.banque.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
// JPA trop peu parametrable @Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "role", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = { "idrole" }))

//Hibernate Tag

public class Role {
	
	@Id
	@Column(name = "idrole", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "labelrole", nullable = false, unique = true)
	private String label;
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
