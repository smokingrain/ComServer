package com.xk.server.interfaces;

import java.util.List;
import java.util.Set;

import com.xk.server.beans.PackageInfo;

public interface IRoom extends IMessageHandler{

	public String getId();
	
	public String getName();
	
	public Set<IClient> getMembers();
	
	public List<PackageInfo> getMessages(Integer last);
	
}
