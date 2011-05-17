/**
 * 
 */
package com.vo;

/**
 * @author prashanthj
 *
 */
public class DocConversionBean {

	private String userID;
	private int conversionID;
	private String filePath;
	private String serviceType;
	private String serverFilePath;
	
	public String getServerFilePath() {
		return serverFilePath;
	}
	public void setServerFilePath(String serverFilePath) {
		this.serverFilePath = serverFilePath;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
	
	public int getConversionID() {
		return conversionID;
	}
	public void setConversionID(int conversionID) {
		this.conversionID = conversionID;
	}
	
	@Override
	public String toString() {
		return "DocConversionBean [userID=" + userID + ", conversionID="
				+ conversionID + ", filePath=" + filePath + ", serviceType="
				+ serviceType + "]";
	}
	

	
}
