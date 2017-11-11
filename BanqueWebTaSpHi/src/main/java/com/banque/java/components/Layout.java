package com.banque.java.components;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Session;

import com.banque.java.pages.Index;

@Import(stylesheet="context:style.css")
public class Layout {

	@Property
	@Inject
	@Path("context:images/drap_fr.gif")
	private Asset imageFr;
	
	@Inject
	private RequestGlobals requestGlobals;

	@Inject
	private Request request;

	@Property
	@Inject
	@Path("context:images/drap_GB.gif")
	private Asset imageEn;

	@Inject
	private PersistentLocale localeService;

	public Object onActionFromChooseFrLocal() {
		localeService.set(Locale.FRENCH);
		return this;
	}

	public Object onActionFromChooseEnLocal() {
		localeService.set(Locale.ENGLISH);
		return this;
	}
	
	public String getRoleAdmin() {
		return String.valueOf(requestGlobals.getHTTPServletRequest().isUserInRole("admin"));
	}

	public String getRoleUser() {
		return String.valueOf(requestGlobals.getHTTPServletRequest().isUserInRole("user"));
	}

	@OnEvent(component = "logout")	
	public Object onActionFromLogout()
    {
		// Invalide la session tapestry
		Session tapestrySession = request.getSession(false);
		if (tapestrySession != null){
			tapestrySession.invalidate();
		}
		
		// Invalide la session HTTP
    	HttpSession httpSession = requestGlobals.getHTTPServletRequest().getSession(false);
    	if (httpSession != null){
    		httpSession.invalidate();    		
    	}
  
    	return Index.class;
    }


	
	

}
