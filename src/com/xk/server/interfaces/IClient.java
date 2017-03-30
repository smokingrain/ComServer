package com.xk.server.interfaces;

import com.xk.server.beans.PackageInfo;

public interface IClient {

	public Long getId();
	
	public boolean sendMsg(PackageInfo info);
	
	public void close();
	
	public IRoom getRoom();
	
	public String toJSON();
	
}
