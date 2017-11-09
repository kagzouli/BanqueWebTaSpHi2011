package com.banque.modele;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Classe representant le compte d'un utilisateur (Donne le solde de ce compte).<br/>
 * @author Karim
 *
 */
@Entity
@Table(name = "banque", schema = "", uniqueConstraints = @UniqueConstraint(columnNames = { "login" }))
public class EtatCompte implements Serializable {

	private static final long serialVersionUID = 54545448787L;

	/** Login du compte **/
	@Id
	@Column(name = "login", nullable = false, unique = true, length = 150)
	private String login;

	/** Montant du compte **/
	@Column(name = "montant", nullable = false, unique = false)
	private BigDecimal montant;
	
	/**
	 * Constructeur par default.<br/>
	 */
	public EtatCompte(){
		super();
	}
	
	/**
	 * Constructeur.<br/>
	 * @param login Le login.<br/>
	 * @param montant Le montant.<br/>
	 */
	public EtatCompte(String login,BigDecimal montant){
		this.login = login;
		this.montant = montant;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the montant
	 */
	public BigDecimal getMontant() {
		return montant;
	}

	/**
	 * @param montant the montant to set
	 */
	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	@Override
	public boolean equals(Object obj) {
		// Vérification de l'égalité des références
		if (obj == this) {
			return true;
		}

		// Vérification du type du paramètre
		if (obj instanceof EtatCompte) {
			EtatCompte other = (EtatCompte) obj;
			return new EqualsBuilder().append(this.login, other.login).isEquals();
		}

		return false;

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.login).toHashCode();
	}

}
