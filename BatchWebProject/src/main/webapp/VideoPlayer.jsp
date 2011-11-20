<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://cdn.sublimevideo.net/js/yn31mk7t.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Innowhite Video Player</title>
<link href="css/styles.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/jplayer.blue.monday.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script src="http://cdn.jquerytools.org/1.2.5/full/jquery.tools.min.js?foo"></script> 
<script type="text/javascript" src="http://static.flowplayer.org/js/global-0.54.js"></script>   

<script type="text/javascript">
$(document).ready(function(){
video();
})
</script>

</head>

<body>


<style type="text/css">
#streams_api{
    height:95%;
    }
.player{
    display:block;
    cursor:pointer;
    display:block;
    text-align:center;
    }
</style>

<div class="popup-video"><div class="close"><img src="images/pop-close-btn.png" alt="close" /></div><div class="videos"><a class="player" id="streams"></div></div>
<script language='javascript'> 
$f("streams", "http://releases.flowplayer.org/swf/flowplayer-3.2.7.swf", {
    clip: {

        provider: 'influxis',

        // combined streams are configured in the "streams" node as follows:
        streams: [
            { url: 'metacafe', duration: 10 },
            { url: 'honda_accord', start: 2, duration: 10 },
            { url: 'metacafe', start: 10, duration: 10 },
            { url: 'honda_accord', start: 10, duration: 10 },
            { url: 'metacafe', start: 20, duration: 10 },
            { url: 'honda_accord', start: 20, duration: 10 },
            { url: 'metacafe', start: 30, duration: 10 },
            { url: 'honda_accord', start: 30, duration: 10 }
        ]
    },

    // our rtmp plugin is configured identically as in the first example
    plugins: {
        influxis: {
            url: 'flowplayer.rtmp-3.2.3.swf',
            netConnectionUrl: 'rtmp://dk2isqp3f.rtmphost.com/flowplayer'
        }
    }
});
</script>



</body>
</html>
