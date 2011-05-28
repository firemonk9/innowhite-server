/**
 * 
 */
package com.innowhite.red5;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.so.ISharedObject;
import org.slf4j.Logger;

import com.innowhite.red5.util.InnowhiteConstants;

/**
 * @author firemonk
 * 
 */
public class UserCacheService {

	private static Logger log = Red5LoggerFactory.getLogger(
			UserCacheService.class, InnowhiteConstants.APP_NAME);

	// has mapping of conference room vs Actual Room
	static ConcurrentHashMap<String, String> confRoom = new ConcurrentHashMap<String, String>();

	// has mapping of participant ID vs actual userID
	// ex: participantId = yuiyi123yi12u3yi123y123iu
	//
	static ConcurrentHashMap<String, String> participantIDUser = new ConcurrentHashMap<String, String>();

	// 6 digit number first 2 : personal code, 4 : room conf id.
	// has mapping of innoUnique ID(personal conference number) vs actual userID
	static ConcurrentHashMap<String, String> innoUniqueIDUser = new ConcurrentHashMap<String, String>();

	// has mapping of room ID vs shared object
	static ConcurrentHashMap<String, ISharedObject> roomIdUserSharedObj = new ConcurrentHashMap<String, ISharedObject>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void addConfRoom(String confNumber, String roomID) {

		confRoom.put(confNumber, roomID);
		log.debug(" adding  confNumber " + confNumber + " roomID " + roomID
				+ "   to map " + confRoom);
	}

	public static void removeConfRoom(String confNumber, String roomID) {

		confRoom.remove(confNumber);
	}

	/*
	 * Get the actual userId from innoUniqueIDUser and set in the con
	 * innoUniqueId = the first 2 digits of 6 digit personal extension
	 * actualUserID = actual user id.
	 */
	public static void addInnoUniqueIDUser(String innoUniqueId,
			String actualUserID) {

		// String userId = innoUniqueIDUser.get(innoUniqueId);
		if (innoUniqueId != null && actualUserID != null)
			innoUniqueIDUser.put(innoUniqueId.trim(), actualUserID.trim());

		log.debug(" adding  innoUniqueId " + innoUniqueId + " actualUserID "
				+ actualUserID + "   to map " + innoUniqueIDUser + "  "
				+ innoUniqueIDUser.get(innoUniqueId));

	}

	public static void removeparticipantIDUser(String participantId) {
		participantIDUser.remove(participantId);
	}

	/*
	 * Get the actual userId from innoUniqueIDUser and set in the con
	 */
	public static void addparticipantIDUser(String participantId,
			String innoUniqueId) {

		innoUniqueId = innoUniqueId.substring(0, 6);

		log.debug(" adding  participantId " + participantId + " innoUniqueId "
				+ innoUniqueId + "   to map " + innoUniqueIDUser
				+ "  the length of map is :" + innoUniqueIDUser.size());
		String userId = innoUniqueIDUser.get(innoUniqueId);
		if (userId != null)
			participantIDUser.put(participantId, userId);
		else {
			log.warn(" ---- SOMETHING IS NOT RIGHT ---- ");
			Collection c = innoUniqueIDUser.keySet();
			// obtain an Iterator for Collection
			Iterator itr = c.iterator();
			// iterate through HashMap values iterator
			while (itr.hasNext()) {

				String key = (String) itr.next();
				log.warn("key :: " + key + " value :: "
						+ innoUniqueIDUser.get(key) + "  my key  "
						+ innoUniqueId + "  length  " + innoUniqueId.length()
						+ "  and key length " + key.length());
			}

		}
		log.debug(" participantIDUser--  " + participantIDUser);

	}

	public static String getUseId(String participantId) {
		String result = participantIDUser.get(participantId);
		if (result == null) {

			log.warn("getUseId -- the result of for lookup participantId ::"
					+ participantId + "  in map ::" + participantIDUser);
		}

		return result;
	}

	// input is 4 digit conf number and returns actual RoomId
	public static String getActualRoom(String confId) {
		if (confId != null & confId.length() > 4)
			confId = confId.substring(0, 4);
		String result = confRoom.get(confId);
		if (result == null) {

			log.warn("getActualRoom -- the result of for lookup confId ::"
					+ confId + "  in map ::" + confRoom);
		}

		return result;
	}

	public static void addRoomIdUserSharedObj(String name,
			ISharedObject sharedObject) {

		log.debug(" adding to the map name " + name + "  shared obj "
				+ sharedObject);
		roomIdUserSharedObj.put(name, sharedObject);

	}

	public static void removeRoomIdUserSharedObj(String name) {

		log.debug(" Removing form the map name " + name);
		roomIdUserSharedObj.remove(name);
	}

	public static ISharedObject getUserSharedObj(String roomId) {

		ISharedObject result = roomIdUserSharedObj.get(roomId);
		if (result == null) {
			log.warn("getUserSharedObj is null  roomId " + roomId
					+ "   map is::" + roomIdUserSharedObj);
		}

		return result;
	}

	public static boolean roomExists(String roomId) {

		if (roomId != null) {
			if (confRoom.containsValue(roomId) == true)
				return true;
			else {
				log.warn(" the roomId " + roomId + " does not exist in "
						+ confRoom);
				return false;
			}
		}
		return true;
	}

}
