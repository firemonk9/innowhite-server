package com.innowhite.PlaybackApp.service;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.model.VideoData;
import com.innowhite.PlaybackApp.util.PlaybackUtil;
import com.innowhite.PlaybackApp.util.PlaybackVO;
import com.innowhite.PlaybackApp.util.ProcessExecutor;

public class PreProcessFLV {

	private static final Logger log = LoggerFactory.getLogger(PreProcessFLV.class);

	public PreProcessFLV() {

	}

	public static void main(String[] args) {
		new PreProcessFLV();
	}

	public static void processFLV(List<VideoData> videoDataList, PlaybackVO playbackVO) {

		for (VideoData vData : videoDataList) {

			String flvPath = vData.getFilePath();

			// if (vData.getVideoType() != null &&
			// vData.getVideoType().equals("WHITEBOARD")) {
			// convertToAVIAndBackToFlv(flvPath);
			// }

			File myFile = new File(flvPath);
			String outPutfile = null;
			if (myFile.exists()) {
				outPutfile = flvPath.replace(myFile.getName(), myFile.getName());
				vData.setFilePath(outPutfile);
			}
			long duration = (vData.getEndTime().getTime() - vData.getStartTime().getTime());

			String command = "ruby /opt/InnowhiteData/scripts/Transcoder/transcoder.rb " + flvPath + " " + vData.getVideoType() + " " + duration;

			ProcessExecutor pe = new ProcessExecutor();
			// MakeExectuable obj = new MakeExectu

			boolean val = pe.executeProcess(command, playbackVO.getTempLocation(), null, true);

			log.debug(" the script that is  exeucted  ::" + command + " and the return val is " + val);

			command = "flvtool2 -U " + outPutfile;

			val = pe.executeProcess(command, playbackVO.getTempLocation(), null, true);

			log.debug(" the script that is  exeucted  ::" + command + " and the return val is " + val);

		}

	}

	private static void convertToAVIAndBackToFlv(String path) {

		try {

			// log.debug(" Temporary fix for whiteboard video size problem. Need to find better solution ");
			// String cmd = " -i " + path + "  -y " + path.replace("flv",
			// "avi");
			// PlaybackUtil.invokeFfmpegProcess(cmd);
			//
			// cmd = " -i " + path.replace("flv", "avi") + " -y " + path;
			// PlaybackUtil.invokeFfmpegProcess(cmd);

		} catch (Exception e) {
			log.error(" error in  convertToAVIAndBackToFlv :: " + e.getMessage(), e);
		}
	}

}
