package com.xk.server.interfaces;

import com.xk.server.beans.PackageInfo;

public interface IMessageHandler {

	/**
	 * ��ȡ���������б��е�Ȩֵ��ԽСԽ���Ƚ���
	 * @return
	 */
	public Integer getWeight();
	
	/**
	 * ������Ϣ
	 * @param info
	 * @return ����Ҫ�ٸ���һ����Ϣ������
	 */
	public boolean handleMsg(PackageInfo info, ISession session);
	
}
