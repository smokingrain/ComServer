package com.xk.server.interfaces;

import com.xk.server.beans.PackageInfo;

public interface IClient {

	public String getId();
	
	public String getName();
	
	public boolean sendMsg(PackageInfo info);
	
	public void close();
	
	public String getRoom();
	
	public void setRoom(String room);
	
	public String toJSON();
	
}
