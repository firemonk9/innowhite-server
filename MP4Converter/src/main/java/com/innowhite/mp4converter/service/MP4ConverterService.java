/*To implement MP4Converter service class */
package com.innowhite.mp4converter.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.mp4converter.dao.MP4ConverterDAO;
import com.innowhite.mp4converter.model.FFMPEGInfoVO;
import com.innowhite.mp4converter.model.PlayBackPlayList;
import com.innowhite.mp4converter.util.ConverterUtil;

/**
 * @author Tanuja
 * @Date  Feb 25,2012
 */

public class MP4ConverterService {
	
	private static final Logger log = LoggerFactory.getLogger(MP4ConverterService.class);
	
	private MP4ConverterDAO mp4ConverterDAO = null;	
	private FFMPEGInfoVO ffmpegInfoVO = null;
	
	public MP4ConverterDAO getMp4ConverterDAO() {
		return mp4ConverterDAO;
	}

	public void setMp4ConverterDAO(MP4ConverterDAO mp4ConverterDAO) {
		this.mp4ConverterDAO = mp4ConverterDAO;
	}

	public FFMPEGInfoVO getFfmpegInfoVO() {
		return ffmpegInfoVO;
	}

	public void setFfmpegInfoVO(FFMPEGInfoVO ffmpegInfoVO) {
		this.ffmpegInfoVO = ffmpegInfoVO;
	}


	
	public void processFLVFile(String inputFilePath, String fileId, String winPath){
		log.debug("entered processFLVFile");
		if(ConverterUtil.isWindows()==true){
			convertToMp4(inputFilePath, fileId);
		}
	}
		
	public void convertToMp4(String inputFLVPath, String fileId){
		log.debug("entered convertToMp4");
			
		try{
			//inputFLVPath="C:/FLVVideoFiles/test.flv";
			//String ffmpegPath = "C:\\Program Files\\ffmpeg-git-b6ff81d-win32-static\\bin\\ffmpeg.exe";
			String outMp4FilePath = inputFLVPath.replace(".flv", ".mp4");
				
			String ffmpegPath=ffmpegInfoVO.getWinFFmpegPath();
										
			File myFile = new File(inputFLVPath);
				
			if (myFile.exists()) {
					
				String command = ffmpegPath+" -i "+inputFLVPath+" "+outMp4FilePath;
				
				System.out.println("Before dispatching command...");
						
				Process proc = Runtime.getRuntime().exec(command);
				System.out.println("Exit:");
				
			}else{
				System.out.println("There is no file to convert");
			}
			
			updateFinalVideoTable(fileId, outMp4FilePath);
				
		}catch(Exception e){
			e.printStackTrace();
		}
		log.debug("leaving convertToMp4  ");
		
	}
	
	
	public void updateFinalVideoTable(String strFileId, String mp4FilePath){
		log.debug("entered updateFinalVideoTable");
			
		PlayBackPlayList playBackObj = new PlayBackPlayList();
		playBackObj.setDownloadUrl(mp4FilePath);
		playBackObj.setId(strFileId);
		
		mp4ConverterDAO.updateMp4FilePath(playBackObj);
		log.debug("leaving updateFinalVideoTable  ");
	}

}



