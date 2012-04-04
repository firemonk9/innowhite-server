package com.innowhite.PlaybackApp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "temp_video_data")
public class WhiteboardVideoFrame implements Serializable {

	public static final Logger log = LoggerFactory.getLogger(WhiteboardVideoFrame.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Column(name = "seq")
	private int seq;

	@Column(name = "data")
	private byte[] data;

	@Column(name = "room_id")
	private String roomId;

	@Column(name = "file_id")
	private String fileId;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append(" id: " + id);

		sb.append(" roomId: " + roomId);
		sb.append(" fileId: " + fileId);
		sb.append(" seq: " + seq);

		return sb.toString();
	}

}
