package com.xk.server.impl;

import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.ISession;
import com.xk.server.managers.SessionManager;
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
	public boolean auth(PackageInfo info, ISession session) {
		String from = info.getFrom();
		if(StringUtil.isBlank(from)){
			authed = false;
			return authed;
		}
		ISession old = SessionManager.getSession(from);
		if(null != old) {
			authed = false;
			return authed;
		}
		String msg = info.getMsg();
		Map<String, Object> uInfo = JSONUtil.fromJson(msg);
		if(null == uInfo) {
			authed = false;
			return authed;
		}
		String name = (String) uInfo.get("name");
		if(StringUtil.isBlank(name)) {
			authed = false;
			return authed;
		}
		XClient client = new XClient(from, name);
		SessionManager.createSession(from, session, client);
		return authed;
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
