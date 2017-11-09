package com.banque.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.banque.dao.IBanqueDAO;
import com.banque.modele.EtatCompte;
import com.banque.modele.OperationCompte;
import com.banque.modele.User;
import com.banque.service.IBanque;
import com.banque.service.IUserService;
import com.banque.service.exception.CompteDebiteurException;
import com.banque.service.exception.LoginInexistantException;
import com.banque.service.exception.PlafondMaxAtteintException;
import com.banque.service.exception.TechnicalException;

@Service("banqueService")
public class BanqueImpl implements IBanque {

	public static final Log LOG = LogFactory.getLog(BanqueImpl.class);

	private static final BigDecimal PLAFOND = BigDecimal.valueOf(10000);

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	@Autowired
	@Qualifier("banqueDAO")
	private IBanqueDAO banqueDAO;

	public BanqueImpl() {
		if (LOG.isInfoEnabled()) {
			LOG.info("Salut");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IBanque#crediter(java.lang.String, java.lang.String, java.math.BigDecimal)
	 */
	public void crediter(final String libelleOperation, final String login, final BigDecimal montantCrediter) throws LoginInexistantException, PlafondMaxAtteintException, TechnicalException {
		LOG.info("Objectif : Crediter utilisateur " + login + " d'un montant de " + montantCrediter);

		try {
			if (montantCrediter.compareTo(PLAFOND) > 0) {
				throw new PlafondMaxAtteintException("La valeur " + montantCrediter + " est superieur au plafond de " + PLAFOND, PLAFOND);
			}

			// On verifie que l'utilisateur ayant le login en parametre existe.
			User user = userService.findUserByLogin(login);
			if (user == null) {
				throw new LoginInexistantException("Le login " + login + " n'existe pas.", login);
			}

			// Si le login existe, on recupere son etat du compte.
			EtatCompte etatCompte = banqueDAO.getEtatCompte(login);
			if (etatCompte == null) {
				throw new TechnicalException("L'etat du compte doit exister.");
			}

			// On met a jour l'etat de son compte
			etatCompte.setMontant(etatCompte.getMontant().add(montantCrediter));
			banqueDAO.updateCompte(etatCompte);

			// On cree une operation crediter
			OperationCompte operationCompte = new OperationCompte(login, libelleOperation, etatCompte.getMontant());
			operationCompte.setMontantCredite(montantCrediter);
			banqueDAO.createOperationCompte(operationCompte);

			LOG.info("Fin Objectif : Crediter utilisateur " + login + " d'un montant de " + montantCrediter);

		} catch (LoginInexistantException e) {
			throw e;
		} catch (PlafondMaxAtteintException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new TechnicalException(e);
		}

	}
	
/*
 * (non-Javadoc)
 * @see com.banque.service.IBanque#debiter(java.lang.String, java.lang.String, java.math.BigDecimal)
 */
	public void debiter(final String libelleOperation, final String login, final BigDecimal montant) throws LoginInexistantException, CompteDebiteurException, TechnicalException {
		LOG.info("Objectif : Debiter utilisateur " + login + " d'un montant de " + montant);

		try {
			// On verifie que l'utilisateur avec le parametre en parametre
			// existe en base
			User user = userService.findUserByLogin(login);
			if (user == null) {
				throw new LoginInexistantException("Le login " + login + " n'existe pas.", login);
			}

			// S'il existe, on recupere l'etat de son compte et on le met a jour
			// avec le montant debiter.<br/>
			EtatCompte etatCompte = banqueDAO.getEtatCompte(login);
			BigDecimal montantEtatCompte = etatCompte.getMontant();
			montantEtatCompte = montantEtatCompte.subtract(montant);
			if (montantEtatCompte.compareTo(BigDecimal.valueOf(0)) < 0) {
				throw new CompteDebiteurException("Le compte ne peut pas devenir debiteur");
			}
			etatCompte.setMontant(montantEtatCompte);
			banqueDAO.updateCompte(etatCompte);

			// On cree une operation de compte debiter
			OperationCompte operationCompte = new OperationCompte(login, libelleOperation, etatCompte.getMontant());
			operationCompte.setMontantDebite(montant);
			banqueDAO.createOperationCompte(operationCompte);

		} catch (LoginInexistantException e) {
			throw e;
		} catch (CompteDebiteurException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new TechnicalException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IBanque#findListEtatCompte()
	 */
	public List<EtatCompte> findListEtatCompte() throws TechnicalException {
		List<EtatCompte> listEtatCompte = Collections.EMPTY_LIST;

		try {
			listEtatCompte = banqueDAO.listEtatCompte();
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
		return listEtatCompte;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IBanque#findEtatCompteByParam(java.lang.String, java.math.BigDecimal, java.math.BigDecimal)
	 */
	public List<EtatCompte> findEtatCompteByParam(final String login, final BigDecimal montantMin, final BigDecimal montantMax) throws TechnicalException {
		List<EtatCompte> listEtatCompte = Collections.EMPTY_LIST;

		try {
			listEtatCompte = banqueDAO.listEtatCompte(login, montantMin, montantMax);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
		return listEtatCompte;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IBanque#etatCompteLogin(java.lang.String)
	 */
	public EtatCompte etatCompteLogin(String login) throws LoginInexistantException, TechnicalException {
		LOG.info("Existence du compte : " + login);
		EtatCompte etatCompte = null;
		try {
			User user = userService.findUserByLogin(login);
			if (user == null) {
				throw new LoginInexistantException("Le login " + login + " n'existe pas.", login);
			}

			etatCompte = banqueDAO.getEtatCompte(login);

			return etatCompte;
		} catch (LoginInexistantException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new TechnicalException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IBanque#virement(java.lang.String, java.lang.String, java.lang.String, java.math.BigDecimal)
	 */
	public void virement(final String libelleOperation, String loginEmetteurVirement, String loginReceveurVirement, final BigDecimal montantVirement) throws LoginInexistantException, CompteDebiteurException, PlafondMaxAtteintException, TechnicalException {
		// Celui qui recoit le virement se fait crediter.
		this.crediter(libelleOperation, loginReceveurVirement, montantVirement);

		// Celui qui emet le virement se fait debiter.
		this.debiter(libelleOperation, loginEmetteurVirement, montantVirement);

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IBanque#createEtatCompte(java.lang.String)
	 */
	public void createEtatCompte(final String login) throws TechnicalException {
		try {
			EtatCompte etatCompte = new EtatCompte(login, BigDecimal.valueOf(0));
			banqueDAO.createEtatCompte(etatCompte);
		} catch (Exception exception) {
			throw new TechnicalException(exception);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IBanque#findLastOperationCompteByLogin(java.lang.String, java.lang.Integer)
	 */
	public List<OperationCompte> findLastOperationCompteByLogin(String login, Integer numberOperation) throws TechnicalException {
		List<OperationCompte> listOperationCompte = null;
		try {
			listOperationCompte = banqueDAO.findLastOperationCompteByLogin(login, numberOperation);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
		return listOperationCompte;
	}

}
