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
import com.banque.service.exception.PlafondMaxAtteintException;
import com.banque.service.exception.TechnicalException;

public class Virement {

	@Component(id = "virement_form")
	private Form virementForm;

	private OperationCompteForm operationCompteForm;

	@Inject
	private IBanque banqueService;

	@Component
	private TextField loginEmetteur;

	@Component
	private TextField loginReceveur;
	
	@Component	
	private TextField operation;


	@Component
	private TextField montant;

	@Inject
	private Messages messages;

	@InjectPage
	private EtatCompte etatCompte;

	private static final String MESSAGE_NOT_FOUND_LOGIN = "login.notfound";
	private static final String MESSAGE_POSITIVE_AMOUNT = "account.mustbe.positive";
	private static final String MESSAGE_AMOUNT_REACHED = "amount.reached";
	private static final String MESSAGE_AMOUNT_MUSTBE_POSITIVE = "amount.mustbe.positive";

	private static final BigDecimal ZERO_BIG_DEC = BigDecimal.valueOf(0);
	
	public Virement() {
		this.operationCompteForm = new OperationCompteForm();
	}

	/**
	 * @return the virementForm
	 */
	public Form getVirementForm() {
		return virementForm;
	}

	/**
	 * @param virementForm the virementForm to set
	 */
	public void setVirementForm(Form virementForm) {
		this.virementForm = virementForm;
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
	
	public void onValidateForm(){
		if ((this.operationCompteForm != null) && (this.operationCompteForm.getMontant().compareTo(ZERO_BIG_DEC) <= 0)){
			virementForm.recordError(this.montant, messages.format(MESSAGE_AMOUNT_MUSTBE_POSITIVE));
		}
	}

	public Object onSuccess() throws TechnicalException {

		try {
			banqueService.virement(this.operationCompteForm.getLibelleOperation(),this.operationCompteForm.getLoginEmetteur(), this.operationCompteForm.getLoginReceveur(), this.operationCompteForm.getMontant());
		} catch (final LoginInexistantException exception) {
			final String errorMessage = exception.getMessage();
			if (errorMessage.contains(this.operationCompteForm.getLoginEmetteur())) {
				virementForm.recordError(this.loginEmetteur, messages.format(MESSAGE_NOT_FOUND_LOGIN, this.operationCompteForm.getLoginEmetteur()));
			} else if (errorMessage.contains(this.operationCompteForm.getLoginReceveur())) {
				virementForm.recordError(this.loginReceveur, messages.format(MESSAGE_NOT_FOUND_LOGIN, this.operationCompteForm.getLoginReceveur()));
			}
			return null;
		} catch (final CompteDebiteurException exception) {
			virementForm.recordError(this.montant, messages.format(MESSAGE_POSITIVE_AMOUNT));
			return null;
		} catch (final PlafondMaxAtteintException exception) {
			virementForm.recordError(this.montant, messages.format(MESSAGE_AMOUNT_REACHED,exception.getPlafond()));
			return null;
		} catch (final TechnicalException exception) {
			throw exception;
		}

		etatCompte.setLogin(this.operationCompteForm.getLoginEmetteur());

		return etatCompte;
	}

	/**
	 * @return the loginEmetteur
	 */
	public TextField getLoginEmetteur() {
		return loginEmetteur;
	}

	/**
	 * @param loginEmetteur the loginEmetteur to set
	 */
	public void setLoginEmetteur(TextField loginEmetteur) {
		this.loginEmetteur = loginEmetteur;
	}

	/**
	 * @return the loginReceveur
	 */
	public TextField getLoginReceveur() {
		return loginReceveur;
	}

	/**
	 * @param loginReceveur the loginReceveur to set
	 */
	public void setLoginReceveur(TextField loginReceveur) {
		this.loginReceveur = loginReceveur;
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
		virementForm.clearErrors();
	}

}
