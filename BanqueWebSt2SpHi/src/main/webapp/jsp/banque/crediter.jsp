<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<br/>
<br/>
<center><span class="titlepage"><s:text name="crediterpage.text.page" /></span></center>
    <br/>
    <br/>

		<s:actionerror />

    <br/>
 
   <s:form id="crediterForm" name="crediterForm" action="/banque/CrediterAction" validate="true" method ="POST">
   
    	
        <s:textfield name="login" id="login" key="login.field" labelposition="left" />
		 
		    <s:textfield name="labelOperation" id="labelOperation" key="operation.field" labelposition="left" />
		 
        <s:textfield name="montant" id="montant" key="amount.field" labelposition="left" />
		
        <s:submit key="submit.field" validate="true" />
        
   </s:form>
