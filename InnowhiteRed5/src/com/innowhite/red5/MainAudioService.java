package com.innowhite.red5;

import java.util.ArrayList;
import java.util.List;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.so.ISharedObject;
import org.slf4j.Logger;

import com.innowhite.red5.audio.events.AudioFileStartStopEvent;
import com.innowhite.red5.audio.events.ConferenceEvent;
import com.innowhite.red5.audio.events.ParticipantJoinedEvent;
import com.innowhite.red5.audio.events.ParticipantLeftEvent;
import com.innowhite.red5.audio.events.ParticipantLockedEvent;
import com.innowhite.red5.audio.events.ParticipantMutedEvent;
import com.innowhite.red5.audio.events.ParticipantTalkingEvent;
import com.innowhite.red5.messaging.AudioMessageProducer;
import com.innowhite.red5.util.InnowhiteConstants;

public class MainAudioService {

	private Main main;

	private static Logger log = Red5LoggerFactory.getLogger(MainAudioService.class, InnowhiteConstants.APP_NAME);
	// InnowhiteConstants.APP_NAME

	// private final ConcurrentMap<String, RoomInfo> voiceRooms;
	// private final ConcurrentMap<String, RoomInfo> webRooms;

	// public ClientManager() {
	// voiceRooms = new ConcurrentHashMap<String, RoomInfo>();
	// webRooms = new ConcurrentHashMap<String, RoomInfo>();
	// }

	private AudioMessageProducer audioDataMessageService;

	public void setAudioDataMessageService(AudioMessageProducer audioDataMessageService) {
		this.audioDataMessageService = audioDataMessageService;
	}

	public void addSharedObject(String webRoom, String voiceRoom, ISharedObject so) {
		log.info("Adding SO for [" + webRoom + "," + voiceRoom + "]");
		// RoomInfo soi = new RoomInfo(webRoom, voiceRoom, so);
		// voiceRooms.putIfAbsent(voiceRoom, soi);
		// webRooms.putIfAbsent(webRoom, soi);
	}

	public void removeSharedObject(String webRoom) {
		// RoomInfo soi = webRooms.remove(webRoom);
		// if (soi != null) voiceRooms.remove(soi.getVoiceRoom());
	}

	private void joined(String confRoom, String participant, String name, Boolean muted, Boolean talking, Boolean locked, String callSource) {
		log.info("name  " + name + "  Participant " + participant + "joining room " + confRoom);

		String room = UserCacheService.getActualRoom(confRoom);
		if (room == null) {
			log.warn(" the room is null for joined --  Participant " + participant + " room " + room);
			return;
		}
		ISharedObject so = UserCacheService.getUserSharedObj(room);

		if (so != null) {

			String innoUniqueId = name + confRoom;
			UserCacheService.addparticipantIDUser(participant, innoUniqueId.trim());
			String userID = UserCacheService.getUseId(participant);

			List<Object> list = new ArrayList<Object>();
			list.add(participant);
			list.add(userID);

			list.add(muted);
			list.add(talking);
			list.add(callSource);

			log.debug("Sending join to client participant::" + participant + "  userID:: " + userID + "  name:: " + name + " room  " + room);

			so.sendMessage("userJoin", list);

			UserCacheService.addUserConfMap(room, userID);

		}
	}

	private void left(String confRoom, String participant) {
		log.debug("Participant [" + participant + "," + confRoom + "] leaving");

		String room = UserCacheService.getActualRoom(confRoom);
		if (room == null) {
			log.warn(" the room is null for roomLeft --  Participant " + participant + " room " + room);
			return;
		}
		ISharedObject so = UserCacheService.getUserSharedObj(room);

		String userID = UserCacheService.getUseId(participant);

		if (so != null) {
			// UserListVO obj = (UserListVO) so.getAttribute(userID);
			// obj.setVoiceconfjoined(obj.getVoiceconfjoined() - 1);

			List<Object> list = new ArrayList<Object>();
			list.add(userID);

			UserCacheService.removeparticipantIDUser(participant);
			// addparticipantIDUser(participant, innoUniqueId.trim() );
			log.debug("Sending left to client participant::" + confRoom + "  confRoom:: " + participant + "  participant:: " + " userID  " + userID + "  room:: " + room);
			so.sendMessage("userLeft", list);
			UserCacheService.removeUserConfMap(room, userID);

		} else {
			log.warn(" remote shared object is null :" + so + " confRoom " + confRoom + " participant " + participant);

		}
	}

	private void muted(String confRoom, String participant, Boolean muted) {
		log.debug("room ::  " + confRoom + "  Participant " + participant + " is muted = " + muted);

		String room = UserCacheService.getActualRoom(confRoom);
		if (room == null) {
			log.debug(" the room is null for muted --  Participant " + participant + " room " + room);
			return;
		}

		String userID = UserCacheService.getUseId(participant);

		ISharedObject so = UserCacheService.getUserSharedObj(room);

		if (so != null) {

			// UserListVO obj = (UserListVO) so.getAttribute(userID);
			// obj.setMuted(muted);
			// so.setAttribute(userID,obj);
			// obj
			List<Object> list = new ArrayList<Object>();
			list.add(userID);
			list.add(muted);

			so.sendMessage("userMute", list);
			// When a user is muted send a message of talking= false.
			talking(confRoom, participant, false);

		}
	}

	private void locked(String confRoom, String participant, Boolean locked) {
		log.debug("confRoom ::  " + confRoom + "  Participant " + participant + " is locked = " + locked);

		String room = UserCacheService.getActualRoom(confRoom);
		if (room == null) {
			log.warn(" the room is null for locked --  Participant " + participant + " room " + room);
			return;
		}
		ISharedObject so = UserCacheService.getUserSharedObj(room);
		if (so != null) {

			String userID = UserCacheService.getUseId(participant);

			List<Object> list = new ArrayList<Object>();
			list.add(userID);
			list.add(locked);

			so.sendMessage("userLockedMute", list);
		}
	}

	private void talking(String confRoom, String participant, Boolean talking) {
		//log.debug("room ::  " + confRoom + "  Participant " + participant + " is talking = " + talking);
		// RoomInfo soi = voiceRooms.get(room);

		String room = UserCacheService.getActualRoom(confRoom);
		if (room == null) {
			log.warn(" the room is null for talking --  Participant " + participant + " room " + room);
			return;
		}

		String userID = UserCacheService.getUseId(participant);

		if (UserCacheService.getUserSharedObj(room) != null) {

			ISharedObject so = UserCacheService.getUserSharedObj(room);
			// UserListVO obj = (UserListVO) so.getAttribute(userID);
			// obj.setTalking(talking);
			// so.setAttribute(userID, obj);
			List<Object> list = new ArrayList<Object>();
			list.add(userID);
			list.add(talking);
			so.sendMessage("userTalk", list);

		} else {

			log.debug("Some thign wrong talking -- userId:" + userID + " participant: " + participant + "  room " + room);
		}
	}

	/*
	 * Every room, when the audio conf starts, this function gets called to
	 * notify of the record file name. and record end state.
	 */
	private void fileRecordStartStop(String confRoom, String participant, String startRecordfile, String stopRecordFile) {

		log.debug("enter fileRecordStartStop room ::  " + confRoom + "  Participant " + participant + "  startRecordfile: " + startRecordfile + "  stopRecordFile: " + stopRecordFile);
		// System.err.println("enter fileRecordStartStop room ::  " + confRoom +
		// "  Participant " +
		// participant+"  startRecordfile: "+startRecordfile+"  stopRecordFile: "+stopRecordFile);
		// RoomInfo soi = voiceRooms.get(room);

		String room = UserCacheService.getActualRoom(confRoom);

		// System.err.println(" in  fileRecordStartStop .. the room is "+room);

		if (room == null) {
			log.warn(" the room is null for getting the file name --  Participant " + participant + " room " + room + "  confRoom: " + confRoom + "  startRecordfile: " + startRecordfile
					+ "  stopRecordFile: " + stopRecordFile);
			return;
		}

		String delimt = "#";

		if (startRecordfile != null)
			audioDataMessageService.sendMessage("RECORDSTART" + delimt + room + delimt + startRecordfile);
		else if (stopRecordFile != null)
			audioDataMessageService.sendMessage("RECORDSTOP" + delimt + room + delimt + stopRecordFile);

	}

	public void handleConferenceEvent(ConferenceEvent event) {

		//log.debug("Hurray --------- " + event);
		try {
			if (event instanceof ParticipantJoinedEvent) {
				ParticipantJoinedEvent pje = (ParticipantJoinedEvent) event;

				joined(pje.getRoom(), pje.getParticipantId(), pje.getCallerIdName(), pje.getMuted(), pje.getSpeaking(), pje.isLocked(), pje.getCallSource());
			} else if (event instanceof ParticipantLeftEvent) {
				left(event.getRoom(), event.getParticipantId());
			} else if (event instanceof ParticipantMutedEvent) {
				ParticipantMutedEvent pme = (ParticipantMutedEvent) event;
				muted(pme.getRoom(), pme.getParticipantId(), pme.isMuted());
			} else if (event instanceof ParticipantTalkingEvent) {
				ParticipantTalkingEvent pte = (ParticipantTalkingEvent) event;
				talking(pte.getRoom(), pte.getParticipantId(), pte.isTalking());
			} else if (event instanceof ParticipantLockedEvent) {
				ParticipantLockedEvent ple = (ParticipantLockedEvent) event;
				locked(ple.getRoom(), ple.getParticipantId(), ple.isLocked());
			} else if (event instanceof AudioFileStartStopEvent) {
				AudioFileStartStopEvent ple = (AudioFileStartStopEvent) event;
				fileRecordStartStop(ple.getRoom(), ple.getParticipantId(), ple.getStartFile(), ple.getStopFile());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		log.debug("Setting the main ---");
		this.main = main;
	}

}
