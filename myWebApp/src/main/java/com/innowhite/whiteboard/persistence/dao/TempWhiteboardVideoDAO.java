package com.innowhite.whiteboard.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.innowhite.whiteboard.persistence.IbatisManager;
import com.innowhite.whiteboard.persistence.beans.WhiteboardFrameVO;

public class TempWhiteboardVideoDAO {

	private static SqlMapClient sqlMapClient = IbatisManager.getSqlMap();

	static boolean val = false;

	private static final Logger log = LoggerFactory.getLogger(TempWhiteboardVideoDAO.class);

	
	public static byte[] intToByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }
	
	public static void main(String[] args) {
		//TempWhiteboardVideoDAO a = new TempWhiteboardVideoDAO();
		byte[] a1 = new byte[4];
		a1 = intToByteArray(3);
		saveWBFrameData("123456", 1, a1,"2");
		saveWBFrameData("123456", 2, a1,"2");
		saveWBFrameData("123456", 3, a1,"2");
		saveWBFrameData("123456", 4, a1,"2");
		
		System.err.println(getTempWhiteboardFrames("1234"));
		
	}


	/*saves the media content.*/
	
	public static void saveWBFrameData(String roomId, int seq, byte[] data, String filePath) {
		
		int x = 0;
		try {
			log.debug(" entered saveWBFrameData roomId " + roomId+" filePath "+filePath+" seq "+seq);
			WhiteboardFrameVO obj = new WhiteboardFrameVO();
			obj.setData(data);
			obj.setSeq(seq);
			obj.setRoomId(roomId);
			obj.setFileId(filePath);
			sqlMapClient.insert("insertVideoBinaryData", obj);
			log.debug(" saveMedia returned " );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 * returns list of WhiteboardFrameVO.
	 */
	@SuppressWarnings("unchecked")
	public static List<WhiteboardFrameVO> getTempWhiteboardFrames(String roomName) {

		log.debug("Entered getTempWhiteboardFrames");
		
		List<WhiteboardFrameVO> value = null;
		try {

			value = (List) sqlMapClient.queryForList("selectVideoBinaryData", roomName);

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("  "+e.getMessage(),e);
		}
		return value;
	}


	
	
	
}
