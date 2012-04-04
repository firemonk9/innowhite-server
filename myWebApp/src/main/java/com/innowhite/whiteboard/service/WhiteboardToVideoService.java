package com.innowhite.whiteboard.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.vo.WhiteboardVideoVO;
import com.innowhite.whiteboard.persistence.beans.VideoDataVO;
import com.innowhite.whiteboard.persistence.dao.TempWhiteboardVideoDAO;
import com.innowhite.whiteboard.persistence.dao.VideoDataDAO;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.innowhite.whiteboard.util.InnowhiteProperties;

public class WhiteboardToVideoService {

	private static HashMap<String, WhiteboardVideoVO> roomFileMap = new HashMap<String, WhiteboardVideoVO>();
	private static final Logger log = LoggerFactory.getLogger(WhiteboardToVideoService.class);

	public static void writeToFile(byte[] data, String roomId, String firstPacket, String counter) {
		try {
			log.debug("entered writeToFile " + roomId+" counter: "+counter);
			String filePath=null;
			if (firstPacket != null && firstPacket.equals("true")) {
				log.debug("writing data to new file for the first time..." + data.length + "  the length is " + data);

				filePath = InnowhiteProperties.getPropertyVal(InnowhiteConstants.VIDEOS_PATH) + roomId + "_" + VideoDataDAO.getLatestWhiteboardRoomId(roomId) + ".flv";
				// os = new DataOutputStream(new FileOutputStream(filePath));

				VideoDataVO videoDataVO = new VideoDataVO();
				videoDataVO.setFlvFilePath(filePath);
				videoDataVO.setRoomName(roomId);
				int id = VideoDataDAO.saveVideoData(videoDataVO);
				WhiteboardVideoVO obj = new WhiteboardVideoVO(filePath, id);
				roomFileMap.put(roomId, obj);

			}else{
				
				WhiteboardVideoVO obj = (WhiteboardVideoVO) roomFileMap.get(roomId);
				filePath = obj.getFileId();
			}

			if (counter != null && counter.trim().length() > 0) {
				int val = Integer.parseInt(counter);
				TempWhiteboardVideoDAO.saveWBFrameData(roomId, val, data, filePath);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

//	public static void writeToFile1(byte[] data, String roomId, String firstPacket, String counter) {
//		try {
//			log.debug("entered writeToFile " + roomId);
//			DataOutputStream os = null;
//			if (firstPacket == null) {
//
//				if (roomId == null || roomFileMap.get(roomId) == null) {
//					log.warn(" screen recording trying to write to a closed file.... for room " + roomId);
//					return;
//				}
//
//				WhiteboardVideoVO obj = (WhiteboardVideoVO) roomFileMap.get(roomId);
//				os = obj.getOs();
//
//			} else if (firstPacket != null && firstPacket.equals("true")) {
//
//				log.debug("writing data to new file for the first time..." + data.length + "  the length is " + data);
//
//				String filePath = InnowhiteProperties.getPropertyVal(InnowhiteConstants.VIDEOS_PATH) + roomId + "_" + VideoDataDAO.getLatestWhiteboardRoomId(roomId) + ".flv";
//				os = new DataOutputStream(new FileOutputStream(filePath));
//
//				VideoDataVO videoDataVO = new VideoDataVO();
//				videoDataVO.setFlvFilePath(filePath);
//				videoDataVO.setRoomName(roomId);
//				int id = VideoDataDAO.saveVideoData(videoDataVO);
//				WhiteboardVideoVO obj = new WhiteboardVideoVO(os, id);
//				roomFileMap.put(roomId, obj);
//
//			}
//
//		} catch (IOException e) {
//
//			log.error(e.getMessage(), e);
//			e.printStackTrace();
//		}
//	}

	public static void stopRecording(String roomId) {
		log.debug("entered stopRecording for room " + roomId);

		try {
			if (roomId != null && roomFileMap.get(roomId) != null) {
				WhiteboardVideoVO obj = (WhiteboardVideoVO) roomFileMap.get(roomId);
				//DataOutputStream os = obj.getOs();
				//os.close();
				boolean val = VideoDataDAO.updateVideoData(obj.getId());
				if (val == false)
					log.error(" could not update database for update  ");
				roomFileMap.remove(roomId);
			}
		} catch (Exception e) {

			log.error(e.getMessage(), e);
			e.printStackTrace();
		}

	}

//	private static void orderFrames(byte[] data, String counter, DataOutputStream os, WhiteboardVideoVO obj) throws IOException {
//		Integer val = null;
//		if (counter != null && counter.trim().length() > 0) {
//			val = Integer.parseInt(counter);
//		}
//		if (val == obj.getNextFrameNum()) {
//			obj.setNextFrameNum(val + 1);
//			// log.debug("all good");
//			os.write(data);
//			// check if the next data is available in the list
//			while (true) {
//
//				byte[] mydata = obj.getFromMap(val + 1);
//				if (mydata != null) {
//					os.write(mydata);
//					val = val + 1;
//					obj.setNextFrameNum(val);
//				} else
//					break;
//			}
//			os.flush();
//		} else {
//
//		}
//
//	}

}
