package com.vireka.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.innowhite.transformation.messages.SimpleMessageProducer;
import com.innowhite.whiteboard.persistence.beans.UserImagesVO;
import com.innowhite.whiteboard.persistence.dao.ClientMediaDAO;
import com.innowhite.whiteboard.persistence.dao.CustomUserImagesDAO;
import com.innowhite.whiteboard.persistence.dao.LiveDocConvDAO;
import com.innowhite.whiteboard.service.BeanService;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.util.Constants;
import com.util.InnowhiteProperties;
import com.util.MakeExectuable;
import com.util.Utility;
import com.vo.DocConversionBean;

public class ProcessConversion {

	/**
	 * @param args
	 */
	
	
	private static Logger log = Red5LoggerFactory.getLogger(ProcessConversion.class, "whiteboard");
	
	SimpleMessageProducer smp = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// convertDocument("");
	}

	public static String lessonDocumentConverter(String documentId,
			String serverURL, String orgName) {

		log.debug(" lessonDocumentConverter enterd ");
		log.debug(" documentId   "+documentId+"  serverURL "+serverURL+" orgName  "+orgName);
		String filePath = ClientMediaDAO.getContentSource(documentId, orgName);

		log.debug(" filePath:: " + filePath);
		String documentsource = InnowhiteProperties
				.getPropertyVal("MEDIA_PATH")
				+ filePath.substring(filePath.lastIndexOf("/") + 1);

		log.debug(" documentId " + documentId + "  documentsource "
				+ documentsource);

		File documentFile = new File(documentsource);

		String fileName = documentsource.substring(documentsource
				.lastIndexOf("/") + 1);

		if ((Utility.allowedFiles(fileName) == true)) {
			// throw new Exception("Invalid File type");

			if (documentFile.exists()) {

				return convertDocument(documentFile.getAbsolutePath(), documentId,
						"", fileName, serverURL, false, fileName,true);

			}
		}
		return null;
	};

	/*
	 * This method converts the input document like pdf, ppt to images.
	 */
	public static String convertDocument(String filePath, String user,
			String desc, String documentName, String serverURL,
			boolean saveInDB, String origFileName, boolean isInet) {

		
		
//		Properties p = System.getProperties();
//		Enumeration keys = p.keys();
//		while (keys.hasMoreElements()) {
//		  String key = (String)keys.nextElement();
//		  String value = (String)p.get(key);
//		  log.debug(key + ": " + value);
//		}
		
		log.debug(" entered convertDocument ");

		log.debug(" filePath: " + filePath + " user: " + user
				+ "  desc: " + desc + "  documentName: " + documentName);
		String pdfName = filePath;
		File outputFile = null;
		File inputFile = null;
		String str = null;

		// for ppt & pptx there is special processing.
		if (fileIsPPT(documentName) == true && isInet == false ) {

			String conversionId = processPPTX(filePath, user, desc,
					documentName, serverURL, saveInDB, origFileName);
			return conversionId;
		}

		try {

			log.debug(" the file is not pdf " + documentName);

			String fileName = Utility.stripExtension(filePath);

			// String urlPath = Constants.UPLOAD_FILES;
			// urlPath = urlPath.replaceAll(Constants.APP_NAME,
			// request.getContextPath().substring(1));

			String urlPath = Constants.UBUNTU_FOLDER_PATH;
			// urlPath = urlPath.replaceAll(Constants.APP_NAME,
			// contextPath.substring(1));

			inputFile = new File(filePath);
			if (Utility.isPdf(filePath) == false) {
				// File inputFile = new File(f);
				outputFile = new File(fileName + ".pdf");

				log.debug(" input :" + inputFile.getAbsolutePath());
				log.debug(" output :" + outputFile.getAbsolutePath());

				OpenOfficeConnection connection = new SocketOpenOfficeConnection(
						8100);

				connection.connect();

				// convert
				DocumentConverter converter = new OpenOfficeDocumentConverter(
						connection);
				converter.convert(inputFile, outputFile);
				connection.disconnect();

			} else {

				outputFile = inputFile;
			}

			pdfName = outputFile.getName();
			log.debug("  The pdf created is :" + pdfName
					+ "  exists :" + outputFile.exists());
			if (outputFile.exists() == true) {
				str = MakeSWFFiles.createSwfFiles(pdfName, user, desc,
						documentName, saveInDB, serverURL,
						outputFile.getAbsolutePath(), origFileName,isInet);
				log.debug(" exiting convertDocument ");
			}

		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			log.error("connection exception ...", e);
			e.printStackTrace();
		}

		return str;

		// createImagesfromPDF(pdfName, user, desc, documentName);

	}

	// process ppt and pptx files. This is the new conversion process to be run
	// on server.
	private static String processPPTX(String filePath, String user,
			String desc, String documentName, String serverURL,
			boolean saveInDB, String origFileName) {
	
		log.debug(" Entered processPPTX ");
		int conversionId=-1;
		try {
			// get conversion ID
			 conversionId = LiveDocConvDAO.save(user);

			// create folder with conversionID and copy the file to this
			// location.
			String shraedPath = InnowhiteProperties
					.getPropertyVal(InnowhiteConstants.SHARED_PATH);
			File dir = new File(shraedPath + conversionId);
		
			log.debug(" shraedPath "+shraedPath+"  dir "+shraedPath + conversionId);
			
			boolean dirCreated = dir.mkdir();
			if (dirCreated == true) {
				// copy the ppt file to this location.
				File srcFile = new File(filePath);
				//File destFile = new File(dirCreated);
				
				
				FileUtils.copyFileToDirectory(srcFile, dir);
				log.debug(" copying file from  "+srcFile.getAbsolutePath()+" to "+conversionId+" //"+srcFile.getName());
				
				String destFile = InnowhiteProperties.getPropertyVal(InnowhiteConstants.DOCUMENT_SHARE_LOCATION)+conversionId+"/"+srcFile.getName();
				
				log.debug(" the dest file is   "+destFile);
				
				SimpleMessageProducer smp =	  BeanService.getSimpleMessageProducerBean();
				DocConversionBean docBean = new DocConversionBean();
				docBean.setUserID(user);
				docBean.setFilePath(destFile);
				docBean.setConversionID(conversionId);
				docBean.setServiceType("THUMBNAILS");
				docBean.setServerFilePath(InnowhiteProperties.getPropertyVal(InnowhiteConstants.SHARED_PATH));
				smp.sendMessages(docBean);
				

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Generic exception ...", e);
			e.printStackTrace();
		}

		return String.valueOf(conversionId);
	}

	private static boolean fileIsPPT(String documentName) {

		return Utility.allowedPptx(documentName);

	}

	private static void createImagesfromPDF(String inputFile, String user,
			String desc, String documentName) {

		String[] Command = null;

		try {

			String fileName = Utility.stripExtension(inputFile);

			StringBuffer sr = new StringBuffer();

			String ext = new String("" + Math.round((Math.random() * 1000000)));

			String modifiedFileName = fileName + "_" + ext;

			String urlPath = Constants.UBUNTU_FOLDER_PATH_COMMAND
					+ modifiedFileName;
			urlPath = urlPath.replaceAll(Constants.APP_NAME,
					InnowhiteConstants.CONTEXT_PATH);

			String urlPathUbuntu = Constants.UBUNTU_FOLDER_PATH_COMMAND;
			urlPathUbuntu = urlPathUbuntu.replaceAll(Constants.APP_NAME,
					InnowhiteConstants.CONTEXT_PATH);

			String outPutDir = urlPath;

			boolean val = (new File(outPutDir)).mkdir();

			sr.append("convert" + " " + urlPathUbuntu + inputFile + " "
					+ urlPathUbuntu + modifiedFileName + "//"
					+ modifiedFileName + ".png");

			sr.append("\n");

			sr.append("convert \"" + urlPathUbuntu + modifiedFileName + "//"
					+ modifiedFileName + "-0" + ".png\""
					+ " -thumbnail 250x90 -unsharp 0x.5 \"" + urlPathUbuntu
					+ modifiedFileName + "//" + "thumbnail" + ".png\"");

			File f = new File(Constants.TEMP_LOCATION + ext + ".sh");
			FileOutputStream fos = new FileOutputStream(f);
			// char[] c = new char(sr);
			fos.write(sr.toString().getBytes());
			fos.close();

			MakeExectuable.getInstance().setExecutable(f.getAbsolutePath());

			Command = new String[1];
			Command[0] = f.getAbsolutePath();

			Process Findspace = Runtime.getRuntime().exec(Command);

			BufferedReader Resultset = new BufferedReader(
					new InputStreamReader(Findspace.getInputStream()));

			String line;
			while ((line = Resultset.readLine()) != null) {
				log.debug(" out put from command execution  " + line);
			}

			addImagestoDB(modifiedFileName, user, desc, documentName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("IOException exception ...", e);
			e.printStackTrace();
		}

	}

	public static void addImagestoDB(String dirName, String user, String desc,
			String documentName) {

		String urlPath = Constants.UBUNTU_FOLDER_PATH_COMMAND + dirName;
		urlPath = urlPath.replaceAll(Constants.APP_NAME,
				InnowhiteConstants.CONTEXT_PATH);

		File dir = new File(urlPath);
		log.debug(" dir path : " + dir.getAbsolutePath());
		String[] children = dir.list();
		// List l = new ArrayList();
		CustomUserImagesDAO dao = new CustomUserImagesDAO();

		// Transaction tx = dao.getSession().beginTransaction();
		log.debug(" number of files :" + children.length);
		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				String filename = children[i];

				log.debug(filename);

				if (filename.equals("thumbnail.png"))
					continue;

				UserImagesVO ui = new UserImagesVO(user, dirName + "/"
						+ filename, 0);
				int begin = filename.lastIndexOf("-");
				int end = filename.lastIndexOf(".");

				int beginIndNum = filename.lastIndexOf("_");
				String presentId = filename.substring(beginIndNum + 1, begin);

				ui.setImageGroup(Integer.parseInt(presentId));
				String num = filename.substring(begin + 1, end);
				log.debug(" length: " + children.length + "  num:  "
						+ num);
				if (children.length == 1) {
					num = "0";
				}
				int numSeq = Integer.parseInt(num);
				ui.setImageFolderSeq(numSeq);
				ui.setImageFolder("true");
				ui.setImageName(documentName);
				ui.setImageType(2);
				if (desc != null && desc.length() > 0)
					ui.setImageDescription(desc);
				else
					ui.setImageDescription(filename);

				ui.setImageDescription(desc);
				dao.save(ui);

			}
		}
		// tx.commit();
		// dao.getSession().close();

	}

	public static void createThumbNail(File f2, String fileName) {
		// TODO Auto-generated method stub
		String[] Command = null;

		try {

			// String fileName = Utility.stripExtension(inputFile);

			StringBuffer sr = new StringBuffer();

			String urlPath = Constants.UBUNTU_FOLDER_PATH_COMMAND;
			urlPath = urlPath.replaceAll(Constants.APP_NAME,
					InnowhiteConstants.CONTEXT_PATH);

			String outPutDir = urlPath;

			// boolean val = (new File(outPutDir)).mkdir();

			sr.append("convert" + " " + outPutDir + fileName
					+ " -thumbnail 250x90 -unsharp 0x.5 " + outPutDir
					+ "thumbn_" + fileName);

			String ext = new String("" + Math.round((Math.random() * 1000000)));

			File f = new File(Constants.TEMP_LOCATION + ext + ".sh");
			FileOutputStream fos = new FileOutputStream(f);
			// char[] c = new char(sr);
			fos.write(sr.toString().getBytes());
			fos.close();

			MakeExectuable.getInstance().setExecutable(f.getAbsolutePath());

			Command = new String[1];
			Command[0] = f.getAbsolutePath();

			Process Findspace = Runtime.getRuntime().exec(Command);

			BufferedReader Resultset = new BufferedReader(
					new InputStreamReader(Findspace.getInputStream()));

			String line;
			while ((line = Resultset.readLine()) != null) {
				log.debug(" out put from command execution  " + line);
			}

			// addImagestoDB(modifiedFileName, user, desc);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("IOException",e);
			e.printStackTrace();
		}

	}

}
