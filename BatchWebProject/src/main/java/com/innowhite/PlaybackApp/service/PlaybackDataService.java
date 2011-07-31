package com.innowhite.PlaybackApp.service;

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
		    List<SessionRecordings> sessionRecordingsList = sessionRecordingsDao.getSessionRecordingList(roomId);
	
		    if (sessionRecordingsList != null && sessionRecordingsList.size() == 0) {
		    	log.info("There are no recorded sessions for room "+roomId);
		    	return;
		    }
	
		    // AudioDataDao audioDataDao = (AudioDataDao)
		    // factory.getBean("audioDataDao");
		    List<AudioData> audioDataList = audioDataDao.getAudioDataList(roomId);
	
		    // VideoDataDao videoDataDao = (VideoDataDao)
		    // factory.getBean("videoDataDao");
		    List<VideoData> videoDataList = videoDataDao.getVideoDataList(roomId);
	
		    // PlayBackPlayListDao playBackPlayListDao = (PlayBackPlayListDao)
		    // factory.getBean("playBackPlayListDao");
		    // List<VideoData> videoDataList =
		    // sessionRecordingsDao.getVideoDataList(roomId);
	
		    // playbackVO = (PlaybackVO) factory.getBean("playBackVO");
		    PlaybackUtil.setPlaybackVO(playbackVO);
	
		    // replace unix file path to windows file path.
	
		    if (PlaybackUtil.isWindows()) {
				log.debug(" windows machine so changing the address ..");
				PlaybackUtil.updateVideoPathWindows(playbackVO.getWinVideoPath(), videoDataList);
				PlaybackUtil.updateAudioPathWindows(playbackVO.getWinAudioPath(), audioDataList);
		    }
		    // WhiteboardDataDao whiteboardDataDao = (WhiteboardDataDao)
		    // factory.getBean("whiteboardDataDao");
		    // whiteboardDataDao.getWhiteboardDTOForRoom("room1");
	
		    // HashMap contains Key-Value pairs of SessionRecording-SessionBucket
		    log.debug("--------------------------------------------------------------");
		    log.debug("Number of sessions in room "+roomId+" :: "+sessionRecordingsList.size());
		    for(int i=0; i<sessionRecordingsList.size(); i++){
		    	log.debug("Session "+i+" start-time:: "+sessionRecordingsList.get(i).getStartTime());
		    	log.debug("Session "+i+" end-time:: "+sessionRecordingsList.get(i).getEndTime());
		    }
		    log.debug("Number of audios in room "+roomId+" :: "+audioDataList.size());
		    for(int i=0; i<audioDataList.size(); i++){
		    	log.debug("Audio "+i+" start-time:: "+audioDataList.get(i).getStartTime());
		    	log.debug("Audio "+i+" end-time:: "+audioDataList.get(i).getEndTime());
		    	log.debug("Audio "+i+" file-path:: "+audioDataList.get(i).getFilePath());
		    }
		    log.debug("Number of videos in room "+roomId+" :: "+videoDataList.size());
		    for(int i=0; i<videoDataList.size(); i++){
		    	log.debug("Video "+i+" start-time:: "+videoDataList.get(i).getStartTime());
		    	log.debug("Video "+i+" end-time:: "+videoDataList.get(i).getEndTime());
		    	log.debug("Video "+i+" file-path:: "+videoDataList.get(i).getFilePath());
		    }
		    log.debug("--------------------------------------------------------------");
		    
			//if no sessions or no videos STOP PROCESS
			if (sessionRecordingsList.size()==0 || videoDataList.size()==0) {
				log.debug("ERROR ALERT::No Sessions or No Videos in Room!");
			}
			
			
		    /* Each session has a Session Bucket
			 * sessionBucket contains audios, videos of only that particular session
			 * audios & videos are trimmed and audio-video formats changed to "same-name".avi
			 */
		    HashMap<SessionRecordings, SessionBucket> sessionMap = new HashMap<SessionRecordings, SessionBucket>();
		    
		    if(sessionRecordingsList.size()==0){
		    	log.debug("No sessions in this room");
		    }
		    else{
		    	ArrayList<String> finalVideoPlaylist = new ArrayList<String>();
		    	log.debug("--------------------------------------------------------------");
			    log.debug("Preparing session buckets for each session!");
			    log.debug("--------------------------------------------------------------");
		    	for (int i=0; i<sessionRecordingsList.size(); i++) {
					long sessionStartTime = sessionRecordingsList.get(i).getStartTime().getTime();
					long sessionEndTime = sessionRecordingsList.get(i).getEndTime().getTime();
					log.debug("session Start Time::"+sessionRecordingsList.get(i).getStartTime());
					log.debug("session End Time::"+sessionRecordingsList.get(i).getEndTime());
			
					SessionBucket sb = new SessionBucket();
					//if room has audios, find which of them lie in the current session 
					if(audioDataList.size()==0){
						log.debug("ALERT:::No audios in this room");
					}
					else{
						for (int j=0; j<audioDataList.size(); j++) {
						    prepareAudioForSessionBucket(sb, j, audioDataList.get(j), sessionStartTime, sessionEndTime);
						}
					}
					//if room has videos, find which of them lie in the current session
					if(videoDataList.size()==0){
						log.debug("ERROR ALERT:: No videos in this room");
						//RETURN;
					}
					else{
						for (int j = 0; j < videoDataList.size(); j++) {
						    prepareVideoForSessionBucket(sb, j, videoDataList.get(j), sessionStartTime, sessionEndTime);
						}
					}
					sessionMap.put(sessionRecordingsList.get(i), sb);
			    }
		    	log.debug("session buckets prepared!!");
		    	
		    	log.debug("--------------------------------------------------------------");
			    log.debug("Preparing Video Playlist for each session!");
			    log.debug("--------------------------------------------------------------");
			    Iterator sessionKeys = sessionMap.keySet().iterator();
			    String cmd = null;
			    
			    while (sessionKeys.hasNext()) {
					SessionRecordings session = (SessionRecordings) sessionKeys.next();
					long sessionStartTime = session.getStartTime().getTime();
					long sessionEndTime = session.getEndTime().getTime();
			
					SessionBucket sessionBucket = sessionMap.get(session);
					List<AudioData> sessionAudioDataList = sessionBucket.getAudioDataList();
					List<VideoData> sessionVideoDataList = sessionBucket.getVideoDataList();
					
					log.debug("--------------------------------------------------------------");
					log.debug("Preparing Session Audio");
					log.debug("--------------------------------------------------------------");
					//paddedSessionAudioDataList contains - session audios padded
					List<AudioData> paddedSessionAudioDataList = new ArrayList<AudioData>();
					//contains the final audio
					AudioData sessionAudio = null;

					//if session has atleast 1 audio
					if(sessionAudioDataList.size()> 0){
						//pad all the session bucket audios (sessionAudioDataList) 
						paddedSessionAudioDataList = padAudioPlaylist(sessionAudioDataList, sessionStartTime);
						log.debug("_______________________________________________________________");
						log.debug("Number of audios after padding :: "+paddedSessionAudioDataList.size());
					    for(int i=0; i<paddedSessionAudioDataList.size(); i++){
					    	log.debug("Audio "+i+" start-time:: "+paddedSessionAudioDataList.get(i).getStartTime());
					    	log.debug("Audio "+i+" end-time:: "+paddedSessionAudioDataList.get(i).getEndTime());
					    	log.debug("Audio "+i+" file-path:: "+paddedSessionAudioDataList.get(i).getFilePath());
					    }
					    log.debug("_______________________________________________________________");
						//concatenate audios if more than one
						sessionAudio = concatenateAudios(paddedSessionAudioDataList);
					}
					//else if room has atleast one audio
					else{
						if(audioDataList.size()>0){
							log.debug("Session has no audios. Since, room has audios.. creating silent audio for this session.");
							sessionAudio = createSilentAudio(sessionEndTime-sessionStartTime);
						}
						sessionAudio = null;
					}
					if(sessionAudio!=null){
						log.debug("FINAL SESSION AUDIO PATH::"+sessionAudio.getFilePath());
					}
					else{
						log.debug("FINAL SESSION AUDIO PATH:: NULL (Reason-No audios in Room)");
					}
					
					
					log.debug("--------------------------------------------------------------");
					log.debug("Preparing Session Video");
					log.debug("--------------------------------------------------------------");
					//uniformSessionVideoDataList contains - session videos of same resolution, format etc
					List<VideoData> uniformSessionVideoDataList = new ArrayList<VideoData>();
					//contains the final video path
					VideoData sessionVideo = null;
					
					//if session has atleast 1 video
					if(sessionVideoDataList.size()> 0){
						//Set resolution of all Session Bucket Videos
						uniformSessionVideoDataList = setVideoResolution(sessionVideoDataList);
						log.debug("_______________________________________________________________");
						log.debug("Number of videos after setting resolution :: "+uniformSessionVideoDataList.size());
					    for(int i=0; i<uniformSessionVideoDataList.size(); i++){
					    	log.debug("Video "+i+" start-time:: "+uniformSessionVideoDataList .get(i).getStartTime());
					    	log.debug("Video "+i+" end-time:: "+uniformSessionVideoDataList .get(i).getEndTime());
					    	log.debug("Video "+i+" file-path:: "+uniformSessionVideoDataList .get(i).getFilePath());
					    }
					    log.debug("_______________________________________________________________");
						//concatenate all Session Bucket Videos
						sessionVideo = concatenateVideos(uniformSessionVideoDataList);
					}
					//if room has atleast one video
					else{
						log.debug("ERROR ALERT::Session has no videos.. Something's gotta be wrong!");
						if(videoDataList.size()<1){
							log.debug("ERROR ALERT::Room Itself has no videos.. Something's terribly wrong!");
						}
					}
					log.debug("FINAL SESSION VIDEO PATH::"+sessionVideo.getFilePath());
					
					log.debug("--------------------------------------------------------------");
					log.debug("Preparing FINAL Session Video: Merging Audio-Video");
					log.debug("--------------------------------------------------------------");
					/*if no audio. then the video paths are directly added to the final video playlist 
					 *else map each audio with corresponding videos
					 */
					
					if(sessionAudio!=null){
						String sessionVideoPlaylist = mergeAudioVideo(sessionAudio, sessionVideo);
						log.debug("_______________________________________________________________");
						log.debug("sessionVideoPlaylist :: "+sessionVideoPlaylist);
						log.debug("_______________________________________________________________");
						finalVideoPlaylist.add(sessionVideoPlaylist);
					}
					else{
						long duration = sessionVideo.getEndTime().getTime()-sessionVideo.getStartTime().getTime();
						log.debug("_______________________________________________________________");
						log.debug("sessionVideoPlaylist :: "+sessionVideo.getFilePath()+"##"+duration);
						log.debug("_______________________________________________________________");
						finalVideoPlaylist.add(sessionVideo.getFilePath()+"##"+duration);
					}
			    }
				log.debug("--------------------------------------------------------------");
				log.debug("SESSION DONE! Adding to finalVideoPlaylist");
				log.debug("--------------------------------------------------------------");
			    List<PlayBackPlayList> listPlayback = new ArrayList<PlayBackPlayList>();
				PlayBackPlayList playlist = null;
				for (int i=0; i<finalVideoPlaylist.size(); i++) {
				    playlist = new PlayBackPlayList();
				    String a[] = new String[2];
				    a = finalVideoPlaylist.get(i).split("##");
					//convert all playlist videos to .mp4
			    	String mp4_filepath = convertAVItoMP4264(a[0]);
			    	log.debug("_______________________________________________________________");
					log.debug("sessionVideoPlaylist::mp4_filepath :: "+mp4_filepath);
					log.debug("_______________________________________________________________");
				    playlist.setFilePath(mp4_filepath);
				    //String flv_filepath = convertAVItoFLV(a[0]);
				    //playlist.setFilePath(flv_filepath);
				    playlist.setInsertedDate(new Date());
				    playlist.setRoomName(roomId);
					log.debug(":::::::DURATION::::::::::::::::"+a[1]);
				    playlist.setDuration(a[1]);
				    listPlayback.add(playlist);
				}
				updateFinalVideoTable(listPlayback, playBackPlayListDao);
		    }
		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
    }
	
    private String mergeAudioVideo(AudioData sessionAudio, VideoData sessionVideo){
    	log.debug("_______________________________________________________________");
		log.debug("sessionAudio :: "+sessionAudio.getFilePath());
		log.debug("_______________________________________________________________");
		
		log.debug("_______________________________________________________________");
		log.debug("sessionVideo :: "+sessionVideo.getFilePath());
		log.debug("_______________________________________________________________");
		
    	log.debug("::::Merging finalSessionAudio and finalSessionVideo::::");
    	String newVideoPath = PlaybackUtil.getUnique();
    	String cmd = null;
    	//ffmpeg.exe -i 1332_2011-06-01-14-42-064756410402.mp3 -ar 44100 -ab 64k Rahul3_audio.avi
    	log.debug(":::first converting sessionAudio.mp3 to sessionAudio.avi:::");
    	cmd = " -i "+sessionAudio.getFilePath()+" -ar 44100 -ab 64k "+sessionAudio.getFilePath().replace(".mp3", ".avi");
    	PlaybackUtil.invokeProcess(cmd);
    	//merging session audio-video
    	cmd = " -i "+sessionVideo.getFilePath()+" -i "+sessionAudio.getFilePath().replace(".mp3", ".avi")+" -ar 44100 -ab 64k "
		   +sessionVideo.getFilePath().replace(".avi", newVideoPath+"playlist.avi");
		log.debug("ffmpeg Command for merging final session audio, video:::" + cmd);
		PlaybackUtil.invokeProcess(cmd);
		long duration = sessionVideo.getEndTime().getTime()-sessionVideo.getStartTime().getTime();
		return sessionVideo.getFilePath().replace(".avi", newVideoPath+"playlist.avi")+"##"+duration;
    }
	
    private AudioData concatenateAudios(List<AudioData> paddedSessionAudioDataList) {
    	log.debug(":::concatenating paddedSessionAudioDataList audios:::");
    	AudioData ad = new AudioData();
    	String newAudioPath = PlaybackUtil.getUnique();
    	if(paddedSessionAudioDataList.size()>1){
        	String cmd = paddedSessionAudioDataList.get(0).getFilePath().replace(".mp3",newAudioPath+"finalSessionAudio.mp3");
        	for(int i=0; i<paddedSessionAudioDataList.size(); i++){
        	    cmd = cmd+" "+paddedSessionAudioDataList.get(i).getFilePath();
        	}
    		log.debug("Mp3Wrap Command for concatenating audios:::" + cmd);
        	PlaybackUtil.invokeMp3Process(cmd);
        	ad.setStartTime(paddedSessionAudioDataList.get(0).getStartTime());
    		ad.setEndTime(paddedSessionAudioDataList.get(paddedSessionAudioDataList.size()-1).getEndTime());
    		ad.setFilePath(paddedSessionAudioDataList.get(0).getFilePath().replace(".mp3",newAudioPath+"finalSessionAudio.mp3"));
//    		ad.setId(id);
//    		ad.setRoomName(roomName);
    	}
    	else{
    		String cmd = " -i "+paddedSessionAudioDataList.get(0).getFilePath()+" "+paddedSessionAudioDataList.get(0).getFilePath().replace(".mp3",newAudioPath+"finalSessionAudio.mp3");
    		log.debug("Ffmpeg Command for concatenating audios::: (renaming file)" + cmd);
        	PlaybackUtil.invokeProcess(cmd);
        	ad.setStartTime(paddedSessionAudioDataList.get(0).getStartTime());
    		ad.setEndTime(paddedSessionAudioDataList.get(0).getEndTime());
    		ad.setFilePath(paddedSessionAudioDataList.get(0).getFilePath().replace(".mp3",newAudioPath+"finalSessionAudio.mp3"));
//    		ad.setId(id);
//    		ad.setRoomName(roomName);
    	}
    	return ad;
	}

	private ArrayList<VideoData> setVideoResolution(List<VideoData> sessionVideoDataList) {
		log.debug(":::setting resolution of sessionVideoPlaylist Videos:::");
		//mencoder.exe wb_audio.avi -oac copy -ovc lavc -vf scale=800:600 -o wb_audio3.avi
		ArrayList<VideoData> uniformSessionVideoDataList = new  ArrayList<VideoData>();
		VideoData vd = null;
//		String newVideoPath = PlaybackUtil.getUnique();
    	String cmd = null;
		
    	for(int i=0; i<sessionVideoDataList.size(); i++){
    	    vd = new VideoData();
    	    long a = (sessionVideoDataList.get(i).getEndTime().getTime()-sessionVideoDataList.get(i).getStartTime().getTime());
    	    log.debug("::::DURATION::::"+PlaybackUtil.secondsToHours(a));
    		cmd = " "+sessionVideoDataList.get(i).getFilePath()+" -oac copy -ovc lavc -vf scale=800:600 -o "+sessionVideoDataList.get(i).getFilePath().replace(".avi","800x600.avi");
			log.debug("::::MenCoder Command for setting resolution of video"+i+"/"+sessionVideoDataList.size()+"::::" + cmd);
			PlaybackUtil.invokeMencoderProcess(cmd);
			vd.setStartTime(sessionVideoDataList.get(i).getStartTime());
			vd.setEndTime(sessionVideoDataList.get(i).getEndTime());
			vd.setFilePath(sessionVideoDataList.get(i).getFilePath().replace(".avi", "800x600.avi"));
			uniformSessionVideoDataList.add(vd);
    	}
    	return uniformSessionVideoDataList;
	}

	private VideoData concatenateVideos(List<VideoData> uniformSessionVideoDataList) {
		log.debug("::::concatenating uniformSessionVideoDataList Videos::::");
		//mencoder.exe -oac copy -ovc lavc wb_audio3.avi screen_share_audio3.avi -o complete33.avi
		VideoData vd = new VideoData();
		String newVideoPath = PlaybackUtil.getUnique();
    	String cmd = " -oac copy -ovc lavc ";
    	for(int i=0; i<uniformSessionVideoDataList.size(); i++){
    	    cmd = cmd+" "+uniformSessionVideoDataList.get(i).getFilePath();
    	}
    	cmd = cmd+" -o "+uniformSessionVideoDataList.get(0).getFilePath().replace(".mp3",newVideoPath+"finalSessionVideo.avi");
		log.debug("MenCoder Command for concatenating videos:::" + cmd);
    	PlaybackUtil.invokeMencoderProcess(cmd);
    	vd.setStartTime(uniformSessionVideoDataList.get(0).getStartTime());
		vd.setEndTime(uniformSessionVideoDataList.get(uniformSessionVideoDataList.size()-1).getEndTime());
		vd.setFilePath(uniformSessionVideoDataList.get(0).getFilePath().replace(".mp3",newVideoPath+"finalSessionVideo.avi"));
//		vd.setId(id);
//		vd.setRoomName(roomName);
//		vd.setVideoType(videoType;)
		return vd;
	}
	
	private List<AudioData> padAudioPlaylist(List<AudioData> sessionAudioDataList, long sessionStartTime) {
    	//Temporary sessionAudioPlaylist contains audios for this session
		log.debug(":::padAudioPlaylist:::");
		List<AudioData> tempSessionAudioPlaylist = new ArrayList<AudioData>();
		AudioData ad = null;
		for(int i=0; i<sessionAudioDataList.size(); i++){
			ad = new AudioData();
			long audioStartTime = sessionAudioDataList.get(i).getStartTime().getTime();
    		log.debug("audioStartTime: "+sessionAudioDataList.get(i).getStartTime());
    		log.debug("sessionStartTime: "+PlaybackUtil.secondsToHours(sessionStartTime));
    		if(i==0){
    			if((audioStartTime-sessionStartTime)>0){
    				log.debug(":::padAudioPlaylist:::(audioStartTime-sessionStartTime)=0");
    				String silentAudioPath = createSilentAudio(audioStartTime-sessionStartTime).getFilePath();
    				log.debug("silentAudioPath: "+silentAudioPath);
    				ad.setStartTime(new Date(sessionStartTime));
    				ad.setEndTime(new Date(audioStartTime));
    				//ad.setId(id);
    				//ad.setRoomName(roomName);
    				ad.setFilePath(silentAudioPath);
    				
    			}
    			else{
    				log.debug(":::padAudioPlaylist:::(audioStartTime-sessionStartTime)=0");
    				ad.setStartTime(sessionAudioDataList.get(i).getStartTime());
    				ad.setEndTime(sessionAudioDataList.get(i).getEndTime());
    				//ad.setId(id);
    				//ad.setRoomName(roomName);
    				ad.setFilePath(sessionAudioDataList.get(i).getFilePath());
    			}
    		}
    		else{
    			long prevAudioEndTime = sessionAudioDataList.get(i-1).getEndTime().getTime();
    			if((audioStartTime-prevAudioEndTime)>0){
    				log.debug(":::padAudioPlaylist:::(audioStartTime-prevAudioEndTime)>0");
    				String silentAudioPath = createSilentAudio(audioStartTime-audioStartTime-prevAudioEndTime).getFilePath();
    				log.debug("silentAudioPath: "+silentAudioPath);
    				ad.setStartTime(new Date(prevAudioEndTime));
    				ad.setEndTime(new Date(audioStartTime));
    				//ad.setId(id);
    				//ad.setRoomName(roomName);
    				ad.setFilePath(silentAudioPath);
    			}
    			else{
    				log.debug(":::padAudioPlaylist:::(audioStartTime-prevAudioEndTime)=0");
    				ad.setStartTime(sessionAudioDataList.get(i).getStartTime());
    				ad.setEndTime(sessionAudioDataList.get(i).getEndTime());
    				//ad.setId(id);
    				//ad.setRoomName(roomName);
    				ad.setFilePath(sessionAudioDataList.get(i).getFilePath());
    			}
    		}
    		tempSessionAudioPlaylist.add(ad);
    	}
		log.debug(":::padAudioPlaylist:::tempSessionAudioPlaylist.size()"+tempSessionAudioPlaylist.size());		
		return tempSessionAudioPlaylist;
	}

	private AudioData createSilentAudio(long duration) {
		log.debug("creating silent audio of "+duration+" seconds");
		AudioData ad = new AudioData();
		String silentAudioPath = "C:/Users/sony/Desktop/Innowhite/playback/silence.avi";
	    String newAudioPath = PlaybackUtil.getUnique();
	    String cmd = null;
		if(duration<=3600){
			cmd = "-i "+silentAudioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(duration)+" -ar 44100 -ab 64k "+silentAudioPath.replace(".avi", newAudioPath+"silence.mp3");
		}
		else{
			log.debug("cannot create silent audio of "+duration+" (>3600)seconds");
		}
	    PlaybackUtil.invokeProcess(cmd);
	    ad.setStartTime(new Date(0));
	    ad.setEndTime(new Date(duration));
		//ad.setId(id);
		//ad.setRoomName(roomName);
		ad.setFilePath(silentAudioPath.replace(".mp3", newAudioPath+"silent.mp3"));
		return ad;
	}
	
    private String convertAVItoMP4264(String avi_filepath) {
    	String cmd = " -i "+avi_filepath+" -vcodec libx264 -g 250 -bf 3 -b_strategy 1 -coder 1 -qmin 10 "+
    	"-qmax 51 -sc_threshold 40 -flags +loop -cmp +chroma -me_range 16 -me_method hex -subq 5 -i_qfactor 0.71 "+
    	"-qcomp 0.6 -qdiff 4 -directpred 1 -flags2 +fastpskip -dts_delta_threshold 1 -ab 64k -ar 44100 "+
    	"-s 800x600 "+avi_filepath.replace(".avi", ".mp4");
    	PlaybackUtil.invokeProcess(cmd);
    	String mp4_filepath = avi_filepath.replace(".avi", ".mp4");
    	//log.debug("-----mp4_filepath before returning------" + mp4_filepath);
    	return mp4_filepath;
	}
    
    private String convertAVItoFLV(String avi_filepath) {
    	String cmd = "-i "+avi_filepath+" "+avi_filepath.replace("avi", "flv");
    	PlaybackUtil.invokeProcess(cmd);
    	String flv_filepath = avi_filepath.replace("avi", "flv");
    	return flv_filepath;
	}

	private static void prepareVideoForSessionBucket(SessionBucket sb, int j, VideoData videoData, long sessionStartTime, long sessionEndTime) {
	log.debug("------------------------------------");
	log.debug("preparing VideoForSessionBucket.....");
	log.debug("------------------------------------");

	//if (videoData.getStartTime() != null && videoData.getEndTime() != null) {
	long videoStartTime = videoData.getStartTime().getTime();
	long videoEndTime = videoData.getEndTime().getTime();
	String videoPath = videoData.getFilePath();
	log.debug("videoStartTime::"+videoData.getStartTime());
	log.debug("videoEndTime::"+videoData.getEndTime());
	log.debug("videoPath::"+videoPath);

	String cmd = null;
	VideoData vd = null;

	if (videoStartTime <= sessionStartTime && videoEndTime <= sessionEndTime && videoEndTime >= sessionStartTime) {
		log.debug("videoStartTime<=sessionStartTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime");
		String newVideoPath = PlaybackUtil.getUnique();
		cmd = "-i "+videoPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime - videoStartTime)+" -an -t "+PlaybackUtil.secondsToHours(videoEndTime - sessionStartTime)+" "
			+ videoPath.replace(".flv", newVideoPath+".avi");
		PlaybackUtil.invokeProcess(cmd);
		vd = new VideoData();
		vd.setFilePath(videoPath.replace(".flv", newVideoPath+".avi"));
		vd.setStartTime(new Date(sessionStartTime));
		vd.setEndTime(new Date(videoEndTime));
	} 
	else if (videoStartTime <= sessionStartTime && videoEndTime >= sessionEndTime) {
		log.debug("videoStartTime<=sessionStartTime && videoEndTime>=sessionEndTime");
		String newVideoPath = PlaybackUtil.getUnique();
		cmd = "-i "+videoPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime - videoStartTime)+" -an -t "+PlaybackUtil.secondsToHours(sessionEndTime - sessionStartTime)+" "
			+ videoPath.replace(".flv", newVideoPath+".avi");
		PlaybackUtil.invokeProcess(cmd);
		vd = new VideoData();
		vd.setFilePath(videoPath.replace(".flv", newVideoPath+".avi"));
		vd.setStartTime(new Date(sessionStartTime));
		vd.setEndTime(new Date(sessionEndTime));
	} 
	else if (videoStartTime >= sessionStartTime && videoStartTime <= sessionEndTime && videoEndTime <= sessionEndTime && videoEndTime >= sessionStartTime) {
		log.debug("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime");
		String newVideoPath = PlaybackUtil.getUnique();
		cmd = "-i "+videoPath+" -ss 00:00:00 -an -t "+PlaybackUtil.secondsToHours(videoEndTime - videoStartTime)+" "+videoPath.replace(".flv", newVideoPath+".avi");
		PlaybackUtil.invokeProcess(cmd);
		vd = new VideoData();
		vd.setFilePath(videoPath.replace(".flv", newVideoPath+".avi"));
		vd.setStartTime(new Date(videoStartTime));
		vd.setEndTime(new Date(videoEndTime));
	} 
	else if (videoStartTime >= sessionStartTime && videoStartTime <= sessionEndTime && videoEndTime >= sessionEndTime) {
		log.debug("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime>=sessionEndTime");
		String newVideoPath = PlaybackUtil.getUnique();
		cmd = "-i "+videoPath+" -ss 00:00:00 -an -t "+PlaybackUtil.secondsToHours(sessionEndTime - videoStartTime)+" "+videoPath.replace(".flv", newVideoPath+".avi");
		PlaybackUtil.invokeProcess(cmd);
		vd = new VideoData();
		vd.setFilePath(videoPath.replace(".flv", newVideoPath+".avi"));
		vd.setStartTime(new Date(videoStartTime));
		vd.setEndTime(new Date(sessionEndTime));
	}
	//log.debug("videoData"+j+"::"+vd);
	// log.debug("videoData: Start Time:: "+vd.getStartTime());
	// log.debug("videoData: End Time:: "+vd.getEndTime());
	log.debug("videoData: File Path:: "+vd.getFilePath());
	sb.getVideoDataList().add(vd);
	log.debug("DONE! Preparing videos for session bucket!");
	//}
    }

    private static void prepareAudioForSessionBucket(SessionBucket sb, int j, AudioData audioData, long sessionStartTime, long sessionEndTime) {
	log.debug("------------------------------------");
	log.debug("preparing AudioForSessionBucket.....");
	log.debug("------------------------------------");
	long audioStartTime = audioData.getStartTime().getTime();
	long audioEndTime = audioData.getEndTime().getTime();
	String audioPath = audioData.getFilePath();
	log.debug("audioStartStime::"+audioData.getStartTime());
	log.debug("audioEndStime::"+audioData.getEndTime());
	log.debug("audio Path::"+audioPath);
	
	String cmd = null;
	AudioData ad = null;

	if (audioStartTime <= sessionStartTime && audioEndTime <= sessionEndTime && audioEndTime >= sessionStartTime) {
	    log.debug("audioStartTime<=sessionStartTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime");
	    String newAudioPath = PlaybackUtil.getUnique();
	    // cmd =
	    // "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime-audioStartTime)+" -t "+PlaybackUtil.secondsToHours(audioEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
	    // +audioPath.replace(".wav", newAudioPath+".mp3");
	    cmd = "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime - audioStartTime)+" -t "+PlaybackUtil.secondsToHours(audioEndTime - sessionStartTime)
		   +" -ar 44100 -ab 64k "+audioPath.replace(".wav", newAudioPath+".mp3");
	    PlaybackUtil.invokeProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
	    ad.setStartTime(new Date(sessionStartTime));
	    ad.setEndTime(new Date(audioEndTime));
	} else if (audioStartTime <= sessionStartTime && audioEndTime >= sessionEndTime) {
	    log.debug("audioStartTime<=sessionStartTime && audioEndTime>=sessionEndTime");
	    String newAudioPath = PlaybackUtil.getUnique();
	    // cmd =
	    // "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime-audioStartTime)+" -t "+PlaybackUtil.secondsToHours(sessionEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
	    // +audioPath.replace(".wav", newAudioPath+".mp3");
	    cmd = "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime - audioStartTime)+" -t "+PlaybackUtil.secondsToHours(sessionEndTime - sessionStartTime)
		   +" -ar 44100 -ab 64k "+audioPath.replace(".wav", newAudioPath+".mp3");
	    PlaybackUtil.invokeProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
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
	    cmd = "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(audioEndTime - audioStartTime)+" -ar 44100 -ab 64k "+audioPath.replace(".wav", newAudioPath+".mp3");
	    PlaybackUtil.invokeProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
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
	    cmd = "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(sessionEndTime - audioStartTime)+" -ar 44100 -ab 64k "+audioPath.replace(".wav", newAudioPath+".mp3");
	    PlaybackUtil.invokeProcess(cmd);
	    ad = new AudioData();
	    ad.setFilePath(audioPath.replace(".wav", newAudioPath+".mp3"));
	    ad.setStartTime(new Date(audioStartTime));
	    ad.setEndTime(new Date(sessionEndTime));
	}
	//log.debug("audioData"+j+"::"+ad);
	// log.debug("audioData: Start Time:: "+ad.getStartTime());
	// log.debug("audioData: End Time:: "+ad.getEndTime());
	log.debug("audioData: File Path:: "+ad.getFilePath());
	sb.getAudioDataList().add(ad);
	log.debug("DONE! Preparing audios for session bucket!");
    }

    private static ArrayList<String> mapAudioToVideoStartBetween(List<AudioData> audioList, int j, List<VideoData> videos, long sessionStartTime, long sessionEndTime) {
	log.debug("--------------------------------------------");
	log.debug("mappingAudioToVideoStartBetween.....audio +"+j);
	log.debug("--------------------------------------------");

	String audioPath = audioList.get(j).getFilePath();
	long audioStartTime = audioList.get(j).getStartTime().getTime();
	long audioEndTime = audioList.get(j).getEndTime().getTime();
	long nextAudioStartTime = 0, nextAudioEndTime = 0;
	int videoStartIndex = 0, videoStopIndex = 0;
	long videoStartTime = 0, videoEndTime = 0;
	String cmd = null, videoPath = null;
	ArrayList<String> finalVideoPlaylist = new ArrayList<String>();
	log.debug("audioStartStime::"+audioList.get(j).getStartTime());
	log.debug("audioEndStime::"+audioList.get(j).getEndTime());
	log.debug("audioFilePath::"+audioList.get(j).getFilePath());

	// for the current audio i.e j, determine the next audio start time (if
	// applicable)
	if (audioList.size() > (j+1)) {// &&
					 // audioList.get(j+1).getStartTime().getTime()
					 // <=
					 // videos.get(j).getStartTime().getTime()
	    nextAudioStartTime = audioList.get(j+1).getStartTime().getTime();
	    log.debug("nextAudioStartTime "+nextAudioStartTime);
	    nextAudioEndTime = audioList.get(j+1).getEndTime().getTime();
	    log.debug("nextAudioEndTime "+nextAudioEndTime);
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
	log.debug("videoStartIndex:::"+videoStartIndex);
	log.debug("videoStopIndex:::"+videoStopIndex);

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
			    cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(audioStartTime - videoStartTime)+" -ar 44100 -ab 64k "
				   +videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath+".avi##"+(audioStartTime - videoStartTime)));
			    // audioList.get(j+1).getStartTime()!=null
			    // if more than one audio then cut/merge the video till the
			    // next audio start time
			    long duration = 0;
			    if (nextAudioStartTime != 0) {
					log.debug("if(nextAudioStartTime!=0)");
					newVideoPath = PlaybackUtil.getUnique();
					duration = nextAudioStartTime - audioStartTime;
					log.debug(":::::DURATION:::::"+duration);
					cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -ss "+PlaybackUtil.secondsToHours(audioStartTime - videoStartTime)+" -t "
						+ PlaybackUtil.secondsToHours(nextAudioStartTime - audioStartTime)+" "
						+ videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play"+newVideoPath+".avi");
					PlaybackUtil.invokeProcess(cmd);
			    }
			    // cut/merge the video till current video ends
			    else {
					log.debug("if(nextAudioStartTime==0)");
					newVideoPath = PlaybackUtil.getUnique();
					duration = videos.get(videoStartIndex).getEndTime().getTime() - videos.get(videoStartIndex).getStartTime().getTime();
					log.debug(":::::DURATION:::::"+duration);
					cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -ss "+PlaybackUtil.secondsToHours(audioStartTime - videoStartTime)+" "
						+ videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play"+newVideoPath+".avi");
					PlaybackUtil.invokeProcess(cmd);
			    }
			    // add merged video to playlist
			    log.debug("merging..");
			    String newVideoPath1 = PlaybackUtil.getUnique();
			    cmd = "-i "+videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play"+newVideoPath+".avi")+" -i "+audioPath+" -ar 44100 -ab 64k "
				   +videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath1+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath1+".avi##"+duration));
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
					log.debug(":::::DURATION:::::"+duration);
					cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -ss "+PlaybackUtil.secondsToHours(audioStartTime - videoStartTime)+" -t "
						+ PlaybackUtil.secondsToHours(nextAudioStartTime - audioStartTime)+" "
						+ videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play"+newVideoPath+".avi");
					PlaybackUtil.invokeProcess(cmd);
			    }
			    // cut video from current audio till the end of video
			    else {
					log.debug("if(nextAudioStartTime==0)");
					newVideoPath = PlaybackUtil.getUnique();
					duration = videoEndTime - audioStartTime;
					log.debug(":::::DURATION:::::"+duration);
					cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -ss "+PlaybackUtil.secondsToHours(audioStartTime - videoStartTime)+" -t "
						+ PlaybackUtil.secondsToHours(videoEndTime - audioStartTime)+" "+videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play"+newVideoPath+".avi");
					PlaybackUtil.invokeProcess(cmd);
			    }
			    // add merged video to playlist
			    log.debug("merging...");
			    String newVideoPath1 = PlaybackUtil.getUnique();
			    cmd = "-i "+videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play"+newVideoPath+".avi")+" -i "+audioPath+" -ar 44100 -ab 64k "
				   +videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath1+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath1+".avi##"+duration));
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
			    log.debug(":::::DURATION:::::"+duration);
			    cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -ss "+PlaybackUtil.secondsToHours(audioStartTime - videoStartTime)+" -t "
				   +PlaybackUtil.secondsToHours(nextAudioStartTime - audioStartTime)+" "+videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play"+intVideoPath+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    log.debug(cmd);
			    
			    log.debug("merging...");
				// finalVideoPlaylist.set(videoStartIndex,
				// videos.get(videoStartIndex).getFilePath().replace(".avi",
				// "playlist"+newVideoPath+".avi"));
				newVideoPath = PlaybackUtil.getUnique();
				cmd = "-i "+videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play"+intVideoPath+".avi")+" -i "+audioPath+" -ar 44100 -ab 64k "
					+ videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath+".avi");
				PlaybackUtil.invokeProcess(cmd);
				log.debug(cmd);
				finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath+".avi##"+duration));
			}
			// cut video from current audio till the end of video
			else {
			    log.debug("if(nextAudioStartTime==0)");
			    newVideoPath = PlaybackUtil.getUnique();
			    duration = videoEndTime - videoStartTime;
			    log.debug(":::::DURATION:::::"+duration);
			    //cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -ss "+PlaybackUtil.secondsToHours(audioList.get(j).getStartTime().getTime() - videoStartTime)+" "
				//   +videos.get(videoStartIndex).getFilePath().replace(".avi", "int_play"+newVideoPath+".avi");
			    cmd = "-i "+videos.get(videoStartIndex).getFilePath()+" -i "+audioPath+" -ar 44100 -ab 64k "
					+videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    log.debug(cmd);
			    finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi", "playlist"+newVideoPath+".avi##"+duration));
			}
	    }
	} 
	else {
		log.debug("videoStartIndex != videoStopIndex");
	    for (int l = videoStartIndex; l <= videoStopIndex; l++) 
	    {
	    	log.debug("videoIndex:::"+l);
			videoStartTime = videos.get(l).getStartTime().getTime();
			videoEndTime = videos.get(l).getEndTime().getTime();
			videoPath = videos.get(l).getFilePath();
			log.debug("videoStartTime::"+videos.get(l).getStartTime());
		    log.debug("videoEndTime::"+videos.get(l).getEndTime());
		    log.debug("videoPath::"+videoPath);
		    
			String newVideoPath = null;
			long duration = 0;
			//audio starts in b/w and ends after
			if (audioStartTime >= videoStartTime && audioStartTime <= videoEndTime && audioEndTime > videoEndTime) {
				log.debug("audio starts in b/w and ends after");
			    if (audioStartTime == videoStartTime) {
			    	log.debug("audioStartTime == videoStartTime");
					newVideoPath = PlaybackUtil.getUnique();
					duration = videoEndTime - videoStartTime;
					log.debug(":::::DURATION:::::"+duration);
					cmd = "-i "+videoPath+" -i "+audioPath+" -ar 44100 -ab 64k "+videoPath.replace(".avi", "playlist"+newVideoPath+".avi");
					PlaybackUtil.invokeProcess(cmd);
					log.debug(cmd);
					finalVideoPlaylist.add(videoPath.replace(".avi", "playlist"+newVideoPath+".avi##"+duration));
			    } 
			    else if (audioStartTime > videoStartTime) {
			    	log.debug("audioStartTime > videoStartTime");
					// cut video and and audio and merge with video
					newVideoPath = PlaybackUtil.getUnique();
					duration = audioStartTime - videoStartTime;
					log.debug(":::::DURATION:::::"+duration);
					cmd = "-i "+videoPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(audioStartTime - videoStartTime)+" "
						+videoPath.replace(".avi", "playlist"+newVideoPath+".avi");
					PlaybackUtil.invokeProcess(cmd);
					log.debug(cmd);
					finalVideoPlaylist.add(videoPath.replace(".avi", "playlist"+newVideoPath+".avi##"+duration));
		
					newVideoPath = PlaybackUtil.getUnique();
					duration = videoEndTime - videoStartTime;
					log.debug(":::::DURATION:::::"+duration);
					cmd = "-i "+videoPath+" -ss "+PlaybackUtil.secondsToHours(audioStartTime - videoStartTime)+" "
						+ videoPath.replace(".avi", "init_play"+newVideoPath+".avi");
					PlaybackUtil.invokeProcess(cmd);
					log.debug(cmd);
					// finalVideoPlaylist.add(videos.get(videoStartIndex).getFilePath().replace(".avi",
					// "playlist"+newVideoPath+".avi"));
		
					String newVideoPath1 = PlaybackUtil.getUnique();
					cmd = "-i "+videoPath.replace(".avi", "init_play"+newVideoPath+".avi")+" -i "+audioPath+" -ar 44100 -ab 64k "
						+videoPath.replace(".avi", "playlist"+newVideoPath1+".avi");
					PlaybackUtil.invokeProcess(cmd);
					log.debug(cmd);
					finalVideoPlaylist.add(videoPath.replace(".avi", "playlist"+newVideoPath1+".avi##"+duration));
					//
					// String newAudioPath = PlaybackUtil.getUnique();
					// cmd =
					// "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(videoEndTime-audioStartTime)+" "+audioPath.replace(".mp3",
					// newAudioPath+".mp3");
					// PlaybackUtil.invokeProcess(cmd);
					//
					// String newVideoPath = PlaybackUtil.getUnique();
					// cmd =
					// "-i "+videoPath+" -i "+audioPath.replace(".mp3",
					// newAudioPath+".mp3")+" -ar 22050 "+videoPath.replace(".flv","playlist"+newVideoPath+".flv");
					// PlaybackUtil.invokeProcess(cmd);
					// finalVideoPlaylist.add(videoPath.replace(".flv","playlist"+newVideoPath+".flv"));
			    }
			    // cut audio and merge with video
			    String newAudioPath = PlaybackUtil.getUnique();
			    duration = videoEndTime - audioStartTime;
			    log.debug(":::::DURATION:::::"+duration);
			    cmd = "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(videoEndTime - audioStartTime)+" "+audioPath.replace(".avi", "init_play"+newAudioPath+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    log.debug(cmd);
	
			    newVideoPath = PlaybackUtil.getUnique();
			    cmd = "-i "+videoPath+" -i "+audioPath.replace(".avi", "init_play"+newAudioPath+".avi")+" -ar 44100 -ab 64k "
				   +videoPath.replace(".avi", "playlist"+newVideoPath+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    log.debug(cmd);
			    finalVideoPlaylist.add(videoPath.replace(".avi", "playlist"+newVideoPath+".avi##"+duration));
			} 
			else if (audioStartTime < videoStartTime && audioEndTime > videoEndTime) {
				log.debug("audioStartTime < videoStartTime && audioEndTime > videoEndTime");
				// cut audio and merge with video
			    String newAudioPath = PlaybackUtil.getUnique();
			    duration = videoEndTime - videoStartTime;
			    log.debug(":::::DURATION:::::"+duration);
			    cmd = "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(videoStartTime - audioStartTime)+" -t "+PlaybackUtil.secondsToHours(videoEndTime - videoStartTime)+" "
				    +audioPath.replace(".avi", newAudioPath+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    log.debug(cmd);
	
			    newVideoPath = PlaybackUtil.getUnique();
			    cmd = "-i "+videoPath+" -i "+audioPath.replace(".avi", newAudioPath+".avi")+" -ar 44100 -ab 64k "
				    +videoPath.replace(".avi", "playlist"+newVideoPath+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    log.debug(cmd);
			    finalVideoPlaylist.add(videoPath.replace(".avi", "playlist"+newVideoPath+".avi##"+duration));
			} 
			else if (audioStartTime < videoStartTime && audioEndTime >= videoStartTime && audioEndTime <= videoEndTime) {
				log.debug("audioStartTime < videoStartTime && audioEndTime >= videoStartTime && audioEndTime <= videoEndTime");
				// cut audio and merge with video
			    String newAudioPath = PlaybackUtil.getUnique();
			    duration = audioEndTime - videoStartTime;
			    log.debug(":::::DURATION:::::"+duration);
			    cmd = "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(videoStartTime - audioStartTime)+" -t "+PlaybackUtil.secondsToHours(audioEndTime - videoStartTime)+" "
				    +audioPath.replace(".avi", newAudioPath+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    log.debug(cmd);
			    newVideoPath = PlaybackUtil.getUnique();
			    cmd = "-i "+videoPath+" -i "+audioPath.replace(".avi", newAudioPath+".avi")+" -ar 44100 -ab 64k "
				   +videoPath.replace(".avi", "playlist"+newVideoPath+".avi");
			    PlaybackUtil.invokeProcess(cmd);
			    log.debug(cmd);
			    finalVideoPlaylist.add(videoPath.replace(".avi", "playlist"+newVideoPath+".avi##"+duration));
			}
	    }
	}
	return finalVideoPlaylist;
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