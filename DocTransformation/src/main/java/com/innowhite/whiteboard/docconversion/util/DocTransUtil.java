package com.innowhite.whiteboard.docconversion.util;

import java.io.File;

import org.apache.log4j.Logger;

import com.innowhite.whiteboard.docconversion.thread.ThumbnailThread;
import com.innowhite.whiteboard.docconversion.vo.FileTransformatioBean;

public class DocTransUtil {

    public static final String CREATED = "CREATED";
    public static final String ERROR = "ERROR";
    public final static String THUMBNAIL = "Thumbnail";
    public final static String SWF = "fullimages";
    public static String separator = System.getProperty("file.separator");

    private static final Logger log = Logger.getLogger(ThumbnailThread.class);

    public static int convertToInt(String s) {
	int x = -1;
	try {
	    x = Integer.parseInt(s);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return x;
    }

    public static String[] getSortedImagesArr(FileTransformatioBean fileTransBean, String type) {
	log.info("ENTER getSortedImagesArr..");

	System.out.println("fileTransBean: " + fileTransBean);
	FileTransformatioBean fileTransfBean = fileTransBean != null ? fileTransBean : new FileTransformatioBean();
	String thumbsFolder = fileTransfBean.getThumbsFolder();
	String imageFolder = fileTransfBean.getSwfFolder();
	File f = null;
	if (THUMBNAIL.equalsIgnoreCase(type)) {
	    f = new File(thumbsFolder);
	}
	if (SWF.equalsIgnoreCase(type)) {
	    f = new File(imageFolder);
	}

	log.debug("  Checking the directory for files :: " + f.getAbsolutePath() + "   and  swf folder is::" + imageFolder);

	File fileArray[] = f.listFiles(new ImageFileFilter());
	// for (int i = 0; i < fileArray.length; i++) {
	// System.out.println(fileArray[i]);
	// }
	// System.out.println("fileArray: "+fileArray);
	String sortedFileArray[] = new String[fileArray.length];
	for (int i = 0; i < sortedFileArray.length; i++) {
	    String tempArray[] = fileArray[i].getName().split("\\.");
	    String fileNumber = tempArray[0];
	    int jpgNumber = 0;
	    if (SWF.equalsIgnoreCase(type)) {
		jpgNumber = convertToSwfInt(fileNumber);
	    } else {

		jpgNumber = convertToInt(fileNumber);
	    }

	    sortedFileArray[jpgNumber - 1] = fileArray[i].getName();
	}
	System.out.println("sortedFileArray: " + sortedFileArray);
	// for (int i = 0; i < sortedFileArray.length; i++) {
	// System.out.println(sortedFileArray[i]);
	// }
	log.info("EXIT getSortedImagesArr.." + sortedFileArray.length);
	return sortedFileArray;

    }

    private static int convertToSwfInt(String fileNumber) {

	if (fileNumber.contains("Slide")) {
	    fileNumber = fileNumber.replace("Slide", "");

	}
	return convertToInt(fileNumber);

    }

    /*
     * public static void main(String[] args) { FileTransformatioBean ftb = new
     * FileTransformatioBean();
     * ftb.setThumbsFolder("/Users/prashanthj/Desktop/Thumbnail"); String str[]
     * = getThumbnails(ftb); for (int i = 0; i < str.length; i++) {
     * System.out.println(str[i]); }
     * 
     * }
     */

    public static String[] getThumbnails(String filePath, String folder) {
	System.out.println("ENTER getThumbnails..");
	System.out.println("Arguments filePath: " + filePath + " folder " + folder);
	File f = new File(filePath);
	String directoryPath = f.getParent();
	System.out.println("directoryPath " + directoryPath);
	directoryPath += folder + separator + f.getName();
	System.out.println("directoryPath " + directoryPath);
	File dir = new File(directoryPath);
	String fileArray[] = dir.list();
	String sortedFileArray[] = new String[fileArray.length];
	for (int i = 0; i < fileArray.length; i++) {
	    String t[] = fileArray[i].split("\\.");
	    String fileNumber = t[0];
	    int jpgNumber = DocTransUtil.convertToInt(fileNumber);
	    sortedFileArray[jpgNumber] = directoryPath + separator + fileArray[i];

	}
	System.out.println("sortedFileArray: " + sortedFileArray);
	System.out.println("EXIT getThumbnails..");
	return sortedFileArray;
    }

}
