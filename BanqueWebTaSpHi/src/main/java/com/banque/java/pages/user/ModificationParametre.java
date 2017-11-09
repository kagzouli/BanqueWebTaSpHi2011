package com.banque.java.pages.user;


import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextArea;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.banque.form.ParametreForm;
import com.banque.modele.Parametre;
import com.banque.service.IUserService;

public class ModificationParametre {
	
	@Persist(PersistenceConstants.SESSION)
	private String codeParametre;
	
	@Component(id = "modifparam_form")
	private transient Form modifParamForm;
	
	private ParametreForm parametreForm = new ParametreForm();

	/** Service utilisateur **/
    @Inject
    private IUserService userService;

	@Component
    private TextField code;
	
	@Component
	private TextField label;
	
	@Component
	private TextArea description;
	
	@Component
	private TextField value;
	
	/** Page liste parametre **/
	@InjectPage
	private ListeParametre listeParametrePage;


	/**
	 * Chargement page.<br/>
	 * @throws Exception Erreur page.<br/>
	 */
	void onActivate() throws Exception {
		
		Parametre parametreSearch = userService.findParametreByCode(codeParametre);
		if (parametreSearch != null){
			this.parametreForm = new ParametreForm();
			this.parametreForm.setCode(codeParametre);
			parametreForm.setLabel(parametreSearch.getLabel());
			parametreForm.setDescription(parametreSearch.getDescription());
			parametreForm.setValeur(parametreSearch.getValeur());
		}
	}
	
	/**
	 * Traitement page.<br/>
	 * @return Renvoie la page suivante.<br/>
	 * @throws Exception Erreur technique.<br/>
	 */
	public Object onSuccess() throws Exception {
		try {
			userService.updateParametre(this.parametreForm.getCode(), this.parametreForm.getLabel(), this.parametreForm.getDescription(), this.parametreForm.getValeur());
		} catch (final Exception exception) {
			throw exception;
		}
		return listeParametrePage;
	}


	
	

	
	public ParametreForm getParametreForm() {
		return parametreForm;
	}

	public void setParametreForm(ParametreForm parametreForm) {
		this.parametreForm = parametreForm;
	}

	public String getCodeParametre() {
		return codeParametre;
	}

	public void setCodeParametre(String codeParametre) {
		this.codeParametre = codeParametre;
	}
	
	

	public TextField getCode() {
		return code;
	}

	public void setCode(TextField code) {
		this.code = code;
	}

	public TextField getLabel() {
		return label;
	}

	public void setLabel(TextField label) {
		this.label = label;
	}

	public TextArea getDescription() {
		return description;
	}

	public void setDescription(TextArea description) {
		this.description = description;
	}

	public TextField getValue() {
		return value;
	}

	public void setValue(TextField value) {
		this.value = value;
	}
}
