package com.banque.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.banque.dao.IBanqueDAO;
import com.banque.dao.exception.DAOException;
import com.banque.modele.EtatCompte;
import com.banque.modele.OperationCompte;

@Repository("banqueDAO")
public class BanqueDAOImpl implements IBanqueDAO {

	/** Logger **/
	public static final Log LOG = LogFactory.getLog(BanqueDAOImpl.class);

	/** Requete pour recuperer l'etat du compte en fonction du login **/
	private static final String REQUEST_SELECT_LOGIN = "from EtatCompte where login = :login";

	/** Requete pour recupere les etats des comptes **/
	private static final String REQUEST_ETAT_COMPTE = "from EtatCompte";

	/** Requete pour recuperer les dernieres operations d'un login donne. **/
	private static final String REQUEST_SELECT_LAST_OPERATION = "from OperationCompte where login = :login order by id desc";

	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#updateCompte(com.banque.modele.EtatCompte)
	 */
	public void updateCompte(final EtatCompte etatCompte) throws DAOException {

		try {
			this.entityManager.persist(etatCompte);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#listEtatCompte()
	 */
	public List<EtatCompte> listEtatCompte() throws DAOException {
		try {
			Query query = this.entityManager.createQuery(REQUEST_ETAT_COMPTE);
			List<EtatCompte> listEtatCompte = (List<EtatCompte>) query.getResultList();
			return listEtatCompte;
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);

		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#getEtatCompte(java.lang.String)
	 */
	public EtatCompte getEtatCompte(final String login) throws DAOException {
		EtatCompte etatCompte = null;
		try{
			Query query = this.entityManager.createQuery(REQUEST_SELECT_LOGIN);

			query.setParameter("login", login);
			etatCompte = (EtatCompte) query.getSingleResult();
		}catch(NoResultException noResultException){
			LOG.warn("No result has been found for getEtatCompte for parameter role login = '" + login +"'");
		}catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return etatCompte;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#createEtatCompte(com.banque.modele.EtatCompte)
	 */
	public void createEtatCompte(EtatCompte etatCompte) throws DAOException {
		try {
			this.entityManager.persist(etatCompte);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#listEtatCompte(java.lang.String, java.math.BigDecimal, java.math.BigDecimal)
	 */
	public List<EtatCompte> listEtatCompte(String login, BigDecimal montantMin, BigDecimal montantMax) throws DAOException {
		List<EtatCompte> listEtatCompte = Collections.emptyList();
		try {
			Map<String, Object> mapParameters = new HashMap<>();
			
			StringBuilder jpqlBuilder = new StringBuilder(64);
	        
	        jpqlBuilder.append("FROM EtatCompte occurFlow WHERE 1=1");
			if (!StringUtils.isEmpty(login)) {
				jpqlBuilder.append(" AND login like :loginParam");
				mapParameters.put("loginParam", "%" + login.toUpperCase() + "%");
			}
			if (montantMin != null) {
				jpqlBuilder.append(" AND montant >= :montantMinParam");
				mapParameters.put("montantMinParam", montantMin);
			}
			if (montantMax != null) {
				jpqlBuilder.append(" AND montant <= :montantMaxParam");
				mapParameters.put("montantMaxParam", montantMax);
			}
			
			Query query = entityManager.createQuery(jpqlBuilder.toString());
	        
	        if (mapParameters != null){
	            for (Map.Entry<String, Object> parameter : mapParameters.entrySet()){
	                query.setParameter(parameter.getKey(), parameter.getValue());                
	            }
	        }

			 listEtatCompte = query.getResultList();
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return listEtatCompte;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#createOperationCompte(com.banque.modele.OperationCompte)
	 */
	public void createOperationCompte(OperationCompte operationCompte) throws DAOException {
		try {
			operationCompte.setDateOperation(new Date());
			this.entityManager.persist(operationCompte);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#findLastOperationCompteByLogin(java.lang.String, java.lang.Integer)
	 */
	public List<OperationCompte> findLastOperationCompteByLogin(String login, Integer numberOperation) throws DAOException {
		List<OperationCompte> listOperationCompte = null;
		try {

			Query query = this.entityManager.createQuery(REQUEST_SELECT_LAST_OPERATION);
			query.setParameter("login", login);
			if (numberOperation != null) {
				query.setMaxResults(numberOperation);
			}	
			listOperationCompte = query.getResultList();
		
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}

		return listOperationCompte;
	}

}
