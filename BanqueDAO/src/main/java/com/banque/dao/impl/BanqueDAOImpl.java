package com.banque.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#updateCompte(com.banque.modele.EtatCompte)
	 */
	public void updateCompte(final EtatCompte etatCompte) throws DAOException {

		try {
			this.sessionFactory.getCurrentSession().update(etatCompte);
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
			Query query = this.sessionFactory.getCurrentSession().createQuery(REQUEST_ETAT_COMPTE);
			List<EtatCompte> listEtatCompte = query.list();
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
		Query query = this.sessionFactory.getCurrentSession().createQuery(REQUEST_SELECT_LOGIN);

		query.setString("login", login);
		List<EtatCompte> listEtatCompte = query.list();
		if (listEtatCompte != null) {
			if (listEtatCompte.size() > 1) {
				throw new DAOException("2 comptes ne peuvent pas appartenir aux memes login");
			}
		}
		return listEtatCompte.get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#createEtatCompte(com.banque.modele.EtatCompte)
	 */
	public void createEtatCompte(EtatCompte etatCompte) throws DAOException {
		try {
			this.sessionFactory.getCurrentSession().save(etatCompte);
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

		try {
			Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(EtatCompte.class);

			if (!StringUtils.isEmpty(login)) {
				criteria.add(Restrictions.like("login", "%" + login.toUpperCase() + "%"));
			}
			if (montantMin != null) {
				criteria.add(Restrictions.ge("montant", montantMin));
			}
			if (montantMax != null) {
				criteria.add(Restrictions.le("montant", montantMax));
			}

			List<EtatCompte> listEtatCompte = criteria.list();
			return listEtatCompte;
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IBanqueDAO#createOperationCompte(com.banque.modele.OperationCompte)
	 */
	public void createOperationCompte(OperationCompte operationCompte) throws DAOException {
		try {
			operationCompte.setDateOperation(new Date());
			this.sessionFactory.getCurrentSession().save(operationCompte);
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

			Query query = this.sessionFactory.getCurrentSession().createQuery(REQUEST_SELECT_LAST_OPERATION);
			query.setString("login", login);
			if (numberOperation != null) {
				query.setMaxResults(numberOperation);
			}	
			listOperationCompte = query.list();
		
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}

		return listOperationCompte;
	}

}
