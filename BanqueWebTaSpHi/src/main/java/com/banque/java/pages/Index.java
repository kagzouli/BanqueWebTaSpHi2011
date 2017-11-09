package com.banque.java.pages;

import javax.servlet.http.HttpSession;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

public class Index {
	
	@SessionState
	@Property
	private String sessionData;
	
	private String roleAdmin;
	
	private String roleUser;
	
	@Inject
	private RequestGlobals requestGlobals;

	
	public void onActivate() {
		if (requestGlobals.getHTTPServletRequest().getSession() != null){
			this.sessionData = "C000233423342";			
		}
		
	}
	
	public String getRoleAdmin() {
		return String.valueOf(requestGlobals.getHTTPServletRequest().isUserInRole("admin"));
	}

	public String getRoleUser() {
		return String.valueOf(requestGlobals.getHTTPServletRequest().isUserInRole("user"));
	}
	
	

}