<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.innowhite.whiteboard.persistence.dao.WhiteboardAuthenticationDAOImpl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<SCRIPT LANGUAGE="JavaScript">
<!-- Original:  Paul Deron (trombonepaul@yahoo.com) -->
<!-- Web Site:  http://paul1.web.com -->

<!-- This script and many more are available free online at -->
<!-- The JavaScript Source!! http://javascript.internet.com -->

<!-- Begin
function fullScreen(theURL) {

	testwindow=window.open(theURL);
	testwindow.moveTo(0,0);
	
}
//  End -->
</script>

</head>
<body>

<%

	String userName = request.getParameter("user");
	String val = request.getRemoteHost();
	StringBuffer sb = request.getRequestURL();
	String url = sb.toString();
 	if(url!= null)
 	{
 		if(url.contains("demo.innowhite.com"))
 			val="demo.innowhite.com";
 		else if (url.contains("127.0.0.1") || url.contains("localhost") )
 			val="127.0.0.1";
 		else if (url.contains("sambaG") || url.contains("sambag") )
 			val="74.193.127.14";
 		else if (url.contains("184.107.155.122") )
 			val="184.107.155.122";
 		else if (url.contains("demo.innowhite.com") )
 			val="demo.innowhite.com";
 			
 	}
 	
 	
 
 %>

<TABLE border="1">
  <TH>Room1<TH>
  <TR>
	<TD><A href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room1&user=John&roomLeader=true');">John</A></TD><TD>Group Leader</TD>
  </TR>
  
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room1&user=Mark');">Mark</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room1&user=Victor');">Victor</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room1&user=George');">George</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room1&previousSession=true');">View Session</a></TD>
  </TR>
  </TABLE>

<BR><BR>

 <TABLE border="1">
  <TH>Room2<TH>
  <TR>
	<TD><A href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room2&user=Harry&roomLeader=true');">Harry</A></TD><TD>Group Leader</TD>
	
  </TR>
  
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room2&user=Mark');">Mark</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room2&user=Victor');">Victor</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room2&user=George');">George</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room2&user=Hardy');">Hardy</a></TD><TD>Student</TD>
  </TR>
 <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room2&previousSession=true');">View Session</a></TD>
  </TR>
  </TABLE>


<BR><BR>
  
   <TABLE border="1">
  <TH>Room3<TH>
  <TR>
	<TD><A href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room3&user=Anita&roomLeader=true');">Anita</A></TD><TD>Group Leader</TD>
  </TR>
  
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room3&user=Mark');">Mark</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room3&user=Victor');">Victor</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room3&user=George');">George</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room3&user=Hardy');">Hardy</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room3&user=Tody');">Tody</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('JoinRoom?userName=<%=userName%>&orgName=DEFAULT_WB&roomId=room3&previousSession=true');">View Session</a></TD>
  </TR>
  </TABLE>

</body>
</html>