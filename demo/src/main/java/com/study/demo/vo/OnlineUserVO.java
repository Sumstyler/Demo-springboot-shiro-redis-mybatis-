package com.study.demo.vo;

import java.util.Date;

public class OnlineUserVO {

	private String sessionId;

	private String username;

	private String ip;

	private Date loginTime;

	private Date lastVisitTime;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

}
