package com.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.HttpRequestHandler;

import com.banque.modele.EtatCompte;
import com.banque.service.IBanque;
import com.banque.service.exception.CompteDebiteurException;
import com.banque.service.exception.LoginInexistantException;
import com.banque.service.exception.PlafondMaxAtteintException;
import com.banque.service.exception.TechnicalException;
import com.thoughtworks.xstream.XStream;

public class DispatchServlet implements HttpRequestHandler {

	private static final Log LOG = LogFactory.getLog(DispatchServlet.class);

	@Autowired
	@Qualifier("banqueService")
	private IBanque banqueService;

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		XStream xstream = new XStream();

		try {
			LOG.info("process" + request.getQueryString());
			LOG.info("process" + banqueService.toString());
			final String operation = request.getParameter("operation");
			final String xmlData = request.getParameter("xmlData");

			if ((operation != null) && (!operation.trim().equals(""))) {
				DispatchOperation dispatchOperation = DispatchOperation.getDispatchOperationFromCode(operation);
				if (dispatchOperation != null) {

					switch (dispatchOperation) {
					case CREDITER:
						xstream.processAnnotations(CrediterCompteRequest.class);
						xstream.processAnnotations(CrediterCompteResponse.class);
						CrediterCompteRequest crediterCompteRequest = (CrediterCompteRequest) xstream.fromXML(xmlData);
						try {
							banqueService.crediter(crediterCompteRequest.getLibelleOperation(),crediterCompteRequest.getLogin(), crediterCompteRequest.getMontant());
						} catch (LoginInexistantException e) {
							this.sendError(new ErrorResponse("LOGINE", "Login inexistant"), response);
						} catch (PlafondMaxAtteintException e) {
							this.sendError(new ErrorResponse("MAXPLA", "Max plafond"), response);
						} catch (TechnicalException e) {
							this.sendError(new ErrorResponse("ERRIND", "Erreur indefini"), response);
						}
						xstream.toXML(new CrediterCompteResponse(), response.getWriter());
						break;

					case DEBITER:
						xstream.processAnnotations(DebiterCompteRequest.class);
						xstream.processAnnotations(DebiterCompteResponse.class);
						DebiterCompteRequest debiterCompteRequest = (DebiterCompteRequest) xstream.fromXML(xmlData);
						try {
							banqueService.debiter(debiterCompteRequest.getLibelleOperation(),debiterCompteRequest.getLogin(), debiterCompteRequest.getMontant());
						} catch (LoginInexistantException e) {
							this.sendError(new ErrorResponse("LOGINE", "Login inexistant"), response);
						} catch (CompteDebiteurException e) {
							this.sendError(new ErrorResponse("COMPDE", "Compte debiteur erreur"), response);

						} catch (TechnicalException e) {
							this.sendError(new ErrorResponse("ERRIND", "Erreur indefini"), response);
						}
						xstream.toXML(new DebiterCompteResponse(), response.getWriter());
						break;

					case LIST_ETAT_COMPTE:
						xstream.processAnnotations(ListEtatCompteRequest.class);
						xstream.processAnnotations(ListEtatCompteResponse.class);

						ListEtatCompteRequest listEtatCompteRequest = (ListEtatCompteRequest) xstream.fromXML(xmlData);
						List<EtatCompte> listEtatCompte = banqueService.findListEtatCompte();

						ListEtatCompteResponse listEtatCompteResponse = new ListEtatCompteResponse();
						listEtatCompteResponse.setListEtatCompte(listEtatCompte);
						xstream.toXML(listEtatCompteResponse, response.getWriter());

						break;

					}
				}
			}
		} catch (Exception exception) {
			throw new ServletException(exception);
		}
	}

	private void sendError(final ErrorResponse errorCatResponse, HttpServletResponse response) throws Exception {
		XStream xstream = new XStream();
		xstream.processAnnotations(ErrorResponse.class);
		xstream.toXML(errorCatResponse, response.getWriter());
		LOG.debug("\n" + xstream.toXML(errorCatResponse));
	}

}
