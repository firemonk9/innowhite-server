package com.db.custom;


public class ImageDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		try {
//			//log.debug(getImageXMLNew("Anita", "http://127.0.0.1:5080/whiteboard/servlet/UserImagesList"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

//	public static String getImageXMLNew(String user, String serverURL) throws IOException {
//		FileWriter fw = null;
//		FileReader fr = null;
//		try {
//			Galleries gs = new Galleries();
//			Gallery g = new Gallery();
//			gs.setGallery(g);
//			UserImages ui = new UserImages();
//			ui.setUserName(user);
//			CustomUserImagesDAO dao = new CustomUserImagesDAO();
//
//			//Transaction tx = dao.getSession().beginTransaction();
//			List<UserImages> l = dao.findByUserName(user);
//
//			String imageURL = Utility.getServerURL(serverURL);
//
//			//tx.commit();
//		//	dao.getSession().close();
//		//	dao.getSession().close();
//			// int i = 0;
//			String baseURL = Utility.getServerURL(serverURL);
//			log.debug(" total number of images :" + l.size() + " serverURL:: " + serverURL);
//
//			// String privPresentationString=null;
//			// Presentation presentation = null;
//			// boolean prevPresentation = false;
//
//			Map<Integer, ArrayList<UserImages>> map = getPhotosMap(l, serverURL);
//			log.debug("  map size:: " + map.size() + "  " + map);
//			ArrayList<UserImages> photoList = (ArrayList<UserImages>) map.get(Constants.PHOT_CONT);
//			Photos photos = new Photos();
//			for (UserImages var : photoList) {
//				Photo p = getPhoto(var, imageURL);
//				photos.addPhoto(p);
//			}
//
//			ArrayList<UserImages> exerciseList = (ArrayList<UserImages>) map.get(Constants.EXER_CONT);
//			Exercises exercises = new Exercises();
//			// for (UserImages var : exerciseList) {
//			// Photo p = getPhoto(var, imageURL);
//			// exercises.addPhoto(p);
//			// //exercises.
//			// }
//
//			ArrayList<UserImages> presentationList = (ArrayList<UserImages>) map.get(Constants.PRES_CONT);
//			// Presentations presentations = new Presentations();
//			int tempId = 0;
//			Presentations presentations = new Presentations();
//			Presentation present = null;
//			for (UserImages var : presentationList) {
//				if (var.getImageGroup() != tempId) {
//					present = new Presentation();
//					presentations.addPresentation(present);
//					tempId = var.getImageGroup();
//
//					String fileName = Utility.getFileName(var.getImageUrl());
//					log.debug(" fileName:::   " + fileName);
//					log.debug(" fileName:::   " + var.getImageName());
//					present.setThumbNailsource(imageURL + var.getImageUrl());
//
//				}
//				Photo p = getPhoto(var, imageURL);
//				present.addPhoto(p);
//
//			}
//
//			Galleries galleries = new Galleries();
//			Gallery gallery = new Gallery();
//			gallery.setPhotos(photos);
//			gallery.setExercises(exercises);
//			gallery.setPresentations(presentations);
//
//			galleries.setGallery(gallery);
//
//			File f = new File(Constants.TEMP_LOCATION + user + ".xml");
//
//			log.debug("Writing to file :::" + f.getAbsolutePath());
//
//			fw = new FileWriter(f);
//			fr = new FileReader(f);
//			galleries.marshal(fw);
//
//			char[] ch = null;
//			ch = new char[(int) f.length()];
//			fr.read(ch);
//			// log.debug(ch);
//			String str = new String(ch);
//			return str;
//		} catch (Exception e) {
//			log.debug(" The error is :::  ");
//			e.printStackTrace();
//		} finally {
//			fw.close();
//			fr.close();
//		}
//		log.debug(" got error :::  ");
//		return null;
//	}

//	private static Photo getPhoto(UserImages var, String imageURL) {
//
//		Photo im = new Photo();
//		im.setDescription(var.getImageDescription());
//		im.setName(var.getUserName());
//		im.setSource(imageURL + var.getImageUrl());
//
//		im.setDocumentName(Utility.getFileName(var.getImageName()));
//		im.setImageFolderSeq(var.getImageFolderSeq());
//		im.setImageID(var.getId());
//		return im;
//
//	}

//	private static Map<Integer, ArrayList<UserImages>> getPhotosMap(List<UserImages> l) {
//
//		log.debug("Entered getPhotosList");
//		HashMap<Integer, ArrayList<UserImages>> m = new HashMap<Integer, ArrayList<UserImages>>();
//		ArrayList<UserImages> l1 = new ArrayList<UserImages>();
//		ArrayList<UserImages> l2 = new ArrayList<UserImages>();
//		ArrayList<UserImages> l3 = new ArrayList<UserImages>();
//
//		m.put(Constants.PHOT_CONT, l1);
//		m.put(Constants.PRES_CONT, l2);
//		m.put(Constants.EXER_CONT, l3);
//
//		//String imageURL = Utility.getServerURL(serverURL);
//		for (UserImages var : l) {
//
//			if (var.getImageType() == Constants.PHOT_CONT)
//				l1.add(var);
//			if (var.getImageType() == Constants.PRES_CONT)
//				l2.add(var);
//			if (var.getImageType() == Constants.EXER_CONT)
//				l3.add(var);
//
//		}
//
//		return m;
//	}

	// public static String getImageXML(String user, String serverURL) {
	//
	// Galleries gs = new Galleries();
	// Gallery g = new Gallery();
	// gs.setGallery(g);
	//
	// UserImages ui = new UserImages();
	// ui.setUserName(user);
	// CustomUserImagesDAO dao = new CustomUserImagesDAO();
	//
	// Transaction tx = dao.getSession().beginTransaction();
	// List<UserImages> l = dao.findByUserName(user);
	//		
	// String imageURL = Utility.getServerURL(serverURL);
	//
	// tx.commit();
	// dao.getSession().close();
	// int i = 0;
	//		
	// log.debug(" total number of images :"+l.size());
	//		
	// String privPresentationString=null;
	// Presentation presentation = null;
	// boolean prevPresentation = false;
	// for (UserImages var : l) {
	// // body-of-loop
	//			
	//			
	// Photo im = new Photo();
	// im.setDescription(var.getImageDescription());
	// im.setName(var.getUserName());
	// im.setSource(imageURL + var.getImageUrl());
	//			
	// im.setDocumentName(Utility.getFileName(var.getImageName()));
	// im.setImageFolderSeq(var.getImageFolderSeq());
	// im.setImageID(var.getId());
	//	
	//			
	// if(var.getImageFolder() != null && var.getImageFolder().equals("true"))
	// {
	// log.debug(var.getImageUrl());
	// String currentPresent = Utility.getFileName(var.getImageUrl());
	// // log.debug(var.getImageUrl());
	//				
	//				
	// if(privPresentationString == null ||
	// !privPresentationString.equals(currentPresent))
	// {
	// if(presentation!=null)
	// g.addPresentation(presentation);
	// i=0;
	// presentation = new Presentation();
	// presentation.setName(var.getImageDescription());
	// presentation.setDescription(var.getImageDescription());
	// presentation.setImageID(var.getId());
	// presentation.setDocumentName(var.getImageName());
	// // String folderName = var.getImageUrl().substring(0,
	// var.getImageUrl().indexOf("/"));
	// presentation.setSource(imageURL + var.getImageUrl());
	// log.debug(imageURL +"  ::  " +var.getImageUrl());
	// }
	// privPresentationString = currentPresent;
	// prevPresentation=true;
	// presentation.addPhoto(i++,im);
	// }else if(prevPresentation==true)
	// {
	// g.addPresentation(presentation);
	// prevPresentation=false;
	// presentation = null;
	// }
	// else{
	//				
	// //Utility.getThumbNailURL();
	// im.setSource(Utility.getThumbNailURL(imageURL , var.getImageUrl()));
	// presentation = null;
	// g.addPhoto(im);
	// }
	// //log.debug(var.getImageUrl()+"    "+var.getImageFolderSeq() );
	// }
	// if(presentation != null)
	// {
	// g.addPresentation(presentation);
	// }
	//		
	//		
	// char[] ch = null;
	// File f = new File(Constants.TEMP_LOCATION + user + ".xml");
	// //log.debug(f.getAbsolutePath());
	// FileWriter fw = null;
	// FileReader fr = null;
	// // String str;
	// try {
	// // DefaultHandler ch = new DefaultHandler();
	// fw = new FileWriter(f);
	// Marshaller.marshal(gs, fw);
	// ch = new char[(int) f.length()];
	// fr = new FileReader(f);
	// fr.read(ch);
	// // log.debug(ch);
	//
	// // ch.startDocument();
	// // ch.
	// // log.debug(ch.toString());
	//
	// // ch.
	//
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// //
	// // Node n = new Node();
	// catch (MarshalException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ValidationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	//
	// try {
	// fr.close();
	// fw.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	//
	// String str = new String(ch);
	//
	// return str;
	// }

}
