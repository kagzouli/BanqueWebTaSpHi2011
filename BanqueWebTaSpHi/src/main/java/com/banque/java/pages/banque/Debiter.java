package com.banque.java.pages.banque;

import java.math.BigDecimal;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.banque.form.OperationCompteForm;
import com.banque.service.IBanque;
import com.banque.service.exception.CompteDebiteurException;
import com.banque.service.exception.LoginInexistantException;

public class Debiter {

	@Component(id = "debiter_form")
	private Form debiterForm;

	private OperationCompteForm operationCompteForm;

	@Inject
	private IBanque banqueService;

	@Inject
	private Messages messages;

	private static final String MESSAGE_NOT_FOUND_LOGIN = "login.notfound";
	private static final String MESSAGE_POSITIVE_AMOUNT = "account.mustbe.positive";
	private static final String MESSAGE_AMOUNT_MUSTBE_POSITIVE = "amount.mustbe.positive";

	private static final BigDecimal ZERO_BIG_DEC = BigDecimal.valueOf(0);
	
	@Component
	private TextField login;
	
	@Component	
	private TextField operation;

	@Component
	private TextField montant;

	@InjectPage
	private EtatCompte etatCompte;

	public Debiter() {
		if (this.operationCompteForm == null) {
			this.operationCompteForm = new OperationCompteForm();
		}
	}

	/**
	 * @return the debiterForm
	 */
	public Form getDebiterForm() {
		return debiterForm;
	}

	/**
	 * @param debiterForm the debiterForm to set
	 */
	public void setDebiterForm(Form debiterForm) {
		this.debiterForm = debiterForm;
	}

	/**
	 * @return the operationCompteForm
	 */
	public OperationCompteForm getOperationCompteForm() {
		return operationCompteForm;
	}

	/**
	 * @param operationCompteForm the operationCompteForm to set
	 */
	public void setOperationCompteForm(OperationCompteForm operationCompteForm) {
		this.operationCompteForm = operationCompteForm;
	}
	
	public void onValidateForm(){
		if ((this.operationCompteForm != null) && (this.operationCompteForm.getMontant().compareTo(ZERO_BIG_DEC) <= 0)){
			debiterForm.recordError(this.montant, messages.format(MESSAGE_AMOUNT_MUSTBE_POSITIVE));
		}
	}

	public Object onSuccess() throws Exception {
		try {
			banqueService.debiter(this.operationCompteForm.getLibelleOperation(),this.operationCompteForm.getLoginEmetteur(), this.operationCompteForm.getMontant());
		} catch (final LoginInexistantException exception) {
			debiterForm.recordError(this.login, messages.format(MESSAGE_NOT_FOUND_LOGIN, exception.getLogin()));
			return null;
		} catch (final CompteDebiteurException exception) {
			debiterForm.recordError(this.montant, messages.format(MESSAGE_POSITIVE_AMOUNT));
			return null;
		} catch (final Exception exception) {
			throw exception;
		}

		etatCompte.setLogin(this.operationCompteForm.getLoginEmetteur());
		return etatCompte;
	}

	/**
	 * @return the banqueService
	 */
	public IBanque getBanqueService() {
		return banqueService;
	}

	/**
	 * @param banqueService the banqueService to set
	 */
	public void setBanqueService(IBanque banqueService) {
		this.banqueService = banqueService;
	}

	/**
	 * @return the login
	 */
	public TextField getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(TextField login) {
		this.login = login;
	}

	public TextField getOperation() {
		return operation;
	}

	public void setOperation(TextField operation) {
		this.operation = operation;
	}

	/**
	 * @return the montant
	 */
	public TextField getMontant() {
		return montant;
	}

	/**
	 * @param montant the montant to set
	 */
	public void setMontant(TextField montant) {
		this.montant = montant;
	}

	void cleanupRender() {
		debiterForm.clearErrors();
	}

}
