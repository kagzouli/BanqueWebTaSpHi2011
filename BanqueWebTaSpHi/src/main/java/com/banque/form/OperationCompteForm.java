package com.banque.form;

import java.math.BigDecimal;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.beaneditor.Validate;

public class OperationCompteForm {

	@Validate("required,minlength=4,maxlength=15")
	private String loginEmetteur;

	@Validate("required,minlength=4,maxlength=15")
	private String loginReceveur;

	@Validate("required,minlength=4,maxlength=70")
	private String libelleOperation;
	
	@Component
	@Validate("required,maxlength=10")
	private BigDecimal montant;

	/**
	 * @return the loginEmetteur
	 */
	public String getLoginEmetteur() {
		return loginEmetteur;
	}

	/**
	 * @param loginEmetteur the loginEmetteur to set
	 */
	public void setLoginEmetteur(String loginEmetteur) {
		this.loginEmetteur = loginEmetteur;
	}

	/**
	 * @return the loginReceveur
	 */
	public String getLoginReceveur() {
		return loginReceveur;
	}

	/**
	 * @param loginReceveur the loginReceveur to set
	 */
	public void setLoginReceveur(String loginReceveur) {
		this.loginReceveur = loginReceveur;
	}
	
	

	public String getLibelleOperation() {
		return libelleOperation;
	}

	public void setLibelleOperation(String libelleOperation) {
		this.libelleOperation = libelleOperation;
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

}
