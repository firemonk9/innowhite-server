package com.innowhite.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.UserImagesVO;
import com.innowhite.whiteboard.persistence.dao.CustomUserImagesDAO;
import com.innowhite.whiteboard.service.ImageService;
import com.innowhite.whiteboard.util.Constants;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.innowhite.whiteboard.util.InnowhiteProperties;
import com.innowhite.whiteboard.util.MakeExectuable;
import com.innowhite.whiteboard.util.Utility;

/*
 * This class takes a pdf document as input and creates swf file for each page.
 * */
public class MakeSWFFiles {

	private static final Logger log = LoggerFactory.getLogger(MakeSWFFiles.class);
	
	private static void createImagesfromPDF(String inputFile, String user, String desc, String documentName) {

		String[] Command = null;

		try {

			log.debug(" entered  createImagesfromPDF");

			String fileName = Utility.stripExtension(inputFile);

			StringBuffer sr = new StringBuffer();

			String ext = new String("" + Math.round((Math.random() * 1000000)));

			String modifiedFileName = fileName + "_" + ext;

			String urlPath = Constants.UBUNTU_FOLDER_PATH_COMMAND + modifiedFileName;
			urlPath = urlPath.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);

			String urlPathUbun = Constants.UBUNTU_FOLDER_PATH_COMMAND;
			urlPath = urlPath.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);

			// String outPutDir = urlPath;

			boolean val = (new File(urlPath)).mkdir();

			sr.append("convert" + " " + urlPathUbun + inputFile + " " + urlPathUbun + modifiedFileName + "//" + modifiedFileName + ".png");

			sr.append("\n");

			sr.append("convert \"" + urlPathUbun + modifiedFileName + "//" + modifiedFileName + "-0" + ".png\""
					+ " -thumbnail 250x90 -unsharp 0x.5 \"" + urlPathUbun + modifiedFileName + "//" + "thumbnail" + ".png\"");

			urlPath = Constants.TEMP_LOCATION + ext + ".sh";
			urlPath = urlPath.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);

			File f = new File(urlPath);
			FileOutputStream fos = new FileOutputStream(f);
			// char[] c = new char(sr);
			fos.write(sr.toString().getBytes());
			fos.close();

			MakeExectuable.getInstance().setExecutable(f.getAbsolutePath());

			log.debug(" executing command  " + f.getAbsolutePath());

			Command = new String[1];
			Command[0] = f.getAbsolutePath();

			Process Findspace = Runtime.getRuntime().exec(Command);

			BufferedReader Resultset = new BufferedReader(new InputStreamReader(Findspace.getInputStream()));

			String line;
			while ((line = Resultset.readLine()) != null) {
				log.debug(" out put from command execution  " + line);
			}

			// addImagestoDB(modifiedFileName, user, desc, documentName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	public static String createSwfFiles(String inputFile, String user, String desc, String documentName, boolean saveInDB, String serverURL, String pdfFileAbsPath, String origFileName, boolean isInet) {
		//String serverURL=null;
	
		log.debug(" entered createSwfFiles ");
		List<UserImagesVO> l = createSwfFilesActual(inputFile, user, desc, documentName,  saveInDB,pdfFileAbsPath,origFileName,isInet);
		
		log.debug(" exiting createSwfFiles ");
		if(saveInDB == true)
			return null;
		else
		{
			return ImageService.getImageXMLList(l,serverURL);
		}
	}


	private static List<UserImagesVO> createSwfFilesActual(String inputFile, String user, String desc, String documentName, boolean saveInDB, String pdfFileAbsPath, String origFileName,boolean isInet) {

		log.debug(" entered createSwfFilesActual ");
		
		log.debug(" inputFile: " + inputFile + " user: " + user + "  desc: " + desc + "  documentName: " + documentName+"    pdfFileAbsPath:  "+pdfFileAbsPath );

		String[] Command = null;

		String fileName = Utility.stripExtension(inputFile);

		StringBuffer sr = new StringBuffer();

		String ext = new String("" + Math.round((Math.random() * 1000000)));

		String modifiedFolderName = fileName + "_" + ext;

		String urlPath = Constants.UBUNTU_FOLDER_PATH_COMMAND + modifiedFolderName;
		urlPath = urlPath.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);

		String urlPathUbun = Constants.UBUNTU_FOLDER_PATH_COMMAND;
		//urlPath = urlPath.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);
		urlPath = InnowhiteProperties.getPropertyVal(InnowhiteConstants.USER_UPLOADED_FILES)+fileName;

		//String outPutDir = urlPath;
		String outPutDir =  InnowhiteProperties.getPropertyVal(InnowhiteConstants.USER_UPLOADED_FILES);
		
		
		origFileName = Utility.putBackSpace(origFileName);

		boolean val = (new File(urlPath)).mkdir();
		List<UserImagesVO> dbobj = new ArrayList<UserImagesVO>();
		/*
		 * sr.append("convert" + " " + Constants.UBUNTU_FOLDER_PATH_COMMAND +
		 * inputFile + " " + Constants.UBUNTU_FOLDER_PATH_COMMAND +
		 * modifiedFileName + "//" + modifiedFileName + ".png");
		 */

		sr.append("identify -format %n " + pdfFileAbsPath);

		sr.append("\n");

		urlPath = Constants.TEMP_LOCATION + ext + ".sh";
		urlPath = urlPath.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);

		File f = new File(urlPath);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(f);
			fos.write(sr.toString().getBytes());
			fos.close();

			MakeExectuable.getInstance().setExecutable(f.getAbsolutePath());

			Command = new String[1];
			Command[0] = f.getAbsolutePath();

			Process Findspace = Runtime.getRuntime().exec(Command);

			BufferedReader Resultset = new BufferedReader(new InputStreamReader(Findspace.getInputStream()));

			String line;
			int numberOfSlides = 0;
			while ((line = Resultset.readLine()) != null) {
				log.debug(" out put from command execution  " + line);

				String str = line.substring(0, line.length());
				str = str.trim();
				log.debug(str + "" + "hgj");
				numberOfSlides = Integer.parseInt(str);
				break;

			}
			log.debug(" total nmber of pages " + numberOfSlides);
			ext = new String("" + Math.round((Math.random() * 1000000)));

			urlPath = Constants.TEMP_LOCATION + ext + ".sh";
			urlPath = urlPath.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);

			File ffile = new File(urlPath);
			fos = new FileOutputStream(ffile);
			sr = new StringBuffer();

		

			for (int i = 1; i <= numberOfSlides; i++) {
				UserImagesVO ui = new UserImagesVO(user, outPutDir + "//" + fileName + "_" + ext + "-" + i + ".swf", 0);
				ui.setImageFolderSeq(i);
				ui.setImageFolder("true");
				ui.setImageName(origFileName);
				ui.setImageType(2);
				ui.setImageGroup(Integer.parseInt(ext));
				dbobj.add(ui);
				sr.append("pdf2swf -o " + outPutDir + "//" + fileName + "_" + ext + "-" + i + ".swf -p " + i + " " + pdfFileAbsPath + "\n");

			}
			fos.write(sr.toString().getBytes());
			fos.close();
			MakeExectuable.getInstance().setExecutable(ffile.getAbsolutePath());

			Command = new String[1];
			Command[0] = ffile.getAbsolutePath();

			Process proc = Runtime.getRuntime().exec(Command);
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// log.debug(" exit value "+exitVal);
			Resultset = new BufferedReader(new InputStreamReader(Findspace.getInputStream()));

			// numberOfSlides = 0;

			sr = new StringBuffer();

			log.debug("  entering check conversion... ");

			for (int i = 1; i <= numberOfSlides; i++) {
				File tempFile = new File(outPutDir + "//" + fileName + "_" + ext + "-" + i + ".swf");
				log.debug(" created :: " + tempFile.getAbsolutePath());
				if (tempFile.exists() == false) {

					//log.debug(" file does not exist:: " + tempFile.getAbsolutePath());
					sr.append("pdf2swf -O 2 -o " + outPutDir + "//" + fileName + "_" + ext + "-" + i + ".swf -p " + i + " " + pdfFileAbsPath
							+ "\n");
				}

			}
			if (sr != null && sr.length() > 0) {

				log.debug(" Error in conversion form PDF to swf so converting in decrease quality ");
				fos = new FileOutputStream(ffile);
				fos.write(sr.toString().getBytes());
				fos.close();
				Command = new String[1];
				Command[0] = ffile.getAbsolutePath();

				Findspace = Runtime.getRuntime().exec(Command);

			}
			CustomUserImagesDAO dao = new CustomUserImagesDAO();

			// Transaction tx = dao.getSession().beginTransaction();
			
			
		//	List l = new ArrayList<E>;
			if (saveInDB == true || isInet == true) {
				for (UserImagesVO obj : dbobj) {
					int id = dao.save((UserImagesVO) obj);
					obj.setId(id);
				}
			}
			// tx.commit();
			// dao.getSession().close();

			log.debug(" exiting createSwfFilesActual ");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// char[] c = new char(sr);
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ProcessConversion.addImagestoDB(modifiedFolderName, user, desc,
		// documentName);
		return dbobj;

	}

	
}
