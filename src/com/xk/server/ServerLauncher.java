package com.xk.server;

import java.io.IOException;

import com.xk.server.impl.AuthInterceptor;
import com.xk.server.impl.RoomInterceptor;
import com.xk.server.managers.InterceptorManager;

public class ServerLauncher {

	private MinaServer sl;
	public static void main(String[] args) {
		new ServerLauncher().lunch();
	}

	public void lunch(){
		int port = 5666;
		sl = MinaServer.getInstance();
		try {
			sl.init(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("��������ʧ��");
		}
		InterceptorManager.addInteceptor(new AuthInterceptor());
		InterceptorManager.addInteceptor(new RoomInterceptor());
		System.out.println("finish");
		
	}
	
	public void stop(){
		sl.stop();
	}
}