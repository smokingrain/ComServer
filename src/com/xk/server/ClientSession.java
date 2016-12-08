package com.xk.server;

import java.net.SocketAddress;
import java.util.Set;

import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.TransportMetadata;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.core.write.WriteRequestQueue;

public class ClientSession implements IoSession {

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IoService getService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IoHandler getHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IoSessionConfig getConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IoFilterChain getFilterChain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriteRequestQueue getWriteRequestQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransportMetadata getTransportMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReadFuture read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriteFuture write(Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriteFuture write(Object paramObject,
			SocketAddress paramSocketAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloseFuture close(boolean paramBoolean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloseFuture closeNow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloseFuture closeOnFlush() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CloseFuture close() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttachment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object setAttachment(Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(Object paramObject1, Object paramObject2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object setAttribute(Object paramObject1, Object paramObject2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object setAttribute(Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object setAttributeIfAbsent(Object paramObject1, Object paramObject2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object setAttributeIfAbsent(Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeAttribute(Object paramObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAttribute(Object paramObject1, Object paramObject2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean replaceAttribute(Object paramObject1, Object paramObject2,
			Object paramObject3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAttribute(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Object> getAttributeKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClosing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSecured() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CloseFuture getCloseFuture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SocketAddress getRemoteAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SocketAddress getLocalAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SocketAddress getServiceAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentWriteRequest(WriteRequest paramWriteRequest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspendRead() {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspendWrite() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resumeRead() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resumeWrite() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReadSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWriteSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateThroughput(long paramLong, boolean paramBoolean) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getReadBytes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getWrittenBytes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getReadMessages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getWrittenMessages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getReadBytesThroughput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWrittenBytesThroughput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getReadMessagesThroughput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWrittenMessagesThroughput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScheduledWriteMessages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getScheduledWriteBytes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getCurrentWriteMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WriteRequest getCurrentWriteRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCreationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastIoTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastReadTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastWriteTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isIdle(IdleStatus paramIdleStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReaderIdle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWriterIdle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBothIdle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getIdleCount(IdleStatus paramIdleStatus) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getReaderIdleCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWriterIdleCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBothIdleCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastIdleTime(IdleStatus paramIdleStatus) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastReaderIdleTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastWriterIdleTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastBothIdleTime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
