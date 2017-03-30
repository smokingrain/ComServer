package com.xk.server.impl;

import org.apache.mina.core.session.IoSession;

import com.xk.server.interfaces.ISession;

public class ClientSession implements ISession {

	private IoSession sess;
	
	@Override
	public void setIoSession(IoSession sess) {
		this.sess = sess;

	}

}
