package com.util;

import java.io.File;
import java.io.IOException;

import org.exolab.castor.builder.SourceGenerator;
import org.exolab.castor.xml.schema.util.XMLInstance2Schema;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

public class SchemaGenerator {

	private static Logger log = Red5LoggerFactory.getLogger(SchemaGenerator.class, InnowhiteConstants.APP_NAME);
	
private static void addImagestoDB(String path){
		
		
	
	
		
		log.debug(path);
		File dir = new File(path);
		String[] children = dir.list();
		if (children == null) {
	        // Either dir does not exist or is not a directory
	    } else {
	        for (int i=0; i<children.length; i++) {
	            // Get filename of file or directory
	            log.debug(children[i]);
	            
	        }
	    }
		
	}
	
	public static void main(String args[]){
		
		
		
//		String var = "Victor__present13_43982/Victor__present13_43982-0.png";
//		
//		
//		String folderName = var.substring(0, var.indexOf("/"));
//		 log.debug("" +folderName+"/thumbnail.png");
		 //log.debug(imageURL +folderName+"//thumbnail.png");
//		int ss  = s.lastIndexOf('.');
//		int sss =s.lastIndexOf('-'); 
//		
//		int ss=s.lastIndexOf('-');
//		int sss=s.lastIndexOf('_');
		//log.debug(sss+" some thing wrong "+ss+"  "+s.substring(sss+1, ss));
	//	s.substring(ss, sss);
		
		generateSchemaFiles();
		
	}
	/*This function takes input an xml and generated the schema and then corresponding java files.
	 * 
	 * */
	
	public static void generateSchemaFiles(){
		
		XMLInstance2Schema xi2s = new XMLInstance2Schema();
		try {
			
//			Schema xsd = xi2s.createSchema( 
//			"data/data.xml"); 
//			Writer dstWriter = new FileWriter("data/newSchema1.xsd"); 
//			xi2s.serializeSchema(dstWriter, xsd); 
//			dstWriter.close();
			
			
			SourceGenerator srcGen = new SourceGenerator();
			srcGen.generateSource("data/newSchema.xsd", "com.innowhite.documentManagery");
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

//		catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	
}
