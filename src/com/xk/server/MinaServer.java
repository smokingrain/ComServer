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
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.xk.server.beans.PackageInfo;
import com.xk.server.impl.ClientSession;
import com.xk.server.impl.StanderedHandler;
import com.xk.server.interfaces.ISession;
import com.xk.server.utils.Constant;
import com.xk.server.utils.JSONUtil;

public class MinaServer {

	private IoAcceptor acceptor;
	
	private MinaServer(){
		
	}
	
	public void init(int port) throws IOException{
		if(null==acceptor){
			acceptor = new NioSocketAcceptor();
	        acceptor.getSessionConfig().setReadBufferSize(2048);
	        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	        acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
	        acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter());
	        acceptor.setHandler(new MessageHandler());
	        acceptor.bind(new InetSocketAddress(port)); 
	        acceptor.bind();
		}
	}
	
	public boolean writeInfo(String uid,String msg){
		IoSession session=Constant.clients.get(uid);
		if(null==session){
			return false;
		}
		WriteFuture future = session.write(msg);
		future.setWritten();
		return true;
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
			PackageInfo info = JSONUtil.toBean(message.toString(), PackageInfo.class);
			if(null == info) {
				System.out.println(session.getId() + " has a message lost : " + message);
				return;
			}
			StanderedHandler handler = (StanderedHandler) session.getAttribute("handler");
			handler.handleMsg(info, null);
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception {
			ISession sess = new ClientSession();
			sess.setIoSession(session);
			StanderedHandler handler = new StanderedHandler(sess);
			session.setAttribute("handler", handler);
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {
			
		}
		
	}
	
	private static class MinaFactory{
		public static final MinaServer INSTANCE=new MinaServer();
	}
}
