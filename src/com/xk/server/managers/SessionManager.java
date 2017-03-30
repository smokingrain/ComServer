package com.xk.server.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xk.server.interfaces.ISession;

public class SessionManager {
	
	
	private static Map<String, ISession> sessions = new ConcurrentHashMap<String, ISession>();
	
	public static ISession getSession(String name) {
		return sessions.get(name);
	}
	
	public static void createSession(String name, ISession session) {
		sessions.put(name, session);
	}
	
	public static void removeSession(String name) {
		sessions.remove(name);
	}
	
}
