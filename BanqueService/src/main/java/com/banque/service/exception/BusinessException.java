package com.banque.service.exception;

public class BusinessException extends Exception{
	
	/**
	 * Serial UID.<br/>
	 */
	private static final long serialVersionUID = 4102300252724803530L;

	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(Throwable exception){
		super(exception);
	}

}
