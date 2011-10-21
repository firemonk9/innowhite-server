package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.VideoDataVO;

public class VideoDataDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

    static boolean val = false;

    private static final Logger log = LoggerFactory.getLogger(VideoDataDAO.class);

    public static void main(String[] args) {
	//

	VideoDataVO videoDataVO = new VideoDataVO();
	videoDataVO.setFlvFilePath("/qwe/qweee/");
	videoDataVO.setRoomName("asdasdasd");
	int val = saveVideoData(videoDataVO);
	System.err.println(getLatestWhiteboardRoomId("asdasdasd"));
	updateVideoData(val);
    }

    /* saves the media content. */

    public static int saveVideoData(VideoDataVO videoDataVO) {

	int x = 0;
	try {
	    log.debug(" entered saveVideoData  ");
	    x = (Integer) sqlMapClient.insert("saveVideoData", videoDataVO);

	} catch (SQLException e) {
	    log.error(e.getMessage(),e);
	    e.printStackTrace();
	}
	return x;
    }

    // /*saves the media content.*/
    //
    public static boolean updateVideoData(int id) {
	boolean roomCreated = false;
	int x = 0;
	try {
	    log.debug("Entered updateVideoData id ::" + id);
	    x = (Integer) sqlMapClient.update("updateVideoData", id);
	    log.debug(" saveMedia returned " + x);
	} catch (SQLException e) {
	    log.error(e.getMessage(),e);
	    e.printStackTrace();
	}
	if (x > 0) {
	    return true;
	} else {
	    return roomCreated;
	}
    }

    public static Integer getLatestWhiteboardRoomId(String roomName) {

	log.debug("Entered getLatestWhiteboardRoomId");
	log.debug("roomName  " + roomName);
	Integer value = null;
	try {

	    value = (Integer) sqlMapClient.queryForObject("getLatestWhitebordId", roomName);
	    if (value == null)
		value = 0;

	} catch (SQLException e) {
	    log.error(e.getMessage(),e);
	    e.printStackTrace();
	    return null;
	}
	return value;

    }

    
    /*returns list of VideoDataVO. 
     * */
    public static List<VideoDataVO> getVideosRoomId(String roomName) {

	log.debug("Entered getVideosRoomId");
	log.debug("roomName  " + roomName);
	List<VideoDataVO> value = null;
	try {

	    value = (List) sqlMapClient.queryForList("getVideosForRoom", roomName);

	} catch (SQLException e) {
	    log.error(e.getMessage(),e);
	    e.printStackTrace();
	    return null;
	}
	return value;

    }

}
