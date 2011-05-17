
<%@page import="com.innowhite.whiteboard.util.InnowhiteConstants"%>
<%@page import="java.util.Enumeration"%><!-- saved from url=(0014)about:internet -->
<%@page import="java.util.StringTokenizer"%>
<%@page import="com.util.Constants"%>


<%
	String userName = request.getParameter("userName");
if(userName==null || userName.equals("null")){
	userName = "whiteboardF";
}
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	HttpSession mysession = request.getSession(true);
	System.err.println(" session id is   :" + mysession.getId()
			+ " PS " + request.getParameter("previousSession") + "   "
			+ request.getRequestURI() + "    "
			+ request.getRequestURL());
	System.err.println(" the url is ########:   "
			+ request.getRequestURL() + "   "
			+ request.getQueryString());

	String previousSession = (String) request
			.getParameter("previousSession");
	String joinroom = (String) request.getParameter("joinroom");
	String clientname = (String) request.getParameter("user");
	String groupLeader = (String) request.getParameter("groupLeader");
	String classRoom = (String) request.getParameter("classRoom");
	String orgName = (String) request.getParameter("orgName");
	String view = (String) request.getParameter("view");
	String lessonId = (String) request.getParameter("lesson_plan_id");
	String courseId = (String) request.getParameter("course_id");
	
	String phoneNum = (String) request.getAttribute(InnowhiteConstants.PHONE_NUM);
	String meetingNum = (String) request.getAttribute(InnowhiteConstants.MEETING_NUM);
	

	String parentOrg = null;
	if (orgName.indexOf(Constants.WEB_DELIMITER) > 0) {
		StringTokenizer st = new StringTokenizer(orgName,
				Constants.WEB_DELIMITER);

		parentOrg = st.nextToken();
		orgName = st.nextToken();
	}

	if (groupLeader == null)
		groupLeader = (String) request.getParameter("roomLeader");

	if (joinroom == null)
		joinroom = (String) request.getParameter("roomId");

	if (view != null) {
		if (view.equals("INET_PUBLIC")) {
			view = "STUDENT_VIEW";
			parentOrg = "INET";
		} else if (view.equals("INET_PRIVATE")) {
			view = "TEACHER_VIEW";
			parentOrg = "INET";
		}

	} else {
		view = "DEFAULT_VIEW";
		parentOrg = "DEFAULT_WB";
	}

	if (view != null && view.equals("PLAYBACK_VIEW"))
		previousSession = "true";

	if (previousSession != null && previousSession.equals("true"))
		groupLeader = "false";

	System.err.println(" ###### " + joinroom + " clientname ## "
			+ clientname + "   " + request.getParameter("roomId")
			+ "   ");

	System.err.println(" previousSession" + previousSession
			+ " joinroom " + joinroom + "  view  " + view
			+ "  lessonId  " + lessonId + " course_id " + courseId
			+ " orgName  " + orgName + "  clientname:" + clientname
			+ "  groupLeader:" + groupLeader);

	Enumeration Names = request.getParameterNames();
	while (Names.hasMoreElements()) {
		String str = (String) Names.nextElement();
		System.out.println("<tr><td>" + str + "</td><td>");
		String[] Values = request.getParameterValues(str);
		if (Values.length == 1) {
			String paramValue = Values[0];
			if (paramValue.length() == 0)
				System.out.println("<I>No Value</I>");
			else
				System.out.println(paramValue);
		} else {
			System.out.println("<UL>");
			for (int i = 0; i < Values.length; i++) {
				System.out.println("<LI>" + Values[i]);
			}
			System.out.println("</UL>");
		}
	}
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


	<script src="http://jquery.com/src/jquery-latest.pack.js">
	</script>

<!--  BEGIN Browser History required section -->
<link rel="stylesheet" type="text/css" href="history/history.css" />
<!--  END Browser History required section -->

<title></title>
<script src="AC_OETags.js" language="javascript"></script>

<!--  BEGIN Browser History required section -->
<script src="/whiteboard/js/jquery.DOMWindow.js" language="javascript"></script>
<!--  END Browser History required section -->

<style>
body {
	margin: 0px;
	overflow: hidden
}
</style>
<script language="JavaScript" type="text/javascript">
// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 10;
// Minor version of Flash required
var requiredMinorVersion = 1;
// Minor version of Flash required
var requiredRevision = 28;
// Minor version of Flash required
var innowhitePluginLoaded = false;


// -----------------------------------------------------------------------------
// -->


		function plugin0()
        {
			//alert(" in plugin0 "+document.getElementById('plugin0'));
            return document.getElementById('plugin0');
        }
      
       
        
        
     function close_session(moderator)
     {
    	 
    	// alert(" in close session .. take the user to another window.");
    	 var url="/whiteboard/wbusers.jsp";
    	 if(moderator != null)
    	 {
    			if(moderator == true){
    				
    				window.open(url,"_self");
    			} else
    			{
    				window.open(url,"_self");
    			}
    	  }
     }   
        
function start_screen_share (stream_id)
{	
	
	//plugin().start_screen_share(stream_id);
	try
    {
    
    	//alert(" in start_screen_share"+pluginLoaded);
		if(checkPlugin()){
			plugin0().start_capture(stream_id, "123","live", "true",75,2);
			return "STARTED";
		}
		else
			return "NOT_SUPPORTED_BROWSER"
	
     }

    catch(err)
    {
    	//alert(err);
    	return "PLUGIN_NOT_AVAILABLE";
    	 //  document.write(err.message + "<br/>")
    }
	
	//"STARTED"
	//"FAILED_TO_START"
	//"PLUGIN_NOT_AVAILABLE"	
}
	

function stop_screen_share()
{	

	try
    {
		plugin0().stop_capture();
		return "STOPPED";
    }

	catch(err)
	{
		//alert(err);	
		return "PLUGIN_NOT_AVAILABLE";
		 //  document.write(err.message + "<br/>")
	}
	
	
}

function plugin()
{
	//alert(" in plugin0 ");
    return document.getElementById('plugin0');
}


  function openCenteredWindow() {

	var url = null;
	if (navigator.appVersion.indexOf("Win")!=-1) 
		url="/whiteboard/loadWinPlugin.html";
	
	if (navigator.appVersion.indexOf("Mac")!=-1) 
		url="/whiteboard/loadMacPlugin.html";

	if(url == null)
		Alert("We do not support this OS yet.");

	alert(url);

    var width = 400;  
    var height = 300;
    var left = parseInt((screen.availWidth/2) - (width/2));
    var top = parseInt((screen.availHeight/2) - (height/2));
    var windowFeatures = "width=" + width + ",height=" + height + ",status,resizable,left=" + left + ",top=" + top + "screenX=" + left + ",screenY=" + top;
    myWindow = window.open(url, "subWind", windowFeatures);
    //window.open(url);
}
  
  
  window.onbeforeunload = function() {
     
        return "Do yuo want to close this session ?"
     
    }
  
  
	
		window.IEFixPlugin = [];
		function checkPInnoWhitePlugin()
		{
			var pluginLoaded = false;
			if(window.navigator.userAgent.search(/MSIE/) == -1)
			{
				var div = document.createElement('div');
				div.innerHTML='<object id="plugin0" type="application/x-innowhite"><div></div></object>';
				document.body.appendChild(div);
				var obj = div.getElementsByTagName('OBJECT')[0];
			}
			else
			{
				obj = document.createElement('OBJECT');
				try
				{
					obj.type = "application/x-innowhite";
				}
				catch(e)
				{
					return false;
				}
			}
			pluginLoaded = obj.echo || false;
			if(window.navigator.userAgent.search(/MSIE/) == -1)
			{
				div.parentNode.removeChild(div);
			}
			return pluginLoaded;
		}
		$(document).ready(function(){
			window.refreshPlugin = !checkPInnoWhitePlugin();}
		);
		function getAllPInnoWhitePluginElements()
		{
			var allObjects = document.getElementsByTagName('OBJECT');
			var pluginObjects = [];
			for(var i = 0; i < allObjects.length; i++)
			{

				if(allObjects[i].type != 'application/x-innowhite')
				{
					continue;
				}
				pluginObjects.push(allObjects[i]);
			}
			return pluginObjects;
		}


			document.onreadystatechange = function(){
			if(checkPInnoWhitePlugin())
			{
				return;
			}
			var plugins = getAllPInnoWhitePluginElements();
			if(window.IEFixPlugin.length == plugins.length)
			{
				return;
			}

			for(plugin = 0; plugin < plugins.length; plugin++)
			{
				var div = document.createElement('DIV');
				div.id = 'IEFixPlugin_' + plugin;
				div.innerHTML = plugins[plugin].outerHTML;
				IEFixPlugin.push(plugins[plugin].outerHTML);
				plugins[plugin].parentNode.replaceChild(div,plugins[0]);
			}
		};
		function checkPlugin()
		{
			var userAgent = typeof(window.navigator.userAgent) != 'udefined' ? window.navigator.userAgent : '';
			if(userAgent.search(/Firefox/) == -1 && userAgent.search(/Chrome/) == -1 && userAgent.search(/MSIE/) == -1)
			{
				$.openDOMWindow();
				$('#DOMWindow').html($('#notSapportedBrowser').html());
				window.refreshPlugin = true;
				return false;
			}
			if(!checkPInnoWhitePlugin())
			{
				$.openDOMWindow();
				$('#DOMWindow').html($('#downloadPlugin').html());
				window.refreshPlugin = true;
				return false;
			}

			if(!window.refreshPlugin)
			{
				return true;
			}
			//if IE
			if(window.IEFixPlugin.length > 0 && window.navigator.userAgent.search(/MSIE/) != -1)
			{
				for(var i = 0; i < window.IEFixPlugin.length; i++)
				{
					var div = document.getElementById('IEFixPlugin_' + i);
					div.outerHTML = window.IEFixPlugin[i];
				}
				window.refreshPlugin = false;
				return true;
			}
			//other browsers
			var pluginObjects = getAllPInnoWhitePluginElements();
			if(pluginObjects.length > 0)
			{

				for(var i = 0; i < pluginObjects.length; i++)
				{
					var oldObject = pluginObjects[i];
					var newObject = oldObject.cloneNode(true);
					oldObject.parentNode.replaceChild(newObject,oldObject);
				}
				window.refreshPlugin = false;
			}
			return true;

		}

	
	
//-->
</script>
<noscript>



</noscript>

<body>


<script>

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
			"src", "<%=userName%>",
			"width", "100%",
			"height", "100%",
			"align", "middle",
			"id", "<%=userName%>",
			"quality", "high",
			"bgcolor", "#ffffff",
			"flashVars","phoneNum=<%=phoneNum%>&meetingNum=<%=meetingNum%>&classRoom=<%=classRoom%>&courseId=<%=courseId%>&parentOrg=<%=parentOrg%>&orgName=<%=orgName%>&view=<%=view%>&lessonId=<%=lessonId%>&previousSession=<%=previousSession%>&joinroom=<%=joinroom%>&clientname=<%=clientname%>&groupLeader=<%=groupLeader%>",
				"name", "<%=userName%>", "allowScriptAccess", "sameDomain",
				"type", "application/x-shockwave-flash", "pluginspage",
				"http://www.adobe.com/go/getflashplayer", "allowFullScreen",
				"true");
	} else { // flash is too old or we can't detect the plugin
		var alternateContent = 'Alternate HTML content should be placed here. '
				+ 'This content requires the Adobe Flash Player. '
				+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
		document.write(alternateContent); // insert non-flash content
	}
	
</script>

<div id="pluginContainer">
	<object id="plugin0" type="application/x-innowhite" width="0" height="0" >
		<param name="onload" value="pluginLoaded" />

		<div id="downloadPlugin" width="0" height="0">
			Plugin, don't work
		</div>
		<div id="notSapportedBrowser" width="0" height="0">
			work only in IE, FF, Google Crome
		</div>
	</object><br />
</div>

<!--
<object id="plugin0" type="application/x-innowhite" width="300" height="300">
    <param name="onload" value="pluginLoaded" />
</object>
-->

</body>
</html>