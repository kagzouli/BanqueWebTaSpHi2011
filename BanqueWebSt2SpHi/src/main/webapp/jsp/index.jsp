<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>

<ul>
 <s:if test="#session.ROLE_KEY == 'user'">
  <li><a href="<s:url value='/banque/InitCrediter '/> "> <s:text name="text.credit.account" /></a></li>
  <li><a href="<s:url value='/banque/debiter'/> "> <s:text name="text.debit.account" /> </a></li>
  <li><a href="<s:url value='/banque/virement'/> "> <s:text name="text.transfert.account" /> </a></li> 
  <li><a href="<s:url value='/banque/rechercheListeCompte'/> "><s:text name="text.search.account"/> </a></li> 
 </s:if>
 
 <s:if test="#session.ROLE_KEY == 'admin'">
  <li><a href="<s:url value='/user/creation'/> "> <s:text name="text.create.user" /> </a></li> 
  <li><a href="<s:url value='/user/performanceUser'/> "> <s:text name="text.performance.user" /> </a></li> 
  <li><a href="<s:url value='/user/listeParametre'/> "> <s:text name="text.parameters" /> </a></li> 
 </s:if>
</ul>
