package com.innowhite.whiteboard.service;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.vo.WhiteboardVideoVO;
import com.innowhite.whiteboard.persistence.beans.VideoDataVO;
import com.innowhite.whiteboard.persistence.dao.VideoDataDAO;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.innowhite.whiteboard.util.InnowhiteProperties;

public class WhiteboardToVideoService {

    private static HashMap<String, WhiteboardVideoVO> roomFileMap = new HashMap<String, WhiteboardVideoVO>();
    private static final Logger log = LoggerFactory.getLogger(WhiteboardToVideoService.class);

    public static void writeToFile(byte[] data, String roomId) {
	try {
	    log.debug("entered writeToFile " + roomId);
	    DataOutputStream os = null;
	    if (roomId != null && roomFileMap.get(roomId) != null) {
		WhiteboardVideoVO obj = (WhiteboardVideoVO) roomFileMap.get(roomId);
		os = obj.getOs();

	    } else if (roomFileMap.get(roomId) == null) {
		
		log.debug("writing data to new file for the first time..."+data.length+"  the length is "+data);
		
		String filePath = InnowhiteProperties.getPropertyVal(InnowhiteConstants.VIDEOS_PATH) + roomId + "_" + VideoDataDAO.getLatestWhiteboardRoomId(roomId) + ".flv";
		os = new DataOutputStream(new FileOutputStream(filePath));

		VideoDataVO videoDataVO = new VideoDataVO();
		videoDataVO.setFlvFilePath(filePath);
		videoDataVO.setRoomName(roomId);
		int id = VideoDataDAO.saveVideoData(videoDataVO);
		WhiteboardVideoVO obj = new WhiteboardVideoVO(os, id);
		roomFileMap.put(roomId, obj);

	    }
	    os.write(data);
	    os.flush();
	} catch (IOException e) {
	    
	    log.error(e.getMessage(), e);
	    e.printStackTrace();
	}
    }

    public static void stopRecording(String roomId) {
	log.debug("entered stopRecording ");

	try {
	    if (roomId != null && roomFileMap.get(roomId) != null) {
		WhiteboardVideoVO obj = (WhiteboardVideoVO) roomFileMap.get(roomId);
		DataOutputStream os = obj.getOs();
		os.close();
		boolean val = VideoDataDAO.updateVideoData(obj.getId());
		if(val == false)
		    log.error(" could not update database for update  ");
		roomFileMap.remove(roomId);
	    }
	} catch (IOException e) {

	    log.error(e.getMessage(), e);
	    e.printStackTrace();
	}

    }

}
