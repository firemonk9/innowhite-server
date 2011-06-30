package com.innowhite.whiteboard.service;

import java.util.List;

import com.innowhite.whiteboard.persistence.beans.PlayBackPlayListVO;
import com.innowhite.whiteboard.persistence.dao.PlayBackPlayListDAO;

public class PlayBackPlayListService {

    public static void main(String args[]) {
	System.err.println(getPlayListXML("Dhiraj4"));
    }

    public static String getPlayListXML(String roomId) {

	List<PlayBackPlayListVO> list = PlayBackPlayListDAO.getPlayList(roomId);
	StringBuffer sb = new StringBuffer();
	
	for (PlayBackPlayListVO playBackPlayListVO : list) {
	    	sb.append("<url>"+playBackPlayListVO.getFilePath()+"</url>");
	    	sb.append("<duration>"+playBackPlayListVO.getDuration()+"</duration>");
	}

	return sb.toString();
    }

}
