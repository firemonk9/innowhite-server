
var participantVideoStatus = "none";

/* This function called when a message is received from firebase. 
  A video will be loaded based on videoid received from moderator.
  This is chromeless player .No controls.*/
function loadChromeLessPlayer() {
	swfobject.removeSWF("chromeLessytPlayer");
	$("#progressbar").remove();
	$("#participantPopUpWindow").append('<div id="videoDiv"/>');
	var params = { allowScriptAccess: "always" };
	var atts = { id: "chromeLessytPlayer" };
	swfobject.embedSWF("http://www.youtube.com/apiplayer?version=3&enablejsapi=1&playerapiid=player1",
   "videoDiv", "480", "295", "9", null, null, params, atts);
}

/* This function called when a message received from firebase.*/
function disableChromelessPopup(){
	swfobject.removeSWF("chromeLessytPlayer");
	$("#progressbar").remove();
	$("#backgroundPopup").fadeOut("slow");  
	$("#participantPopUpWindow").fadeOut("fast");
}

/*This function is to update progress bar based on video duration. */
function updateytplayerProgressBar() {
	if(ytplayer && ytplayer.getDuration) {
		  var val= ytplayer.getCurrentTime();
		  var val2 = ytplayer.getDuration();
		  var val3 = val/val2*100;
		  $("#progressbar").progressbar({ value: val3});
	}
}

/*This function called when a message received from firebase with status message play or seekTo. */
function playVideo(currenttime) {
    if (ytplayer) {
      ytplayer.seekTo(currenttime,true);
      ytplayer.playVideo();
    }
}
 
/*This function called when a message received from firebase with status message pause. */ 
function pauseVideo() {
    if (ytplayer) {
      ytplayer.pauseVideo();
    }
}
  
/* This function called when chromeless player state changes. 
 * When a status change message received from firebase. */
function handleChromelessPlayerStateChange(state) {
	if(state == 1 && participantVideoStatus == "startBuffering"){ 
		messageText = "endBuffering";
		messagesRef.push({msg:messageText});
		ytplayer.pauseVideo();
	}
}


