package com.xk.server.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xk.server.impl.XClient;
import com.xk.server.interfaces.IClient;
import com.xk.server.interfaces.ISession;

public class SessionManager {
	
	
	private static Map<String, ISession> sessions = new ConcurrentHashMap<String, ISession>();
	private static Map<String, IClient> clients = new ConcurrentHashMap<String, IClient>();
	
	
	public static IClient getClient(String id) {
		return clients.get(id);
	}
	
	public static ISession getSession(String id) {
		return sessions.get(id);
	}
	
	public static void createSession(String id, ISession session, IClient client) {
		sessions.put(id, session);
		clients.put(id, client);
	}
	
	public static void removeSession(String id) {
		sessions.remove(id);
		clients.remove(id);
	}
	
}
