package com.innowhite.PlaybackApp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.service.PlaybackDataService;

public class ProcessExecutor {

    public static final Logger log = LoggerFactory.getLogger(ProcessExecutor.class);

    public boolean executeProcess(String cmd, String tempPath) {

	try {
	    // String cmd = executable + " -i " + input + " " + params + " " +
	    // output;
	    log.debug(cmd);

	    if (PlaybackUtil.isWindows() == false) {
		File f = new File(tempPath + "/file_" + Math.random() * 10000 + ".sh");
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
	    StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERR");

	    // any output?
	    StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUT");

	    // Start the threads
	    errorGobbler.start();
	    outputGobbler.start();

	    // any error???
	    // Appears to be blocking operation
	    int exitVal = proc.waitFor();
	    // log.debug("ExitValue: " + exitVal);

	    if (exitVal == 0) {
		return true;
	    }

	} catch (Exception t) {
	    t.printStackTrace();
	    log.error(t.getMessage(),t);
	}

	return false;
    }
}

// Consume process output
class StreamGobbler extends Thread {
    Logger log = LoggerFactory.getLogger(StreamGobbler.class);
    InputStream is;
    String type;

    StreamGobbler(InputStream is, String type) {
	this.is = is;
	this.type = type;
    }

    public void run() {
	try {
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line = null;
	    while ((line = br.readLine()) != null)
		// Show output in development
		log.debug(type + ">" + line);
	} catch (Exception ioe) {
	    ioe.printStackTrace();
	}
    }
}