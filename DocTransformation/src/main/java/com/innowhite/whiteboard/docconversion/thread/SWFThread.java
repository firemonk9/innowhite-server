package com.innowhite.whiteboard.docconversion.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.innowhite.whiteboard.docconversion.messages.ConversionMessageListener;
import com.innowhite.whiteboard.docconversion.persistence.dao.MessagePersistenceDAO;
import com.innowhite.whiteboard.docconversion.util.DocTransUtil;
import com.innowhite.whiteboard.docconversion.util.ProcessExecutor;
import com.innowhite.whiteboard.docconversion.vo.DocConversionBean;
import com.innowhite.whiteboard.docconversion.vo.FileTransformatioBean;

public class SWFThread extends Thread {

    private static final Logger log = Logger.getLogger(SWFThread.class);
    private DocConversionBean docBean = null;
    private FileTransformatioBean fileTransBean = null;

    private int MAX_SLEEP_TIME = 180000;
    private int sleepCounter = 0;

    private int sleepInterval = 2000;

    static String separator = System.getProperty("file.separator");

//    public SWFThread() {
//	// TODO Auto-generated constructor stub
//    }
//
//    public SWFThread(DocConversionBean docBean, FileTransformatioBean fileTransBean) {
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
//	    createBatchFile();
//	    b = invokeProcess();
//	   // executeSymlinkProcess();
//	    while (!conversionFlag) {
//		log.debug("conversion Flag......... " + conversionFlag);
//		conversionFlag = ConversionMessageListener.hTable.get(docBean.getConversionID());
//
//		sleep(sleepInterval);
//		sleepCounter += sleepInterval;
//
//		if (sleepCounter == MAX_SLEEP_TIME)
//		    break;
//
//	    }
//	    saveImagesToDB(b);
//
//	    log.info("IN SWF Thread Before Completing Listener: ++++++++++++++++++++ ");
//	} catch (Exception e) {
//	    e.printStackTrace();
//	}
//    }
//
//    protected void createBatchFile() {
//	log.info("ENTER SWF createBatchFile...... ");
//	BufferedWriter out = null;
//	try {
//	    boolean bCreated = false;
//	    String originalFilePath = docBean != null ? docBean.getFilePath() : ""; // C:/Documents
//										    // and
//										    // Settings/Administrator/Desktop/Presentation1.pptx
//	    File f = new File(originalFilePath);
//	    String swfFolder = null;
//	    if (fileTransBean.isSwf() == true) {
//		
//		File originFile = new File(originalFilePath);
//		String strippedName = originFile.getName().substring(0, originFile.getName().lastIndexOf("."));
//		if(originalFilePath.endsWith("pptx"))
//		{
//		    strippedName=strippedName+"x";
//		}
//		fileTransBean.setOriginalFileNameStripped(strippedName);
//		
//		swfFolder = f.getParent() + separator + strippedName; // C:/Documents
//	    } else {// and
//		swfFolder = f.getParent() + separator + DocTransUtil.SWF; // C:/Documents
//	    }
//	    String swfBatFileContent = "";
//
//	    // Batch File Content
//	    StringBuffer sb = new StringBuffer();
//	    // sb.append(" \"C:/Program Files/Ppt2SwfSDK/Samples/Precompiled/Ppt2SwfSampleCSharpConsole.exe\" ");
//	    // sb.append(" \"Dhiraj Peechara - Vireka LLC\" ");
//	    // sb.append(" \"d4f0ec7829702966119f20d2a5a74837\" -tc jpg 700 700 ");
//
//	    sb.append(fileTransBean.getActualFileCommand() + " ");
//
//	    sb.append("\"" + swfFolder + "\" ");
//	    sb.append("\"" + originalFilePath + "\" ");
//	    if (fileTransBean.isSwf() == false) {
//		sb.append(" \"\"");
//	    }
//	    swfBatFileContent = sb.toString();
//
//	    // BatchFile path
//	    String originalDir = f.getParent(); // C:/Documents and
//						// Settings/Administrator/Desktop
//
//	    String swfBatFilePath = originalDir + separator + "swf.bat"; // C:/Documents
//									 // and
//									 // Settings/Administrator/Desktop/thumbs.bat
//
//	    fileTransBean.setOriginalFilePath(originalFilePath);
//	    fileTransBean.setSwfFolder(swfFolder);
//	    fileTransBean.setSwfBatFileContent(swfBatFileContent);
//	    fileTransBean.setOriginalDir(originalDir);
//	    fileTransBean.setSwfBatFilePath(swfBatFilePath);
//
//	    out = new BufferedWriter(new FileWriter(new File(swfBatFilePath)));
//	    out.write(swfBatFileContent);
//	    out.close();
//	    // C:/Documen and Settings/Administrator/Desktop/s.bat
//
////	    if (fileTransBean.isSwf()) {
////
////		File originFile = new File(originalFilePath);
////		String strippedName = originFile.getName().substring(0, originFile.getName().lastIndexOf("."));
////
////		String symlinkBatFilePath = originalDir + separator + "symlink.bat";
////		f = new File(symlinkBatFilePath);
////		sb = new StringBuffer();
////		sb.append("mklink /j " + DocTransUtil.SWF + "  " + strippedName);
////		if(originalFilePath.endsWith("pptx"))
////		{
////		    sb.append("x");
////		}
////		out = new BufferedWriter(new FileWriter(f));
////		out.write(sb.toString());
////		out.close();
////		fileTransBean.setSymlinkBatFilePath(symlinkBatFilePath);
////	    }
//	    // Create symlink batch file ..
//
//	} catch (IOException ioException) {
//	    log.error("createBatchFile " + ioException);
//	} catch (Exception exception) {
//	    log.error("createBatchFile " + exception);
//	} finally {
//	    try {
//		out.close();
//	    } catch (Exception e) {
//		log.error("createBatchFile finally block: " + e);
//	    }
//	}
//	log.info(fileTransBean.getClass().toString());
//	log.info("EXIT createBatchFile........");
//    }
//
//    private boolean invokeProcess() {
//	boolean bFlag = false;
//
//	int x = -1;
//	try {
//
//	    // String command = "C:/sharedFloder/pptx/1021/thumbs.bat";
//	    // Process child =
//	    // Runtime.getRuntime().exec(fileTransBean.getSwfBatFilePath());
//	    bFlag = new ProcessExecutor().executeProcess(fileTransBean.getSwfBatFilePath());
//
//	} catch (Exception e) {
//	    log.error(" exception ", e.fillInStackTrace());
//	}
//	return bFlag;
//    }
//
////    private boolean executeSymlinkProcess() {
////	boolean bFlag = false;
////	log.debug(" entered  executeSymlinkProcess ");
////	int x = -1;
////	try {
////
////	    // String command = "C:/sharedFloder/pptx/1021/thumbs.bat";
////	    // Process child =
////	    // Runtime.getRuntime().exec(fileTransBean.getSymlinkBatFilePath());
////
////	    bFlag = new ProcessExecutor().executeProcess(fileTransBean.getSymlinkBatFilePath());
////
////	} catch (Exception e) {
////	    log.error(" exception ", e.fillInStackTrace());
////	}
////	return bFlag;
////    }
//
////    private boolean saveImagesToDB(boolean bInvoked) {
//////	log.info("ENTER saveSwfsToDB........"+bInvoked);
//////	boolean bSavedToDB = false;
//////	log.debug("bInvoked " + bInvoked);
//////	MessagePersistenceDAO mdao = new MessagePersistenceDAO();
//////	if (bInvoked) {
//////	    String fileArray[] = DocTransUtil.getSortedImagesArr(fileTransBean, DocTransUtil.SWF);
//////	    for (int i = 0; i < fileArray.length; i++) {
//////		log.debug(fileArray[i]);
//////		
//////		if(fileTransBean.isSwf())
//////		    mdao.updateImageURL(docBean.getConversionID(), docBean.getServerFilePath() + "/" + docBean.getConversionID() + "/" +fileTransBean.getOriginalFileNameStripped() + "/" + fileArray[i], i + 1);
//////		else
//////		    mdao.updateImageURL(docBean.getConversionID(), docBean.getServerFilePath() + "/" + docBean.getConversionID() + "/" +DocTransUtil.SWF + "/" + fileArray[i], i + 1);
//////		
//////		
//////	    }
//////
//////	    mdao.updateLDCSWFURL(docBean.getConversionID(), DocTransUtil.CREATED);
//////	    bSavedToDB = true;
//////	} else {
//////	    log.debug("Else block ");
//////	    mdao.updateLDCSWFURL(docBean.getConversionID(), DocTransUtil.ERROR);
//////	    bSavedToDB = true;
//////	}
//////	log.info("EXIT save SWF To DB........");
////	//return bSavedToDB;
////    }

}
