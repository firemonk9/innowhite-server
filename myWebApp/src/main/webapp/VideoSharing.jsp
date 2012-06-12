<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
	String roomid = request.getParameter("room");
	if(roomid==null || roomid.equals("null")){
		roomid = "roomidnull";
	}
	
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Youtube popup with Jquery</title>
    
    <script type="text/javascript" src="http://jquery.com/src/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="http://www.google.com/jsapi" ></script>
    <script type="text/javascript" src="js/swfobject.js" ></script>
    <script type="text/javascript" src="http://happylucky.googlecode.com/svn-history/r19/trunk/common/script/jquery-ui-1.8.14.custom.min.js"></script>
    <script src="http://static.firebase.com/v0/firebase.js"></script>
    <script type="text/javascript" src="videosharing.moderator.js" ></script>
    <script type="text/javascript" src="videosharing.participants.js" ></script>
    <link rel="stylesheet" type="text/css" media="screen" href="resources/styles/styles.css"  />
    
    <script type="text/javascript">
    
    	var firemonk9Ref = null; 
    	var roomId=<%=roomid%>;
    	var messageText = "";
    	firemonk9Ref_roomId = new Firebase('http://gamma.firebase.com/Firemonk9/'+roomId);
    	
        jQuery.fn.center = function () {
	    	    this.css("position","absolute");
	    	    this.css("top", (($(window).height() - this.outerHeight()) / 2) +  $(window).scrollTop() + "px");
	    	    this.css("left", (($(window).width() - this.outerWidth()) / 2) +  $(window).scrollLeft() + "px");
	    	    return this;
	     }
		
		 /*This function is automatically called by the player once it loads. 
		 This is for both simple embed and chromeless players.*/
	     function onYouTubePlayerReady(playerId) {
	    	if ($('#chromeLessPopUpWindow:visible').length > 0){
	    		$("#chromeLessPopUpWindow").append('<div id="progressbar" />');
		        ytplayer = document.getElementById("chromeLessytPlayer");
		        setInterval(updateytplayerProgressBar, 250);
		        updateytplayerProgressBar();
		        ytplayer.addEventListener("onStateChange", "handleChromelessPlayerStateChange");
		        //ytplayer.addEventListener("onError", "onPlayerError");
		        ytplayer.cueVideoById(videoID);
			}else if($('#simplePopUpWindow:visible').length > 0){
				ytplayer1 = document.getElementById("simpleytPlayer"); 
			    if(ytplayer1){ 
			    	ytplayer1.addEventListener( "onStateChange", "handleSimplePlayerStateChange" ); 
			    } 
	        }
		 }
		 
		 
		/* This function called when a message is received from firebase .
		This is for both simple embed and chromeless players.*/
		firemonk9Ref_roomId.on('child_added', function(snapshot) {
			  if(snapshot.val() === null) {
			   		alert('User firemonk9Ref_roomId does not exist.');
			  }else {
					  if($('#simplePopUpWindow:visible').length<=0){
						    var msgReceived = snapshot.val().msg;
						    if(msgReceived == "closePopup"){
						    	disableChromelessPopup();
						    }else{
							    var msgArray = msgReceived.split("&");
							    participantVideoStatus = msgArray[0];
							    videoID = msgArray[1];
							    currenttime = msgArray[2];
							    
							    if(participantVideoStatus == "load"){
							    	loadpopup('chromeLessPopUpWindow');
							    }else if(participantVideoStatus == "startBuffering"){
							    	playVideo(currenttime);
							    }else if(participantVideoStatus == "play"){
							    	playVideo(currenttime);
							    }else if(participantVideoStatus == "pause"){
							    	pauseVideo();
							    }else if(participantVideoStatus == "end"){
							    	ytplayer.stopVideo();
							    }
						    }
					  }else if($('#simplePopUpWindow:visible').length>0){
						    var status = snapshot.val().msg;
						    if(status == "endBuffering"){
						    	usersReadyToPlay++;
						    	//If no of users in the room equal to usersReadyToPlay then play the video
						    	ytplayer1.playVideo();
						    }
					  }
			  }
		});
      
    </script>
    
 </head>
  
  
 <body style="font-family: Arial;border: 0 none;">
    <div id="backgroundPopup" class="backgroundfade"></div>
    <input type="button" value="Open Simplepopup !" onclick="loadpopup('simplePopUpWindow')"/> </br>
    
    <div id="simplePopUpWindow" style="display:none" class="youtubePopup simpleyoutubepopupheight">
    	<div id="showHideImgdiv">
    		<a href="" onclick="toggleSearchDiv();return false;"  style="display:block;float:left;margin-left:10px;">
					<img src="resources/images/right.png" alt="show/hide" /></a>
		</div>
    	<div class="close">
			<a href="" onclick="disablePopup(this);return false;" style="display:block;float:right;margin-right:10px;">
					<img src="images/pop-close-btn.png" alt="close" /></a>
		</div>
   		<div id="youtubeUrlDiv">
   			<span><input type="text" class="input-box" id="youtubeurl" value="Enter URL here" onFocus='this.value="";' /></span>
   			<span><input type="button" value="Load Video" onclick="loadSimpleYTPlayer()"/></span>
   		</div>
   		<div id="errordiv" class="errortext-msg" style="display:none">Invalid Url.</div>
   		<div id="infodiv" class="pop-form1 infodiv2" width="100%" height="100%">
			<div class="help-tabs ahelp-tabs" style="height:260px;" id="ht1">
				<div class="icon-share">
					<img src="images/ss.png" alt="audio" width="130" height="85" >
				</div>
				<div class="text-heading">To share video, please enter valid youtube Url.
				And click on The "Load Video" button. </div>
			</div>
		</div>
    </div>
    <div id="chromeLessPopUpWindow" style="display:none" class="youtubePopup chromelessyoutubepopupheight"> </div>
  </body>
</html>



</body>
</html>