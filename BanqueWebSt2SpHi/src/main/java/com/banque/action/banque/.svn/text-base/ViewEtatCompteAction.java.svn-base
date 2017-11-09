package com.banque.action.banque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banque.form.EtatCompteForm;
import com.banque.service.IBanque;
import com.banque.service.exception.LoginInexistantException;
import com.banque.service.exception.TechnicalException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Component("viewEtatCompteAction")
public class ViewEtatCompteAction extends ActionSupport{
	
	/**
	 * Serial version UID.<br/>
	 */
	private static final long serialVersionUID = 1785415764380038563L;

	/** Login en parametre **/
	private String login;
	
	/** Service **/
	@Autowired
	private IBanque banqueService;
	
	private EtatCompteForm etatCompteForm;
	
	/** Message login not found **/
	private static final String MESSAGE_NOT_FOUND_LOGIN = "viewetatcompte.login.notfound";
	
	public String view(){
	
		try {
			com.banque.modele.EtatCompte etatCompte = banqueService.etatCompteLogin(this.login);
			if (etatCompte != null) {
				this.etatCompteForm = new EtatCompteForm();
				this.etatCompteForm.setLogin(etatCompte.getLogin());
				this.etatCompteForm.setMontant(etatCompte.getMontant());
			}
			return SUCCESS;
		} catch (LoginInexistantException exception) {
			LOG.error(exception.getMessage(), exception);
			this.addActionError(this.getText(MESSAGE_NOT_FOUND_LOGIN, new String[]{this.login}));
			return ERROR;
		} catch (TechnicalException exception) {
			LOG.error(exception.getMessage(), exception);
			this.addActionError(exception.getMessage());
			return ERROR;
		}
	}
	
	
	@RequiredStringValidator(type=ValidatorType.SIMPLE,fieldName="${getText('login.field')",key="error.field.mandatory")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public EtatCompteForm getEtatCompteForm() {
		return etatCompteForm;
	}


	public void setEtatCompteForm(EtatCompteForm etatCompteForm) {
		this.etatCompteForm = etatCompteForm;
	}


	public IBanque getBanqueService() {
		return banqueService;
	}

	public void setBanqueService(IBanque banqueService) {
		this.banqueService = banqueService;
	}

	
	
	

}
