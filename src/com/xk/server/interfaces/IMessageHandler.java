package com.xk.server.interfaces;

import com.xk.server.beans.PackageInfo;

public interface IMessageHandler {

	/**
	 * 获取在拦截器列表中的权值，越小越优先接收
	 * @return
	 */
	public Integer getWeight();
	
	/**
	 * 处理消息
	 * @param info
	 * @return 不需要再给下一个消息处理器
	 */
	public boolean handleMsg(PackageInfo info, ISession session);
	
}
