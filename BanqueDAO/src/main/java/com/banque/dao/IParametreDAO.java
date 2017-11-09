package com.banque.dao;

import java.util.List;

import com.banque.dao.exception.DAOException;
import com.banque.modele.Parametre;

public interface IParametreDAO {
	
	
	/**
	 * Methode permettant de recuperer la liste des parametres.<br/>
	 * @return Retourne la liste des parametres.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	List<Parametre> findAll() throws DAOException;
	
	/**
	 * Methode permettant de recuperer le parametre en fonction de son code.<br/>
	 * @param code Le code en parametre.<br/>
	 * @return Retourne le parametre recupere. Si aucun parametre n'a ete trouve, renvoie null.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	Parametre findByCode(final String code) throws DAOException;

	
	/**
	 * Methode permettant de mettre a jour le parametre en fonction des parametres en entree.<br/>
	 * @param parametre Parametres.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	void update(Parametre parametre) throws DAOException;
	
	/**
	 * Methode permettant de supprimer un parametre.<br/>
	 * @param parametre Le parametre en question.<br/>
	 * @throws DAOException Erreur technique.<br/>
	 */
	void delete(final Parametre parametre) throws DAOException;
	
}
