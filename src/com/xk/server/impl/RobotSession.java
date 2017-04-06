package com.xk.server.impl;

import org.apache.mina.core.session.IoSession;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.ISession;

public class RobotSession implements ISession {

	@Override
	public void setIoSession(IoSession sess) {

	}

	@Override
	public String getSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean auth(PackageInfo info, ISession session) {
		return true;
	}

	@Override
	public boolean authed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendMsg(PackageInfo info) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
