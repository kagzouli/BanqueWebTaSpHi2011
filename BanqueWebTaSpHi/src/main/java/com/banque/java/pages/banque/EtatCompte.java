package com.banque.java.pages.banque;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.banque.form.EtatCompteForm;
import com.banque.java.pages.Index;
import com.banque.service.IBanque;
import com.banque.service.exception.LoginInexistantException;
import com.banque.service.exception.TechnicalException;

public class EtatCompte {

	//@Persist(PersistenceConstants.FLASH)
	@Persist(PersistenceConstants.SESSION)
	private String login;

	@Inject
	private IBanque banqueService;

	@Inject
	private Messages messages;

	@Property
	private EtatCompteForm etatCompteForm;

	private static final String MESSAGE_NOT_FOUND_LOGIN = "login.notfound";
	
	//Test pour les sessions;
	@InjectPage
	@Property
	private Index indexPage;

	public void onActivate() throws Exception {
		try {
			com.banque.modele.EtatCompte etatCompte = banqueService.etatCompteLogin(this.login);
			if (etatCompte != null) {
				etatCompteForm = new EtatCompteForm();
				etatCompteForm.setLogin(etatCompte.getLogin());
				etatCompteForm.setMontant(etatCompte.getMontant());
			}
		} catch (LoginInexistantException e) {
			throw new LoginInexistantException(messages.format(MESSAGE_NOT_FOUND_LOGIN, this.login), this.login);
		} catch (TechnicalException exception) {
			throw exception;
		}

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
