package com.banque.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.banque.dao.IParametreDAO;
import com.banque.dao.exception.DAOException;
import com.banque.modele.Parametre;

@Repository("parametreDAO")
public class ParametreDAOImpl implements IParametreDAO{
	
	/** Logger **/
	public static final Log LOG = LogFactory.getLog(ParametreDAOImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	
	/** Requete recuperant l'ensemble des parametres **/
	private static final String REQUEST_ALL = "from Parametre";

	
	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IParametreDAO#findAll()
	 */
	public List<Parametre> findAll() throws DAOException {
		List<Parametre> listParametres = Collections.emptyList();	
		try{
			Query query = this.entityManager.createQuery(REQUEST_ALL);
			listParametres = (List<Parametre>) query.getResultList();			
		}catch(Exception exception){
			LOG.error(exception.getMessage(),exception);
			throw new DAOException(exception);
		}
		
		return listParametres;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IParametreDAO#findByCode(java.lang.String)
	 */
	public Parametre findByCode(String code) throws DAOException {
		Parametre parametre = null;		
		try{
			parametre = this.entityManager.find(Parametre.class, code);
		}catch(Exception exception){
			LOG.error(exception.getMessage(),exception);
			throw new DAOException(exception);
		}
		
		return parametre;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IParametreDAO#update(com.banque.modele.Parametre)
	 */
	public void update(Parametre parametre) throws DAOException {

		try {
			this.entityManager.persist(parametre);
			this.entityManager.flush();
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}

		
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IParametreDAO#delete(com.banque.modele.Parametre)
	 */
	public void delete(Parametre parametre) throws DAOException {

		try {
			Parametre parametreRecover  = this.entityManager.find(Parametre.class, parametre.getCode());
			entityManager.remove(parametreRecover);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		
	}

}
