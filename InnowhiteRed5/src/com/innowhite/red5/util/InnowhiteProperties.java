package com.innowhite.red5.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

public class InnowhiteProperties {

	private static Logger log = Red5LoggerFactory.getLogger(InnowhiteProperties.class, "whiteboard");
	
	private static Properties prop=null;
	
	public static String getPropertyVal(String key){
		
		if(prop== null)
			loadProperties();
		//if(prop.getProperty(key) != null)
			
		log.debug(""+prop);
		if(prop != null && prop.getProperty(key) != null)
			return prop.getProperty(key);
		else
		{
			log.debug(" key:: "+key+"  unavailable ");
			return null;
		}
	
	
	}

	private static void loadProperties() {
	
		//File f = new File("myFile.txt");
		//String path = f.getAbsolutePath();
		//path = path.replace("myFile.txt", "");
		//log.debug("  path::"+f.getAbsolutePath());
		String path = "webapps/whiteboard/conf/storage.properties";
		
		prop = new Properties();
		try {
			
			log.debug("  Path ::"+path);
			prop.load(new FileReader(path));
			
			
			
			//prop.g
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	/*	prop = new Properties();
		 try {
			 prop.load(new FileInputStream("storage.properties"));
		     log.debug("Properties loaded succefully");
		 } catch (IOException e) {
			e.printStackTrace();
			
		 }*/
		
		
	}
	
	
	
}
