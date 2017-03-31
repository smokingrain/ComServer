package com.xk.server.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xk.server.beans.PackageInfo;
import com.xk.server.impl.CCRoom;
import com.xk.server.interfaces.IRoom;
import com.xk.server.utils.JSONUtil;
import com.xk.server.utils.StringUtil;

public class RoomManager {
	
	private static final Map<String, IRoom> allRooms = new ConcurrentHashMap<String, IRoom>();
	private static final Map<String, List<IRoom>> typedRooms = new ConcurrentHashMap<String, List<IRoom>>();
	
	public static IRoom getRoom(String name) {
		return allRooms.get(name);
	}
	
	public static List<IRoom> getRooms(String app) {
		if(StringUtil.isBlank(app)) {
			return Collections.emptyList();
		}
		List<IRoom> rooms = typedRooms.get(app);
		if(null == rooms) {
			rooms = new ArrayList<IRoom>();
			typedRooms.put(app, rooms);
		}
		return rooms;
	}
	
	public static IRoom createRoom(PackageInfo info) {
		String app = info.getApp();
		String from = info.getFrom();
		if(StringUtil.isBlank(app) || StringUtil.isBlank(from)) {
			return null;
		}
		if("cc".equals(app)) {
			return createCCRoom(info);
		}
		return null;
	}
	
	private static CCRoom createCCRoom(PackageInfo info) {
		String msg = info.getMsg();
		Map<String, Object> roomInfo = JSONUtil.fromJson(msg);
		String name = (String) roomInfo.get("name");
		Integer type = (Integer) roomInfo.get("type");
		CCRoom room = new CCRoom(info.getFrom(), type);
		room.setName(name);
		allRooms.put(name, room);
		List<IRoom> rooms = typedRooms.get(info.getApp());
		if(null == rooms) {
			rooms = new ArrayList<IRoom>();
			typedRooms.put(info.getApp(), rooms);
		}
		rooms.add(room);
		return room;
	}
	
}
