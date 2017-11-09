package com.banque.java.pages.user;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.services.BeanModelSource;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.banque.bean.TypeAffichageOperationCompte;
import com.banque.java.components.GenericSelectModel;
import com.banque.modele.OperationCompte;
import com.banque.modele.Parametre;
import com.banque.modele.User;
import com.banque.service.IBanque;
import com.banque.service.IUserService;

public class PerformanceUser {

	@Component(id = "performanceuser_form")
	private Form performanceUserForm;

	/** Selection utilisateur **/
	@Component
	private Select user;

	/** Type affichage **/
	@Component
	private Select typeAffichage;

	/** Service utilisateur **/
	@Inject
	private IUserService userService;

	/** Service utilisateur **/
	@Inject
	private IBanque banqueService;

	/** Operation compte choisi dans le tableau **/
	private OperationCompte operationCompte;

	@Inject
	private PropertyAccess _access;

	/** Pour la liste des utilisateurs **/
	private GenericSelectModel<User> modelUsers;

	/** Pour la liste du type d'affichage **/
	private GenericSelectModel<TypeAffichageOperationCompte> modelTypeAffichage;

	/** Pour ajax : zone affichage graphique **/
	@Component
	private Zone zoneRechercheUser;

	/** Selection utilisateur **/
	@Persist
	@Validate("required")
	private User userChoose;

	/** Selection type affichage **/
	@Persist
	@Validate("required")
	private TypeAffichageOperationCompte typeAffichageChoose;

	/** Verifie si l'affichage est en mode texte. **/
	private String isViewText;

	/** Verifie si l'affichage est en mode graphique. **/
	private String isViewGraph;


	/** Liste des operations **/
	private List<OperationCompte> listOperationCompte;

	/** Bean model source **/
	@Inject
	private BeanModelSource beanModelSource;

	/** Message **/
	@Inject
	private Messages messages;

	/** Date format pour l'initialisation **/
	private static final SimpleDateFormat DATE_FORMAT_SOLDE_INIT = new SimpleDateFormat("dd/MM/yyyy");

	void onActivate() throws Exception {
		// Liste des utilisateurs
		List<User> listUser = userService.findAll();
		if (listUser != null) {
			Collections.sort(listUser);
			modelUsers = new GenericSelectModel<User>(listUser, User.class, "login", "login", _access);
			if (userChoose == null) {
				if ((listUser != null) && (listUser.size() >= 1)) {
					userChoose = listUser.get(0);
				}
			}
		}

		// On recupere la liste des operations compte en base
		if ((userChoose != null) && (!StringUtils.isEmpty(userChoose.getLogin()))) {
			Integer nbMaxCourbeGraph = this.findNbMaxCourbeGraph();
			this.listOperationCompte = banqueService.findLastOperationCompteByLogin(userChoose.getLogin(), nbMaxCourbeGraph);
			if (this.listOperationCompte != null){
				// On inverse la liste pour avoir l'ordre chronologique.
				Collections.reverse(this.listOperationCompte);				
			}

		}

		// Liste des types d'affichage
		List<TypeAffichageOperationCompte> listTypeAffichage = new ArrayList<TypeAffichageOperationCompte>();
		listTypeAffichage.add(new TypeAffichageOperationCompte(TypeAffichageOperationCompte.CODE_TYPE_TEXT, messages.get("typeaffichage.codetext.label")));
		//On ne l'affiche que s'il y'a X elements parametrables.
		Integer nbPointAffichGraph = findNbPointAffichGraph();
		if ((this.listOperationCompte != null) && (this.listOperationCompte.size() >= nbPointAffichGraph)){
			listTypeAffichage.add(new TypeAffichageOperationCompte(TypeAffichageOperationCompte.CODE_TYPE_GRAPH, messages.get("typeaffichage.codegraph.label")));			
		}
		modelTypeAffichage = new GenericSelectModel<TypeAffichageOperationCompte>(listTypeAffichage, TypeAffichageOperationCompte.class, "libelleTypeAffichage", "codeTypeAffichage", _access);

		// Par default, on selectionne texte ou si on a un seul dans la liste type affichage.
		if ((this.typeAffichageChoose == null) || (listTypeAffichage.size() <= 1)) {
			this.typeAffichageChoose = listTypeAffichage.get(0);
		}
		
		

	}

	public List<OperationCompte> getListOperationCompte() throws Exception {

		try {
			if ((this.listOperationCompte != null) & (!this.listOperationCompte.isEmpty())) {
				BigDecimal montantGlobal = null;
				BigDecimal totalMontantCredite = BigDecimal.valueOf(0);
				BigDecimal totalMontantDebite = BigDecimal.valueOf(0);

				// On change le 1er libelle
				OperationCompte firstOperation = this.listOperationCompte.get(0);
				firstOperation.setLibelleOperation(messages.get("solde.crediteur.label") + " " + DATE_FORMAT_SOLDE_INIT.format(firstOperation.getDateOperation()));
				firstOperation.setMontantCredite(null);
				firstOperation.setMontantDebite(null);
				
				// On enleve les montants globaux sauf pour le premier.
				for (int indx = 1; indx < (this.listOperationCompte.size()); indx++) {
					OperationCompte operationCompte = this.listOperationCompte.get(indx);
					montantGlobal = operationCompte.getMontantGlobal();
					operationCompte.setMontantGlobal(null);

					if (operationCompte.getMontantCredite() != null) {
						totalMontantCredite = totalMontantCredite.add(operationCompte.getMontantCredite());
					}
					if (operationCompte.getMontantDebite() != null) {
						totalMontantDebite = totalMontantDebite.add(operationCompte.getMontantDebite());
					}
				}

				if (this.listOperationCompte.size() > 1) {

					// On rajoute un enregistrement contenant le total des
					// montants
					OperationCompte operationTotalMontant = new OperationCompte();
					operationTotalMontant.setLibelleOperation(messages.get("total.amount.label"));
					operationTotalMontant.setMontantCredite(totalMontantCredite);
					operationTotalMontant.setMontantDebite(totalMontantDebite);
					this.listOperationCompte.add(operationTotalMontant);

					// On rajoute le dernier enregistrement contenant le total.
					OperationCompte lastOperationCompte = new OperationCompte();
					lastOperationCompte.setDateOperation(new Date());
					lastOperationCompte.setMontantGlobal(montantGlobal);
					this.listOperationCompte.add(lastOperationCompte);
				}

			}
		} catch (Exception exception) {
			throw exception;
		}
		return listOperationCompte;
	}

	public BeanModel<OperationCompte> getModel() {
		BeanModel<OperationCompte> model = beanModelSource.createDisplayModel(OperationCompte.class, messages);
		model.exclude("idCompte");
		model.exclude("login");

		model.reorder("dateOperation", "libelleOperation", "montantDebite", "montantCredite", "montantGlobal");

		// Ajout d'une colonne edit au tableau
		model.get("dateOperation").label(messages.get("dateOperation.field"));
		model.get("dateOperation").sortable(false);
		model.get("libelleOperation").label(messages.get("libelleoperation.field"));
		model.get("libelleOperation").sortable(false);
		model.get("montantDebite").label(messages.get("debit.field"));
		model.get("montantDebite").sortable(false);
		model.get("montantCredite").label(messages.get("credit.field"));
		model.get("montantCredite").sortable(false);
		model.get("montantGlobal").label(messages.get("montanttotal.field"));
		model.get("montantGlobal").sortable(false);
		return model;
	}

	@SuppressWarnings("deprecation")
	public JFreeChart getLineChart() {
		JFreeChart chart = null;

		if (this.listOperationCompte != null) {
			//Recupere la liste des montants globaux pour avoir la valeur min et max.
			List<BigDecimal> listMontantGlob = new ArrayList<BigDecimal>();
			for (OperationCompte operationCompte : this.listOperationCompte){
				listMontantGlob.add(operationCompte.getMontantGlobal());
			}
			BigDecimal minValue = Collections.min(listMontantGlob);
			minValue.setScale(10,2);
			BigDecimal maxValue = Collections.max(listMontantGlob);
			maxValue.setScale(10,2);

			// Chargement des points pour la courbe.
			int indxOperationCompte = 1;
			XYSeries series = new XYSeries(messages.get("chart.ylabel"));
			for (OperationCompte operationCompte : this.listOperationCompte) {
				series.add(indxOperationCompte, operationCompte.getMontantGlobal());
				indxOperationCompte++;
			}
			XYDataset xyDataset = new XYSeriesCollection(series);

			chart = ChartFactory.createXYLineChart(messages.get("chart.title"), // Title
					messages.get("chart.xlabel"), // X-Axis label
					messages.get("chart.ylabel"), // Y-Axis label
					xyDataset, // Dataset
					PlotOrientation.VERTICAL, // Orientation de la courbe
					true, // Show legend
					false, // Genere des tools tips
					false);
			
			chart.setBackgroundPaint(Color.white);
			
			XYPlot xyPlot = chart.getXYPlot();
			xyPlot.setBackgroundPaint(Color.white);
			
			//Axe y
			xyPlot.getRangeAxis().setRange(minValue.doubleValue(), maxValue.doubleValue());
			
			//Axe x
			xyPlot.getDomainAxis().setAutoRangeMinimumSize(1);
			xyPlot.getDomainAxis().setAutoRange(false);
			xyPlot.getDomainAxis().setVisible(false);

			//La courbe a la couleur rouge
			xyPlot.getRenderer().setSeriesPaint(0, Color.red);

			
			
		}

		return chart;
	}
	
	/**
	 * Methode permettant de donner le nombre de points a partir duquel on affiche le graphe.<br/>
	 * @return Nombre de points a partir duquel on affiche le graphe.<br/>
	 */
	private Integer findNbPointAffichGraph(){
		Integer nbPointAffichGraph = null;
		try{
			nbPointAffichGraph = userService.findParametreByCode(Parametre.NBPTAFFGRA).getValeur();		
		}catch(Exception exception){
			//S'il y'a une exception, on prend la valeur par defaut 3.
			nbPointAffichGraph = Integer.valueOf(3);
		}
		return nbPointAffichGraph;
	}
	
	/**
	 * Nombre le maximum de points sur la courbe du graphique.<br/>
	 * @return  Renvoie le nombre maximum de points sur la courbe du graphique.<br/>
	 */
	private Integer findNbMaxCourbeGraph(){
		Integer nbMaxCourbeGraph = null;
		try{
			nbMaxCourbeGraph = userService.findParametreByCode(Parametre.NBPTMAXCOUGRA).getValeur();		
		}catch(Exception exception){
			//S'il y'a une exception, on prend la valeur par defaut 10.
			nbMaxCourbeGraph = Integer.valueOf(10);
		}
		return nbMaxCourbeGraph;

	}

	public Zone onSuccess() throws Exception {
		return zoneRechercheUser;
	}

	public GenericSelectModel<User> getModelUsers() {
		return modelUsers;
	}

	public void setModelUsers(GenericSelectModel<User> modelUsers) {
		this.modelUsers = modelUsers;
	}

	public GenericSelectModel<TypeAffichageOperationCompte> getModelTypeAffichage() {
		return modelTypeAffichage;
	}

	public void setModelTypeAffichage(GenericSelectModel<TypeAffichageOperationCompte> modelTypeAffichage) {
		this.modelTypeAffichage = modelTypeAffichage;
	}

	public Zone getZoneRechercheUser() {
		return zoneRechercheUser;
	}

	public void setListOperationCompte(List<OperationCompte> listOperationCompte) {
		this.listOperationCompte = listOperationCompte;
	}

	public void setZoneRechercheUser(Zone zoneRechercheUser) {
		this.zoneRechercheUser = zoneRechercheUser;
	}

	public User getUserChoose() {
		return userChoose;
	}

	public void setUserChoose(User userChoose) {
		this.userChoose = userChoose;
	}

	public OperationCompte getOperationCompte() {
		return operationCompte;
	}

	public void setOperationCompte(OperationCompte operationCompte) {
		this.operationCompte = operationCompte;
	}

	public TypeAffichageOperationCompte getTypeAffichageChoose() {
		return typeAffichageChoose;
	}

	public void setTypeAffichageChoose(TypeAffichageOperationCompte typeAffichageChoose) {
		this.typeAffichageChoose = typeAffichageChoose;
	}

	public String getIsViewText() {
		return String.valueOf(TypeAffichageOperationCompte.CODE_TYPE_TEXT.equals(typeAffichageChoose.getCodeTypeAffichage()));
	}

	public String getIsViewGraph() {
		return String.valueOf(TypeAffichageOperationCompte.CODE_TYPE_GRAPH.equals(typeAffichageChoose.getCodeTypeAffichage()));
	}

}
