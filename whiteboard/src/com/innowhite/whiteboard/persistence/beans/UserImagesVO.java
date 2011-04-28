package com.innowhite.whiteboard.persistence.beans;

import java.util.Date;

public class UserImagesVO {

	private int id;
	private String userName;
	private String imageURL;
	private String imageDescription;
	private String imageFolder;
	private int imageFolderSeq;
	private int imageGroup;
	private String imageName;
	private int imageType;
	private Date insertedDate;
	private int conversionID;
	private String thumbnailURL;
	

	public UserImagesVO(){
		
	}
	
	/** minimal constructor */
	public UserImagesVO(String userName, String imageUrl, Integer imageFolderSeq) {
		this.userName = userName;
		this.imageURL = imageUrl;
		this.imageFolderSeq = imageFolderSeq;
	}

	/** full constructor */
	public UserImagesVO(String userName, String imageUrl,
			String imageDescription, String imageFolder,
			Integer imageFolderSeq, Integer imageGroup, String imageName,
			Integer imageType) {
		this.userName = userName;
		this.imageURL = imageURL;
		this.imageDescription = imageDescription;
		this.imageFolder = imageFolder;
		this.imageFolderSeq = imageFolderSeq;
		this.imageGroup = imageGroup;
		this.imageName = imageName;
		this.imageType = imageType;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getImageDescription() {
		return imageDescription;
	}
	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}
	public String getImageFolder() {
		return imageFolder;
	}
	public void setImageFolder(String imageFolder) {
		this.imageFolder = imageFolder;
	}
	public int getImageFolderSeq() {
		return imageFolderSeq;
	}
	public void setImageFolderSeq(int imageFolderSeq) {
		this.imageFolderSeq = imageFolderSeq;
	}
	public int getImageGroup() {
		return imageGroup;
	}
	public void setImageGroup(int imageGroup) {
		this.imageGroup = imageGroup;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public int getImageType() {
		return imageType;
	}
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}
	
	
	public int getConversionID() {
		return conversionID;
	}

	public void setConversionID(int conversionID) {
		this.conversionID = conversionID;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}
	
	

	
	
}
