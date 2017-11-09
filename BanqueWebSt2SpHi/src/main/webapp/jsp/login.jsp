<html>

<head>

  <title>TBanqueDemo</title>	
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css" />
  
  <script language="JavaScript">
  function donner_focus(chp)
  {
    document.getElementById(chp).focus();
  }
  </script>


</head>


<body onload="donner_focus('loginid')">
 <div class="emplacementlogin">

   <TABLE BORDER="0">
     <tr>
		 <td> <span class="logintitre"> TBanqueWeb, the best bank of the world </span> </td>
	 </tr>
   </TABLE>


<br/> <br/> <br/>

<% // Si il y'a une erreur %>
<TABLE BORDER="0">
  <tr>
    <td class="tdmessagelogin"> 
    
	<%
	String authentificate = request.getParameter("authentificate");
			if ("false".equals(authentificate)){
		%>
       <div id="idmessage" class="messageerreur">  
         User with this login/password not found.
      </div>  
    <% } %>   
      
   </td>
  </tr>
</TABLE>

<br/><br/>

 <form action="<%=request.getContextPath()%>/j_security_check" method="POST">
  <table class="tablelogin" cellspacing="15">
    <col width="180" />
    <col width="50" />
    <col width="180" />
  
    <tr>
      <td><span class="labelfield">Login</span></td>
      <td><span class="labelfield">:</span></td>
      <td><input id="loginid" type="text" name="j_username" /></td>
    </tr>
    <tr>
      <td><span class="labelfield">Password</span></td>
      <td><span class="labelfield">:</span></td>
      <td><input type="password" name="j_password" /></td>
    </tr>
    <tr>
       <td colspan="3"><center><input type="submit" value="Submit" class="styleButton" /></center></td>
    </tr>
  </table>
</form>
<br/>	
 </div>
 
 <br/>
 <br/>
 <br/>
 <br/>
   <center> <span class="labelfield">(*) Fait avec Struts2/Spring/Hibernate</span></center>
 
</body>
</html>