package com.banque.service.exception;

import java.math.BigDecimal;

public class PlafondMaxAtteintException extends BusinessException{
	
	/**
	 * Serial UID.<br/>
	 */
	private static final long serialVersionUID = 4144310517584373910L;
	
	private BigDecimal plafond;
	
	public PlafondMaxAtteintException(final String message,final BigDecimal plafond){
		super(message);
		this.plafond = plafond;
	}
	
	public PlafondMaxAtteintException(Throwable exception,final BigDecimal plafond){
		super(exception);
		this.plafond = plafond;
	}

	/**
	 * @return the plafond
	 */
	public BigDecimal getPlafond() {
		return plafond;
	}

	/**
	 * @param plafond the plafond to set
	 */
	public void setPlafond(BigDecimal plafond) {
		this.plafond = plafond;
	}

	
}
