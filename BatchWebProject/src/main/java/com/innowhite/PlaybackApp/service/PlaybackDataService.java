package com.innowhite.PlaybackApp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.dao.AudioDataDao;
import com.innowhite.PlaybackApp.dao.CallBackUrlsDao;
import com.innowhite.PlaybackApp.dao.PlayBackPlayListDao;
import com.innowhite.PlaybackApp.dao.RoomDao;
import com.innowhite.PlaybackApp.dao.SessionRecordingDao;
import com.innowhite.PlaybackApp.dao.VideoDataDao;
import com.innowhite.PlaybackApp.messaging.MP4ConverterMsgProducer;
import com.innowhite.PlaybackApp.model.AudioData;
import com.innowhite.PlaybackApp.model.CallBackUrlsData;
import com.innowhite.PlaybackApp.model.PlayBackPlayList;
import com.innowhite.PlaybackApp.model.SessionBucket;
import com.innowhite.PlaybackApp.model.SessionRecordings;
import com.innowhite.PlaybackApp.model.VideoData;
import com.innowhite.PlaybackApp.util.PlaybackUtil;
import com.innowhite.PlaybackApp.util.PlaybackVO;
import com.innowhite.PlaybackApp.util.ProcessExecutor;

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

	public RoomDao getRoomDao() {
		return roomDao;
	}

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}
	
	public void setMp4ConverterMsgProducer(MP4ConverterMsgProducer mp4ConverterMsgProducer) {
		this.mp4ConverterMsgProducer = mp4ConverterMsgProducer;
	}
	
	private MP4ConverterMsgProducer mp4ConverterMsgProducer;
	private RoomDao roomDao;
	private AudioDataDao audioDataDao;
	private VideoDataDao videoDataDao;
	private SessionRecordingDao sessionRecordingsDao;
	private PlaybackVO playbackVO = null;
	private PlayBackPlayListDao playBackPlayListDao = null;
	private CreateWhiteboardVideoFilesService createWhiteboardVideoFilesService=null;

	public void setCreateWhiteboardVideoFilesService(CreateWhiteboardVideoFilesService createWhiteboardVideoFilesService) {
		this.createWhiteboardVideoFilesService = createWhiteboardVideoFilesService;
	}

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

	public void process(String roomId, boolean upload) {
		// TODO Auto-generated method stub
		// ClassPathXmlApplicationContext appContext = new
		// ClassPathXmlApplicationContext(new String[] { "root-context.xml" });
		// of course, an ApplicationContext is just a BeanFactory
		// BeanFactory factory = (BeanFactory) appContext;

		// String roomId = "Dhiraj4";
		// if (args != null ) {
		// roomId = args;
		// }
		
		String retFileId = null; // This is playback_playlist's generate fileID (uniqueId)
		

		try {

			// SessionRecordingDao sessionRecordingsDao = (SessionRecordingDao)
			// factory.getBean("sessionRecordingsDao");

			PlaybackUtil.setPlaybackVO(playbackVO);
			
			createWhiteboardVideoFilesService.createFiles(roomId);
			
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
			log.debug("maxVideoDimensions (w+pad)x(h+pad):: " + maxVideoDimensions);
			log.debug("screenShareFlag:: " + screenShareFlag);

			// if no sessions or no videos STOP PROCESS
			if (sessionRecordingsList.size() == 0 || videoDataList.size() == 0) {
				log.debug("ERROR ALERT::No Sessions or No Videos in Room!");
			}
			
			// paddedSessionVideoPlaylist - padded session videos
//			List<VideoData> paddedSessionVideoDatalist = new ArrayList<VideoData>();
			// if session has atleast 1 video
			if (videoDataList.size() > 0) {
				// if screen-share was recorded
				if (screenShareFlag.equals("true")) {
					log.debug("session contains screenshare video... padding it...");
					videoDataList = padSessionVideoPlaylist(videoDataList, maxVideoDimensions, roomId);
				} else {
					log.debug("session does not contain screenshare video... no padding");
				}
			}

			log.debug("_______________________________________________________________");
			log.debug("Number of videos after padding:: " + videoDataList.size());
			for (int i = 0; i < videoDataList.size(); i++) {
				log.debug("Video " + i + " start-time:: " + videoDataList.get(i).getStartTime());
				log.debug("Video " + i + " end-time:: " + videoDataList.get(i).getEndTime());
				log.debug("Video " + i + " file-path:: " + videoDataList.get(i).getFilePath());
			}
			log.debug("_______________________________________________________________");
			
			/*
			 * Each session has a Session Bucket sessionBucket contains audios,
			 * videos of only that particular session audios & videos are
			 * trimmed and audio-video formats changed to "same-name".avi
			 */
			LinkedHashMap<SessionRecordings, SessionBucket> sessionMap = new LinkedHashMap<SessionRecordings, SessionBucket>();

			if (sessionRecordingsList.size() == 0) {
				log.debug("ERROR ALERT:: No sessions in this room");
			} else {
				ArrayList<String> finalVideoPlaylist = new ArrayList<String>();

				log.debug("--------------------------------------------------------------");
				log.debug("PREPARING SESSION BUCKETS!");
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
						log.debug("ERROR ALERT:::No audios in this room");
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
				log.debug(sessionMap.size()+" SESSION BUCKETS PREPARED!!");

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
					Date sst = session.getStartTime();
					log.debug("sessionStartTime(long)::::: " + PlaybackUtil.secondsToHours(sessionStartTime) + " sessionStartTime(Date)::::" + sst);
					long sessionEndTime = session.getEndTime().getTime();

					SessionBucket sessionBucket = sessionMap.get(session);
					log.debug("--------------------------------------------------------------");
					log.debug("SESSION "+sessionCounter+":: PREPARING SESSION AUDIO(pad and concat)");
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
						log.debug("##################### Final session audio path::" + sessionAudio.getFilePath());
					} else {
						log.debug("##################### Final session audio path:: NULL (Reason-No audios in Room)");
					}

					log.debug("--------------------------------------------------------------");
					log.debug("SESSION "+sessionCounter+":: PREPARING SESSION VIDEO (setResolution and concat)");
					log.debug("--------------------------------------------------------------");
					// sessionVideoDataList = session videos
					List<VideoData> sessionVideoDataList = sessionBucket.getVideoDataList();
					// uniformSessionVideoDataList - session videos of same
					// resolution, format
					List<VideoData> uniformSessionVideoDataList = new ArrayList<VideoData>();
//					// paddedSessionVideoPlaylist - padded session videos
//					List<VideoData> paddedSessionVideoDatalist = new ArrayList<VideoData>();
					// contains the final video path
					VideoData sessionVideo = null;

					// if session has atleast 1 video
					if (sessionVideoDataList.size() > 0) {
						// if screen-share was recorded
//						if (screenShareFlag.equals("true")) {
//							log.debug("session contains screenshare video... padding it...");
//							paddedSessionVideoDatalist = padSessionVideoPlaylist(sessionVideoDataList, maxVideoDimensions, roomId);
							// uniformSessionVideoDataList = setVideoFormatResolution(paddedSessionVideoPlaylist, videoDimensions);
							// uniformSessionVideoDataList = VideoImageMagick.formatSessionVideoPlaylist(paddedSessionVideoDatalist, maxVideoDimensions, playbackVO);
							uniformSessionVideoDataList = setDimensionsSessionVideoPlaylist(sessionVideoDataList, maxVideoDimensions, playbackVO);
//						} else {
//							log.debug("session does not contain screenshare video... no padding");
							// Set resolution of all Session Bucket Videos
							// uniformSessionVideoDataList = setVideoFormatResolution(sessionVideoDataList, videoDimensions);
							// uniformSessionVideoDataList = VideoImageMagick.formatSessionVideoPlaylist(sessionVideoDataList, maxVideoDimensions, playbackVO);
//							uniformSessionVideoDataList = setDimensionsSessionVideoPlaylist(sessionVideoDataList, maxVideoDimensions, playbackVO);
//						}
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
					log.debug("##################### Final session video path::" + sessionVideo.getFilePath());

					log.debug("--------------------------------------------------------------");
					log.debug("SESSION "+sessionCounter+":: MERGING SESSION VIDEO-AUDIO:");
					log.debug("--------------------------------------------------------------");
					/*
					 * if no audio. then the video paths are directly added to
					 * the final video playlist. else map each audio with
					 * corresponding videos
					 */
					if (sessionAudio != null) {
						String finalSessionVideoPath = mergeAudioVideo(sessionAudio, sessionVideo);
						log.debug("Adding SessionVideoPath to plalist :: " + finalSessionVideoPath);
						finalVideoPlaylist.add(finalSessionVideoPath);
					} else {
						// long duration = sessionVideo.getEndTime().getTime() -
						// sessionVideo.getStartTime().getTime();
						log.debug("Adding SessionVideoPath to plalist :: " + sessionVideo.getFilePath()); // +"##"+duration);
						finalVideoPlaylist.add(sessionVideo.getFilePath()); // +"##"+duration);
					}
				}
				// finalVideoPlaylist ::: contains all the session videos of the
				// room

				log.debug("--------------------------------------------------------------");
				log.debug("ADDING "+finalVideoPlaylist+ " SESSIONS TO finalVideoPlaylist!");
				log.debug("--------------------------------------------------------------");
				List<PlayBackPlayList> listPlayback = new ArrayList<PlayBackPlayList>();
				PlayBackPlayList playlist = null;
				for (int i = 0; i < finalVideoPlaylist.size(); i++) {
					playlist = new PlayBackPlayList();
					String finalSessionVideoPath = finalVideoPlaylist.get(i);

					// convert all playlist videos to .mp4
					// String mp4_filepath = convertAVItoMP4264(a[0]);
					// log.debug("_______________________________________________________________");
					// log.debug("sessionVideoPlaylist::mp4_filepath :: " +
					// mp4_filepath);
					// playlist.setFilePath(mp4_filepath);

					// convert all playlist videos to .flv
					String flv_filepath = convertAVItoFLV(finalSessionVideoPath);
					playlist = setPlayBackPlayList(flv_filepath, roomId);
					log.debug("sessionVideoPlaylist::flv_filepath :: " + flv_filepath);
					listPlayback.add(playlist);
				}
				
				log.debug("_______________________________________________________________");
				for(int i=0;i<listPlayback.size();i++){
					log.debug("playlist video no. "+i+":: "+listPlayback.get(i).toString());
				}
				log.debug("_______________________________________________________________");
				
				// concat videos to prepare a single video for the meeting
				String meetingRoomVideoPath = setMeetingRoomVideo(listPlayback, roomId);
				log.debug("--------------------------------------------------------------");
				log.debug("Uploading Meeting Room Video (Path) to youtube:: "+meetingRoomVideoPath);
				log.debug("--------------------------------------------------------------");
				PlayBackPlayList meetingRoomVideo = setPlayBackPlayList(meetingRoomVideoPath, roomId);
				// upload to youtube and get-set the youtube url of the
				// meeting-room video
				if (upload) {
					YoutubeUploadService ytUpload = new YoutubeUploadService();
					String xml = roomDao.getSessionDetailXML(roomId);
					String roomName = "Name not available for room :: " + roomId;
					String roomDescription = " Description not available for room " + roomId;
					if (xml != null) {
						roomName = UtilService.getRoomName(xml);
						roomDescription = UtilService.getRoomDescription(xml);
						log.debug("roomName: " + roomName + "\t roomDescription: " + roomDescription);
					}
					String youtubeURL = ytUpload.uploadVideo(meetingRoomVideo.getFilePath(), roomName, roomDescription);
					log.debug("meetingRoomVideoPath :: " + meetingRoomVideoPath);
					log.debug("################### youtubeURL :: " + youtubeURL);
					meetingRoomVideo.setYoutubeUrl(youtubeURL);

					// send notification so that the innowhite server can send
					// email to all users in the room.
					if (youtubeURL != null && youtubeURL.length() > 0) {
						CallBackUrlsData callBackVO = callBackUrlsDao.getURLData(roomId);
						String url = null;
						if (callBackVO != null) {
							url = callBackVO.getPlaybackReadyUrl();
						}
						NotifyPlayBackReadyStatus.notifyPlayBackReady(roomId, null);
					}
				}
				
				retFileId = updateFinalVideoTable(meetingRoomVideo, playBackPlayListDao);
				
				log.debug("######### retFileId :: " + retFileId);
				if (meetingRoomVideoPath!=null && retFileId!=null){
					
					String strMessage= meetingRoomVideoPath +"^"+ retFileId;
					mp4ConverterMsgProducer.sendMessage(strMessage);
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private List<VideoData> setDimensionsSessionVideoPlaylist(List<VideoData> paddedSessionVideoDatalist, String maxVideoDimensions, PlaybackVO playbackVO2) {
		log.debug("Inside setDimensionsSessionVideoPlaylist...........................................");
		String cmd = null, in_path = null, out_path = null;
		int vidWidth = 0, vidHeight = 0, padWidth = 0, padHeight = 0;
		List<VideoData> sameDimensionSessionVideoDatalist = new ArrayList<VideoData>();
		String temp[] = maxVideoDimensions.split("x");
		int maxWidth = Integer.parseInt(temp[0]);
		int maxHeight = Integer.parseInt(temp[1]);

		String out_path_flv=null;
		for (int i = 0; i < paddedSessionVideoDatalist.size(); i++) {
			in_path = paddedSessionVideoDatalist.get(i).getFilePath();
			out_path = in_path.replace(".flv", "_overlay_avi.avi");
			out_path_flv = in_path.replace(".flv", "_overlay.flv");
			vidWidth = paddedSessionVideoDatalist.get(i).getWidth();
			vidHeight = paddedSessionVideoDatalist.get(i).getHeight();
			padWidth = ((maxWidth - vidWidth) / 2);
			padHeight = ((maxHeight - vidHeight) / 2);
			log.debug("--->printing video data: " + paddedSessionVideoDatalist.get(i));
			String color = "black";
			log.debug("--->Setting deminesions of video" + i);
			cmd = " -i " + in_path + " -vf pad=" + maxWidth + ":" + maxHeight + ":" + padWidth + ":" + padHeight + ":" + color + " -sameq " + out_path_flv;
			PlaybackUtil.invokeFfmpegProcess(cmd);
			
			cmd= " -i "+out_path_flv+ " -r 2 -an -vcodec copy "+ out_path;
			PlaybackUtil.invokeFfmpegProcess(cmd);
			
			sameDimensionSessionVideoDatalist.add(paddedSessionVideoDatalist.get(i));
			sameDimensionSessionVideoDatalist.get(i).setFilePath(out_path);
		}
		return sameDimensionSessionVideoDatalist;
	}

	CallBackUrlsDao callBackUrlsDao;

	public void setCallBackUrlsDao(CallBackUrlsDao callBackUrlsDao) {
		this.callBackUrlsDao = callBackUrlsDao;
	}

	private PlayBackPlayList setPlayBackPlayList(String videoPath, String roomId) {
		HashMap<String, String> videohm1 = new HashMap<String, String>();
		String cmd = " -i " + videoPath;
		PlaybackUtil.invokeVideoAttribProcess(cmd, videohm1);
		PlayBackPlayList playlist = new PlayBackPlayList();
		playlist.setWidth(PlaybackUtil.getNum(videohm1.get("width")));
		playlist.setHeight(PlaybackUtil.getNum(videohm1.get("height")));
		playlist.setSize(PlaybackUtil.getNumLong(videohm1.get("filesize")));

		long actualDuration=0;
		long dur = PlaybackUtil.getNumLong(videohm1.get("duration"));
		long Dur = PlaybackUtil.hoursToMillis(videohm1.get("Duration")) / 1000;
		log.debug("((duration hack)) duration:: " + dur + "\t Duration:: " + Dur);
		if(dur!=0){
			actualDuration = dur;
		}else if (Dur!=0) {
			actualDuration = Dur;
		} else{
			log.warn("Video does not exist or is broken!");
		}
		playlist.setDuration("" + actualDuration);
		playlist.setFilePath(videoPath);
		playlist.setInsertedDate(new Date());
		playlist.setRoomName(roomId);
		return playlist;
	}

	private String setMeetingRoomVideo(List<PlayBackPlayList> listPlayback, String roomId) {
		log.debug("Preparing setMeetingRoomVideo..............");
		// mencoder.exe -oac copy -ovc lavc wb_audio3.avi
		// screen_share_audio3.avi -o complete33.avi
		String meetingRoomVideoPath = listPlayback.get(0).getFilePath().replace(".flv", "_meeting.flv");
		
		String cmd = null;
		if (listPlayback.size() > 1) {
			log.debug("listPlayback.size() > 1");
			cmd = " -oac mp3lame -ovc copy -msglevel all=4 -force-avi-aspect 1.777 ";
			for (int i = 0; i < listPlayback.size(); i++) {
				cmd = cmd + " " + listPlayback.get(i).getFilePath();
			}
			cmd = cmd + " -o " + meetingRoomVideoPath;
			log.debug("MenCoder Command for concatenating videos:::" + cmd);
			PlaybackUtil.invokeMencoderProcess(cmd);
		} else {
			log.debug("listPlayback.size() <= 1");
//			log.debug("Command for concatenating just the 1 video::: (renaming file)" + cmd);
//			cmd = " -i " + listPlayback.get(0).getFilePath() + " " + meetingRoomVideoPath;
//			log.debug("Ffmpeg Command for concatenating just the 1 video::: (renaming file)" + cmd);
//			PlaybackUtil.invokeFfmpegProcess(cmd);
			
			
			cmd = " cp " + listPlayback.get(0).getFilePath() + " " + meetingRoomVideoPath;
			ProcessExecutor pe = new ProcessExecutor();
			log.debug("Command for concatenating just the 1 video::: (renaming file)" + cmd);
			boolean val = pe.executeProcess(cmd, playbackVO.getTempLocation(), null, true);
			log.debug("return from the UNIX COPY CMD process executor :: " + val);
			
		}
		return meetingRoomVideoPath;
	}

	private List<VideoData> padSessionVideoPlaylist(List<VideoData> sessionVideoDataList, String maxVideoDimensions, String roomId) {
		// Temporary sessionVideoPlaylist contains videos for this session
		log.debug("Inside padSessionVideoPlaylist...........................................");
		List<VideoData> tempSessionVideoPlaylist = new ArrayList<VideoData>();

		VideoData vd = null;
		for (int i = 0; i < sessionVideoDataList.size(); i++) {
			String videoType = sessionVideoDataList.get(i).getVideoType();
			if (videoType != null && videoType.equals("DESKTOP")) {
				log.debug("screen share video found. Padding with (diffDuration)sec vid :");

				// String newVideoPath = PlaybackUtil.getUnique();

				// get the ffmpeg duration
				HashMap<String, String> vhm = new HashMap<String, String>();
				String cmd = " -i " + sessionVideoDataList.get(i).getFilePath();
				PlaybackUtil.invokeVideoAttribProcess(cmd, vhm);
				log.debug(" printing the video hash map ...::" + vhm);

				long start_time = sessionVideoDataList.get(i).getStartTime().getTime();
				long end_time = sessionVideoDataList.get(i).getEndTime().getTime();
				long dbDuration = (end_time - start_time) / 1000;
				long actualDuration = 0;
				long dur = PlaybackUtil.getNumLong(vhm.get("duration"));
				long Dur = PlaybackUtil.hoursToMillis(vhm.get("Duration")) / 1000;
				log.debug("((duration hack)) duration:: " + dur + "\t Duration:: " + Dur);
				if(dur!=0){
					actualDuration = dur;
				}else if (Dur!=0) {
					actualDuration = Dur;
				} else{
					log.warn("Video -> "+sessionVideoDataList.get(i).getFilePath()+" <- does not exist or is broken!");
				}

				long padDuration = (dbDuration - actualDuration);

				log.debug(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				log.debug(" start_time(getTime):: "+start_time);
				log.debug(" start time for screen share video.. (newDate):: " + new Date(start_time)+" (secondsToHours):: " + PlaybackUtil.secondsToHours(start_time));
				log.debug(" end time for screen share video.. (newDate):: " + new Date(end_time)+" (secondsToHours):: " + PlaybackUtil.secondsToHours(end_time));
				log.debug(" Actual (ffmpeg) video duration :: " + actualDuration);
				log.debug(" Expected (database) video duration :: " + dbDuration);
				log.debug(" duration to pad is ::" + padDuration);
				log.debug(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

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
					log.warn(" The file path is null... this is not right..  ");
				}

				log.debug("directory for padding video.. ");
				String uniquePath = PlaybackUtil.getUnique();

				log.debug("creating ss padding video.. cutting the 1hr video to specified duration..");
				String ss_pad_input_path = "/opt/InnowhiteData/scripts/ScreenSharePad.flv";
				String ss_pad_output_path = curDir + "/" + roomId + "_ss_pad_" + uniquePath + ".flv";
				cmd = " -i " + ss_pad_input_path + " -t " + PlaybackUtil.secondsToHours(padDuration * 1000) + " -ar 44100 -ab 64k " + ss_pad_output_path;

				log.debug("setting the resolution of ss padding video..");
				String[] dim = maxVideoDimensions.split("x");
				PlaybackUtil.invokeFfmpegProcess(cmd);
				int width = Integer.parseInt(dim[0]) - 10;
				int height = Integer.parseInt(dim[1]) - 10;
				String ss_pad_scaled_path = ss_pad_output_path.replace(".flv", "_" + width + "x" + height + ".flv");
				cmd = " " + ss_pad_output_path + " -oac copy -ovc lavc -vf scale=" + width + ":" + height + " -o " + ss_pad_scaled_path;
				PlaybackUtil.invokeMencoderProcess(cmd);

				log.debug("-->adding screen share pad video to paddingSessionVideoPlaylist");
				vd = new VideoData();
				// vd.setVideoData(vd.getStartTime(),vd.getEndTime(),
				// vd.getFilePath(),vd.getWidth(),vd.getHeight(),vd.getDuration(),vd.getVideoType(),vd.getId(),vd.getRoomName());
				vd.setStartTime(new Date(start_time));
				vd.setEndTime(new Date(start_time + (padDuration*1000)));
				log.debug("setting end time for ss pad:: "+new Date(start_time + (padDuration*1000)) + " ::: padDuration*1000:: "+padDuration*1000);
				vd.setFilePath(ss_pad_scaled_path);
				vd.setDuration("" + padDuration);
				vd.setHeight(height);
				vd.setWidth(width);
				vd.setVideoType("VIDEO");
				vd.setId(sessionVideoDataList.get(i).getId());
				vd.setRoomName(sessionVideoDataList.get(i).getRoomName());
				tempSessionVideoPlaylist.add(vd);

				log.debug("-->adding screen share video to paddingSessionVideoPlaylist");
				vd = new VideoData();
				//Screen-share hack
//				if(i==0){
					// i==0 with padD = SS starts.. not working
					vd.setStartTime(new Date(start_time));
//					log.debug("screen share hack. right shifting start time of ss video by: "+(padDuration*1000));
//				}
//				else{
//					
//					vd.setStartTime(new Date(start_time + (padDuration*1000)));
//					log.debug("screen share hack not applied. ss video start_time: "+new Date(start_time));
//				}
//				vd.setStartTime(new Date(start_time));
				vd.setEndTime(sessionVideoDataList.get(i).getEndTime());
				vd.setFilePath(sessionVideoDataList.get(i).getFilePath());
				vd.setDuration(sessionVideoDataList.get(i).getDuration());
				vd.setHeight(sessionVideoDataList.get(i).getHeight());
				vd.setWidth(sessionVideoDataList.get(i).getWidth());
				vd.setVideoType(sessionVideoDataList.get(i).getVideoType());
				vd.setId(sessionVideoDataList.get(i).getId());
				vd.setRoomName(sessionVideoDataList.get(i).getRoomName());
				tempSessionVideoPlaylist.add(vd);
			} else {
				log.debug("-->adding whiteboard video to paddingSessionVideoPlaylist");
				tempSessionVideoPlaylist.add(sessionVideoDataList.get(i));
			}
		}
		return tempSessionVideoPlaylist;
	}

	private String getMaxVideoDimensions(List<VideoData> videoList, HashMap<String, String> vhm) {
		log.debug("Inside getMaxVideoDimensions..............");
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
			vhm.clear();
			if (videoType != null) {
				// if (i == 0) {
				// cmd = " -i " + videoList.get(i).getFilePath();
				// PlaybackUtil.invokeVideoAttribProcess(cmd, vhm);
				// maxWidth = Integer.parseInt(vhm.get("width"));
				// maxHeight = Integer.parseInt(vhm.get("height"));
				// log.debug("width:"+maxWidth+"\t height:"+maxHeight);
				// videoList.get(i).setWidth(maxWidth);
				// videoList.get(i).setHeight(maxHeight);
				// log.debug("printing video data: "+videoList.get(i));
				// } else if (i > 0) {
				cmd = " -i " + videoList.get(i).getFilePath();
				PlaybackUtil.invokeVideoAttribProcess(cmd, vhm);
				tempWidth = Integer.parseInt(vhm.get("width"));
				tempHeight = Integer.parseInt(vhm.get("height"));
				log.debug("width:" + tempWidth + "\t height:" + tempHeight);
				videoList.get(i).setWidth(tempWidth);
				videoList.get(i).setHeight(tempHeight);
				log.debug("printing video data: " + videoList.get(i));
				if (tempWidth > maxWidth) {
					maxWidth = tempWidth;
				}
				if (tempHeight > maxHeight) {
					maxHeight = tempHeight;
				}
				// }
			} else if (videoType == null) {
				log.warn("printing the videoObj :: " + videoList);
			}
		}
		if ((maxWidth % 2) == 0) {
			maxWidth = maxWidth + 10;
		} else {
			maxWidth = maxWidth + 11;
		}
		if ((maxHeight % 2) == 0) {
			maxHeight = maxHeight + 10;
		} else {
			maxHeight = maxHeight + 11;
		}

		return maxWidth + "x" + maxHeight + "##" + screenShareFlag;
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
		log.debug("sessionAudio :: " + sessionAudio.getFilePath());
		log.debug("sessionVideo :: " + sessionVideo.getFilePath());

		// String newVideoPath = PlaybackUtil.getUnique();
		String cmd = null;
		// convert audio from mp3 to avi
		log.debug(":::first converting sessionAudio.mp3 to sessionAudio.avi:::");
		cmd = " -i " + sessionAudio.getFilePath() + " -ar 44100 -ab 64k " + sessionAudio.getFilePath().replace(".mp3", ".avi");
		PlaybackUtil.invokeFfmpegProcess(cmd);

		// merging session audio-video
		cmd = " -i " + sessionVideo.getFilePath() + " -i " + sessionAudio.getFilePath().replace(".mp3", ".avi") + " -ar 44100 -ab 64k -vcodec copy "
				+ sessionVideo.getFilePath().replace(".avi", "_playlist.avi");
		log.debug(":::merging final session audio and final session video:::" + cmd);
		PlaybackUtil.invokeFfmpegProcess(cmd);
		// long duration = sessionVideo.getEndTime().getTime() -
		// sessionVideo.getStartTime().getTime();
		return sessionVideo.getFilePath().replace(".avi", "_playlist.avi");// "##"
																			// +
																			// duration;
	}

	private AudioData concatenateAudios(List<AudioData> paddedSessionAudioDataList, int sessionCounter) {
		log.debug("Inside concatenateAudios..............");
		AudioData ad = new AudioData();
		String sessionAudioPath = paddedSessionAudioDataList.get(0).getFilePath().replace(".mp3", "_session" + sessionCounter + ".mp3");
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
			ad.setFilePath(sessionAudioPath.replace(".mp3", "_MP3WRAP.mp3"));
		} else {
//			String cmd = " -i " + paddedSessionAudioDataList.get(0).getFilePath() + " " + sessionAudioPath;
//			log.debug("Ffmpeg Command for concat 1 audio::: (renaming file)" + cmd);
//			PlaybackUtil.invokeFfmpegProcess(cmd);
			
			String cmd = " cp " + paddedSessionAudioDataList.get(0).getFilePath() + " " + sessionAudioPath;
			ProcessExecutor pe = new ProcessExecutor();
			log.debug("Ffmpeg Command for concat 1 audio::: (renaming file)" + cmd);
			boolean val = pe.executeProcess(cmd, playbackVO.getTempLocation(), null, true);
			log.debug("return from the UNIX COPY CMD process executor :: " + val);
			
			ad.setEndTime(paddedSessionAudioDataList.get(0).getEndTime());
			ad.setFilePath(sessionAudioPath);
			
		}
		ad.setStartTime(paddedSessionAudioDataList.get(0).getStartTime());
		// ad.setFilePath(paddedSessionAudioDataList.get(0).getFilePath().replace(".mp3",
		// "finalSessionAudio.mp3"));
		// ad.setId(id);
		// ad.setRoomName(roomName);
		return ad;
	}

/*	
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
			cmd = " " + sessionVideoDataList.get(i).getFilePath() + " -oac copy -ovc copy -vf scale=" + dim[0] + ":" + dim[1] + " -o "
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
*/
	
	private VideoData concatenateVideos(List<VideoData> uniformSessionVideoDataList, int sessionCounter) {
		log.debug("Inside concatenateVideos..............");
		// mencoder.exe -oac copy -ovc lavc wb_audio3.avi
		// screen_share_audio3.avi -o complete33.avi
		VideoData vd = new VideoData();
		String out_path = uniformSessionVideoDataList.get(0).getFilePath().replace(".avi", "_session" + sessionCounter + ".avi");

		if (uniformSessionVideoDataList.size() > 1) {
			String cmd = " -nosound -ovc copy -msglevel all=4 -force-avi-aspect 1.777 ";
			for (int i = 0; i < uniformSessionVideoDataList.size(); i++) {
				cmd = cmd + " " + uniformSessionVideoDataList.get(i).getFilePath();
			}
			cmd = cmd + " -o " + out_path + "";
			log.debug("MenCoder Command for concatenating videos:::" + cmd);
			PlaybackUtil.invokeMencoderProcess(cmd);

			long video_start_time = uniformSessionVideoDataList.get(0).getStartTime().getTime();
			long video_end_time = uniformSessionVideoDataList.get(uniformSessionVideoDataList.size() - 1).getEndTime().getTime();
			long duration = video_end_time - video_start_time;
			vd.setDuration("" + duration);
			vd.setEndTime(new Date(video_end_time));
		} else {
//			String cmd = " -i " + uniformSessionVideoDataList.get(0).getFilePath() + " " + out_path;
//			log.debug("Ffmpeg Command for concatenating just the 1 video::: (renaming file)" + cmd);
//			PlaybackUtil.invokeFfmpegProcess(cmd);
			
			String cmd = " cp " + uniformSessionVideoDataList.get(0).getFilePath() + " " + out_path;
			ProcessExecutor pe = new ProcessExecutor();
			log.debug("Command for concatenating just the 1 video::: (renaming file)" + cmd);
			boolean val = pe.executeProcess(cmd, playbackVO.getTempLocation(), null, true);
			log.debug("return from the UNIX COPY CMD process executor :: " + val);
			
			vd.setEndTime(uniformSessionVideoDataList.get(0).getEndTime());
			vd.setDuration("" + (uniformSessionVideoDataList.get(0).getEndTime().getTime() - uniformSessionVideoDataList.get(0).getStartTime().getTime()));
		}
		vd.setStartTime(uniformSessionVideoDataList.get(0).getStartTime());
		vd.setFilePath(out_path);
		vd.setVideoType("PLAYBACK");
		vd.setHeight(uniformSessionVideoDataList.get(0).getHeight());
		vd.setWidth(uniformSessionVideoDataList.get(0).getWidth());
		vd.setId(uniformSessionVideoDataList.get(0).getId());
		vd.setRoomName(uniformSessionVideoDataList.get(0).getRoomName());
		return vd;
	}

	private List<AudioData> padAudioPlaylist(List<AudioData> sessionAudioDataList, long sessionStartTime) {
		log.debug("Inside padAudioPlaylist.................");

		// Temporary sessionAudioPlaylist contains audios for this session
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
					log.debug("audio Starts after session Start");
					String silentAudioPath = (createSilentAudio(audioStartTime - sessionStartTime)).getFilePath();
					log.debug("silentAudioPath: " + silentAudioPath);
					ad.setStartTime(new Date(sessionStartTime));
					ad.setEndTime(new Date(audioStartTime));
					ad.setFilePath(silentAudioPath);
					ad.setId(sessionAudioDataList.get(i).getId());
					ad.setRoomName(sessionAudioDataList.get(i).getRoomName());

					tempSessionAudioPlaylist.add(ad);
					tempSessionAudioPlaylist.add(sessionAudioDataList.get(i));

				} else {
					log.debug("audio and session Start at the same Time");
					tempSessionAudioPlaylist.add(sessionAudioDataList.get(i));
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
					ad.setId(sessionAudioDataList.get(i).getId());
					ad.setRoomName(sessionAudioDataList.get(i).getRoomName());
					ad.setFilePath(silentAudioPath);

					tempSessionAudioPlaylist.add(ad);
					tempSessionAudioPlaylist.add(sessionAudioDataList.get(i));
				} else {
					log.debug("next audio Starts soon after previous audio");
					tempSessionAudioPlaylist.add(sessionAudioDataList.get(i));
				}
			}
			// tempSessionAudioPlaylist.add(ad);
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
		String audio_out_path=silentAudioPath.replace(".mp4", "_" + newAudioPath + "_"+(duration/1000)+".mp3");
		
		if (duration <= 7200000) {
			cmd = " -i " + silentAudioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(duration) + " -ar 44100 -ab 64k " + audio_out_path;
		} else {
			log.debug("cannot create silent audio of " + duration + " (>7200000)seconds");
		}
		// cmd = cmd = " -i "+silentAudioPath+"";
		PlaybackUtil.invokeFfmpegProcess(cmd);
		ad.setStartTime(new Date(0));
		ad.setEndTime(new Date(duration));
		// ad.setId(id);
		// ad.setRoomName(roomName);
		ad.setFilePath(audio_out_path);
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
		String flv_filepath = avi_filepath.replace(".avi", ".flv");
		String cmd = " -i " + avi_filepath + " -acodec copy -vcodec copy -ar 44100 -ab 64k " + flv_filepath;
		PlaybackUtil.invokeFfmpegProcess(cmd);
		return flv_filepath;
	}

	private static void prepareVideoForSessionBucket(SessionBucket sb, int j, VideoData videoData, long sessionStartTime, long sessionEndTime) {
		log.debug("-----------------------------------------");
		log.debug("preparing Video " + j + " ForSessionBucket.....");
		log.debug("-----------------------------------------");

		// if (videoData.getStartTime() != null && videoData.getEndTime() !=
		// null) {
		long videoStartTime = videoData.getStartTime().getTime();
		long videoEndTime = videoData.getEndTime().getTime();
		String videoPath = videoData.getFilePath();
		log.debug("videoStartTime::" + videoData.getStartTime());
		log.debug("videoEndTime::" + videoData.getEndTime());
		log.debug("videoPath::" + videoPath);
		log.debug("Also printing video data: " + videoData);

		String cmd = null;
		String newVideoPath = PlaybackUtil.getUnique();
		boolean sessionBucketFlag = true;
		VideoData vd = new VideoData();

		String out_video_path = videoPath.replace(".flv", "_" + newVideoPath + "_v_bucket.flv");

		if (videoStartTime <= sessionStartTime && videoEndTime <= sessionEndTime && videoEndTime >= sessionStartTime) {
			log.debug("videoStartTime<=sessionStartTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime");
			cmd = "-i " + videoPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - videoStartTime) + " -an -t " + PlaybackUtil.secondsToHours(videoEndTime - sessionStartTime) + " -sameq "+ out_video_path;
			executeFfmpegAndSetVideoData(cmd, vd, videoData, sessionStartTime, videoEndTime, out_video_path);
		} else if (videoStartTime <= sessionStartTime && videoEndTime >= sessionEndTime) {
			log.debug("videoStartTime<=sessionStartTime && videoEndTime>=sessionEndTime");
			cmd = "-i " + videoPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - videoStartTime) + " -an -t " + PlaybackUtil.secondsToHours(sessionEndTime - sessionStartTime)+ " -sameq " + out_video_path;
			executeFfmpegAndSetVideoData(cmd, vd, videoData, sessionStartTime, sessionEndTime, out_video_path);
		} else if (videoStartTime >= sessionStartTime && videoStartTime <= sessionEndTime && videoEndTime <= sessionEndTime && videoEndTime >= sessionStartTime) {
			log.debug("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime<=sessionEndTime && videoEndTime>=sessionStartTime");
			cmd = "-i " + videoPath + " -ss 00:00:00 -an -t " + PlaybackUtil.secondsToHours(videoEndTime - videoStartTime) + " -sameq " + out_video_path;
			executeFfmpegAndSetVideoData(cmd, vd, videoData, videoStartTime, videoEndTime, out_video_path);
		} else if (videoStartTime >= sessionStartTime && videoStartTime <= sessionEndTime && videoEndTime >= sessionEndTime) {
			log.debug("videoStartTime>=sessionStartTime && videoStartTime<=sessionEndTime && videoEndTime>=sessionEndTime");
			cmd = "-i " + videoPath + " -ss 00:00:00 -an -t " + PlaybackUtil.secondsToHours(sessionEndTime - videoStartTime) + " -sameq " + out_video_path;
			executeFfmpegAndSetVideoData(cmd, vd, videoData, videoStartTime, sessionEndTime, out_video_path);
		} else {
			sessionBucketFlag = false;
		}

		if (sessionBucketFlag) {
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
			log.debug("Video " + j + " was added to session bucket!");
		} else {
			log.debug("Video " + j + " was NOT added to session bucket!");
		}

	}

	private static void prepareAudioForSessionBucket(SessionBucket sb, int j, AudioData audioData, long sessionStartTime, long sessionEndTime) {
		log.debug("-----------------------------------------");
		log.debug("preparing Audio " + j + " ForSessionBucket.....");
		log.debug("-----------------------------------------");
		long audioStartTime = audioData.getStartTime().getTime();
		long audioEndTime = audioData.getEndTime().getTime();
		String audioPath = audioData.getFilePath();
		log.debug("audioStartStime::" + audioData.getStartTime());
		log.debug("audioEndStime::" + audioData.getEndTime());
		log.debug("audio Path::" + audioPath);

		String cmd = null;
		 String newAudioPath = PlaybackUtil.getUnique();
		boolean sessionBucketFlag = true;
		AudioData ad = new AudioData();

		String out_audio_path = audioPath.replace(".wav", "_" + newAudioPath + "_a_bucket.mp3");

		if (audioStartTime <= sessionStartTime && audioEndTime <= sessionEndTime && audioEndTime >= sessionStartTime) {
			log.debug("audioStartTime<=sessionStartTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime");
			// cmd =
			// "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime-audioStartTime)+" -t "+PlaybackUtil.secondsToHours(audioEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
			// +audioPath.replace(".wav", newAudioPath+".mp3");
			cmd = " -i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(audioEndTime - sessionStartTime)
					+ " -ar 44100 -ab 64k " + out_audio_path;
			executeFfmpegAndSetAudioData(cmd, ad, sessionStartTime, audioEndTime, out_audio_path);
		} else if (audioStartTime <= sessionStartTime && audioEndTime >= sessionEndTime) {
			log.debug("audioStartTime<=sessionStartTime && audioEndTime>=sessionEndTime");
			// cmd =
			// "-i "+audioPath+" -ss "+PlaybackUtil.secondsToHours(sessionStartTime-audioStartTime)+" -t "+PlaybackUtil.secondsToHours(sessionEndTime-sessionStartTime)+" -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
			// +audioPath.replace(".wav", newAudioPath+".mp3");
			cmd = " -i " + audioPath + " -ss " + PlaybackUtil.secondsToHours(sessionStartTime - audioStartTime) + " -t " + PlaybackUtil.secondsToHours(sessionEndTime - sessionStartTime)
					+ " -ar 44100 -ab 64k " + out_audio_path;
			executeFfmpegAndSetAudioData(cmd, ad, sessionStartTime, sessionEndTime, out_audio_path);
		}
		// audio in b/w the session
		else if (audioStartTime >= sessionStartTime && audioStartTime <= sessionEndTime && audioEndTime <= sessionEndTime && audioEndTime >= sessionStartTime) {
			log.debug("audioStartTime>=sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime<=sessionEndTime && audioEndTime>=sessionStartTime");
			// cmd =
			// "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(audioEndTime-audioStartTime)+"  -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
			// +audioPath.replace(".wav", newAudioPath+".mp3");
			cmd = " -i " + audioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(audioEndTime - audioStartTime) + " -ar 44100 -ab 64k " + out_audio_path;
			executeFfmpegAndSetAudioData(cmd, ad, audioStartTime, audioEndTime, out_audio_path);
		}
		// starts in b/w and ends after
		else if (audioStartTime >= sessionStartTime && audioStartTime <= sessionEndTime && audioEndTime >= sessionEndTime) {
			log.debug("audioStartTime >= sessionStartTime && audioStartTime<=sessionEndTime && audioEndTime >= sessionEndTime");
			// cmd =
			// "-i "+audioPath+" -ss 00:00:00 -t "+PlaybackUtil.secondsToHours(sessionEndTime-audioStartTime)+"  -ab 32k -acodec libmp3lame -qscale 8 -ab 196608 "
			// +audioPath.replace(".wav", newAudioPath+".mp3");
			cmd = " -i " + audioPath + " -ss 00:00:00 -t " + PlaybackUtil.secondsToHours(sessionEndTime - audioStartTime) + " -ar 44100 -ab 64k " + out_audio_path;
			executeFfmpegAndSetAudioData(cmd, ad, audioStartTime, sessionEndTime, out_audio_path);
		} else {
			sessionBucketFlag = false;
		}

		if (sessionBucketFlag) {
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
			log.debug("Audio " + j + " was added to session bucket!");
		} else {
			log.debug("Audio " + j + " was NOT added to session bucket!");
		}

	}

	private static void executeFfmpegAndSetAudioData(String cmd, AudioData ad, long start_time, long end_time, String audio_path) {
		PlaybackUtil.invokeFfmpegProcess(cmd);
		ad.setStartTime(new Date(start_time));
		ad.setEndTime(new Date(end_time));
		ad.setFilePath(audio_path);
	}

	private static void executeFfmpegAndSetVideoData(String cmd, VideoData vd, VideoData videoData, long start_time, long end_time, String video_path) {
		PlaybackUtil.invokeFfmpegProcess(cmd);
		vd.setStartTime(new Date(start_time));
		vd.setEndTime(new Date(end_time));
		vd.setFilePath(video_path);
		vd.setDuration(videoData.getDuration());
		vd.setWidth(videoData.getWidth());
		vd.setHeight(videoData.getHeight());
		vd.setRoomName(videoData.getRoomName());
		vd.setVideoType(videoData.getVideoType());
		vd.setId(videoData.getId());
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

	private static String updateFinalVideoTable(PlayBackPlayList meetingRoomVideo, PlayBackPlayListDao playBackPlayListDao) {
		log.debug("Inside updateFinalVideoTable ..............");
		String returnFileId = null;
		// ClassPathXmlApplicationContext appContext = new
		// ClassPathXmlApplicationContext(new String[] { "root-context.xml" });
		// of course, an ApplicationContext is just a BeanFactory
		// BeanFactory factory = (BeanFactory) appContext;

		// PlayBackPlayListDao sessionRecordingsDao = (PlayBackPlayListDao)
		// factory.getBean("playBackPlayListDao");
		returnFileId = playBackPlayListDao.savePlayBackPlayList(meetingRoomVideo);
		return returnFileId;
	}

}