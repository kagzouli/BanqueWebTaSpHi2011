package com.banque.java.pages.user;

import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import com.banque.modele.Parametre;
import com.banque.service.IUserService;

// @IncludeJavaScriptLibrary("js/listeparametre.js")
public class ListeParametre {
	
	/** Liste des parametres  **/
	private List<Parametre> listParametre;
	
	/** Parametre choisi **/
	private Parametre parametre;
	
	/** Element du tableau **/
	private BeanModel<Parametre> model;

	/** Messages a afficher **/
	@Inject
	private Messages messages;
	
	/** Service utilisateur **/
	@Inject
	private IUserService userService;
	
	@Inject
	private BeanModelSource beanModelSource;
	
	/** Page modification **/
	@InjectPage
	private ModificationParametre modificationParametrePage;
	
	/** Image modification **/
	@Property
	@Inject
	@Path("context:images/modifier.gif")
	private Asset imageModification;

	/** Image modification **/
	@Property
	@Inject
	@Path("context:images/supprimer.gif")
	private Asset imageSuppression;
	
	void onActivate() throws Exception {
		// Liste des utilisateurs
		this.listParametre = userService.findAllParametres();
	}
	
	public BeanModel<Parametre> getModel() {
		this.model = beanModelSource.createDisplayModel(Parametre.class, messages);
		this.model.add("modification", null);
		this.model.add("suppression", null);
		
		this.model.exclude("description");
		this.model.reorder("code","label","valeur","modification","suppression");

		// Ajout d'une colonne edit au tableau
		this.model.get("code").label(messages.get("codeparametre.field"));
		this.model.get("label").label(messages.get("labelparametre.field"));
		this.model.get("label").sortable(false);
		this.model.get("valeur").label(messages.get("valeurparametre.field"));
		this.model.get("valeur").sortable(false);
		return this.model;
	}
	
	/**
	 * Renvoie la page de modification de page
	 * @param codeParametre
	 * @return
	 */
	public Object onActionFromModification(final String codeParametre) {
		modificationParametrePage.setCodeParametre(codeParametre);
		return modificationParametrePage;
	}
	
	/**
	 * Gere la suppression du parametre.<br/>
	 * @param codeParametre
	 * @return
	 */
	public Object onActionFromSuppression(final String codeParametre) throws Exception{
		userService.deleteParametre(codeParametre);
		return this;
	}


	
	public List<Parametre> getListParametre(){
		return listParametre;
	}

	public Parametre getParametre() {
		return parametre;
	}

	public void setParametre(Parametre parametre) {
		this.parametre = parametre;
	}

	public void setModel(BeanModel<Parametre> model) {
		this.model = model;
	}
}
