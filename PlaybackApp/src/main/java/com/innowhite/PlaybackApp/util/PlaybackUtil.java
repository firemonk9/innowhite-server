package com.innowhite.PlaybackApp.util;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.VideoData;

public class PlaybackUtil {

    public static void main(String args[]) {

	String path = "/opt/InnowhiteData/videos/room83_0.flv";
	String newPath = path.substring(path.lastIndexOf("/"));
	System.err.println(newPath);
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

    public static String secondsToHours(long seconds) {
	int ss = (int) ((seconds / 1000) % 60);
	int mm = (int) ((seconds / 1000) / 60);
	int hh = (int) ((seconds / 1000) / 3600);
	// slog.debug("seconds to hours::" + hh + ":" + mm + ":" + ss);
	return (hh + ":" + mm + ":" + ss + ".000");
    }

    /* Send the object Audio and video and the windows folder */
    public static void updatePathWindows(String winPath, List<AudioData> audios, List<VideoData> videos) {

	for (AudioData aud : audios) {

	    String path = aud.getFilePath();
	    String newPath = path.substring(path.lastIndexOf("/"));
	    aud.setFilePath(winPath + newPath);
	}

	for (VideoData vid : videos) {

	    String path = vid.getFilePath();
	    String newPath = path.substring(path.lastIndexOf("/"));
	    vid.setFilePath(winPath + newPath);
	}

    }

}
