package com.innowhite.whiteboard.docconversion.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class ProcessExecutor {

    private static final Logger log = Logger.getLogger(ProcessExecutor.class);

    public boolean executeProcess(String cmd) {

	try {
	    // String cmd = executable + " -i " + input + " " + params + " " +
	    // output;
	    log.debug(" executing the command executeProcess :: " + cmd);

	    Runtime rt = Runtime.getRuntime();
	    Process proc = rt.exec(cmd);
	    // any error message?
	    StreamGobbler errorGobbler = new StreamGobbler(new BufferedInputStream(proc.getErrorStream()), "ERR");
	    // any output?
	    StreamGobbler outputGobbler = new StreamGobbler(new BufferedInputStream(proc.getInputStream()), "OUT");

	    // Start the threads
	    errorGobbler.start();
	    outputGobbler.start();

	    // any error???
	    // Appears to be blocking operation
	    int exitVal = proc.waitFor();
	    // System.out.println("ExitValue: " + exitVal);
	    log.debug("The return or exit value is :: " + exitVal);
	    if (exitVal == 0) {
		return true;
	    }
	    return true;

	} catch (Throwable t) {
	    t.printStackTrace();
	}

	return false;
    }
}

// Consume process output
class StreamGobbler extends Thread {
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
		System.out.println(type + ">" + line);
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}
    }
}