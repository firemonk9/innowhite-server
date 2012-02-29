/*To implement MP4Converter service class */
package com.innowhite.mp4converter.service;

import java.io.File;

import com.innowhite.mp4converter.dao.MP4ConverterDAO;
import com.innowhite.mp4converter.model.FFMPEGInfoVO;
import com.innowhite.mp4converter.util.ConverterUtil;

/**
 * @author Tanuja
 * @Date  Feb 25,2012
 */

public class MP4ConverterService {

	private MP4ConverterDAO mp4COnverterDAO = null;	
	private FFMPEGInfoVO ffmpegInfoVO = null;
	
	public MP4ConverterDAO getmp4ConverterDAO() {
		return mp4COnverterDAO;
	}

	public void setmp4ConverterDAO(MP4ConverterDAO mp4COnverterDAO) {
		this.mp4COnverterDAO = mp4COnverterDAO;
	}

	public FFMPEGInfoVO getffmpegInfoVO() {
		return ffmpegInfoVO;
	}

	public void setffmpegInfoVO(FFMPEGInfoVO playbackVO) {
		this.ffmpegInfoVO = ffmpegInfoVO;
	}	
	
	/*public static void main(String args[]){
			
			//convertToMp4();
	}*/
		
	public void processFLVFile(String inputFilePath, String fileId, String winPath){
		
		if(ConverterUtil.isWindows()==true){
			convertToMp4(inputFilePath);
		}
			
	}
		
	public void convertToMp4(String inputFLVPath){
			
		try{
			
			//inputFLVPath="C:/FLVVideoFiles/test.flv";
			//String ffmpegPath = "C:\\Program Files\\ffmpeg-git-b6ff81d-win32-static\\bin\\ffmpeg.exe";
				
			String ffmpegPath=ffmpegInfoVO.getWinFFmpegPath();
			String tempFilePath = "C:/FLVVideoFiles/test.mp4";
										
			File myFile = new File(inputFLVPath);
				
			if (myFile.exists()) {
					
				String command = ffmpegPath+" -i "+inputFLVPath+" C:/FLVVideoFiles/test.mp4";
				
				System.out.println("Before dispatching command...");
						
				Process proc = Runtime.getRuntime().exec(command);
				System.out.println("Exit:");
				
			}else{
				System.out.println("There is no file to convert");
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}



