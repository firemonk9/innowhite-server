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

	String roomId = "Rahul3";

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
		System.out.println("session Start Time::"+sessionRecordingsList.get(i).getStartTime());
		System.out.println("session End Time::"+sessionRecordingsList.get(i).getEndTime());
		
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
		System.out.println("done preparing session buckets...");
	}
	System.out.println("preparing session buckets done!");
	System.out.println("--------------------------------------------------------------");
	//iterate through the Session Buckets .. Set of keys(sessions) 
	Iterator itr = hm.keySet().iterator();
	String cmd = null;
	while(itr.hasNext()){
		System.out.println("for each session...");
		SessionRecordings sessions = (SessionRecordings) itr.next();
		long sessionStartTime = sessions.getStartTime().getTime();
		long sessionEndTime = sessions.getEndTime().getTime();;
		System.out.println("Session Start Time::"+sessions.getStartTime());
		System.out.println("Session End Time::"+sessions.getEndTime());
		
		SessionBucket sessionBucket = hm.get(sessions);
		List<AudioData> audios = sessionBucket.getAudioDataList();
		List<VideoData> videos = sessionBucket.getVideoDataList();
		
//		for(int i=0; i < videos.size(); i++){
//			finalVideoPlaylist.add(videos.get(i).getFilePath());
//			System.out.println("finalPlayList"+i+"::"+finalVideoPlaylist.get(i));
//		}
		
		System.out.println("key::"+sessions);
		System.out.println("value::"+sessionBucket);
		
		for(int j=0; j<audios.size(); j++){
			mapAudioToVideoStartBetween(audios,j , videos, sessionStartTime, sessionEndTime);
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
		System.out.println("------------------------------------");
		System.out.println("preparing VideoForSessionBucket.....");
		System.out.println("------------------------------------");
		
	if(videoData.getStartTime()!=null && videoData.getEndTime()!=null){
		long videoStartTime = videoData.getStartTime().getTime();
		long videoEndTime = videoData.getEndTime().getTime();
		String videoPath = videoData.getFilePath();
		System.out.println("videoStartTime::"+videoData.getStartTime());
		System.out.println("videoEndTime::"+videoData.getEndTime());
		System.out.println("videoPath::"+videoPath);
		
		String cmd = null;
		VideoData vd = null;
		
		if(videoStartTime<=sessionStartTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime){
			System.out.println("videoStartTime<=sessionStartTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime");
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i "+videoPath+" -ss "+secondsToHours(sessionStartTime-videoStartTime)+" -t "+secondsToHours(videoEndTime-sessionStartTime)+" " +videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(sessionStartTime));
			vd.setEndTime(new Date(videoEndTime));
		}
		else if(videoStartTime<=sessionStartTime && videoEndTime>=sessionEndTime){
			System.out.println("videoStartTime<=sessionStartTime && videoEndTime>=sessionEndTime");
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i "+videoPath+" -ss "+secondsToHours(sessionStartTime-videoStartTime)+" -t "+secondsToHours(sessionEndTime-sessionStartTime)+" "+videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(sessionStartTime));
			vd.setEndTime(new Date(sessionEndTime));
		}
		else if(videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime){
			System.out.println("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime");
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i "+videoPath+" -ss 00:00:00 -t "+secondsToHours(videoEndTime-videoStartTime)+" "+videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(videoStartTime));
			vd.setEndTime(new Date(videoEndTime));
		}
		else if(videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime>=sessionEndTime){
			System.out.println("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime>=sessionEndTime");
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "-i "+videoPath+" -ss 00:00:00 -t "+secondsToHours(sessionEndTime-videoStartTime)+" "+videoPath.replace(".flv", newVideoPath+".flv");
			invokeProcess(cmd);
			vd = new VideoData();
			vd.setFilePath(videoPath.replace(".flv", newVideoPath+".flv"));
			vd.setStartTime(new Date(videoStartTime));
			vd.setEndTime(new Date(sessionEndTime));
		}
		System.out.println("videoData"+j+"::"+vd);
		//System.out.println("videoData: Start Time:: "+vd.getStartTime());
		//System.out.println("videoData: End Time:: "+vd.getEndTime());
		System.out.println("videoData: File Path:: "+vd.getFilePath());
		sb.getVideoDataList().add(vd);
	}
	}

	private static void prepareAudioForSessionBucket(SessionBucket sb, int j, AudioData audioData,long sessionStartTime, long sessionEndTime) {
		System.out.println("------------------------------------");
		System.out.println("preparing AudioForSessionBucket.....");
		System.out.println("------------------------------------");
		long audioStartTime = audioData.getStartTime().getTime();
		long audioEndTime = audioData.getEndTime().getTime();
		String audioPath = audioData.getFilePath();
		System.out.println("audioStartStime::"+audioData.getStartTime());
		System.out.println("audioEndStime::"+audioData.getEndTime());
		System.out.println("audio Path::"+audioPath);
		String cmd = null;
		AudioData ad = null;
		if(audioStartTime<=sessionStartTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime){
			System.out.println("audioStartTime<=sessionStartTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime");
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "-i "+audioPath+" -ss "+secondsToHours(sessionStartTime-audioStartTime)+" -t "+secondsToHours(audioEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(sessionStartTime));
			ad.setEndTime(new Date(audioEndTime));
		}
		else if(audioStartTime<=sessionStartTime && audioEndTime>=sessionEndTime){
			System.out.println("audioStartTime<=sessionStartTime && audioEndTime>=sessionEndTime");
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "-i "+audioPath+" -ss "+secondsToHours(sessionStartTime-audioStartTime)+" -t "+secondsToHours(sessionEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(sessionStartTime));
			ad.setEndTime(new Date(sessionEndTime));
		}
		//audio in b/w the session
		else if(audioStartTime>=sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime){
			System.out.println("audioStartTime>=sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime");
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "-i "+audioPath+" -ss 00:00:00 -t "+secondsToHours(audioEndTime-audioStartTime)+"  -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(audioStartTime));
			ad.setEndTime(new Date(audioEndTime));
		}
		//starts in b/w and ends after
		else if(audioStartTime >= sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime >= sessionEndTime){
			System.out.println("audioStartTime >= sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime >= sessionEndTime");
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "-i "+audioPath+" -ss 00:00:00 -t "+secondsToHours(sessionEndTime-audioStartTime)+"  -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 " +audioPath.replace(".wav", newAudioPath+".mp3");
			invokeProcess(cmd);
			ad = new AudioData();
			ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
			ad.setStartTime(new Date(audioStartTime));
			ad.setEndTime(new Date(sessionEndTime));
		}
		System.out.println("audioData"+j+"::"+ad);
		//System.out.println("audioData: Start Time:: "+ad.getStartTime());
		//System.out.println("audioData: End Time:: "+ad.getEndTime());
		System.out.println("audioData: File Path:: "+ad.getFilePath());
		sb.getAudioDataList().add(ad);
	}

	/* 
	 *invokes process executor class and executes the ffmpeg cmd
	 */

	private static String secondsToHours(long seconds) {
		int ss = (int) ((seconds / 1000) % 60);
		int mm = (int) ((seconds / 1000) / 60);
		int hh   = (int) ((seconds/ 1000) / 3600);
		System.out.println("seconds to hours::"+hh+":"+mm+":"+ss);
		return (hh+":"+mm+":"+ss+".000");
	}

	private static void invokeProcess(String cmd) {
		ProcessExecutor pe = new ProcessExecutor();
		System.out.println(pe.executeProcess("C:/ffmpeg/ffmpeg.exe "+cmd));
	}

	private static void mapAudioToVideoStartBetween(List<AudioData> audioList,int j, List<VideoData> videos, long sessionStartTime, long sessionEndTime) {
		System.out.println("--------------------------------------------");
		System.out.println("mappingAudioToVideoStartBetween.....audio"+j);
		System.out.println("--------------------------------------------");

		String audioPath = audioList.get(j).getFilePath();
		long audioStartTime = audioList.get(j).getStartTime().getTime();
		long audioEndTime = audioList.get(j).getEndTime().getTime();
		long nextAudioStartTime = 0;
		long nextAudioEndTime = 0;
		if(audioList.size()> (j+1)){
			nextAudioStartTime = audioList.get(j+1).getStartTime().getTime();
			System.out.println("nextAudioStartTime "+nextAudioStartTime);
			nextAudioEndTime = audioList.get(j+1).getEndTime().getTime();	
		}
		
		System.out.println("audioStartStime::"+audioList.get(j).getStartTime());
		System.out.println("audioEndStime::"+audioList.get(j).getEndTime());
		System.out.println("audioFilePath::"+audioList.get(j).getFilePath());
		
		int videoStartIndex=0,videoStopIndex=0;
		long videoStartTime=0, videoEndTime=0;
		String cmd = null; String videoPath = null;
		
		/* loop through the sessionBucket video list
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
		System.out.println("videoStartIndex:::"+videoStartIndex);
		System.out.println("videoStopIndex:::"+videoStopIndex);
		if(videoStartIndex==videoStopIndex){
			System.out.println("videoStartIndex==videoStopIndex");
//			videoStartTime = videos.get(k).getStartTime().getTime();
//			videoEndTime = videos.get(k).getEndTime().getTime();
			String newVideoPath = null;
			
			//cut video into two -- 
			//1. without audio and  with audio
			//2. with audio
			if(audioStartTime>=videoStartTime){
				System.out.println("audioStartTime>videoStartTime");
				if(j==0){
					System.out.println("if(j==0)");
					newVideoPath = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -ss 00:00:00 -t "+secondsToHours(audioStartTime-videoStartTime)+" -b 200 " +
							""+ videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv");
					invokeProcess(cmd);
					finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
					//audioList.get(j+1).getStartTime()!=null
					if(nextAudioStartTime!=0){
						System.out.println("if(nextAudioStartTime!=0)");
						newVideoPath = PlaybackUtil.getUnique();
						cmd = "-i "+videos.get(videoStartIndex).getFilePath()+
						" -ss "+secondsToHours(audioStartTime-videoStartTime)+" -t "+secondsToHours(nextAudioStartTime-audioStartTime)+" -b 200 "+
						videos.get(videoStartIndex).getFilePath().replace(".flv", "int_play"+newVideoPath+".flv");
						invokeProcess(cmd);
					}
					else{
						System.out.println("if(nextAudioStartTime==0)");
						newVideoPath = PlaybackUtil.getUnique();
						cmd = "-i "+videos.get(videoStartIndex).getFilePath()+
						" -ss "+secondsToHours(audioStartTime-videoStartTime)+" -b 200 "+
						videos.get(videoStartIndex).getFilePath().replace(".flv", "int_play"+newVideoPath+".flv");
						invokeProcess(cmd);
					}
					System.out.println("merging..");
					String newVideoPath1 = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(videoStartIndex).getFilePath().replace(".flv", "int_play"+newVideoPath+".flv")+" -i "+audioPath+" -ar 22050 " +
							""+ videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath1+".flv");
					invokeProcess(cmd);
					finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist" + newVideoPath1 + ".flv"));
					//finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
				}//play from second audio 
				else if(j>0){
					System.out.println("else if(j>0)");
					//cut video from current audio till next audio starts
					if(nextAudioStartTime!=0){
						System.out.println("if(nextAudioStartTime!=0)");
						newVideoPath = PlaybackUtil.getUnique();
						cmd = "-i "+videos.get(videoStartIndex).getFilePath()+
						" -ss "+secondsToHours(audioStartTime-videoStartTime)+" -t "+secondsToHours(nextAudioStartTime-audioStartTime)+" -b 200 "+
						videos.get(videoStartIndex).getFilePath().replace(".flv", "int_play"+newVideoPath+".flv");
						invokeProcess(cmd);
					}
					//cut video from current audio till the end of video
					else{
						System.out.println("if(nextAudioStartTime==0)");
						newVideoPath = PlaybackUtil.getUnique();
						cmd = "-i "+videos.get(videoStartIndex).getFilePath()+
						" -ss "+secondsToHours(audioStartTime-videoStartTime)+" -t "+secondsToHours(videoEndTime-audioStartTime)+" -b 200 "+
						videos.get(videoStartIndex).getFilePath().replace(".flv", "int_play"+newVideoPath+".flv");
						invokeProcess(cmd);
					}
					System.out.println("merging...");
					String newVideoPath1 = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(videoStartIndex).getFilePath().replace(".flv", "int_play"+newVideoPath+".flv")+" -i "+audioPath+" -ar 22050 " +
							""+ videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath1+".flv");
					invokeProcess(cmd);
					finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist" + newVideoPath1 + ".flv"));
					//finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
				}
			}
			else if(audioStartTime==videoStartTime)
			{
				System.out.println("else if(audioStartTime==videoStartTime)");
				if(nextAudioStartTime!=0){
					System.out.println("if(nextAudioStartTime!=0)");
					newVideoPath = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(videoStartIndex).getFilePath()+
					" -ss "+secondsToHours(audioStartTime-videoStartTime)+" -t "+secondsToHours(nextAudioStartTime-audioStartTime)+" -b 200 "+
					videos.get(videoStartIndex).getFilePath().replace(".flv", "int_play"+newVideoPath+".flv");
					invokeProcess(cmd);
					System.out.println(cmd);
				}
				//cut video from current audio till the end of video
				else{
					System.out.println("if(nextAudioStartTime==0)");
					newVideoPath = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(videoStartIndex).getFilePath()+
					" -ss "+secondsToHours(audioList.get(j).getStartTime().getTime()-videoStartTime)+" -b 200 "+
					videos.get(videoStartIndex).getFilePath().replace(".flv", "int_play"+newVideoPath+".flv");
					invokeProcess(cmd);
					System.out.println(cmd);
				}
				System.out.println("merging...");
				//finalVideoPlaylist.set(videoStartIndex, videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
				newVideoPath = PlaybackUtil.getUnique();
				cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -i "+audioPath+" -ar 22050 "+ videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv");
				invokeProcess(cmd);
				System.out.println(cmd);
				finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
			}
		}
		else{
			for(int l=videoStartIndex; l<=videoStopIndex; l++){
				videoStartTime = videos.get(l).getStartTime().getTime();
				videoEndTime = videos.get(l).getEndTime().getTime();
				String newVideoPath = null;
				if(audioStartTime>=videoStartTime && audioStartTime<=videoEndTime && audioEndTime>videoEndTime){
					if(audioStartTime == videoStartTime){
						newVideoPath = PlaybackUtil.getUnique();
						cmd = "-i "+videos.get(l).getFilePath()+" -i "+audioPath+" -ar 22050 "+ videos.get(l).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv");
						invokeProcess(cmd);
						System.out.println(cmd);
						finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
					}
					else if(audioStartTime > videoStartTime){
						//cut video and  and audio  and merge with video
						newVideoPath = PlaybackUtil.getUnique();
						cmd = "-i "+videos.get(l).getFilePath()+" -ss 00:00:00 -t "+secondsToHours(audioStartTime-videoStartTime)+" -b 200 "+ videos.get(l).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv");
						invokeProcess(cmd);
						System.out.println(cmd);
						finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
						
						newVideoPath = PlaybackUtil.getUnique();
						cmd = "-i "+videos.get(l).getFilePath()+" -ss "+secondsToHours(audioStartTime-videoStartTime)+" -b 200 "+ videos.get(l).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv");
						invokeProcess(cmd);
						System.out.println(cmd);
						//finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
						
						String newVideoPath1 = PlaybackUtil.getUnique();
						cmd = "-i "+videos.get(l).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv")+" -i "+audioPath+" -ar 22050 "+ videos.get(l).getFilePath().replace(".flv", "playlist"+newVideoPath1+".flv");
						invokeProcess(cmd);
						System.out.println(cmd);
						finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".flv", "playlist"+newVideoPath+".flv"));
//						
//						String newAudioPath = PlaybackUtil.getUnique();
//						cmd = "-i "+audioPath+" -ss 00:00:00 -t "+secondsToHours(videoEndTime-audioStartTime)+" "+audioPath.replace(".mp3", newAudioPath+".mp3");
//						invokeProcess(cmd);
//						
//						String newVideoPath = PlaybackUtil.getUnique();
//						cmd = "-i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3", newAudioPath+".mp3")+" -ar 22050 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
//						invokeProcess(cmd);
//						finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv"));
					}
					//cut audio and merge with video
					String newAudioPath = PlaybackUtil.getUnique();
					cmd = "-i "+audioPath+" -ss 00:00:00 -t "+secondsToHours(videoEndTime-audioStartTime)+" "+audioPath.replace(".mp3", newAudioPath+".mp3");
					invokeProcess(cmd);
					System.out.println(cmd);
					
					newVideoPath = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3", newAudioPath+".mp3")+" -ar 22050 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
					invokeProcess(cmd);
					System.out.println(cmd);
					finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv"));
				}
				else if(audioStartTime<videoStartTime && audioEndTime>videoEndTime){
					//cut audio and merge with video
					String newAudioPath = PlaybackUtil.getUnique();
					cmd = "-i "+audioPath+" -ss "+secondsToHours(videoStartTime-audioStartTime)+" -t "+secondsToHours(videoEndTime-videoStartTime)+" "+audioPath.replace(".mp3", newAudioPath+".mp3");
					invokeProcess(cmd);
					System.out.println(cmd);
					
					newVideoPath = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3", newAudioPath+".mp3")+" -ar 22050 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
					invokeProcess(cmd);
					System.out.println(cmd);
					finalVideoPlaylist.add(videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv"));
				}
				else if(audioStartTime<videoStartTime && audioEndTime>=videoStartTime && audioEndTime<=videoEndTime){
					//cut audio and merge with video
					String newAudioPath = PlaybackUtil.getUnique();
					cmd = "-i "+audioPath+" -ss "+secondsToHours(videoStartTime-audioStartTime)+" -t "+secondsToHours(audioEndTime-videoStartTime)+" "+audioPath.replace(".mp3", newAudioPath+".mp3");
					invokeProcess(cmd);
					System.out.println(cmd);
					newVideoPath = PlaybackUtil.getUnique();
					cmd = "-i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".mp3", newAudioPath+".mp3")+" -ar 22050 "+videos.get(l).getFilePath().replace(".flv","playlist"+newVideoPath+".flv");
					invokeProcess(cmd);
					System.out.println(cmd);
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
