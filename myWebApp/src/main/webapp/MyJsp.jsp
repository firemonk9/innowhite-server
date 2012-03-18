
<%@page import="com.innowhite.whiteboard.persistence.beans.ServerVO"%>
<%@page import="com.innowhite.whiteboard.service.LoadBalancerService"%>
<%@page import="com.innowhite.whiteboard.util.InnowhiteConstants"%>
<%@page import="java.util.Enumeration"%><!-- saved from url=(0014)about:internet -->
<%@page import="java.util.StringTokenizer"%>
<%@page import="com.innowhite.whiteboard.util.Constants"%>


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
	String skypeId = LoadBalancerService.getSkypeId();
    ServerVO audioSerVO = LoadBalancerService.getServerURL("AUDIO",orgName);
    ServerVO skypeSerVO = LoadBalancerService.getServerURL("SKYPE",orgName);
    
	// Whiteboard server and port
	String wbSer = (String) request.getAttribute(InnowhiteConstants.WHITEBOARD_SERVER);
	String wbSerPort = (String) request.getAttribute(InnowhiteConstants.WHITEBOARD_SERVER_PORT);

	String phoneNum = (String) request.getAttribute(InnowhiteConstants.PHONE_NUM);
	String meetingNum = (String) request.getAttribute(InnowhiteConstants.MEETING_NUM);


	String hostURL = null;
	if (orgName.indexOf(Constants.WEB_DELIMITER) > 0) {
		StringTokenizer st = new StringTokenizer(orgName,
				Constants.WEB_DELIMITER);

		orgName = st.nextToken();
		hostURL = st.nextToken();
	}

	if (groupLeader == null)
		groupLeader = (String) request.getParameter("roomLeader");

	if (joinroom == null)
		joinroom = (String) request.getParameter("roomId");

	if (view != null) {
		if (view.equals("INET_PUBLIC")) {
			view = "STUDENT_VIEW";
			orgName = "INET";
		} else if (view.equals("INET_PRIVATE")) {
			view = "TEACHER_VIEW";
			orgName = "INET";
		}

	} else {
		view = "DEFAULT_VIEW";
		orgName = "DEFAULT_WB";
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
<script src="http://jquery.com/src/jquery-latest.pack.js"></script>

<!--  BEGIN Browser History required section -->
<link rel="stylesheet" type="text/css" href="history/history.css" />
<link rel="stylesheet" type="text/css" media="screen" href="resources/styles/styles.css"  /> 
<!--  END Browser History required section -->

<title></title>
<script src="AC_OETags.js" language="javascript"></script>

<!--  BEGIN Browser History required section -->
<script src="js/jquery.DOMWindow.js" language="javascript"></script>
<!--  END Browser History required section -->


<script type="text/javascript" src="http://download.skype.com/share/skypebuttons/js/skypeCheck.js"></script>

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
var requiredMinorVersion = 2;
// Minor version of Flash required
var requiredRevision = 0;
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
    	allowConfirm=false;
    	 var url="http://innowhite.com";
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


     function make_skype_call(){
    	 var a =1;
    	 //document.getElementById('skypeid').click();
    	 //$('#skypeid').click();
    	 //skype:innowhiteconf?call.click();
    	 //skype().innowhiteConf?call;
    	  allowConfirm=false;
    	      var ele = document.getElementById('skypeid');

            if (ele != null)
			{
			  if (ele.fireEvent) {
			    ele.fireEvent("onclick");
			  }
			  else
			  {
				var evt = document.createEvent("MouseEvents");
            	evt.initMouseEvent("click", true, true, window,
			                               0, 0, 0, 0, 0, false, false, false, false, 0, null);
            	ele.dispatchEvent(evt);
			  }
            }
            allowConfirm=true;
     }
   
     
var screen_sharing=false;

function start_screen_share (stream_id,recordStatus, serverUrl,roomId,port)
{
	var userAgent = typeof(window.navigator.userAgent) != 'udefined' ? window.navigator.userAgent : '';
	 if( userAgent.search(/MSIE/) >= 0)
	{
		$.openDOMWindow({windowSourceID:'#popUpTemplate', width:'0px', height:'0px'});
		setTimeout("$('#DOMWindow .DOMWindowContent').html($('#notSapportedBrowser').html())",0);
		return false;
	} 
	if(!pluginIsLoad())
	{
		pluginRefresh();
	}
	//plugin().start_screen_share(stream_id);
	try
    {
		
		if(serverUrl == null)
			serverUrl='main.innowhite.com';
		
		if(port == null)
			port=80;
    	//alert(" in start_screen_share"+pluginLoaded);
		if(pluginIsLoad()){
			plugin0().start_capture(stream_id, recordStatus, 75,2,serverUrl,roomId,port,15);
			screen_sharing=true;
			return "STARTED";
		}
		else
		{
			showDownloadPlugin();
			return "NOT_SUPPORTED_BROWSER"
		}

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
		if(pluginIsLoad()){
			plugin0().stop_capture();
			screen_sharing=false;
			return "STOPPED";
		}
    }

	catch(err)
	{
		//alert(err);
		return "PLUGIN_NOT_AVAILABLE";
		 //  document.write(err.message + "<br/>")
	}


}

var recording=false;
var roomName="";

function setRecordingStatus(myRecordStatus, myRoomName){
	recording= myRecordStatus;
	roomName = myRoomName;
	
}




function openHelpWindow()
{
	$.openDOMWindow({windowSourceID:'#popUpTemplate',overlayOpacity:50, width:'0px', height:'0px'});
	setTimeout("$('#DOMWindow .DOMWindowContent').html($('#helpWindow').html())",0);

}


function plugin()
{
	//alert(" in plugin0 ");
    return document.getElementById('plugin0');
}


  function openCenteredWindow() {
	var url = null;
	if (navigator.appVersion.indexOf("Win")!=-1)
		url="loadWinPlugin.html";

	if (navigator.appVersion.indexOf("Mac")!=-1)
		url="loadMacPlugin.html";

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


  // boolean value
  var allowConfirm=true;
  
  var loadUrl = "servlet/SessionRecording"; 
//  window.onbeforeunload = confirmExit;
  window.onunload = test;
  function test(){
	  //alert(" Session Recording is stopping");
	  if(recording == true)
	  {
		  
		 // alert("sending to net");
		  $.get(loadUrl, { roomId: roomName, recordStatus: "recordStopKill" } );
		  alert(" Session Recording is stopping");
		 // $("#result").html(ajax_load).load(loadUrl, "language=php&version=5");    
	  }
  	//<? header("Location:logout.php"); ?>
  	//alert('user is exiting');
  }
  
 
  
  function confirmExit(evt)
  {
    if(allowConfirm == true)
        return "Are you sure you want to close this session ?";
   
  }
  
  
 /*  window.onbeforeunload = function() {


        return "Do yuo want to close this session ?"

    } */


//////blugin detection

	var pluginContent =	'<object id="plugin0" type="application/x-innowhite" width="300" height="300">'
    +'<param name="onload" value="pluginLoaded" /><div></div>'
	+'</object>';
	function pluginIsLoad()
	{
		var object = plugin0();

		if(!object)
		{
			return false;
		}
		if(!object.get_inno_version)
		{
			return false;
		}
		return true;
	}
	function pluginRefresh()
	{
		var plugin = plugin0();

		document.getElementById('pluginContainer').innerHTML = pluginContent;
	}

	function pluginDetection()
	{
		if(pluginIsLoad())
		{
			$.openDOMWindow({windowSourceID:'#popUpTemplate', height:'0px', width:'0px'});
			setTimeout("$('#DOMWindow .DOMWindowContent').html($('#pluginInstalledTemplateId').html())",0);
			return true;
		}
		pluginRefresh();
		setTimeout('pluginDetection()',10);
	}
	function fixForFF()
	{
		if(pluginIsLoad())
		{
			return;
		}
		pluginRefresh();
		setTimeout('fixForFF()',10);
	}
	$(document).ready(function(){
			if(window.navigator.userAgent.search(/Firefox/) == -1)
			{
				return;
			}
			if(pluginIsLoad())
			{
				return;
			}

			fixForFF();
		})
	function showDownloadPlugin()
	{
			$.openDOMWindow({windowSourceID:'#popUpTemplate', height:'0px', width:'0px'});
    		setTimeout("$('#DOMWindow .DOMWindowContent').html($('#downloadPluginTemplate').html())",0);
	}
    function detectPluginSystem(downloadElement)
	{
		var platform = window.navigator.platform;
		if(platform.indexOf('Win') != -1)
		{
			downloadElement.href = 'http://demo.innowhite.com/InnowhitePlugin.msi';
		}
		if(platform.indexOf('Mac') != -1)
		{
			downloadElement.href = 'http://demo.innowhite.com/InnowhitePlugin.pkg';
		}
		if(platform.indexOf('Mac') != -1 && (( window.navigator.userAgent.search(/Chrome/) != -1 ) ||  (window.navigator.userAgent.search(/Safari/) != -1 )))
		{
			setTimeout('showReloadPage()',10000);
		} 
		pluginDetection();
	}
	
	function showReloadPage()
	{
		$.openDOMWindow({windowSourceID:'#popUpTemplate', width:'0px', height:'0px'});
		setTimeout("$('#DOMWindow .DOMWindowContent').html($('#reloadPage').html())",0);
	} 

var countryName =null; var countryCode=null; 
	
	function getCountry() {
		var geoUrl ="http://www.geoplugin.net/json.gp?jsoncallback=?";
		$.getJSON(geoUrl, function(data){
				countryName=data["geoplugin_countryName"];
				countryCode=data["geoplugin_countryCode"]
				getCountryBucket(countryCode);
		});		
	}
	
	function getCountryBucket(cntrCode){
		var countryBucket="3";
		if(cntrCode=="US" || cntrCode=="CA")
			countryBucket="1";
		//else if(cntrCode=="IN" || cntrCode=="LK" || cntrCode=="PK" || cntrCode=="BD")
			//countryBucket="3";
		
		alert("----countryBucket-----"+countryBucket);
	}




</script>
<noscript></noscript>
</head>
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
			'wmode', "opaque",
			"align", "middle",
			"id", "<%=userName%>",
			"quality", "high",
			"bgcolor", "#ffffff",
			"flashVars","audSer=<%=audioSerVO.getServerAddr()%>&audSerPort=<%=audioSerVO.getServerPort()%>&wbSer=<%=wbSer%>&wbSerPort=<%=wbSerPort%>&phoneNum=<%=phoneNum%>&meetingNum=<%=meetingNum%>&orgName=<%=orgName%>&joinroom=<%=joinroom%>&clientname=<%=clientname%>&groupLeader=<%=groupLeader%>",
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
		<object id="plugin0" type="application/x-innowhite" width="300"
			height="300">
			<param name="onload" value="pluginLoaded" />
			<div></div>
		</object>
		<br />
	</div>
	
	<div id="popUpTemplate" style="display:none">
		<div class="DOMWindowContent"></div> 
	</div>
	
	<div id="notSapportedBrowser" style="display: none;">
		<div class="popup-audio" style="left:456px;top:124.5px;">
				<div class="close">
					<a href="" onclick="$.closeDOMWindow();return false;" style="display:block;float:right;margin-right:10px;">
					<img src="images/pop-close-btn.png" alt="close"></a>
				</div>
				<div class="pop-heading1">Screen Share </div>
				<br><br>Internet Explorer is not supported to share the screen. Please use Firefox or Google Chrome for sharing Screen.
		</div>
	</div>
	
	<div id="reloadPage" style="display: none;">
		<div class="popup-audio" style="left:456px;top:124.5px;">
				<div class="close">
					<a href="" onclick="$.closeDOMWindow();return false;" style="display:block;float:right;margin-right:10px;">
					<img src="images/pop-close-btn.png" alt="close"></a>
				</div>
				<div class="pop-heading1">Screen Share </div>
				<br><br>
				<h4>Plugin cannot be loaded dynamically for Safari and Chrome in Mac</h4>
				Please <b>quit</b> your browser and open again for plugin to get loaded and enjoy screen share.
		</div>
	</div>
	
	<div id="downloadPluginTemplate" style="display:none;">
		<div class="popup-audio" style="left:456px;top:124.5px;">
			<div class="close">
				<a href="" onclick="$.closeDOMWindow();return false;" style="display:block;float:right;margin-right:10px;">
				<img src="images/pop-close-btn.png" alt="close"></a>
			</div>
			<div class="pop-heading1" >Screen Share </div>
			<br><br>
			<div id="downloadPlugin" class="pop-form1 login1" width="100%" height="100%">
				<div class="help-tabs ahelp-tabs" id="ht1">
						<div class="icon-share">
							<img src="images/ss.png" alt="audio" width="130" height="85" >
						</div>
						<div class="text-heading">To share your screen, a plugin download is required.
						Please click on The "Download" button to start download of Innowhite ScreenShare plugin. </div>
						<div class="download-button">
							<a target="_blank" href="" onmousedown="detectPluginSystem(this);">
							<img src="images/button-download.png" /></a>
						</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="pluginInstalledTemplateId" style="display:none;">
		<div class="popup-audio-enjoy" style="left:350px;top:120.5px;">
			<div class="close">
				<a href="" onclick="$.closeDOMWindow();return false;" style="display:block;float:right;margin-right:10px;">
					<img src="images/pop-close-btn.png" alt="close"></a>
			</div>
			<div class="enjoy">
				<div class="plugin">Please click on the
					 <img src="images/ss-icons.png" alt="download" /> icon to start screen sharing
				 </div>
			</div>
		</div>
	</div>
	
	<a href="skype:<%=skypeId%>?call" id="skypeid"><img src="http://download.skype.com/share/skypebuttons/buttons/call_green_white_153x63.png"  style="border: none;" width="153" height="63" alt="Skype Me!" /></a>
	
	<!--
<object id="plugin0" type="application/x-innowhite" width="300" height="300">
    <param name="onload" value="pluginLoaded" />
</object>
-->

</body>
</html>
