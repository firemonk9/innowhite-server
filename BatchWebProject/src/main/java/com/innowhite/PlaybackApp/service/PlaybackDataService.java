package com.innowhite.PlaybackApp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

public class PlaybackDataService {

    static BeanFactory factory = null;
    static ArrayList<String> finalVideoPlaylist = new ArrayList<String>();

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
    private VideoDataDao videoDataDao ;
    private SessionRecordingDao sessionRecordingsDao;
    private PlaybackVO playbackVO = null;
    private PlayBackPlayListDao playBackPlayListDao=null;
    
    
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

  
   

    public static void loadInit() {
	ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "root-context.xml" });
	// of course, an ApplicationContext is just a BeanFactory
	factory = (BeanFactory) appContext;

    }

    public  void process(String roomId) {
	// TODO Auto-generated method stub
	//ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "root-context.xml" });
	// of course, an ApplicationContext is just a BeanFactory
	//BeanFactory factory = (BeanFactory) appContext;

	//String roomId = "Dhiraj4";
//	if (args != null ) {
//	    roomId = args;
//	}

	//SessionRecordingDao sessionRecordingsDao = (SessionRecordingDao) factory.getBean("sessionRecordingsDao");
	List<SessionRecordings> sessionRecordingsList = sessionRecordingsDao.getSessionRecordingList(roomId);

	//AudioDataDao audioDataDao = (AudioDataDao) factory.getBean("audioDataDao");
	List<AudioData> audioDataList = audioDataDao.getAudioDataList(roomId);

	//VideoDataDao videoDataDao = (VideoDataDao) factory.getBean("videoDataDao");
	List<VideoData> videoDataList = videoDataDao.getVideoDataList(roomId);

	//PlayBackPlayListDao playBackPlayListDao = (PlayBackPlayListDao) factory.getBean("playBackPlayListDao");
	// List<VideoData> videoDataList =
	// sessionRecordingsDao.getVideoDataList(roomId);

	//playbackVO = (PlaybackVO) factory.getBean("playBackVO");
	PlaybackUtil.setPlaybackVO(playbackVO);

	// replace unix file path to windows file path.

	if (PlaybackUtil.isWindows()) {
	    log.debug(" windows machine so changing the page ..");
	    PlaybackUtil.updatePathWindows("C:/Users/sony/Desktop/INNOWHITE/playback/video_playlist", audioDataList, videoDataList);
	}
	// WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)
	// factory.getBean("whiteboardDataDao");
	// whiteboardDataDao.getWhiteboardDTOForRoom("room1");

	// HashMap contains Key-Value pairs of SessionRecording-SessionBucket
	HashMap<SessionRecordings, SessionBucket> hm = new HashMap<SessionRecordings, SessionBucket>();

	for (int i = 0; i < sessionRecordingsList.size(); i++) {
	    long sessionStartTime = sessionRecordingsList.get(i).getStartTime().getTime();
	    long sessionEndTime = sessionRecordingsList.get(i).getEndTime().getTime();
	    log.debug("session Start Time::" + sessionRecordingsList.get(i).getStartTime());
	    log.debug("session End Time::" + sessionRecordingsList.get(i).getEndTime());

	    /*
	     * sessionBucket for each Session contains audios, videos in the
	     * session audios & videos are trimmed and audio format is changed
	     * to "same-name".mp3
	     */
	    SessionBucket sb = new SessionBucket();
	    for (int j = 0; j < audioDataList.size(); j++) {
		prepareAudioForSessionBucket(sb, j, audioDataList.get(j), sessionStartTime, sessionEndTime);
	    }
	    for (int j = 0; j < videoDataList.size(); j++) {
		prepareVideoForSessionBucket(sb, j, videoDataList.get(j), sessionStartTime, sessionEndTime);
	    }
	    log.debug("SessionBucket.AudioData :: " + sb.audioDataList);
	    log.debug("SessionBucket.VideoData :: " + sb.videoDataList);
	    hm.put(sessionRecordingsList.get(i), sb);
	    log.debug("done preparing session buckets...");
	}
	log.debug("preparing session buckets done!");
	log.debug("--------------------------------------------------------------");
	// iterate through the Session Buckets .. Set of keys(sessions)
	Iterator itr = hm.keySet().iterator();
	String cmd = null;
	while (itr.hasNext()) {
	    log.debug("for each session...");
	    SessionRecordings sessions = (SessionRecordings) itr.next();
	    long sessionStartTime = sessions.getStartTime().getTime();
	    long sessionEndTime = sessions.getEndTime().getTime();
	    ;
	    log.debug("Session Start Time::" + sessions.getStartTime());
	    log.debug("Session End Time::" + sessions.getEndTime());

	    SessionBucket sessionBucket = hm.get(sessions);
	    List<AudioData> audios = sessionBucket.getAudioDataList();
	    List<VideoData> videos = sessionBucket.getVideoDataList();

	    // for(int i=0; i < videos.size(); i++){
	    // finalVideoPlaylist.add(videos.get(i).getFilePath());
	    // log.debug("finalPlayList"+i+"::"+finalVideoPlaylist.get(i));
	    // }

	    log.debug("key::" + sessions);
	    log.debug("value::" + sessionBucket);

	    for (int j = 0; j < audios.size(); j++) {
		mapAudioToVideoStartBetween(audios, j, videos, sessionStartTime, sessionEndTime);
	    }
	    for (int j = 0; j < videos.size(); j++) {
		// mapAudioToVideoStartBetween(audios, j, videos,
		// sessionStartTime, sessionEndTime);
		log.debug(videos.get(j).getFilePath());
	    }

	    PlayBackPlayList playlist = null;
	    List<PlayBackPlayList> listPlayback = new ArrayList<PlayBackPlayList>();
	    for (int i = 0; i < finalVideoPlaylist.size(); i++) {
		playlist = new PlayBackPlayList();
		playlist.setFilePath(finalVideoPlaylist.get(i));
		playlist.setInsertedDate(new Date());
		playlist.setRoomName(roomId);
		
		listPlayback.add(playlist);
	    }
	    updateFinalVideoTable(listPlayback,  playBackPlayListDao);
	}
    }

    private static void prepareVideoForSessionBucket(SessionBucket sb, int j, VideoData videoData, long sessionStartTime, long sessionEndTime) {
	log.debug("------------------------------------");
	log.debug("preparing VideoForSessionBucket.....");
	log.debug("------------------------------------");

	if (videoData.getStartTime() != null && videoData.getEndTime() != null) {
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
		cmd = "-i " + videoPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - videoStartTime) + " -an -t " + PlaybackUtil.secondsToHours(videoEndTime - sessionStartTime) + " "
			+ videoPath.replace(".flv", newVideoPath + ".avi");
		PlaybackUtil.invokeProcess(cmd);
		vd = new VideoData();
		vd.setFilePath(videoPath.replace(".flv", newVideoPath + ".avi"));
		vd.setStartTime(new Date(sessionStartTime));
		vd.setEndTime(new Date(videoEndTime));
	    } else if (videoStartTime <= sessionStartTime && videoEndTime >= sessionEndTime) {
		log.debug("videoStartTime<=sessionStartTime && videoEndTime>=sessionEndTime");
		String newVideoPath = PlaybackUtil.getUnique();
		cmd = "-i " + videoPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - videoStartTime) + " -an -t " + PlaybackUtil.secondsToHours(sessionEndTime - sessionStartTime) + " "
			+ videoPath.replace(".flv", newVideoPath + ".avi");
		PlaybackUtil.invokeProcess(cmd);
		vd = new VideoData();
		vd.setFilePath(videoPath.replace(".flv", newVideoPath + ".avi"));
		vd.setStartTime(new Date(sessionStartTime));
		vd.setEndTime(new Date(sessionEndTime));
	    } else if (videoStartTime >= sessionStartTime && videoStartTime <= sessionEndTime && videoEndTime <= sessionEndTime && videoEndTime >= sessionStartTime) {
		log.debug("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime");
		String newVideoPath = PlaybackUtil.getUnique();
		cmd = "-i " + videoPath + " -ss 00:00:00 -an -t " + PlaybackUtil.secondsToHours(videoEndTime - videoStartTime) + " " + videoPath.replace(".flv", newVideoPath + ".avi");
		PlaybackUtil.invokeProcess(cmd);
		vd = new VideoData();
		vd.setFilePath(videoPath.replace(".flv", newVideoPath + ".avi"));
		vd.setStartTime(new Date(videoStartTime));
		vd.setEndTime(new Date(videoEndTime));
	    } else if (videoStartTime >= sessionStartTime && videoStartTime <= sessionEndTime && videoEndTime >= sessionEndTime) {
		log.debug("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime>=sessionEndTime");
		String newVideoPath = PlaybackUtil.getUnique();
		cmd = "-i " + videoPath + " -ss 00:00:00 -an -t " + PlaybackUtil.secondsToHours(sessionEndTime - videoStartTime) + " " + videoPath.replace(".flv", newVideoPath + ".avi");
		PlaybackUtil.invokeProcess(cmd);
		vd = new VideoData();
		vd.setFilePath(videoPath.replace(".flv", newVideoPath + ".avi"));
		vd.setStartTime(new Date(videoStartTime));
		vd.setEndTime(new Date(sessionEndTime));
	    }
	    log.debug("videoData" + j + "::" + vd);
	    // log.debug("videoData: Start Time:: "+vd.getStartTime());
	    // log.debug("videoData: End Time:: "+vd.getEndTime());
	    log.debug("videoData: File Path:: " + vd.getFilePath());
	    sb.getVideoDataList().add(vd);
	}
    }

    private static void prepareAudioForSessionBucket(SessionBucket sb, int j, AudioData audioData, long sessionStartTime, long sessionEndTime) {
	log.debug("------------------------------------");
	log.debug("preparing AudioForSessionBucket.....");
	log.debug("------------------------------------");
	long audioStartTime = audioData.getStartTime().getTime();
	long audioEndTime = audioData.getEndTime().getTime();
	String audioPath = audioData.getFilePath();
	log.debug("audioStartStime::" + audioStartTime);
	log.debug("audioEndStime::" + audioEndTime);
	log.debug("sessionStartTime::" + sessionStartTime);
	log.debug("sessionEndTime::" + sessionEndTime);
	log.debug("audio Path::" + audioPath);
	String cmd = null;
	AudioData ad = null;
	if (audioStartTime <= sessionStartTime && audioEndTime <= sessionEndTime && audioEndTime >= sessionStartTime) {
	    log.debug("audioStartTime<=sessionStartTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime");
	    String newAudioPath = PlaybackUtil.getUnique();
	    // cmd =
	    // "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime-audioStartTime)+" -t "+PlaybackUtil.secondsToHours(audioEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
	    // +audioPath.replace(".wav", newAudioPath+".mp3");
	    cmd = "-i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(audioEndTime - sessionStartTime)
		    + " -ar 44100 -ab 64k " + audioPath.replace(".wav", newAudioPath + ".avi");
	    PlaybackUtil.invokeProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath + ".avi"));
	    ad.setStartTime(new Date(sessionStartTime));
	    ad.setEndTime(new Date(audioEndTime));
	} else if (audioStartTime <= sessionStartTime && audioEndTime >= sessionEndTime) {
	    log.debug("audioStartTime<=sessionStartTime && audioEndTime>=sessionEndTime");
	    String newAudioPath = PlaybackUtil.getUnique();
	    // cmd =
	    // "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime-audioStartTime)+" -t "+PlaybackUtil.secondsToHours(sessionEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
	    // +audioPath.replace(".wav", newAudioPath+".mp3");
	    cmd = "-i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(sessionEndTime - sessionStartTime)
		    + " -ar 44100 -ab 64k " + audioPath.replace(".wav", newAudioPath + ".avi");
	    PlaybackUtil.invokeProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath + ".avi"));
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
	    cmd = "-i " + audioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(audioEndTime - audioStartTime) + " -ar 44100 -ab 64k " + audioPath.replace(".wav", newAudioPath + ".avi");
	    PlaybackUtil.invokeProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath + ".avi"));
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
	    cmd = "-i " + audioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(sessionEndTime - audioStartTime) + " -ar 44100 -ab 64k " + audioPath.replace(".wav", newAudioPath + ".avi");
	    PlaybackUtil.invokeProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath + ".avi"));
	    ad.setStartTime(new Date(audioStartTime));
	    ad.setEndTime(new Date(sessionEndTime));
	}
	log.debug("audioData" + j + "::" + ad);
	// log.debug("audioData: Start Time:: "+ad.getStartTime());
	// log.debug("audioData: End Time:: "+ad.getEndTime());
	log.debug("audioData: File Path:: " + ad.getFilePath());
	sb.getAudioDataList().add(ad);
    }

    private static void mapAudioToVideoStartBetween(List<AudioData> audioList, int j, List<VideoData> videos, long sessionStartTime, long sessionEndTime) {
	log.debug("--------------------------------------------");
	log.debug("mappingAudioToVideoStartBetween.....audio" + j);
	log.debug("--------------------------------------------");

	String audioPath = audioList.get(j).getFilePath();
	long audioStartTime = audioList.get(j).getStartTime().getTime();
	long audioEndTime = audioList.get(j).getEndTime().getTime();
	long nextAudioStartTime = 0, nextAudioEndTime = 0;
	int videoStartIndex = 0, videoStopIndex = 0;
	long videoStartTime = 0, videoEndTime = 0;
	String cmd = null, videoPath = null;

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
			    + videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4");
		    PlaybackUtil.invokeProcess(cmd);
		    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4"));
		    // audioList.get(j+1).getStartTime()!=null
		    // if more than one audio then cut/merge the video till the
		    // next audio start time
		    if (nextAudioStartTime != 0) {
			log.debug("if(nextAudioStartTime!=0)");
			newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " -t "
				+ PlaybackUtil.secondsToHours(nextAudioStartTime - audioStartTime) + " "
				+ videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeProcess(cmd);
		    }
		    // cut/merge the video till current video ends
		    else {
			log.debug("if(nextAudioStartTime==0)");
			newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " "
				+ videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeProcess(cmd);
		    }
		    // add merged video to playlist
		    log.debug("merging..");
		    String newVideoPath1 = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi") + " -i " + audioPath + " -ar 44100 -ab 64k "
			    + videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".mp4");
		    PlaybackUtil.invokeProcess(cmd);
		    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".mp4"));
		    // finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv",
		    // "playlist"+newVideoPath+".flv"));
		}
		// if not the first audio..
		else if (j > 0) {
		    log.debug("else if(j>0)");
		    // cut video from current audio till next audio starts
		    if (nextAudioStartTime != 0) {
			log.debug("if(nextAudioStartTime!=0)");
			newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " -t "
				+ PlaybackUtil.secondsToHours(nextAudioStartTime - audioStartTime) + " "
				+ videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeProcess(cmd);
		    }
		    // cut video from current audio till the end of video
		    else {
			log.debug("if(nextAudioStartTime==0)");
			newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " -t "
				+ PlaybackUtil.secondsToHours(videoEndTime - audioStartTime) + " " + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeProcess(cmd);
		    }
		    // add merged video to playlist
		    log.debug("merging...");
		    String newVideoPath1 = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi") + " -i " + audioPath + " -ar 44100 -ab 64k "
			    + videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".mp4");
		    PlaybackUtil.invokeProcess(cmd);
		    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".mp4"));
		    // finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv",
		    // "playlist"+newVideoPath+".flv"));
		}
	    } else if (audioStartTime == videoStartTime) {
		log.debug("else if(audioStartTime==videoStartTime)");
		// cut video from current audio till next audio starts
		if (nextAudioStartTime != 0) {
		    log.debug("if(nextAudioStartTime!=0)");
		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " -t "
			    + PlaybackUtil.secondsToHours(nextAudioStartTime - audioStartTime) + " " + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
		    PlaybackUtil.invokeProcess(cmd);
		    log.debug(cmd);
		}
		// cut video from current audio till the end of video
		else {
		    log.debug("if(nextAudioStartTime==0)");
		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioList.get(j).getStartTime().getTime() - videoStartTime) + " "
			    + videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play" + newVideoPath + ".avi");
		    PlaybackUtil.invokeProcess(cmd);
		    log.debug(cmd);
		}
		log.debug("merging...");
		// finalVideoPlaylist.set(videoStartIndex,
		// videos.get(videoStartIndex).getFilePath().replace(".avi",
		// "playlist"+newVideoPath+".avi"));
		newVideoPath = PlaybackUtil.getUnique();
		cmd = "-i " + videos.get(videoStartIndex).getFilePath() + " -i " + audioPath + " -ar 44100 -ab 64k "
			+ videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4");
		PlaybackUtil.invokeProcess(cmd);
		log.debug(cmd);
		finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4"));
	    }
	} else {
	    for (int l = videoStartIndex; l <= videoStopIndex; l++) {
		videoStartTime = videos.get(l).getStartTime().getTime();
		videoEndTime = videos.get(l).getEndTime().getTime();
		String newVideoPath = null;
		if (audioStartTime >= videoStartTime && audioStartTime <= videoEndTime && audioEndTime > videoEndTime) {
		    if (audioStartTime == videoStartTime) {
			newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i " + videos.get(l).getFilePath() + " -i " + audioPath + " -ar 44100 -ab 64k " + videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4");
			PlaybackUtil.invokeProcess(cmd);
			log.debug(cmd);
			finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4"));
		    } else if (audioStartTime > videoStartTime) {
			// cut video and and audio and merge with video
			newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i " + videos.get(l).getFilePath() + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " "
				+ videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4");
			PlaybackUtil.invokeProcess(cmd);
			log.debug(cmd);
			finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4"));

			newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i " + videos.get(l).getFilePath() + " -ss " + PlaybackUtil.secondsToHours(audioStartTime - videoStartTime) + " "
				+ videos.get(l).getFilePath().replace(".avi", "init_play" + newVideoPath + ".avi");
			PlaybackUtil.invokeProcess(cmd);
			log.debug(cmd);
			// finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi",
			// "playlist"+newVideoPath+".avi"));

			String newVideoPath1 = PlaybackUtil.getUnique();
			cmd = "-i " + videos.get(l).getFilePath().replace(".avi", "init_play" + newVideoPath + ".avi") + " -i " + audioPath + " -ar 44100 -ab 64k "
				+ videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".mp4");
			PlaybackUtil.invokeProcess(cmd);
			log.debug(cmd);
			finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath1 + ".mp4"));
			//
			// String newAudioPath = PlaybackUtil.getUnique();
			// cmd =
			// "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(videoEndTime-audioStartTime)+" "+audioPath.replace(".mp3",
			// newAudioPath+".mp3");
			// PlaybackUtil.invokeProcess(cmd);
			//
			// String newVideoPath = PlaybackUtil.getUnique();
			// cmd =
			// "-i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3",
			// newAudioPath+".mp3")+" -ar 22050 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
			// PlaybackUtil.invokeProcess(cmd);
			// finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv"));
		    }
		    // cut audio and merge with video
		    String newAudioPath = PlaybackUtil.getUnique();
		    cmd = "-i " + audioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(videoEndTime - audioStartTime) + " " + audioPath.replace(".avi", "init_play" + newAudioPath + ".avi");
		    PlaybackUtil.invokeProcess(cmd);
		    log.debug(cmd);

		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(l).getFilePath() + " -i " + audioPath.replace(".avi", "init_play" + newAudioPath + ".avi") + " -ar 44100 -ab 64k "
			    + videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4");
		    PlaybackUtil.invokeProcess(cmd);
		    log.debug(cmd);
		    finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4"));
		} else if (audioStartTime < videoStartTime && audioEndTime > videoEndTime) {
		    // cut audio and merge with video
		    String newAudioPath = PlaybackUtil.getUnique();
		    cmd = "-i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(videoStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(videoEndTime - videoStartTime) + " "
			    + audioPath.replace(".avi", newAudioPath + ".avi");
		    PlaybackUtil.invokeProcess(cmd);
		    log.debug(cmd);

		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(l).getFilePath() + " -i " + audioPath.replace(".avi", newAudioPath + ".avi") + " -ar 44100 -ab 64k "
			    + videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4");
		    PlaybackUtil.invokeProcess(cmd);
		    log.debug(cmd);
		    finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4"));
		} else if (audioStartTime < videoStartTime && audioEndTime >= videoStartTime && audioEndTime <= videoEndTime) {
		    // cut audio and merge with video
		    String newAudioPath = PlaybackUtil.getUnique();
		    cmd = "-i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(videoStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(audioEndTime - videoStartTime) + " "
			    + audioPath.replace(".avi", newAudioPath + ".avi");
		    PlaybackUtil.invokeProcess(cmd);
		    log.debug(cmd);
		    newVideoPath = PlaybackUtil.getUnique();
		    cmd = "-i " + videos.get(l).getFilePath() + " -i " + audioPath.replace(".avi", newAudioPath + ".avi") + " -ar 44100 -ab 64k "
			    + videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4");
		    PlaybackUtil.invokeProcess(cmd);
		    log.debug(cmd);
		    finalVideoPlaylist.set(l, videos.get(l).getFilePath().replace(".avi", "playlist" + newVideoPath + ".mp4"));
		}
	    }
	}
    }

    private static void updateFinalVideoTable(List<PlayBackPlayList> list, PlayBackPlayListDao playBackPlayListDao) {
	// ClassPathXmlApplicationContext appContext = new
	// ClassPathXmlApplicationContext(new String[] { "root-context.xml" });
	// of course, an ApplicationContext is just a BeanFactory
	// BeanFactory factory = (BeanFactory) appContext;

	// PlayBackPlayListDao sessionRecordingsDao = (PlayBackPlayListDao)
	// factory.getBean("playBackPlayListDao");
	playBackPlayListDao.savePlayBackPlayList(list);
    }
}
