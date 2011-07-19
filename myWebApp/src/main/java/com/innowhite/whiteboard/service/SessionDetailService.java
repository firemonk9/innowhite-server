package com.innowhite.whiteboard.service;

import java.util.List;

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
    
    
    public static void main(String args[]){
	
	getSessionDetail("room1");
    }
    
    
    /*This function returns the XML of each session.
     * */
    public static String getSessionDetail(String roomId){
	
	
	// get list of videos, Screen share, for this session. 
	List<VideoDataVO> l = VideoDataDAO.getVideosRoomId(roomId);
	System.err.println(" videos size ::  "+l.size());
	
	// get list of users who have connected to this room, also if they joined by phone, voip or not joined
	List<RoomUsersVO> users = RoomUsersDAO.getUsersForRoom(roomId);
	System.err.println("  users size ::  "+users.size());
	
	// get list of audio start and end time...
	List<AudioDataVO> audios = AudioDataDAO.getAudiosRoomId(roomId);
	System.err.println("  users size ::  "+audios.size());
	//getAudiosRoomId
	
	// get room start and end time.
	RoomVO room = RoomUsersDAO.getRoomInfo(roomId);
	System.err.println("  users size ::  "+room);
	
	// get list of playback play list
	List<PlayBackPlayListVO> playList = PlayBackPlayListDAO.getPlayList(roomId);
	System.err.println("  users size ::  "+playList.size());
	
	
	
	
	
	
	return null;
    }

}
