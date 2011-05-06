package com.innowhite.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.util.Constants;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.innowhite.whiteboard.util.InnowhiteProperties;
import com.innowhite.whiteboard.util.MakeExectuable;

/*
 * 	This class provides the functionality to make sure that the size of image is decreased when the size is greater then whiteboard.
 * */
public class ImageServicess {
	// private static final Log log = LogFactory.getLog(ImageServicess.class);
	public static final float DECREASE_PERSENT = 0.93f;
	private static final Logger log = LoggerFactory.getLogger(ImageServicess.class);
	
	/*This function minimizes the images when the size of image is greater then 
	 * 
	 * */
	
	public static void resizeImage(String path) {
		
		//log.debug();
		log.debug(" resizeImage entered ");
		int value = 100;
		int level = 0;
		if (path != null) {
			ImageIcon ic = new ImageIcon(path);
			int width = ic.getIconWidth();
			int height = ic.getIconHeight();
			
			log.debug(" width and height are : "+width+" height : "+height+"  path :"+path);
			 
			// File f = new File(path);
			// f.length();
			
			if (width > Constants.WB_WIDTH || height > Constants.WB_HEIGHT) {
//				int width = width - Constants.WB_WIDTH;
//				int height = height - Constants.WB_HEIGHT;
				
				int changedWidth = (int) (width * DECREASE_PERSENT);
				int changedHeight = (int) (height * DECREASE_PERSENT);
				log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
				level++;
				if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
					changedWidth = (int) (changedWidth * DECREASE_PERSENT);
					changedHeight = (int) (changedHeight * DECREASE_PERSENT);
					log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
					level++;
					if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
						changedWidth = (int) (changedWidth * DECREASE_PERSENT);
						changedHeight = (int) (changedHeight * DECREASE_PERSENT);
						log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
						level++;
						if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
							changedWidth = (int) (changedWidth * DECREASE_PERSENT);
							changedHeight = (int) (changedHeight * DECREASE_PERSENT);
							log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
							level++;
							if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
								changedWidth = (int) (changedWidth * DECREASE_PERSENT);
								changedHeight = (int) (changedHeight * DECREASE_PERSENT);
								log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
								level++;
								if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
									changedWidth = (int) (changedWidth * DECREASE_PERSENT);
									changedHeight = (int) (changedHeight * DECREASE_PERSENT);
									log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
									level++;
									if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
										changedWidth = (int) (changedWidth * DECREASE_PERSENT);
										changedHeight = (int) (changedHeight * DECREASE_PERSENT);
										log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
										level++;
										if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
											changedWidth = (int) (changedWidth * DECREASE_PERSENT);
											changedHeight = (int) (changedHeight * DECREASE_PERSENT);
											log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
											level++;
											if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
												changedWidth = (int) (changedWidth * DECREASE_PERSENT);
												changedHeight = (int) (changedHeight * DECREASE_PERSENT);
												log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
												level++;
												if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
													changedWidth = (int) (changedWidth * DECREASE_PERSENT);
													changedHeight = (int) (changedHeight * DECREASE_PERSENT);
													log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
													level++;
													if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
														changedWidth = (int) (changedWidth * DECREASE_PERSENT);
														changedHeight = (int) (changedHeight * DECREASE_PERSENT);
														log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
														level++;
														if (changedWidth > Constants.WB_WIDTH || changedHeight > Constants.WB_HEIGHT) {
															changedWidth = (int) (changedWidth * DECREASE_PERSENT);
															changedHeight = (int) (changedHeight * DECREASE_PERSENT);
															log.debug("changedWidth "+changedWidth+"  changedHeight:"+changedHeight+"  level:"+level);
															level++;

														}
													}
												}
											}
										}
									}
								}
							}
						}
					}

				}

			}

			for (int i = 0; i < level; i++) {
				value = (int) (DECREASE_PERSENT  * value);
			}

		}
		
		log.debug(" needs image resize by :"+value+" level: "+level);
		if (value < 100)
			convertImage(path, value);
		
		log.debug(" resizeImage exiting ");
	}
	
	
	
	
	

	private static void convertImage(String fileName, int minimizePercent) {
		// TODO Auto-generated method stub
		
		log.debug(" convertImage entered fileName: "+fileName);
		String[] Command = null;

		try {

			// String fileName = Utility.stripExtension(inputFile);

			StringBuffer sr = new StringBuffer();

			// String outPutDir = Constants.UBUNTU_FOLDER_PATH_COMMAND;

			// boolean val = (new File(outPutDir)).mkdir();

			sr.append("convert" + " " + fileName + " -resize " + minimizePercent +"%  "+ fileName);

			String ext = new String("" + Math.round((Math.random() * 1000000)));

			File f = new File(InnowhiteProperties.getPropertyVal(InnowhiteConstants.TEMP_LOCATION) + ext + ".sh");
			
			FileOutputStream fos = new FileOutputStream(f);
			// char[] c = new char(sr);
			fos.write(sr.toString().getBytes());	
			fos.close();

			log.debug("image resize shell script is : "+f.getAbsolutePath());
			
			MakeExectuable.getInstance().setExecutable(f.getAbsolutePath());

			Command = new String[1];
			Command[0] = f.getAbsolutePath();

			Process Findspace = Runtime.getRuntime().exec(Command);

			BufferedReader Resultset = new BufferedReader(new InputStreamReader(Findspace.getInputStream()));

			String line;
			while ((line = Resultset.readLine()) != null) {
				log.debug(" out put from command execution  " + line);
			}
			
			log.debug("image resized successufully ");
			
			log.debug("exiting convertImage");	
			// addImagestoDB(modifiedFileName, user, desc);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}

}
