package com.banque.service;

import java.util.List;

import com.banque.service.exception.ParametreNotFoundException;
import com.banque.service.exception.TechnicalException;
import com.banque.modele.Parametre;
import com.banque.modele.User;
import com.banque.modele.Role;
import com.banque.service.exception.LoginDejaExistantException;

public interface IUserService {
	/**
	 * Methode permettant de recuperer l'utilisateur en question en fonction de son login.<br/>
	 * @param login Login en parametre.<br/>
	 * @return Retourne l'utilisateur en question.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	User findUserByLogin(final String login) throws TechnicalException;

	/**
	 * Methode permettant de recuperer l'utilisateur en question en fonction de son id.<br/>
	 * @param id L'id en parametre.<br/>
	 * @return Retourne l'utilisateur en question.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	User findUserById(final Integer id) throws TechnicalException;

	/**
	 * Recupere la liste des utilisateurs.
	 * @return La liste des utilisateurs.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	List<User> findAll() throws TechnicalException;

	/**
	 * Methode permettant de creer un utilisateur dans l'application.<br/>
	 * @param newUser L'utilisateur.<br/>
	 * @param passwordNotCrypt Mot passe non crypte.<br/>
	 * @throws LoginDejaExistantException Erreur levee lorsque le login existe deja.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	void createUser(final User newUser,final String passwordNotCrypt) throws LoginDejaExistantException,TechnicalException;

	/**
	 * Methode permettant la liste des roles.<br/>
	 * @return Recupere la liste des roles.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	List<Role> findRoles() throws TechnicalException;
	
	/**
	 * Methode permettant de recuperer le role en fonction de son label.<br/>
	 * @param label Le label.<br/>
	 * @return Retourne le role en fonction de son label.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	Role findRoleByLabel(final String label) throws TechnicalException;
	
	/**
	 * Methode permettant de recuperer le role en fonction de son id.<br/>
	 * @param id L'id.<br/>
	 * @return Retourne le role en fonction de son id.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	Role findRoleById(final Integer id) throws TechnicalException;
	
	
	/**
	 * Methode permettant de recuperer la liste des parametres.<br/>
	 * @return Renvoie la liste des parametres.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	List<Parametre> findAllParametres() throws TechnicalException;
	
	/**
	 * Methode permettant de recuperer un parametre en fonction de son code.<br/>
	 * @param code Le code.<br/>
	 * @return Renvoie le parametre en fonction du code.<br/>
	 * @throws ParametreNotFoundException Exception leve si le parametre n'est pas trouve.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	Parametre findParametreByCode(final String code) throws ParametreNotFoundException,TechnicalException;
	
	/**
	 * Methode permettant de mettre a jour un parametre en fonction des parametres ci dessous.<br/>
	 * @param code Le code.<br/>
	 * @param label Le label.<br/>
	 * @param description La description.<br/>
	 * @param valeur La valeur.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	void updateParametre(final String code,final String label,final String description,Integer valeur) throws TechnicalException;
	
	/**
	 * Methode permettant de supprimer un parametre en fonction de son code.<br/>
	 * @param code Le code.<br/>
	 * @throws TechnicalException Erreur technique.<br/>
	 */
	void deleteParametre(final String code) throws TechnicalException;
}
