package com.xk.server;

import java.io.IOException;

public class ServerLauncher {

	private MinaServer sl;
	public static void main(String[] args) {
		new ServerLauncher().lunch();
	}

	public void lunch(){
		int port = 5492;
		sl = MinaServer.getInstance();
		try {
			sl.init(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("·þÎñÆô¶¯Ê§°Ü");
		}
		
	}
	
	public void stop(){
		sl.stop();
	}
}