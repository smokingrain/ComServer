package com.xk.server.impl;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.IClient;
import com.xk.server.interfaces.IRoom;
import com.xk.server.utils.JSONUtil;

public class XClient implements IClient {

	private String id;
	private String name;
	private IRoom room;
	
	public XClient(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean sendMsg(PackageInfo info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {

	}

	@Override
	public IRoom getRoom() {
		return room;
	}
	
	@Override
	public void setRoom(IRoom room) {
		this.room = room;
	}
	

	@Override
	public String toJSON() {
		return JSONUtil.toJosn(this);
	}

	@Override
	public String getName() {
		return name;
	}

}
