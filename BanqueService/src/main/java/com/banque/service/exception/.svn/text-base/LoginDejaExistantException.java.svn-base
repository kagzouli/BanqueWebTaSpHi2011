package com.banque.service.exception;

public class LoginDejaExistantException extends BusinessException {

	private static final long serialVersionUID = 2757551262806799753L;

	private String login;

	public LoginDejaExistantException(final String message, final String login) {
		super(message);
		this.login = login;
	}

	public LoginDejaExistantException(Throwable exception, final String login) {
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
