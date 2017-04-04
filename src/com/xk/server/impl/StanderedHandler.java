package com.xk.server.impl;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.IMessageHandler;
import com.xk.server.interfaces.ISession;
import com.xk.server.managers.InterceptorManager;

public class StanderedHandler implements IMessageHandler {

	private ISession session;
	
	public StanderedHandler(ISession session) {
		this.session = session;
	}
	
	@Override
	public boolean handleMsg(PackageInfo info, ISession session) {
		info.setFrom(this.session.getSessionId());
		if(InterceptorManager.intercept(info, this.session)) {
			return true;
		}
		return false;
	}

	@Override
	public Integer getWeight() {
		return 0;
	}

}
