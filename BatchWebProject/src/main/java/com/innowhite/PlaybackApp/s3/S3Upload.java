package com.innowhite.PlaybackApp.s3;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.EmailAddressGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.model.StorageOwner;
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

	    AccessControlList acl = new AccessControlList();

	    // Grant access by email address. Note that this only works email
	    // address of AWS S3 members.
	    StorageOwner so = new StorageOwner("bfd83085c1610b14991371e8fd5790e95a208ab213ef94d093fa4cd914d598c3","Innowhite");
	    
	    acl.setOwner(so);
	    acl.grantPermission(new EmailAddressGrantee("info@uencode.com"), Permission.PERMISSION_FULL_CONTROL);

	   
	    fileObject.setAcl(acl);
	    s3Service.putObject("inno_input", fileObject);

	    log.debug(" file downloaded and length is " + fileObject.getContentLength() + " file name " + fileObject.getName());
	    
	    log.debug(" setting permissions ");
	   

	} catch (S3ServiceException e) {
	    log.error(e.getMessage(), e);
	} catch (NoSuchAlgorithmException e) {
	    log.error(e.getMessage(), e);
	} catch (IOException e) {
	    log.error(e.getMessage(), e);
	}

    }
}
