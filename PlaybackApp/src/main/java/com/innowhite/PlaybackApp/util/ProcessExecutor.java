package com.innowhite.PlaybackApp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessExecutor {
	
	private String executable;
	private String input;
	private String params;
	private String output;
		
	public boolean executeProcess(String cmd) {

		try {
			//String cmd = executable + " -i " + input + " " + params + " " + output;
			System.out.println( cmd );
			
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd);

			// any error message?
			StreamGobbler errorGobbler = new StreamGobbler(
					proc.getErrorStream(), "ERR");

			// any output?
			StreamGobbler outputGobbler = new StreamGobbler(
					proc.getInputStream(), "OUT");

			// Start the threads
			errorGobbler.start();
			outputGobbler.start();

			// any error???
			// Appears to be blocking operation
			int exitVal = proc.waitFor();
			//System.out.println("ExitValue: " + exitVal);
			
			if (exitVal == 0) {
				return true;
			}			

		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return false;
	}
}

//Consume process output
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