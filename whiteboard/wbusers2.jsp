
<%@page import="com.db.custom.CustomUserImagesDAO"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE> New Document </TITLE>
  <META NAME="Generator" CONTENT="EditPlus">
  <META NAME="Author" CONTENT="">
  <META NAME="Keywords" CONTENT="">
  <META NAME="Description" CONTENT="">
 </HEAD>

<%
System.out.println("session is :::  "+request.getSession(false));
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 

 System.out.println("session is :::  "+request.getSession(false));
%>

<SCRIPT LANGUAGE="JavaScript">
<!-- Original:  Paul Deron (trombonepaul@yahoo.com) -->
<!-- Web Site:  http://paul1.web.com -->

<!-- This script and many more are available free online at -->
<!-- The JavaScript Source!! http://javascript.internet.com -->

<!-- Begin
function fullScreen(theURL) {
window.open(theURL, '', 'fullscreen=yes, scrollbars=auto,resizable=Yes');
}
//  End -->
</script>


 <BODY>
  <TABLE border="1">
  
  <a href="<%=basePath%>/NewSession.jsp">create New Room</a>
  
  <TH>Room1<TH>
  <TR>
	<TD><A href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room1&clientname=John&groupLeader=true');">John</A></TD><TD>Group Leader</TD>
  </TR>
  
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room1&clientname=Mark');">Mark</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room1&clientname=Victor');">Victor</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room1&clientname=George');">George</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/PlayBack.jsp?joinroom=room1&previousSession=true');">View Session</a></TD>
  </TR>
  </TABLE>

<BR><BR>

 <TABLE border="1">
  <TH>Room2<TH>
  <TR>
	<TD><A href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room2&clientname=Harry&groupLeader=true');">Harry</A></TD><TD>Group Leader</TD>
	
  </TR>
  
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room2&clientname=Mark');">Mark</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room2&clientname=Victor');">Victor</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room2&clientname=George');">George</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room2&clientname=Hardy');">Hardy</a></TD><TD>Student</TD>
  </TR>
 <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/PlayBack.jsp?joinroom=room2&previousSession=true');">View Session</a></TD>
  </TR>
  </TABLE>


<BR><BR>
  
   <TABLE border="1">
  <TH>Room3<TH>
  <TR>
	<TD><A href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room3&clientname=Anita&groupLeader=true');">Anita</A></TD><TD>Group Leader</TD>
  </TR>
  
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room3&clientname=Mark');">Mark</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room3&clientname=Victor');">Victor</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room3&clientname=George');">George</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room3&clientname=Hardy');">Hardy</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/MyJsp.jsp?joinroom=room3&clientname=Tody');">Tody</a></TD><TD>Student</TD>
  </TR>
  <TR>
	<TD><a href="javascript:void(0);" onClick="fullScreen('http://184.107.155.122:5080/whiteboard/PlayBack.jsp?joinroom=room3&previousSession=true');">View Session</a></TD>
  </TR>
  </TABLE>
  
 </BODY>
</HTML>
