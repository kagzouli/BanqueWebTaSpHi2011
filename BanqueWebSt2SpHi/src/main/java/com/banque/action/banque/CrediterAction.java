package com.banque.action.banque;

import java.math.BigDecimal;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banque.service.IBanque;
import com.banque.service.exception.LoginInexistantException;
import com.banque.service.exception.PlafondMaxAtteintException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Component("crediterAction")
public class CrediterAction extends ActionSupport implements Preparable{
		
	private static final Logger LOG = LoggerFactory.getLogger(CrediterAction.class);
	
	/** Login. **/
	private String login;
	
	/** label **/
	private String labelOperation;
	
	/** montant **/
	private BigDecimal montant;

	/** Zero big decimal **/
	private static final BigDecimal ZERO_BIG_DEC = BigDecimal.valueOf(0);

	/** Service **/
	@Autowired
	private IBanque banqueService;

	/** message **/
	private static final String MESSAGE_NOT_FOUND_LOGIN = "crediterpage.login.notfound";
	private static final String MESSAGE_AMOUNT_REACHED = "crediterpage.amount.reached";
	private static final String MESSAGE_AMOUNT_MUSTBE_POSITIVE = "crediterpage.amount.mustbe.positive";
	
	private static final String[] EMPTY_TAB = new String[]{};

	/**
	 * Serial UID.<br/>
	 */
	private static final long serialVersionUID = 1433445656576745L;

	/**
	 * Pour initialiser la page.<br/>
	 * @return
	 */
	@SkipValidation
	public String init(){		
		return SUCCESS;
	}
	
	public String crediter(){
		
		LOG.info("Login           : " +this.login);
		LOG.info("Label operation :" +this.labelOperation);
		LOG.info("Montant         :" +this.montant);
		
		if ((this.montant != null) && (this.montant.compareTo(ZERO_BIG_DEC) <= 0)){
			this.addFieldError("montant", this.getText(MESSAGE_AMOUNT_MUSTBE_POSITIVE,EMPTY_TAB));
			return INPUT;
		}

		try {
			banqueService.crediter(this.labelOperation,this.login, this.montant);
		} catch (final LoginInexistantException exception) {
			this.addFieldError("login",this.getText(MESSAGE_NOT_FOUND_LOGIN,new String[]{exception.getLogin()}));
			return INPUT;
		} catch (final PlafondMaxAtteintException exception) {
			this.addFieldError("montant", this.getText(MESSAGE_AMOUNT_REACHED,new String[]{exception.getPlafond().toString()}));
			return INPUT;
		} catch (final Exception exception) {
			LOG.error(exception.getMessage(),exception);
			this.addActionError(exception.getMessage());
			return ERROR;
		}

		
		
		
		return SUCCESS;
	}

	@RequiredStringValidator(type=ValidatorType.SIMPLE,fieldName="${getText('login.field')",key="error.field.mandatory")
	@StringLengthFieldValidator(key="error.field.stringrange",minLength="4",maxLength="15")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@RequiredStringValidator(type=ValidatorType.SIMPLE,fieldName="${getText('operation.field')",key="error.field.mandatory")
	@StringLengthFieldValidator(key="error.field.stringrange",minLength="4",maxLength="70")
	public String getLabelOperation() {
		return labelOperation;
	}

	public void setLabelOperation(String labelOperation) {
		this.labelOperation = labelOperation;
	}

	@RequiredFieldValidator(type=ValidatorType.SIMPLE,fieldName="${getText('amount.field')",key="error.field.mandatory")
	public BigDecimal getMontant() {
		return montant;
	}

	@TypeConversion(converter="com.banque.action.common.BigDecimalConverter")
	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public void prepare() throws Exception {
		
		// TODO Auto-generated method stub
		
	}

	public IBanque getBanqueService() {
		return banqueService;
	}

	public void setBanqueService(IBanque banqueService) {
		this.banqueService = banqueService;
	}
}
