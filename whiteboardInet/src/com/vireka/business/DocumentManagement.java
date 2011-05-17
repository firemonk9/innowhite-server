package com.vireka.business;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import com.innowhite.whiteboard.persistence.beans.UserImagesVO;
import com.innowhite.whiteboard.persistence.dao.CustomUserImagesDAO;
import com.util.MakeExectuable;

public class DocumentManagement {
	private static Logger log = Red5LoggerFactory.getLogger(DocumentManagement.class, "whiteboard");
	
	public static void main(String args[])
	{
		
		CustomUserImagesDAO dao = new CustomUserImagesDAO();
		//dao.deleteById(76);
		//dao.deleteByImageGroup(729217,"Victor");
		deleteDocuments(46);
	} 
	
	
	public static String deleteDocuments(int id)
	{
		
		CustomUserImagesDAO dao = new CustomUserImagesDAO();
		//UserImagesDAO dao = new UserImagesDAO();
	//	Transaction tx = dao.getSession().beginTransaction(); 
		UserImagesVO l = dao.findById(id);
		
		log.debug(" imgfolder  "+l.getImageFolder());
		
		if(l.getImageFolder() ==  null)
		{
			dao.deleteById(id);
			
			
		}else if(l.getImageFolder().equals("true"))
		{
			dao.deleteByImageGroup(l.getImageGroup(), l.getUserName());
		}
		
		//String imageURL = Utility.getServerURL(serverURL);

	//	tx.commit();

		return null;
		
	}
	
	
	public static void saveDocument(UserImagesVO obj)
	{
		CustomUserImagesDAO dao = new CustomUserImagesDAO();
		//UserImagesDAO dao = new UserImagesDAO();
		//Transaction tx = dao.getSession().beginTransaction(); 
		 dao.save(obj);
		
		//log.debug(" imgfolder  "+l.getImageFolder());
		
	
		
		//String imageURL = Utility.getServerURL(serverURL);

		//tx.commit();

		
	}
	
	
}
