/*To implement MP4Converter service class */
package com.innowhite.mp4converter.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.mp4converter.dao.MP4ConverterDAO;
import com.innowhite.mp4converter.model.FFMPEGInfoVO;
import com.innowhite.mp4converter.model.PlayBackPlayList;
import com.innowhite.mp4converter.util.ConverterUtil;
import com.innowhite.mp4converter.util.ProcessExecutor;

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


	
	public void processFLVFile(String unixFLVPath, String fileId){
		log.debug("entered processFLVFile"+ "====" +fileId+ "====" +  unixFLVPath);
		
		String winFilePath =  ffmpegInfoVO.getWinFilePath();
		String fileName = unixFLVPath.substring(unixFLVPath.lastIndexOf("/")+1);    // test.flv
		String inputFLVPath=winFilePath + fileName;									// Z:/videos/ + test.flv
		if(ConverterUtil.isWindows()==true){
			convertToMp4(inputFLVPath, fileId, unixFLVPath);
			convertToWebM(inputFLVPath, fileId, unixFLVPath);
		}
	}
		
	public void convertToMp4(String inputFLVPath, String fileId, String unixFLVPath){
		log.debug("entered convertToMp4======"+inputFLVPath);
			
		try{
			//inputFLVPath="C:/FLVVideoFiles/test.flv";
			//String ffmpegPath = "C:\\ffmpeg-git-b6ff81d-win32-static\\bin\\ffmpeg.exe";
			
			String outMp4FilePath = inputFLVPath.replace(".flv", ".mp4");
			String ffmpegPath=ffmpegInfoVO.getWinFFmpegPath();
			
			File myFile = new File(inputFLVPath);
				
			if (myFile.exists()) {
					
				String command = ffmpegPath+" -i "+inputFLVPath+" "+outMp4FilePath;
				
				log.debug("Before dispatching command...");
				
				
				ProcessExecutor pe = new ProcessExecutor();
				pe.executeProcess(command, null, true);
				//Process proc = Runtime.getRuntime().exec(command);
				log.debug("Exit:");
				
				updateFinalVideoTable(fileId, outMp4FilePath, unixFLVPath, true);	
					
			}else{
				log.warn("----There is no file to convert-------"+inputFLVPath);
			}
			
			
				
		}catch(Exception e){
			log.error(""+e.getMessage(),e);
			e.printStackTrace();
		}
		log.debug("leaving convertToMp4  ");
		
	}
	
	public void convertToWebM(String inputFLVPath, String fileId, String unixFLVPath){
		log.debug("entered convertToWebM======"+inputFLVPath);
			
		try{
			//inputFLVPath="C:/FLVVideoFiles/test.flv";
			//String ffmpegPath = "C:\\ffmpeg-git-b6ff81d-win32-static\\bin\\ffmpeg.exe";
			
			String outMp4FilePath = inputFLVPath.replace(".flv", ".webm");
			String ffmpegPath=ffmpegInfoVO.getWinFFmpegPath();
			
			File myFile = new File(inputFLVPath);
				
			if (myFile.exists()) {
					
				String command = ffmpegPath+" -i "+inputFLVPath+" "+outMp4FilePath;
				
				log.debug("Before dispatching command...");
				
				
				ProcessExecutor pe = new ProcessExecutor();
				pe.executeProcess(command, null, true);
				//Process proc = Runtime.getRuntime().exec(command);
				log.debug("Exit:");
				
				updateFinalVideoTable(fileId, outMp4FilePath, unixFLVPath, false);	
					
			}else{
				log.warn("----There is no file to convert-------"+inputFLVPath);
			}
			
			
				
		}catch(Exception e){
			e.printStackTrace();
		}
		log.debug("leaving convertToMp4  ");
		
	}
	
	
	
	public void updateFinalVideoTable(String strFileId, String mp4FilePath, String unixFLVPath, boolean mp4 ){
		log.debug("entered updateFinalVideoTable");
		try{
			
			String finalPath = unixFLVPath.substring(0,unixFLVPath.lastIndexOf("/"));
			String fileName = mp4FilePath.substring(mp4FilePath.lastIndexOf("/")); 
			String unixMP4Path = finalPath + fileName;						
			
			
			PlayBackPlayList playBackObj = new PlayBackPlayList();
			if(mp4 == true)
				playBackObj.setMp4Path(unixMP4Path);
			else
				playBackObj.setWebmPath(unixMP4Path);
			
			playBackObj.setId(Long.parseLong(strFileId));
			
			mp4ConverterDAO.updateMp4FilePath(playBackObj);
			
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}
		log.debug("leaving updateFinalVideoTable  ");
	}

}



