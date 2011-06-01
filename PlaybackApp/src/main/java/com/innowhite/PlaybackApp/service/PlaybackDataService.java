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

	String roomId = "room200";

	SessionRecordingDao sessionRecordingsDao = (SessionRecordingDao) factory.getBean("sessionRecordingsDao");
	List<SessionRecordings> sessionRecordingsList = sessionRecordingsDao.getSessionRecordingList(roomId);
	
	AudioDataDao audioDataDao = (AudioDataDao) factory.getBean("audioDataDao");
	List<AudioData> audioDataList = audioDataDao.getAudioDataList(roomId);

	VideoDataDao videoDataDao = (VideoDataDao) factory.getBean("videoDataDao");
	List<VideoData> videoDataList = videoDataDao.getVideoDataList(roomId);

	PlaybackUtil.updatePathWindows("C:/Users/sony/Desktop/INNOWHITE/playback/video_playlist", audioDataList, videoDataList);
	
	// WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)
	// factory.getBean("whiteboardDataDao");
	// whiteboardDataDao.getWhiteboardDTOForRoom("room1");
	
	//HashMap contains Key-Value pairs of SessionRecording-SessionBucket
	HashMap<SessionRecordings, SessionBucket> hm = new HashMap<SessionRecordings, SessionBucket>();
	
	for(int i =0; i< sessionRecordingsList.size(); i++){
		
		long sessionStartTime = sessionRecordingsList.get(i).getStartTime().getTime();
		long sessionEndTime = sessionRecordingsList.get(i).getEndTime().getTime();
		System.out.println("session Start Time::"+sessionRecordingsList.get(i).getStartTime().getTime());
		System.out.println("session End Time::"+sessionRecordingsList.get(i).getEndTime().getTime());
		
		/* sessionBucket for each Session
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
	System.out.println("preparing session buckets done!");
	//iterate through the Session Buckets .. Set of keys(sessions) 
//	Iterator itr = hm.keySet().iterator();
//	String cmd = null;
//	while(itr.hasNext()){
//		SessionRecordings sessions = (SessionRecordings) itr.next();
//		long sessionStartTime = sessions.getStartTime().getTime();
//		long sessionEndTime = sessions.getEndTime().getTime();;
//		System.out.println("Session Start Time::"+sessionStartTime);
//		System.out.println("Session End Time::"+sessionEndTime);
//		
//		SessionBucket sessionBucket = hm.get(sessions);
//		List<AudioData> audios = sessionBucket.getAudioDataList();
//		List<VideoData> videos = sessionBucket.getVideoDataList();
//		
//		for(int i=0; i < videos.size(); i++){
//			finalVideoPlaylist.add(videos.get(i).getFilePath());
//			System.out.println("finalPlayList"+i+"::"+finalVideoPlaylist.get(i));
//		}
//		
//		System.out.println("key::"+sessions);
//		System.out.println("value::"+hm.get(sessionBucket));
//		
//		for(int j=0; j<audios.size(); j++){
//			mapAudioToVideoStartBetween(audios.get(j), videos, sessionStartTime, sessionEndTime);
//		}
//		PlayBackPlayList playlist = null;
//		List<PlayBackPlayList> listPlayback = new ArrayList<PlayBackPlayList>();
//		for(int i=0; i<finalVideoPlaylist.size();i++){
//			playlist = new PlayBackPlayList();
//			playlist.setFilePath(finalVideoPlaylist.get(i));
//			playlist.setInsertedDate(new Date());
//			playlist.setRoomName(roomId);
//		}
//		updateFinalVideoTable(listPlayback);
//	}
	}

	private static void prepareVideoForSessionBucket(SessionBucket sb, int j, VideoData videoData,long sessionStartTime, long sessionEndTime) {
		long videoStartTime = videoData.getStartTime().getTime();
		long videoEndTime = videoData.getEndTime().getTime();
		System.out.println("videoStartTime::"+videoStartTime);
		System.out.println("videoEndTime::"+videoEndTime);
		String videoPath = videoData.getFilePath();
		String cmd = null;
		VideoData vd = null;
		if(videoStartTime<=sessionStartTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime){
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i "+videoPath+" -ss "+(sessionStartTime-videoStartTime)+" -t "+(videoEndTime-sessionStartTime)+" " +videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(sessionStartTime));
			vd.setEndTime(new Date(videoEndTime));
		}
		else if(videoStartTime<=sessionStartTime && videoEndTime>=sessionEndTime){
			int seconds = (int) (((sessionStartTime-videoStartTime) / 1000) % 60);
			int minutes = (int) (((sessionStartTime-videoStartTime) / 1000) / 60);
			int hours   = (int) (((sessionStartTime-videoStartTime)/ 1000) / 3600);
			String cutFrom = hours+":"+minutes+":"+seconds;
			System.out.println("cutFrom String::"+cutFrom);

			seconds = (int) (((sessionEndTime-sessionStartTime) / 1000) % 60);
			minutes = (int) (((sessionEndTime-sessionStartTime) / 1000) / 60);
			hours   = (int) (((sessionEndTime-sessionStartTime) / 1000) / 3600);
			String cutTo = hours+":"+minutes+":"+seconds;
			System.out.println("cutTill String::"+cutTo);

			
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i "+videoPath+" -ss "+cutFrom+" -t "+cutTo+" "+videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(sessionStartTime));
			vd.setEndTime(new Date(sessionEndTime));
		}
		else if(videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime){
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i "+videoPath+" -ss 00:00:00 -t "+(videoEndTime-videoStartTime)+" "+videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(videoStartTime));
			vd.setEndTime(new Date(videoEndTime));
		}
		else if(videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime>=sessionEndTime){
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i "+videoPath+" -ss 00:00:00 -t "+(sessionEndTime-videoStartTime)+" "+videoPath.replace(".flv", newVideoPath+".flv");
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
		long audioEndTime = audioData.getEndTime().getTime();
		System.out.println("audioStartStime::"+audioStartTime);
		System.out.println("audioEndStime::"+audioEndTime);
		String audioPath = audioData.getFilePath();
		String cmd = null;
		AudioData ad = null;
		if(audioStartTime<=sessionStartTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime){
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "-i "+audioPath+" -ss "+(sessionStartTime-audioStartTime)+" -t "+(audioEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(sessionStartTime));
			ad.setEndTime(new Date(audioEndTime));
		}
		else if(audioStartTime<=sessionStartTime && audioEndTime>=sessionEndTime){
			String newAudioPath = PlaybackUtil.getUnique();
			int seconds = (int) (((sessionStartTime-audioStartTime) / 1000) % 60);
			int minutes = (int) (((sessionStartTime-audioStartTime) / 1000) / 60);
			int hours   = (int) (((sessionStartTime-audioStartTime)/ 1000) / 3600);
			String cutFrom = hours+":"+minutes+":"+seconds;
			System.out.println("cutFrom String::"+cutFrom);

			seconds = (int) (((sessionEndTime-sessionStartTime) / 1000) % 60);
			minutes = (int) (((sessionEndTime-sessionStartTime) / 1000) / 60);
			hours   = (int) (((sessionEndTime-sessionStartTime) / 1000) / 3600);
			String cutTo = hours+":"+minutes+":"+seconds;
			System.out.println("cutTill String::"+cutTo);
			
			cmd = "-i "+audioPath+" -ss "+cutFrom+" -t "+cutTo+" -ab 32k -acodec libmp3lame " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(sessionStartTime));
			ad.setEndTime(new Date(sessionEndTime));
		}
		//audio in b/w the session
		else if(audioStartTime>=sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime){
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "-i "+audioPath+" -ss 00:00:00 -t "+(audioEndTime-audioStartTime)+"  -ab 32k -acodec libmp3lame " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(audioStartTime));
			ad.setEndTime(new Date(audioEndTime));
		}
		//starts in b/w and ends after
		else if(audioStartTime >= sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime >= sessionEndTime){
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "-i "+audioPath+" -ss 00:00:00 -t "+(sessionEndTime-audioStartTime)+"  -ab 32k -acodec libmp3lame " +audioPath.replace(".wav", newAudioPath+".mp3");
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
		System.out.println(pe.executeProcess("C:/ffmpeg/ffmpeg.exe "+cmd));
	}

	private static void mapAudioToVideoStartBetween(AudioData audioData,List<VideoData> videos, long sessionStartTime, long sessionEndTime) {
		String cmd = null;
		String audioPath = audioData.getFilePath();
		long audioStartTime = audioData.getStartTime().getTime();
		long audioEndTime = audioData.getEndTime().getTime();
		System.out.println("audioStartStime::"+audioStartTime);
		System.out.println("audioEndStime::"+audioEndTime);
		
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
//			videoStartTime = videos.get(k).getStartTime().getTime();
//			videoEndTime = videos.get(k).getEndTime().getTime();
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -i "+audioPath+" -ar 11025 "+ videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv");
			invokeProcess(cmd);
			finalVideoPlaylist.set(videoStartIndex, videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
		}
		else{
			for(int l=videoStartIndex; l<=videoStopIndex; l++){
				videoStartTime = videos.get(l).getStartTime().getTime();
				videoEndTime = videos.get(l).getEndTime().getTime();
				if(audioStartTime>=videoStartTime && audioStartTime<=videoEndTime && audioEndTime>videoEndTime){
					//cut audio and merge with video
					String newAudioPath = PlaybackUtil.getUnique();
					cmd = "-i "+audioPath+" -ss 00:00:00 -t "+(videoEndTime-audioStartTime)+" "+audioPath.replace(".mp3", newAudioPath+".mp3");
					invokeProcess(cmd);
					
					String newVideoPath = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3", newAudioPath+".mp3")+" -ar 11025 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
					invokeProcess(cmd);
					finalVideoPlaylist.set(l, videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv"));
				}
				else if(audioStartTime<videoStartTime && audioEndTime>videoEndTime){
					//cut audio and merge with video
					String newAudioPath = PlaybackUtil.getUnique();
					cmd = "-i "+audioPath+" -ss "+(videoStartTime-audioStartTime)+" -t "+(videoEndTime-videoStartTime)+" "+audioPath.replace(".mp3", newAudioPath+".mp3");
					invokeProcess(cmd);
					
					String newVideoPath = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3", newAudioPath+".mp3")+" -ar 11025 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
					invokeProcess(cmd);
					finalVideoPlaylist.set(l, videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv"));
				}
				else if(audioStartTime<videoStartTime && audioEndTime>=videoStartTime && audioEndTime<=videoEndTime){
					//cut audio and merge with video
					String newAudioPath = PlaybackUtil.getUnique();
					cmd = "-i "+audioPath+" -ss "+(videoStartTime-audioStartTime)+" -t "+(audioEndTime-videoStartTime)+" "+audioPath.replace(".mp3", newAudioPath+".mp3");
					invokeProcess(cmd);
					String newVideoPath = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3", newAudioPath+".mp3")+" -ar 11025 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
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
