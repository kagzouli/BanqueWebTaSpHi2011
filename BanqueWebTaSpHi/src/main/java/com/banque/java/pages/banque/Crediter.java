package com.banque.java.pages.banque;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;

import com.banque.form.OperationCompteForm;
import com.banque.service.IBanque;
import com.banque.service.exception.LoginInexistantException;
import com.banque.service.exception.PlafondMaxAtteintException;

public class Crediter {

	private static final Log LOGGER = LogFactory.getLog(Crediter.class);

	@Component(id = "crediter_form")
	private Form crediterForm;

	private OperationCompteForm operationCompteForm;

	@Component
	private TextField login;

	@Component
	private TextField montant;
	@Component	
	private TextField operation;

	@Inject
	private IBanque banqueService;

	@Inject
	private Messages messages;

	@InjectPage
	private EtatCompte etatCompte;

	@Inject
	private PersistentLocale localeService;

	private static final String MESSAGE_NOT_FOUND_LOGIN = "login.notfound";
	private static final String MESSAGE_AMOUNT_REACHED = "amount.reached";
	private static final String MESSAGE_AMOUNT_MUSTBE_POSITIVE = "amount.mustbe.positive";
	
	private static final BigDecimal ZERO_BIG_DEC = BigDecimal.valueOf(0);

	public Crediter() {
		if (this.operationCompteForm == null) {
			this.operationCompteForm = new OperationCompteForm();
		}
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

	/**
	 * @return the crediterForm
	 */
	public Form getCrediterForm() {
		return crediterForm;
	}

	/**
	 * @param crediterForm the crediterForm to set
	 */
	public void setCrediterForm(Form crediterForm) {
		this.crediterForm = crediterForm;
	}

	public void onActivate() {
		LOGGER.info("Call on activate ");
	}
	
	public void onValidateForm(){
		if ((this.operationCompteForm != null) && (this.operationCompteForm.getMontant().compareTo(ZERO_BIG_DEC) <= 0)){
			crediterForm.recordError(this.montant, messages.format(MESSAGE_AMOUNT_MUSTBE_POSITIVE));
		}
	}

	public Object onSuccess() throws Exception {
		try {
			banqueService.crediter(this.operationCompteForm.getLibelleOperation(),this.operationCompteForm.getLoginEmetteur(), this.operationCompteForm.getMontant());
		} catch (final LoginInexistantException exception) {
			crediterForm.recordError(this.login, messages.format(MESSAGE_NOT_FOUND_LOGIN, exception.getLogin()));
			return null;
		} catch (final PlafondMaxAtteintException exception) {
			crediterForm.recordError(this.montant, messages.format(MESSAGE_AMOUNT_REACHED, exception.getPlafond()));
			return null;
		} catch (final Exception exception) {
			throw exception;
		}

		this.etatCompte.setLogin(this.getOperationCompteForm().getLoginEmetteur());

		return etatCompte;
	}

	void cleanupRender() {
		crediterForm.clearErrors();
	}

}
