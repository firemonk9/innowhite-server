<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<script language="JavaScript" type="text/javascript">
	
		
		
		
		
		
		function callFromJavaCloseOnly (msg)
	    {
	    	//alert(msg);
	    	//invokeFlexFunctions(msg);
	    	window.close();
	    	this.close();
	    	self.close();
	       //document.JTOJSForm.msg.value = msg;
	    }
	
		
	 	function callFromJava (msg)
	    {
	    	//alert(msg);
	    	invokeFlexFunctions(msg);
	    	window.close();
	    	this.close();
	    	self.close();
	       //document.JTOJSForm.msg.value = msg;
	    }
	
	
	function invokeFlexFunctions(fileNameStr)
	{
		
	    window.opener.invokeFlexFunctions(fileNameStr);
	    
	}
	
	function minThisPage(){
	
		window.blur();
		window.innerWidth = 100;
		window.innerHeight = 100;
		window.screenX = screen.width;
		window.screenY = screen.height;
		alwaysLowered = true;
	}
	
	</script>    
   
    
    <title>My JSP 'MyApplet.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body onload="minThisPage()">
  
   <APPLET code="com.adaequare.ecommunicator.screenshot.ScreenCap"  archive="ScreenShot.jar"
	width="100" height="100" mayscript>
	<param name="bgcolor" value="ffffff">
	<param name="USER_FILENAME" value="<%=request.getParameter("USER_FILENAME")%>">
	<param name="fontcolor" value="000000">
	Your browser is not Java enabled.
	</APPLET>
  
    Please Do not close this window. <br>
  </body>
</html>
