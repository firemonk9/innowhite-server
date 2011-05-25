package com.innowhite.PlaybackApp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.innowhite.PlaybackApp.dao.AudioDataDao;
import com.innowhite.PlaybackApp.dao.SessionRecordingDao;
import com.innowhite.PlaybackApp.dao.VideoDataDao;
import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.SessionBucket;
import com.innowhite.PlaybackApp.model.SessionRecordings;
import com.innowhite.PlaybackApp.model.VideoData;
import com.innowhite.PlaybackApp.util.PlaybackUtil;
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

	// WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)
	// factory.getBean("whiteboardDataDao");
	// whiteboardDataDao.getWhiteboardDTOForRoom("room1");
	
	//HashMap contains Key-Value pairs of SessionRecording-SessionBucket
	HashMap<SessionRecordings, SessionBucket> hm = new HashMap<SessionRecordings, SessionBucket>();
	
	for(int i =0; i< sessionRecordingsList.size(); i++){
		
		Date sessionStartDate = sessionRecordingsList.get(i).getStartTime();
		long sessionStartTime = sessionStartDate.getTime();
		Date sessionEndDate = sessionRecordingsList.get(i).getEndTime();
		long sessionEndTime = sessionEndDate.getTime();
		System.out.println("sessionStartDate::"+sessionStartDate);
		System.out.println("sessionEndDate::"+sessionEndDate);
		//sessionBucket for each Session
		SessionBucket sb = new SessionBucket();
		
		for(int j=0; j<audioDataList.size(); j++){
			
			Date audioStartDate = audioDataList.get(j).getStartTime();
			long audioStartTime = audioStartDate.getTime();
			Date audioEndDate = audioDataList.get(j).getEndTime();
			long audioEndTime = audioEndDate.getTime();
			System.out.println("audioStartDate::"+audioStartDate);
			System.out.println("audioEndDate::"+audioEndDate);
			
			if((audioStartTime >= sessionStartTime &&  audioStartTime < sessionEndTime) 
					|| (audioEndTime > sessionStartTime && audioEndTime <= sessionEndTime) 
					|| (audioStartTime <= sessionStartTime &&  audioEndTime >= sessionEndTime)){
				System.out.println("audioData"+j+"::"+audioDataList.get(j));
				sb.audioDataList.add(audioDataList.get(j));
			}
		}
		for(int j=0; j<videoDataList.size(); j++){
		
			Date videoStartDate = videoDataList.get(j).getStartTime();
			long videoStartTime = videoStartDate.getTime();
			Date videoEndDate = videoDataList.get(j).getEndTime();
			long videoEndTime = videoEndDate.getTime();
			System.out.println("videoStartDate::"+videoStartDate);
			System.out.println("videoEndDate::"+videoEndDate);
			
			if(videoStartTime >= sessionStartTime &&  videoEndTime <= sessionEndTime){
				System.out.println("videoData"+j+"::"+videoDataList.get(j));
				sb.videoDataList.add(videoDataList.get(j));
			}
		}
		System.out.println("SessionBucket.AudioData :: "+sb.audioDataList);
		System.out.println("SessionBucket.VideoData :: "+sb.videoDataList);
		hm.put(sessionRecordingsList.get(i), sb);
	}
	//iterate through the Set of keys
	Iterator itr = hm.keySet().iterator();
	String cmd = null;
	while(itr.hasNext()){
		SessionRecordings sessions = (SessionRecordings) itr.next();
		SessionBucket sessionBucket = hm.get(sessions);
		System.out.println("key::"+sessions);
		System.out.println("value::"+hm.get(sessionBucket));
		
		Date sessionStartDate = sessions.getStartTime();
		long sessionStartTime = sessionStartDate.getTime();
		Date sessionEndDate = sessions.getEndTime();
		long sessionEndTime = sessionEndDate.getTime();
		
		List<AudioData> audios = sessionBucket.getAudioDataList();
		List<VideoData> videos = sessionBucket.getVideoDataList();
		
		for(int j=0; j<audios.size(); j++){
			Date audioStartDate = audios.get(j).getStartTime();
			long audioStartTime = audioStartDate.getTime();
			Date audioEndDate = audios.get(j).getEndTime();
			long audioEndTime = audioEndDate.getTime();
			System.out.println("audioStartDate::"+audioStartDate);
			System.out.println("audioEndDate::"+audioEndDate);
			String audioPath = audios.get(j).getFilePath();
			//audio start < session start
			if(audioStartTime < sessionStartTime){
					//audio end in b/w the session
					if(audioEndTime > sessionStartTime && audioEndTime <= sessionEndTime){
						cmd = "ffmpeg.exe -i "+audioPath+" -ss "+sessionStartTime+" -ab 32k -acodec libmp3lame "+audioPath.replace(".wav", ".mp3");
						mapAudioToVideoStartBefore(cmd, audios.get(j), videos, sessionStartTime, sessionEndTime);
					}
					//audio end > session end 
					else if(audioEndTime > sessionEndTime){
						//cut the audio from session start to session end
						String newAudioPath = PlaybackUtil.getUnique();
						cmd = "ffmpeg.exe -i "+audioPath+" -ss "+sessionStartTime+" -t "+ sessionEndTime+" -ab 32k -acodec libmp3lame "+audioPath.replace(".wav", newAudioPath+".mp3");//audio.wav -> audio1.wav
						mapAudioToVideoStartBefore(cmd, audios.get(j), videos, sessionStartTime, sessionEndTime);
					}
			}
			//audio start >= session start && audio start <= session end
			else if(audioStartTime >= sessionStartTime && audioStartTime <= sessionEndTime){
					if(audioEndTime < sessionStartTime){
						System.out.println("invalid audio with end time<start time");
					}
					//audio ends b/w the session
					else if(audioEndTime > sessionStartTime && audioEndTime <= sessionEndTime){
						cmd = "ffmpeg.exe -i "+audioPath+" -ab 32k -acodec libmp3lame "+audioPath.replace(".wav", ".mp3");
						mapAudioToVideoStartBetween(cmd, audios.get(j), videos, sessionStartTime, sessionEndTime);
					}
					//audio ends after the session
					else if(audioEndTime > sessionEndTime){
						cmd = "ffmpeg.exe -i "+audioPath+" -ss "+audioStartTime+" -t "+sessionEndTime+" -ab 32k -acodec libmp3lame "+audioPath.replace(".wav", ".mp3");
						mapAudioToVideoStartBetween(cmd, audios.get(j), videos, sessionStartTime, sessionEndTime);
					}
			}
		}
	}
		
    }

	/* 
	 * 
	 * 
	 * */
	private static void mapAudioToVideoStartBefore(String cmd, AudioData audioData,List<VideoData> videos, long sessionStartTime, long sessionEndTime) {
		//trim the audio and convert into mp3
		String audioPath = audioData.getFilePath();
		long audioStartTime = audioData.getStartTime().getTime();
		long audioEndTime = audioData.getEndTime().getTime();
		int k;
		for(k=0; k<videos.size(); k++){
			long videoStartTime = videos.get(k).getStartTime().getTime();
			long videoEndTime = videos.get(k).getEndTime().getTime();
			//System.out.println("videoStartDate::"+videoStartDate);
			//System.out.println("videoEndDate::"+videoEndDate);
			if(audioEndTime>videoStartTime && audioEndTime<=videoEndTime)
			{
				break;
			}
		}	//etc/etc/etc/audio.wav -> 	
		for(int l=0; l<k; l++){
			//cut the audio into k audio files
			String newAudioPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+audioPath.replace(".wav", ".mp3")+" -ss "+videos.get(l).getStartTime()+" -t "+ videos.get(l).getEndTime() + " " +audioPath.replace(".wav", newAudioPath+".mp3");
			//mergeAudioVideo();
			String newVideoPath = PlaybackUtil.getUnique();
			cmd = "ffmpeg.exe -i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".wav", newAudioPath+".mp3")+" -ar 11025"+ newVideoPath+".mp4";
			finalVideoPlaylist.add(newVideoPath+".mp4");
		}
	}
	private static void mapAudioToVideoStartBetween(String cmd, AudioData audioData,List<VideoData> videos, long sessionStartTime, long sessionEndTime) {
		//trim the audio and convert into mp3
		String audioPath = audioData.getFilePath();
		long audioStartTime = audioData.getStartTime().getTime();
		long audioEndTime = audioData.getEndTime().getTime();
		int k,videoStartIndex=0,videoStopIndex=0;
		for(k=0; k<videos.size(); k++){
			long videoStartTime = videos.get(k).getStartTime().getTime();
			long videoEndTime = videos.get(k).getEndTime().getTime();
			//System.out.println("videoStartDate::"+videoStartDate);
			//System.out.println("videoEndDate::"+videoEndDate);
			if(audioStartTime>=videoStartTime && audioStartTime<=videoEndTime){
				videoStartIndex = k;
			}
			if(audioEndTime>=videoStartTime && audioEndTime<=videoEndTime){
				videoStopIndex = k;
			}
		}	
		//etc/etc/etc/audio.wav -> 	
		for(int l=videoStartIndex; l<=videoStopIndex; l++){
			if(l==videoStartIndex){
				//cut the audio into k audio files
				String newAudioPath = PlaybackUtil.getUnique();
				cmd = "ffmpeg.exe -i "+audioPath.replace(".wav", ".mp3")+" -ss "+audioStartTime+" -t "+ videos.get(l).getEndTime() + " " +audioPath.replace(".wav", newAudioPath+".mp3");
				//mergeAudioVideo();
				String newVideoPath = PlaybackUtil.getUnique();
				cmd = "ffmpeg.exe -i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".wav", newAudioPath+".mp3")+" -ar 11025"+ newVideoPath+".mp4";
				finalVideoPlaylist.add(newVideoPath+".mp4");
			}
			else if(l==videoStopIndex){
				//cut the audio into k audio files
				String newAudioPath = PlaybackUtil.getUnique();
				cmd = "ffmpeg.exe -i "+audioPath.replace(".wav", ".mp3")+" -ss "+videos.get(l).getStartTime()+" -t "+ audioEndTime + " " +audioPath.replace(".wav", newAudioPath+".mp3");
				//mergeAudioVideo();
				String newVideoPath = PlaybackUtil.getUnique();
				cmd = "ffmpeg.exe -i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".wav", newAudioPath+".mp3")+" -ar 11025"+ newVideoPath+".mp4";
				finalVideoPlaylist.add(newVideoPath+".mp4");
			}
			else{
				//cut the audio into k audio files
				String newAudioPath = PlaybackUtil.getUnique();
				cmd = "ffmpeg.exe -i "+audioPath.replace(".wav", ".mp3")+" -ss "+videos.get(l).getStartTime()+" -t "+ videos.get(l).getEndTime() + " " +audioPath.replace(".wav", newAudioPath+".mp3");
				//mergeAudioVideo();
				String newVideoPath = PlaybackUtil.getUnique();
				cmd = "ffmpeg.exe -i "+videos.get(l).getFilePath()+" -i "+audioPath.replace(".wav", newAudioPath+".mp3")+" -ar 11025"+ newVideoPath+".mp4";
				finalVideoPlaylist.add(newVideoPath+".mp4");
			}
		}
	}
}
