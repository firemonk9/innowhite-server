
var videoID=0; 
var moderatorVideoStatus = "none";
var sendMessage = false;
var currenttime = 0;
var usersReadyToPlay = 0;

/*This function called when moderator clicks on share video icon.
A popup will be generated. And moderator has option to enter youtube url in a input text box */
function loadpopup(objid){
	resetAllFields();
	$("#simplePopUpWindow").draggable();
	$("#chromeLessPopUpWindow").draggable();
	$("#"+objid).center();
	$("#backgroundPopup").css({  
  				"opacity": "0.7"  
  		});  
  	$("#backgroundPopup").fadeIn("slow");
  	$("#"+objid).fadeIn("fast");
	$("#youtubeurl").focus();
  	if(objid=='chromeLessPopUpWindow'){
  		loadChromeLessPlayer();
  	}
}

function resetAllFields(){
	 $("#youtubeurl").val("");
	 swfobject.removeSWF("simpleytPlayer");
	 $("#infodiv").show();  $("#errordiv").hide();
}

/*This function called when moderator clicks on close button in popup.
This will disable the popup at participant side also.*/
function disablePopup(){ 
	swfobject.removeSWF("simpleytPlayer");
	//var objid = $(obj).parent('div').parent('div').attr('id'); $("#"+objid).fadeOut("fast"); 
	$("#backgroundPopup").fadeOut("slow");  
	$("#simplePopUpWindow").fadeOut("fast");
	firemonk9Ref_roomId.push({msg:"closePopup"});
} 

/* This function called when moderator clicks on load video button.
   Simple embed video will be loaded. */
function loadSimpleYTPlayer() {
	usersReadyToPlay = 0;
	$("#infodiv").hide();  $("#errordiv").hide();
	swfobject.removeSWF("simpleytPlayer");
	var youtubeUrl = $("#youtubeurl").val();
	videoID = youtube_parser(youtubeUrl);
	if(videoID!=0 && videoID!=null){
		$("#youtubeUrlDiv").slideToggle("fast");
		$("#simplePopUpWindow").append('<div id="simplevideoDiv"/>');
		var params = { allowScriptAccess: "always" };
		var atts = { id: "simpleytPlayer" };
		swfobject.embedSWF("http://www.youtube.com/v/" + videoID + "?listType=search&version=3&enablejsapi=1&playerapiid=player1", 
                      "simplevideoDiv", "480", "295", "9", null, null, params, atts);
	}
}

/*This function is used to check moderator entered url is valid or not.
 And separates videoid from url and returns this id */
function youtube_parser(url){
    var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
    var match = url.match(regExp);
    if (match&&match[7].length==11){
        return match[7];
    }else{
        $("#infodiv").show();
        $("#errordiv").show();
        return 0;
    }
}

/* This function called when player state changes.
   -1 - Video has not started yet. 
	0 - Video has ended. 
	1 - Video is currently playing. 
	2 - Video has been paused. 
	3 - Video is buffering. 
	5 - Video is cued.  */
function handleSimplePlayerStateChange(state) {
	 sendMessage = false;
	 currenttime = ytplayer1.getCurrentTime();
	 if(state == -1){
		 moderatorVideoStatus = "load";  
		 sendMessage = true;
	 }else if(state == 1 && usersReadyToPlay == 0){
		 moderatorVideoStatus = "startBuffering";
		 sendMessage = true;
	 }else if(state == 1 && usersReadyToPlay > 0){
		 moderatorVideoStatus = "play";
		 sendMessage = true;
	 }else if(state == 2 && usersReadyToPlay > 0){
		 moderatorVideoStatus = "pause";
		 sendMessage = true;
	 }else if(state == 0){
		 moderatorVideoStatus = "end";
		 sendMessage = true;
	 }
	 if(sendMessage){
		 messageText = moderatorVideoStatus +"&"+videoID+"&"+currenttime;
		 firemonk9Ref_roomId.push({msg:messageText});
		 if(moderatorVideoStatus == "startBuffering"){
			 ytplayer1.pauseVideo();
		 }
	 }
}

function toggleSearchDiv(){
	  $("#youtubeUrlDiv").slideToggle("fast");
}




