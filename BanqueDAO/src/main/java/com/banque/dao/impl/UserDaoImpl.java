package com.banque.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.banque.dao.IUserDao;
import com.banque.dao.exception.DAOException;
import com.banque.modele.Role;
import com.banque.modele.User;

@Repository("userDAO")
public class UserDaoImpl implements IUserDao {

	public static final Log LOG = LogFactory.getLog(UserDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;	

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IUserDao#createUser(com.banque.modele.User)
	 */
	public void createUser(User newUser) throws DAOException {
		try {
			this.sessionFactory.getCurrentSession().save(newUser);
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
			Query query = this.sessionFactory.getCurrentSession().createQuery("from User as c order by c.login");
			query.setCacheable(true);
			return (List<User>) query.list();

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
			user = (User) this.sessionFactory.getCurrentSession().load(User.class, id);
			LOG.debug("Id user :" + user.getId());
		} catch (ObjectNotFoundException exception) {
			LOG.info("Utilisateur avec l'id " + id + " non trouve.");
			return null;
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
			Query query = this.sessionFactory.getCurrentSession().createQuery("FROM User where login=:loginParam and password= :passwordParam");
			query.setCacheable(true);
			query.setString("loginParam", login);
			query.setString("passwordParam", password);

			List<User> listUser = query.list();
			if ((listUser != null) && (!listUser.isEmpty())) {
				user = listUser.get(0);
			}
		} catch (Exception exception) {
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
			Query query = this.sessionFactory.getCurrentSession().createQuery("FROM User where login=:loginParam");
			query.setCacheable(true);
			query.setString("loginParam", login);

			List<User> listUser = query.list();
			if ((listUser != null) && (!listUser.isEmpty())) {
				user = listUser.get(0);
			}
		} catch (Exception exception) {
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
		Query query = this.sessionFactory.getCurrentSession().createQuery("from Role as c order by c.id");
		query.setCacheable(true);
		return (List<Role>) query.list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.dao.IUserDao#findRoleByLabel(java.lang.String)
	 */
	public Role findRoleByLabel(String label) throws DAOException {
		Role role = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery("FROM Role where label=:labelRoleParam");
			query.setCacheable(true);
			query.setString("labelRoleParam", label);

			List<Role> listRole = query.list();
			if ((listRole != null) && (!listRole.isEmpty())) {
				role = listRole.get(0);
			}
		} catch (Exception exception) {
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
			role = (Role) this.sessionFactory.getCurrentSession().load(Role.class, id);
			LOG.debug("Id role :" + role.getId());
		} catch (ObjectNotFoundException exception) {
			LOG.info("Role avec l'id " + id + " non trouve.");
			return null;
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return role;
	}
	
	@Override
	public Role findRoleByLogin(final String login) throws DAOException {
		User user = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery("FROM User where login=:loginParam");
			query.setCacheable(true);
			query.setString("loginParam", login);

			List<User> listUser = query.list();
			if ((listUser != null) && (!listUser.isEmpty())) {
				user = listUser.get(0);
			}
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new DAOException(exception);
		}
		return user.getRole();

	}
}
