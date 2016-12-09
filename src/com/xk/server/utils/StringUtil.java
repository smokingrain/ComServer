package com.xk.server.utils;

import com.xk.server.MinaServer;
import com.xk.server.ServerLauncher;
import com.xk.server.beans.Client;
import com.xk.server.beans.HConnection;
import com.xk.server.beans.PackageInfo;
import com.xk.server.beans.Rooms;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.mina.core.session.IoSession;

public class StringUtil {
	public static String createUID() {
		String uid = new UID().toString();
		StringTokenizer st = new StringTokenizer(uid, ":");
		uid = "";
		while (st.hasMoreElements()) {
			uid = uid + st.nextToken();
		}
		char[] ch = uid.toCharArray();
		char[] tmp = new char[ch.length];
		int ind = ch.length - 1;
		for (int i = 0; i < ch.length; ++i) {
			tmp[(ind--)] = ch[i];
		}
		uid = String.valueOf(tmp);
		return uid;
	}

	public static String formatDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static boolean isBlank(String str) {
		return ((str == null) || ("".equals(str)));
	}

	public static void handleMessage(PackageInfo info, MinaServer server, IoSession session) {
		if (info != null)
			if(-1L!=info.getTo()){
				if(!server.writeInfo(info.getTo(), JSONUtil.toJosn(info))){
					PackageInfo rst = new PackageInfo(info.getFrom(), "failed", -1L, "noclient", "server");
					server.writeInfo(info.getFrom(), JSONUtil.toJosn(rst));
				}
				return;
			}
			if("hps".equals(info.getType())){
				List<Map<String, String>> result = new ArrayList<Map<String,String>>();
				for(HConnection conn : Constant.hps.values()) {
					Map<String, String> props = new HashMap<String, String>();
					props.put("name", conn.getName());
					props.put("ip", conn.getIp());
					props.put("id", conn.getSession().getId() + "");
					result.add(props);
				}
				PackageInfo rst = new PackageInfo(info.getFrom(), JSONUtil.toJosn(result), -1L, info.getType(), "server");
				server.writeInfo(rst.getTo(), JSONUtil.toJosn(rst));
				return;
			} else if ("regin".equals(info.getType())) {
				if("hp".equals(info.getApp())) {
					String name = info.getMsg();
					String ip = session.getServiceAddress().toString();
					HConnection conn = new HConnection(session, name, ip);
					Constant.hps.put(info.getFrom(), conn);
				}
			} else if ("rooms".equals(info.getType())) {
				System.out.println("query rooms");
				String app = info.getApp();
				Long from = info.getFrom();
				PackageInfo sendTo = new PackageInfo();
				sendTo.setApp("server");
				sendTo.setFrom(-1L);
				sendTo.setTo(from);
				sendTo.setType(info.getType());
				List<Rooms> list =  Constant.typedroompool.get(app);
				if (list == null) {
					list = new ArrayList<Rooms>();
					Constant.typedroompool.put(app, list);
				}
				sendTo.setMsg(JSONUtil.toJosn(list));
				server.writeInfo(from, JSONUtil.toJosn(sendTo));
			} else if ("croom".equals(info.getType())) {
				System.out.println("create room");
				String app = info.getApp();
				Long from = info.getFrom();
				Rooms room = (Rooms) JSONUtil
						.toBean(info.getMsg(), Rooms.class);
				room.setId(createUID());
				room.setCreateTime(formatDate(new Date(), "yyyy-MM-dd HHmmss"));
				List<Rooms> list =Constant.typedroompool.get(app);
				if (list == null) {
					list = new ArrayList<Rooms>();
					Constant.typedroompool.put(app, list);
				}
				list.add(room);
				Constant.roompool.put(room.getId(), room);
				Constant.users.put(from, room);
				PackageInfo sendTo = new PackageInfo();
				sendTo.setApp("server");
				sendTo.setFrom(-1L);
				sendTo.setTo(from);
				sendTo.setType(info.getType());
				sendTo.setMsg(room.getId());
				System.out.println("croom:"+room.getId());
				server.writeInfo(from, JSONUtil.toJosn(sendTo));
			} else if ("join".equals(info.getType())) {
				Client c = (Client) JSONUtil
						.toBean(info.getMsg(), Client.class);
				Rooms room = (Rooms) Constant.roompool.get(c.getRoomid());
				if ((room != null) && (room.getClient() == null)) {
					Client creator = room.getCreater();
					room.setClient(c);
					PackageInfo sendTo = new PackageInfo();
					sendTo.setApp("server");
					sendTo.setFrom(-1L);
					sendTo.setTo(creator.getCid());
					sendTo.setType(info.getType());
					sendTo.setMsg(info.getMsg());
					server.writeInfo(creator.getCid(), JSONUtil.toJosn(sendTo));
					sendTo.setTo(c.getCid());
					sendTo.setType("joinResult");
					sendTo.setMsg(JSONUtil.toJosn(room));
					server.writeInfo(c.getCid(), JSONUtil.toJosn(sendTo));
					Constant.users.put(c.getCid(), room);
					return;
				}
				PackageInfo sendTo = new PackageInfo();
				sendTo.setApp("server");
				sendTo.setFrom(-1L);
				sendTo.setTo(c.getCid());
				sendTo.setType("joinResult");
				sendTo.setMsg("fail");
				server.writeInfo(c.getCid(), JSONUtil.toJosn(sendTo));
			} else if ("exitRoom".equals(info.getType())) {
				String message = info.getMsg();
				String app = info.getApp();
				Client cl = JSONUtil.toBean(message, Client.class);
				Rooms rooms = Constant.roompool.get(cl.getRoomid());
				if (rooms != null) {
					PackageInfo pi = new PackageInfo();
					if (rooms.getCreater().getCid()==(cl.getCid())) {
						if (rooms.getClient() != null) {
							pi.setApp("server");
							pi.setTo(rooms.getClient().getCid());
							pi.setType("exitRoom");
							pi.setMsg(cl.getCid()+"");
							pi.setFrom(-1L);
							server.writeInfo(rooms.getClient().getCid(),
									JSONUtil.toJosn(pi));
							rooms.setCreater(rooms.getClient());
							rooms.setClient(null);
						} else {
							Constant.roompool.remove(cl.getRoomid());
							if(null==app||"".equals(app.trim())){
								for(List<Rooms>rms:Constant.typedroompool.values()){
									rms.remove(rooms);
								}
							}else{
								List<Rooms> rms = Constant.typedroompool.get(app);
								if (rms != null)
									rms.remove(rooms);
							}
							
						}
					} else if (rooms.getClient().getCid()==(cl.getCid())) {
						pi.setTo(rooms.getCreater().getCid());
						pi.setType("exitRoom");
						pi.setMsg(cl.getCid()+"");
						pi.setFrom(-1L);
						server.writeInfo(rooms.getCreater().getCid(),
								JSONUtil.toJosn(pi));
						rooms.setClient(null);
					}
				}
			}
	}

	public static void handleMessage(String msg, MinaServer server, IoSession session) {
		PackageInfo info = (PackageInfo) JSONUtil
				.toBean(msg, PackageInfo.class);
		info.setFrom(session.getId());
		handleMessage(info, server, session);
	}
}