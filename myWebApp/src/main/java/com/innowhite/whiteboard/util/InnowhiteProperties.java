package com.innowhite.whiteboard.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InnowhiteProperties {

	private static final Logger log = LoggerFactory
			.getLogger(InnowhiteProperties.class);

	private static Properties prop = null;

	public static String getPropertyVal(String key) {

		try {
			if (prop == null) {
				InnowhiteProperties obj = new InnowhiteProperties();
				obj.loadProperties();

			}// if(prop.getProperty(key) != null)

			log.debug("" + prop);
			if (prop != null && prop.getProperty(key) != null)
				return prop.getProperty(key);
			else {
				log.debug(" key:: " + key + "  unavailable ");
				return null;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

		// return null;

	}

	private void loadProperties() {

		// File f = new File("myFile.txt");
		// String path = f.getAbsolutePath();
		// path = path.replace("myFile.txt", "");
		// log.debug("  path::"+f.getAbsolutePath());
		String path = "conf/storage.properties";

		prop = new Properties();
		try {

			InputStream inputStream = this.getClass().getClassLoader()
					.getResourceAsStream(path);

			log.debug("  Path ::" + path);
			prop.load(inputStream);

			// prop.g
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

		/*
		 * prop = new Properties(); try { prop.load(new
		 * FileInputStream("storage.properties"));
		 * log.debug("Properties loaded succefully"); } catch (IOException e) {
		 * e.printStackTrace();
		 * 
		 * }
		 */

	}

}
