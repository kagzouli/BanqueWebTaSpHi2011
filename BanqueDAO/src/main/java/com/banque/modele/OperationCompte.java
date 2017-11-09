package com.banque.modele;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "operationcompte", schema = "")

public class OperationCompte {

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idCompte;
	
	@Column(name = "login", nullable = false, unique = false, length = 150)
	private String login;
	
	
	@Column(name = "libelleOperation", nullable = false, unique = false, length = 150)	
	private String libelleOperation;

	@Column(name = "montantCredite", nullable = true, unique = false)
	private BigDecimal montantCredite;

	@Column(name = "montantDebite", nullable = true, unique = false)
	private BigDecimal montantDebite;

	@Column(name = "dateOperation", nullable = false, unique = false)
	private Date dateOperation;

	
	@Column(name = "montantGlobal", nullable = false, unique = false)
	private BigDecimal montantGlobal;
	
	/**
	 * Constructeur par default.<br/>
	 */
	public OperationCompte(){
		super();
	}
	
	/**
	 * Constructeur.<br/>
	 * @param login Le login.<br/>
	 * @param libelleOperation Le libelle de l'operation.<br/>
	 * @param montantGlobal Le montant global.<br/>
	 */
	public OperationCompte(String login,String libelleOperation,BigDecimal montantGlobal){
		this.login = login;
		this.libelleOperation = libelleOperation;
		this.montantGlobal = montantGlobal;
	}


	public Integer getIdCompte() {
		return idCompte;
	}


	public void setIdCompte(Integer idCompte) {
		this.idCompte = idCompte;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getLibelleOperation() {
		return libelleOperation;
	}


	public void setLibelleOperation(String libelleOperation) {
		this.libelleOperation = libelleOperation;
	}


	public BigDecimal getMontantCredite() {
		return montantCredite;
	}


	public void setMontantCredite(BigDecimal montantCredite) {
		this.montantCredite = montantCredite;
	}


	public BigDecimal getMontantDebite() {
		return montantDebite;
	}


	public void setMontantDebite(BigDecimal montantDebite) {
		this.montantDebite = montantDebite;
	}


	public BigDecimal getMontantGlobal() {
		return montantGlobal;
	}
	
	public Date getDateOperation() {
		return dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public void setMontantGlobal(BigDecimal montantGlobal) {
		this.montantGlobal = montantGlobal;
	}
	
	@Override
	public boolean equals(Object obj) {
		// Vérification de l'égalité des références
		if (obj == this) {
			return true;
		}

		// Vérification du type du paramètre
		if (obj instanceof OperationCompte) {
			OperationCompte other = (OperationCompte) obj;
			return new EqualsBuilder().append(this.login, other.login).isEquals();
		}

		return false;

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(14, 44).append(this.login).toHashCode();
	}


	
	

}
