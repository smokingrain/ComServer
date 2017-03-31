package com.xk.server.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.IClient;
import com.xk.server.interfaces.IRoom;
import com.xk.server.interfaces.ISession;
import com.xk.server.utils.StringUtil;

/**
 * 
 * @author o-kui.xiao
 *
 */
public class CCRoom implements IRoom {
	
	/**
	 * 创建者/房主
	 */
	private String creator ;
	/**
	 * 类型，1，对战，2，人机
	 */
	private Integer type;
	private String name;
	private String id;
	private Set<String> members;
	private List<PackageInfo> msgs;
	
	public CCRoom(String creator, Integer type) {
		this.creator = creator;
		this.type = type;
		members = new CopyOnWriteArraySet<String>();
		members.add(creator);
		id = StringUtil.createUID();
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
	public Set<String> getMembers() {
		return Collections.unmodifiableSet(members);
	}

	@Override
	public List<PackageInfo> getMessages(Integer last) {
		return Collections.unmodifiableList(msgs);
	}

	@Override
	public boolean handleMsg(PackageInfo info, ISession session) {
		if("join".equals(info.getType())) {
			if(type == 1) {
				String from = info.getFrom();
			}
		}
		return false;
	}
	
	private void join() {
		
	}
	

	@Override
	public Integer getWeight() {
		return 0;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addMember(String client) {
		members.add(client);
	}
	

}
