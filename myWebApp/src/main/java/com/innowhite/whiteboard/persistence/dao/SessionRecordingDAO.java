package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.SessionRecordingDataVO;

public class SessionRecordingDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

    static boolean val = false;

    private static final Logger log = LoggerFactory.getLogger(SessionRecordingDAO.class);

    public static void main(String[] args) {
	//

	// VideoDataVO videoDataVO = new VideoDataVO();
	// videoDataVO.setFlvFilePath("/qwe/qweee/");
	// videoDataVO.setRoomName("asdasdasd");
	// int val = saveVideoData(videoDataVO);
	// System.err.println(getLatestWhiteboardRoomId("asdasdasd"));
	// updateVideoData(val);

	startSessionRecording("123123");
	endSessionRecording("123123");

    }

    /* saves the media content. */

    public static int startSessionRecording(String roomId) {

	int x = 0;
	try {
	    SessionRecordingDataVO obj = new SessionRecordingDataVO();
	    obj.setRoomId(roomId);
	    log.debug(" entered startSessionRecording  ");
	    x = (Integer) sqlMapClient.insert("startSessionRecording", obj);

	} catch (SQLException e) {

	    e.printStackTrace();
	}
	return x;
    }

    // /*saves the media content.*/
    //
    public static boolean endSessionRecording(String roomId) {
	boolean roomCreated = false;
	int x = 0;
	try {
	    log.debug("Entered updateVideoData id ::" + roomId);
	    x = (Integer) sqlMapClient.update("endSessionRecording", roomId);
	    log.debug(" saveMedia returned " + x);
	} catch (SQLException e) {

	    e.printStackTrace();
	}
	if (x > 0) {
	    return true;
	} else {
	    return roomCreated;
	}
    }

}
