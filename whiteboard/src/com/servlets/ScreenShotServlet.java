package com.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

/**
 * Servlet implementation class for Servlet: ScreenShotServlet
 *
 */
 public class ScreenShotServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	 
	 
	 private static Logger log = Red5LoggerFactory.getLogger(ScreenShotServlet.class, InnowhiteConstants.APP_NAME); 
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ScreenShotServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}  	
	
	private void process(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException{
		try{
			// Check that we have a file upload request
			   log.debug("Entered  Process ... " );
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			log.debug("Entered  Process ... "+isMultipart );
			if(isMultipart){
				// Create a factory for disk-based file items
				FileItemFactory factory = new DiskFileItemFactory();
				log.debug("Entered  Process ... "+factory );	
				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);
	
				//Create a progress listener
				ProgressListener progressListener = new ProgressListener(){
				   private long megaBytes = -1;
				   public void update(long pBytesRead, long pContentLength, int pItems) {
				       long mBytes = pBytesRead / 1000000;
				       if (megaBytes == mBytes) {
				           return;
				       }
				       log.debug("Entered  Process ... "+mBytes );
				       megaBytes = mBytes;
				       log.debug("We are currently reading item " + pItems);
				       if (pContentLength == -1) {
				           log.debug("So far, " + pBytesRead + " bytes have been read.");
				       } else {
				           log.debug("So far, " + pBytesRead + " of " + pContentLength
				                              + " bytes have been read.");
				       }
				   }
				};
				
				upload.setProgressListener(progressListener);
				
				// Parse the request
				List items = upload.parseRequest(request);
				// Process the uploaded items
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
				    FileItem item = (FileItem) iter.next();

				    if (item.isFormField()) {
				        //processFormField(item);
				    } else {
				        processUploadedFile(item);
				    }
				}
			}			
		}catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
		
	}

	private void processUploadedFile(FileItem item) throws Exception{
		// Process a file upload
		log.debug("Entered  processUploadedFile ... " );
		if (!item.isFormField()) {
			
			String fieldName = item.getFieldName();
			log.debug("Entered  processUploadedFile ... "+fieldName );
			String fileName = item.getName();
		    String contentType = item.getContentType();
		    boolean isInMemory = item.isInMemory();
		    long sizeInBytes = item.getSize();
		    File uploadedFile = new File(fileName);
		    item.write(uploadedFile);
		}
		
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}   	  	    
}