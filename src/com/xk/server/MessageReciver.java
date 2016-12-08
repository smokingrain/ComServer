package com.xk.server;

import org.apache.mina.core.session.IoSession;

public interface MessageReciver {
	public static final String SERVER = "server";
	public static final String ALL_CLIENT = "all_client";

	public boolean reciveMessage(String paramString,IoSession session);
}