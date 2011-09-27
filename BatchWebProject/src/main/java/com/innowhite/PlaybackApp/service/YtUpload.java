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

public class YtUpload {

    String client_id = "innowhite";
    String developer_key = "AI39si6Nb_onkYi1b7Efa13CIKvXb1Ivyz2JhSPfcuTdVd0ckNQ3fHMu3DzTXzij1aCLbCsjMP6I-M8LDNaWTQxPjwG0p9OUDw";

    private static final Logger log = LoggerFactory.getLogger(YtUpload.class);
    
    public YtUpload() {
	try {
	    YouTubeService service = new YouTubeService(client_id, developer_key);
	    service.setUserCredentials("rahul7588@gmail.com", "prodigy157");
	    VideoEntry newEntry = new VideoEntry();

	    XmlBlob xmlBlob = new XmlBlob();
	    xmlBlob.setBlob("<yt:accessControl action='list' permission='denied'/>");
	    newEntry.setXmlBlob(xmlBlob);

	    YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
	    // mg.setXmlBlob(xmlBlob);
	    mg.setTitle(new MediaTitle());
	    mg.getTitle().setPlainTextContent("Second Conference");
	    mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Tech"));
	    mg.setKeywords(new MediaKeywords());
	    mg.getKeywords().addKeyword("conference");
	    mg.setDescription(new MediaDescription());
	    mg.getDescription().setPlainTextContent("Innowhite's hello world 2 YT upload");
	    mg.setPrivate(false);
	    mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "mydevtag"));
	    mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "anotherdevtag"));

	    newEntry.setGeoCoordinates(new GeoRssWhere(37.0, -122.0));
	    // alternatively, one could specify just a descriptive string
	    newEntry.setLocation("Mountain View, CA");

	    MediaFileSource ms = new MediaFileSource(new File("c:\\testFinal.avi"), "video/quicktime");
	    newEntry.setMediaSource(ms);

	    String uploadUrl = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";

	    VideoEntry createdEntry = service.insert(new URL(uploadUrl), newEntry);
	    printVideoEntry(createdEntry, true);
	} catch (MalformedURLException e) {
	    log.error(e.getMessage(),e);
	    e.printStackTrace();
	} catch (IOException e) {
	    log.error(e.getMessage(),e);
	    e.printStackTrace();
	} catch (ServiceException se) {
	    log.error(se.getMessage(),se);
	    log.error("Sorry, your upload was invalid:");
	    log.error(se.getResponseBody());
	    return;
	    // e.printStackTrace();
	}
	log.debug("Video uploaded successfully!");
    }

    public static void main(String[] args) {
	new YtUpload();
    }

    public static void printVideoEntry(VideoEntry videoEntry, boolean detailed) {
	log.debug("Title: " + videoEntry.getTitle().getPlainText());

	if (videoEntry.isDraft()) {
	    log.debug("Video is not live");
	    YtPublicationState pubState = videoEntry.getPublicationState();
	    if (pubState.getState() == YtPublicationState.State.PROCESSING) {
		log.debug("Video is still being processed.");
	    } else if (pubState.getState() == YtPublicationState.State.REJECTED) {
		System.out.print("Video has been rejected because: ");
		log.debug(pubState.getDescription());
		System.out.print("For help visit: ");
		log.debug(pubState.getHelpUrl());
	    } else if (pubState.getState() == YtPublicationState.State.FAILED) {
		System.out.print("Video failed uploading because: ");
		log.debug(pubState.getDescription());
		System.out.print("For help visit: ");
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
	    System.out.print("Keywords: ");
	    for (String keyword : keywords.getKeywords()) {
		System.out.print(keyword + ",");
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
	    

	    log.debug("\tThumbnails:");
	    for (MediaThumbnail mediaThumbnail : mediaGroup.getThumbnails()) {
		log.debug("\t\tThumbnail URL: " + mediaThumbnail.getUrl());
		log.debug("\t\tThumbnail Time Index: " + mediaThumbnail.getTime());
		
	    }

	    log.debug("\tMedia:");
	    for (YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
		log.debug("\t\tMedia Location: " + mediaContent.getUrl());
		log.debug("\t\tMedia Type: " + mediaContent.getType());
		log.debug("\t\tDuration: " + mediaContent.getDuration());
		
	    }

	    for (YouTubeMediaRating mediaRating : mediaGroup.getYouTubeRatings()) {
		log.debug("Video restricted in the following countries: " + mediaRating.getCountries().toString());
	    }
	}
    }

}
