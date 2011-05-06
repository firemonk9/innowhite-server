package com.innowhite.whiteboard.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

public class SAXCopy {
	
	private static final Logger log = LoggerFactory.getLogger(SAXCopy.class);
	
	static public void main(String[] arg) {
		String infilename = "/Users/firemonk/Desktop/room1.xml";
		String outfilename = "/Users/firemonk/Desktop/room2.xml";
		// if (arg.length == 2) {
		// infilename = arg[0];
		// outfilename = arg[1];
		// } else {
		// usage();
		// }

		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setErrorHandler(new MyErrorHandler());
			FileOutputStream fos = new FileOutputStream(outfilename);
			PrintWriter out = new PrintWriter(fos);
			MyCopyHandler duper = new MyCopyHandler(out, "1");
			reader.setContentHandler(duper);
			InputSource is = new InputSource(infilename);
			reader.parse(is);
			
			out.close();
		} catch (SAXException e) {
			System.exit(1);
		} catch (ParserConfigurationException e) {
			log.error("Parse error", e);
			System.exit(1);
		} catch (IOException e) {
			log.error("io error",e);
			System.exit(1);
		}
	}

	private static void usage() {
		log.debug("Usage: SAXCopy <infile> <outfile>");
		System.exit(1);
	}
}

class MyCopyHandler implements ContentHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MyCopyHandler.class);
	
	private boolean namespaceBegin = false;

	private String currentNamespace;

	private String currentNamespaceUri;

	private Locator locator;

	private PrintWriter out;

	private boolean copyNode = false;
	private boolean beginCopyNode = false;

	private StringBuffer sb = new StringBuffer();

	private String pageNumber = "1";

	public MyCopyHandler(PrintWriter out, String pageNum) {
		pageNumber = pageNum;
		this.out = out;
	}

	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}

	public void startDocument() {
//		out.println("<?xml version=\"1.0\"?>");
//		out.println();
	}

	public void endDocument() {
		out.write("</object-stream>");
		log.debug("endDocument ");
	}

	public void startPrefixMapping(String prefix, String uri) {
		namespaceBegin = true;
		currentNamespace = prefix;
		currentNamespaceUri = uri;
	}

	public void endPrefixMapping(String prefix) {
		log.debug("endPrefixMapping ");
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
		sb.append("<" + qName);
		if (namespaceBegin) {
			sb.append(" xmlns:" + currentNamespace + "=\"" + currentNamespaceUri + "\"");
			namespaceBegin = false;
		}
		for (int i = 0; i < atts.getLength(); i++) {
			sb.append(" " + atts.getQName(i) + "=\\" + atts.getValue(i) + "\"");
		}
		sb.append(">");

		if (qName != null && qName.equals("wbNumber")) {
			beginCopyNode = true;
		}

	}

	static int i = 0;

	public void endElement(String namespaceURI, String localName, String qName) {
		sb.append("</" + qName + ">");

		if (qName != null && qName.equals("com.vo.ShapeEventsVO")) {
			

			if (copyNode == true) {

				log.debug(" count " + i++);
				out.println(sb.toString());

				copyNode = false;
			}
			sb = new StringBuffer();
		}

	}

	public void characters(char[] ch, int start, int length) {
		String val = "";
		for (int i = start; i < start + length; i++)
			val = val + ch[i];

		sb.append(val);

		if (beginCopyNode == true) {
			if (val != null && val.contains("."))
				val = val.substring(0, val.indexOf("."));

			log.debug(val);

			beginCopyNode = false;
			if (val != null && val.trim().equals(pageNumber)) {
				copyNode = true;
			}
		}
	}

	public void ignorableWhitespace(char[] ch, int start, int length) {
		for (int i = start; i < start + length; i++)
			sb.append(ch[i]);
	}

	public void processingInstruction(String target, String data) {
		sb.append("<?" + target + " " + data + "?>");
	}

	public void skippedEntity(String name) {
		sb.append("&" + name + ";");
	}
}

class MyErrorHandler implements ErrorHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MyErrorHandler.class);
	public void warning(SAXParseException e) throws SAXException {
		show("Warning", e);
		throw (e);
	}

	public void error(SAXParseException e) throws SAXException {
		show("Error", e);
		throw (e);
	}

	public void fatalError(SAXParseException e) throws SAXException {
		show("Fatal Error", e);
		throw (e);
	}

	private void show(String type, SAXParseException e) {
		log.debug(type + ": " + e.getMessage());
		log.debug("Line " + e.getLineNumber() + " Column " + e.getColumnNumber());
		log.debug("System ID: " + e.getSystemId());
	}
}

// Example XML document
/*
 * An XML Document
 * 
 * <?xml version="1.0" standalone="yes"?> <person> <name>Joe Wang</name> <phone
 * type="home">306 555-9999</phone> <email>joe@yoursite.net;</email> </person>
 */
