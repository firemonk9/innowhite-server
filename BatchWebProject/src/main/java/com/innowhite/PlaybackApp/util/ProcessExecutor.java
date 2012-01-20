package com.innowhite.PlaybackApp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessExecutor {

	public static final Logger log = LoggerFactory
			.getLogger(ProcessExecutor.class);

	public boolean executeProcess(String cmd, String tempPath,
			HashMap<String, String> videohm, boolean printLog) {

		try {
			// String cmd = executable + " -i " + input + " " + params + " " +
			// output;
			// log.debug(cmd);
			// System.out.println("cmd from process executor::"+cmd);
			if (PlaybackUtil.isWindows() == false) {
				File f = new File(tempPath + "/file_" + Math.random() * 10000
						+ ".sh");
				FileWriter fw = new FileWriter(f);
				fw.write("#!/bin/bash \n");
				fw.write(cmd + "\n");
				fw.close();
				cmd = f.getAbsolutePath();

				MakeExectuable.getInstance().setExecutable(cmd);
			}

			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd);

			// any error message?
			StreamGobbler errorGobbler = new StreamGobbler(
					proc.getErrorStream(), "ERR", videohm, printLog);

			// any output?
			StreamGobbler outputGobbler = new StreamGobbler(
					proc.getInputStream(), "OUT", videohm, printLog);

			// Start the threads
			errorGobbler.start();
			outputGobbler.start();

			// any error???
			// Appears to be blocking operation
			int exitVal = proc.waitFor();
			// log.debug("ExitValue: " + exitVal);

			// TO DO -- This is a temporary fix... need to find a permanent
			// solution.
			while (true) {
				if (errorGobbler.isAlive() || outputGobbler.isAlive()) {
					if (printLog)
						log.debug(" The threads which read the data from the terminal are still alive --------so sleeping for 10 seconds...  ");
					Thread.sleep(2000);
				} else {
					break;
				}
			}

			if (exitVal == 0) {
				return true;
			}

		} catch (Exception t) {
			t.printStackTrace();
			log.error(t.getMessage(), t);
		}

		return false;
	}
}

// Consume process output
class StreamGobbler extends Thread {
	Logger log = LoggerFactory.getLogger(StreamGobbler.class);
	InputStream is;
	String type;
	// PlayBackPlayList pbp;
	HashMap<String, String> videohm;
	boolean printLog = false;

	StreamGobbler(InputStream is, String type,
			HashMap<String, String> videohmin, boolean printLog) {
		this.is = is;
		this.type = type;
		this.videohm = videohmin;
		this.printLog = printLog;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			if (this.videohm != null) {

				// log.debug(" starting sleep for the thred ... ");
				// Thread.sleep(10000);
				// log.debug(" Thread woke up");

				while ((line = br.readLine()) != null) {
					// log.debug(" line ::::---- "+line);

					if (line.contains("duration")) {
						getSubstr(line, "duration", this.videohm);
					} else if (line.contains("width")) {
						getSubstr(line, "width", this.videohm);
					} else if (line.contains("height")) {
						getSubstr(line, "height", this.videohm);
					} else if (line.contains("filesize")) {
						getSubstr(line, "filesize", this.videohm);
					} else if (line.contains("Stream")) {
						getSubstr2(line, "dimensions", this.videohm);
					} else if(line.contains("Duration")) {
						getSubstr3(line, "Duration", this.videohm);
					}
				}
				if (this.videohm.get("width") == null
						|| this.videohm.get("height") == null
						|| this.videohm.get("width") == ""
						|| this.videohm.get("height") == "") {
					log.debug("could not get width and heght with ffmpeg...");
					if (this.videohm.get("dimensions") != null) {
						String dim[] = this.videohm.get("dimensions")
								.split("x");
						this.videohm.put("width", dim[0]);
						this.videohm.put("height", dim[1]);
						log.debug("since width and height are null.. setting them through Stream's dimensions");
					} else {
						log.warn("even dimensions is null... somethings wrong!");
					}
				}

			}
			// Show output in development
			if (printLog)
				log.debug(type + ">" + line);
		} catch (Exception ioe) {
			log.error("" + ioe.getMessage(), ioe);
			ioe.printStackTrace();
		}
	}

	public void getSubstr(String line, String val,HashMap<String, String> videohm) {
		String temp[] = line.split(":");
		if (temp.length == 2) {
			if (temp[1] != null && PlaybackUtil.getNumLong(temp[1]) > 0) {
				if (printLog)
					log.debug("-->Inside ProcessExecutor.. " + val + "="+ temp[1].trim());
				videohm.put(val, temp[1].trim());
			}
			// if (val != null)
			// this.videohm.put("size", val);
		}
	}

	public void getSubstr2(String line, String val,HashMap<String, String> videohm) {
		String temp[] = line.split(",");
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] != null && Pattern.matches("[\\d]+[x][\\d]+", temp[i].trim())) {
				if (printLog)
					log.debug("-->Inside ProcessExecutor.. " + val + "="+ temp[i].trim());
				videohm.put(val, temp[i].trim());
			}
		}
	}
	
	public void getSubstr3(String line, String val,HashMap<String, String> videohm) {
		String temp[] = line.split(",");
		String Duration=null;
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] != null) {
				if(temp[i].contains("Duration:")){
					Duration = temp[i].trim().substring(9).trim();
				}
				if (printLog)
					log.debug("-->Inside ProcessExecutor.. " + val + "="+ Duration);
				videohm.put(val, Duration);
			}
		}
	}
}