package com.xk.server.impl;

import java.util.List;
import java.util.Set;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.IClient;
import com.xk.server.interfaces.IRoom;
import com.xk.server.interfaces.ISession;

public class CCRoom implements IRoom {

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IClient> getMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PackageInfo> getMessages(Integer last) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handleMsg(PackageInfo info, ISession session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
