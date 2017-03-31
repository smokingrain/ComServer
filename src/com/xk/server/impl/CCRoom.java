package com.xk.server.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.IRoom;
import com.xk.server.interfaces.ISession;
import com.xk.server.managers.SessionManager;
import com.xk.server.utils.JSONUtil;
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
	@JsonIgnore
	private boolean destroied = false;
	
	private List<String> members = new ArrayList<String>();
	
	@JsonIgnore
	private int version = 0;
	
	@JsonIgnore
	private List<PackageInfo> msgs = new ArrayList<PackageInfo>();
	/**
	 * 房间操作锁，任何房间更改必须保证同步
	 */
	@JsonIgnore
	private ReentrantReadWriteLock lock;
	
	public CCRoom(String creator, Integer type) {
		this.creator = creator;
		this.type = type;
		members.add(creator);
		id = StringUtil.createUID();
		lock = new ReentrantReadWriteLock();
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
	public List<String> members() {
		lock.readLock().lock();
		List<String> result = Collections.unmodifiableList(members);
		lock.readLock().unlock();
		return result;
	}

	@Override
	public List<PackageInfo> messages(Integer last) {
		lock.readLock().lock();
		List<PackageInfo> result = Collections.unmodifiableList(msgs);
		lock.readLock().unlock();
		return result;
	}

	@Override
	public boolean handleMsg(PackageInfo info, ISession session) {
		if("join".equals(info.getType())) {
			if(type == 1) {
				String from = info.getFrom();
				if(join(from)) {
					PackageInfo joined = new PackageInfo(from, JSONUtil.toJosn(this), getId(), info.getType(), info.getApp(), info.getVersion());
					keepVersion(joined);
					for(String client : members) {
						info.setTo(client);
						SessionManager.getSession(client).sendMsg(info);
					}
				}
			}
		} else if("exitRoom".equals(info.getType())) {
			String from = info.getFrom();
			boolean leave = leaveRoom(from);
			if(leave) {
				destroy();
			}
		}
		return false;
	}
	
	
	private boolean checkVersion(PackageInfo info) {
		lock.readLock().lock();
		boolean valied = info.getVersion() - version == 1;
		lock.readLock().unlock();
		return valied;
	}
	
	private PackageInfo keepVersion(PackageInfo info) {
		lock.writeLock().lock();
		info.setVersion(++version);
		this.msgs.add(info);
		lock.writeLock().unlock();
		return info;
	}
	
	/**
	 * 加入房间
	 * @param client
	 */
	private boolean join(String client) {
		lock.writeLock().lock();
		boolean joined = false;
		if(!destroied) {
			if(members.size() == 1) {
				members.add(client);
				joined = true;
			}
		}
		lock.writeLock().unlock();
		return joined;
	}
	
	/**
	 * 退出房间
	 * @param client
	 * @return 房间是不是空了。。
	 */
	private boolean leaveRoom(String client) {
		lock.writeLock().lock();
		if(!destroied) {
			if(creator.equals(client)) {//创建者退出。。。
				if(members.size() == 1) {
					creator = members.get(0);
				}
			}
		}
		int size = members.size();
		lock.writeLock().unlock();
		return size == 0;
	}
	

	@Override
	@JsonIgnore
	public Integer getWeight() {
		return 0;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public void destroy() {
		lock.writeLock().lock();
		creator = null;
		members.clear();
		msgs.clear();
		destroied = true;
		lock.writeLock().unlock();
	}
	

}
