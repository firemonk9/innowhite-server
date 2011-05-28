package com.innowhite.PlaybackApp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import com.innowhite.PlaybackApp.util.ProcessExecutor;
import com.sun.org.apache.bcel.internal.generic.ALOAD;

public class PlaybackDataService {

	static BeanFactory factory = null;
	static ArrayList<String> finalVideoPlaylist = new ArrayList<String>();
	
	public static void loadInit() {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "app-context.xml" });
		// of course, an ApplicationContext is just a BeanFactory
		factory = (BeanFactory) appContext;

	}

	public static void main(String[] args) {
	// TODO Auto-generated method stub
	ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "app-context.xml" });
	// of course, an ApplicationContext is just a BeanFactory
	BeanFactory factory = (BeanFactory) appContext;

	String roomId = "123123";

	SessionRecordingDao sessionRecordingsDao = (SessionRecordingDao) factory.getBean("sessionRecordingsDao");
	List<SessionRecordings> sessionRecordingsList = sessionRecordingsDao.getSessionRecordingList(roomId);
	
	AudioDataDao audioDataDao = (AudioDataDao) factory.getBean("audioDataDao");
	List<AudioData> audioDataList = audioDataDao.getAudioDataList(roomId);

	VideoDataDao videoDataDao = (VideoDataDao) factory.getBean("videoDataDao");
	List<VideoData> videoDataList = videoDataDao.getVideoDataList(roomId);

	PlaybackUtil.updatePathWindows("C:/Users/sony/Desktop/INNOWHITE/playback/myPlayback/", audioDataList, videoDataList);
	
	// WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)
	// factory.getBean("whiteboardDataDao");
	// whiteboardDataDao.getWhiteboardDTOForRoom("room1");
	
	//HashMap contains Key-Value pairs of SessionRecording-SessionBucket
	HashMap<SessionRecordings, SessionBucket> hm = new HashMap<SessionRecordings, SessionBucket>();
	
	for(int i =0; i< sessionRecordingsList.size(); i++){
		
		long sessionStartTime = sessionRecordingsList.get(i).getStartTime().getTime();
		long sessionEndTime = sessionRecordingsList.get(i).getEndTime().getTime();
		System.out.println("session Start Time::"+sessionRecordingsList.get(i).getStartTime());
		System.out.println("session End Time::"+sessionRecordingsList.get(i).getEndTime());
		
		/*sessionBucket for each Session
		 * contains audios, videos in the session
		 * audios & videos are trimmed and audio format is changed to "same-name".mp3
		 */
		SessionBucket sb = new SessionBucket();
		for(int j=0; j<audioDataList.size(); j++){
			prepareAudioForSessionBucket(sb, j, audioDataList.get(j), sessionStartTime, sessionEndTime);
		}
		for(int j=0; j<videoDataList.size(); j++){
			prepareVideoForSessionBucket(sb, j, videoDataList.get(j), sessionStartTime, sessionEndTime);
		}
		System.out.println("SessionBucket.AudioData :: "+sb.audioDataList);
		System.out.println("SessionBucket.VideoData :: "+sb.videoDataList);
		hm.put(sessionRecordingsList.get(i), sb);
	}
	//iterate through the Session Buckets .. Set of keys(sessions) 
	Iterator itr = hm.keySet().iterator();
	String cmd = null;
	while(itr.hasNext()){
		SessionRecordings sessions = (SessionRecordings) itr.next();
		long sessionStartTime = sessions.getStartTime().getTime();
		long sessionEndTime = sessions.getEndTime().getTime();;

		SessionBucket sessionBucket = hm.get(sessions);
		List<AudioData> audios = sessionBucket.getAudioDataList();
		List<VideoData> videos = sessionBucket.getVideoDataList();
		
		for(int i=0; i < videos.size(); i++){
			finalVideoPlaylist.add(videos.get(i).getFilePath());
		}
		
		System.out.println("key::"+sessions);
		System.out.println("value::"+hm.get(sessionBucket));
		
		for(int j=0; j<audios.size(); j++){
			mapAudioToVideoStartBetween(audios.get(j), videos, sessionStartTime, sessionEndTime);
		}
		PlayBackPlayList playlist = null;
		List<PlayBackPlayList> listPlayback = new ArrayList<PlayBackPlayList>();
		for(int i=0; i<finalVideoPlaylist.size();i++){
			playlist = new PlayBackPlayList();
			playlist.setFilePath(finalVideoPlaylist.get(i));
			playlist.setInsertedDate(new Date());
			playlist.setRoomName(roomId);
		}
		updateFinalVideoTable(listPlayback);
	}
    }

	private static void prepareVideoForSessionBucket(SessionBucket sb, int j, VideoData videoData,long sessionStartTime, long sessionEndTime) {
		long videoStartTime = videoData.getStartTime().getTime();
		long videoEndTime = videoData.getStartTime().getTime();
		String videoPath = videoData.getFilePath();
		String cmd = null;
		VideoData vd = null;
		if(videoStartTime <= sessionStartTime &&  videoEndTime <= sessionEndTime){
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+videoPath+" -ss "+sessionStartTime+" -t "+ videoEndTime + " " +videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(sessionStartTime));
			vd.setEndTime(new Date(videoEndTime));
		}
		else if(videoStartTime <= sessionStartTime && videoEndTime >= sessionEndTime){
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+videoPath+" -ss "+sessionStartTime+" -t "+ sessionEndTime + " " +videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(sessionStartTime));
			vd.setEndTime(new Date(sessionEndTime));
		}
		else if(videoStartTime >= sessionStartTime &&  videoEndTime <= sessionEndTime){
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+videoPath+" -ss "+videoStartTime+" -t "+ videoEndTime + " " +videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(videoStartTime));
			vd.setEndTime(new Date(videoEndTime));
		}
		else if(videoStartTime >= sessionStartTime && videoEndTime >= sessionEndTime){
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+videoPath+" -ss "+videoStartTime+" -t "+ sessionEndTime + " " +videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(videoStartTime));
			vd.setEndTime(new Date(sessionEndTime));
		}
		System.out.println("videoData"+j+"::"+vd);
		System.out.println("videoData: Start Time:: "+vd.getStartTime());
		System.out.println("videoData: End Time:: "+vd.getEndTime());
		System.out.println("videoData: File Path:: "+vd.getFilePath());
		sb.getVideoDataList().add(vd);
	}

	private static void prepareAudioForSessionBucket(SessionBucket sb, int j, AudioData audioData,long sessionStartTime, long sessionEndTime) {
		long audioStartTime = audioData.getStartTime().getTime();
		long audioEndTime = audioData.getStartTime().getTime();
		String audioPath = audioData.getFilePath();
		String cmd = null;
		AudioData ad = null;
		if(audioStartTime <= sessionStartTime &&  audioEndTime <= sessionEndTime){
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+audioPath+" -ss "+sessionStartTime+" -t "+ audioEndTime + "  -ab 32k -acodec libmp3lame " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(sessionStartTime));
			ad.setEndTime(new Date(audioEndTime));
		}
		else if(audioStartTime <= sessionStartTime && audioEndTime >= sessionEndTime){
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+audioPath+" -ss "+sessionStartTime+" -t "+ sessionEndTime + "  -ab 32k -acodec libmp3lame " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(sessionStartTime));
			ad.setEndTime(new Date(sessionEndTime));
		}
		else if(audioStartTime >= sessionStartTime &&  audioEndTime <= sessionEndTime){
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+audioPath+" -ss "+audioStartTime+" -t "+ audioEndTime + "  -ab 32k -acodec libmp3lame " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(audioStartTime));
			ad.setEndTime(new Date(audioEndTime));
		}
		else if(audioStartTime >= sessionStartTime && audioEndTime >= sessionEndTime){
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+audioPath+" -ss "+audioStartTime+" -t "+ sessionEndTime + "  -ab 32k -acodec libmp3lame " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(audioStartTime));
			ad.setEndTime(new Date(sessionEndTime));
		}
		System.out.println("audioData"+j+"::"+ad);
		System.out.println("audioData: Start Time:: "+ad.getStartTime());
		System.out.println("audioData: End Time:: "+ad.getEndTime());
		System.out.println("audioData: File Path:: "+ad.getFilePath());
		sb.getAudioDataList().add(ad);
	}

	/* 
	 *invokes process executor class and executes the ffmpeg cmd
	 */

	private static void invokeProcess(String cmd) {
		ProcessExecutor pe = new ProcessExecutor();
		System.out.println(pe.executeProcess(cmd));
	}

	private static void mapAudioToVideoStartBetween(AudioData audioData,List<VideoData> videos, long sessionStartTime, long sessionEndTime) {
		String cmd = null;
		String audioPath = audioData.getFilePath();
		long audioStartTime = audioData.getStartTime().getTime();
		long audioEndTime = audioData.getEndTime().getTime();
		
		int videoStartIndex=0,videoStopIndex=0;
		long videoStartTime=0, videoEndTime=0;
		String videoPath = null;
		
		/*
		 * loop through the sessionBucket video list
		 * determine videos (videoIndex) where audio starts and ends
		 */
		for(int k=0; k<videos.size(); k++){
			videoStartTime = videos.get(k).getStartTime().getTime();
			videoEndTime = videos.get(k).getEndTime().getTime();
			if(audioStartTime>=videoStartTime && audioStartTime<=videoEndTime){
				videoStartIndex = k;
			}
			if(audioEndTime>=videoStartTime && audioEndTime<=videoEndTime){
				videoStopIndex = k;
			}
		}	
		/*
		 * audio lies entirely within a video
		 * merge them
		 * update video filepath in playlist
		 */
		if(videoStartIndex==videoStopIndex){
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+videos.get(videoStartIndex).getFilePath()+" -i "+audioPath+" -ar 11025 "+ videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv");
			invokeProcess(cmd);
			finalVideoPlaylist.set(videoStartIndex, videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
		}
		else{
			for(int l=videoStartIndex; l<=videoStopIndex; l++){
				if(audioStartTime>=videoStartTime && audioStartTime<=videoEndTime){
					//cut audio and merge with video
					String newAudioPath = PlaybackUtil.getUnique();
					cmd = "ffmpeg.exe -i "+audioPath+" -ss "+audioStartTime+" -t "+videos.get(l).getEndTime()+" "+audioPath.replace(".mp3", newAudioPath+".mp3");
					invokeProcess(cmd);
					String newVideoPath = PlaybackUtil.getUnique();
					cmd = "ffmpeg.exe -i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3", newAudioPath+".mp3")+" -ar 11025 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
					invokeProcess(cmd);
					finalVideoPlaylist.set(l, videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv"));
				}
				else if(audioStartTime>=videoStartTime && audioStartTime<=videoEndTime){
					//cut audio and merge with video
					String newAudioPath = PlaybackUtil.getUnique();
					cmd = "ffmpeg.exe -i "+audioPath+" -ss "+videos.get(l).getStartTime()+" -t "+audioEndTime+" "+audioPath.replace(".mp3", newAudioPath+".mp3");
					invokeProcess(cmd);
					String newVideoPath = PlaybackUtil.getUnique();
					cmd = "ffmpeg.exe -i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3", newAudioPath+".mp3")+" -ar 11025 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
					invokeProcess(cmd);
					finalVideoPlaylist.set(l, videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv"));
				}
			}
		}
	}

	private static void updateFinalVideoTable(List<PlayBackPlayList> list ){
	      ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "app-context.xml" });
	  // of course, an ApplicationContext is just a BeanFactory
	  BeanFactory factory = (BeanFactory) appContext;

	  PlayBackPlayListDao sessionRecordingsDao = (PlayBackPlayListDao) factory.getBean("playBackPlayListDao");
	  sessionRecordingsDao.savePlayBackPlayList(list);
	     
	 }
}
