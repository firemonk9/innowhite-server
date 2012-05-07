package com.innowhite.whiteboard.docconversion.thread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
			createBatchFile();
			b = invokeProcess();
			
			copyBackToOriginalLocation();
			
			bSavedToDB = saveThumbnailsToDB(b);
			ConversionMessageListener.hTable.put(docBean.getConversionID(), true);

			log.info("IN Thread in Listener: ++++++++++++++++++++ ");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	private void createBatchFile() {
		log.info("ENTER createBatchFile...... ");
		BufferedWriter out = null; File originalFile =null;
		String tempSourceFilePath = null; String tempDestFilePath=null; int intCount =0;
		try {
			String originalFilePath = docBean != null ? docBean.getFilePath() : ""; // C:/Documents and
																					// Settings/Administrator/Desktop/Presentation1.pptx
			//originalFilePath = Z:/pptx/90/KolkataTicket.pdf
			originalFile = new File(originalFilePath);
			if (!originalFile.exists()) {
				log.error(" There is a problem wiht the file :: " + originalFilePath);
				return;
			}
			
			tempSourceFilePath = originalFile.getParent();
			tempDestFilePath = "C"+tempSourceFilePath.substring(1,tempSourceFilePath.length());
			log.info(" tempSourceFilePath :: " + tempSourceFilePath + " tempDestFilePath :: "+tempDestFilePath);
			
			intCount = copyFileOrDirectory(new File(tempSourceFilePath), new File(tempDestFilePath));
			tempDestFilePath = tempDestFilePath + separator + originalFile.getName();
			
			File f = new File(tempDestFilePath);
			if (!f.exists()) {
				log.error(" Something went wrong.  File copy failed !!!!!" +intCount+ "Files copied");
			}
			fileTransBean.setOriginalFileName(f.getName());

			String outputFolderPath = fileTransBean.getOriginalFileName() + "_files";
			String thumbsFolder = f.getParent() + separator + outputFolderPath; // C:/Documents
																				// and
																				// Settings/Administrator/Desktop/THUMBNAIL
			log.debug(" the file name :: " + f.getName() + " thumbsFolder ::  " + thumbsFolder);
		
			String finalStr = fileTransBean.getThumbsCommand().replace("#INPUT#", tempDestFilePath);
			String thumbnailBatFileContent = "";
			thumbnailBatFileContent = finalStr;
			log.info(" The command to be executed :: " + finalStr);
			
			// BatchFile path
			String originalDir = f.getParent(); 
			String thumbnailBatFilePath = originalDir + separator + "thumbs.bat"; // C:/Documents and
																				  // Settings/Administrator/Desktop/thumbs.bat
			log.info("thumbnailBatFilePath...... " + thumbnailBatFilePath);	  
			
			fileTransBean.setOriginalFilePath(originalFilePath);
			//All the below paths are pointed to C drive in windows. Update them once batch file executed.
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

		} catch (IOException ie) {
			log.error("createBatchFile " + ie);
			ie.printStackTrace();
		} catch (Exception ex) {
			log.error("createBatchFile " + ex);
			ex.printStackTrace();
		}finally {
			try {
				out.close();
			} catch (Exception e) {
				log.error("createBatchFile finally block: " + e);
			}
		}
		log.info(fileTransBean);
		log.info("EXIT createBatchFile........");
	}
	
	private int copyFileOrDirectory(File sourceFileOrDirectory, File destinationFileOrDirectory)throws IOException {
		log.info("ENTER copyFileOrDirectory...... ");
			int numberOfFilesCopied = 0; long fileSize= 0;
			try{
					if (sourceFileOrDirectory.isDirectory()) {
						  destinationFileOrDirectory.mkdirs();
						  String list[] = sourceFileOrDirectory.list();
						  for (int i = 0; i < list.length; i++) {
						    String dest1 = destinationFileOrDirectory.getPath() + File.separator + list[i];
						    String src1 = sourceFileOrDirectory.getPath() + File.separator + list[i];
						    numberOfFilesCopied += copyFileOrDirectory(new File(src1), new File(dest1));
						  }
					} else {
						if (destinationFileOrDirectory.exists()) {
							log.info("In copyFileOrDirectory.....File already exists ..FileName... "+destinationFileOrDirectory.getName());
				    		fileSize = destinationFileOrDirectory.length();
				    		if(fileSize>0){
				    			return 0;
				    		}
						}
						InputStream fin = new FileInputStream(sourceFileOrDirectory);
						  fin = new BufferedInputStream(fin);
						  try {
						    OutputStream fout = new FileOutputStream(destinationFileOrDirectory);
						    fout = new BufferedOutputStream(fout);
						    try {
						      int c;
						      while ((c = fin.read()) >= 0) {
						        fout.write(c);
						      }
						    } finally {
						      fout.close();
						    }
						  } finally {
						    fin.close();
						  }
						  numberOfFilesCopied++;
					}
					log.info("Exiting copyFileOrDirectory....numberOfFilesCopied...."+numberOfFilesCopied);
			}catch(Exception e){
				e.printStackTrace();
				log.error(" In Catch Block of copyFileOrDirectory"+e);
			}
			return numberOfFilesCopied;
	}

	private boolean invokeProcess() {
		log.info("Entered invokeProcess......");
		boolean bFlag = false;
		try {
			bFlag = new ProcessExecutor().executeProcess(fileTransBean.getThumbnailBatFilePath());
			log.info("Exiting invokeProcess......");
		} catch (Exception e) {
			log.error(e);
		}
		return bFlag;
	}
	
	private boolean copyBackToOriginalLocation(){
		log.info("Entered copyBackToOriginalLocation......");
		boolean copied = false; int intCount =0;
		try{
			String strSourceDir = fileTransBean.getOriginalDir();
			String strDestDir = "Z"+strSourceDir.substring(1,strSourceDir.length());
			intCount = copyFileOrDirectory(new File(strSourceDir), new File(strDestDir));
			if(intCount>0){
				//copied all files 
				copied= true;
			}
			fileTransBean.setOriginalDir(strDestDir);
			
			String thumbsFolderPath = fileTransBean.getThumbsFolder();
			fileTransBean.setThumbsFolder("Z"+thumbsFolderPath.substring(1,thumbsFolderPath.length()));
			fileTransBean.setThumbServerBasePath("Z"+thumbsFolderPath.substring(1,thumbsFolderPath.length()));
			fileTransBean.setOriginalServerBasePath("Z"+thumbsFolderPath.substring(1,thumbsFolderPath.length()));
			
			String thumbnailBatFilePath = fileTransBean.getThumbnailBatFilePath();
			fileTransBean.setThumbnailBatFilePath("Z"+thumbnailBatFilePath.substring(1,thumbnailBatFilePath.length()));
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(" In Catch Block of copyBackToOriginalLocation"+e);
		}
		log.info("Exiting copyBackToOriginalLocation......");
		return copied;
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

	
	public static void main(String[] args) {
		File f = new File("test.bat");
		String pathSeparator = System.getProperty(f.pathSeparator);
		String separator = System.getProperty("file.separator");
		log.debug("path separator " + pathSeparator);
		log.debug("separator " + separator);
	}
}
