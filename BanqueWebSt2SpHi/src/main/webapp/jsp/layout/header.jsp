<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.opensymphony.xwork2.ActionProxy"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page import="org.apache.struts2.ServletActionContext"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.lang.StringBuilder"%>


<%
             final ActionProxy actionProxy = ActionContext.getContext().getActionInvocation().getProxy();
             final String actionName = actionProxy.getNamespace()+"/"+actionProxy.getActionName();
             
		     StringBuilder parametersString = new StringBuilder(40);
	  		 final Map<String,String []> mapParameters = ServletActionContext.getRequest().getParameterMap();
   			 if (mapParameters != null){
   				for (Map.Entry<String,String[]> parameter : mapParameters.entrySet()){
   				  if (!"request_locale".equals(parameter.getKey())){
   					  parametersString.append("&").append(parameter.getKey()).append("=").append(parameter.getValue()[0]);   				  
   				  }
   				}
   			 }
               
             final String linkFr = request.getContextPath()+actionName+".action?request_locale=fr"+parametersString.toString();
             final String linkUk = request.getContextPath()+actionName+".action?request_locale=en"+parametersString.toString();
%>	


<div id="header">
  <h1><a href="#">TBanqueDemo en ligne </a>
     <a href="<%=linkFr%>"><img src="<%=request.getContextPath()%>/images/drap_fr.gif" border="0" /></a>
     <a href="<%=linkUk%>"><img src="<%=request.getContextPath()%>/images/drap_GB.gif" border="0" /></a>
  </h1>
</div>
