package com.vireka.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.documentManager.Exercises;
import com.innowhite.documentManager.Galleries;
import com.innowhite.documentManager.Gallery;
import com.innowhite.documentManager.Photo;
import com.innowhite.documentManager.Photos;
import com.innowhite.documentManager.Presentation;
import com.innowhite.documentManager.Presentations;
import com.innowhite.whiteboard.persistence.beans.UserImagesVO;
import com.innowhite.whiteboard.persistence.dao.CustomUserImagesDAO;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.util.Constants;
import com.util.Utility;

public class ImageService {

	private static Logger log = Red5LoggerFactory.getLogger(ImageService.class, "whiteboard");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// try {
		// // log.debug(getImageXMLNew("Anita",
		// // "http://127.0.0.1:5080/whiteboard/servlet/UserImageList"));
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/*
	 * This funciton returns the xml of images when it is called from dynamic
	 * upload.(run time conversion for Inet)
	 */
	public static String getSingleXMLNewByConversionId(String user, String conversionId,
			String serverURL) throws IOException {

		List<UserImagesVO> l = null;
		CustomUserImagesDAO dao = new CustomUserImagesDAO();
		l = dao.findByConversionId(conversionId);
		log.debug(" returned  "+l.size() +" records for conversionId "+conversionId+"   ");
		if (l.size() == 0) {

			StringBuffer sb = new StringBuffer();

			sb.append("<?xml version='1.0' encoding='UTF-8'?>");
			sb.append(" <response> <returnStatus>");
			sb.append(InnowhiteConstants.PROCESSING);
			sb.append("</returnStatus>  ");
			sb.append(" </response>");
			return sb.toString();
			// PrintWriter out = response.getWriter();

		} else
			return getImageXMLNew(user, serverURL,l);

	}

	public static String getSingleImageXMLbyId(int id, String serverURL) {

		CustomUserImagesDAO dao = new CustomUserImagesDAO();
		UserImagesVO userImagesVO = dao.findById(id);

		StringBuffer sb = new StringBuffer();
		if (userImagesVO != null) {

			String imageURL = Utility.getServerURL(serverURL,
					Constants.APP_NAME);

			//sb.append("<photos>");
			sb.append("<photo>");

			sb.append("<typ>Image</typ>");
			sb.append("<selected>false</selected>");
			sb.append("<name>" + userImagesVO.getUserName() + "</name>");
			sb.append("<description>" + userImagesVO.getImageDescription()
					+ "</description>");
			sb.append("<source>" + imageURL +  Constants.APP_NAME + InnowhiteConstants.FILE_SEPRATOR +   Constants.MEDIA_URL
					+ userImagesVO.getId() + "_USER</source>");
			if (userImagesVO.getThumbnailURL() != null) {
				
				sb.append("<thumbNailsource>" + imageURL + Constants.EMPTY_QUOT
						+ Constants.MEDIA_URL + userImagesVO.getId()
						+ "_USER</thumbNailsource>");
				
//				sb.append("<thumbNailsource>" + imageURL + Constants.EMPTY_QUOT
//						+ Constants.MEDIA_URL +Constants.EMPTY_QUOT+Constants.MEDIA_TYPE+Constants.EMPTY_QUOT+Constants.MEDIA_THUMBS+userImagesVO.getId()
//						+ "_USER</thumbNailsource>");
			} else {
				sb.append("<thumbNailsource>" + imageURL + Constants.EMPTY_QUOT
						+ Constants.MEDIA_URL + userImagesVO.getId()
						+ "_USER</thumbNailsource>");
			}
			sb.append("<imageFolderSeq>0</imageFolderSeq>");
			sb.append("<documentName>" + userImagesVO.getImageName()
					+ "</documentName>");
			sb.append("<imageID>" + userImagesVO.getId() + "</imageID>");
			sb.append("</photo>");
		//	sb.append("</photos>");
		}

			
		return sb.toString();
	}

	/*
	 * This funciton returns the xml of images when it is called from dynamic
	 * upload.(run time conversion for Inet)
	 */
	public static String getImageXMLNew(String user, String serverURL, List<UserImagesVO> l)
			throws IOException {
		FileWriter fw = null;
		FileReader fr = null;
		try {
			Galleries gs = new Galleries();
			Gallery g = new Gallery();
			gs.setGallery(g);
			UserImagesVO ui = new UserImagesVO();
			ui.setUserName(user);
			CustomUserImagesDAO dao = new CustomUserImagesDAO();

			// Transaction tx = dao.getSession().beginTransaction();
			// List<UserImagesVO> l=null;
			// if(conversionId != null)
			// l = dao.findByConversionId(conversionId);
			// else
			if(l == null){
				l = dao.findByUserName(user);
			}
			String imageURL = Utility.getServerURL(serverURL,
					Constants.APP_NAME);

			// tx.commit();
			// dao.getSession().close();
			// dao.getSession().close();
			// int i = 0;
			// String baseURL = Utility.getServerURL(serverURL,contextPath);
			log.debug(" total number of images :" + l.size()
					+ " serverURL:: " + serverURL);

			// String privPresentationString=null;
			// Presentation presentation = null;
			// boolean prevPresentation = false;

			Map<Integer, ArrayList<UserImagesVO>> map = getPhotosMap(l,
					serverURL);
		//	log.debug("  map size:: " + map.size() + "  " + map);
			ArrayList<UserImagesVO> photoList = (ArrayList<UserImagesVO>) map
					.get(Constants.PHOT_CONT);
			Photos photos = new Photos();
			log.debug("photos size ::" + photoList.size());

			for (UserImagesVO var : photoList) {

				Photo p = getPhoto(var, imageURL, true);
				// setThumbNail(var,imageURL);
				photos.addPhoto(p);
			}

			ArrayList<UserImagesVO> exerciseList = (ArrayList<UserImagesVO>) map
					.get(Constants.EXER_CONT);
			Exercises exercises = new Exercises();
			// for (UserImages var : exerciseList) {
			// Photo p = getPhoto(var, imageURL);
			// exercises.addPhoto(p);
			// //exercises.
			// }

			ArrayList<UserImagesVO> presentationList = (ArrayList<UserImagesVO>) map
					.get(Constants.PRES_CONT);
			// Presentations presentations = new Presentations();
			int tempId = 0;
			Presentations presentations = new Presentations();
			Presentation present = null;
			boolean firstTime = true;
			Photo firstPhoto = null;

			log.debug("entering Presentations:: ");
			String imageID=null;
			for (UserImagesVO var : presentationList) {
				if (var.getImageGroup() != tempId) {
					present = new Presentation();
					presentations.addPresentation(present);
					tempId = var.getImageGroup();

					//String fileName = Utility.getFileName(var.getImageURL());
					present.setThumbNailsource(imageURL + var.getImageURL());
					firstTime = true;

				}
				Photo p = getPhoto(var, imageURL, false);
				present.addPhoto(p);

				if (firstTime == true) {
					firstTime = false;
					// firstPhoto = p;
					present.setDocumentName(""+p.getImageID());
//					String desc = p.getDescription();
//					if (desc == null)
//						desc = p.getDocumentName();
//					present.setDescription(desc);
					present.setThumbNailsource(p.getThumbNailsource());

					if (present.getThumbNailsource() == null)
						present.setThumbNailsource(p.getSource());
					
					present.setImageID(p.getImageID());
					present.setUploadedDate(p.getUploadedDate());
					present.setDocumentTitle(p.getDocumentTitle());
					imageID = ""+p.getImageID();
					
					log.debug("++--" + present.getDocumentName() + "  "
							+ present.getDocumentTitle() + "  "
							+ present.getThumbNailsource());

				}
				p.setDocumentName(imageID);
			}

			Galleries galleries = new Galleries();
			Gallery gallery = new Gallery();
			gallery.setPhotos(photos);
			gallery.setExercises(exercises);
			gallery.setPresentations(presentations);

			galleries.setGallery(gallery);

			String tempPath = Constants.TEMP_LOCATION.replace(
					Constants.APP_NAME, Constants.APP_NAME);
			File f = new File(tempPath + user + ".xml");

			log.debug("Writing to file :::" + f.getAbsolutePath());

			fw = new FileWriter(f);
			fr = new FileReader(f);
			galleries.marshal(fw);

			char[] ch = null;
			ch = new char[(int) f.length()];
			fr.read(ch);
			// log.debug(ch);
			String str = new String(ch);
			return str;
		} catch (Exception e) {
			log.debug(" The error is :::  ");
			e.printStackTrace();
		} finally {
			try {
				fw.close();
				fr.close();
			} catch (Exception e) {

			}
		}
		log.debug(" got error :::  ");
		return null;
	}

	private static String getProcessStatusXML() {

		return null;
	}

	private static String getThumbNailUrl(String baseUrl, String imageURL,
			boolean thumbBoolean) {

		log.debug("entered getThumbNailUrl baseUrl:" + baseUrl
				+ " imageURL:" + imageURL + "  thumbBoolean" + thumbBoolean);
		String str = baseUrl + InnowhiteConstants.FILE_SEPRATOR
				+ Constants.APP_NAME + "thumbn_" + imageURL;
		if (thumbBoolean == false)
			return baseUrl + imageURL;

		String urlPath = Constants.MAC_FOLDER_PATH + "thumbn_" + imageURL;
		urlPath = urlPath.replaceAll(Constants.APP_NAME,
				InnowhiteConstants.CONTEXT_PATH);

		File f = new File(urlPath);
		String returnStr = baseUrl + InnowhiteConstants.FILE_SEPRATOR
				+ Constants.APP_NAME + imageURL;
		if (f.exists())
			returnStr = str;

		log.debug(" the thumbnail url is :"
				+ Constants.MAC_FOLDER_PATH + "thumbn_" + imageURL);

		return returnStr;
		// Utility.getActualImageName(var.getImageName());

		// return null;
	}

	private static Photo getPhoto(UserImagesVO var, String imageURL,
			boolean thumbBoolean) {

		Photo im = new Photo();
		im.setDescription(var.getImageDescription());
		im.setName(var.getUserName());
		im.setSource(imageURL + Constants.APP_NAME + Constants.MEDIA_URL
				+ var.getId() + "_USER");

		im.setThumbNailsource(imageURL + Constants.APP_NAME
				+ Constants.MEDIA_URL + var.getId() + "_USER&"+Constants.MEDIA_TYPE+Constants.EQUAL+Constants.MEDIA_THUMBS);
		
		
		im.setDocumentName(""+var.getId());
		
		
		im.setDocumentTitle(var.getImageName());
		im.setUploadedDate(var.getInsertedDate());
		im.setImageFolderSeq(var.getImageFolderSeq());
		im.setImageID(var.getId());
		return im;

	}

	private static Map<Integer, ArrayList<UserImagesVO>> getPhotosMap(
			List<UserImagesVO> l, String serverURL) {

		log.debug("Entered getPhotosList");
		HashMap<Integer, ArrayList<UserImagesVO>> m = new HashMap<Integer, ArrayList<UserImagesVO>>();
		ArrayList<UserImagesVO> l1 = new ArrayList<UserImagesVO>();
		ArrayList<UserImagesVO> l2 = new ArrayList<UserImagesVO>();
		ArrayList<UserImagesVO> l3 = new ArrayList<UserImagesVO>();

		m.put(Constants.PHOT_CONT, l1);
		m.put(Constants.PRES_CONT, l2);
		m.put(Constants.EXER_CONT, l3);

		// String imageURL = Utility.getServerURL(serverURL,contextPath);
		for (UserImagesVO var : l) {

			// log.debug("##### var::"+var);

			if (var == null)
				continue;
			if (var.getImageType() == Constants.PHOT_CONT)
				l1.add(var);
			if (var.getImageType() == Constants.PRES_CONT)
				l2.add(var);
			if (var.getImageType() == Constants.EXER_CONT)
				l3.add(var);

		}

		return m;
	}

	
	
	
	public static String getImageXMLList(List<UserImagesVO> presentationList,
			String serverURL) {

		log.debug(" entering getImageXMLList :: ");

		log.debug(" serverURL :: " + serverURL);
		int tempId = 0;
		Presentations presentations = new Presentations();
		Presentation present = null;
		boolean firstTime = true;
		FileWriter fw = null;
		FileReader fr = null;
		// Photo firstPhoto = null;
		try {
			String imageURL = Utility.getServerURL(serverURL,
					Constants.APP_NAME);

			for (UserImagesVO var : presentationList) {

				if (var.getImageGroup() != tempId) {
					present = new Presentation();
					presentations.addPresentation(present);
					tempId = var.getImageGroup();

					// String fileName = Utility.getFileName(var.getImageURL());
					present.setThumbNailsource(imageURL + var.getImageURL());
					firstTime = true;

				}
				Photo p = getPhoto(var, imageURL, false);
				present.addPhoto(p);

				if (firstTime == true) {
					firstTime = false;
					// firstPhoto = p;
					present.setDocumentName(""+p.getImageID());
					//String desc = p.getDescription();
					//if (desc == null)
						//desc = p.getDocumentName();
					//present.setDescription(desc);
					present.setThumbNailsource(p.getThumbNailsource());
					if (present.getThumbNailsource() == null)
						present.setThumbNailsource(p.getSource());
					
					present.setUploadedDate(p.getUploadedDate());
					present.setImageID(p.getImageID());
					present.setDocumentTitle(p.getDocumentTitle());
					
					log.debug("doc name ::++--" + present.getDocumentName() + "  "
							+ present.getDescription() + "  "
							+ present.getThumbNailsource());

				}
			}

			Galleries galleries = new Galleries();
			Gallery gallery = new Gallery();

			gallery.setPresentations(presentations);

			galleries.setGallery(gallery);

			String tempPath = Constants.TEMP_LOCATION.replace(
					Constants.APP_NAME, Constants.APP_NAME);
			File f = new File(tempPath + Math.rint(123123123) + ".xml");

			log.debug("Writing to file :::" + f.getAbsolutePath());

			fw = new FileWriter(f);

			fr = new FileReader(f);
			galleries.marshal(fw);

			char[] ch = null;
			ch = new char[(int) f.length()];
			fr.read(ch);
			// log.debug(ch);
			String str = new String(ch);

			log.debug(" exiting getImageXMLList :: ");
			return str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MarshalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/* This method checks if image url is null and if null trys to check if the image exists in 
	 * real from from file system. If the image exists than return the new source.
	 * */
	public static String manipulateImageSource(UserImagesVO uiVO, boolean thumbs) {
		log.debug("Entered manipulateImageSource");
		if(thumbs == false)
		{
			if(uiVO.getImageURL() == null)
			{
				String thumbsURL = uiVO.getThumbnailURL();
				if(thumbsURL != null)
				{
					log.debug(" thumbsURL "+thumbsURL+"  THUMBS "+Constants.THUMBS_PATH+" ACTUAL "+Constants.ACTUAL_PATH);
					String actualURL = thumbsURL.replace(Constants.THUMBS_PATH, Constants.ACTUAL_PATH);
					File actFile = new File(actualURL);
					if(actFile.exists())
						return actFile.getAbsolutePath();
				}
			}
		}
		return null;
	}

}
