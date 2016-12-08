package com.xk.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.xk.server.beans.Client;
import com.xk.server.beans.PackageInfo;
import com.xk.server.beans.Rooms;
import com.xk.server.utils.Constant;
import com.xk.server.utils.JSONUtil;
import com.xk.server.utils.StringUtil;

public class MinaServer {

	private MessageReciver mr;
	private IoAcceptor acceptor;
	
	private MinaServer(){
		
	}
	
	public void init(int port) throws IOException{
		if(null==acceptor){
			acceptor = new NioSocketAcceptor();
	        acceptor.getSessionConfig().setReadBufferSize(2048);
	        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	        acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
	        acceptor.setHandler(new MessageHandler());
	        acceptor.bind(new InetSocketAddress(port)); 
	        acceptor.bind();
		}
	}
	
	public void setmReciver(MessageReciver mr){
		this.mr=mr;
	}
	
	public boolean writeInfo(Long uid,String msg){
		IoSession session=Constant.clients.get(uid);
		if(null==session){
			return false;
		}
		WriteFuture future=session.write(msg);
		return future.isWritten();
	}
	
	public void stop(){
		if(null!=acceptor){
			acceptor.dispose();
		}
		acceptor=null;
	}
	
	public static MinaServer getInstance(){
		return MinaFactory.INSTANCE;
	}
	
	private class MessageHandler extends IoHandlerAdapter  {

		@Override
		public void exceptionCaught(IoSession session, Throwable cause)
				throws Exception {
			session.closeNow();
		}

		@Override
		public void messageSent(IoSession session, Object message)
				throws Exception {
			System.out.println(message.toString()+" | sent!");
		}

		@Override
		public void messageReceived(IoSession session, Object message)
				throws Exception {
			System.out.println(message);
			MinaServer.this.mr.reciveMessage(message.toString(),session);
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception {
			Constant.clients.put(session.getId(), session);
			PackageInfo pi=new PackageInfo(session.getId(), session.getId()+"", -1L, "LOGIN", "gb");
			session.write(JSONUtil.toJosn(pi));
			super.sessionOpened(session);
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {
			Constant.clients.remove(session.getId());
			Rooms room =  Constant.users.remove(session.getId());
			if(null!=room){
				PackageInfo pkg = new PackageInfo();
				pkg.setType("exitRoom");
				pkg.setTo(-1L);
				pkg.setFrom(-1L);
				Client c = new Client();
				c.setCid(session.getId());
				c.setRoomid(room.getId());
				pkg.setMsg(JSONUtil.toJosn(c));
				StringUtil.handleMessage(pkg, MinaServer.this);
			}

			super.sessionClosed(session);
		}
		
	}
	
	private static class MinaFactory{
		public static final MinaServer INSTANCE=new MinaServer();
	}
}
