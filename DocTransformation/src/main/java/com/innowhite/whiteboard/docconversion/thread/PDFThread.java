package com.innowhite.whiteboard.docconversion.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.innowhite.whiteboard.docconversion.persistence.dao.MessagePersistenceDAO;
import com.innowhite.whiteboard.docconversion.util.Constants;
import com.innowhite.whiteboard.docconversion.util.DocTransUtil;
import com.innowhite.whiteboard.docconversion.util.ProcessExecutor;
import com.innowhite.whiteboard.docconversion.util.Utility;
import com.innowhite.whiteboard.docconversion.vo.DocConversionBean;
import com.innowhite.whiteboard.docconversion.vo.FileTransformatioBean;
import com.innowhite.whiteboard.docconversion.vo.UserImagesVO;

public class PDFThread extends Thread {

//    private static final Logger log = Logger.getLogger(PDFThread.class);
//    private DocConversionBean docBean = null;
//    private FileTransformatioBean fileTransBean = null;
//
//  
//    static String separator = System.getProperty("file.separator");
//
//    public PDFThread() {
//	// TODO Auto-generated constructor stub
//    }
//
//    public PDFThread(DocConversionBean docBean, FileTransformatioBean fileTransBean) {
//	this.docBean = docBean;
//	this.fileTransBean = fileTransBean;
//    }
//
//    // private static final Logger log = Logger.getLogger(SWFThread.class);
//
//    public void run() {
//	boolean conversionFlag = false;
//	boolean b = false;
//	try {
//
//	    // createBatchFile();
//	    createSwfFilesActual();
//	    // b = invokeProcess();
//	    // executeSymlinkProcess();
//	    // while (!conversionFlag) {
//	    log.debug("conversion Flag......... " + conversionFlag);
//	    // conversionFlag =
//	    // ConversionMessageListener.hTable.get(docBean.getConversionID());
//
//	    // sleep(sleepInterval);
//	    // sleepCounter += sleepInterval;
//
//	    // if (sleepCounter == MAX_SLEEP_TIME)
//	    // break;
//
//	    // }
//	    saveImagesToDB(b);
//
//	    log.info("IN SWF Thread Before Completing Listener: ++++++++++++++++++++ ");
//	} catch (Exception e) {
//	    log.error(e.getMessage());
//	    e.printStackTrace();
//	}
//    }
//
//    private List<UserImagesVO> createSwfFilesActual() {
//
//	log.debug(" entered createSwfFilesActual ");
//
//	String originalFilePath = docBean != null ? docBean.getFilePath() : "";
//	File origFile = new File(originalFilePath);
//	
//	fileTransBean.setOriginalFileName(origFile.getName());
//	
//	String parentDir = null;
//	String inputFile = null;
//
//	String pdfFileAbsPath = null;
//	String origFileName = null;
//	if (origFile.exists()) {
//
//	    pdfFileAbsPath = originalFilePath;
//	    origFileName = origFile.getName();
//	    parentDir = origFile.getParent();
//	    
//	    
//	} else {
//	    log.error(" Error in creating the file :: " + originalFilePath);
//	    return null;
//	}
//
//	log.debug(" inputFile: " + inputFile + "    pdfFileAbsPath:  " + pdfFileAbsPath);
//
//	String[] Command = null;
//
//	String fileName = Utility.stripExtension(inputFile);
//
//	StringBuffer sr = new StringBuffer();
//
//	// String ext = new String("" + Math.round((Math.random() * 1000000)));
//
//	String modifiedFolderName = parentDir + "/" + DocTransUtil.SWF;
//	fileTransBean.setSwfFolder(modifiedFolderName);
//	
//	String urlPathUbun = Constants.UBUNTU_FOLDER_PATH_COMMAND;
//	
//	String outPutDir = parentDir + "/" + DocTransUtil.SWF;
//
//	origFileName = Utility.putBackSpace(origFileName);
//
//	boolean val = (new File(modifiedFolderName)).mkdir();
//	List<UserImagesVO> dbobj = new ArrayList<UserImagesVO>();
//	/*
//	 * sr.append("convert" + " " + Constants.UBUNTU_FOLDER_PATH_COMMAND +
//	 * inputFile + " " + Constants.UBUNTU_FOLDER_PATH_COMMAND +
//	 * modifiedFileName + "//" + modifiedFileName + ".png");
//	 */
//	try {
//	
////
////	sr.append("identify -format %n " + pdfFileAbsPath);
////
////	sr.append("\n");
////
////	String urlPath = parentDir + "/pageCount.bat";
////	// urlPath = urlPath.replaceAll(Constants.APP_NAME,
////	// InnowhiteConstants.CONTEXT_PATH);
////
////	File f = new File(urlPath);
////	FileOutputStream fos;
////	try {
////	    fos = new FileOutputStream(f);
////	    fos.write(sr.toString().getBytes());
////	    fos.close();
////
////	    // MakeExectuable.getInstance().setExecutable(f.getAbsolutePath());
////
////	    Command = new String[1];
////	    Command[0] = f.getAbsolutePath();
////
////	    Process Findspace = Runtime.getRuntime().exec(Command);
////
////	    BufferedReader Resultset = new BufferedReader(new InputStreamReader(Findspace.getInputStream()));
////
////	    String line;
////	    int numberOfSlides = 0;
////	    while ((line = Resultset.readLine()) != null) {
////		log.debug(" out put from command execution  " + line);
////
////		String str = line.substring(0, line.length());
////		str = str.trim();
////		log.debug(str + "" + "hgj");
////		numberOfSlides = Integer.parseInt(str);
////		break;
////
////	    }
//	
//	   int numberOfSlides = Integer.parseInt(fileTransBean.getNumPages());	
//	   log.debug(" total nmber of pages " + numberOfSlides);
//	    // ext = new String("" + Math.round((Math.random() * 1000000)));
//
//	    // urlPath = Constants.TEMP_LOCATION + ext + ".bat";
//	    // urlPath = urlPath.replaceAll(Constants.APP_NAME,
//	    // InnowhiteConstants.CONTEXT_PATH);
//
//	    File ffile = new File(parentDir + "/pdftoswf.bat");
//	    FileOutputStream fos = new FileOutputStream(ffile);
//	    sr = new StringBuffer();
//
//	   // String inputfile = pdfFileAbsPath;
//	    for (int i = 1; i <= numberOfSlides; i++) {
//		UserImagesVO ui = new UserImagesVO(null, outPutDir + "/" + i + ".swf", 0);
//		ui.setImageFolderSeq(i);
//		ui.setImageFolder("true");
//		ui.setImageName(origFileName);
//		ui.setImageType(2);
//		// ui.setImageGroup(Integer.parseInt(ext));
//		dbobj.add(ui);
//		//sr.append("pdf2swf  -O 2  -o " + outPutDir + "/" + i + ".swf -p " + i + " " + pdfFileAbsPath + "\n");
//		String output = outPutDir + "/" + i + ".swf";
//		String command = fileTransBean.getActualFileCommand().replace("OUTPUT", output);
//		command = command.replace("INPUT"," "+i+" "+pdfFileAbsPath);
//		sr.append(command+"\n");
//		// pdfToSwf
//
//	    }
//	    fos.write(sr.toString().getBytes());
//	    fos.close();
//	    // MakeExectuable.getInstance().setExecutable(ffile.getAbsolutePath());
//
//	    // executing the pdf file
//	    invokeProcess(ffile.getAbsolutePath());
//
//	    log.debug(" exiting createSwfFilesActual ");
//
//	} catch (FileNotFoundException e) {
//	    log.error(e.getMessage());
//	    e.printStackTrace();
//	}
//	// char[] c = new char(sr);
//	catch (IOException e) {
//	    log.error(e.getMessage());
//	    e.printStackTrace();
//	}
//	// ProcessConversion.addImagestoDB(modifiedFolderName, user, desc,
//	// documentName);
//	return dbobj;
//
//    }
//
//    private boolean invokeProcess(String filePath) {
//	boolean bFlag = false;
//
//	int x = -1;
//	try {
//
//	    // String command = "C:/sharedFloder/pptx/1021/thumbs.bat";
//	    // Process child =
//	    // Runtime.getRuntime().exec(fileTransBean.getSwfBatFilePath());
//	    bFlag = new ProcessExecutor().executeProcess(filePath);
//
//	} catch (Exception e) {
//	    log.error(e.getMessage());
//	    log.error(" exception ", e.fillInStackTrace());
//	}
//	return bFlag;
//    }
//
//    private boolean saveImagesToDB(boolean bInvoked) {
//	log.info("ENTER save swfs To DB........");
//	boolean bSavedToDB = false;
//	log.debug("bInvoked " + bInvoked+"  imageName "+fileTransBean.getOriginalFileName());
//	bInvoked=true;
//	MessagePersistenceDAO mdao = new MessagePersistenceDAO();
//	if (bInvoked) {
//	    String fileArray[] = DocTransUtil.getSortedImagesArr(fileTransBean, DocTransUtil.SWF);
//	    for (int i = 0; i < fileArray.length; i++) {
//		log.debug(fileArray[i]);
//		UserImagesVO userImagesVO = new UserImagesVO();
//		log.debug("docbean convid " + docBean.getConversionID());
//		userImagesVO.setConversionID(docBean.getConversionID());
//		userImagesVO.setImageFolder("true");
//		userImagesVO.setUserName(docBean.getUserID());
//		userImagesVO.setThumbnailURL(docBean.getServerFilePath() + "/" + docBean.getConversionID() + "/" + DocTransUtil.SWF + "/" + fileArray[i]);
//		userImagesVO.setImageURL(docBean.getServerFilePath() + "/" + docBean.getConversionID() + "/" + DocTransUtil.SWF + "/" + fileArray[i]);
//		userImagesVO.setImageName(fileTransBean.getOriginalFileName());
//		userImagesVO.setImageFolderSeq(i + 1);
//		userImagesVO.setImageType(2);
//		userImagesVO.setImageGroup(docBean.getConversionID());
//		log.debug(userImagesVO);
//		mdao.saveImage(userImagesVO);
//	    }
//
//	    mdao.updateLDCThumbnailURL(docBean.getConversionID(), DocTransUtil.CREATED);
//	    bSavedToDB = true;
//	} else {
//	    log.debug("Else block ");
//	    mdao.updateLDCThumbnailURL(docBean.getConversionID(), DocTransUtil.ERROR);
//	    bSavedToDB = true;
//	}
//	log.info("EXIT saveThumbnailsToDB........");
//	return bSavedToDB;
//    }
//
//    // private boolean saveImagesToDB(boolean bInvoked) {
//    // log.info("ENTER saveSwfsToDB........" + bInvoked);
//    // boolean bSavedToDB = false;
//    // log.debug("bInvoked " + bInvoked);
//    // MessagePersistenceDAO mdao = new MessagePersistenceDAO();
//    // if (bInvoked) {
//    // String fileArray[] = DocTransUtil.getSortedImagesArr(fileTransBean,
//    // DocTransUtil.SWF);
//    // for (int i = 0; i < fileArray.length; i++) {
//    // log.debug(fileArray[i]);
//    //
//    // if (fileTransBean.isSwf())
//    // mdao.updateImageURL(docBean.getConversionID(),
//    // docBean.getServerFilePath() + "/" + docBean.getConversionID() + "/" +
//    // fileTransBean.getOriginalFileNameStripped() + "/"
//    // + fileArray[i], i + 1);
//    // else
//    // mdao.updateImageURL(docBean.getConversionID(),
//    // docBean.getServerFilePath() + "/" + docBean.getConversionID() + "/" +
//    // DocTransUtil.SWF + "/" + fileArray[i], i + 1);
//    //
//    // }
//    //
//    // mdao.updateLDCSWFURL(docBean.getConversionID(), DocTransUtil.CREATED);
//    // bSavedToDB = true;
//    // } else {
//    // log.debug("Else block ");
//    // mdao.updateLDCSWFURL(docBean.getConversionID(), DocTransUtil.ERROR);
//    // bSavedToDB = true;
//    // }
//    // log.info("EXIT save SWF To DB........");
//    // return bSavedToDB;
//    // }

}
