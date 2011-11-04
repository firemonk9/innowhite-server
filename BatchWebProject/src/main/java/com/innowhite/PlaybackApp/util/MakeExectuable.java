package com.innowhite.PlaybackApp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MakeExectuable {

    // private static final Logger log = Logger.getLogger(MakeExectuable.class);
    // //Red5LoggerFactory.getLogger(MakeExectuable.class, "openmeetings");

    private static MakeExectuable instance;

    private static final Logger log = LoggerFactory.getLogger(MakeExectuable.class);

    private MakeExectuable() {
    }

    public static synchronized MakeExectuable getInstance() {
	if (instance == null) {
	    instance = new MakeExectuable();
	}
	return instance;
    }

    public String setExecutable(String fileName) {
	try {

	    // log.debug("setExecutable: "+fileName);

	    String[] cmd = new String[1];
	    cmd[0] = "chmod +x " + fileName;

	    Runtime rt = Runtime.getRuntime();
	    Process proc = rt.exec("chmod +x " + fileName);

	    InputStream stderr = proc.getErrorStream();
	    InputStreamReader isr = new InputStreamReader(stderr);
	    BufferedReader br = new BufferedReader(isr);
	    String line = null;
	    String error = "";
	    while ((line = br.readLine()) != null) {
		error += line;
		log.debug("line: " + line);
	    }
	    int exitVal = proc.waitFor();
	    if (exitVal != 0) {

		log.warn(" Some thing is not correct. could not set the file permission to execute  exitVal: " + exitVal);
	    }
	} catch (Exception err) {
	    err.printStackTrace();
	}
	return null;
    }

}
