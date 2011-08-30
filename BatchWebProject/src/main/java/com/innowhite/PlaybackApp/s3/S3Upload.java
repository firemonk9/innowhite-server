package com.innowhite.PlaybackApp.s3;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S3Upload {

    private static final Logger log = LoggerFactory.getLogger(S3Upload.class);
    
    public void uploadFile(String absFilePath, AWSCredentials awsCredentials) {
	try {

	    S3Service s3Service = new RestS3Service(awsCredentials);

	    // S3Bucket[] myBuckets = s3Service.listAllBuckets();
	    // System.out.println("How many buckets to I have in S3? " +
	    // myBuckets.length);

	    File fileData = new File(absFilePath);
	    S3Object fileObject = new S3Object(fileData);

	    s3Service.putObject("inno_input", fileObject);
	    
	    log.debug(" file downloaded and length is "+fileObject.getContentLength()+" file name "+fileObject.getName());

	} catch (S3ServiceException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
