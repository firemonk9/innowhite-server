package com.innowhite.red5.whiteboard;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.so.ISharedObject;
import org.slf4j.Logger;

import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.thoughtworks.xstream.XStream;
import com.util.Constants;
import com.util.InnowhiteProperties;
import com.vo.ChatVO;
import com.vo.ShapeEventsVO;

public class SavingData {

	public static HashMap<String, ObjectOutputStream> fileMap = new HashMap<String, ObjectOutputStream>();
	static XStream xstream=null;
	
	private static Logger log = Red5LoggerFactory.getLogger(SavingData.class, "whiteboard");
	
	static{
		 xstream = new XStream();
		
		 //String string = properties.getProperty("a.b");
	}
	
	public static void openNewFile(String room) {

		if (room != null && !room.equals("PRIV_SESSION")) {
			ObjectOutputStream outputStream = null;
			try {
				
				
				String urlPath = InnowhiteProperties.getPropertyVal("DATA_PATH") + room + ".xml";
				urlPath = urlPath.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);
				File f = new File(urlPath);
				log.debug(" opening new file ---------: "+f.getName());

			//	outputStream = new ObjectOutputStream();
				//xstream.c
				
				
				ObjectOutputStream out = xstream.createObjectOutputStream(new FileOutputStream(f));
				
				//ObjectOutputStream out = xstream.createObjectOutputStream();
				// out = (new FileOutputStream(Constants.FOLDER_PATH+room)));
				fileMap.put(room, out);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void closeFile(String room) {

		try {
			ObjectOutputStream out = null;
			out = fileMap.get(room);
			//out.flush();
			if(out != null)
			{	
				out.flush();
				out.close();
				out=null;
				fileMap.remove(room);
			}log.debug("##### CLOSING FILE  -------"+room);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void saveToFile(ShapeEventsVO obj, String room) {

		// serialize the Queue
		// log.debug("serializing theQueue");

		ObjectOutputStream out = null;
		try {
			out = fileMap.get(room);
			// FileOutputStream fout = new FileOutputStream("thequeue.dat");
			// ObjectOutputStream oos = new ObjectOutputStream(fout);
			out.writeObject(obj);
			
			// oos.close();

			// readObj();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void saveToFile(ChatVO obj, String room) {

		// serialize the Queue
		// log.debug("serializing theQueue");

		ObjectOutputStream out = null;
		try {
			out = fileMap.get(room);
			// FileOutputStream fout = new FileOutputStream("thequeue.dat");
			// ObjectOutputStream oos = new ObjectOutputStream(fout);
			
			out.writeObject(obj);
			out.flush();
			// oos.close();

			// readObj();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void readObj(String s, ISharedObject objectDrawingSO,
			ISharedObject objectChatSO) {
		log.debug("unserializing theQueue");
		ShapeEventsVO obj = null;
		ChatVO objChat = null;
		// List l = new ArrayList<ShapeEventsVO>();
		ObjectInputStream ois = null;
		int i = 0;
		try {

			
			String ss = InnowhiteProperties.getPropertyVal("DATA_PATH") + s + ".xml";
			ss = ss.replaceAll(Constants.APP_NAME, InnowhiteConstants.CONTEXT_PATH);

			
			//String ss = Constants.FOLDER_PATH + s + ".dat";
			log.debug("::  " + ss);
			//File f = new File(ss);
		//	log.debug(f.canRead() + "   " + f.getAbsolutePath()
		//			+ "    " + f.getPath());
			FileInputStream fin = new FileInputStream(ss);
			//BufferedInputStream bf = new BufferedInputStream(fin);
			//ois = new xst(fin);
			
			log.debug("  xstream: "+ xstream+"  fin: "+fin);
			ois = xstream.createObjectInputStream(fin);
			log.debug("  ois "+ ois);
	//		fileMap.put(s, ois);

			while(true){
				// if(ois.)
				//if (ois.available() > 0) {
				//ois.
					Object o = ois.readObject();
					//log.debug(o);
					if (o == null)
						break;

					if (o instanceof ChatVO) {
						// log.debug("  ChatVO: "+o.toString());
						objChat = (ChatVO) o;
						// l.add(objChat);
						objectChatSO.setAttribute(""+objChat.getSeq(),
								objChat);

					} else if (o instanceof ShapeEventsVO) {

						// log.debug("  ShapeEventsVO: "+o.toString());
						obj = (ShapeEventsVO) o;
						// l.add(obj);
						objectDrawingSO.setAttribute(obj.getObjName(), obj);
						// log.debug(" seq  "+obj.getSeq()+"  objtype "+obj.getObjType()+"  objname  "+obj.getObjName()+"  actiontype  "+obj.getActionType());

					}
			//	}
				// obj = (ShapeEventsVO)o;
				// l.add(obj);
				// objectDrawingSO.setAttribute(obj.getObjName(), obj);
			}

		} catch (EOFException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
