package com.banque.service.exception;

public class TechnicalException extends RuntimeException{
	
	/**
	 * Serial UID.<br/>
	 */
	private static final long serialVersionUID = 4666842074479277337L;

	public TechnicalException(String message){
		super(message);
	}
	
	public TechnicalException(Throwable exception){
		super(exception);
	}

}
