package com.xk.server.beans;

import org.apache.mina.core.session.IoSession;

public class HConnection {
	private IoSession session;
	private String name;
	private String ip;
	
	public HConnection(IoSession session, String name, String ip) {
		super();
		this.session = session;
		this.name = name;
		this.ip = ip;
	}
	
	public IoSession getSession() {
		return session;
	}
	
	public void setSession(IoSession session) {
		this.session = session;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
