package com.banque.service.impl;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.banque.common.AppConfig;
import com.banque.dao.IBanqueDAO;
import com.banque.dao.IParametreDAO;
import com.banque.dao.IUserDao;
import com.banque.modele.EtatCompte;
import com.banque.modele.OperationCompte;
import com.banque.modele.Parametre;
import com.banque.modele.Role;
import com.banque.modele.User;
import com.banque.service.IUserService;
import com.banque.service.exception.LoginDejaExistantException;
import com.banque.service.exception.ParametreNotFoundException;
import com.banque.service.exception.TechnicalException;

@Service("userService")
@Transactional(readOnly=true)
public class UserServiceImpl implements IUserService {

	public static final Log LOG = LogFactory.getLog(BanqueImpl.class);

	/** Cryptage MD5 **/
	protected static final String ENCRYPTION_MD5 = "MD5";

	/** Cryptage SHA-1 **/
	protected static final String ENCRYPTION_SHA_1 = "SHA1";

	/** Zero big decimal **/
	protected static final BigDecimal BIGZERO = BigDecimal.valueOf(0);

	/** Libelle de l'operation lorsque de la creation du compte **/
	protected static final String LABEL_DEFAULT_OPERATION_COMPTE = "Initialisation";

	@Autowired
	@Qualifier("userDAO")
	private IUserDao userDao;

	@Autowired
	@Qualifier("parametreDAO")
	private IParametreDAO parametrageDao;

	@Autowired
	@Qualifier("banqueDAO")
	private IBanqueDAO banqueDAO;

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#createUser(com.banque.modele.User, java.lang.String)
	 */
	@Transactional(rollbackFor=Throwable.class, propagation = Propagation.REQUIRED, transactionManager=AppConfig.TRANSACTION_MANAGER)
	public void createUser(final User newUser, final String passwordNotCrypt) throws LoginDejaExistantException {
		final String login = newUser.getLogin();

		if (!StringUtils.isEmpty(passwordNotCrypt)) {
			String passwordCrypt = this.getPasswordCrypted(passwordNotCrypt, ENCRYPTION_SHA_1);
			newUser.setPassword(passwordCrypt);
		}

		try {
			// Cree l'utilisateur en question.<br/>
			User user = userDao.findUserByLogin(login);
			if (user != null) {
				throw new LoginDejaExistantException("Le login " + login + " existe deja.", login);
			}
			userDao.createUser(newUser);

			// Cree l'etat du compte correspondant au login de l*'utilisateur
			EtatCompte etatCompte = new EtatCompte(login, BIGZERO);
			banqueDAO.createEtatCompte(etatCompte);

			// Insertion d'une ligne dans l'operation du compte pour l'operation
			// d'initialisation.
			OperationCompte operationCompte = new OperationCompte(login, LABEL_DEFAULT_OPERATION_COMPTE, BIGZERO);
			banqueDAO.createOperationCompte(operationCompte);

		} catch (LoginDejaExistantException exception) {
			throw exception;
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#findAll()
	 */
	// @Cacheable(modelId = "userCache")
	public List<User> findAll()  {
		try {
			return userDao.findAll();
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#findUserById(java.lang.Integer)
	 */
	public User findUserById(final Integer id) {
		User user = null;
		try {
			user = userDao.findUserById(id);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
		return user;
	}
	
	public User findUserByLoginPassword(final String login, final String password){
		User user = null;
		try {
			user = userDao.findUserByLoginPassword(login, this.getPasswordCrypted(password, ENCRYPTION_SHA_1));
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
		return user;		
	}
	
/*
 * (non-Javadoc)
 * @see com.banque.service.IUserService#findUserByLogin(java.lang.String)
 */
	// @Cacheable(modelId = "userCache")
	public User findUserByLogin(final String login) {
		User user = null;
		try {
			user = userDao.findUserByLogin(login);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#findRoles()
	 */
	public List<Role> findRoles() {
		try {
			return userDao.findRoles();
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#findRoleByLabel(java.lang.String)
	 */
	public Role findRoleByLabel(final String label) {
		try {
			return userDao.findRoleByLabel(label);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#findRoleById(java.lang.Integer)
	 */
	public Role findRoleById(Integer id) {
		Role role = null;
		try {
			role = userDao.findRoleById(id);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
		return role;
	}
	
	public Role findRoleByLogin(final String login) {
		Role role = null;
		try {
			role = userDao.findRoleByLogin(login);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
		return role;
		
	}

	/**
	 * Methode permettant de crypter le mot de passe.<br/>
	 * @param credential Le mot de passe en clair.<br/>
	 * @param typeEncryption Le type de cryptage (MD5 | SHA1 | TEXT)
	 * @return Retourne le mot de passe crypte.<br/>
	 */
	protected String getPasswordCrypted(final String credential, final String typeEncryption) {

		String credentialCrypted = credential;

		try {

			if (typeEncryption.equalsIgnoreCase(ENCRYPTION_MD5)) {

				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] md5hash = new byte[40];
				md5hash = md.digest(credential.getBytes("UTF-8"));
				credentialCrypted = this.hashToString(md5hash);

			} else if (typeEncryption.equalsIgnoreCase(ENCRYPTION_SHA_1)) {

				MessageDigest md = MessageDigest.getInstance("SHA-1");
				byte[] sha1hash = new byte[40];
				sha1hash = md.digest(credential.getBytes("UTF-8"));
				credentialCrypted = this.hashToString(sha1hash);
			}

		} catch (Exception exception) {
			throw new TechnicalException(exception.getMessage());

		}

		return credentialCrypted;

	}

	/**
	 * Methode permettant de recuperer une String correspondant au hash.<br/>
	 * @param md5hash Le hash.<br/>
	 * @return Recupere une String correspondant au hash.<br/>
	 */
	protected String hashToString(final byte[] md5hash) {
		StringBuffer hashString = new StringBuffer();

		for (int i = 0; i < md5hash.length; ++i) {
			String hex = Integer.toHexString(md5hash[i]);

			if (hex.length() == 1) {
				hashString.append('0');
				hashString.append(hex.charAt(hex.length() - 1));

			} else {
				hashString.append(hex.substring(hex.length() - 2));
			}

		}
		return hashString.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#findAllParametres()
	 */
	public List<Parametre> findAllParametres() {
		try {
			return parametrageDao.findAll();
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#findParametreByCode(java.lang.String)
	 */
	public Parametre findParametreByCode(String code) throws ParametreNotFoundException, TechnicalException {
		Parametre parametre = null;
		try {
			   parametre = parametrageDao.findByCode(code);
			if ((parametre == null) || (StringUtils.isEmpty(parametre.getCode()))) {
				throw new ParametreNotFoundException("Le parametre ayant le code '" + code + "' n'a pas ete trouve");
			}
		}catch(ObjectNotFoundException exception){
			throw new ParametreNotFoundException("Aucun parametre n'a ete trouve pour le code '"+code+"'");
		}
		catch (ParametreNotFoundException exception) {
			throw exception;
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}
		return parametre;
	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#update(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Transactional(rollbackFor=Throwable.class, propagation = Propagation.REQUIRED, transactionManager=AppConfig.TRANSACTION_MANAGER)
	public void updateParametre(String code, String label, String description, Integer valeur) {
		try {
			Parametre parametre = parametrageDao.findByCode(code);
			if (parametre == null){
				throw new TechnicalException("Aucun parametre n'a ete trouve pour le code '"+code+"'");
			}
			
			if (StringUtils.isNotEmpty(label)){
				parametre.setLabel(label);
			}
			if (StringUtils.isNotEmpty(description)){
				parametre.setDescription(description);
			}
			if (valeur != null){
				parametre.setValeur(valeur);
			}
			
			parametrageDao.update(parametre);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.banque.service.IUserService#delete(java.lang.String)
	 */
	@Transactional(rollbackFor=Throwable.class, propagation = Propagation.REQUIRED, transactionManager=AppConfig.TRANSACTION_MANAGER)
	public void deleteParametre(String code) {
		try {
			Parametre parametre = parametrageDao.findByCode(code);
			if (parametre == null){
				throw new TechnicalException("Aucun parametre n'a ete trouve pour le code '"+code+"'");
			}
						
			parametrageDao.delete(parametre);
		} catch (Exception exception) {
			LOG.error(exception.getMessage(), exception);
			throw new TechnicalException(exception);
		}

	}

}
