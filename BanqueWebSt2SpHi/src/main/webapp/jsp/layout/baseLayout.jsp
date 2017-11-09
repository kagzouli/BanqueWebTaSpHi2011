<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
 "http://www.w3.org/TR/html4/loose.dtd">  
 
<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>

 

 
<html>
<head>
<title>TBanqueDemo</title>	
<meta http-equiv="Content-Type" content="text/html; charset=${message:page.encoding}" />
<link rel="stylesheet" type="text/css" href="<s:url value='/style.css'/>"" media="screen" />
</head>
<body>

<div id="page">

  <tiles:insertAttribute name="header" />

  <tiles:insertAttribute name="menu" />


<div id="wrapper">
  <div id="content">
 
	  <tiles:insertAttribute name="body" />
         
  </div>
  <div style="clear: both;"> </div>
</div>

 <tiles:insertAttribute name="footer" />


</div>

</body>

</html>     