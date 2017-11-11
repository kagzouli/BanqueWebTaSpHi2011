package com.banque.service;

import java.util.List;

import com.banque.service.exception.ParametreNotFoundException;
import com.banque.modele.Parametre;
import com.banque.modele.User;
import com.banque.modele.Role;
import com.banque.service.exception.LoginDejaExistantException;

public interface IUserService {
	/**
	 * Methode permettant de recuperer l'utilisateur en question en fonction de son login.<br/>
	 * @param login Login en parametre.<br/>
	 * @return Retourne l'utilisateur en question.<br/>
	 */
	User findUserByLogin(final String login);

	/**
	 * Methode permettant de recuperer l'utilisateur en question en fonction de son id.<br/>
	 * @param id L'id en parametre.<br/>
	 * @return Retourne l'utilisateur en question.<br/>
	 */
	User findUserById(final Integer id);
	
	/**
	 * Methode permettant de recuperer un user par login/ Mot de passe en clair.<br/>
	 * @param login Login du user.<br/>
	 * @param password Mot de passe en clair.<br/>
	 * @return Retourne l'utilisateur corresppndant.<br/>
	 */
	User findUserByLoginPassword(final String login, final String password);

	/**
	 * Recupere la liste des utilisateurs.
	 * @return La liste des utilisateurs.<br/>
	 */
	List<User> findAll();

	/**
	 * Methode permettant de creer un utilisateur dans l'application.<br/>
	 * @param newUser L'utilisateur.<br/>
	 * @param passwordNotCrypt Mot passe non crypte.<br/>
	 * @throws LoginDejaExistantException Erreur levee lorsque le login existe deja.<br/>
	 */
	void createUser(final User newUser,final String passwordNotCrypt) throws LoginDejaExistantException;

	/**
	 * Methode permettant la liste des roles.<br/>
	 * @return Recupere la liste des roles.<br/>
	 */
	List<Role> findRoles();
	
	/**
	 * Methode permettant de recuperer le role en fonction de son label.<br/>
	 * @param label Le label.<br/>
	 * @return Retourne le role en fonction de son label.<br/>
	 */
	Role findRoleByLabel(final String label);
	
	/**
	 * Methode permettant de recuperer le role en fonction de son id.<br/>
	 * @param id L'id.<br/>
	 * @return Retourne le role en fonction de son id.<br/>
	 */
	Role findRoleById(final Integer id);
	
	/**
	 * Methode permettant de recuperer le role a partir du login.<br/>
	 * 
	 * @param login Login.<br/>
	 * @return
	 */
	Role findRoleByLogin(final String login);
	
	
	/**
	 * Methode permettant de recuperer la liste des parametres.<br/>
	 * @return Renvoie la liste des parametres.<br/>
	 */
	List<Parametre> findAllParametres();
	
	/**
	 * Methode permettant de recuperer un parametre en fonction de son code.<br/>
	 * @param code Le code.<br/>
	 * @return Renvoie le parametre en fonction du code.<br/>
	 * @throws ParametreNotFoundException Exception leve si le parametre n'est pas trouve.<br/>
	 */
	Parametre findParametreByCode(final String code) throws ParametreNotFoundException;
	
	/**
	 * Methode permettant de mettre a jour un parametre en fonction des parametres ci dessous.<br/>
	 * @param code Le code.<br/>
	 * @param label Le label.<br/>
	 * @param description La description.<br/>
	 * @param valeur La valeur.<br/>
	 */
	void updateParametre(final String code,final String label,final String description,Integer valeur);
	
	/**
	 * Methode permettant de supprimer un parametre en fonction de son code.<br/>
	 * @param code Le code.<br/>
	 */
	void deleteParametre(final String code);
}
