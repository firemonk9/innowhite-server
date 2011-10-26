package com.innowhite.PlaybackApp.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.extensions.Rating;
import com.google.gdata.data.geo.impl.GeoRssWhere;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.mediarss.MediaCategory;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaPlayer;
import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaContent;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeMediaRating;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.data.youtube.YtPublicationState;
import com.google.gdata.data.youtube.YtStatistics;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class YtUploadService {

    private static final Logger log = LoggerFactory.getLogger(YtUploadService.class);

    public YtUploadService() throws IOException, AuthenticationException {

	String client_id = "innowhite";
	String developer_key = "AI39si6Nb_onkYi1b7Efa13CIKvXb1Ivyz2JhSPfcuTdVd0ckNQ3fHMu3DzTXzij1aCLbCsjMP6I-M8LDNaWTQxPjwG0p9OUDw";
	YouTubeService service = new YouTubeService(client_id, developer_key);
	service.setUserCredentials("username", "password");
	VideoEntry newEntry = new VideoEntry();

	YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
	mg.setTitle(new MediaTitle());
	mg.getTitle().setPlainTextContent("My Web Movie");
	mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Web-conference"));
	mg.setKeywords(new MediaKeywords());
	mg.getKeywords().addKeyword("conference");
	mg.setDescription(new MediaDescription());
	mg.getDescription().setPlainTextContent("My description");
	mg.setPrivate(false);
	mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "mydevtag"));
	mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "anotherdevtag"));

	newEntry.setGeoCoordinates(new GeoRssWhere(37.0, -122.0));
	// alternatively, one could specify just a descriptive string
	// newEntry.setLocation("Hyderabad, India");

	MediaFileSource ms = new MediaFileSource(new File("48938931307_0.avi"), "video/avi");
	newEntry.setMediaSource(ms);

	String uploadUrl = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";
	try {
	    // VideoEntry createdEntry =
	    service.insert(new URL(uploadUrl), newEntry);
	    // printVideoEntry(createdEntry, true);
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (ServiceException se) {
	    log.debug("Sorry, your upload was invalid:");
	    log.debug(se.getResponseBody());
	    return;
	}
	log.debug("Video uploaded successfully!");
    }

    public static void printVideoEntry(VideoEntry videoEntry, boolean detailed) {
	log.debug("Title: " + videoEntry.getTitle().getPlainText());

	if (videoEntry.isDraft()) {
	    log.debug("Video is not live");
	    YtPublicationState pubState = videoEntry.getPublicationState();
	    if (pubState.getState() == YtPublicationState.State.PROCESSING) {
		log.debug("Video is still being processed.");
	    } else if (pubState.getState() == YtPublicationState.State.REJECTED) {
		log.debug("Video has been rejected because: ");
		log.debug(pubState.getDescription());
		log.debug("For help visit: ");
		log.debug(pubState.getHelpUrl());
	    } else if (pubState.getState() == YtPublicationState.State.FAILED) {
		log.debug("Video failed uploading because: ");
		log.debug(pubState.getDescription());
		log.debug("For help visit: ");
		log.debug(pubState.getHelpUrl());
	    }
	}

	if (videoEntry.getEditLink() != null) {
	    log.debug("Video is editable by current user.");
	}

	if (detailed) {

	    YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

	    log.debug("Uploaded by: " + mediaGroup.getUploader());

	    log.debug("Video ID: " + mediaGroup.getVideoId());
	    log.debug("Description: " + mediaGroup.getDescription().getPlainTextContent());

	    MediaPlayer mediaPlayer = mediaGroup.getPlayer();
	    log.debug("Web Player URL: " + mediaPlayer.getUrl());
	    MediaKeywords keywords = mediaGroup.getKeywords();
	    log.debug("Keywords: ");
	    for (String keyword : keywords.getKeywords()) {
		log.debug(keyword + ",");
	    }

	    GeoRssWhere location = videoEntry.getGeoCoordinates();
	    if (location != null) {
		log.debug("Latitude: " + location.getLatitude());
		log.debug("Longitude: " + location.getLongitude());
	    }

	    Rating rating = videoEntry.getRating();
	    if (rating != null) {
		log.debug("Average rating: " + rating.getAverage());
	    }

	    YtStatistics stats = videoEntry.getStatistics();
	    if (stats != null) {
		log.debug("View count: " + stats.getViewCount());
	    }
	    // log.debug();

	    log.debug("\tThumbnails:");
	    for (MediaThumbnail mediaThumbnail : mediaGroup.getThumbnails()) {
		log.debug("\t\tThumbnail URL: " + mediaThumbnail.getUrl());
		log.debug("\t\tThumbnail Time Index: " + mediaThumbnail.getTime());
		// log.debug();
	    }

	    log.debug("\tMedia:");
	    for (YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
		log.debug("\t\tMedia Location: " + mediaContent.getUrl());
		log.debug("\t\tMedia Type: " + mediaContent.getType());
		log.debug("\t\tDuration: " + mediaContent.getDuration());
		// log.debug();
	    }

	    for (YouTubeMediaRating mediaRating : mediaGroup.getYouTubeRatings()) {
		log.debug("Video restricted in the following countries: " + mediaRating.getCountries().toString());
	    }
	}
    }

    public static void Main(String args[]) throws IOException, AuthenticationException {
	new YtUploadService();
    }

    /**
     * Uploads a new video to YouTube.
     * 
     * @param service
     *            An authenticated YouTubeService object.
     * @throws IOException
     *             Problems reading user input.
     */
    /*
     * private static void uploadVideo(YouTubeService service) throws
     * IOException {
     * 
     * log.debug("First, type in the path to the movie file:"); File videoFile =
     * new File(readLine());
     * 
     * if (!videoFile.exists()) { log.debug("Sorry, that video doesn't exist.");
     * return; }
     * 
     * log.debug(
     * "What is the MIME type of this file? (ex. 'video/quicktime' for .mov)");
     * String mimeType = readLine();
     * 
     * log.debug("What should I call this video?"); String videoTitle =
     * readLine();
     * 
     * VideoEntry newEntry = new VideoEntry();
     * 
     * YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
     * 
     * mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME,
     * "Tech")); mg.setTitle(new MediaTitle());
     * mg.getTitle().setPlainTextContent(videoTitle); mg.setKeywords(new
     * MediaKeywords()); mg.getKeywords().addKeyword("gdata-test");
     * mg.setDescription(new MediaDescription());
     * mg.getDescription().setPlainTextContent(videoTitle); MediaFileSource ms =
     * new MediaFileSource(videoFile, mimeType); newEntry.setMediaSource(ms);
     * 
     * try { service.insert(new URL(VIDEO_UPLOAD_FEED), newEntry); } catch
     * (ServiceException se) { log.debug("Sorry, your upload was invalid:");
     * log.debug(se.getResponseBody()); return; }
     * 
     * log.debug("Video uploaded successfully!"); }
     */
}