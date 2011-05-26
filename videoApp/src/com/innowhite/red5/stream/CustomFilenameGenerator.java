package com.innowhite.red5.stream;

import org.red5.server.api.IScope;
import org.red5.server.api.stream.IStreamFilenameGenerator;

import com.innowhite.red5.stream.messaging.MessagingService;

public class CustomFilenameGenerator implements IStreamFilenameGenerator {
    /** Path that will store recorded videos. */
    public String recordPath = "recordedStreams/";
    /** Path that contains VOD streams. */
    public String playbackPath = "videoStreams/";
    /** Set if the path is absolute or relative */
    public boolean resolvesAbsolutePath = true;

    private MessagingService messagingService;

    // private MessagingService messagingService;

    public MessagingService getMessagingService() {
	return messagingService;
    }

    public void setMessagingService(MessagingService messagingService) {
	this.messagingService = messagingService;
    }

    public String generateFilename(IScope scope, String name, GenerationType type) {
	// Generate filename without an extension.
	return generateFilename(scope, name, null, type);
    }

    public String generateFilename(IScope scope, String name, String extension, GenerationType type) {
	String filename;

	if (type == GenerationType.RECORD)
	    filename = recordPath + name;
	else
	    filename = playbackPath + name;

	// add extension
	if (extension != null)
	    filename += extension;

	//messagingService.sendStreamMessage("RECORDSTART#" + name + "#" + filename);

	return filename;
    }

    
    public boolean resolvesToAbsolutePath() {
	return resolvesAbsolutePath;
    }

    public void setRecordPath(String path) {
	recordPath = path;
    }

    public void setPlaybackPath(String path) {
	playbackPath = path;
    }

    public void setAbsolutePath(Boolean absolute) {
	resolvesAbsolutePath = absolute;
    }

}