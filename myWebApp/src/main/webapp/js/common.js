var wWidth = $(window).width();
var wHeight = $(window).height();
wWidth = wWidth / 2 - 227;
wHeight = wHeight / 2 - 202;

function login() {
	$('body').append('<div class="fade"></div>');
	$('body')
			.append(
					'<div class="popup"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading">Login</div><div class="pop-form login"><div class="pop-form-left-col"><div class="row">Name:</div><div class="row">Password:</div></div><div class="pop-form-right-col"><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><a href="dashboard.html"><img src="images/login-submit-btn.jpg" alt="Submit" /></a></div><div class="row"><a href="#">Forgot your password?</a></div><div class="row"><input type="checkbox" /> Remember me</div></div></div></div>');
	$('.popup').css({
		'left' : wWidth,
		'top' : wHeight
	});

	// CLOSE
	$('.popup .close').click(function() {
		$('.fade').remove();
		$('.popup').remove();
	})

}

function signUp() {
	$('body').append('<div class="fade"></div>');
	$('body')
			.append(
					'<div class="popup"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading">Sign Up</div><div class="pop-form login"><div class="pop-form-left-col"><div class="row">User Id:</div><div class="row">Email:</div><div class="row">Password:</div><div class="row">Confirm Password:</div></div><div class="pop-form-right-col"><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><img src="images/signup-btn.jpg" alt="Sign Up" /></div></div></div></div>');
	$('.popup').css({
		'left' : wWidth,
		'top' : wHeight
	});

	// CLOSE
	$('.popup .close').click(function() {
		$('.fade').remove();
		$('.popup').remove();
	})

}

function Reset() {
	$('body').append('<div class="fade"></div>');
	$('body')
			.append(
					'<div class="popup"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading">Reset Password</div><div class="pop-form login"><div class="pop-form-left-col"><div class="row">Old Password:</div><div class="row">New Password:</div></div><div class="pop-form-right-col"><div class="row"><input type="text" class="input-box" /></div><div class="row"><input type="text" class="input-box" /></div><div class="row"><a href="dashboard.html"><img src="images/reset-white-btn.png" alt="Submit" /></a></div></div></div></div>');
	$('.popup').css({
		'left' : wWidth,
		'top' : wHeight
	});

	// CLOSE
	$('.popup .close').click(function() {
		$('.fade').remove();
		$('.popup').remove();
	})

}

function enjoy() {
	$('body').append('<div class="fade"></div>');
	$('body')
			.append(
					'<div class="popup-audio-enjoy"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="enjoy"><div class="plugin" >Please click on the <img src="images/ss-icons.png" alt="download" /> icon to start screen sharing </div></div><div class="help-tabs" id="ht2" style="display:none;"><div class="top-space-ado"><p align="center"> <img src="images/message.png" alt="message send" /> </p></div></div></div></div>');
	$('.popup-audio-enjoy').css({
		'left' : wWidth,
		'top' : wHeight
	});

	// CLOSE
	$('.popup-audio-enjoy .close').click(function() {
		$('.fade').remove();
		$('.popup-audio-enjoy').remove();
	})

}

function popupaudio() {
	$('body').append('<div class="fade"></div>');
	$('body')
			.append(
					'<div class="popup-audio"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading1">Screen Share </div><div class="pop-form1 login1"><div class="help-tabs ahelp-tabs" id="ht1"><div class="icon-share"><img src="images/ss.png" alt="audio" width="130" height="85" /></div><div class="text-heading" >To share your screen, a plugin download is required. Please click on The &quot;Download&quot; button to start download of Innowhite ScreenShare plugin. </div><div class="download-button" > <img src="images/button-download.png" /></div></div></div><div class="help-tabs" id="ht2" style="display:none;"> <div style="padding:15px;"><p align="center"> <img src="images/message.png" alt="message send" /> </p></div></div></div></div>');
	$('.popup-audio').css({
		'left' : wWidth,
		'top' : wHeight
	});

	// CLOSE
	$('.popup-audio .close').click(function() {
		$('.fade').remove();
		$('.popup-audio').remove();
	})

}

function popupaudio1() {

	$('body').append('<div class="fade"></div>');
	$('body')
			.append(
					'<div class="popup-audio"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading1">Audio Conference</div><div class="pop-form1 login1"><ul class="help-tabs-head"><li class="ht1 active">Join Audio conference </li> <li class="ht2">Join Audio conference </li></ul><div class="help-tabs ahelp-tabs" id="ht1"><div><img src="images/audio-icons.png" alt="audio" width="588" height="69" /></div><div class="space-head"><div class="phone"><div class="pd-head"><img src="images/user-phone.png" width="140" height="23" /></div><div class="call">1.  Call in to the meeting:<br /></div><div class="call-num">1-650-429-330<br /><b>(Call-in-toll number (US/Canada))</b></div><div class="call">2. Enter the access code:<br /><div class="call-num"><b>1-650-429-330</b></div></div><div class="call">3. Enter your attendee ID:<br /><div class="call-num"><b>109#</b></div></div></div><div class="seprator-2"><img src="images/shadow-image.png" />  </div><div class="icon-space"><p><img src="images/use-computer.png" />  </p><p><img src="images/call-using-comp.png" width="229" height="53" /><br /><br /><span class="link-blue">Test speaker/microphone</span></p></div></div></div><div class="help-tabs" id="ht2" style="display:none;"> <div class="space-top"><p class="main-text">Share the link below with the participants to invite them to the meeting. </p><p class="link-blue"><a href="#"  class="link-blue">Lorem Ipsum is simply dummy text of the printing</a> </p><p class="or-padding">Or</p><div class="text-email">Enter email address: <input name="" type="text" class="bg-send-form" /></div><div class="pad-button"><img src="images/send-button.png" alt="send" /></div></div></div></div></div></div></div>');
	$('.popup-audio').css({
		'left' : wWidth,
		'top' : wHeight
	});

	$('.help-tabs-head li').click(function() {
		var value = $(this).attr('class').split(' ')[0];
		// alert(value)
		$('.help-tabs').hide();
		$('#' + value).show();

		$('.help-tabs-head li').removeClass('active');
		$(this).addClass('active');
	})

	// CLOSE
	$('.popup-audio .close').click(function() {
		$('.fade').remove();
		$('.popup-audio').remove();
	})

}

function popupaudio2() {
	$('body').append('<div class="fade"></div>');
	$('body')
			.append(
					'<div class="popup-audio"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading1">Audio Conference</div><div class="pop-form1 login1"><div class="help-tabs" id="ht2" style="display:none;"></div><img src="images/mail-send.png" alt="mail sent" width="591" height="329" /></div></div></div></div></div>');
	$('.popup-audio').css({
		'left' : wWidth,
		'top' : wHeight
	});

	// CLOSE
	$('.popup-audio .close').click(function() {
		$('.fade').remove();
		$('.popup-audio').remove();
	})

}

function popupaudio3() {
	$('body').append('<div class="fade"></div>');
	$('body')
			.append(
					'<div class="popup-audio"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="pop-heading1">Audio Conference</div><div class="pop-form login1"><ul class="help-tabs-head"><li class="active ht1">Video</li> <li class="ht2">Voice</li> <li class="ht3">Chat</li> <li class="ht4">Other</li></ul><div class="help-tabs" id="Video"><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley </p><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley </p></div><div class="help-tabs" id="Voice" style="display:none;"><img src="images/help-video-img.png" alt="" align="left" style="margin-right:10px;"/> <p>02</p><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley </p></div><div class="help-tabs" id="Chat" style="display:none;"><img src="images/help-video-img.png" alt="" align="left" style="margin-right:10px;"/> <p>03</p><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley </p></div><div class="help-tabs" id="Other" style="display:none;"><img src="images/help-video-img.png" alt="" align="left" style="margin-right:10px;"/> <p>04</p><p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industrys standard dummy text ever since the 1500s, when an unknown printer took a galley </p></div></div></div>');
	$('.popup-audio').css({
		'left' : wWidth,
		'top' : wHeight
	});

	$(document).ready(function() {
		// Reset();

		$('.help-tabs-head li').click(function() {
			var value = $(this).text();
			// alert(value)
			$('.help-tabs').hide();
			$('#' + value).show();

			$('.help-tabs-head li').removeClass('active');
			$(this).addClass('active');
		})
	})
	// CLOSE
	$('.popup-audio .close').click(function() {
		$('.fade').remove();
		$('.popup-audio').remove();
	})

}

// Tooltip

var tooltip = function() {
	var id = 'tt';
	var top = 3;
	var left = 3;
	var maxw = 300;
	var speed = 10;
	var timer = 20;
	var endalpha = 95;
	var alpha = 0;
	var tt, t, c, b, h;
	var ie = document.all ? true : false;
	return {
		show : function(v, w) {
			if (tt == null) {
				tt = document.createElement('div');
				tt.setAttribute('id', id);
				t = document.createElement('div');
				t.setAttribute('id', id + 'top');
				c = document.createElement('div');
				c.setAttribute('id', id + 'cont');
				b = document.createElement('div');
				b.setAttribute('id', id + 'bot');
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
			if (!w && ie) {
				t.style.display = 'none';
				b.style.display = 'none';
				tt.style.width = tt.offsetWidth;
				t.style.display = 'block';
				b.style.display = 'block';
			}
			if (tt.offsetWidth > maxw) {
				tt.style.width = maxw + 'px'
			}
			h = parseInt(tt.offsetHeight) + top;
			clearInterval(tt.timer);
			tt.timer = setInterval(function() {
				tooltip.fade(1)
			}, timer);
		},
		pos : function(e) {
			var u = ie ? event.clientY + document.documentElement.scrollTop
					: e.pageY;
			var l = ie ? event.clientX + document.documentElement.scrollLeft
					: e.pageX;
			tt.style.top = (u - h) + 'px';
			tt.style.left = (l + left) + 'px';
		},
		fade : function(d) {
			var a = alpha;
			if ((a != endalpha && d == 1) || (a != 0 && d == -1)) {
				var i = speed;
				if (endalpha - a < speed && d == 1) {
					i = endalpha - a;
				} else if (alpha < speed && d == -1) {
					i = a;
				}
				alpha = a + (i * d);
				tt.style.opacity = alpha * .01;
				tt.style.filter = 'alpha(opacity=' + alpha + ')';
			} else {
				clearInterval(tt.timer);
				if (d == -1) {
					tt.style.display = 'none'
				}
			}
		},
		hide : function() {
			clearInterval(tt.timer);
			tt.timer = setInterval(function() {
				tooltip.fade(-1)
			}, timer);
		}
	};
}();