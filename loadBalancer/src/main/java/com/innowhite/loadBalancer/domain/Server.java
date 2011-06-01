package com.innowhite.loadBalancer.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Server {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	@Column(name = "server_name")
	private String serverName;
	
	@Column(name = "server_addr")
	private String serverAddr;
	
	@Column(name = "creation_time")
	private Date creationTime;
	
	@Column(name = "server_active")
	private boolean serverActive;
	
	@Column(name = "org_name")
	private String orgName;
	
	@Column(name = "app_name")
	private String appName;
	

	public String getAppName() {
	    return appName;
	}

	public void setAppName(String appName) {
	    this.appName = appName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public boolean isServerActive() {
		return serverActive;
	}

	public void setServerActive(boolean serverActive) {
		this.serverActive = serverActive;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
