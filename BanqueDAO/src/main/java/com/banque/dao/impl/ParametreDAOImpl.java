package com.banque.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.banque.dao.IParametreDAO;
import com.banque.dao.exception.DAOException;
import com.banque.modele.Parametre;

@Repository("parametreDAO")
public class ParametreDAOImpl implements IParametreDAO{
	
	/** Logger **/
	public static final Log LOG = LogFactory.getLog(ParametreDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/** Requete recuperant l'ensemble des parametres **/
	private static final String REQUEST_ALL = "from Parametre";



	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IParametreDAO#findAll()
	 */
	public List<Parametre> findAll() throws DAOException {
		List<Parametre> listParametres = Collections.emptyList();	
		try{
			Query query = this.sessionFactory.getCurrentSession().createQuery(REQUEST_ALL);
			listParametres = query.list();			
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
			parametre = (Parametre) this.sessionFactory.getCurrentSession().load(Parametre.class, code);
		}catch(ObjectNotFoundException exception){
			return null;
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
			this.sessionFactory.getCurrentSession().update(parametre);
			this.sessionFactory.getCurrentSession().flush();
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
			this.sessionFactory.getCurrentSession().delete(parametre);
			this.sessionFactory.getCurrentSession().flush();
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		
	}

}
