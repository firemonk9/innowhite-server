package com.innowhite.PlaybackApp.util;

import java.util.Calendar;
import java.util.List;

import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.VideoData;

public class PlaybackUtil {

    
    public static void main(String args[]){
	
	String path = "/opt/InnowhiteData/videos/room83_0.flv";
	 String newPath = path.substring(path.lastIndexOf("/"));
	 System.err.println(newPath);
    }
    
    public static String getUnique() {

	String val = String.valueOf(Calendar.getInstance().getTimeInMillis());
	String r = "" + Math.round((Math.random() * 100)) + val.substring(5);
	return r;
    }

    /* Send the object Audio and video and the windows folder */
    public static void updatePathWindows(String winPath, List<AudioData> audios, List<VideoData> videos) {

	for (AudioData aud : audios) {

	    String path = aud.getFilePath();
	    String newPath = path.substring(path.lastIndexOf("/"));
	    aud.setFilePath(winPath +  newPath);
	}

	for (VideoData vid : videos) {

	    String path = vid.getFilePath();
	    String newPath = path.substring(path.lastIndexOf("/"));
	    vid.setFilePath(winPath +  newPath);
	}

    }

}
