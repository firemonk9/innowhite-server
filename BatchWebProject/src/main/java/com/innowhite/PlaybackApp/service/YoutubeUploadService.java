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
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.XmlBlob;

public class YoutubeUploadService {

	String client_id = "innowhitedotcom";
	// String developer_key =
	// "AI39si6Nb_onkYi1b7Efa13CIKvXb1Ivyz2JhSPfcuTdVd0ckNQ3fHMu3DzTXzij1aCLbCsjMP6I-M8LDNaWTQxPjwG0p9OUDw";
	// String developer_key =
	// "AI39si64bpaTa0lVyE62839SZTReT1oUbUvmtWFOxB271UNnMLWjdFFlFvFp94AjjVGnMf3XwaKuLKMOhR6-85UXU524bkKXBw";
	// String developer_key =
	// "AI39si6zaP1yWygSnC-KfsItkLe_5qzBjHNxVh2v6xZl-DZnGKMzlz5m5huYYcBVQzsPIbXqmpfKMyZCPhWomVZM45qYoZXI0Q";
	String developer_key = "AI39si4Hh7XCwTNMsKYHNRAXY5SmBPeOPhrX1u12dofk9RTvn0ZsefooZKqaGOO27IXp6V1mPZpxJTBiGUTft9xQiCSjX06Jhw";
	String youtube_url = null;
	private static final Logger log = LoggerFactory.getLogger(YoutubeUploadService.class);

	public String uploadVideo(String video_url) {
		try {
			log.debug("youtube: started uploading video on youtube...");
			log.debug("youtube: video_url to be uploaded-"+video_url);
			YouTubeService service = new YouTubeService(client_id,developer_key);
			log.debug("youtube: creating service object::"+service);
			service.setUserCredentials("innowhite.com@gmail.a", "dhiraj31");
			log.debug("youtube: username, password...");
			
			VideoEntry newEntry = new VideoEntry();
			XmlBlob xmlBlob = new XmlBlob();
			xmlBlob.setBlob("<yt:accessControl action='list' permission='denied'/>");
			newEntry.setXmlBlob(xmlBlob);

			YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
			// mg.setXmlBlob(xmlBlob);
			mg.setTitle(new MediaTitle());
			mg.getTitle().setPlainTextContent("Third Conference");
			log.debug("youtube: set title..");
			mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME,"Tech"));
			log.debug("youtube:  set category");
			mg.setKeywords(new MediaKeywords());
			mg.getKeywords().addKeyword("conference");
			log.debug("youtube: set keywords");
			mg.setDescription(new MediaDescription());
			mg.getDescription().setPlainTextContent("Innowhite's hello world 3 YT upload");
			log.debug("youtube: set description");
			mg.setPrivate(false);
			mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "mydevtag"));
			mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "anotherdevtag"));

			newEntry.setGeoCoordinates(new GeoRssWhere(37.0, -122.0));
			// alternatively, one could specify just a descriptive string
			newEntry.setLocation("Mountain View, CA");

			MediaFileSource ms = new MediaFileSource(new File(video_url),"video/quicktime");
			newEntry.setMediaSource(ms);

			String uploadUrl = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";

			VideoEntry createdEntry = service.insert(new URL(uploadUrl),newEntry);
			log.debug("youtube: uploading finished");
			YouTubeMediaGroup mediaGroup = createdEntry.getMediaGroup();
			MediaPlayer mediaPlayer = mediaGroup.getPlayer();
			youtube_url = mediaPlayer.getUrl();
			log.debug("Youtube: UploadURL" + youtube_url);
			// printVideoEntry(createdEntry, true);
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			log.debug("Sorry, your upload was invalid:");
			log.debug(se.getResponseBody());
			return "";
			// e.printStackTrace();
		}
		log.debug("Video uploaded successfully on youtube!");
		return youtube_url;
	}

	public static void printVideoEntry(VideoEntry videoEntry, boolean detailed) {
		System.out.println("Title: " + videoEntry.getTitle().getPlainText());

		if (videoEntry.isDraft()) {
			System.out.println("Video is not live");
			YtPublicationState pubState = videoEntry.getPublicationState();
			if (pubState.getState() == YtPublicationState.State.PROCESSING) {
				System.out.println("Video is still being processed.");
			} else if (pubState.getState() == YtPublicationState.State.REJECTED) {
				System.out.print("Video has been rejected because: ");
				System.out.println(pubState.getDescription());
				System.out.print("For help visit: ");
				System.out.println(pubState.getHelpUrl());
			} else if (pubState.getState() == YtPublicationState.State.FAILED) {
				System.out.print("Video failed uploading because: ");
				System.out.println(pubState.getDescription());
				System.out.print("For help visit: ");
				System.out.println(pubState.getHelpUrl());
			}
		}

		if (videoEntry.getEditLink() != null) {
			System.out.println("Video is editable by current user.");
		}

		if (detailed) {

			YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();

			System.out.println("Uploaded by: " + mediaGroup.getUploader());

			System.out.println("Video ID: " + mediaGroup.getVideoId());
			System.out.println("Description: "
					+ mediaGroup.getDescription().getPlainTextContent());

			MediaPlayer mediaPlayer = mediaGroup.getPlayer();
			String youtube_url = mediaPlayer.getUrl();
			System.out.println("Web Player URL: " + youtube_url);
			MediaKeywords keywords = mediaGroup.getKeywords();
			System.out.print("Keywords: ");
			for (String keyword : keywords.getKeywords()) {
				System.out.print(keyword + ",");
			}

			GeoRssWhere location = videoEntry.getGeoCoordinates();
			if (location != null) {
				System.out.println("Latitude: " + location.getLatitude());
				System.out.println("Longitude: " + location.getLongitude());
			}

			Rating rating = videoEntry.getRating();
			if (rating != null) {
				System.out.println("Average rating: " + rating.getAverage());
			}

			YtStatistics stats = videoEntry.getStatistics();
			if (stats != null) {
				System.out.println("View count: " + stats.getViewCount());
			}

			System.out.println("\tThumbnails:");
			for (MediaThumbnail mediaThumbnail : mediaGroup.getThumbnails()) {
				System.out.println("\t\tThumbnail URL: "
						+ mediaThumbnail.getUrl());
				System.out.println("\t\tThumbnail Time Index: "
						+ mediaThumbnail.getTime());

			}

			System.out.println("\tMedia:");
			for (YouTubeMediaContent mediaContent : mediaGroup
					.getYouTubeContents()) {
				System.out.println("\t\tMedia Location: "
						+ mediaContent.getUrl());
				System.out.println("\t\tMedia Type: " + mediaContent.getType());
				System.out.println("\t\tDuration: "
						+ mediaContent.getDuration());

			}

			for (YouTubeMediaRating mediaRating : mediaGroup
					.getYouTubeRatings()) {
				System.out
						.println("Video restricted in the following countries: "
								+ mediaRating.getCountries().toString());
			}
		}
	}
}