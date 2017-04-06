package com.xk.server.impl;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.IClient;
import com.xk.server.utils.JSONUtil;
import com.xk.server.utils.StringUtil;

public class Robot implements IClient {

	private String id;
	private String name;
	private String room;
	
	public Robot() {
		id = StringUtil.createUID();
		name = "Robot";
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean sendMsg(PackageInfo info) {
		//do nothing
		return true;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getRoom() {
		return room;
	}

	@Override
	public void setRoom(String room) {
		this.room = room;

	}

	@Override
	public String toJSON() {
		return JSONUtil.toJosn(this);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
