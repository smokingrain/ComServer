package com.xk.server.impl;

import java.util.Arrays;
import java.util.List;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.IMessageHandler;
import com.xk.server.interfaces.IRoom;
import com.xk.server.interfaces.ISession;
import com.xk.server.managers.RoomManager;
import com.xk.server.managers.SessionManager;
import com.xk.server.utils.JSONUtil;

public class RoomInterceptor implements IMessageHandler {
	
	private static final List<String> ALLOWED_MSG = Arrays.asList(new String[]{"croom", "rooms", "join", "exitRoom", "action"});

	@Override
	public Integer getWeight() {
		return 10;
	}

	@Override
	public boolean handleMsg(PackageInfo info, ISession session) {
		if(!ALLOWED_MSG.contains(info.getType())) {
			return false;
		}
		String type = info.getType();
		if("croom".equals(type)) {
			IRoom room = RoomManager.createRoom(info);
			if(null != room) {
				SessionManager.getClient(session.getSessionId()).setRoom(room.getId());
				String id = room.getId();
				PackageInfo created = new PackageInfo(info.getFrom(), id, "server", type, info.getApp(), 0);
				session.sendMsg(created);
			}else {
				PackageInfo created = new PackageInfo(info.getFrom(), null, "server", type, info.getApp(), 0);
				session.sendMsg(created);
			}
			return true;
		}else if("rooms".equals(type)) {
			List<IRoom> rooms = RoomManager.getRooms(info.getApp());
			String msg = JSONUtil.toJosn(rooms);
			PackageInfo created = new PackageInfo(info.getFrom(), msg, "server", type, info.getApp(), 0);
			session.sendMsg(created);
			return true;
		}else {
			String to = info.getTo();
			IRoom room = RoomManager.getRoom(to);
			if(null != room) {
				room.handleMsg(info, session);
			}
			return true;
		}
	}

}
