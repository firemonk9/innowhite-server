package com.innowhite.PlaybackApp.s3;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class S3Download {
   
    private static final Logger log = LoggerFactory.getLogger(S3Download.class);
    
    @Autowired
    public void downloadFile(String absFilePath, String fileName, AWSCredentials awsCredentials) {
	try {

	    S3Service s3Service = new RestS3Service(awsCredentials);

	    // S3Bucket[] myBuckets = s3Service.listAllBuckets();
	    // System.out.println("How many buckets to I have in S3? " +
	    // myBuckets.length);

	    S3Object objectComplete = s3Service.getObject("inno_input", fileName);
	    File fout = new File(absFilePath);
	    FileOutputStream fos = new FileOutputStream(fout);
	    BufferedOutputStream bos = new BufferedOutputStream(fos);

	    File f = objectComplete.getDataInputFile();

	    // InputStreamReader is= new
	    // InputStreamReader(objectComplete.getDataInputStream());
	    InputStream in = objectComplete.getDataInputStream();
	    int b;
	    while ((b = in.read()) != -1) {
		bos.write(b);
	    }
	    fos.close();
	    in.close();
	    
	    log.debug(" file downloaded and length is "+objectComplete.getContentLength()+" file name "+objectComplete.getName());
	    
	    // String data = null;
	    // while ((data = reader.read) != null) {
	    // bos.write());
	    // }

	} catch (S3ServiceException e) {
	    log.error(e.getMessage(),e);
	    
	} catch (ServiceException e) {
	    log.error(e.getMessage(),e);
	    
	} catch (IOException e) {
	    log.error(e.getMessage(),e);
	    
	}

    }
}
