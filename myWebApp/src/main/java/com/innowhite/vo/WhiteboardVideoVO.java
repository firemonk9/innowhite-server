package com.innowhite.vo;

import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;

public class WhiteboardVideoVO {

	private DataOutputStream os;
	private int id;
	private int nextFrameNum = 0;
	private String fileId=null;
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	private Map<Integer, byte[]> map = new HashMap<Integer, byte[]>();

	public int getNextFrameNum() {
		return nextFrameNum;
	}

	public void setNextFrameNum(int lastFrameNum) {
		this.nextFrameNum = lastFrameNum;
	}

	public WhiteboardVideoVO(String fileId, int id) {
		this.fileId = fileId;
		this.id = id;
	}

	public DataOutputStream getOs() {
		return os;
	}

	public int getId() {
		return id;
	}

	public void addToMap(int val, byte[] data){
		map.put(val, data);
	}
	
	public byte[] getFromMap(int val){
		byte[] data = map.get(val);
		if(data != null)
			map.remove(val);
		return data;
	}
	
	
}
