package com.banque.java.pages.banque;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import com.banque.modele.EtatCompte;
import com.banque.service.IBanque;
import com.banque.service.exception.TechnicalException;

@Import(stylesheet="context:style.css")
public class RechercheListeCompte {
	
	@Component(id = "recherchecompte_form")
    private Form rechercheCompteForm;

	@Component
	private TextField login;

	@Property
	@Persist
	private String loginValue;

	@Component
	private TextField montantMin;

	@Property
	@Persist
	private BigDecimal montantMinValue;

	@Component
	private TextField montantMax;

	@Property
	@Persist
	private BigDecimal montantMaxValue;

	@Inject
	private IBanque banqueService;

	private BeanModel<EtatCompte> model;

	private EtatCompte etatCompte;

	@Inject
	private BeanModelSource beanModelSource;

	/** Messages a afficher **/
	@Inject
	private Messages messages;

	@InjectPage
	private com.banque.java.pages.banque.EtatCompte pageEtatCompte;
	
	@Component
	private Zone zoneRechercheCompte;

	/** Image consultation **/
	@Property
	@Inject
	@Path("context:images/oeil.gif")
	private Asset imageOeil;

	
	public BeanModel<EtatCompte> getModel() {
		BeanModel<EtatCompte> model = beanModelSource.createDisplayModel(EtatCompte.class, messages);
		model.add("edit", null);

		// Ajout d'une colonne edit au tableau
		model.get("login").label(messages.get("login.field"));
		model.get("montant").label(messages.get("amount.field"));
		model.get("edit").label(messages.get("page.edit"));
		return model;
	}

	/**
	 * @return the listEtatCompte
	 */
	public List<EtatCompte> getListEtatCompte() throws Exception {
		List<EtatCompte> listEtatCompte = null;
		try {
			if ((StringUtils.isEmpty(this.loginValue)) && (this.montantMinValue == null) && (this.montantMaxValue == null)){
				listEtatCompte = banqueService.findListEtatCompte();
			}else{
				listEtatCompte = banqueService.findEtatCompteByParam(this.loginValue,this.montantMinValue,this.montantMaxValue);
			}
			
			
		} catch (TechnicalException exception) {
			throw exception;
		}
		return listEtatCompte;
	}

	/**
	 * @return the etatCompte
	 */
	public EtatCompte getEtatCompte() {
		return etatCompte;
	}

	public Object onActionFromEdit(final String login) {
		pageEtatCompte.setLogin(login);
		return pageEtatCompte;
	}
	
	public void onValidateForm(){
		
	}
	
	 public Zone onSuccess() throws Exception {
		 return zoneRechercheCompte;
	 }

	/**
	 * @param etatCompte the etatCompte to set
	 */
	public void setEtatCompte(EtatCompte etatCompte) {
		this.etatCompte = etatCompte;
	}

}
