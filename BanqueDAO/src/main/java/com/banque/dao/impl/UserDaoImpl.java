package com.banque.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.banque.dao.IUserDao;
import com.banque.dao.exception.DAOException;
import com.banque.modele.Role;
import com.banque.modele.User;

@Repository("userDAO")
public class UserDaoImpl implements IUserDao {

	public static final Log LOG = LogFactory.getLog(UserDaoImpl.class);
	
	@PersistenceContext //(name="CigarUnit",unitName="CigarUnit")
	private EntityManager entityManager;


	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IUserDao#createUser(com.banque.modele.User)
	 */
	public void createUser(User newUser) throws DAOException {
		try {
			this.entityManager.persist(newUser);
			this.entityManager.refresh(newUser);			
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IUserDao#findAll()
	 */
	public List<User> findAll() throws DAOException {
		try {
			Query query = this.entityManager.createQuery("from User as c order by c.login");
			query.setHint("org.hibernate.cacheable", Boolean.TRUE);
			return (List<User>) query.getResultList();

		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IUserDao#findUserById(java.lang.Integer)
	 */
	public User findUserById(Integer id) throws DAOException {
		User user = null;
		try {
			user = this.entityManager.find(User.class, id);
			LOG.debug("Id user :" + user.getId());
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return user;
	}
	
	@Override
	public User findUserByLoginPassword(final String login, String password) throws DAOException {
		User user = null;
		try {
			Query query = this.entityManager.createQuery("FROM User where login=:loginParam and password= :passwordParam");
			query.setParameter("loginParam", login);
			query.setParameter("passwordParam", password);

			user = (User) query.getSingleResult();
		}catch(NoResultException noResultException){
			LOG.warn("No result has been found for findUserByLoginPassword for parameter login = '" + login +"' and password='" + password+ "'");
		}catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return user;

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IUserDao#findUserByLogin(java.lang.String)
	 */
	public User findUserByLogin(String login) throws DAOException {
		User user = null;
		try {
			Query query = this.entityManager.createQuery("FROM User where login=:loginParam");
			query.setHint("org.hibernate.cacheable", Boolean.TRUE);
			query.setParameter("loginParam", login);

			user = (User) query.getSingleResult();
		} catch(NoResultException noResultException){
			LOG.warn("No result has been found for findUserByLogin for parameter login = '" + login +"'");
		}catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IUserDao#findRoles()
	 */
	public List<Role> findRoles() throws DAOException {
		List<Role> listRoles = new ArrayList<Role>();
		Query query = this.entityManager.createQuery("from Role as c order by c.id");
		query.setHint("org.hibernate.cacheable", Boolean.TRUE);
		return (List<Role>) query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IUserDao#findRoleByLabel(java.lang.String)
	 */
	public Role findRoleByLabel(String label) throws DAOException {
		Role role = null;
		try {
			Query query = this.entityManager.createQuery("FROM Role where label=:labelRoleParam");
			query.setHint("org.hibernate.cacheable", Boolean.TRUE);
			query.setParameter("labelRoleParam", label);

			role = (Role) query.getSingleResult();
		}catch(NoResultException noResultException){
			LOG.warn("No result has been found for findRoleByLabel for parameter role label = '" + label +"'");
		}catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return role;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IUserDao#findRoleById(java.lang.Integer)
	 */
	public Role findRoleById(Integer id) throws DAOException {
		Role role = null;
		try {
			role = this.entityManager.find(Role.class, id);
			LOG.debug("Id role :" + role.getId());
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return role;
	}
	
	@Override
	public Role findRoleByLogin(final String login) throws DAOException {
		Role role = null;
		try {
			Query query = this.entityManager.createQuery("FROM User where login=:loginParam");
			query.setHint("org.hibernate.cacheable", Boolean.TRUE);
			query.setParameter("loginParam", login);

			final User user = (User) query.getSingleResult();
			if (user != null){
				role = user.getRole();
			}
		}catch(NoResultException noResultException){
			LOG.warn("No result has been found for findRoleByLogin for parameter login = '" + login +"'");
		}catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return role;

	}	
}
