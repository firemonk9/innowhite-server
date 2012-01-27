package com.innowhite.whiteboard.docconversion.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innowhite.whiteboard.docconversion.vo.FileTransformatioBean;

public class DocTransUtil {

	public static final String CREATED = "CREATED";
	public static final String ERROR = "ERROR";
	public final static String THUMBNAIL = "Thumbnail";
	public final static String SWF = "fullimages";
	public final static String SWF_EXTENSION = "swf";
	public final static String THUMBNAIL_EXTENSION = "jpg";
	public static String separator = System.getProperty("file.separator");

	private static final Logger log = Logger.getLogger(DocTransUtil.class);

	public static int convertToInt(String s) {
		int x = -1;
		try {
			x = Integer.parseInt(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return x;
	}

	public static List<File> getSortedImagesArr(FileTransformatioBean fileTransBean, String type) {
		log.info("ENTER getSortedImagesArr..");

		log.debug("fileTransBean: " + fileTransBean);
		log.debug("type: " + type);

		FileTransformatioBean fileTransfBean = fileTransBean;
		String thumbsFolder = fileTransfBean.getThumbsFolder();
		String imageFolder = fileTransfBean.getThumbsFolder();
		File f = null;
		if (THUMBNAIL.equalsIgnoreCase(type)) {
			f = new File(thumbsFolder);
		}
		if (SWF.equalsIgnoreCase(type)) {
			f = new File(imageFolder);
		}

		log.debug("  Checking the directory for files :: " + f.getAbsolutePath() + "   and  swf folder is::" + imageFolder);

		File fileArray[] = f.listFiles(new ImageFileFilter());

		Map<String, File> fileNameList = getListFileName(fileArray);

		String prep = null;
		String ext = null;
		if (THUMBNAIL.equals(type)) {
			prep = "thumbnail";
			ext = THUMBNAIL_EXTENSION;
		} else {
			prep = "page";
			ext = SWF_EXTENSION;
		}
		int count = 0;

		List<File> finalList = new ArrayList<File>();

		while (true) {

			count++;

			String fileName = prep + "_" + count + "." + ext;

			log.debug(" checking the file name :: " + fileName);
			if (fileNameList.keySet().contains(fileName)) {
				finalList.add(fileNameList.get(fileName));
			} else
				break;
		}

		return finalList;

	}

	private static Map<String, File> getListFileName(File fileArray[]) {

		Map<String, File> fileNameList = new LinkedHashMap<String, File>();
		for (int i = 0; i < fileArray.length; i++) {
			log.debug(" name is : " + fileArray[i].getName() + " and file is ::  " + fileArray[i]);
			fileNameList.put(fileArray[i].getName(), fileArray[i]);
		}
		return fileNameList;

	}

	// private static int convertToSwfInt(String fileNumber) {
	//
	// if (fileNumber.contains("Slide")) {
	// fileNumber = fileNumber.replace("Slide", "");
	//
	// }
	// return convertToInt(fileNumber);
	//
	// }

	/*
	 * public static void main(String[] args) { FileTransformatioBean ftb = new
	 * FileTransformatioBean();
	 * ftb.setThumbsFolder("/Users/prashanthj/Desktop/Thumbnail"); String str[]
	 * = getThumbnails(ftb); for (int i = 0; i < str.length; i++) {
	 * log.debug(str[i]); }
	 * 
	 * }
	 */

	public static String[] getThumbnails(String filePath, String folder) {
		log.debug("ENTER getThumbnails..");
		log.debug("Arguments filePath: " + filePath + " folder " + folder);
		File f = new File(filePath);
		String directoryPath = f.getParent();
		log.debug("directoryPath " + directoryPath);
		directoryPath += folder + separator + f.getName();
		log.debug("directoryPath " + directoryPath);
		File dir = new File(directoryPath);
		String fileArray[] = dir.list();
		String sortedFileArray[] = new String[fileArray.length];
		for (int i = 0; i < fileArray.length; i++) {
			String t[] = fileArray[i].split("\\.");
			String fileNumber = t[0];
			int jpgNumber = DocTransUtil.convertToInt(fileNumber);
			sortedFileArray[jpgNumber] = directoryPath + separator + fileArray[i];

		}
		log.debug("sortedFileArray: " + sortedFileArray);
		log.debug("EXIT getThumbnails..");
		return sortedFileArray;
	}

}
