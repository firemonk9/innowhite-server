package com.innowhite.PlaybackApp.util;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.VideoData;

public class PlaybackUtil {

    private static final Logger log = LoggerFactory.getLogger(PlaybackUtil.class);

    public static void main(String args[]) {

	String os = System.getProperty("os.name").toLowerCase();
	System.err.println(os);
	
	String a = "asd#qwe";
	String arr[] = new String[2];
	arr = a.split("#");
	System.err.println(arr[0]+"  "+arr[1]);
	
	
    }

    public static String getUnique() {

	String val = String.valueOf(Calendar.getInstance().getTimeInMillis());
	String r = "" + Math.round((Math.random() * 100)) + val.substring(5);
	return r;
    }

    public static boolean isWindows() {

	String os = System.getProperty("os.name").toLowerCase();
	// windows
	return (os.indexOf("win") >= 0);

    }
    
    public static boolean isMac() {

	String os = System.getProperty("os.name").toLowerCase();
	// windows
	return (os.indexOf("mac") >= 0);

    }
    
    public static boolean isUbuntu() {

	String os = System.getProperty("os.name").toLowerCase();
	// windows
	return (os.indexOf("linux") >= 0);

    }
    
    

    /*
     * invokes process executor class and executes the ffmpeg cmd
     */

    public static void invokeProcess(String cmd) {
	ProcessExecutor pe = new ProcessExecutor();
	// MakeExectuable obj = new MakeExectu

	boolean val = pe.executeProcess( playbackVO.getFfmpegPath()+" " + cmd, playbackVO.getTempLocation() );
	log.debug("return from the proess :: " + val);
    }

    public static String secondsToHours(long seconds) {
	int ss = (int) ((seconds / 1000) % 60);
	int mm = (int) ((seconds / 1000) / 60);
	int hh = (int) ((seconds / 1000) / 3600);
	// slog.debug("seconds to hours::" + hh + ":" + mm + ":" + ss);
	return (hh + ":" + mm + ":" + ss + ".000");
    }

    
    
    /* Send the object Audio and video and the windows folder */
    public static void updateAudioPathWindows(String winPath, List<AudioData> mediaList) {
//
	if(mediaList == null)
	    return;
	
	for (AudioData aud : mediaList) {

	    String path = aud.getFilePath();
	    String newPath = path.substring(path.lastIndexOf("/"));
	    aud.setFilePath(winPath + newPath);
	}

    }
    
    /* Send the object Audio and video and the windows folder */
    public static void updateVideoPathWindows(String winPath, List<VideoData> mediaList) {

	if(mediaList == null)
	    return;
	
	for (VideoData vid : mediaList) {

	    String path = vid.getFilePath();
	    String newPath = path.substring(path.lastIndexOf("/"));
	    vid.setFilePath(winPath + newPath);
	}

    }

    static PlaybackVO playbackVO;

    public static void setPlaybackVO(PlaybackVO p) {
	playbackVO = p;
	processEnv();
    }

    private static void processEnv() {
	
	if(isWindows())
	{
	    playbackVO.setFfmpegPath(playbackVO.getWinFFmpegPath());
	    playbackVO.setTempLocation(playbackVO.getWinTempLocation());
	}else if(isMac()){
	    playbackVO.setFfmpegPath(playbackVO.getMacFFmpegPath());
	    playbackVO.setTempLocation(playbackVO.getMacTempLocation());
	}else if(isUbuntu()){
	    playbackVO.setFfmpegPath(playbackVO.getUbuntuFFmpegPath());
	    playbackVO.setTempLocation(playbackVO.getUbuntuTempLocation());
	}
    }
}
