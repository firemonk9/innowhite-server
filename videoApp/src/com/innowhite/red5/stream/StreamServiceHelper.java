package com.innowhite.red5.stream;

import org.apache.log4j.*;
import org.red5.server.*;
import org.red5.server.api.*;
import org.red5.server.api.stream.*;
import org.red5.server.net.rtmp.*;
import org.red5.server.net.rtmp.status.*;
import org.red5.server.stream.*;

/**
 * This class is an extention to Red5's StreamService class
 * which allows to manage streams from server-side.
 */
public abstract class StreamServiceHelper {
	
	private static final Logger logger = Logger.getLogger(StreamServiceHelper.class);

	/**
	 * Close stream.
	 * This method can close both IClientBroadcastStream (coming from Flash Player to Red5)
	 * and ISubscriberStream (from Red5 to Flash Player).
	 * Corresponding application handlers (streamSubscriberClose, etc.) are called as if
	 * close was initiated by Flash Player.
	 * 
	 * It is recommended to remember stream id in application handlers, ex.:
	 * <pre>
	 * public void streamBroadcastStart(IBroadcastStream stream) {
	 * 	super.streamBroadcastStart(stream);
	 * 	if (stream instanceof IClientBroadcastStream) {
	 * 		int publishedStreamId = ((ClientBroadcastStream)stream).getStreamId();
	 * 		Red5.getConnectionLocal().setAttribute(PUBLISHED_STREAM_ID_ATTRIBUTE, publishedStreamId);
	 * 	}
	 * }
	 * </pre>
	 * <pre>
	 * public void streamPlaylistItemPlay(IPlaylistSubscriberStream stream, IPlayItem item, boolean isLive) {
	 * 	super.streamPlaylistItemPlay(stream, item, isLive);
	 * 	Red5.getConnectionLocal().setAttribute(WATCHED_STREAM_ID_ATTRIBUTE, stream.getStreamId());
	 * }
	 * </pre>
	 * When stream is closed, corresponding NetStream status will be sent to stream provider / consumers.
	 * Implementation is based on Red5's StreamService.close()
	 * @param connection client connection
	 * @param streamId stream ID (number: 1,2,...)
	 */
	public static void closeStream(IClientBroadcastStream stream) {
//		if (!(connection instanceof IStreamCapableConnection)) {
//			logger.warn("Connection is not instance of IStreamCapableConnection: " + connection);
//			return;
//		}
//
//		IStreamCapableConnection scConnection = (IStreamCapableConnection) connection;
//		IClientStream stream = scConnection.getStreamById(streamId);
//		
//		
//		if (stream == null) {
//			logger.info("Stream not found: streamId=" + streamId + ", connection=" + connection);
//			return;
//		}

//		if (stream instanceof IClientBroadcastStream) {
//			// this is a broadcasting stream (from Flash Player to Red5)
//			IClientBroadcastStream bs = (IClientBroadcastStream) stream;
//			IBroadcastScope bsScope = (IBroadcastScope)connection.getScope().getBasicScope(
//					IBroadcastScope.TYPE, bs.getPublishedName());
//			if (bsScope != null && connection instanceof BaseConnection) {
//				((BaseConnection) connection).unregisterBasicScope(bsScope);
//			}
//		}
		//stream.getScope().unregister
		stream.close();
		//stream.
		//scConnection.deleteStreamById(streamId);

		// in case of broadcasting stream, status is sent automatically by Red5
//		if (!(stream instanceof IClientBroadcastStream)) {
//			sendNetStreamStatus(connection, StatusCodes.NS_PLAY_STOP, "Stream closed by server",
//					stream.getName(), streamId);
//		}
	}
	
	/**
	 * Send NetStream status to client (Flash Player)
	 * @param conn
	 * @param statusCode see StatusCodes class
	 * @param description
	 * @param name
	 * @param streamId
	 */
	private static void sendNetStreamStatus(IConnection conn, String statusCode, String description, String name, int streamId) {
		if (!(conn instanceof RTMPConnection)) {
			throw new RuntimeException("Connection is not RTMPConnection: " + conn);
		}

		Status s = new Status(statusCode);
		s.setClientid(streamId);
		s.setDesciption(description);
		s.setDetails(name);
		s.setLevel(Status.STATUS);

		Channel channel = ((RTMPConnection) conn).getChannel((byte) (4 + ((streamId - 1) * 5)));
		channel.sendStatus(s);
	}
}
