package com.banque.dao;

import java.util.List;

import com.banque.dao.exception.DAOException;
import com.banque.modele.Role;
import com.banque.modele.User;

public interface IUserDao {

	/**
	 * Methode permettant de recuperer la liste des utilisateurs.<br/>
	 * @return Recupere la liste des utilisateurs.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	List<User> findAll() throws DAOException;

	/**
	 * Methode permettant de recuperer l'utilisateur ayant le login.<br/>
	 * @param login Le login en question.<br/>
	 * @return Recupere l'utilisateur ayant le login en parametre.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	User findUserByLogin(final String login) throws DAOException;

	/**
	 * Methode permettant de recuperer l'utilisateur ayant l'id suivant.<br/>
	 * @param id L'id en question.<br/>
	 * @return Recupere l'utilisateur ayant l'id en parametre.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	User findUserById(final Integer id) throws DAOException;

	/**
	 * Methode permettant de creer l'utilisateur en parametre.<br/>
	 * @param newUser L'utilisateur.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	void createUser(final User newUser) throws DAOException;
	
	/**
	 * Methode permettant de recuperer la liste des roles.<br/>
	 * @return Retourne la liste des roles.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	List<Role> findRoles() throws DAOException;
	
	/**
	 * Methode permettant de recuperer le role ayant le label en parametre.<br/>
	 * @param label Le label en question.<br/>
	 * @return Retourne le role ayant le label en parametre.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	Role findRoleByLabel(final String label) throws DAOException;
	
	/**
	 * Methode permettant de recuperer le role ayant l'id en parametre.<br/>
	 * @param id L'id en parametre.<br/>
	 * @return Retourne le role ayant l'id en parametre.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	Role findRoleById(final Integer id) throws DAOException;
}
