package com.xk.server.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.xk.server.beans.PackageInfo;
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
	 * ������/����
	 */
	private String creator ;
	/**
	 * ���ͣ�1����ս��2���˻�
	 */
	private Integer type;
	private String name;
	private String id;
	private List<String> members = new ArrayList<String>();
	private List<PackageInfo> msgs = new ArrayList<PackageInfo>();
	private boolean destroied = false;
	
	/**
	 * ������������κη�����ı��뱣֤ͬ��
	 */
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
	public List<String> getMembers() {
		return Collections.unmodifiableList(members);
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
				join(from);
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
	
	private void join(String client) {
		lock.writeLock().lock();
		if(!destroied) {
			members.add(client);
		}
		lock.writeLock().unlock();
	}
	
	/**
	 * �˳�����
	 * @param client
	 * @return �����ǲ��ǿ��ˡ���
	 */
	private boolean leaveRoom(String client) {
		lock.writeLock().lock();
		if(!destroied) {
			if(creator.equals(client)) {//�������˳�������
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
