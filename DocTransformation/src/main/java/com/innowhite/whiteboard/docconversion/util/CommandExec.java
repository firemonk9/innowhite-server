/**
 * 
 */
package com.innowhite.whiteboard.docconversion.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

/**
 * @author prashanthj
 * 
 */
public class CommandExec {

    private static final Logger log = Logger.getLogger(CommandExec.class);
    
    public static int invoke(String args) {
	int exitVal = -1;
	if (args == null) {
	    log.debug("USAGE: java TestExec \"cmd\"");
	    // System.exit(1);
	    return exitVal;
	}

	try {
	    String cmd = args;
	    Runtime rt = Runtime.getRuntime();
	    Process proc = rt.exec(cmd);

	    // any error message?
	    StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERR");

	    // any output?
	    StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUT");

	    // kick them off
	    errorGobbler.start();
	    outputGobbler.start();

	    // any error???
	    exitVal = proc.waitFor();
	    log.debug("ExitValue: " + exitVal);
	} catch (Throwable t) {
	    t.printStackTrace();
	}
	return exitVal;
    }
}

class StreamGobbler extends Thread {
    InputStream is;
    String type;
    OutputStream os;

    StreamGobbler(InputStream is, String type) {
	this(is, type, null);
    }

    StreamGobbler(InputStream is, String type, OutputStream redirect) {
	this.is = is;
	this.type = type;
	this.os = redirect;
    }

    public void run() {
	try {
	    PrintWriter pw = null;
	    if (os != null)
		pw = new PrintWriter(os);

	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line = null;
	    while ((line = br.readLine()) != null) {
		if (pw != null)
		    pw.println(line);
		//log.debug(""+type + ">" + line);
	    }
	    if (pw != null)
		pw.flush();
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	}
    }
}
