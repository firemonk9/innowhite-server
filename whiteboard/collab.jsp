
<%@page import="java.util.Enumeration"%><!-- saved from url=(0014)about:internet -->
<%@page import="java.util.StringTokenizer"%>
<%@page import="com.util.Constants"%>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 HttpSession mysession = request.getSession(true);
 System.err.println(" session id is   :"+mysession.getId()+" PS "+request.getParameter("previousSession")+"   "+request.getRequestURI()+"    "+request.getRequestURL());
 System.err.println(" the url is ########:   "+request.getRequestURL()+"   "+request.getQueryString());

 String previousSession = (String)request.getParameter("previousSession");
 String joinroom = (String)request.getParameter("joinroom");
 String clientname = (String)request.getParameter("user");
 String groupLeader = (String)request.getParameter("groupLeader");
 String classRoom = (String)request.getParameter("classRoom");
 String orgName = (String)request.getParameter("orgName");
 String view = (String)request.getParameter("view");
 String lessonId = (String)request.getParameter("lesson_plan_id");
 String courseId = (String)request.getParameter("course_id");
 String parentOrg = (String)request.getParameter("parentOrg");
 
 String hostURL =null;
	if (orgName.indexOf(Constants.WEB_DELIMITER) > 0) {
		StringTokenizer st = new StringTokenizer(orgName,
				Constants.WEB_DELIMITER);

		parentOrg = st.nextToken();
		orgName = hostURL= st.nextToken();
	}
 
	
		if(parentOrg != null)
			parentOrg = parentOrg.trim(); 
 if(groupLeader == null)
	groupLeader =  	(String)request.getParameter("roomLeader");

 
 if(joinroom == null)
 	joinroom = (String)request.getParameter("roomId");
 	
 	
 
   if(view != null)
   {
   		if(view.equals("INET_PUBLIC"))
		{
			view = "STUDENT_VIEW";
			parentOrg= "INET";
		}
		else if(view.equals("INET_PRIVATE"))
		{
			view = "TEACHER_VIEW";
			parentOrg= "INET";
		}
	
		   
   } else
	{
			view = "DEFAULT_VIEW";
			parentOrg= "DEFAULT_WB";
	}
   
 if(view != null && view.equals("PLAYBACK_VIEW"))
 	previousSession="true";	
 
 
 if(previousSession != null && previousSession.equals("true"))
 	groupLeader="false";
 
 
  System.err.println(" ###### "+joinroom+" clientname ## "+ clientname +"   "+ request.getParameter("roomId")+"   ");
 
  System.err.println(" previousSession"+previousSession+" joinroom "+joinroom+"  view  "+view+"  lessonId  "+lessonId+" course_id "+courseId+" orgName  "+orgName+"  clientname:"+clientname+"  groupLeader:"+groupLeader);
 
     Enumeration Names = request.getParameterNames();
    while(Names.hasMoreElements()) {
      String str = (String)Names.nextElement();
      System.out.println("<tr><td>" + str + "</td><td>");
      String[] Values = request.getParameterValues(str);
      if (Values.length == 1) {
        String paramValue = Values[0];
        if (paramValue.length() == 0)
          System.out.println("<I>No Value</I>");
        else
          System.out.println(paramValue);
      }
      else {
        System.out.println("<UL>");
        for(int i=0; i<Values.length; i++) {
          System.out.println("<LI>" + Values[i]);
        }
        System.out.println("</UL>");
      }
    }
   

    System.err.println(" what the hell ....  parent ORG in jsp  "+parentOrg+"  orgname "+orgName);
%>

<html lang="en">

<!-- 
Smart developers always View Source. 

This application was built using Adobe Flex, an open source framework
for building rich Internet applications that get delivered via the
Flash Player or to desktops via Adobe AIR. 

Learn more about Flex at http://flex.org 
// -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!--  BEGIN Browser History required section -->
<link rel="stylesheet" type="text/css" href="history/history.css" />
<!--  END Browser History required section -->

<title></title>
<script src="AC_OETags.js" language="javascript"></script>

<!--  BEGIN Browser History required section -->
<script src="history/history.js" language="javascript"></script>
<!--  END Browser History required section -->

<style>
body {
	margin: 0px;
	overflow: hidden
}
</style>
<script language="JavaScript" type="text/javascript">
<!--
// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 9;
// Minor version of Flash required
var requiredMinorVersion = 0;
// Minor version of Flash required
var requiredRevision = 28;
// -----------------------------------------------------------------------------
// -->
</script>
</head>


<body scroll="no">
<script language="JavaScript" type="text/javascript">
<!--
// Version check for the Flash Player that has the ability to start Player Product Install (6.0r65)
var hasProductInstall = DetectFlashVer(6, 0, 65);

// Version check based upon the values defined in globals
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

if ( hasProductInstall && !hasRequestedVersion ) {
	// DO NOT MODIFY THE FOLLOWING FOUR LINES
	// Location visited after installation is complete if installation is required
	var MMPlayerType = (isIE == true) ? "ActiveX" : "PlugIn";
	var MMredirectURL = window.location;
    document.title = document.title.slice(0, 47) + " - Flash Player Installation";
    var MMdoctitle = document.title;

	AC_FL_RunContent(
		"src", "playerProductInstall",
		"FlashVars", "MMredirectURL="+MMredirectURL+'&MMplayerType='+MMPlayerType+'&MMdoctitle='+MMdoctitle+"",
		"width", "100%",
		"height", "100%",
		"align", "middle",
		"id", "whiteboardF",
		"quality", "high",
		"bgcolor", "#ffffff",
		"name", "whiteboardF",
		"allowScriptAccess","sameDomain",
		"type", "application/x-shockwave-flash",
		"pluginspage", "http://www.adobe.com/go/getflashplayer",
		"allowFullScreen","true"
	);
} else if (hasRequestedVersion) {
	// if we've detected an acceptable version
	// embed the Flash Content SWF when all tests are passed
	AC_FL_RunContent(
			"src", "whiteboardF",
			"width", "100%",
			"height", "100%",
			"align", "middle",
			"id", "whiteboardF",
			"quality", "high",
			"bgcolor", "#ffffff",
			"flashVars","classRoom=<%=classRoom%>&parentOrg=<%=parentOrg%>&courseId=<%=courseId%>&orgName=<%= orgName %>&view=<%= view %>&lessonId=<%= lessonId %>&previousSession=<%= previousSession %>&joinroom=<%= joinroom %>&clientname=<%= clientname %>&groupLeader=<%= groupLeader %>",
			"name", "whiteboardF",
			"allowScriptAccess","sameDomain",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer",
			"allowFullScreen","true"
	);
  } else {  // flash is too old or we can't detect the plugin
    var alternateContent = 'Alternate HTML content should be placed here. '
  	+ 'This content requires the Adobe Flash Player. '
   	+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
    document.write(alternateContent);  // insert non-flash content
  }
// -->
</script>
<noscript>
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="whiteboardF" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="whiteboardF.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="allowScriptAccess" value="sameDomain"
				param name="allowFullScreen" value="true"/>
		
			<embed src="whiteboardF.swf" quality="high" bgcolor="#ffffff"
				width="100%" height="100%" name="whiteboardF" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash" allowFullScreen="true"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object>
</noscript>
</body>
</html>
