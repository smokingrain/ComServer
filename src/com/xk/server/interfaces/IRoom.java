package com.xk.server.interfaces;

import java.util.List;
import java.util.Set;

import com.xk.server.beans.PackageInfo;

public interface IRoom extends IMessageHandler{

	public String getId();
	
	public String getName();
	
	public List<String> members();
	
	public List<PackageInfo> messages(Integer last);
	
	public void destroy();
	
}
