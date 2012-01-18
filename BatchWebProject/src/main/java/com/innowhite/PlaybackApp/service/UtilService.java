package com.innowhite.PlaybackApp.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UtilService {

    private static String nameStr = "//web-session/session-name/text()";
    private static String descStr = "//web-session/session-desc/text()";
    private static final Logger log = LoggerFactory.getLogger(UtilService.class);

    public static void main(String args[]) {
	System.err.println(" this is the name  " + getRoomName(null));
    }

    public static String getRoomName(String xml) {

	return getData(xml, nameStr);

    }

    public static String getRoomDescription(String xml) {
	// TODO Auto-generated method stub
	return getData(xml, descStr);
    }

    private static String getData(String xml, String val) {

	log.debug("Entered getData...");
	log.debug("parameters ::  val "+val);
	
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware(true); // never forget this!
	DocumentBuilder builder;
	try {
	    builder = factory.newDocumentBuilder();
	    
	    ByteArrayInputStream bs = new ByteArrayInputStream(xml.getBytes());
	    
	    Document doc = builder.parse(bs);

	    XPathFactory xpathfactory = XPathFactory.newInstance();
	    XPath xpath = xpathfactory.newXPath();
	    XPathExpression expr = xpath.compile(val);

	    Object result = expr.evaluate(doc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    String sessionName = null;
	    for (int i = 0; i < nodes.getLength(); i++) {
		// log.debug(nodes.item(i).getNodeValue());
		sessionName = nodes.item(i).getNodeValue().toString();
	    }

	    return sessionName;

	} catch (ParserConfigurationException e) {
	    log.error(e.getMessage(), e);
	} catch (SAXException e) {
	    log.error(e.getMessage(), e);
	} catch (IOException e) {
	    log.error(e.getMessage(), e);
	} catch (XPathExpressionException e) {
	    log.error(e.getMessage(), e);
	}catch (Exception e) {
	    log.error(e.getMessage(), e);
	}

	return null;

    }

}
