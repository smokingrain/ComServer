package com.xk.server.interfaces;

import org.apache.mina.core.session.IoSession;

import com.xk.server.beans.PackageInfo;

public interface ISession {

	public void setIoSession(IoSession sess) ;
	
	public String getSessionId();
	
	public boolean auth(PackageInfo info, ISession session);
	
	public boolean authed();
	
	public void sendMsg(PackageInfo info);
	
	public void close();
}
