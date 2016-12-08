package com.xk.server;

import com.xk.server.utils.StringUtil;

import java.io.IOException;

import org.apache.mina.core.session.IoSession;

public class ServerLauncher {

	private MinaServer sl;
	public static void main(String[] args) {
		new ServerLauncher().lunch();
	}

	public void lunch(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int port = 5492;
				sl = MinaServer.getInstance();
				MessageReciver mReciver = new MessageReciver() {

					@Override
					public boolean reciveMessage(String msg,
							IoSession session) {
						System.out.println("handle to-server msg");
						StringUtil.handleMessage(msg, sl,session);
						return false;
					}
				};
				sl.setmReciver(mReciver);
				try {
					sl.init(port);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("·þÎñÆô¶¯Ê§°Ü");
				}
			}
		}).start();
		
	}
	
	public void stop(){
		sl.stop();
	}
}