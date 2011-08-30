package com.innowhite.PlaybackApp.s3;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.security.AWSCredentials;

public class S3Test {

    public static void main(String args[]) {
	try {
	    String awsAccessKey = "AKIAJTG3XGQZFPYS5TTQ";
	    String awsSecretKey = "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";
	    AWSCredentials awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey);

	    S3Service s3Service = new RestS3Service(awsCredentials);
	    
	    S3Bucket[] myBuckets = s3Service.listAllBuckets();
	    System.out.println("How many buckets to I have in S3? " + myBuckets.length);
	    
	} catch (S3ServiceException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
