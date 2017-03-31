package com.xk.server.impl;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.IMessageHandler;
import com.xk.server.interfaces.ISession;
import com.xk.server.managers.SessionManager;

public class AuthInterceptor implements IMessageHandler {

	@Override
	public Integer getWeight() {
		return 0;
	}

	@Override
	public boolean handleMsg(PackageInfo info, ISession session) {
		String from = info.getFrom();
		if(!session.authed() && !"auth".equals(info.getType())) {
			return true;
		}else if("auth".equals(info.getType())) {
			boolean auth = session.auth(info, session);
			PackageInfo authed = new PackageInfo(from, String.valueOf(auth), "server", info.getType(), info.getApp(),0);
			session.sendMsg(authed);
			return true;
		}
		
		return false;
	}

}
