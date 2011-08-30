package com.innowhite.PlaybackApp.s3;

import org.jets3t.service.security.AWSCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.PlaybackApp.util.PlaybackVO;

public class S3Service {

    static AWSCredentials awsCredentials;
    private static final Logger log = LoggerFactory.getLogger(S3Service.class);
    
    private PlaybackVO playbackVO;
    
    

    public void setPlaybackVO(PlaybackVO playbackVO) {
        this.playbackVO = playbackVO;
    }

    public static void main(String args[]) {

	
	log.debug("  uploading file ...");
	new S3Service().uploadFile("/opt/InnowhiteData/videos/10000519.flv");
	log.debug("   file uploaded ...");
	new S3Service().downloadFile("/opt/InnowhiteData/videos/10000519_1.flv","10000519.flv");
	log.debug("   file downloaded ...");
    }

    
    public S3Service(){
	
	String awsAccessKey = playbackVO.getAwsAccessKey();
	String awsSecretKey = playbackVO.getAwsSecretKey();
	awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey);
	
    }
    
    /* uploads the file form the given path. 
     * ex : absFilePath  = "/opt/InnowhiteData/videos/10000520.flv"
     * */
    public void uploadFile(String absFilePath) {
	S3Upload upload = new S3Upload();
	upload.uploadFile(absFilePath, awsCredentials);
    }

    /* downloads the file to given file Path, the downloaded file is fileName 
     * absFilePath  = "/opt/InnowhiteData/videos/10000520.flv"
     * fileName = 10000520.flv
     * */
    public void downloadFile(String absFilePath, String fileName) {
	S3Download download = new S3Download();
	download.downloadFile(absFilePath,fileName ,awsCredentials);
    }

}
