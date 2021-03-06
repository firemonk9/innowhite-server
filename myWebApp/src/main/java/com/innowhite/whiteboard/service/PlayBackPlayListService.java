package com.innowhite.whiteboard.service;

import java.util.List;

import com.innowhite.whiteboard.persistence.beans.PlayBackPlayListVO;
import com.innowhite.whiteboard.persistence.dao.PlayBackPlayListDAO;

public class PlayBackPlayListService {

	public static void main(String args[]) {
		// System.err.println(getPlayListXML("Dhiraj4"));
		System.err.println(getFilePath("/opt/InnowhiteData/videos/826913489_0_1288434469_v_bucket_overlay_avi_session1_meeting.webm"));
		
	}

	public static String getPlayListXML(String roomId) {

		List<PlayBackPlayListVO> list = PlayBackPlayListDAO.getPlayList(roomId);
		StringBuffer sb = new StringBuffer();

		for (PlayBackPlayListVO playBackPlayListVO : list) {
			sb.append("<videoSrc>");
			sb.append("<url>" + getServerURL(playBackPlayListVO.getFilePath()) + "</url>");
			sb.append("<duration>" + playBackPlayListVO.getDuration() + "</duration>");
			sb.append("<server>" + playBackPlayListVO.getServer() + "</server>");
			sb.append("<size>" + playBackPlayListVO.getSize() + "</size>");
			sb.append("<width>" + playBackPlayListVO.getWidth() + "</width>");
			sb.append("<height>" + playBackPlayListVO.getHeight() + "</height>");
			sb.append("<youtube>" + playBackPlayListVO.getYoutubeUrl() + "</youtube>");
			sb.append("<status>" + playBackPlayListVO.getStatus() + "</status>");			
			sb.append("<mp4path>" + getFilePath(playBackPlayListVO.getMp4Path()) + "</mp4path>");
			sb.append("<webMpath>" + getFilePath(playBackPlayListVO.getWebmPath()) + "</webMpath>");
			sb.append("</videoSrc>");
		}

		return sb.toString();
	}

	private static String getFilePath(String path) {
		if(path != null){
			if(path.indexOf("/") > -1){
				return path.substring(path.lastIndexOf("/")+1);
			}
		}
		return null;
	}

	private static String getServerURL(String absPath) {

		if (absPath != null) {
			absPath = absPath.substring(absPath.lastIndexOf("/") + 1);

			if (absPath.endsWith("mp4")) {
				absPath = "mp4:" + absPath;
			}
			return absPath;
		}
		return null;
	}

}
