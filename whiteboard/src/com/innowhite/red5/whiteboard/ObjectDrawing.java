package com.innowhite.red5.whiteboard;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.Red5;
import org.red5.server.api.so.ISharedObject;
import org.slf4j.Logger;

import com.innowhite.red5.vo.ShapeEventsVO;

public class ObjectDrawing {
	// private static final Log log = LogFactory.getLog(ObjectDrawing.class);

	private Main whiteboard;

	private int keyIndex = 0;

	private static Logger log = Red5LoggerFactory.getLogger(ObjectDrawing.class, InnowhiteConstants.APP_NAME);
	
	public ObjectDrawing() {

	}

	public ObjectDrawing(Main whiteboard) {
		this.whiteboard = whiteboard;
	}

	public void doDraw(ShapeEventsVO se) {

		String clientId = Red5.getConnectionLocal().getClient().getAttribute("userid").toString();
		// log.info((new
		// StringBuilder()).append("The client id for the client is : ").append(clientId).toString());
		ISharedObject objectDrawingSO = whiteboard.getSharedObject(Red5.getConnectionLocal().getScope(), "ObjectDrawingSO");
		objectDrawingSO.setAttribute(Integer.toString(keyIndex++), se);

		// ISharedObject obj = this.getSharedObject(room, "ObjectDrawingSO");
		// List ll = SavingData.readObj("test");
		// ListIterator<ShapeEventsVO> li = ll.listIterator();
		// int i=0;
		// while(li.hasNext())
		// {
		// ShapeEventsVO t = li.next();
		// objectDrawingSO.setAttribute(new Integer(i).toString(),t);
		// log.debug(t.getActionType());
		//		      			
		// }

	}
}
