package com.innowhite.whiteboard.docconversion.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.innowhite.whiteboard.docconversion.messages.ConversionMessageListener;
import com.innowhite.whiteboard.docconversion.persistence.dao.MessagePersistenceDAO;
import com.innowhite.whiteboard.docconversion.util.DocTransUtil;
import com.innowhite.whiteboard.docconversion.util.ProcessExecutor;
import com.innowhite.whiteboard.docconversion.vo.DocConversionBean;
import com.innowhite.whiteboard.docconversion.vo.FileTransformatioBean;
import com.innowhite.whiteboard.docconversion.vo.UserImagesVO;

public class ThumbnailThread extends Thread {

	private DocConversionBean docBean = null;
	private FileTransformatioBean fileTransBean = null;

	static String separator = System.getProperty("file.separator");

	public ThumbnailThread() {
		// TODO Auto-generated constructor stub
	}

	public ThumbnailThread(DocConversionBean docBean, FileTransformatioBean fileTransBean) {
		this.docBean = docBean;
		this.fileTransBean = fileTransBean;
	}

	private static final Logger log = Logger.getLogger(ThumbnailThread.class);

	public void run() {
		boolean b = false;
		boolean bSavedToDB = false;
		try {
			// createThumbnails();
			createBatchFile();
			b = invokeProcess();
			bSavedToDB = saveThumbnailsToDB(b);
			// if (bSavedToDB) {
			ConversionMessageListener.hTable.put(docBean.getConversionID(), true);
			// }

			// Thread.sleep(10000);
			log.info("IN Thread in Listener: ++++++++++++++++++++ ");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void createBatchFile() throws Exception {
		log.info("ENTER createBatchFile...... ");
		BufferedWriter out = null;
		try {
			// boolean bCreated = false;
			String originalFilePath = docBean != null ? docBean.getFilePath() : ""; // C:/Documents
																					// and
																					// Settings/Administrator/Desktop/Presentation1.pptx
			File f = new File(originalFilePath);
			if (!f.exists()) {
				log.error(" There is a problem wiht the file :: " + originalFilePath);
				return;
			}
			fileTransBean.setOriginalFileName(f.getName());

			String outputFolderPath = fileTransBean.getOriginalFileName() + "_files";
			String thumbsFolder = f.getParent() + separator + outputFolderPath; // C:/Documents
																				// and
																				// Settings/Administrator/Desktop/THUMBNAIL
			String thumbnailBatFileContent = "";

			log.debug(" the file name :: " + f.getName() + "   " + fileTransBean.getOriginalFileName());
			// Batch File Content
			StringBuilder sb = new StringBuilder();
			// sb.append(" \"C:/Program Files/Ppt2SwfSDK/Samples/Precompiled/Ppt2SwfSampleCSharpConsole.exe\" ");
			// sb.append(" \"Dhiraj Peechara - Vireka LLC\" ");
			// sb.append(" \"d4f0ec7829702966119f20d2a5a74837\" -tc jpg 150 150 ");

			String finalStr = fileTransBean.getThumbsCommand().replace("#INPUT#", originalFilePath);

			// sb.append(fileTransBean.getThumbsCommand() + " ");
			// sb.append("\"" + thumbsFolder + "\" ");
			// sb.append("\"" + originalFilePath + "\" ");
			// sb.append(" \"\"");
			thumbnailBatFileContent = finalStr;
			log.info(" The command to be executed :: " + finalStr);
			// BatchFile path
			String originalDir = f.getParent(); // C:/Documents and
			// Settings/Administrator/Desktop

			String thumbnailBatFilePath = originalDir + separator + "thumbs.bat"; // C:/Documents
			// and
			// Settings/Administrator/Desktop/thumbs.bat
			log.info("thumbnailBatFilePath...... " + thumbnailBatFilePath);
			fileTransBean.setOriginalFilePath(originalFilePath);
			fileTransBean.setThumbsFolder(thumbsFolder);
			fileTransBean.setThumbnailBatFileContent(thumbnailBatFileContent);
			fileTransBean.setOriginalDir(originalDir);
			fileTransBean.setThumbnailBatFilePath(thumbnailBatFilePath);

			fileTransBean.setThumbServerBasePath(f.getParent() + separator + outputFolderPath);
			fileTransBean.setOriginalServerBasePath(f.getParent() + separator + outputFolderPath);

			File thumbFile = new File(thumbnailBatFilePath);
			if (thumbFile.createNewFile()) {
				out = new BufferedWriter(new FileWriter(thumbFile));
				out.write(thumbnailBatFileContent);
				out.close();
			} else {
				throw new Exception("Can't write to file: " + thumbnailBatFilePath);
			}

		} catch (IOException ioException) {
			log.error("createBatchFile " + ioException);
			ioException.printStackTrace();
		} catch (Exception exception) {
			log.error("createBatchFile " + exception);
			exception.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				log.error("createBatchFile finally block: " + e);
			}
		}
		log.info(fileTransBean);
		log.info("EXIT createBatchFile........");
	}

	private boolean invokeProcess() {
		boolean bFlag = false;
		// String args[] = new String[2];
		// args[0] = fileTransBean.getThumbnailBatFilePath();
		try {
			bFlag = new ProcessExecutor().executeProcess(fileTransBean.getThumbnailBatFilePath());
			// log.debug("x " + x);

		} catch (Exception e) {
			log.error(e);
		}
		return bFlag;
	}

	private boolean saveThumbnailsToDB(boolean bInvoked) {
		log.info("ENTER saveThumbnailsToDB........");
		boolean bSavedToDB = false;
		log.debug("bInvoked " + bInvoked);

		MessagePersistenceDAO mdao = new MessagePersistenceDAO();

		// if (bInvoked) {
		List<File> fileArrayThumb = DocTransUtil.getSortedImagesArr(fileTransBean, DocTransUtil.THUMBNAIL);
		List<File> fileArraySwf = DocTransUtil.getSortedImagesArr(fileTransBean, DocTransUtil.SWF);

		log.debug(" Total thumbnails : "+fileArrayThumb.size()+" fileArraySwf size  "+fileArraySwf.size());
		
		String path = docBean.getServerFilePath() + "/" + docBean.getConversionID() + "/" +  fileTransBean.getOriginalFileName()+"_files" +"/"  ;
		
		for (int i = 0; i < fileArrayThumb.size(); i++) {
			// log.debug(fileArray.[i]);
			UserImagesVO userImagesVO = new UserImagesVO();
			log.debug("docbean convid " + docBean.getConversionID());
			userImagesVO.setConversionID(docBean.getConversionID());
			userImagesVO.setImageFolder("true");
			userImagesVO.setUserName(docBean.getUserID());

			userImagesVO.setThumbnailURL(path + fileArrayThumb.get(i).getName());
			userImagesVO.setImageURL(path + fileArraySwf.get(i).getName());

			userImagesVO.setImageName(fileTransBean.getOriginalFileName());
			userImagesVO.setImageFolderSeq(i + 1);
			userImagesVO.setImageType(2);
			userImagesVO.setImageGroup(docBean.getConversionID());
			log.debug(userImagesVO);
			mdao.saveImage(userImagesVO);
		}

		mdao.updateLDCThumbnailURL(docBean.getConversionID(), DocTransUtil.CREATED);
		mdao.updateLDCSWFURL(docBean.getConversionID(), DocTransUtil.CREATED);
		
		bSavedToDB = true;
		
		log.info("EXIT saveThumbnailsToDB........");
		return bSavedToDB;
	}

//	
//	private String getDocParentFolderName(String absPath){
//		
//		
//		return null;
//	}
	
	
	public static void main(String[] args) {
		File f = new File("test.bat");
		String pathSeparator = System.getProperty(f.pathSeparator);
		String separator = System.getProperty("file.separator");
		log.debug("path separator " + pathSeparator);
		log.debug("separator " + separator);
	}
}
