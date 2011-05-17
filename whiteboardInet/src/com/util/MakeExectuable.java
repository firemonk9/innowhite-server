package com.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.servlets.WTest;



public class MakeExectuable {
	
	//private static final Logger log =  Logger.getLogger(MakeExectuable.class); //Red5LoggerFactory.getLogger(MakeExectuable.class, "openmeetings");

	private static MakeExectuable instance;

	private static Logger log = Red5LoggerFactory.getLogger(MakeExectuable.class, "whiteboard");
	
	private MakeExectuable() {}

	public static synchronized MakeExectuable getInstance() {
		if (instance == null) {
			instance = new MakeExectuable();
		}
		return instance;
	}
	
	public String setExecutable(String fileName){
		try {
			
			log.debug("setExecutable: "+fileName);
			
			String[] cmd = new String[1];
			cmd[0] = "chmod +x "+fileName;
			
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec( "chmod +x "+fileName );
			
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			String error = "";
			while ((line = br.readLine()) != null){
				error += line;
				log.debug("line: "+line);
			}
			int exitVal = proc.waitFor();
			log.debug("exitVal: "+exitVal);
			
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}

}
