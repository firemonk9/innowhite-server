package com.innowhite.whiteboard.docconversion.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.innowhite.whiteboard.docconversion.messages.ConversionMessageListener;
import com.innowhite.whiteboard.docconversion.persistence.dao.MessagePersistenceDAO;
import com.innowhite.whiteboard.docconversion.util.CommandExec;
import com.innowhite.whiteboard.docconversion.util.DocTransUtil;
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

	public ThumbnailThread(DocConversionBean docBean,
			FileTransformatioBean fileTransBean) {
		this.docBean = docBean;
		this.fileTransBean = fileTransBean;
	}

	private static final Logger LOG = Logger.getLogger(ThumbnailThread.class);

	public void run() {
		boolean b = false;
		boolean bSavedToDB = false;
		try {
			// createThumbnails();
			createBatchFile();
			b = invokeProcess();
			bSavedToDB = saveThumbnailsToDB(b);
			if (bSavedToDB) {
				ConversionMessageListener.hTable.put(docBean.getConversionID(),
						true);
			}

			// Thread.sleep(10000);
			LOG.info("IN Thread in Listener: ++++++++++++++++++++ ");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void createBatchFile()  throws Exception {
		LOG.info("ENTER createBatchFile...... ");
		BufferedWriter out = null;
		try {
			boolean bCreated = false;
			String originalFilePath = docBean != null ? docBean.getFilePath()
					: ""; // C:/Documents and
							// Settings/Administrator/Desktop/Presentation1.pptx
			File f = new File(originalFilePath);
			String thumbsFolder = f.getParent() + separator
					+ DocTransUtil.THUMBNAIL; // C:/Documents and
												// Settings/Administrator/Desktop/THUMBNAIL
			String thumbnailBatFileContent = "";
			
			fileTransBean.setOriginalFileName(f.getName());

			LOG.debug(" the file name :: "+f.getName()+"   "+fileTransBean.getOriginalFileName());
			
			// Batch File Content
			StringBuilder sb = new StringBuilder();
//			sb.append(" \"C:/Program Files/Ppt2SwfSDK/Samples/Precompiled/Ppt2SwfSampleCSharpConsole.exe\" ");
//			sb.append(" \"Dhiraj Peechara - Vireka LLC\" ");
//			sb.append(" \"d4f0ec7829702966119f20d2a5a74837\" -tc jpg 150 150 ");
			
			
			sb.append(fileTransBean.getThumbsCommand()+" ");
			sb.append("\"" + thumbsFolder+ "\" ");
			sb.append("\"" +  originalFilePath  + "\" ");
			sb.append(" \"\"");
			thumbnailBatFileContent = sb.toString();
			LOG.info(sb.toString());
			// BatchFile path
			String originalDir = f.getParent(); // C:/Documents and
												// Settings/Administrator/Desktop

			String thumbnailBatFilePath = originalDir + separator
					+ "thumbs.bat"; // C:/Documents and
									// Settings/Administrator/Desktop/thumbs.bat
			LOG.info("thumbnailBatFilePath...... " + thumbnailBatFilePath);
			fileTransBean.setOriginalFilePath(originalFilePath);
			fileTransBean.setThumbsFolder(thumbsFolder);
			fileTransBean.setThumbnailBatFileContent(thumbnailBatFileContent);
			fileTransBean.setOriginalDir(originalDir);
			fileTransBean.setThumbnailBatFilePath(thumbnailBatFilePath);
			
			fileTransBean.setThumbServerBasePath(docBean.getServerFilePath()+separator+DocTransUtil.THUMBNAIL);
			fileTransBean.setOriginalServerBasePath(docBean.getServerFilePath()+separator+DocTransUtil.SWF);

			File thumbFile = new File(thumbnailBatFilePath);
			if(thumbFile.createNewFile()){
				out = new BufferedWriter(new FileWriter(thumbFile));
			out.write(thumbnailBatFileContent);
			out.close();
			}else{
				throw new Exception("Can't write to file: "+ thumbnailBatFilePath);
			}

		} catch (IOException ioException) {
			LOG.error("createBatchFile " + ioException);
			ioException.printStackTrace();
		} catch (Exception exception) {
			LOG.error("createBatchFile " + exception);
			exception.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				LOG.error("createBatchFile finally block: " + e);
			}
		}
		LOG.info(fileTransBean);
		LOG.info("EXIT createBatchFile........");
	}

	private boolean invokeProcess() {
		boolean bFlag = false;
		String args[] = new String[2];
		args[0] = fileTransBean.getThumbnailBatFilePath();
		try {
			int x = CommandExec.invoke(args);
			LOG.debug("x " + x);
			if (x == 0)
				bFlag = true;
		} catch (Exception e) {
			LOG.error(e);
		}
		return bFlag;
	}

	private boolean saveThumbnailsToDB(boolean bInvoked) {
		LOG.info("ENTER saveThumbnailsToDB........");
		boolean bSavedToDB = false;
		LOG.debug("bInvoked " + bInvoked);
		MessagePersistenceDAO mdao = new MessagePersistenceDAO();
		if (bInvoked) {
			String fileArray[] = DocTransUtil.getSortedImagesArr(fileTransBean,
					DocTransUtil.THUMBNAIL);
			for (int i = 0; i < fileArray.length; i++) {
				LOG.debug(fileArray[i]);
				UserImagesVO userImagesVO = new UserImagesVO();
				LOG.debug("docbean convid "
						+ docBean.getConversionID());
				userImagesVO.setConversionID(docBean.getConversionID());
				userImagesVO.setImageFolder("true");
				userImagesVO.setUserName(docBean.getUserID());
				userImagesVO.setThumbnailURL(docBean.getServerFilePath()+"/"+docBean.getConversionID()+"/"+DocTransUtil.THUMBNAIL+"/"+fileArray[i]);
				userImagesVO.setImageName(fileTransBean.getOriginalFileName());
				userImagesVO.setImageFolderSeq(i+1);
				userImagesVO.setImageType(2);
				userImagesVO.setImageGroup(docBean.getConversionID());
				LOG.debug(userImagesVO);
				mdao.saveImage(userImagesVO);
			}

			mdao.updateLDCThumbnailURL(docBean.getConversionID(),
					DocTransUtil.CREATED);
			bSavedToDB = true;
		} else {
			LOG.debug("Else block ");
			mdao.updateLDCThumbnailURL(docBean.getConversionID(),
					DocTransUtil.ERROR);
			bSavedToDB = true;
		}
		LOG.info("EXIT saveThumbnailsToDB........");
		return bSavedToDB;
	}

	private boolean createThumbnails() {
		LOG.debug("ENTER createThumbnails.. ");
		boolean bCreated = false;
		String originalFilePath = docBean != null ? docBean.getFilePath() : "";
		LOG.debug("filePath: " + originalFilePath);
		File f = new File(originalFilePath);
		String outFolder = f.getParent() + separator + DocTransUtil.THUMBNAIL;

		LOG.debug("outFolder: " + outFolder);
		MessagePersistenceDAO mdao = new MessagePersistenceDAO();
		if (execCommandLine(originalFilePath, outFolder)) {
			String fileArray[] = DocTransUtil.getThumbnails(originalFilePath,
					DocTransUtil.THUMBNAIL);
			for (int i = 0; i < fileArray.length; i++) {
				UserImagesVO userImagesVO = new UserImagesVO();
				userImagesVO.setConversionID(docBean.getConversionID());
				userImagesVO.setImageFolder("true");
				userImagesVO.setUserName(docBean.getUserID());
				userImagesVO.setThumbnailURL(fileArray[i]);
				userImagesVO.setImageName(fileTransBean.getOriginalFileName());
				userImagesVO.setImageFolderSeq(i);
				userImagesVO.setImageType(2);
				userImagesVO.setImageGroup(docBean.getConversionID());
				mdao.saveImage(userImagesVO);
			}

			mdao.updateLDCThumbnailURL(docBean.getConversionID(),
					DocTransUtil.CREATED);

		} else {
			mdao.updateLDCThumbnailURL(docBean.getConversionID(),
					DocTransUtil.ERROR);
		}
		LOG.debug("EXIT createThumbnails.. ");
		return bCreated;
	}

	private boolean execCommandLine(String originalFilePath, String outfolder) {
		LOG.debug("ENTER execCommandLine.. ");
		LOG.debug("arguments: originalFilePath: " + originalFilePath
				+ " outfolder: " + outfolder);
		boolean bExecuted = false;
		StringBuilder sb = new StringBuilder();
		sb.append(" C:/Program Files/Ppt2SwfSDK/SDK/Ppt2SwfSampleCSharpConsole.exe ");
		sb.append(" \"Dhiraj Peechara - Vireka LLC \" ");
		sb.append(" \"d4f0ec7829702966119f20d2a5a74837\" -t jpg 600 800 ");
		sb.append("\"" + outfolder + "\" ");
		sb.append("\"" + originalFilePath + "\" ");
		File f = new File(originalFilePath);
		String dir = f.getParent();
		LOG.debug("dir: " + dir);
		String folderName = f.getName();
		LOG.debug("folderName: " + folderName);
		String batchFile = dir + separator + folderName + separator
				+ "thumbs.bat";
		LOG.debug("batch file syntax : " + dir + separator
				+ "thumbs.bat");
		String folder = dir + separator + DocTransUtil.THUMBNAIL + separator
				+ folderName;
		LOG.debug("folder: " + folder);
		try {
			File f1 = new File(folder);
			Boolean bFolderCreated = f1.mkdirs();
			LOG.debug("Folder created " + bFolderCreated);
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(
					batchFile)));
			out.write(sb.toString());
			out.close();
			String args[] = new String[10];
			args[0] = batchFile;
			int x = CommandExec.invoke(args);
			if (x == 0) {
				bExecuted = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ea) {
			ea.printStackTrace();
		}
		LOG.debug("EXIT execCommandLine.. ");
		// C:\Program Files\Ppt2SwfSDK\SDK>Ppt2SwfSampleCSharpConsole.exe
		// "Dhiraj Peechara - Vireka LLC" "d4f0ec7829702966119f20d2a5a74837" -t
		// jpg 600 800 "C:\Users\Administrator\Desktop\thumbs"
		// "C:\Users\Administrator\Desktop\Presentation1.pptx"
		return bExecuted;
	}

	/*
	 * private String[] getThumbnails(){ String filePath =
	 * docBean.getFilePath(); File f = new File(filePath); String directoryPath
	 * = f.getParent(); File dir = new File(directoryPath); String fileArray[] =
	 * dir.list(); String sortedFileArray[] = new String[fileArray.length]; for
	 * (int i = 0; i < fileArray.length; i++) {
	 * if(fileArray[i].startsWith(thumbnail)){ String t[] =
	 * fileArray[i].split("\\."); String fileNumber =
	 * t[0].substring(thumbnail.length(),t[0].length()+1); int jpgNumber =
	 * DocTransUtil.convertToInt(fileNumber); sortedFileArray[jpgNumber] =
	 * directoryPath + File.pathSeparator + fileArray[i]; } } return
	 * sortedFileArray; }
	 * 
	 * 
	 * 
	 * public static void main(String[] args) { String t[] =
	 * "thumbnail12234234234234234.jpg".split("\\."); int y = t[0].length(); int
	 * x = thumbnail.length(); LOG.debug(x + " : "+ y + " t "+t[0]);
	 * String fileNumber = t[0].substring(x,y);
	 * LOG.debug("Filenumber "+fileNumber); }
	 */

	public static void main(String[] args) {
		File f = new File("test.bat");
		String pathSeparator = System.getProperty(f.pathSeparator);
		String separator = System.getProperty("file.separator");
		LOG.debug("path separator " + pathSeparator);
		LOG.debug("separator " + separator);
	}
}
