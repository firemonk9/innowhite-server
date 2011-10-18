package com.innowhite.PlaybackApp.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.PlayBackPlayList;
import com.innowhite.PlaybackApp.model.VideoData;
import com.innowhite.PlaybackApp.model.VideoPlayBackPlayListBucket;

public class PlaybackUtil {

    private static final Logger log = LoggerFactory.getLogger(PlaybackUtil.class);

    public static void main(String args[]) {

	String os = System.getProperty("os.name").toLowerCase();
	System.err.println(os);

	String a = "asd#qwe";
	String arr[] = new String[2];
	arr = a.split("#");
	System.err.println(arr[0] + "  " + arr[1]);

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
	log.debug(" the os is :: " + os);
	return (os.indexOf("linux") >= 0);

    }

    /*
     * invokes process executor class and executes the ffmpeg cmd
     */
    // public static void invokeVideoAttribProcess(String cmd, PlayBackPlayList
    // playlist) {
    public static void invokeVideoAttribProcess(String cmd, HashMap<String, String> videohm) {
	ProcessExecutor pe = new ProcessExecutor();
	// MakeExectuable obj = new MakeExectu

	boolean val = pe.executeProcess(playbackVO.getFfmpegPath() + " " + cmd, playbackVO.getTempLocation(), videohm);
	// log.debug(" populating the width , height, size and duration :: duration : "
	// + playlist.getDuration() + " size " + playlist.getSize() + "  width "
	// + playlist.getWidth() + " height + playlist.getHeight());
	log.debug("return from the vidAttrib ffmpeg process executor :: " + val);
    }

    public static void invokeFfmpegProcess(String cmd) {
	ProcessExecutor pe = new ProcessExecutor();
	// MakeExectuable obj = new MakeExectu

	boolean val = pe.executeProcess(playbackVO.getFfmpegPath() + " " + cmd, playbackVO.getTempLocation(), null);
	log.debug("return from the ffmpeg process executor :: " + val);
    }

    public static void invokeMp3Process(String cmd) {
	ProcessExecutor pe = new ProcessExecutor();
	// MakeExectuable obj = new MakeExectu

	boolean val = pe.executeProcess(playbackVO.getMp3WrapPath() + " " + cmd, playbackVO.getTempLocation(), null);
	log.debug("return from the Mp3Wrap process executor :: " + val);
    }

    public static void invokeMencoderProcess(String cmd) {
	ProcessExecutor pe = new ProcessExecutor();
	// MakeExectuable obj = new MakeExectu

	boolean val = pe.executeProcess(playbackVO.getMencoderPath() + " " + cmd, playbackVO.getTempLocation(), null);
	log.debug("return from the Mencoder process executor :: " + val);
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

	log.debug("changing the source path for Audio");
	if (mediaList == null)
	    return;

	for (AudioData aud : mediaList) {

	    String path = aud.getFilePath();
	    String newPath = path.substring(path.lastIndexOf("/"));
	    aud.setFilePath(winPath + newPath);
	}

    }

    /* Send the object Audio and video and the windows folder */
    public static void updateVideoPathWindows(String winPath, List<VideoData> mediaList) {

	if (mediaList == null)
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

	if (isWindows()) {
	    playbackVO.setFfmpegPath(playbackVO.getWinFFmpegPath());
	    playbackVO.setTempLocation(playbackVO.getWinTempLocation());
	    playbackVO.setMencoderPath(playbackVO.getWinMencoderPath());
	    playbackVO.setMp3WrapPath(playbackVO.getWinMp3WrapPath());
	    playbackVO.setSilentAudioPath(playbackVO.getWinSilentAudioPath());

	} else if (isMac()) {
	    playbackVO.setFfmpegPath(playbackVO.getMacFFmpegPath());
	    playbackVO.setTempLocation(playbackVO.getMacTempLocation());
	} else if (isUbuntu()) {
	    playbackVO.setFfmpegPath(playbackVO.getUbuntuFFmpegPath());
	    playbackVO.setTempLocation(playbackVO.getUbuntuTempLocation());
	    playbackVO.setMencoderPath(playbackVO.getWinMencoderPath());
	    playbackVO.setMp3WrapPath(playbackVO.getWinMp3WrapPath());
	    playbackVO.setSilentAudioPath(playbackVO.getWinSilentAudioPath());
	}
    }

    public static void printVals(long videoStartTime, long sessionStartTime, long videoEndTime, long sessionEndTime) {

	log.warn("videoStartTime ::" + videoStartTime);
	log.warn("sessionStartTime ::" + sessionStartTime);
	log.warn("videoEndTime ::" + videoEndTime);
	log.warn("sessionEndTime ::" + sessionEndTime);

    }

    public static int getNum(String string) {
	try {

	    if (string != null)
		return Integer.parseInt(string.trim());
	} catch (Exception e) {

	}
	return 0;
    }

    public static long getNumLong(String string) {
	try {

	    if (string != null)
		return Long.parseLong(string.trim());
	} catch (Exception e) {

	}
	return 0;
    }
}
