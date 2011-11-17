package com.innowhite.PlaybackApp.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.dao.AudioDataDao;
import com.innowhite.PlaybackApp.dao.PlayBackPlayListDao;
import com.innowhite.PlaybackApp.dao.SessionRecordingDao;
import com.innowhite.PlaybackApp.dao.VideoDataDao;
import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.PlayBackPlayList;
import com.innowhite.PlaybackApp.model.SessionBucket;
import com.innowhite.PlaybackApp.model.SessionRecordings;
import com.innowhite.PlaybackApp.model.VideoData;
import com.innowhite.PlaybackApp.util.PlaybackUtil;
import com.innowhite.PlaybackApp.util.PlaybackVO;
import com.sun.xml.internal.bind.v2.TODO;

//

public class PlaybackDataService {

    private static final Logger log = LoggerFactory.getLogger(PlaybackDataService.class);

    // private String roomId;
    public AudioDataDao getAudioDataDao() {
	return audioDataDao;
    }

    public void setAudioDataDao(AudioDataDao audioDataDao) {
	this.audioDataDao = audioDataDao;
    }

    public VideoDataDao getVideoDataDao() {
	return videoDataDao;
    }

    public void setVideoDataDao(VideoDataDao videoDataDao) {
	this.videoDataDao = videoDataDao;
    }

    public SessionRecordingDao getSessionRecordingsDao() {
	return sessionRecordingsDao;
    }

    public void setSessionRecordingsDao(SessionRecordingDao sessionRecordingsDao) {
	this.sessionRecordingsDao = sessionRecordingsDao;
    }

    private AudioDataDao audioDataDao;
    private VideoDataDao videoDataDao;
    private SessionRecordingDao sessionRecordingsDao;
    private PlaybackVO playbackVO = null;
    private PlayBackPlayListDao playBackPlayListDao = null;

    public PlayBackPlayListDao getPlayBackPlayListDao() {
	return playBackPlayListDao;
    }

    public void setPlayBackPlayListDao(PlayBackPlayListDao playBackPlayListDao) {
	this.playBackPlayListDao = playBackPlayListDao;
    }

    public PlaybackVO getPlaybackVO() {
	return playbackVO;
    }

    public void setPlaybackVO(PlaybackVO playbackVO) {
	this.playbackVO = playbackVO;
    }

    // public static void loadInit() {
    // ClassPathXmlApplicationContext appContext = new
    // ClassPathXmlApplicationContext(new String[] { "root-context.xml" });
    // // of course, an ApplicationContext is just a BeanFactory
    // factory = (BeanFactory) appContext;
    //
    // }

    public void process(String roomId) {
	// TODO Auto-generated method stub
	// ClassPathXmlApplicationContext appContext = new
	// ClassPathXmlApplicationContext(new String[] { "root-context.xml" });
	// of course, an ApplicationContext is just a BeanFactory
	// BeanFactory factory = (BeanFactory) appContext;

	// String roomId = "Dhiraj4";
	// if (args != null ) {
	// roomId = args;
	// }

	try {

	    // SessionRecordingDao sessionRecordingsDao = (SessionRecordingDao)
	    // factory.getBean("sessionRecordingsDao");

	    PlaybackUtil.setPlaybackVO(playbackVO);

	    List<SessionRecordings> sessionRecordingsList = sessionRecordingsDao.getSessionRecordingList(roomId);

	    if (sessionRecordingsList != null && sessionRecordingsList.size() == 0) {
		log.info("There are no recorded sessions for room " + roomId);
		return;
	    }

	    // AudioDataDao audioDataDao = (AudioDataDao)
	    // factory.getBean("audioDataDao");
	    List<AudioData> audioDataList = audioDataDao.getAudioDataList(roomId);

	    // VideoDataDao videoDataDao = (VideoDataDao)
	    // factory.getBean("videoDataDao");
	    // TODO remove videos with (<=)3seconds duration
	    List<VideoData> videoDataList = videoDataDao.getVideoDataList(roomId);
	    for (int i = 0; i < videoDataList.size(); i++) {

		if (videoDataList.get(i) == null || videoDataList.get(i).getStartTime() == null || videoDataList.get(i).getEndTime() == null) {
		    log.warn(" video cannot have bacis information null so exiting the process.  ");
		    log.warn(" Getting invalid information for video obj " + videoDataList.get(i));

		    return;
		}
		long vS = videoDataList.get(i).getStartTime().getTime();
		long vE = videoDataList.get(i).getEndTime().getTime();
		if ((vE - vS) < 3) {
		    videoDataList.remove(i);
		    log.debug("removing video at index:" + i + " with end-time:" + vE + " and start-time:" + vS);
		}
	    }

	    // Process each of the video files to transcode for seek.
	    PreProcessFLV.processFLV(videoDataList, playbackVO);

	    // PlayBackPlayListDao playBackPlayListDao = (PlayBackPlayListDao)
	    // factory.getBean("playBackPlayListDao");
	    // List<VideoData> videoDataList =
	    // sessionRecordingsDao.getVideoDataList(roomId);

	    // playbackVO = (PlaybackVO) factory.getBean("playBackVO");

	    // replace unix file path to windows file path.

	    if (PlaybackUtil.isWindows()) {
		log.debug(" windows machine so changing the source path for audio and video ..");
		PlaybackUtil.updateVideoPathWindows(playbackVO.getWinVideoPath(), videoDataList);
		PlaybackUtil.updateAudioPathWindows(playbackVO.getWinAudioPath(), audioDataList);
	    }
	    if (PlaybackUtil.isUbuntu()) {
		log.debug(" ubuntu machine so changing the Audio path ..");
		// PlaybackUtil.updateVideoPathWindows(playbackVO.getWinVideoPath(),
		// videoDataList);
		PlaybackUtil.updateAudioPathWindows(playbackVO.getUbuntuAudioPath(), audioDataList);
	    }

	    // WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)
	    // factory.getBean("whiteboardDataDao");
	    // whiteboardDataDao.getWhiteboardDTOForRoom("room1");

	    // HashMap contains Key-Value pairs of
	    // SessionRecording-SessionBucket
	    log.debug("--------------------------------------------------------------");
	    log.debug("Number of sessions in room " + roomId + " :: " + sessionRecordingsList.size());
	    for (int i = 0; i < sessionRecordingsList.size(); i++) {
		log.debug("Session " + i + " start-time:: " + sessionRecordingsList.get(i).getStartTime());
		log.debug("Session " + i + " end-time:: " + sessionRecordingsList.get(i).getEndTime());
	    }
	    log.debug("Number of audios in room " + roomId + " :: " + audioDataList.size());
	    for (int i = 0; i < audioDataList.size(); i++) {
		log.debug("Audio " + i + " start-time:: " + audioDataList.get(i).getStartTime());
		log.debug("Audio " + i + " end-time:: " + audioDataList.get(i).getEndTime());
		log.debug("Audio " + i + " file-path:: " + audioDataList.get(i).getFilePath());
	    }
	    log.debug("Number of videos in room " + roomId + " :: " + videoDataList.size());
	    for (int i = 0; i < videoDataList.size(); i++) {
		log.debug("Video " + i + " start-time:: " + videoDataList.get(i).getStartTime());
		log.debug("Video " + i + " end-time:: " + videoDataList.get(i).getEndTime());
		log.debug("Video " + i + " file-path:: " + videoDataList.get(i).getFilePath());
	    }
	    log.debug("--------------------------------------------------------------");

	    // TODO determining max height/width of videos
	    log.debug("--------------------------------------------------------------");
	    log.debug("Calculating video dimensions: Max height and width");
	    log.debug("--------------------------------------------------------------");
	    HashMap<String, String> videohm = new HashMap<String, String>();
	    String[] dimArr = getMaxVideoDimensions(videoDataList, videohm).split("##");
	    String maxVideoDimensions = dimArr[0];
	    String screenShareFlag = dimArr[1];
	    log.debug("maxVideoDimensions:: " + maxVideoDimensions);
	    log.debug("screenShareFlag:: " + screenShareFlag);

	    // if no sessions or no videos STOP PROCESS
	    if (sessionRecordingsList.size() == 0 || videoDataList.size() == 0) {
		log.debug("ERROR ALERT::No Sessions or No Videos in Room!");
	    }

	    /*
	     * Each session has a Session Bucket sessionBucket contains audios,
	     * videos of only that particular session audios & videos are
	     * trimmed and audio-video formats changed to "same-name".avi
	     */
	    HashMap<SessionRecordings, SessionBucket> sessionMap = new HashMap<SessionRecordings, SessionBucket>();

	    if (sessionRecordingsList.size() == 0) {
		log.debug("ERROR ALERT:: No sessions in this room");
	    } else {
		ArrayList<String> finalVideoPlaylist = new ArrayList<String>();

		log.debug("--------------------------------------------------------------");
		log.debug("Preparing session buckets for each session!");
		log.debug("--------------------------------------------------------------");
		for (int i = 0; i < sessionRecordingsList.size(); i++) {
		    long sessionStartTime = sessionRecordingsList.get(i).getStartTime().getTime();
		    long sessionEndTime = sessionRecordingsList.get(i).getEndTime().getTime();
		    log.debug("session Start Time::" + sessionRecordingsList.get(i).getStartTime());
		    log.debug("session End Time::" + sessionRecordingsList.get(i).getEndTime());

		    SessionBucket sb = new SessionBucket();
		    // if room has audios, find which of them lie in the current
		    // session
		    if (audioDataList.size() == 0) {
			log.debug("ALERT:::No audios in this room");
		    } else {
			for (int j = 0; j < audioDataList.size(); j++) {
			    // session audios in mp3 format
			    prepareAudioForSessionBucket(sb, j, audioDataList.get(j), sessionStartTime, sessionEndTime);
			}
			log.debug("Finished preparing session bucket audios!");
		    }
		    // if room has videos, find which of them lie in the current
		    // session
		    if (videoDataList.size() == 0) {
			log.debug("ERROR ALERT:: No videos in this room");
			// RETURN;
		    } else {
			for (int j = 0; j < videoDataList.size(); j++) {
			    // session videos in flv format
			    prepareVideoForSessionBucket(sb, j, videoDataList.get(j), sessionStartTime, sessionEndTime);
			}
			log.debug("Finished preparing session bucket videos!");
		    }
		    sessionMap.put(sessionRecordingsList.get(i), sb);
		}
		log.debug("SESSION BUCKETS PREPARED!!");

		log.debug("--------------------------------------------------------------");
		log.debug("Preparing Audio and Video for each session!");
		log.debug("--------------------------------------------------------------");
		Iterator sessionKeys = sessionMap.keySet().iterator();
		int sessionCounter = 0;
		while (sessionKeys.hasNext()) {
		    // to maintain a count of all the sessions
		    sessionCounter++;
		    SessionRecordings session = (SessionRecordings) sessionKeys.next();
		    long sessionStartTime = session.getStartTime().getTime();
		    long sessionEndTime = session.getEndTime().getTime();

		    SessionBucket sessionBucket = sessionMap.get(session);
		    log.debug("--------------------------------------------------------------");
		    log.debug("PREPARING SESSION AUDIO(pad and concat)");
		    log.debug("--------------------------------------------------------------");
		    // sessionAudioDataList contains - session audios
		    List<AudioData> sessionAudioDataList = sessionBucket.getAudioDataList();
		    // paddedSessionAudioDataList contains - session audios are
		    // padded
		    List<AudioData> paddedSessionAudioDataList = new ArrayList<AudioData>();
		    // contains the final audio
		    AudioData sessionAudio = null;
		    // if session has atleast 1 audio pad all the gaps in
		    // session-bucket-audio-list with silent-audios
		    if (sessionAudioDataList.size() > 0) {
			paddedSessionAudioDataList = padAudioPlaylist(sessionAudioDataList, sessionStartTime);
			log.debug("_______________________________________________________________");
			log.debug("Number of audios after padding :: " + paddedSessionAudioDataList.size());
			for (int i = 0; i < paddedSessionAudioDataList.size(); i++) {
			    log.debug("Audio " + i + " start-time:: " + paddedSessionAudioDataList.get(i).getStartTime());
			    log.debug("Audio " + i + " end-time:: " + paddedSessionAudioDataList.get(i).getEndTime());
			    log.debug("Audio " + i + " file-path:: " + paddedSessionAudioDataList.get(i).getFilePath());
			}
			log.debug("_______________________________________________________________");
			// concatenate audios if more than one
			sessionAudio = concatenateAudios(paddedSessionAudioDataList, sessionCounter);
		    } else {
			if (audioDataList.size() > 0) {
			    log.debug("Session has no audios. Since, room has audios.. creating silent audio for this session.");
			    sessionAudio = createSilentAudio(sessionEndTime - sessionStartTime);
			} else {
			    log.debug("Session has no audios. Room has no audios.. sessionAudio=NULL");
			    sessionAudio = null;
			}
		    }
		    // sessionAudio is null only when roomAudio is null
		    if (sessionAudio != null) {
			log.debug("Final session audio path::" + sessionAudio.getFilePath());
		    } else {
			log.debug("Final session audio path:: NULL (Reason-No audios in Room)");
		    }

		    log.debug("--------------------------------------------------------------");
		    log.debug("PREPARING SESSION VIDEO (pad screenshare, frmtRes(IM) and concat)");
		    log.debug("--------------------------------------------------------------");
		    // sessionVideoDataList = session videos
		    List<VideoData> sessionVideoDataList = sessionBucket.getVideoDataList();
		    // uniformSessionVideoDataList - session videos of same
		    // resolution, format
		    List<VideoData> uniformSessionVideoDataList = new ArrayList<VideoData>();
		    // paddedSessionVideoPlaylist - padded session videos
		    List<VideoData> paddedSessionVideoDatalist = new ArrayList<VideoData>();
		    // contains the final video path
		    VideoData sessionVideo = null;

		    // if session has atleast 1 video
		    if (sessionVideoDataList.size() > 0) {
			// if screen-share was recorded
			if (screenShareFlag.equals("true")) {
			    log.debug("session contains screenshare video..");
			    paddedSessionVideoDatalist = padSessionVideoPlaylist(sessionVideoDataList, maxVideoDimensions);
			    // uniformSessionVideoDataList =
			    // setVideoFormatResolution(paddedSessionVideoPlaylist,
			    // videoDimensions);
			    uniformSessionVideoDataList = VideoImageMagick.formatSessionVideoPlaylist(paddedSessionVideoDatalist, maxVideoDimensions, playbackVO);
			} else {
			    // Set resolution of all Session Bucket Videos
			    // uniformSessionVideoDataList =
			    // setVideoFormatResolution(sessionVideoDataList,
			    // videoDimensions);
			    uniformSessionVideoDataList = VideoImageMagick.formatSessionVideoPlaylist(sessionVideoDataList, maxVideoDimensions, playbackVO);
			}
			log.debug("_______________________________________________________________");
			log.debug("Number of videos after setting resolution :: " + uniformSessionVideoDataList.size());
			for (int i = 0; i < uniformSessionVideoDataList.size(); i++) {
			    log.debug("Video " + i + " start-time:: " + uniformSessionVideoDataList.get(i).getStartTime());
			    log.debug("Video " + i + " end-time:: " + uniformSessionVideoDataList.get(i).getEndTime());
			    log.debug("Video " + i + " file-path:: " + uniformSessionVideoDataList.get(i).getFilePath());
			}
			log.debug("_______________________________________________________________");
			// concatenate videos if more than one
			sessionVideo = concatenateVideos(uniformSessionVideoDataList, sessionCounter);
		    }
		    // if room has atleast one video
		    else {
			log.warn("ERROR ALERT::Session has no videos.. Something's gotta be wrong!");
			if (videoDataList.size() < 1) {
			    log.warn("ERROR ALERT::Room Itself has no videos.. Something's terribly wrong!");
			}
		    }
		    log.debug("FINAL SESSION VIDEO PATH::" + sessionVideo.getFilePath());

		    log.debug("--------------------------------------------------------------");
		    log.debug("PREPARING FINAL SESSION VIDEO: Merging Audio-Video");
		    log.debug("--------------------------------------------------------------");
		    /*
		     * if no audio. then the video paths are directly added to
		     * the final video playlist. else map each audio with
		     * corresponding videos
		     */
		    if (sessionAudio != null) {
			String sessionVideoPlaylist = mergeAudioVideo(sessionAudio, sessionVideo);
			log.debug("sessionVideoPlaylist :: " + sessionVideoPlaylist);
			finalVideoPlaylist.add(sessionVideoPlaylist);
		    } else {
			// long duration = sessionVideo.getEndTime().getTime() -
			// sessionVideo.getStartTime().getTime();
			log.debug("sessionVideoPlaylist :: " + sessionVideo.getFilePath());// +"##"+duration);
			finalVideoPlaylist.add(sessionVideo.getFilePath());// +"##"+duration);
		    }
		}
		log.debug("--------------------------------------------------------------");
		log.debug("SESSION DONE! Adding to finalVideoPlaylist");
		log.debug("--------------------------------------------------------------");
		List<PlayBackPlayList> listPlayback = new ArrayList<PlayBackPlayList>();
		PlayBackPlayList playlist = null;
		for (int i = 0; i < finalVideoPlaylist.size(); i++) {
		    playlist = new PlayBackPlayList();
		    String a = finalVideoPlaylist.get(i);
		    // String a[] = new String[2];
		    // a = finalVideoPlaylist.get(i).split("##");
		    // String flv_filepath = convertAVItoFLV(a[0]);

		    // convert all playlist videos to .mp4
		    // String mp4_filepath = convertAVItoMP4264(a[0]);
		    // log.debug("_______________________________________________________________");
		    // log.debug("sessionVideoPlaylist::mp4_filepath :: " +
		    // mp4_filepath);
		    // playlist.setFilePath(mp4_filepath);

		    // convert all playlist videos to .flv
		    String flv_filepath = convertAVItoFLV(a);
		    playlist = setPlayBackPlayList(flv_filepath, roomId);
		    log.debug("_______________________________________________________________");
		    log.debug("sessionVideoPlaylist::flv_filepath :: " + flv_filepath);
		    log.debug("_______________________________________________________________");
		    listPlayback.add(playlist);
		}
		// concat videos to prepare flow player playlist
		String flowPlayerVideoPath = flowPlayerVideo(listPlayback, roomId);
		PlayBackPlayList flowPlayerVideo = setPlayBackPlayList(flowPlayerVideoPath, roomId);
		// upload to youtube and get-set the url
		YoutubeUploadService ytUpload = new YoutubeUploadService();
		String youtubeURL = ytUpload.uploadVideo(flowPlayerVideo.getFilePath());
		log.debug("_______________________________________________________________");
		log.debug("flowPlayerVideoPath :: " + flowPlayerVideoPath);
		log.debug("youtubeURL :: " + youtubeURL);
		log.debug("_______________________________________________________________");
		flowPlayerVideo.setYoutubeUrl(youtubeURL);
		updateFinalVideoTable(flowPlayerVideo, playBackPlayListDao);
	    }
	} catch (Exception e) {
	    log.error(e.getMessage(), e);
	}
    }

    private PlayBackPlayList setPlayBackPlayList(String videoPath, String roomId) {
	HashMap<String, String> videohm1 = new HashMap<String, String>();
	String cmd = " -i " + videoPath;
	PlaybackUtil.invokeVideoAttribProcess(cmd, videohm1);
	PlayBackPlayList playlist = new PlayBackPlayList();
	playlist.setWidth(PlaybackUtil.getNum(videohm1.get("width")));
	playlist.setHeight(PlaybackUtil.getNum(videohm1.get("height")));
	playlist.setSize(PlaybackUtil.getNumLong(videohm1.get("filesize")));
	playlist.setDuration(videohm1.get("duration"));
	playlist.setFilePath(videoPath);
	playlist.setInsertedDate(new Date());
	playlist.setRoomName(roomId);
	return playlist;
    }

    private String flowPlayerVideo(List<PlayBackPlayList> listPlayback, String roomId) {
	log.debug("Preparing flowPlayerPlaylist..............");
	// mencoder.exe -oac copy -ovc lavc wb_audio3.avi
	// screen_share_audio3.avi -o complete33.avi
	String flowPlayerVideoPath = listPlayback.get(0).getFilePath().replace(".flv", "flowPlayerVideo" + roomId + ".flv");
	;
	String cmd = null;
	if (listPlayback.size() > 1) {
	    log.debug("listPlayback.size() > 1");
	    cmd = " -oac copy -ovc lavc ";
	    for (int i = 0; i < listPlayback.size(); i++) {
		cmd = cmd + " " + listPlayback.get(i).getFilePath();
	    }
	    cmd = cmd + " -o " + flowPlayerVideoPath;
	    log.debug("MenCoder Command for concatenating videos:::" + cmd);
	    PlaybackUtil.invokeMencoderProcess(cmd);
	} else {
	    log.debug("listPlayback.size() <= 1");
	    cmd = " -i " + listPlayback.get(0).getFilePath() + " " + flowPlayerVideoPath;
	    log.debug("Ffmpeg Command for concatenating just the 1 video::: (renaming file)" + cmd);
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	}
	return flowPlayerVideoPath;
    }

    private List<VideoData> padSessionVideoPlaylist(List<VideoData> sessionVideoDataList, String maxVideoDimensions) {
	// Temporary sessionVideoPlaylist contains videos for this session
	log.debug("Inside padSessionVideoPlaylist..............");
	List<VideoData> tempSessionVideoPlaylist = new ArrayList<VideoData>();

	VideoData vd = null;
	for (int i = 0; i < sessionVideoDataList.size(); i++) {
	    String videoType = sessionVideoDataList.get(i).getVideoType();
	    if (videoType != null && videoType.equals("DESKTOP")) {
		vd = new VideoData();
		// String newVideoPath = PlaybackUtil.getUnique();

		log.debug("screen share video found. Padding with (diffDuration)sec vid :");

		// get the ffmpeg duration
		HashMap<String, String> vhm = new HashMap<String, String>();
		String cmd = " -i " + sessionVideoDataList.get(i).getFilePath();
		PlaybackUtil.invokeVideoAttribProcess(cmd, vhm);
		log.debug(" printing the video hash map ...::" + vhm);

		long start_time = sessionVideoDataList.get(i).getStartTime().getTime();
		long end_time = sessionVideoDataList.get(i).getEndTime().getTime();
		long dbDuration = (end_time - start_time) / 1000;
		long actualDuration = PlaybackUtil.getNumLong(vhm.get("duration"));
		long padDuration = (dbDuration - actualDuration);

		vd.setStartTime(new Date(start_time));
		vd.setEndTime(new Date(start_time + (padDuration)));

		log.debug(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		log.debug(" start time for screen share video:: " + PlaybackUtil.secondsToHours(start_time));
		log.debug(" end time for screen share video:: " + PlaybackUtil.secondsToHours(end_time));
		log.debug(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		log.debug(" Actual (ffmpeg) video duration :: " + actualDuration);
		log.debug(" Expected video duration(from database) :: " + dbDuration);
		log.debug(" duration to pad is ::" + padDuration);

		if (padDuration == 0) {

		    log.debug("Padding is not needed as the pad duration is 0");
		    return sessionVideoDataList;
		}

		// if (padDuration > 0 && padDuration < 7) {

		// actualDuration-dbDuration
		// -s "+maxVideoDimensions+"
		// String cmd =
		// " -r 1 -b 200 -i %03d.jpg "+newVideoPath+" padScreenShareVideo.avi";
		// PlaybackUtil.invokeFfmpegProcess(curDir);

		File f = new File(sessionVideoDataList.get(i).getFilePath());
		String curDir = null;
		if (f != null && f.isFile())
		    curDir = f.getParent();
		else {
		    log.warn(" The file path is null... this is not right. ");
		}

//		// Driectory for pad creating temp pads for screenshare
		String uniquePath = PlaybackUtil.getUnique();
//		String strDirectoy = playbackVO.getTempLocation() + "/SSPads" + uniquePath;
//		// TODO Create random sessionVideo directory
//		boolean success = VideoImageMagick.createDir(strDirectoy);
//		if (success == false) {
//		    log.warn(" Could not create the directory.... returnign  ");
//		    return null;
//		}

//		// TODO resize screenShareImage (in current directory) using
//		// ImageMagick
//		String imagePath = curDir + "/screenShareImage.jpg";
//		log.debug("before creating two images...");
//		cmd = " " + imagePath + " -resize " + maxVideoDimensions + " " + strDirectoy + "/01.jpg";
//		PlaybackUtil.invokeImageMagickProcess(cmd);
//		log.debug("debug 1::" + cmd);
//		cmd = " " + imagePath + " -resize " + maxVideoDimensions + " " + strDirectoy + "/02.jpg";
//		PlaybackUtil.invokeImageMagickProcess(cmd);
//		log.debug("debug 1::" + cmd);
//
//		// TODO make 1sec video of 2 screenShareImage's (2 frames)
//		String imageVideoPath = strDirectoy + "/tempSSPad" + uniquePath + ".flv";
//		cmd = " -y -r 2 -i " + strDirectoy + "/%02d.jpg -an " + imageVideoPath;
//		PlaybackUtil.invokeFfmpegProcess(cmd);
//		// TODO concat (padDuration)number of 1sec videos
//		cmd = " -oac copy -ovc lavc ";
//		for (int j = 0; j < (int) padDuration; j++) {
//		    cmd = cmd + " " + imageVideoPath;
//		}
//		cmd = cmd + " -o " + curDir + "/padScreenShareVideo" + uniquePath + ".flv";
//		log.debug("MenCoder Command for concatenating videos:::" + cmd);
//		PlaybackUtil.invokeMencoderProcess(cmd);

		//cut the 1hr video to specified duration
		String SSPaddingVideoPath = "/opt/InnowhiteData/scripts/ScreenSharePad.flv";
		cmd = " -i "+SSPaddingVideoPath+" -t " + PlaybackUtil.secondsToHours(padDuration*1000) + " -ar 44100 -ab 64k "+curDir+"/SSPad"+uniquePath+".flv";
		//set the resolution
		String[] dim = maxVideoDimensions.split("x");
		PlaybackUtil.invokeFfmpegProcess(cmd);
		int width = Integer.parseInt(dim[0]) - 10;
		int height = Integer.parseInt(dim[1]) - 5;
		cmd = " "+curDir+"/SSPad"+uniquePath+".flv -oac copy -ovc lavc -vf scale="+width+":"+height+" -o "+curDir+"/SSPad"+uniquePath+maxVideoDimensions+".flv";
		PlaybackUtil.invokeMencoderProcess(cmd);
		
		// String[] dim = maxVideoDimensions.split("x");
		// String filePath = curDir +
		// "/padScreenShareVideo"+padDuration+".avi";
		// String cmd = " "+filePath+" -oac copy -ovc lavc -vf scale=" +
		// dim[0] + ":" + dim[1] + " -o "+filePath.replace(".flv",
		// dim[0]+"x"+dim[1]+".avi");
		// log.debug("::::MenCoder Command for setting resolution of video"
		// + i + "/" + sessionVideoDataList.size() + "::::" + cmd);
		// PlaybackUtil.invokeMencoderProcess(cmd);
		// padDuation determines - one of 6 videos (of 3s duration) path
		// from the current directory
		vd.setFilePath(curDir+"/SSPad"+uniquePath+maxVideoDimensions+".flv");
		// vd.setId(sessionVideoDataList.get(i).getId());
		// vd.setRoomName(sessionVideoDataList.get(i).getRoomName());
		// vd.setVideoType("VIDEO");
		tempSessionVideoPlaylist.add(vd);

		vd = new VideoData();
		vd.setStartTime(new Date(start_time + padDuration));
		vd.setEndTime(sessionVideoDataList.get(i).getEndTime());
		vd.setFilePath(sessionVideoDataList.get(i).getFilePath());
		vd.setId(sessionVideoDataList.get(i).getId());
		vd.setRoomName(sessionVideoDataList.get(i).getRoomName());
		vd.setVideoType(videoType);
		tempSessionVideoPlaylist.add(sessionVideoDataList.get(i));
	    } else {
		tempSessionVideoPlaylist.add(sessionVideoDataList.get(i));
	    }
	}
	return tempSessionVideoPlaylist;
    }

    private String getMaxVideoDimensions(List<VideoData> videoList, HashMap<String, String> vhm) {
	log.debug("Entered getMaxVideoDimensions..............");
	String cmd = null;
	String screenShareFlag = "false";
	int maxWidth = 0, tempWidth = 0;
	int maxHeight = 0, tempHeight = 0;

	for (int i = 0; i < videoList.size(); i++) {
	    String videoType = videoList.get(i).getVideoType();
	    log.debug("Video" + i + " is of type:: " + videoType);
	    if (videoType.equals("DESKTOP")) {
		screenShareFlag = "true";
	    }
	    if (videoType != null) {
		if (i == 0) {
		    cmd = " -i " + videoList.get(i).getFilePath();
		    PlaybackUtil.invokeVideoAttribProcess(cmd, vhm);
		    maxWidth = Integer.parseInt(vhm.get("width"));
		    maxHeight = Integer.parseInt(vhm.get("height"));
		} else if (i > 0) {
		    cmd = " -i " + videoList.get(i).getFilePath();
		    PlaybackUtil.invokeVideoAttribProcess(cmd, vhm);
		    tempWidth = Integer.parseInt(vhm.get("width"));
		    tempHeight = Integer.parseInt(vhm.get("height"));
		    if (tempWidth > maxWidth) {
			maxWidth = tempWidth;
		    }
		    if (tempHeight > maxHeight) {
			maxWidth = tempHeight;
		    }
		}
	    } else if (videoType == null) {
		log.warn("printing the videoObj :: " + videoList);
	    }
	}
	return (maxWidth+5) + "x" + (maxHeight+5) + "##" + screenShareFlag;
    }

    private String getScreenShareDimensions(List<VideoData> videoList, HashMap<String, String> videohm) {
	log.debug("Entered getScreenShareDimensions..............");
	String screenShareFlag = "false";
	for (int i = 0; i < videoList.size(); i++) {
	    String videoType = videoList.get(i).getVideoType();
	    log.debug("Video" + i + " is of type:: " + videoType);
	    if (videoType != null && videoType.equals("DESKTOP")) {
		screenShareFlag = "true";
		String cmd = " -i " + videoList.get(i).getFilePath();
		PlaybackUtil.invokeVideoAttribProcess(cmd, videohm);
		log.debug("DESKTOP Video :: breaking out!!" + videoType);
		break;// i=sessionVideoDataList.size();
	    } else if (videoType != null && i == (videoList.size() - 1)) {
		// executed only when last video is being processed and no
		// screen-share detected
		log.debug("i == sessionVideoDataList.size()-1.. no screen-share.. getting dimensions of whiteboard video..");
		String cmd = " -i " + videoList.get(0).getFilePath();
		PlaybackUtil.invokeVideoAttribProcess(cmd, videohm);
	    } else if (videoType == null) {
		log.warn("printing the videoObj :: " + videoList);
	    }
	}
	return videohm.get("width") + "x" + videohm.get("height") + "##" + screenShareFlag;
    }

    private String mergeAudioVideo(AudioData sessionAudio, VideoData sessionVideo) {
	log.debug("Inside Merge finalSessionAudio and finalSessionVideo..............");
	log.debug("_______________________________________________________________");
	log.debug("sessionAudio :: " + sessionAudio.getFilePath());
	log.debug("sessionVideo :: " + sessionVideo.getFilePath());
	log.debug("_______________________________________________________________");

	String newVideoPath = PlaybackUtil.getUnique();
	String cmd = null;
	// ffmpeg.exe -i 1332_2011-06-01-14-42-064756410402.mp3 -ar 44100 -ab
	// 64k Rahul3_audio.avi
	log.debug(":::first converting sessionAudio.mp3 to sessionAudio.avi:::");
	cmd = " -i " + sessionAudio.getFilePath() + " -ar 44100 -ab 64k " + sessionAudio.getFilePath().replace(".mp3", ".avi");
	PlaybackUtil.invokeFfmpegProcess(cmd);
	// merging session audio-video
	cmd = " -i " + sessionVideo.getFilePath() + " -i " + sessionAudio.getFilePath().replace(".mp3", ".avi") + " -ar 44100 -ab 64k "
		+ sessionVideo.getFilePath().replace(".avi", newVideoPath + "playlist.avi");
	log.debug("ffmpeg Command for merging final session audio, video:::" + cmd);
	PlaybackUtil.invokeFfmpegProcess(cmd);
	// long duration = sessionVideo.getEndTime().getTime() -
	// sessionVideo.getStartTime().getTime();
	return sessionVideo.getFilePath().replace(".avi", newVideoPath + "playlist.avi");// +
											 // "##"
											 // +
											 // duration;
    }

    private AudioData concatenateAudios(List<AudioData> paddedSessionAudioDataList, int sessionCounter) {
	log.debug("Inside concatenateAudios..............");
	AudioData ad = new AudioData();
	String sessionAudioPath = paddedSessionAudioDataList.get(0).getFilePath().replace(".mp3", "SessionAudio" + sessionCounter + ".mp3");
	if (paddedSessionAudioDataList.size() > 1) {
	    String cmd = " " + sessionAudioPath;
	    // String cmd =
	    // paddedSessionAudioDataList.get(0).getFilePath().replace(".mp3",
	    // "finalSessionAudio.mp3");
	    for (int i = 0; i < paddedSessionAudioDataList.size(); i++) {
		cmd = cmd + " " + paddedSessionAudioDataList.get(i).getFilePath();
	    }
	    log.debug("Mp3Wrap Command for concatenating audios:::" + cmd);
	    PlaybackUtil.invokeMp3Process(cmd);
	    ad.setEndTime(paddedSessionAudioDataList.get(paddedSessionAudioDataList.size() - 1).getEndTime());
	} else {
	    String cmd = " -i " + paddedSessionAudioDataList.get(0).getFilePath() + " " + sessionAudioPath;
	    log.debug("Ffmpeg Command for concat 1 audio::: (renaming file)" + cmd);
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    ad.setEndTime(paddedSessionAudioDataList.get(0).getEndTime());
	}
	ad.setStartTime(paddedSessionAudioDataList.get(0).getStartTime());
	// ad.setFilePath(paddedSessionAudioDataList.get(0).getFilePath().replace(".mp3",
	// "finalSessionAudio.mp3"));
	ad.setFilePath(sessionAudioPath);
	// ad.setId(id);
	// ad.setRoomName(roomName);
	return ad;
    }

    private ArrayList<VideoData> setVideoFormatResolution(List<VideoData> sessionVideoDataList, String screenShareDimensions) {
	log.debug("Inside setVideoFormatResolution..............");
	// mencoder.exe wb_audio.avi -oac copy -ovc lavc -vf scale=800:600 -o
	// wb_audio3.avi
	String dim[] = screenShareDimensions.split("x");
	ArrayList<VideoData> uniformSessionVideoDataList = new ArrayList<VideoData>();
	VideoData vd = null;
	// String newVideoPath = PlaybackUtil.getUnique();
	String cmd = null;
	log.debug(":::setting resolution :::" + dim[0] + ":" + dim[1]);
	for (int i = 0; i < sessionVideoDataList.size(); i++) {
	    vd = new VideoData();
	    long a = (sessionVideoDataList.get(i).getEndTime().getTime() - sessionVideoDataList.get(i).getStartTime().getTime());
	    log.debug("::::DURATION::::" + PlaybackUtil.secondsToHours(a));
	    // screenShareDimension
	    cmd = " " + sessionVideoDataList.get(i).getFilePath() + " -oac copy -ovc lavc -vf scale=" + dim[0] + ":" + dim[1] + " -o "
		    + sessionVideoDataList.get(i).getFilePath().replace(".flv", dim[0] + "x" + dim[1] + ".avi");
	    log.debug("::::MenCoder Command for setting resolution of video" + i + "/" + sessionVideoDataList.size() + "::::" + cmd);
	    PlaybackUtil.invokeMencoderProcess(cmd);
	    vd.setStartTime(sessionVideoDataList.get(i).getStartTime());
	    vd.setEndTime(sessionVideoDataList.get(i).getEndTime());
	    vd.setFilePath(sessionVideoDataList.get(i).getFilePath().replace(".flv", dim[0] + "x" + dim[1] + ".avi"));
	    uniformSessionVideoDataList.add(vd);
	}
	return uniformSessionVideoDataList;
    }

    private VideoData concatenateVideos(List<VideoData> uniformSessionVideoDataList, int sessionCounter) {
	log.debug("Inside setVideoFormatResolution..............");
	// mencoder.exe -oac copy -ovc lavc wb_audio3.avi
	// screen_share_audio3.avi -o complete33.avi
	VideoData vd = new VideoData();
	String sessionVideoPath = uniformSessionVideoDataList.get(0).getFilePath().replace(".flv", "SessionVideo" + sessionCounter + ".avi");
	;
	if (uniformSessionVideoDataList.size() > 1) {
	    String cmd = " -oac copy -ovc lavc ";
	    for (int i = 0; i < uniformSessionVideoDataList.size(); i++) {
		cmd = cmd + " " + uniformSessionVideoDataList.get(i).getFilePath();
	    }
	    cmd = cmd + " -o " + sessionVideoPath;
	    log.debug("MenCoder Command for concatenating videos:::" + cmd);
	    PlaybackUtil.invokeMencoderProcess(cmd);
	    vd.setEndTime(uniformSessionVideoDataList.get(uniformSessionVideoDataList.size() - 1).getEndTime());
	} else {
	    String cmd = " -i " + uniformSessionVideoDataList.get(0).getFilePath() + " " + sessionVideoPath;
	    log.debug("Ffmpeg Command for concatenating just the 1 video::: (renaming file)" + cmd);
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    vd.setEndTime(uniformSessionVideoDataList.get(0).getEndTime());
	}
	vd.setStartTime(uniformSessionVideoDataList.get(0).getStartTime());
	vd.setFilePath(sessionVideoPath);
	return vd;
    }

    private List<AudioData> padAudioPlaylist(List<AudioData> sessionAudioDataList, long sessionStartTime) {
	// Temporary sessionAudioPlaylist contains audios for this session
	log.debug("Inside padAudioPlaylist..............");
	// "silence"+newAudioPath+".mp3";
	List<AudioData> tempSessionAudioPlaylist = new ArrayList<AudioData>();
	AudioData ad = null;
	for (int i = 0; i < sessionAudioDataList.size(); i++) {
	    ad = new AudioData();
	    long audioStartTime = sessionAudioDataList.get(i).getStartTime().getTime();
	    log.debug("audioStartTime: " + sessionAudioDataList.get(i).getStartTime());
	    log.debug("sessionStartTime: " + PlaybackUtil.secondsToHours(sessionStartTime));
	    // if its the first audio of the session
	    if (i == 0) {
		if ((audioStartTime - sessionStartTime) > 0) {
		    log.debug("(audio Starts after session Start");
		    String silentAudioPath = (createSilentAudio(audioStartTime - sessionStartTime)).getFilePath();
		    log.debug("silentAudioPath: " + silentAudioPath);
		    ad.setStartTime(new Date(sessionStartTime));
		    ad.setEndTime(new Date(audioStartTime));
		    // ad.setId(id);
		    // ad.setRoomName(roomName);
		    ad.setFilePath(silentAudioPath);

		} else {
		    log.debug("(audio and session Start at the same Time");
		    ad.setStartTime(sessionAudioDataList.get(i).getStartTime());
		    ad.setEndTime(sessionAudioDataList.get(i).getEndTime());
		    // ad.setId(id);
		    // ad.setRoomName(roomName);
		    ad.setFilePath(sessionAudioDataList.get(i).getFilePath());
		}
	    } else {
		long prevAudioEndTime = sessionAudioDataList.get(i - 1).getEndTime().getTime();
		if ((audioStartTime - prevAudioEndTime) > 0) {
		    log.debug("there is a gap between audio and previous audio");
		    // String silentAudioPath = createSilentAudio(audioStartTime
		    // - audioStartTime - prevAudioEndTime).getFilePath();
		    String silentAudioPath = createSilentAudio(audioStartTime - prevAudioEndTime).getFilePath();
		    log.debug("silentAudioPath: " + silentAudioPath);
		    ad.setStartTime(new Date(prevAudioEndTime));
		    ad.setEndTime(new Date(audioStartTime));
		    // ad.setId(id);
		    // ad.setRoomName(roomName);
		    ad.setFilePath(silentAudioPath);
		} else {
		    log.debug("next audio Starts soon after previous audio");
		    ad.setStartTime(sessionAudioDataList.get(i).getStartTime());
		    ad.setEndTime(sessionAudioDataList.get(i).getEndTime());
		    // ad.setId(id);
		    // ad.setRoomName(roomName);
		    ad.setFilePath(sessionAudioDataList.get(i).getFilePath());
		}
	    }
	    tempSessionAudioPlaylist.add(ad);
	}
	log.debug("tempSessionAudioPlaylist.size()" + tempSessionAudioPlaylist.size());
	return tempSessionAudioPlaylist;
    }

    private AudioData createSilentAudio(long duration) {
	log.debug("Inside createSilentAudio of " + duration + " seconds..............");
	AudioData ad = new AudioData();
	String silentAudioPath = playbackVO.getSilentAudioPath();// "C:/Users/sony/Desktop/Innowhite/playback/silence.mp4";
	String newAudioPath = PlaybackUtil.getUnique();
	String cmd = null;
	if (duration <= 7200000) {
	    cmd = " -i " + silentAudioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(duration) + " -ar 44100 -ab 64k " + silentAudioPath.replace(".mp4", newAudioPath + ".mp3");
	} else {
	    log.debug("cannot create silent audio of " + duration + " (>7200000)seconds");
	}
	// cmd = cmd = " -i "+silentAudioPath+"";
	PlaybackUtil.invokeFfmpegProcess(cmd);
	ad.setStartTime(new Date(0));
	ad.setEndTime(new Date(duration));
	// ad.setId(id);
	// ad.setRoomName(roomName);
	ad.setFilePath(silentAudioPath.replace(".mp4", newAudioPath + ".mp3"));
	return ad;
    }

    @SuppressWarnings("unused")
    private String convertAVItoMP4264(String avi_filepath) {
	String cmd = " -i " + avi_filepath + " -vcodec libx264 -g 250 -bf 3 -b_strategy 1 -coder 1 -qmin 10 "
		+ "-qmax 51 -sc_threshold 40 -flags +loop -cmp +chroma -me_range 16 -me_method hex -subq 5 -i_qfactor 0.71 "
		+ "-qcomp 0.6 -qdiff 4 -directpred 1 -flags2 +fastpskip -dts_delta_threshold 1 -ab 64k -ar 44100 " + "-s 800x600 " + avi_filepath.replace(".avi", ".mp4");
	PlaybackUtil.invokeFfmpegProcess(cmd);
	String mp4_filepath = avi_filepath.replace(".avi", ".mp4");
	// log.debug("-----mp4_filepath before returning------" + mp4_filepath);
	return mp4_filepath;
    }

    private String convertAVItoFLV(String avi_filepath) {
	log.debug("Inside convertAVItoFLV ..............");
	String cmd = " -i " + avi_filepath + " " + avi_filepath.replace(".avi", ".flv");
	PlaybackUtil.invokeFfmpegProcess(cmd);
	String flv_filepath = avi_filepath.replace(".avi", ".flv");
	return flv_filepath;
    }

    private static void prepareVideoForSessionBucket(SessionBucket sb, int j, VideoData videoData, long sessionStartTime, long sessionEndTime) {
	log.debug("------------------------------------");
	log.debug("preparing VideoForSessionBucket.....");
	log.debug("------------------------------------");

	// if (videoData.getStartTime() != null && videoData.getEndTime() !=
	// null) {
	long videoStartTime = videoData.getStartTime().getTime();
	long videoEndTime = videoData.getEndTime().getTime();
	String videoPath = videoData.getFilePath();
	log.debug("videoStartTime::" + videoData.getStartTime());
	log.debug("videoEndTime::" + videoData.getEndTime());
	log.debug("videoPath::" + videoPath);

	String cmd = null;
	VideoData vd = null;

	if (videoStartTime <= sessionStartTime && videoEndTime <= sessionEndTime && videoEndTime >= sessionStartTime) {
	    log.debug("videoStartTime<=sessionStartTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime");
	    String newVideoPath = PlaybackUtil.getUnique();
	    cmd = "-i " + videoPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - videoStartTime) + " -an -t " + PlaybackUtil.secondsToHours(videoEndTime - sessionStartTime) + " -acodec copy -vcodec copy "
		    + videoPath.replace(".flv", newVideoPath + ".flv");
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    vd = new VideoData();
	    vd.setFilePath(videoPath.replace(".flv", newVideoPath + ".flv"));
	    vd.setStartTime(new Date(sessionStartTime));
	    vd.setEndTime(new Date(videoEndTime));
	} else if (videoStartTime <= sessionStartTime && videoEndTime >= sessionEndTime) {
	    log.debug("videoStartTime<=sessionStartTime && videoEndTime>=sessionEndTime");
	    String newVideoPath = PlaybackUtil.getUnique();
	    cmd = "-i " + videoPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - videoStartTime) + " -an -t " + PlaybackUtil.secondsToHours(sessionEndTime - sessionStartTime) + " -acodec copy -vcodec copy "
		    + videoPath.replace(".flv", newVideoPath + ".flv");
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    vd = new VideoData();
	    vd.setFilePath(videoPath.replace(".flv", newVideoPath + ".flv"));
	    vd.setStartTime(new Date(sessionStartTime));
	    vd.setEndTime(new Date(sessionEndTime));
	} else if (videoStartTime >= sessionStartTime && videoStartTime <= sessionEndTime && videoEndTime <= sessionEndTime && videoEndTime >= sessionStartTime) {
	    log.debug("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime");
	    String newVideoPath = PlaybackUtil.getUnique();
	    cmd = "-i " + videoPath + " -ss 00:00:00 -an -t " + PlaybackUtil.secondsToHours(videoEndTime - videoStartTime) + " -acodec copy -vcodec copy " + videoPath.replace(".flv", newVideoPath + ".flv");
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    vd = new VideoData();
	    vd.setFilePath(videoPath.replace(".flv", newVideoPath + ".flv"));
	    vd.setStartTime(new Date(videoStartTime));
	    vd.setEndTime(new Date(videoEndTime));
	} else if (videoStartTime >= sessionStartTime && videoStartTime <= sessionEndTime && videoEndTime >= sessionEndTime) {
	    log.debug("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime>=sessionEndTime");
	    String newVideoPath = PlaybackUtil.getUnique();
	    cmd = "-i " + videoPath + " -ss 00:00:00 -an -t " + PlaybackUtil.secondsToHours(sessionEndTime - videoStartTime) + " -acodec copy -vcodec copy " + videoPath.replace(".flv", newVideoPath + ".flv");
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    vd = new VideoData();
	    vd.setFilePath(videoPath.replace(".flv", newVideoPath + ".flv"));
	    vd.setStartTime(new Date(videoStartTime));
	    vd.setEndTime(new Date(sessionEndTime));
	}
	// log.debug("videoData"+j+"::"+vd);
	// log.debug("videoData: Start Time:: "+vd.getStartTime());
	// log.debug("videoData: End Time:: "+vd.getEndTime());
	if (vd != null) {
	    log.debug("videoData: File Path:: " + vd.getFilePath());
	    vd.setVideoType(videoData.getVideoType());
	    vd.setRoomName(videoData.getRoomName());
	    sb.getVideoDataList().add(vd);
	} else {

	    log.warn(" printing all vals because there seems to be some thing wrong ");
	    PlaybackUtil.printVals(videoStartTime, sessionStartTime, videoEndTime, sessionEndTime);

	}
	log.debug("DONE! Preparing videos for session bucket!");
	// }
    }

    private static void prepareAudioForSessionBucket(SessionBucket sb, int j, AudioData audioData, long sessionStartTime, long sessionEndTime) {
	log.debug("------------------------------------");
	log.debug("preparing Audio " + j + " ForSessionBucket.....");
	log.debug("------------------------------------");
	long audioStartTime = audioData.getStartTime().getTime();
	long audioEndTime = audioData.getEndTime().getTime();
	String audioPath = audioData.getFilePath();
	log.debug("audioStartStime::" + audioData.getStartTime());
	log.debug("audioEndStime::" + audioData.getEndTime());
	log.debug("audio Path::" + audioPath);

	String cmd = null;
	AudioData ad = null;

	if (audioStartTime <= sessionStartTime && audioEndTime <= sessionEndTime && audioEndTime >= sessionStartTime) {
	    log.debug("audioStartTime<=sessionStartTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime");
	    String newAudioPath = PlaybackUtil.getUnique();
	    // cmd =
	    // "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime-audioStartTime)+" -t "+PlaybackUtil.secondsToHours(audioEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
	    // +audioPath.replace(".wav", newAudioPath+".mp3");
	    cmd = " -i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(audioEndTime - sessionStartTime)
		    + " -ar 44100 -ab 64k " + audioPath.replace(".wav", newAudioPath + ".mp3");
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath + ".mp3"));
	    ad.setStartTime(new Date(sessionStartTime));
	    ad.setEndTime(new Date(audioEndTime));
	} else if (audioStartTime <= sessionStartTime && audioEndTime >= sessionEndTime) {
	    log.debug("audioStartTime<=sessionStartTime && audioEndTime>=sessionEndTime");
	    String newAudioPath = PlaybackUtil.getUnique();
	    // cmd =
	    // "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime-audioStartTime)+" -t "+PlaybackUtil.secondsToHours(sessionEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
	    // +audioPath.replace(".wav", newAudioPath+".mp3");
	    cmd = " -i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(sessionEndTime - sessionStartTime)
		    + " -ar 44100 -ab 64k " + audioPath.replace(".wav", newAudioPath + ".mp3");
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath + ".mp3"));
	    ad.setStartTime(new Date(sessionStartTime));
	    ad.setEndTime(new Date(sessionEndTime));
	}
	// audio in b/w the session
	else if (audioStartTime >= sessionStartTime && audioStartTime <= sessionEndTime && audioEndTime <= sessionEndTime && audioEndTime >= sessionStartTime) {
	    log.debug("audioStartTime>=sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime");
	    String newAudioPath = PlaybackUtil.getUnique();
	    // cmd =
	    // "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(audioEndTime-audioStartTime)+"  -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
	    // +audioPath.replace(".wav", newAudioPath+".mp3");
	    cmd = " -i " + audioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(audioEndTime - audioStartTime) + " -ar 44100 -ab 64k " + audioPath.replace(".wav", newAudioPath + ".mp3");
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath + ".mp3"));
	    ad.setStartTime(new Date(audioStartTime));
	    ad.setEndTime(new Date(audioEndTime));
	}
	// starts in b/w and ends after
	else if (audioStartTime >= sessionStartTime && audioStartTime <= sessionEndTime && audioEndTime >= sessionEndTime) {
	    log.debug("audioStartTime >= sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime >= sessionEndTime");
	    String newAudioPath = PlaybackUtil.getUnique();
	    // cmd =
	    // "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(sessionEndTime-audioStartTime)+"  -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
	    // +audioPath.replace(".wav", newAudioPath+".mp3");
	    cmd = " -i " + audioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(sessionEndTime - audioStartTime) + " -ar 44100 -ab 64k " + audioPath.replace(".wav", newAudioPath + ".mp3");
	    PlaybackUtil.invokeFfmpegProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath + ".mp3"));
	    ad.setStartTime(new Date(audioStartTime));
	    ad.setEndTime(new Date(sessionEndTime));
	}
	// log.debug("audioData"+j+"::"+ad);
	// log.debug("audioData: Start Time:: "+ad.getStartTime());
	// log.debug("audioData: End Time:: "+ad.getEndTime());
	if (ad != null) {
	    log.debug("audioData: File Path:: " + ad.getFilePath());
	    sb.getAudioDataList().add(ad);
	} else {
	    log.warn("no audio");
	    // log.debug("audioData: File Path:: " + ad.getFilePath());
	    // sb.getAudioDataList().add(ad);
	}
	log.debug("Prepared audio " + j + " for session bucket!");
    }

    @SuppressWarnings("unused")
    private static ArrayList<String> mapAudioToVideoStartBetween(List<AudioData> audioList, int j, List<VideoData> videos, long sessionStartTime, long sessionEndTime) {
	log.debug("--------------------------------------------");
	log.debug("mappingAudioToVideoStartBetween.....audio +" + j);
	log.debug("--------------------------------------------");

	String audioPath = audioList.get(j).getFilePath();
	long audioStartTime = audioList.get(j).getStartTime().getTime();
	long audioEndTime = audioList.get(j).getEndTime().getTime();
	long nextAudioStartTime = 0, nextAudioEndTime = 0;
	int videoStartIndex = 0, videoStopIndex = 0;
	long videoStartTime = 0, videoEndTime = 0;
	String cmd = null, videoPath = null;
	ArrayList<String> finalVideoPlaylist = new ArrayList<String>();
	log.debug("audioStartStime::" + audioList.get(j).getStartTime());
	log.debug("audioEndStime::" + audioList.get(j).getEndTime());
	log.debug("audioFilePath::" + audioList.get(j).getFilePath());

	// for the current audio i.e j, determine the next audio start time (if
	// applicable)
	if (audioList.size() > (j + 1)) {// &&
					 // audioList.get(j+1).getStartTime().getTime()
					 // <=
					 // videos.get(j).getStartTime().getTime()
	    nextAudioStartTime = audioList.get(j + 1).getStartTime().getTime();
	    log.debug("nextAudioStartTime " + nextAudioStartTime);
	    nextAudioEndTime = audioList.get(j + 1).getEndTime().getTime();
	    log.debug("nextAudioEndTime " + nextAudioEndTime);
	}

	/*
	 * loop through the sessionBucket video list determine videos
	 * (videoIndex) where audio starts and ends
	 */
	for (int k = 0; k < videos.size(); k++) {
	    videoStartTime = videos.get(k).getStartTime().getTime();
	    videoEndTime = videos.get(k).getEndTime().getTime();
	    if (audioStartTime >= videoStartTime && audioStartTime <= videoEndTime) {
		videoStartIndex = k;
	    }
	    if (audioEndTime >= videoStartTime && audioEndTime <= videoEndTime) {
		videoStopIndex = k;
	    }
	}
	log.debug("videoStartIndex:::" + videoStartIndex);
	log.debug("videoStopIndex:::" + videoStopIndex);

	/*
	 * if audio lies entirely within a video (check if audio starts after
	 * the video starts, merge them, update video filepath in playlist
	 */
	if (videoStartIndex == videoStopIndex) {
	    log.debug("videoStartIndex==videoStopIndex");
	    String newVideoPath = null;
	    // if audio starts after video starts
	    if (audioStartTime > videoStartTime) {
		log.debug("audioStartTime>videoStartTime");
		// check if its the first audio.
		if (j == 0) {
		    // cut video till audio starts and add to playlist
		    log.debug("if(j==0)");
		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " -ar 44100 -ab 64k "
			    + videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".avi##" + (audioStartTime - videoStartTime)));
		    // audioList.get(j+1).getStartTime()!=null
		    // if more than one audio then cut/merge the video till the
		    // next audio start time
		    long duration = 0;
		    if (nextAudioStartTime != 0) {
			log.debug("if(nextAudioStartTime!=0)");
			newVideoPath = PlaybackUtil.getUnique();
			duration = nextAudioStartTime - audioStartTime;
			log.debug(":::::DURATION:::::" + duration);
			cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " -t "
				+ PlaybackUtil.secondsToHours(nextAudioStartTime - audioStartTime) + " "
				+ videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeFfmpegProcess(cmd);
		    }
		    // cut/merge the video till current video ends
		    else {
			log.debug("if(nextAudioStartTime==0)");
			newVideoPath = PlaybackUtil.getUnique();
			duration = videos.get(videoStartIndex).getEndTime().getTime() - videos.get(videoStartIndex).getStartTime().getTime();
			log.debug(":::::DURATION:::::" + duration);
			cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " "
				+ videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeFfmpegProcess(cmd);
		    }
		    // add merged video to playlist
		    log.debug("merging..");
		    String newVideoPath1 = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi") + " -i " + audioPath + " -ar 44100 -ab 64k "
			    + videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".avi##" + duration));
		    // finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv",
		    // "playlist"+newVideoPath+".flv"));
		}
		// if not the first audio..
		else if (j > 0) {
		    log.debug("else if(j>0)");
		    long duration = 0;
		    // cut video from current audio till next audio starts
		    if (nextAudioStartTime != 0) {
			log.debug("if(nextAudioStartTime!=0)");
			newVideoPath = PlaybackUtil.getUnique();
			duration = nextAudioStartTime - audioStartTime;
			log.debug(":::::DURATION:::::" + duration);
			cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " -t "
				+ PlaybackUtil.secondsToHours(nextAudioStartTime - audioStartTime) + " "
				+ videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeFfmpegProcess(cmd);
		    }
		    // cut video from current audio till the end of video
		    else {
			log.debug("if(nextAudioStartTime==0)");
			newVideoPath = PlaybackUtil.getUnique();
			duration = videoEndTime - audioStartTime;
			log.debug(":::::DURATION:::::" + duration);
			cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " -t "
				+ PlaybackUtil.secondsToHours(videoEndTime - audioStartTime) + " " + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeFfmpegProcess(cmd);
		    }
		    // add merged video to playlist
		    log.debug("merging...");
		    String newVideoPath1 = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi") + " -i " + audioPath + " -ar 44100 -ab 64k "
			    + videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".avi##" + duration));
		    // finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv",
		    // "playlist"+newVideoPath+".flv"));
		}
	    }
	    // audio and video start at the same time
	    else if (audioStartTime == videoStartTime) {
		long duration = 0;
		log.debug("else if(audioStartTime==videoStartTime)");
		// cut video from current audio till next audio starts
		if (nextAudioStartTime != 0) {
		    log.debug("if(nextAudioStartTime!=0)");
		    String intVideoPath = PlaybackUtil.getUnique();
		    duration = nextAudioStartTime - audioStartTime;
		    log.debug(":::::DURATION:::::" + duration);
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " -t "
			    + PlaybackUtil.secondsToHours(nextAudioStartTime - audioStartTime) + " " + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + intVideoPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    log.debug(cmd);

		    log.debug("merging...");
		    // finalVideoPlaylist.set(videoStartIndex,
		    // videos.get(videoStartIndex).getFilePath().replace(".avi",
		    // "playlist"+newVideoPath+".avi"));
		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + intVideoPath + ".avi") + " -i " + audioPath + " -ar 44100 -ab 64k "
			    + videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    log.debug(cmd);
		    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".avi##" + duration));
		}
		// cut video from current audio till the end of video
		else {
		    log.debug("if(nextAudioStartTime==0)");
		    newVideoPath = PlaybackUtil.getUnique();
		    duration = videoEndTime - videoStartTime;
		    log.debug(":::::DURATION:::::" + duration);
		    // cmd =
		    // "-i "+videos.get(videoStartIndex).getFilePath()+" -ss "+PlaybackUtil.secondsToHours(audioList.get(j).getStartTime().getTime()
		    // - videoStartTime)+" "
		    // +videos.get(videoStartIndex).getFilePath().replace(".avi",
		    // "int_play"+newVideoPath+".avi");
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -i " + audioPath + " -ar 44100 -ab 64k "
			    + videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    log.debug(cmd);
		    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".avi##" + duration));
		}
	    }
	} else {
	    log.debug("videoStartIndex != videoStopIndex");
	    for (int l = videoStartIndex; l <= videoStopIndex; l++) {
		log.debug("videoIndex:::" + l);
		videoStartTime = videos.get(l).getStartTime().getTime();
		videoEndTime = videos.get(l).getEndTime().getTime();
		videoPath = videos.get(l).getFilePath();
		log.debug("videoStartTime::" + videos.get(l).getStartTime());
		log.debug("videoEndTime::" + videos.get(l).getEndTime());
		log.debug("videoPath::" + videoPath);

		String newVideoPath = null;
		long duration = 0;
		// audio starts in b/w and ends after
		if (audioStartTime >= videoStartTime && audioStartTime <= videoEndTime && audioEndTime > videoEndTime) {
		    log.debug("audio starts in b/w and ends after");
		    if (audioStartTime == videoStartTime) {
			log.debug("audioStartTime == videoStartTime");
			newVideoPath = PlaybackUtil.getUnique();
			duration = videoEndTime - videoStartTime;
			log.debug(":::::DURATION:::::" + duration);
			cmd = "-i " + videoPath + " -i " + audioPath + " -ar 44100 -ab 64k " + videoPath.replace(".avi", "playlist" + newVideoPath + ".avi");
			PlaybackUtil.invokeFfmpegProcess(cmd);
			log.debug(cmd);
			finalVideoPlaylist.add(videoPath.replace(".avi", "playlist" + newVideoPath + ".avi##" + duration));
		    } else if (audioStartTime > videoStartTime) {
			log.debug("audioStartTime > videoStartTime");
			// cut video and and audio and merge with video
			newVideoPath = PlaybackUtil.getUnique();
			duration = audioStartTime - videoStartTime;
			log.debug(":::::DURATION:::::" + duration);
			cmd = "-i " + videoPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " "
				+ videoPath.replace(".avi", "playlist" + newVideoPath + ".avi");
			PlaybackUtil.invokeFfmpegProcess(cmd);
			log.debug(cmd);
			finalVideoPlaylist.add(videoPath.replace(".avi", "playlist" + newVideoPath + ".avi##" + duration));

			newVideoPath = PlaybackUtil.getUnique();
			duration = videoEndTime - videoStartTime;
			log.debug(":::::DURATION:::::" + duration);
			cmd = "-i " + videoPath + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " " + videoPath.replace(".avi", "init_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeFfmpegProcess(cmd);
			log.debug(cmd);
			// finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi",
			// "playlist"+newVideoPath+".avi"));

			String newVideoPath1 = PlaybackUtil.getUnique();
			cmd = "-i " + videoPath.replace(".avi", "init_play" + newVideoPath + ".avi") + " -i " + audioPath + " -ar 44100 -ab 64k "
				+ videoPath.replace(".avi", "playlist" + newVideoPath1 + ".avi");
			PlaybackUtil.invokeFfmpegProcess(cmd);
			log.debug(cmd);
			finalVideoPlaylist.add(videoPath.replace(".avi", "playlist" + newVideoPath1 + ".avi##" + duration));
			//
			// String newAudioPath = PlaybackUtil.getUnique();
			// cmd =
			// "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(videoEndTime-audioStartTime)+" "+audioPath.replace(".mp3",
			// newAudioPath+".mp3");
			// PlaybackUtil.invokeFfmpegProcess(cmd);
			//
			// String newVideoPath = PlaybackUtil.getUnique();
			// cmd =
			// "-i "+videoPath+" -i "+audioPath.replace(".mp3",
			// newAudioPath+".mp3")+" -ar 22050 "+videoPath.replace(".flv","playlist"+newVideoPath+".flv");
			// PlaybackUtil.invokeFfmpegProcess(cmd);
			// finalVideoPlaylist.add(videoPath.replace(".flv","playlist"+newVideoPath+".flv"));
		    }
		    // cut audio and merge with video
		    String newAudioPath = PlaybackUtil.getUnique();
		    duration = videoEndTime - audioStartTime;
		    log.debug(":::::DURATION:::::" + duration);
		    cmd = "-i " + audioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(videoEndTime - audioStartTime) + " " + audioPath.replace(".avi", "init_play" + newAudioPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    log.debug(cmd);

		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videoPath + " -i " + audioPath.replace(".avi", "init_play" + newAudioPath + ".avi") + " -ar 44100 -ab 64k "
			    + videoPath.replace(".avi", "playlist" + newVideoPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    log.debug(cmd);
		    finalVideoPlaylist.add(videoPath.replace(".avi", "playlist" + newVideoPath + ".avi##" + duration));
		} else if (audioStartTime < videoStartTime && audioEndTime > videoEndTime) {
		    log.debug("audioStartTime < videoStartTime && audioEndTime > videoEndTime");
		    // cut audio and merge with video
		    String newAudioPath = PlaybackUtil.getUnique();
		    duration = videoEndTime - videoStartTime;
		    log.debug(":::::DURATION:::::" + duration);
		    cmd = "-i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(videoStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(videoEndTime - videoStartTime) + " "
			    + audioPath.replace(".avi", newAudioPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    log.debug(cmd);

		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videoPath + " -i " + audioPath.replace(".avi", newAudioPath + ".avi") + " -ar 44100 -ab 64k " + videoPath.replace(".avi", "playlist" + newVideoPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    log.debug(cmd);
		    finalVideoPlaylist.add(videoPath.replace(".avi", "playlist" + newVideoPath + ".avi##" + duration));
		} else if (audioStartTime < videoStartTime && audioEndTime >= videoStartTime && audioEndTime <= videoEndTime) {
		    log.debug("audioStartTime < videoStartTime && audioEndTime >= videoStartTime && audioEndTime <= videoEndTime");
		    // cut audio and merge with video
		    String newAudioPath = PlaybackUtil.getUnique();
		    duration = audioEndTime - videoStartTime;
		    log.debug(":::::DURATION:::::" + duration);
		    cmd = "-i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(videoStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(audioEndTime - videoStartTime) + " "
			    + audioPath.replace(".avi", newAudioPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    log.debug(cmd);
		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videoPath + " -i " + audioPath.replace(".avi", newAudioPath + ".avi") + " -ar 44100 -ab 64k " + videoPath.replace(".avi", "playlist" + newVideoPath + ".avi");
		    PlaybackUtil.invokeFfmpegProcess(cmd);
		    log.debug(cmd);
		    finalVideoPlaylist.add(videoPath.replace(".avi", "playlist" + newVideoPath + ".avi##" + duration));
		}
	    }
	}
	return finalVideoPlaylist;
    }

    private static void updateFinalVideoTable(PlayBackPlayList flowPlayerVideo, PlayBackPlayListDao playBackPlayListDao) {
	log.debug("Inside updateFinalVideoTable ..............");
	// ClassPathXmlApplicationContext appContext = new
	// ClassPathXmlApplicationContext(new String[] { "root-context.xml" });
	// of course, an ApplicationContext is just a BeanFactory
	// BeanFactory factory = (BeanFactory) appContext;

	// PlayBackPlayListDao sessionRecordingsDao = (PlayBackPlayListDao)
	// factory.getBean("playBackPlayListDao");
	playBackPlayListDao.savePlayBackPlayList(flowPlayerVideo);
    }
}