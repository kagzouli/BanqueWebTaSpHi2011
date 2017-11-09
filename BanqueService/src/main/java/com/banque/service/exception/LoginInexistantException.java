package com.banque.service.exception;

public class LoginInexistantException extends BusinessException{
	
	/**
	 * Serial UID.<br/>
	 */
	private static final long serialVersionUID = -4668478096047347916L;

	private String login;
	
	public LoginInexistantException(final String message,final String login){
		super(message);
		this.login = login;
	}
	
	public LoginInexistantException(Throwable exception,final String login){
		super(exception);
		this.login = login;
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
	
	

}
