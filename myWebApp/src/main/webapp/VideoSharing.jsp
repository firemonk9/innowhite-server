<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
	String roomid = request.getParameter("room");
	if(roomid==null || roomid.equals("null")){
		roomid = "roomidnull";
	}
	String userid = request.getParameter("user");
	if(userid==null || userid.equals("null")){
		userid = "useridnull";
	}
	String isModer = request.getParameter("moder");
	
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
    
    	var firemonk9Ref_roomId = null; 
    	var roomId=<%=roomid%>;
    	var userId = <%=userid%>;
    	var ismoder = <%=isModer%>;
    	var messageText = "";
    	var numberOfParticipants =0; 
    	firemonk9Ref_roomId = new Firebase('http://gamma.firebase.com/Firemonk9/'+roomId+'/');
    	
    	var usersRef = firemonk9Ref_roomId.child('users');
    	var myUserRef = usersRef.child(userId);
    	
    	var messagesRef = firemonk9Ref_roomId.child('messages'); 
    	    	 
    	myUserRef.on("value", function(snapshot) {
    	    if(snapshot.val() === null) {
    	    	myUserRef.removeOnDisconnect();
    	    	myUserRef.set({msg:"userRefCreated"});
    		}
    	});
    	
        jQuery.fn.center = function () {
	    	    this.css("position","absolute");
	    	    this.css("top", (($(window).height() - this.outerHeight()) / 2) +  $(window).scrollTop() + "px");
	    	    this.css("left", (($(window).width() - this.outerWidth()) / 2) +  $(window).scrollLeft() + "px");
	    	    return this;
	     }
        
        function isModerator(){
        	if($('#moderatorPopupWindow:visible').length > 0){
        		return true;
        	}else if($('#participantPopUpWindow:visible').length > 0){
        		return false;
        	}
        	return false;
    	 }
		
		 /*This function is automatically called by the player once it loads. 
		 This is for both simple embed and chromeless players.*/
	     function onYouTubePlayerReady(playerId) {
	    	if(!isModerator()){
	    		$("#participantPopUpWindow").append('<div id="progressbar" />');
		        ytplayer = document.getElementById("chromeLessytPlayer");
		        setInterval(updateytplayerProgressBar, 250);
		        updateytplayerProgressBar();
		        ytplayer.addEventListener("onStateChange", "handleChromelessPlayerStateChange");
		        //ytplayer.addEventListener("onError", "onPlayerError");
		        ytplayer.cueVideoById(videoID);
		       // firemonk9Ref_roomId.push({msg:"clientadded"});
		        
			}else if(isModerator()){
				ytplayer1 = document.getElementById("simpleytPlayer"); 
			    if(ytplayer1){ 
			    	ytplayer1.addEventListener( "onStateChange", "handleSimplePlayerStateChange" ); 
			    } 
	        }
		 }
		 
	     usersRef.on('child_removed', function(snapshot) {
	    	var userName = snapshot.name();
	    	if(ismoder == "yes"){
	    		 numberOfParticipants--; 
	    		// alert(userName+" left chat.numberOfParticipants left: "+numberOfParticipants);
	    	}
	    });
	     
		/* This function called when a message is received from firebase .
		When a new user added to room, handling that new user here.*/
		usersRef.on('child_added', function(snapshot) {
			  if(snapshot.val() === null) {
			   		alert('User firemonk9Ref_roomId does not exist.');
			  }else {
				     var msgReceived = snapshot.val().msg;
				     if(msgReceived == "userRefCreated" && ismoder == "yes"){
						   numberOfParticipants++;
						  // alert("numberOfParticipants in room including moderator : "+numberOfParticipants);
				     }
			  }
		});
		
		messagesRef.on('child_added', function(snapshot) {
			var msgReceived = snapshot.val().msg;
			if(snapshot.val() === null) {
		   		alert('User messagesRef does not exist.');
		    }else if(!isModerator()){
				    if(msgReceived == "userRefCreated" || msgReceived == "endBuffering"){
				      //this message is for moderator. Nothing to do
				    }else if(msgReceived == "closePopup"){
				    	disableChromelessPopup();
				    }else{
					    var msgArray = msgReceived.split("&");
					    participantVideoStatus = msgArray[0];
					    videoID = msgArray[1];
					    currenttime = msgArray[2];

					    if(participantVideoStatus == "load"){
					    	loadpopup('participantPopUpWindow');
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
			  }else if(isModerator()){
				  if(msgReceived == "endBuffering"){
				    	usersReadyToPlay++; 
				    	//If no of users in the room equal to usersReadyToPlay then play the video
				    	if(numberOfParticipants == usersReadyToPlay){
				    		ytplayer1.playVideo();
				    	}
				   }
			  }
		});
      
    </script>
    
 </head>
  
  
 <body style="font-family: Arial;border: 0 none;">
    <div id="backgroundPopup" class="backgroundfade"></div>
    <input type="button" value="Open Simplepopup !" onclick="loadpopup('moderatorPopupWindow')"/> </br>
    
    <div id="moderatorPopupWindow" style="display:none" class="youtubePopup simpleyoutubepopupheight">
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
    <div id="participantPopUpWindow" style="display:none" class="youtubePopup chromelessyoutubepopupheight"> </div>
  </body>
</html>



</body>
</html>