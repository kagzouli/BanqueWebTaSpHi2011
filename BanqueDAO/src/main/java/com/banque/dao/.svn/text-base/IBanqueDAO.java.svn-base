package com.banque.dao;

import java.math.BigDecimal;
import java.util.List;

import com.banque.dao.exception.DAOException;
import com.banque.modele.EtatCompte;
import com.banque.modele.OperationCompte;

public interface IBanqueDAO {

	/**
	 * Methode permettant de mettre a jour l'etat d'un compte.<br/>
	 * @param etatCompte Le compte a mettre a jour.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	void updateCompte(final EtatCompte etatCompte) throws DAOException;

	/**
	 * Methode recuperation la liste des etats des comptes de la banque.<br/>
	 * @return Retourne la liste des etats des comptes de la banque.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	List<EtatCompte> listEtatCompte() throws DAOException;
	
	/**
	 * Methode permettant de recuperer la liste des comptes de la banque en fonction du login, montant minimal et maximal.<br/>
	 * @param login Le login qui commence par ce parametre.<br/>
	 * @param montantMin Montant minimal.<br/>
	 * @param montantMax montant maximal.<br/>
	 * @return Retourne la liste des comptes de la banque en fonction du login, montant minimal et maximal.<br/>
	 * @throws DAOException Erreur compte.<br/>
	 */
	List<EtatCompte> listEtatCompte(final String login,final BigDecimal montantMin,final BigDecimal montantMax) throws DAOException;

	/**
	 * Methode permettant de recuperer l'etat du compte d'un utilisateur donne avec son login.<br/>
	 * @param login Login.<br/>
	 * @return Retourne l'etat du compte d'un utilisateur donne avec son login.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	EtatCompte getEtatCompte(final String login) throws DAOException;
	
	/**
	 * Methode permettant de creer l'etat du compte.<br/>
	 * @param etatCompte Etat compte.<br/>
	 * @throws DAOException Erreur technique
	 */
	void createEtatCompte(final EtatCompte etatCompte) throws DAOException;
	
	
	/**
	 * Methode permettant de creer une operation d'un etat du compte.<br/>
	 * @param operationCompte L'Operation du compte.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	void createOperationCompte(final OperationCompte operationCompte) throws DAOException;
	
	/**
	 * Methode permettant de recuperer les dernieres operations de compte pour un login donne. On peux limiter le nombre de donne recupere.<br/>
	 * @param login Le login en question.<br/>
	 * @param numberOperation Nombre operations.<br/>
	 * @return Retourne les dernieres operations de compte pour un login donne.On peux limiter le nombre d'enregistrements.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	List<OperationCompte> findLastOperationCompteByLogin(final String login,Integer numberOperation) throws DAOException;

}
