<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>

<head>
  <title>TBanqueDemo</title>	
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/style.css" />
</head>


<body>
 <div class="emplacementlogin">

   <TABLE BORDER="0">
     <tr>
		 <td> <span class="logintitre"> TBanqueWeb, the best bank of the world </span> </td>
	 </tr>
   </TABLE>


   <br/> <br/> <br/> <br/><br/><br/>

  <% // Si il y'a une erreur %>
  <TABLE BORDER="0">
  <tr>
    <td class="tdmessagelogin"> 
       <div id="idmessage" class="messageerreur">
          <s:actionerror />
          <s:fielderror />
       </div>  
     </td>
  </tr>
  </TABLE>

</body>
</html>
