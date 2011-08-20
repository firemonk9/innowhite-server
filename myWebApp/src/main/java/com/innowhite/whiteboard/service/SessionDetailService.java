package com.innowhite.whiteboard.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.AudioDataVO;
import com.innowhite.whiteboard.persistence.beans.PlayBackPlayListVO;
import com.innowhite.whiteboard.persistence.beans.RoomUsersVO;
import com.innowhite.whiteboard.persistence.beans.RoomVO;
import com.innowhite.whiteboard.persistence.beans.VideoDataVO;
import com.innowhite.whiteboard.persistence.dao.AudioDataDAO;
import com.innowhite.whiteboard.persistence.dao.PlayBackPlayListDAO;
import com.innowhite.whiteboard.persistence.dao.RoomUsersDAO;
import com.innowhite.whiteboard.persistence.dao.VideoDataDAO;

public class SessionDetailService {

    private static final Logger log = LoggerFactory.getLogger(SessionDetailService.class);

    public static void main(String args[]) {

	log.debug(getSessionDetail("84461721822"));
    }

    /*
     * This function returns the XML of each session.
     */
    public static String getSessionDetail(String roomId) {

	log.debug("entered getSessionDetail ");
	// get room start and end time.
	RoomVO room = RoomUsersDAO.getRoomInfo(roomId);
	log.debug("  users size ::  " + room);
	String returnXML = null;
	if (room != null) {
	    // get list of videos, Screen share, for this session.
	    List<VideoDataVO> videos = VideoDataDAO.getVideosRoomId(roomId);
	    log.debug(" videos size ::  " + videos.size());

	    // get list of users who have connected to this room, also if they
	    // joined by phone, voip or not joined
	    List<RoomUsersVO> users = RoomUsersDAO.getUsersForRoom(roomId);
	    log.debug("  users size ::  " + users.size());

	    // get list of audio start and end time...
	    List<AudioDataVO> audios = AudioDataDAO.getAudiosRoomId(roomId);
	    log.debug("  users size ::  " + audios.size());
	    // getAudiosRoomId

	    // get list of playback play list
	    List<PlayBackPlayListVO> playList = PlayBackPlayListDAO.getPlayList(roomId);
	    log.debug("  users size ::  " + playList.size());

	    returnXML = convertToXML(roomId, videos, users, audios, room, playList);

	} else {
	    StringBuffer xml = new StringBuffer();
	    xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
	    xml.append("<SessionDetail>");
	    xml.append("<roomId>" + roomId + "</roomId>");
	    xml.append("<status>INVALID_ROOM_ID</status>");
	    xml.append("</SessionDetail>");
	}
	return returnXML;
    }

    private static String convertToXML(String roomId, List<VideoDataVO> videos, List<RoomUsersVO> users, List<AudioDataVO> audios, RoomVO room, List<PlayBackPlayListVO> playList) {

	StringBuffer xml = new StringBuffer();
	xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
	xml.append("<SessionDetail>");
	xml.append("<roomId>" + roomId + "</roomId>");
	xml.append("<sessionStartTime>" + room.getStartDate() + "</sessionStartTime>");
	xml.append("<sessionEndTime>" + room.getEndDate() + "</sessionEndTime>");

	if (playList != null)
	    xml.append("<sessionRecording>" + true + "</sessionRecording>");
	else
	    xml.append("<sessionRecording>" + false + "</sessionRecording>");

	
	// list all videos
	xml.append("<sessionUsers>");
	for (RoomUsersVO user : users) {
	   

		xml.append("<user>");
		xml.append("<userId>" + user.getUserId() + "</userId>");
		xml.append("<userStartTime>" + user.getStartTime() + "</userStartTime>");
		xml.append("<userEndTime>" + user.getStartTime() + "</userEndTime>");
		xml.append("<userJoinedVOIPConf>" + user.isJoinedVoipConference() + "</userJoinedVOIPConf>");
		xml.append("<userJoinedPhoneConf>" + user.isJoinedPhoneConference() + "</userJoinedPhoneConf>");
		xml.append("</user>");
	   
	}
	xml.append("</sessionUsers>");
	
	// list all videos
	xml.append("<sessionVideos>");
	for (VideoDataVO video : videos) {
	    if (video.getVideoType() != null && video.getVideoType().equals("VIDEO")) {

		xml.append("<video>");
		xml.append("<videoUser>" + video.getUserId() + "</videoUser>");
		xml.append("<videoStartTime>" + video.getStartTime() + "</videoStartTime>");
		xml.append("<videoEndTime>" + video.getStartTime() + "</videoEndTime>");
		xml.append("<videoServer>" + video.getId() + "</videoServer>");
		xml.append("</video>");
	    }
	}
	xml.append("</sessionVideos>");

	// list all screen shares
	xml.append("<sessionScreenShares>");
	for (VideoDataVO video : videos) {
	    if (video.getVideoType() != null && video.getVideoType().equals("DESKTOP")) {
		xml.append("<screenShare>");
		xml.append("<screenShareUser>" + video.getUserId() + "</screenShareUser>");
		xml.append("<screenShareStartTime>" + video.getStartTime() + "</screenShareStartTime>");
		xml.append("<screenShareEndTime>" + video.getStartTime() + "</screenShareEndTime>");
		xml.append("<screenShareServer>" + video.getId() + "</screenShareServer>");
		xml.append("</screenShare>");
	    }
	}
	xml.append("</sessionScreenShares>");

	// list all audios
	xml.append("<sessionAudios>");
	for (AudioDataVO audio : audios) {
	    xml.append("<audio>");
	    xml.append("<audioStartTime>" + audio.getStartTime() + "</audioStartTime>");
	    xml.append("<audioEndTime>" + audio.getStartTime() + "</audioEndTime>");
	    xml.append("</audio>");
	}
	xml.append("</sessionAudios>");

	// list all play list
	xml.append("<playList>");
	for (PlayBackPlayListVO playListItem : playList) {
	    xml.append("<playBackVideo>");
	    xml.append("<playBackDuration>" + playListItem.getDuration() + "</playBackDuration>");
	    xml.append("<playBackFilePath>" + playListItem.getFilePath() + "</playBackFilePath>");
	    xml.append("<playBackServer>" + playListItem.getServer() + "</playBackServer>");
	    xml.append("<playBackSize>" + playListItem.getSize() + "</playBackSize>");
	    xml.append("</playBackVideo>");
	}
	xml.append("</playList>");

	xml.append("</SessionDetail>");

	return xml.toString();

    }

}
