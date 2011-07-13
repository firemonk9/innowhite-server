var wWidth = $(window).width();
var wHeight = $(window).height();
wWidth = wWidth/2-227;
wHeight = wHeight/2-202;
	
function login()
{
	$('body').append('<div class="fade"></div>');
	$('body').append('<div class="popup"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading">Login</div><div class="pop-form login"><div class="pop-form-left-col"><div class="row">Name:</div><div class="row">Password:</div></div><div class="pop-form-right-col"><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><a href="dashboard.html"><img src="images/login-submit-btn.jpg" alt="Submit" /></a></div><div class="row"><a href="#">Forgot your password?</a></div><div class="row"><input type="checkbox" /> Remember me</div></div></div></div>');
	$('.popup').css({'left':wWidth, 'top':wHeight});
	
	//CLOSE
	$('.popup .close').click(function(){
									  $('.fade').remove();
									  $('.popup').remove();
									  })

}


function signUp()
{
		$('body').append('<div class="fade"></div>');
		$('body').append('<div class="popup"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading">Sign Up</div><div class="pop-form login"><div class="pop-form-left-col"><div class="row">User Id:</div><div class="row">Email:</div><div class="row">Password:</div><div class="row">Confirm Password:</div></div><div class="pop-form-right-col"><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><img src="images/signup-btn.jpg" alt="Sign Up" /></div></div></div></div>');
	$('.popup').css({'left':wWidth, 'top':wHeight});
	
	//CLOSE
	$('.popup .close').click(function(){
									  $('.fade').remove();
									  $('.popup').remove();
									  })

}


function Reset()
{
	$('body').append('<div class="fade"></div>');
	$('body').append('<div class="popup"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading">Reset Password</div><div class="pop-form login"><div class="pop-form-left-col"><div class="row">Old Password:</div><div class="row">New Password:</div></div><div class="pop-form-right-col"><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><a href="dashboard.html"><img src="images/reset-white-btn.png" alt="Submit" /></a></div></div></div></div>');
	$('.popup').css({'left':wWidth, 'top':wHeight});
	
	//CLOSE
	$('.popup .close').click(function(){
									  $('.fade').remove();
									  $('.popup').remove();
									  })

}



function Help()
{
	$('body').append('<div class="fade"></div>');
	$('body').append('<div class="popup-help"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading">Help</div><div class="pop-form login"><ul class="help-tabs-head"><li class="active ht1">Video</li> <li class="ht2">Voice</li> <li class="ht3">Chat</li> <li class="ht4">Other</li></ul><div class="help-tabs" id="Video"><img src="images/help-video-img.png" alt="" align="left" style="margin-right:10px;"/> <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry’s standard dummy text ever since the 1500s, when an unknown printer took a galley </p><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry’s standard dummy text ever since the 1500s, when an unknown printer took a galley </p></div><div class="help-tabs" id="Voice" style="display:none;"><img src="images/help-video-img.png" alt="" align="left" style="margin-right:10px;"/> <p>02</p><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry’s standard dummy text ever since the 1500s, when an unknown printer took a galley </p></div><div class="help-tabs" id="Chat" style="display:none;"><img src="images/help-video-img.png" alt="" align="left" style="margin-right:10px;"/> <p>03</p><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry’s standard dummy text ever since the 1500s, when an unknown printer took a galley </p></div><div class="help-tabs" id="Other" style="display:none;"><img src="images/help-video-img.png" alt="" align="left" style="margin-right:10px;"/> <p>04</p><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry’s standard dummy text ever since the 1500s, when an unknown printer took a galley </p></div></div></div>');
	$('.popup').css({'left':wWidth, 'top':wHeight});
	
	//CLOSE
	$('.popup .close').click(function(){
									  $('.fade').remove();
									  $('.popup-help').remove();
									  })

}




function video()
{
//	$(window).width()
//	$(window).height()
	wWidth = $(window).width()/2-400;
	wHeight = $(window).height()/2-300;
	$('body').append('<div class="fade"></div>');
//	$('body').append('<div class="popup-video"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="videos"><div class="jp-video jp-video-270p"><div class="jp-type-playlist"><div id="jquery_jplayer_1" class="jp-jplayer"></div><div id="jp_interface_1" class="jp-interface"><div class="jp-video-play"></div><ul class="jp-controls"><li><a href="#" class="jp-play" tabindex="1">play</a></li><li><a href="#" class="jp-pause" tabindex="1">pause</a></li><li><a href="#" class="jp-mute" tabindex="1">mute</a></li><li><a href="#" class="jp-unmute" tabindex="1">unmute</a></li></ul><div class="jp-progress"><div class="jp-seek-bar"><div class="jp-play-bar"></div></div></div><div class="jp-volume-bar"><div class="jp-volume-bar-value"></div></div><div class="jp-duration"></div><div class="jp-current-time"></div></div></div></div></div></div>');
	$('.popup-video').css({'left':wWidth, 'top':wHeight});
	
	//CLOSE
	$('.popup-video .close').click(function(){
									  $('.fade').remove();
									  $('.popup-video').remove();
									  })

}


//Tooltip

var tooltip=function(){
var id = 'tt';
var top = 3;
var left = 3;
var maxw = 300;
var speed = 10;
var timer = 20;
var endalpha = 95;
var alpha = 0;
var tt,t,c,b,h;
var ie = document.all ? true : false;
return{
show:function(v,w){
if(tt == null){
tt = document.createElement('div');
tt.setAttribute('id',id);
t = document.createElement('div');
t.setAttribute('id',id + 'top');
c = document.createElement('div');
c.setAttribute('id',id + 'cont');
b = document.createElement('div');
b.setAttribute('id',id + 'bot');
tt.appendChild(t);
tt.appendChild(c);
tt.appendChild(b);
document.body.appendChild(tt);
tt.style.opacity = 0;
tt.style.filter = 'alpha(opacity=0)';
document.onmousemove = this.pos;
}
tt.style.display = 'block';
c.innerHTML = v;
tt.style.width = w ? w + 'px' : 'auto';
if(!w && ie){
t.style.display = 'none';
b.style.display = 'none';
tt.style.width = tt.offsetWidth;
t.style.display = 'block';
b.style.display = 'block';
}
if(tt.offsetWidth > maxw){tt.style.width = maxw + 'px'}
h = parseInt(tt.offsetHeight) + top;
clearInterval(tt.timer);
tt.timer = setInterval(function(){tooltip.fade(1)},timer);
},
pos:function(e){
var u = ie ? event.clientY + document.documentElement.scrollTop : e.pageY;
var l = ie ? event.clientX + document.documentElement.scrollLeft : e.pageX;
tt.style.top = (u - h) + 'px';
tt.style.left = (l + left) + 'px';
},
fade:function(d){
var a = alpha;
if((a != endalpha && d == 1) || (a != 0 && d == -1)){
var i = speed;
if(endalpha - a < speed && d == 1){
i = endalpha - a;
}else if(alpha < speed && d == -1){
i = a;
}
alpha = a + (i * d);
tt.style.opacity = alpha * .01;
tt.style.filter = 'alpha(opacity=' + alpha + ')';
}else{
clearInterval(tt.timer);
if(d == -1){tt.style.display = 'none'}
}
},
hide:function(){
clearInterval(tt.timer);
tt.timer = setInterval(function(){tooltip.fade(-1)},timer);
}
};
}(); 