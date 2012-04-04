package com.innowhite.PlaybackApp.service;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.dao.WhiteboardFrameVideoDao;
import com.innowhite.PlaybackApp.model.WhiteboardVideoFrame;

public class CreateWhiteboardVideoFilesService {

	private static final Logger log = LoggerFactory.getLogger(CreateWhiteboardVideoFilesService.class);

	WhiteboardFrameVideoDao whiteboardFrameVideoDao;

	

	public void setWhiteboardFrameVideoDao(WhiteboardFrameVideoDao whiteboardFrameVideoDao) {
		this.whiteboardFrameVideoDao = whiteboardFrameVideoDao;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// notifyPlayBackReady("993048460", "");

	}

	public boolean createFiles(String roomId) throws FileNotFoundException {
		
		log.debug(" entered  createFiles");
		List<WhiteboardVideoFrame> framesList = whiteboardFrameVideoDao.getFramesList(roomId);
		String path = "";
		DataOutputStream os = null;

		try {
			for (WhiteboardVideoFrame obj : framesList) {

				if (obj.getFileId() != null && obj.getFileId().equals(path)) {
					os.write(obj.getData());
				} else {

					if (os != null) {
						os.close();
						log.debug(" closing  file:: "+path);
					}
					log.debug(" writing new file :: "+obj.getFileId());
					os = new DataOutputStream(new FileOutputStream(obj.getFileId()));
					os.write(obj.getData());
					path = obj.getFileId();

				}

			}
			if (os != null) {
				os.close();
			}
		} catch (IOException e) {
			log.error(" error : " + e.getMessage(), e);
			e.printStackTrace();
			return false;
		}
		whiteboardFrameVideoDao.deleteRoomData(roomId);
		return true;
	}

}
