package com.banque.service;

import java.math.BigDecimal;
import java.util.List;

import com.banque.service.exception.CompteDebiteurException;
import com.banque.service.exception.LoginInexistantException;
import com.banque.service.exception.PlafondMaxAtteintException;
import com.banque.service.exception.TechnicalException;
import com.banque.modele.EtatCompte;
import com.banque.modele.OperationCompte;

public interface IBanque {
	
	/**
	 * Methode permettant de crediter le montant suivant au login en parametre.<br/>
	 * @param libelleOperation Libelle de l'operation.<br/>
	 * @param login Le login en question.<br/>
	 * @param montant Le montant a crediter.<br/>
	 * @throws LoginInexistantException Erreur lorsque le login inexistant.<br/>
	 * @throws PlafondMaxAtteintException Erreur lorsque le plafond max est atteint.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	void crediter(final String libelleOperation,final String login, final BigDecimal montant) throws LoginInexistantException, PlafondMaxAtteintException, TechnicalException;

	/**
	 * Methode permettant de debiter le montant suivant au login en parametre.<br/>
	 * @param libelleOperation Libelle de l'operation.<br/>
	 * @param login Le login en question.<br/>
	 * @param montant Le montant a debiter.<br/>
	 * @throws LoginInexistantException Erreur lorsque le login est inexistant.<br/>
	 * @throws CompteDebiteurException Erreur levee lorsque le compte devient debiteur.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	void debiter(final String libelleOperation,final String login, final BigDecimal montant) throws LoginInexistantException, CompteDebiteurException, TechnicalException;

	/**
	 * Recupere la liste des etats des comptes de la banque.<br/>
	 * @return Recupere la liste des etats des comptes de la banque.<br/<
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	List<EtatCompte> findListEtatCompte() throws TechnicalException;
	
	/**
	 * Recupere la listes des etats des comptes en fonction du login, montant minimal et maximal.<br/>
	 * @param login Le login en question.<br/>
	 * @param montantMin Le montant minimal.<br/>
	 * @param montantMax Le montant maximal.<br/>
	 * @return Retourne la liste des etats des comptes en fonction du login, montant minimal et maximal.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	List<EtatCompte> findEtatCompteByParam(final String login,final BigDecimal montantMin,final BigDecimal montantMax) throws TechnicalException;

	/**
	 * Recupere l'etat du compte en fonction du login exact.<br/>
	 * @param login Le login en question.<br/>
	 * @return Retourne l'etat du compte en fonction du login exact.<br/>
	 * @throws LoginInexistantException Erreur levee lorsque le login en parametre n'existe pas en base.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	EtatCompte etatCompteLogin(final String login) throws LoginInexistantException, TechnicalException;

	/**
	 * Effectue un virement.<br/> 
	 * @param libelleOperation Libelle de l'operation.<br/>
	 * @param loginEmetteurVirement Login emetteur du virement.<br/>
	 * @param loginReceveurVirement Login recepteur du virement.<br/>
	 * @param montantVirement Montant virement.<br/>
	 * @throws LoginInexistantException Erreur levee lorsque le login emetteur ou debiteur du virement n'existe pas.<br/>
	 * @throws CompteDebiteurException Lorsque le compte devient debiteur pour l'emetteur du virement.<br/>
	 * @throws PlafondMaxAtteintException Levee lorsque le plafond maximal est atteint.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	void virement(final String libelleOperation,final String loginEmetteurVirement, final String loginReceveurVirement, final BigDecimal montantVirement) throws LoginInexistantException, CompteDebiteurException, PlafondMaxAtteintException, TechnicalException;

	/**
	 * Cree l'etat du compte pour un utilisateur donne.<br/>
	 * @param login Le login en question.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	void createEtatCompte(final String login) throws TechnicalException;
	
	/**
	 * Methode permettant de recuperer les dernieres operations de compte pour un login donne. On peux limiter le nombre de donne recupere.<br/>
	 * @param login Le login en question.<br/>
	 * @param numberOperation Nombre operations.<br/>
	 * @return Retourne les dernieres operations de compte pour un login donne.On peux limiter le nombre d'enregistrements.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	List<OperationCompte> findLastOperationCompteByLogin(final String login,Integer numberOperation) throws TechnicalException;

}