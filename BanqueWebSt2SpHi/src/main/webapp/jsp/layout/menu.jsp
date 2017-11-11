<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="menu">
  <ul>      
     <s:if test="#session.ROLE_KEY == 'user'">
    	<li><a href="<s:url value='/banque/InitCrediter'/> "><s:text name="text.credit.account" /></a></li> 
     </s:if>
  
     <s:if test="#session.ROLE_KEY == 'admin'">  
     </s:if>
     
      <li><a href="<s:url value='/LogoutAction'/> "> <s:text name="text.logout" /> </a></li>  

  
</div>
