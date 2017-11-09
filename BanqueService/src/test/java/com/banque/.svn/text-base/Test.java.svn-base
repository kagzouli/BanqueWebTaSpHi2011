package com.banque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.banque.common.ConfigConst;
import com.banque.common.SQLUtils;
import com.banque.modele.EtatCompte;
import com.banque.modele.OperationCompte;
import com.banque.modele.Parametre;
import com.banque.modele.Role;
import com.banque.modele.User;
import com.banque.service.IBanque;
import com.banque.service.IUserService;
import com.banque.service.exception.CompteDebiteurException;
import com.banque.service.exception.LoginDejaExistantException;
import com.banque.service.exception.LoginInexistantException;
import com.banque.service.exception.ParametreNotFoundException;
import com.banque.service.exception.PlafondMaxAtteintException;
import com.banque.service.exception.TechnicalException;

@TransactionConfiguration(transactionManager = "txTestManager")
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/com/banque/applicationContext.xml", "/com/banque/config/applicationContextConfiguration.xml" })
public class Test extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	@Qualifier("banqueService")
	private transient IBanque banqueService = null;

	@Autowired
	@Qualifier("userService")
	private transient IUserService userService = null;

	private static final String LOGIN_TEST = "Karim";
	private static final String LOGIN_TEST2 = "Didier";
	private static final String LOGIN_TEST_WRONG = "Roger";

	/** Test pour la partie parametrage **/
	public static final String LABEL_NBPTMAXCOUGRA = "nombre de points maximum courbe graphique";
	public static final String DESCRIPTION_NBPTMAXCOUGRA = "Nombre de points maximum que l'on peut afficher sur le graphique";
	public static final Integer VALEUR_NBPTAFFGRA = Integer.valueOf(10);

	private static final File FILE_CREATE_DATABASE = new File("src/test/resources/create-database.sql");

	@Override
	public void setDataSource(@Qualifier("EBPMDataSource") DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Before
	public void setUp() throws Exception {
		if (ConfigConst.TestEnv.test.getLabel().equals(System.getProperty("test.env"))) {
			DataSource datasource = (DataSource) this.applicationContext.getBean("EBPMDataSource");
			File[] listSqlScript = { FILE_CREATE_DATABASE };
			SQLUtils.executeSqlScript(datasource, listSqlScript);
		}

	}

	@org.junit.Test
	public void testCrediterMontant() {
		final String libelleOperation = "Crediter 1500 Lolo";
		try {
			banqueService.crediter(libelleOperation, LOGIN_TEST, BigDecimal.valueOf(1500));

			// Verification de l'etat du compte
			EtatCompte etatCompteKar1 = banqueService.etatCompteLogin(LOGIN_TEST);
			assertEquals(etatCompteKar1.getMontant(), BigDecimal.valueOf(1500));

			// Verification de la derniere operation
			List<OperationCompte> listOperationCompte = banqueService.findLastOperationCompteByLogin(LOGIN_TEST, 1);
			if ((listOperationCompte == null) || (listOperationCompte.isEmpty())) {
				assertEquals(true, false);
			}
			OperationCompte operationCompte = listOperationCompte.get(0);
			assertEquals(operationCompte.getLogin(), LOGIN_TEST);
			assertEquals(operationCompte.getMontantCredite(), BigDecimal.valueOf(1500));
			assertEquals(operationCompte.getMontantGlobal(), BigDecimal.valueOf(1500));
			assertEquals(operationCompte.getLibelleOperation(), libelleOperation);
			assertNull(operationCompte.getMontantDebite());

		} catch (LoginInexistantException e) {
			e.printStackTrace();
			assertEquals(true, false);
		} catch (PlafondMaxAtteintException e) {
			e.printStackTrace();
			assertEquals(true, false);
		} catch (TechnicalException e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testCrediterMontantLoginInexistant() {
		try {
			banqueService.crediter("Crediter login incorrect", LOGIN_TEST_WRONG, BigDecimal.valueOf(1500));
			assertEquals(true, false);
		} catch (LoginInexistantException e) {
			assertEquals(true, true);
		} catch (PlafondMaxAtteintException e) {
			e.printStackTrace();
			assertEquals(true, false);
		} catch (TechnicalException e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testCrediterMontantPlafondAtteint() {
		try {
			banqueService.crediter("crediter plafond atteint", LOGIN_TEST, BigDecimal.valueOf(100000));
			assertEquals(true, false);
		} catch (LoginInexistantException e) {
			e.printStackTrace();
			assertEquals(true, false);
		} catch (PlafondMaxAtteintException e) {
			assertEquals(true, true);
		} catch (TechnicalException e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testDebiterMontant() {
		try {
			final String libelleOperationCrediter = "libelle operation crediter 1500";
			final String libelleOperationDebiter = "libelle operation debiter 1200";

			banqueService.crediter(libelleOperationCrediter, LOGIN_TEST, BigDecimal.valueOf(1500));
			banqueService.debiter(libelleOperationDebiter, LOGIN_TEST, BigDecimal.valueOf(1200));

			// Verification de l'etat du compte
			EtatCompte etatCompteKar = banqueService.etatCompteLogin(LOGIN_TEST);
			assertEquals(etatCompteKar.getMontant(), BigDecimal.valueOf(300));

			// Verification des dernieres operations de ce login.
			List<OperationCompte> listOperationCompte = banqueService.findLastOperationCompteByLogin(LOGIN_TEST, 2);
			if ((listOperationCompte == null) || (listOperationCompte.isEmpty())) {
				assertEquals(true, false);
			}
			OperationCompte operationDebiter = listOperationCompte.get(0);
			assertEquals(operationDebiter.getLogin(), LOGIN_TEST);
			assertEquals(operationDebiter.getLibelleOperation(), libelleOperationDebiter);
			assertEquals(operationDebiter.getMontantDebite(), BigDecimal.valueOf(1200));
			assertEquals(operationDebiter.getMontantGlobal(), BigDecimal.valueOf(300));
			assertNull(operationDebiter.getMontantCredite());

			OperationCompte operationCrediter = listOperationCompte.get(1);
			assertEquals(operationCrediter.getLogin(), LOGIN_TEST);
			assertEquals(operationCrediter.getLibelleOperation(), libelleOperationCrediter);
			assertEquals(operationCrediter.getMontantCredite(), BigDecimal.valueOf(1500));
			assertEquals(operationCrediter.getMontantGlobal(), BigDecimal.valueOf(1500));
			assertNull(operationCrediter.getMontantDebite());

		} catch (LoginInexistantException e) {
			e.printStackTrace();
			assertEquals(true, false);
		} catch (CompteDebiteurException e) {
			e.printStackTrace();
			assertEquals(true, false);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testDebiterMontantLoginIncorrect() {
		try {
			banqueService.debiter("Libelle Operation debit incorrect", LOGIN_TEST_WRONG, BigDecimal.valueOf(1200));
			assertEquals(true, false);
		} catch (LoginInexistantException e) {
			assertEquals(true, true);
		} catch (CompteDebiteurException e) {
			e.printStackTrace();
			assertEquals(true, false);
		} catch (TechnicalException e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testDebiterMontantCompteDebiteur() {
		try {
			final String libelleOperationCrediter = "Crediter test compte debiteur";
			final String libelleOperationDebiteur = "Debiter test compte debiteur";

			banqueService.crediter(libelleOperationCrediter, LOGIN_TEST, BigDecimal.valueOf(1200));

			// On verifie que l'operation du compte est en base
			// Verification des dernieres operations de ce login.
			List<OperationCompte> listOperationCompte = banqueService.findLastOperationCompteByLogin(LOGIN_TEST, 1);
			if ((listOperationCompte == null) || (listOperationCompte.isEmpty())) {
				assertEquals(true, false);
			}
			OperationCompte operationCompte = listOperationCompte.get(0);
			assertEquals(operationCompte.getLogin(), LOGIN_TEST);
			assertEquals(operationCompte.getLibelleOperation(), libelleOperationCrediter);
			assertEquals(operationCompte.getMontantCredite(), BigDecimal.valueOf(1200));
			assertEquals(operationCompte.getMontantGlobal(), BigDecimal.valueOf(1200));
			assertNull(operationCompte.getMontantDebite());

			// On debiter - cela provoque un solde debiteur et donc une
			// exception.
			banqueService.debiter(libelleOperationDebiteur, LOGIN_TEST, BigDecimal.valueOf(1500));

			assertEquals(true, false);
		} catch (LoginInexistantException e) {
			e.printStackTrace();
			assertEquals(true, false);
		} catch (CompteDebiteurException e) {
			assertEquals(true, true);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testVirement() {
		try {
			final String libelleVirement = "Virement compte a compte";

			banqueService.virement(libelleVirement, LOGIN_TEST2, LOGIN_TEST, BigDecimal.valueOf(1500));

			// Verifie l'etat des 2 comptes en jeux
			List<EtatCompte> listEtat = banqueService.findListEtatCompte();
			assertEquals(listEtat.size(), 2);

			for (EtatCompte etatCompte : listEtat) {
				final String login = etatCompte.getLogin();
				if (login.equals(LOGIN_TEST)) {
					assertEquals(etatCompte.getMontant(), BigDecimal.valueOf(1500));
				} else if (login.equals(LOGIN_TEST2)) {
					assertEquals(etatCompte.getMontant(), BigDecimal.valueOf(4200));
				}
			}

			// Verification des operations compte
			// On verifie que l'operation du compte est en base
			// Verification des dernieres operations de ce login.
			// Partie debiteur
			List<OperationCompte> listOperationCompteDebiteur = banqueService.findLastOperationCompteByLogin(LOGIN_TEST2, 1);
			if ((listOperationCompteDebiteur == null) || (listOperationCompteDebiteur.isEmpty())) {
				assertEquals(true, false);
			}
			OperationCompte operationCompteDebiteur = listOperationCompteDebiteur.get(0);
			assertEquals(operationCompteDebiteur.getLogin(), LOGIN_TEST2);
			assertEquals(operationCompteDebiteur.getLibelleOperation(), libelleVirement);
			assertEquals(operationCompteDebiteur.getMontantDebite(), BigDecimal.valueOf(1500));
			assertEquals(operationCompteDebiteur.getMontantGlobal(), BigDecimal.valueOf(4200));
			assertNull(operationCompteDebiteur.getMontantCredite());

			// Partie crediteur
			List<OperationCompte> listOperationCompteCrediteur = banqueService.findLastOperationCompteByLogin(LOGIN_TEST, 1);
			if ((listOperationCompteCrediteur == null) || (listOperationCompteCrediteur.isEmpty())) {
				assertEquals(true, false);
			}
			OperationCompte operationCompteCrediteur = listOperationCompteCrediteur.get(0);
			assertEquals(operationCompteCrediteur.getLogin(), LOGIN_TEST);
			assertEquals(operationCompteCrediteur.getLibelleOperation(), libelleVirement);
			assertEquals(operationCompteCrediteur.getMontantCredite(), BigDecimal.valueOf(1500));
			assertEquals(operationCompteCrediteur.getMontantGlobal(), BigDecimal.valueOf(1500));
			assertNull(operationCompteCrediteur.getMontantDebite());

		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testCompte() {
		try {
			final String libelleOperationCrediter = "libelle operation crediter";
			banqueService.crediter(libelleOperationCrediter, LOGIN_TEST, BigDecimal.valueOf(1500));
			List<EtatCompte> listEtat = banqueService.findListEtatCompte();
			assertEquals(listEtat.size(), 2);

			for (EtatCompte etatCompte : listEtat) {
				final String login = etatCompte.getLogin();
				if (login.equals(LOGIN_TEST)) {
					assertEquals(etatCompte.getMontant(), BigDecimal.valueOf(1500));
				} else if (login.equals(LOGIN_TEST2)) {
					assertEquals(etatCompte.getMontant(), BigDecimal.valueOf(5700));
				}
			}

			// Verification de l'operateur du login ayant crediter
			List<OperationCompte> listOperationCompteCrediteur = banqueService.findLastOperationCompteByLogin(LOGIN_TEST, 1);
			if ((listOperationCompteCrediteur == null) || (listOperationCompteCrediteur.isEmpty())) {
				assertEquals(true, false);
			}
			OperationCompte operationCompteCrediteur = listOperationCompteCrediteur.get(0);
			assertEquals(operationCompteCrediteur.getLogin(), LOGIN_TEST);
			assertEquals(operationCompteCrediteur.getLibelleOperation(), libelleOperationCrediter);
			assertEquals(operationCompteCrediteur.getMontantCredite(), BigDecimal.valueOf(1500));
			assertEquals(operationCompteCrediteur.getMontantGlobal(), BigDecimal.valueOf(1500));
			assertNull(operationCompteCrediteur.getMontantDebite());

			// Verification de l'utilisateur n'ayant fait aucune operation
			List<OperationCompte> listOperationCompteNoOperation = banqueService.findLastOperationCompteByLogin(LOGIN_TEST2, 1);
			if ((listOperationCompteNoOperation == null) || (listOperationCompteNoOperation.isEmpty())) {
				assertEquals(true, false);
			}
			OperationCompte operationCompteNoOperation = listOperationCompteNoOperation.get(0);
			assertEquals(operationCompteNoOperation.getLogin(), LOGIN_TEST2);
			assertEquals(operationCompteNoOperation.getLibelleOperation(), "Initialisation");
			assertEquals(operationCompteNoOperation.getMontantGlobal(), BigDecimal.valueOf(5700));
			assertNull(operationCompteNoOperation.getMontantCredite());
			assertNull(operationCompteNoOperation.getMontantDebite());

		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testLogin() {
		try {
			User user = userService.findUserByLogin(LOGIN_TEST);
			assertNotNull(user);
			assertEquals(user.getId(), new Integer(2));
			assertEquals(user.getLogin(), LOGIN_TEST);
			assertEquals(user.getRole().getLabel(), "user");
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testLoginInexistant() {
		try {
			User user = userService.findUserByLogin(LOGIN_TEST_WRONG);
			assertNull(user);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void createUser() {
		final String newUserLogin = "Test";
		final String password = "PWD";
		final String newUserRole = "user";
		try {
			User newUser = new User();
			newUser.setLogin(newUserLogin);
			newUser.setPassword(password);
			newUser.setRole(userService.findRoleByLabel(newUserRole));
			userService.createUser(newUser, password);

			// Verifie le user en question
			User user = userService.findUserByLogin(newUserLogin);
			assertNotNull(user);
			assertEquals(user.getId(), new Integer(3));
			assertEquals(user.getLogin(), newUserLogin);
			assertEquals(user.getRole().getLabel(), newUserRole);

			// Verification de l'utilisateur n'ayant fait aucune operation
			List<OperationCompte> listOperationCompteNoOperation = banqueService.findLastOperationCompteByLogin(newUserLogin, 1);
			if ((listOperationCompteNoOperation == null) || (listOperationCompteNoOperation.isEmpty())) {
				assertEquals(true, false);
			}
			OperationCompte operationCompteNoOperation = listOperationCompteNoOperation.get(0);
			assertEquals(operationCompteNoOperation.getLogin(), newUserLogin);
			assertEquals(operationCompteNoOperation.getLibelleOperation(), "Initialisation");
			assertEquals(operationCompteNoOperation.getMontantGlobal(), BigDecimal.valueOf(0));
			assertNull(operationCompteNoOperation.getMontantCredite());
			assertNull(operationCompteNoOperation.getMontantDebite());

		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void findAllUsers() {
		try {
			// 1er appel : recherche tous les utilisateurs
			List<User> listUser = userService.findAll();

			// 2eme appel : recherche tous les utilisateurs
			listUser = userService.findAll();

			// 3eme appel : recherche tous les utilisateurs
			listUser = userService.findAll();

			assertNotNull(listUser);
			assertEquals(listUser.size(), 2);

		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void createUserOk() {
		final String LOGIN = "Roger";
		final String PASSWORD = "pwd";
		final String ROLE = "admin";

		try {
			User user = new User();
			user.setLogin(LOGIN);
			user.setPassword(PASSWORD);
			user.setRole(userService.findRoleByLabel(ROLE));
			userService.createUser(user, PASSWORD);

			// Verification utilisateur.
			User userRecup = userService.findUserByLogin(LOGIN);
			assertNotNull(userRecup);
			assertEquals(userRecup.getId(), new Integer(3));
			assertEquals(userRecup.getLogin(), LOGIN);
			assertEquals(userRecup.getRole().getLabel(), ROLE);

			// Verification etat compte.
			EtatCompte etatCompte = banqueService.etatCompteLogin(LOGIN);
			assertNotNull(etatCompte);
			assertEquals(etatCompte.getLogin(), LOGIN);
			assertEquals(etatCompte.getMontant(), BigDecimal.valueOf(0));

			// Verification de l'utilisateur n'ayant fait aucune operation
			List<OperationCompte> listOperationCompteNoOperation = banqueService.findLastOperationCompteByLogin(LOGIN, 1);
			if ((listOperationCompteNoOperation == null) || (listOperationCompteNoOperation.isEmpty())) {
				assertEquals(true, false);
			}
			OperationCompte operationCompteNoOperation = listOperationCompteNoOperation.get(0);
			assertEquals(operationCompteNoOperation.getLogin(), LOGIN);
			assertEquals(operationCompteNoOperation.getLibelleOperation(), "Initialisation");
			assertEquals(operationCompteNoOperation.getMontantGlobal(), BigDecimal.valueOf(0));
			assertNull(operationCompteNoOperation.getMontantCredite());
			assertNull(operationCompteNoOperation.getMontantDebite());

		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void createUserKo() {
		try {
			User user = new User();
			user.setLogin(LOGIN_TEST);
			user.setRole(userService.findRoleByLabel("admin"));
			userService.createUser(user, "pass");
			assertEquals(true, false);
		} catch (LoginDejaExistantException exception) {
			assertEquals(true, true);
		}

		catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testListRoles() {
		try {

			List<Role> listRole = userService.findRoles();
			assertNotNull(listRole);
			assertEquals(listRole.size(), 2);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testFindAllParametres() {
		try {
			List<Parametre> listParametre = userService.findAllParametres();
			assertNotNull(listParametre);
			assertEquals(listParametre.size(), 2);

			for (Parametre parametre : listParametre) {
				if (parametre.getCode().equals(Parametre.NBPTMAXCOUGRA)) {
					assertEquals(parametre.getLabel(), LABEL_NBPTMAXCOUGRA);
					assertEquals(parametre.getDescription(), DESCRIPTION_NBPTMAXCOUGRA);
					assertEquals(parametre.getValeur(), VALEUR_NBPTAFFGRA);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testFindParametreByCode() {
		try {
			Parametre parametre = userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);
			assertNotNull(parametre);
			assertEquals(parametre.getCode(), Parametre.NBPTMAXCOUGRA);
			assertEquals(parametre.getLabel(), LABEL_NBPTMAXCOUGRA);
			assertEquals(parametre.getDescription(), DESCRIPTION_NBPTMAXCOUGRA);
			assertEquals(parametre.getValeur(), VALEUR_NBPTAFFGRA);

		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testFindParametreWrongCode() {
		try {
			userService.findParametreByCode("WRONGCODE");
			assertEquals(true, false);
		} catch (ParametreNotFoundException exception) {
			assertEquals(true, true);
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testUpdateParametre() {
		try {
			final String newLabel = "new label";
			final String newDescriptionLabel = "description label";
			final Integer newValeur = Integer.valueOf(12);
			userService.updateParametre(Parametre.NBPTMAXCOUGRA, newLabel, newDescriptionLabel, newValeur);

			Parametre parametreSearch = userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);
			if (parametreSearch.getCode().equals(Parametre.NBPTMAXCOUGRA)) {
				assertEquals(parametreSearch.getLabel(), newLabel);
				assertEquals(parametreSearch.getDescription(), newDescriptionLabel);
				assertEquals(parametreSearch.getValeur(), newValeur);
			}
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
	}

	@org.junit.Test
	public void testDeleteParametre(){
		try {
			userService.deleteParametre(Parametre.NBPTMAXCOUGRA);			
			userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);
			assertEquals(true, false);
		}catch(ParametreNotFoundException exception){
			assertEquals(true, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
		
	}
	
	@org.junit.Test
	public void testParameters(){
		try {
			userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);
			userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);
			userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);
			final String newLabel = "new label";
			final String newDescriptionLabel = "description label";
			final Integer newValeur = Integer.valueOf(12);
			userService.updateParametre(Parametre.NBPTMAXCOUGRA, newLabel, newDescriptionLabel, newValeur);
			Parametre parametreSearch = userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);

			assertEquals(parametreSearch.getLabel(), newLabel);
			assertEquals(parametreSearch.getDescription(), newDescriptionLabel);
			assertEquals(parametreSearch.getValeur(), newValeur);

			userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);
			userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);
			
			userService.deleteParametre(Parametre.NBPTMAXCOUGRA);			
			userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);
			userService.findParametreByCode(Parametre.NBPTMAXCOUGRA);

			
			assertEquals(true, false);
		}catch(ParametreNotFoundException exception){
			assertEquals(true, true);
		}
		catch (Exception e) {
			e.printStackTrace();
			assertEquals(true, false);
		}
		
	}


}
