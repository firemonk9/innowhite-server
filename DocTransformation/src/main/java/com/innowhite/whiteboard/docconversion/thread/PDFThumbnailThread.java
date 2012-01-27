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
import com.innowhite.whiteboard.docconversion.vo.UserImagesVO;

public class PDFThumbnailThread extends Thread {

//    private DocConversionBean docBean = null;
//    private FileTransformatioBean fileTransBean = null;
//
//    static String separator = System.getProperty("file.separator");
//
//    public PDFThumbnailThread() {
//	// TODO Auto-generated constructor stub
//    }
//
//    public PDFThumbnailThread(DocConversionBean docBean, FileTransformatioBean fileTransBean) {
//	this.docBean = docBean;
//	this.fileTransBean = fileTransBean;
//    }
//
//    private static final Logger log = Logger.getLogger(PDFThumbnailThread.class);
//
//    public void run() {
//	boolean b = false;
//	boolean bSavedToDB = false;
//	try {
//	    // createThumbnails();
//	    createBatchFile();
//	    b = invokeProcess();
//	    bSavedToDB = saveThumbnailsToDB(b);
//	    if (bSavedToDB) {
//		ConversionMessageListener.hTable.put(docBean.getConversionID(), true);
//	    }
//
//	    // Thread.sleep(10000);
//	    log.info("IN Thread in Listener: ++++++++++++++++++++ ");
//
//	} catch (Exception e) {
//
//	    e.printStackTrace();
//	}
//
//    }
//
//    private void createBatchFile() throws Exception {
//	log.info("ENTER createBatchFile...... ");
//	BufferedWriter out = null;
//	try {
//	    boolean bCreated = false;
//	    String originalFilePath = docBean != null ? docBean.getFilePath() : ""; // C:/Documents
//										    // and
//										    // Settings/Administrator/Desktop/Presentation1.pptx
//	    File f = new File(originalFilePath);
//	    String thumbsFolder = f.getParent() + separator + DocTransUtil.THUMBNAIL; // C:/Documents
//										      // and
//										      // Settings/Administrator/Desktop/THUMBNAIL
//	    String thumbnailBatFileContent = "";
//
//	    fileTransBean.setOriginalFileName(f.getName());
//
//	    log.debug(" the file name :: " + f.getName() + "   " + fileTransBean.getOriginalFileName());
//
//	    // Batch File Content
//	    StringBuilder sb = new StringBuilder();
//	    // sb.append(" \"C:/Program Files/Ppt2SwfSDK/Samples/Precompiled/Ppt2SwfSampleCSharpConsole.exe\" ");
//	    // sb.append(" \"Dhiraj Peechara - Vireka LLC\" ");
//	    // sb.append(" \"d4f0ec7829702966119f20d2a5a74837\" -tc jpg 150 150 ");
//
//	    sb.append(fileTransBean.getThumbsCommand() + " ");
//	    sb.append("\"" + thumbsFolder + "\" ");
//	    sb.append("\"" + originalFilePath + "\" ");
//	    sb.append(" \"\"");
//	    thumbnailBatFileContent = sb.toString();
//	    log.info(sb.toString());
//	    // BatchFile path
//	    String originalDir = f.getParent(); // C:/Documents and
//						// Settings/Administrator/Desktop
//
//	    String thumbnailBatFilePath = originalDir + separator + "thumbs.bat"; // C:/Documents
//										  // and
//										  // Settings/Administrator/Desktop/thumbs.bat
//	    log.info("thumbnailBatFilePath...... " + thumbnailBatFilePath);
//	    fileTransBean.setOriginalFilePath(originalFilePath);
//	    fileTransBean.setThumbsFolder(thumbsFolder);
//	    fileTransBean.setThumbnailBatFileContent(thumbnailBatFileContent);
//	    fileTransBean.setOriginalDir(originalDir);
//	    fileTransBean.setThumbnailBatFilePath(thumbnailBatFilePath);
//
//	    fileTransBean.setThumbServerBasePath(docBean.getServerFilePath() + separator + DocTransUtil.THUMBNAIL);
//	    fileTransBean.setOriginalServerBasePath(docBean.getServerFilePath() + separator + DocTransUtil.SWF);
//
//	    File thumbFile = new File(thumbnailBatFilePath);
//	    if (thumbFile.createNewFile()) {
//		out = new BufferedWriter(new FileWriter(thumbFile));
//		out.write(thumbnailBatFileContent);
//		out.close();
//	    } else {
//		throw new Exception("Can't write to file: " + thumbnailBatFilePath);
//	    }
//
//	} catch (IOException ioException) {
//	    log.error("createBatchFile " + ioException);
//	    ioException.printStackTrace();
//	} catch (Exception exception) {
//	    log.error("createBatchFile " + exception);
//	    exception.printStackTrace();
//	} finally {
//	    try {
//		out.close();
//	    } catch (Exception e) {
//		log.error("createBatchFile finally block: " + e);
//	    }
//	}
//	log.info(fileTransBean);
//	log.info("EXIT createBatchFile........");
//    }
//
//    private boolean invokeProcess() {
//	boolean bFlag = false;
//	// String args[] = new String[2];
//	// args[0] = fileTransBean.getThumbnailBatFilePath();
//	try {
//	    bFlag = new ProcessExecutor().executeProcess(fileTransBean.getThumbnailBatFilePath());
//	    // log.debug("x " + x);
//
//	} catch (Exception e) {
//	    log.error(e);
//	}
//	return bFlag;
//    }
//
//    private boolean saveThumbnailsToDB(boolean bInvoked) {
//	log.info("ENTER saveThumbnailsToDB........");
//	boolean bSavedToDB = false;
//	log.debug("bInvoked " + bInvoked);
//	MessagePersistenceDAO mdao = new MessagePersistenceDAO();
//	if (bInvoked) {
//	    String fileArray[] = DocTransUtil.getSortedImagesArr(fileTransBean, DocTransUtil.THUMBNAIL);
//	    for (int i = 0; i < fileArray.length; i++) {
//		log.debug(fileArray[i]);
//		UserImagesVO userImagesVO = new UserImagesVO();
//		log.debug("docbean convid " + docBean.getConversionID());
//		userImagesVO.setConversionID(docBean.getConversionID());
//		userImagesVO.setImageFolder("true");
//		userImagesVO.setUserName(docBean.getUserID());
//		userImagesVO.setThumbnailURL(docBean.getServerFilePath() + "/" + docBean.getConversionID() + "/" + DocTransUtil.THUMBNAIL + "/" + fileArray[i]);
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
//    private boolean createThumbnails() {
//	log.debug("ENTER createThumbnails.. ");
//	boolean bCreated = false;
//	String originalFilePath = docBean != null ? docBean.getFilePath() : "";
//	log.debug("filePath: " + originalFilePath);
//	File f = new File(originalFilePath);
//	String outFolder = f.getParent() + separator + DocTransUtil.THUMBNAIL;
//
//	log.debug("outFolder: " + outFolder);
//	MessagePersistenceDAO mdao = new MessagePersistenceDAO();
//	//if (execCommandLine(originalFilePath, outFolder)) {
//	    String fileArray[] = DocTransUtil.getThumbnails(originalFilePath, DocTransUtil.THUMBNAIL);
//	    for (int i = 0; i < fileArray.length; i++) {
//		UserImagesVO userImagesVO = new UserImagesVO();
//		userImagesVO.setConversionID(docBean.getConversionID());
//		userImagesVO.setImageFolder("true");
//		userImagesVO.setUserName(docBean.getUserID());
//		userImagesVO.setThumbnailURL(fileArray[i]);
//		userImagesVO.setImageName(fileTransBean.getOriginalFileName());
//		userImagesVO.setImageFolderSeq(i);
//		userImagesVO.setImageType(2);
//		userImagesVO.setImageGroup(docBean.getConversionID());
//		mdao.saveImage(userImagesVO);
//	    }
//
//	    mdao.updateLDCThumbnailURL(docBean.getConversionID(), DocTransUtil.CREATED);
//
//	//} else {
//	 //   mdao.updateLDCThumbnailURL(docBean.getConversionID(), DocTransUtil.ERROR);
//	//}
//	log.debug("EXIT createThumbnails.. ");
//	return bCreated;
//    }
//
////    private boolean execCommandLine(String originalFilePath, String outfolder) {
////	log.debug("ENTER execCommandLine.. ");
////	log.debug("arguments: originalFilePath: " + originalFilePath + " outfolder: " + outfolder);
////	boolean bExecuted = false;
////	StringBuilder sb = new StringBuilder();
////	sb.append(" C:/Program Files/Ppt2SwfSDK/SDK/Ppt2SwfSampleCSharpConsole.exe ");
////	sb.append(" \"Dhiraj Peechara - Vireka LLC \" ");
////	sb.append(" \"d4f0ec7829702966119f20d2a5a74837\" -t jpg 600 800 ");
////	sb.append("\"" + outfolder + "\" ");
////	sb.append("\"" + originalFilePath + "\" ");
////	File f = new File(originalFilePath);
////	String dir = f.getParent();
////	log.debug("dir: " + dir);
////	String folderName = f.getName();
////	log.debug("folderName: " + folderName);
////	String batchFile = dir + separator + folderName + separator + "thumbs.bat";
////	log.debug("batch file syntax : " + dir + separator + "thumbs.bat");
////	String folder = dir + separator + DocTransUtil.THUMBNAIL + separator + folderName;
////	log.debug("folder: " + folder);
////	try {
////	    File f1 = new File(folder);
////	    Boolean bFolderCreated = f1.mkdirs();
////	    log.debug("Folder created " + bFolderCreated);
////	    BufferedWriter out = new BufferedWriter(new FileWriter(new File(batchFile)));
////	    out.write(sb.toString());
////	    out.close();
////
////	    bExecuted = new ProcessExecutor().executeProcess(batchFile);
////
////	} catch (IOException e) {
////	    e.printStackTrace();
////	} catch (Exception ea) {
////	    ea.printStackTrace();
////	}
////	log.debug("EXIT execCommandLine.. ");
////	// C:\Program Files\Ppt2SwfSDK\SDK>Ppt2SwfSampleCSharpConsole.exe
////	// "Dhiraj Peechara - Vireka LLC" "d4f0ec7829702966119f20d2a5a74837" -t
////	// jpg 600 800 "C:\Users\Administrator\Desktop\thumbs"
////	// "C:\Users\Administrator\Desktop\Presentation1.pptx"
////	return bExecuted;
////    }
//
//    /*
//     * private String[] getThumbnails(){ String filePath =
//     * docBean.getFilePath(); File f = new File(filePath); String directoryPath
//     * = f.getParent(); File dir = new File(directoryPath); String fileArray[] =
//     * dir.list(); String sortedFileArray[] = new String[fileArray.length]; for
//     * (int i = 0; i < fileArray.length; i++) {
//     * if(fileArray[i].startsWith(thumbnail)){ String t[] =
//     * fileArray[i].split("\\."); String fileNumber =
//     * t[0].substring(thumbnail.length(),t[0].length()+1); int jpgNumber =
//     * DocTransUtil.convertToInt(fileNumber); sortedFileArray[jpgNumber] =
//     * directoryPath + File.pathSeparator + fileArray[i]; } } return
//     * sortedFileArray; }
//     * 
//     * 
//     * 
//     * public static void main(String[] args) { String t[] =
//     * "thumbnail12234234234234234.jpg".split("\\."); int y = t[0].length(); int
//     * x = thumbnail.length(); LOG.debug(x + " : "+ y + " t "+t[0]); String
//     * fileNumber = t[0].substring(x,y); LOG.debug("Filenumber "+fileNumber); }
//     */
//
//    public static void main(String[] args) {
//	File f = new File("test.bat");
//	String pathSeparator = System.getProperty(f.pathSeparator);
//	String separator = System.getProperty("file.separator");
//	log.debug("path separator " + pathSeparator);
//	log.debug("separator " + separator);
//    }
}
