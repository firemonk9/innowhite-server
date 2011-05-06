package com.innowhite.whiteboard.persistence.beans;


public class ClientMediaVO {

	private int id;
	private String contentId;
	private String contentSource;
	private String contentType;
	private String contentSourceFileExt;
	private String documentXML;
	private String orgName;
	//private char roomActive;

	
	public String getDocumentXML() {
		return documentXML;
	}
	public void setDocumentXML(String documentXML) {
		this.documentXML = documentXML;
	}
	public String getContentSourceFileExt() {
		return contentSourceFileExt;
	}
	public void setContentSourceFileExt(String contentSourceFileExt) {
		this.contentSourceFileExt = contentSourceFileExt;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContentSource() {
		return contentSource;
	}
	public void setContentSource(String contentSource) {
		this.contentSource = contentSource;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgName() {
		return orgName;
	}
		
	
}
