<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>

<center><span class="titlepage"><s:text name="viewetatcompte.text.page" /></span></center>
<br/>
<br/>
  
<table cellspacing="15">
	<colgroup>
    <col width="200" />
    <col width="50" />
    <col width="200" />
  </colgroup>
  <tr>
		<td> <span class="labelfield"><s:text name="login.field" /></span></td>
		<td>:</td>
		<td><s:property value="%{etatCompteForm.login}"/> </td>
	</tr>
	<tr>
		<td> <span class="labelfield"><s:text name="amount.field" /></span></td>
		<td>:</td>
		<td><s:property value="%{etatCompteForm.montant}"/></td>
	</tr>
	<tr>
		<td> <span class="labelfield"><s:text name="viewetatcompte.sessiondata.field" /></span></td>
		<td>:</td>
		<td><s:property value="%{#session.sessionData}"/></td>
 </tr>
 <tr>
		 <td colspan="3"><center><a href="<s:url value='/IndexAction'/> "> <s:text name="text.back" /></a></center></td>
 </tr> 
</table>
