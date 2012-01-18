package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.AudioDataVO;
import com.innowhite.whiteboard.persistence.beans.VideoDataVO;

public class AudioDataDAO {

    private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

    static boolean val = false;

    private static final Logger log = LoggerFactory.getLogger(AudioDataDAO.class);

    public static void main(String[] args) {
	//

	VideoDataVO videoDataVO = new VideoDataVO();
	videoDataVO.setFlvFilePath("/qwe/qweee/");
	videoDataVO.setRoomName("asdasdasd");
//	int val = saveVideoData(videoDataVO);
//	System.err.println(getLatestWhiteboardRoomId("asdasdasd"));
//	updateVideoData(val);
    }

    
    /*returns list of VideoDataVO. 
     * */
    public static void updateAudiosEndTime(String roomName) {

	log.debug("Entered updateAudiosEndTime");
	log.info("Entered updateAudiosEndTime roomName  " + roomName);
	List<AudioDataVO> value = null;
	try {

	  int i = sqlMapClient.update("updateAudioEndTime", roomName);
	  log.debug("updating "+i+"  audio records.");
	  
	} catch (SQLException e) {
	    e.printStackTrace();
	    
	}
	

    }

    public static boolean audioConfIsOn(String roomId){
	log.debug("Entered audioConfIsOn");
	log.debug("roomName  " + roomId);
	List<AudioDataVO> value = null;
	try {

	    value = (List) sqlMapClient.queryForList("audioConfIsOnSql", roomId);
	    if(value != null && value.size() > 0)
		return true;
	    else
		return false;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
	
    }
    
    
    /*returns list of VideoDataVO. 
     * */
    public static List<AudioDataVO> getAudiosRoomId(String roomName) {

	log.debug("Entered getVideosRoomId");
	log.debug("roomName  " + roomName);
	List<AudioDataVO> value = null;
	try {

	    value = (List) sqlMapClient.queryForList("getAudiosForRoom", roomName);

	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
	return value;

    }

}
