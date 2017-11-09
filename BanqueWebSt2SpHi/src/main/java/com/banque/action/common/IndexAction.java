package com.banque.action.common;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport {
	
	public static final String SESSION_ROLE_KEY = "ROLE_KEY";
	
	public static final String SESSION_SESSION_DATA = "sessionData";
	
	/**
	 * Constante pour le role d'administrateur.<br/>
	 */
	public static final String ADMIN_ROLE = "admin";
	
	/**
	 * Constante pour le role d'utilisateur.<br/>
	 */
	public static final String USER_ROLE = "user";
	
	/**
	 * Serial UID.<br/> 
	 */
	private static final long serialVersionUID = 80434905327148157L;

	/**
	 * Pour afficher la page init.<br/>
	 * @return
	 */
	public String init(){
		
        final ActionProxy actionProxy = ActionContext.getContext().getActionInvocation().getProxy();
        
      
 		//Teste le role de l'utilisateur
		Map session = ActionContext.getContext().getSession();
		if (session != null){
			if (ServletActionContext.getRequest().isUserInRole(ADMIN_ROLE)){
				session.put(SESSION_ROLE_KEY, ADMIN_ROLE);
			}else if (ServletActionContext.getRequest().isUserInRole(USER_ROLE)){
				session.put(SESSION_ROLE_KEY, USER_ROLE);				
			}
			
			//Add a variable to the session
			session.put(SESSION_SESSION_DATA, "C0001212983");
		}
	      
		
		
		return SUCCESS;
	}

}
