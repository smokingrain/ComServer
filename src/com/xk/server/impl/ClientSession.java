package com.xk.server.impl;

import org.apache.mina.core.session.IoSession;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.ISession;
import com.xk.server.utils.JSONUtil;
import com.xk.server.utils.StringUtil;

public class ClientSession implements ISession {

	private IoSession sess;
	private String id;
	private boolean authed;
	
	public ClientSession() {
		id = StringUtil.createUID();
	}
	
	@Override
	public void setIoSession(IoSession sess) {
		this.sess = sess;
	}

	@Override
	public String getSessionId() {
		return id;
	}

	@Override
	public void close() {
		if(null != sess) {
			sess.closeNow();
		}
		
	}

	@Override
	public void auth() {
		authed = true;
	}
	
	@Override
	public boolean authed() {
		return authed;
	}

	@Override
	public void sendMsg(PackageInfo info) {
		if(null != sess) {
			sess.write(JSONUtil.toJosn(info));
		}
		
	}

}
