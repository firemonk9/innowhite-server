package com.innowhite.whiteboard.docconversion.vo;

public class FileTransformatioBean {
	
	
	private String originalFilePath;
	private String thumbsFolder;
	private String swfFolder;
	private String thumbnailBatFilePath;
	private String swfBatFilePath;
	private String originalFileName;
	private String originalFileNameStripped;
	private String thumbnailBatFileContent;
	private String swfBatFileContent;
	private String originalDir;
	private String thumbServerBasePath;
	private String originalServerBasePath;
	
	
	private String thumbsCommand;
	private String actualFileCommand;
	
	
	
	public String getThumbsCommand() {
		return thumbsCommand;
	}
	public void setThumbsCommand(String thumbsCommand) {
		this.thumbsCommand = thumbsCommand;
	}
	public String getActualFileCommand() {
		return actualFileCommand;
	}
	public void setActualFileCommand(String actualFileCommand) {
		this.actualFileCommand = actualFileCommand;
	}


	public String getThumbServerBasePath() {
		return thumbServerBasePath;
	}
	public void setThumbServerBasePath(String thumbServerBasePath) {
		this.thumbServerBasePath = thumbServerBasePath;
	}
	public String getOriginalServerBasePath() {
		return originalServerBasePath;
	}
	public void setOriginalServerBasePath(String originalServerBasePath) {
		this.originalServerBasePath = originalServerBasePath;
	}
	public String getOriginalFilePath() {
		return originalFilePath;
	}
	public void setOriginalFilePath(String originalFilePath) {
		this.originalFilePath = originalFilePath;
	}
	public String getThumbsFolder() {
		return thumbsFolder;
	}
	public void setThumbsFolder(String thumbsFolder) {
		this.thumbsFolder = thumbsFolder;
	}
	public String getSwfFolder() {
		return swfFolder;
	}
	public void setSwfFolder(String swfFolder) {
		this.swfFolder = swfFolder;
	}
	public String getThumbnailBatFilePath() {
		return thumbnailBatFilePath;
	}
	public void setThumbnailBatFilePath(String thumbnailBatFilePath) {
		this.thumbnailBatFilePath = thumbnailBatFilePath;
	}
	public String getSwfBatFilePath() {
		return swfBatFilePath;
	}
	public void setSwfBatFilePath(String swfBatFilePath) {
		this.swfBatFilePath = swfBatFilePath;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getOriginalFileNameStripped() {
		return originalFileNameStripped;
	}
	public void setOriginalFileNameStripped(String originalFileNameStripped) {
		this.originalFileNameStripped = originalFileNameStripped;
	}
	
	public String getThumbnailBatFileContent() {
		return thumbnailBatFileContent;
	}
	public void setThumbnailBatFileContent(String thumbnailBatFileContent) {
		this.thumbnailBatFileContent = thumbnailBatFileContent;
	}
	public String getSwfBatFileContent() {
		return swfBatFileContent;
	}
	public void setSwfBatFileContent(String swfBatFileContent) {
		this.swfBatFileContent = swfBatFileContent;
	}
	
	
	public String getOriginalDir() {
		return originalDir;
	}
	public void setOriginalDir(String originalDir) {
		this.originalDir = originalDir;
	}
	
	
	@Override
	public String toString() {
		return "FileTransformatioBean [originalFilePath=" + originalFilePath
				+ ", thumbsFolder=" + thumbsFolder + ", swfFolder=" + swfFolder
				+ ", thumbnailBatFilePath=" + thumbnailBatFilePath
				+ ", swfBatFilePath=" + swfBatFilePath + ", originalFileName="
				+ originalFileName + ", originalFileNameStripped="
				+ originalFileNameStripped + ", thumbnailBatFileContent="
				+ thumbnailBatFileContent + ", swfBatFileContent="
				+ swfBatFileContent + ", originalDir=" + originalDir + "]";
	}
	
	
	
	
	
	
	
	
	

}
