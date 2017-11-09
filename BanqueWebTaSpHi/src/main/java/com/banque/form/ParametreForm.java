package com.banque.form;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.beaneditor.Validate;

public class ParametreForm {
	
	@Validate("required,maxlength=20")
	private String code;

	@Validate("required,maxlength=96")
	private String label;
	
	@Validate("maxlength=512")
	private String description;
	
	@Component
	@Validate("required,maxlength=5")
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
