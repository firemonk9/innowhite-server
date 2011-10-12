package com.innowhite.PlaybackApp.service;

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
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaContent;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeMediaRating;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.data.youtube.YtPublicationState;
import com.google.gdata.data.youtube.YtStatistics;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class YtUploadService {
	
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
	
		newEntry.setGeoCoordinates(new GeoRssWhere(37.0,-122.0));
		// alternatively, one could specify just a descriptive string
		//newEntry.setLocation("Hyderabad, India");
	
		MediaFileSource ms = new MediaFileSource(new File("48938931307_0.avi"), "video/avi");
		newEntry.setMediaSource(ms);
	
		String uploadUrl = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";
		try {	
			//VideoEntry createdEntry = 
			service.insert(new URL(uploadUrl), newEntry);
			//printVideoEntry(createdEntry, true);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (ServiceException se) {
			System.out.println("Sorry, your upload was invalid:");
		    System.out.println(se.getResponseBody());
		    return;
		}
		System.out.println("Video uploaded successfully!");
	}
	public static void printVideoEntry(VideoEntry videoEntry, boolean detailed) {
		  System.out.println("Title: " + videoEntry.getTitle().getPlainText());

		  if(videoEntry.isDraft()) {
		    System.out.println("Video is not live");
		    YtPublicationState pubState = videoEntry.getPublicationState();
		    if(pubState.getState() == YtPublicationState.State.PROCESSING) {
		      System.out.println("Video is still being processed.");
		    }
		    else if(pubState.getState() == YtPublicationState.State.REJECTED) {
		      System.out.print("Video has been rejected because: ");
		      System.out.println(pubState.getDescription());
		      System.out.print("For help visit: ");
		      System.out.println(pubState.getHelpUrl());
		    }
		    else if(pubState.getState() == YtPublicationState.State.FAILED) {
		      System.out.print("Video failed uploading because: ");
		      System.out.println(pubState.getDescription());
		      System.out.print("For help visit: ");
		      System.out.println(pubState.getHelpUrl());
		    }
		  }

		  if(videoEntry.getEditLink() != null) {
		    System.out.println("Video is editable by current user.");
		  }

		  if(detailed) {

		    YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

		    System.out.println("Uploaded by: " + mediaGroup.getUploader());

		    System.out.println("Video ID: " + mediaGroup.getVideoId());
		    System.out.println("Description: " + 
		      mediaGroup.getDescription().getPlainTextContent());

		    MediaPlayer mediaPlayer = mediaGroup.getPlayer();
		    System.out.println("Web Player URL: " + mediaPlayer.getUrl());
		    MediaKeywords keywords = mediaGroup.getKeywords();
		    System.out.print("Keywords: ");
		    for(String keyword : keywords.getKeywords()) {
		      System.out.print(keyword + ",");
		    }

		    GeoRssWhere location = videoEntry.getGeoCoordinates();
		    if(location != null) {
		      System.out.println("Latitude: " + location.getLatitude());
		      System.out.println("Longitude: " + location.getLongitude());
		    }

		    Rating rating = videoEntry.getRating();
		    if(rating != null) {
		      System.out.println("Average rating: " + rating.getAverage());
		    }

		    YtStatistics stats = videoEntry.getStatistics();
		    if(stats != null ) {
		      System.out.println("View count: " + stats.getViewCount());
		    }
		    System.out.println();

		    System.out.println("\tThumbnails:");
		    for(MediaThumbnail mediaThumbnail : mediaGroup.getThumbnails()) {
		      System.out.println("\t\tThumbnail URL: " + mediaThumbnail.getUrl());
		      System.out.println("\t\tThumbnail Time Index: " +
		      mediaThumbnail.getTime());
		      System.out.println();
		    }

		    System.out.println("\tMedia:");
		    for(YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
		      System.out.println("\t\tMedia Location: "+ mediaContent.getUrl());
		      System.out.println("\t\tMedia Type: "+ mediaContent.getType());
		      System.out.println("\t\tDuration: " + mediaContent.getDuration());
		      System.out.println();
		    }

		    for(YouTubeMediaRating mediaRating : mediaGroup.getYouTubeRatings()) {
		      System.out.println("Video restricted in the following countries: " +
		        mediaRating.getCountries().toString());
		    }
		  }
		}
	public static void Main(String args[]) throws IOException, AuthenticationException
	{
		new YtUploadService();
	}
	
	/**
	   * Uploads a new video to YouTube.
	   * 
	   * @param service An authenticated YouTubeService object.
	   * @throws IOException Problems reading user input.
	   */
	/*private static void uploadVideo(YouTubeService service) throws IOException {
		    
			System.out.println("First, type in the path to the movie file:");
		    File videoFile = new File(readLine());

		    if (!videoFile.exists()) {
		      System.out.println("Sorry, that video doesn't exist.");
		      return;
		    }

		    System.out.println("What is the MIME type of this file? (ex. 'video/quicktime' for .mov)");
		    String mimeType = readLine();

		    System.out.println("What should I call this video?");
		    String videoTitle = readLine();

		    VideoEntry newEntry = new VideoEntry();

		    YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();

		    mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Tech"));
		    mg.setTitle(new MediaTitle());
		    mg.getTitle().setPlainTextContent(videoTitle);
		    mg.setKeywords(new MediaKeywords());
		    mg.getKeywords().addKeyword("gdata-test");
		    mg.setDescription(new MediaDescription());
		    mg.getDescription().setPlainTextContent(videoTitle);
		    MediaFileSource ms = new MediaFileSource(videoFile, mimeType);
		    newEntry.setMediaSource(ms);

		    try {
		      service.insert(new URL(VIDEO_UPLOAD_FEED), newEntry);
		    } catch (ServiceException se) {
		      System.out.println("Sorry, your upload was invalid:");
		      System.out.println(se.getResponseBody());
		      return;
		    }

		    System.out.println("Video uploaded successfully!");
		  }*/
}