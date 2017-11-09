package com.banque.bean;

public class TypeAffichageOperationCompte implements Comparable<TypeAffichageOperationCompte> {
	
	/** code type affichage **/
	private String codeTypeAffichage;
	
	/** libelle type affichage **/
	private String libelleTypeAffichage;
	
	/** Constante correspondant a l'affichage en mode graphique. **/
	public static final String CODE_TYPE_GRAPH = "GRAPH";
	
	/** Constante correspondant a l'affichage en mode texte **/
	public static final String CODE_TYPE_TEXT = "TEXT";
	
	/**
	 * Constructeur.<br/>
	 */
	public TypeAffichageOperationCompte(){
		super();
	}
	
	/**
	 * Constructeur.<br/>
	 * @param codeTypeAffichage
	 * @param libelleTypeAffichage
	 */
	public TypeAffichageOperationCompte(String codeTypeAffichage,String libelleTypeAffichage){
		this.codeTypeAffichage = codeTypeAffichage;
		this.libelleTypeAffichage = libelleTypeAffichage;
	}

	public String getCodeTypeAffichage() {
		return codeTypeAffichage;
	}

	public void setCodeTypeAffichage(String codeTypeAffichage) {
		this.codeTypeAffichage = codeTypeAffichage;
	}

	public String getLibelleTypeAffichage() {
		return libelleTypeAffichage;
	}

	public void setLibelleTypeAffichage(String libelleTypeAffichage) {
		this.libelleTypeAffichage = libelleTypeAffichage;
	}

	public int compareTo(TypeAffichageOperationCompte otherTypeAffichage) {
		if (otherTypeAffichage == null){
			return 1;
		}
		
		return this.codeTypeAffichage.compareTo(otherTypeAffichage.codeTypeAffichage);
	}
	
	

}
