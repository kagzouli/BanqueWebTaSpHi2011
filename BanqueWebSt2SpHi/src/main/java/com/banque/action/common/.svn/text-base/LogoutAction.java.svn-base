package com.banque.action.common;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Logout.<br/>
 * @author Karim
 *
 */
public class LogoutAction extends ActionSupport{

	/**
	 * Serial UID.<br/>
	 */
	private static final long serialVersionUID = 8550494154527112864L;
	
	public String logout(){
		//Gere le logout
		Map mapSession = ActionContext.getContext().getSession();
	
		if ((mapSession != null) && (mapSession instanceof SessionMap)){
			mapSession.clear();
			((SessionMap) mapSession).invalidate();
		}
		
		
		return SUCCESS;
		
	}

}
