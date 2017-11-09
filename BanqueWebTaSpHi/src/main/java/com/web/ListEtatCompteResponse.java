package com.web;

import java.util.List;

import com.banque.modele.EtatCompte;

public class ListEtatCompteResponse {

	private List<EtatCompte> listEtatCompte;

	/**
	 * @return the listEtatCompte
	 */
	public List<EtatCompte> getListEtatCompte() {
		return listEtatCompte;
	}

	/**
	 * @param listEtatCompte the listEtatCompte to set
	 */
	public void setListEtatCompte(List<EtatCompte> listEtatCompte) {
		this.listEtatCompte = listEtatCompte;
	}

}
