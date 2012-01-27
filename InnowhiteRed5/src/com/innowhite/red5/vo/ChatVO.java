package com.innowhite.red5.vo;

import java.io.Serializable;

public class ChatVO implements Serializable{
	
	String chattext;
	String textcolor;
	int seq;
	String privateChatTo;
	String recipient;
	String sender;
	boolean publicChat;
	
	public boolean isPublicChat() {
		return publicChat;
	}

	public void setPublicChat(boolean publicChat) {
		this.publicChat = publicChat;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getPrivateChatTo() {
		return privateChatTo;
	}

	public void setPrivateChatTo(String privateChatTo) {
		this.privateChatTo = privateChatTo;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getChattext() {
		return chattext;
	}

	public void setChattext(String chattext) {
		this.chattext = chattext;
	}

	public String getTextcolor() {
		return textcolor;
	}

	public void setTextcolor(String textcolor) {
		this.textcolor = textcolor;
	}

}
