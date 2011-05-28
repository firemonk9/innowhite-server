package com.innowhite.whiteboard.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.innowhite.whiteboard.persistence.beans.ClientMediaVO;
import com.innowhite.whiteboard.persistence.beans.LessonPlanVO;
import com.innowhite.whiteboard.persistence.dao.ClientMediaDAO;
import com.innowhite.whiteboard.persistence.dao.LessonPlanXMLDAO;
import com.innowhite.whiteboard.util.InnowhiteConstants;
import com.util.Constants;
import com.util.Utility;

public class LessonPlanGetDataService {

	/**
	 * @param args. This class gets the components and caches them. 
	 */

	private static Logger log = Red5LoggerFactory.getLogger(LessonPlanGetDataService.class, InnowhiteConstants.APP_NAME);
	
	static ExecutorService threadExecutor = Executors.newFixedThreadPool(20);

	public static void main(String[] args) {
		// log.debug("  "+getXML("",""));

		try {

			// String xml = getXML("14", "2");
			// // xml = getXMLTest("", "");
			//
			// log.debug("   ");
			// Map<String, Map<String, String>> map = parseXML(xml);
			//
			// String baseURL =
			// Utility.getServerURL("http://127.0.0.1:5080/whiteboard/asd",
			// InnowhiteConstants.APP_NAME);
			//
			// log.debug(" the result of map is :: " + map);
			//
			// for (String key : map.keySet()) {
			// if (key != null) {
			// Map<String, String> tempMap = map.get(key);
			// for (String componentId : tempMap.keySet()) {
			//
			// String componentSource = tempMap.get(componentId);
			// String exists = checkIfContentExistsLocally(componentId);
			// if (exists == null) {
			//
			// ClientMediaVO clientMediaVO = new ClientMediaVO();
			// clientMediaVO.setContentId(componentId);
			// clientMediaVO.setContentSource(componentSource);
			// clientMediaVO.setContentType(key);
			// getContent(clientMediaVO);
			//
			// }
			//
			// }
			//
			// }
			// }

			cacheAllData("14", "4",null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void cacheAllData(String courseId, String lessonPlanId, String requestedURL) {
		// log.debug("  "+getXML("",""));

		try {

			log.debug(" entered  cacheAllData requestedURL or orgName "+requestedURL+"  lessonPlanId "+lessonPlanId+"  courseId "+courseId+"  orgName "+requestedURL);
			String xml = getXML(lessonPlanId,courseId,requestedURL);
			// xml = getXMLTest(courseId, lessonPlanId);

			String orgName = 	requestedURL;
			//log.debug(" xml ::"+xml);
			Map<String, Map<String, String>> map = parseXML(xml);
			// String key=null;
			log.debug("   "+map);

			for (String key : map.keySet()) {
				if (key != null) {

					Map<String, String> tempMap = map.get(key);
					for (String componentId : tempMap.keySet()) {

						String componentSource = tempMap.get(componentId);
						String exists = checkIfContentExistsLocally(componentId,orgName);
						if (exists == null) {

							ClientMediaVO clientMediaVO = new ClientMediaVO();
							clientMediaVO.setContentId(componentId);

							log.debug(" The component type : " + key);
							//String out = alterComponentSource(componentSource, key);

						//	clientMediaVO.setContentSourceFileExt(out);
							clientMediaVO.setContentSource(componentSource);
							clientMediaVO.setContentType(key);
							clientMediaVO.setOrgName(orgName);
							getContent(clientMediaVO);

						}

					}

				}
			}

			LessonPlanVO lessonPlanVO = new LessonPlanVO();
			lessonPlanVO.setCourseID(courseId);
			lessonPlanVO.setLessonPlanId(lessonPlanId);
			lessonPlanVO.setLessonPlanXML(xml);
			lessonPlanVO.setOrgName(orgName);
			// save the xml in the database
			LessonPlanXMLDAO.saveLessonPlanXML(lessonPlanVO);

			// log.debug(getInnowhiteXML("123","asdasdasdasd","http://127.0.0.1:5080/whiteboard/asd"));
			log.debug(" exiting  cacheAllData");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * When there is no extension we give default extension.
	 */
	private static String alterComponentSource(String componentSource, String key) {

		log.debug("  Entered :: alterComponentSource key" + key);
		String out = componentSource;
		if (key.trim().equals(InnowhiteConstants.IMAGE_TYPE)) {
			if (componentSource != null && !componentSource.endsWith(".jpeg")) {

				out = componentSource + ".jpeg";
			}
		}
		if (key.trim().equals(InnowhiteConstants.AUDIO_TYPE)) {
			if (componentSource != null && !componentSource.endsWith(".mp3")) {

				out = componentSource + ".mp3";
			}

		}
		if (key.trim().equals(InnowhiteConstants.VIDEO_TYPE)) {
			if (componentSource != null && !componentSource.endsWith(".flv")) {

				out = componentSource + ".flv";
			}

		}
		if (key.trim().equals(InnowhiteConstants.EUREKA_TYPE)) {
			if (componentSource != null && !componentSource.contains(".swf")) {

				out = componentSource + ".swf";
			}

		}
		if (key.trim().equals(InnowhiteConstants.DOCUMENT_TYPE)) {
			if (componentSource != null && !componentSource.contains(".ppt")) {

				out = componentSource + ".ppt";
			}

		}

		log.debug(" in " + componentSource + "  out " + out);
		return out;
	}

	private static String getXMLTest(String string, String string2) {
		StringBuffer sb = new StringBuffer();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream("t.xml");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				// log.debug(strLine);
				sb.append(strLine);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			log.debug("Error: " + e.getMessage());
		}
		return sb.toString();
	}

	/*
	 * Gets the xml from the remote client
	 */
	private static String getXML(String lessonPlanId, String courseId, String hostName) {

		StringBuilder sb = new StringBuilder();
		// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String xml = null;
		String requestURL = "http://"+hostName+"/mentor.php/eclass/lessonPlanXML?lesson_plan_id=" + lessonPlanId + "&course_id=" + courseId;
		log.debug("URL in invokeRoomStatus: " + requestURL);
		try {
			URL url = new URL(requestURL);
			// DocumentBuilder db = dbf.newDocumentBuilder();

			URLConnection connection = url.openConnection();
			connection.setDoInput(true);
			InputStream inStream = connection.getInputStream();
			BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
			String line = "";
			while ((line = input.readLine()) != null) {
				sb.append(line);
			}
			xml = sb.toString();
			// log.debug(sb.toString());
		} catch (Exception e) {
			log.debug(e.toString());
		}
		return xml;

	}

	/*
	 * Gets the Innowhite xml from the remote client dabase and changes the
	 * source.
	 */
	public static String getInnowhiteXML(String courseId, String lessonPlanId, String baseURL, String orgName) {

		// gets the original xml.

		log.debug("entered getInnowhiteXML orgName"+orgName+"  lessonPlanId "+lessonPlanId+"  courseId "+courseId);
		String xml = LessonPlanXMLDAO.getLessonPlanXML(courseId, lessonPlanId,orgName);
		//log.debug("  the xml is "+xml);
		// need to change the source of the xml
		Map<String, Map<String, String>> map;
		try {
			map = parseXML(xml);

			for (String key : map.keySet()) {
				if (key != null) {
					Map<String, String> tempMap = map.get(key);
					for (String componentId : tempMap.keySet()) {

						String componentSource = tempMap.get(componentId);
					

						// relativePath =
						
						
							String innoUrl = getInnowhiteContentURL(key, componentId, baseURL,orgName);

							xml = xml.replace(componentSource, innoUrl);
							log.debug(" ....1 componentSource " + componentSource  +" componentId  "+componentId+"   ");
							log.debug("componentSource   "+componentSource+"  innoUrl "+innoUrl);
							
					
					}

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(" errors in processign XML :::  ", e);
			e.printStackTrace();
		}
		log.debug("  the output xml :: " + xml);

		return xml;
	}

	private static String getInnowhiteContentURL(String key, String componentId,  String baseURL, String orgName) {

//		if (key.equals(InnowhiteConstants.VIDEO_TYPE) ) {
//			
//			String componentSource = ClientMediaDAO.getContentSource(componentId);
//			log.debug(" componentSource " + componentSource);
//			log.debug(" componentSource / :" + componentSource.lastIndexOf("/"));
//			log.debug(" componentSource . :" + componentSource.lastIndexOf("."));
//			return Constants.RTMP_BASE_URL + componentSource.substring(componentSource.lastIndexOf("/") + 1, componentSource.lastIndexOf("."));
//		}
		String mybaseURL = Utility.getServerURL(baseURL, Constants.APP_NAME);
		return mybaseURL + Constants.APP_NAME + InnowhiteConstants.FILE_SEPRATOR + Constants.MEDIA_URL+componentId + "&orgName="+orgName;
	}

	/*
	 * Parse the xml from
	 */
	private static Map<String, Map<String, String>> parseXML(String responseXML) throws Exception {

		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		InputSource is;
		Document document;
		
		if(responseXML == null )
			return null;
		
		// String wbSessionID = "-1";
		Map<String, Map<String, String>> myMap = new HashMap<String, Map<String, String>>();
		try {

			// log.debug(" content :: " + responseXML);

			builder = dbfactory.newDocumentBuilder();
			is = new InputSource();
			is.setCharacterStream(new StringReader(responseXML));
			document = builder.parse(is);
			XPath xpath = XPathFactory.newInstance().newXPath();
			// XPath Query for showing all nodes value
			XPathExpression expr = xpath.compile("/lesson_plan/components/component");
			XPathExpression expr1 = null;
			XPathExpression exprType = null;
			Object result = expr.evaluate(document, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;

			myMap.put(InnowhiteConstants.IMAGE_TYPE, new HashMap<String, String>());
			myMap.put(InnowhiteConstants.VIDEO_TYPE, new HashMap<String, String>());
			myMap.put(InnowhiteConstants.AUDIO_TYPE, new HashMap<String, String>());
			myMap.put(InnowhiteConstants.DOCUMENT_TYPE, new HashMap<String, String>());
			myMap.put(InnowhiteConstants.EUREKA_TYPE, new HashMap<String, String>());

			for (int i = 1; i <= nodes.getLength(); i++) {
				// log.debug(nodes.item(i).getNodeValue());

				expr = xpath.compile("/lesson_plan/components/component[" + i + "]/component_id/text()");
				expr1 = xpath.compile("/lesson_plan/components/component[" + i + "]/component_source/text()");
				exprType = xpath.compile("/lesson_plan/components/component[" + i + "]/component_type/text()");

				String str = (String) expr.evaluate(document, XPathConstants.STRING);
				String source = (String) expr1.evaluate(document, XPathConstants.STRING);
				String type = (String) exprType.evaluate(document, XPathConstants.STRING);

				log.debug(i + " str " + str + " source " + source + " type  " + type);

				if (str != null && str.trim().length() > 1)
					populateMap(type, myMap, str, source);

				// log.debug("value ::  "+str+"    "+source);

			}

			log.debug(" ************** ");

			expr = xpath.compile("/lesson_plan/components/component/subcomponents/subcomponent");
			expr1 = null;
			result = expr.evaluate(document, XPathConstants.NODESET);
			nodes = (NodeList) result;

			for (int i = 1; i <= nodes.getLength(); i++) {
				 log.debug(" subcomponent  ");

				expr = xpath.compile("/lesson_plan/components/component/subcomponents/subcomponent[" + i + "]/subcomponent_quest_asset_id/text()");
				expr1 = xpath.compile("/lesson_plan/components/component/subcomponents/subcomponent[" + i + "]/subcomponent_source/text()");
				exprType = xpath.compile("/lesson_plan/components/component/subcomponents/subcomponent[" + i + "]/subcomponent_type/text()");

				String str = (String) expr.evaluate(document, XPathConstants.STRING);
				String source = (String) expr1.evaluate(document, XPathConstants.STRING);
				String type = (String) exprType.evaluate(document, XPathConstants.STRING);

				if (str != null && str.trim().length() > 1)
					populateMap(type, myMap, str, source);

				 log.debug("subcomponent  value ::  "+str+"    "+source);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return myMap;
	}

	private static void populateMap(String componentType, Map<String, Map<String, String>> myMap, String id, String source) {

		Map<String, String> m = null;
		if (componentType.equals(InnowhiteConstants.IMAGE_TYPE)) {
			m = myMap.get(InnowhiteConstants.IMAGE_TYPE);
			m.put(id, source);
		} else if (componentType.equals(InnowhiteConstants.VIDEO_TYPE)) {
			m = myMap.get(InnowhiteConstants.VIDEO_TYPE);
			m.put(id, source);
		} else if (componentType.equals(InnowhiteConstants.AUDIO_TYPE)) {
			m = myMap.get(InnowhiteConstants.AUDIO_TYPE);
			m.put(id, source);
		} else if (componentType.equals(InnowhiteConstants.DOCUMENT_TYPE)) {
			m = myMap.get(InnowhiteConstants.DOCUMENT_TYPE);
			m.put(id, source);
		} else if (componentType.equals(InnowhiteConstants.EUREKA_TYPE)) {
			m = myMap.get(InnowhiteConstants.EUREKA_TYPE);
			m.put(id, source);
		}

	}

	/*
	 * Get content from remote client
	 */
	public static void getContent(ClientMediaVO clientMediaVO) {

		DownloadThread task1 = new DownloadThread(clientMediaVO);

		log.debug("Starting threads");

		threadExecutor.execute(task1); // start task1

		log.debug("Threads started, main ends\n");

	}

	/*
	 * Check if the content needs to be pulled from remote client
	 */
	private static String checkIfContentExistsLocally(String contentId, String orgName) {

		log.debug(" contentId  "+contentId+"  orgName "+orgName);
		String source = ClientMediaDAO.checkMediaExists(contentId,orgName);
		if (source == null)
			return null;
		else
			return source;
	}

	/*
	 * Once the content is pulled from remote client cache it for subsequent
	 * usage.
	 */
	public static void updateContentLocalDB() {

	}

}
