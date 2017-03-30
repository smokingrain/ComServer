package com.xk.server.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.xk.server.beans.PackageInfo;
import com.xk.server.interfaces.IMessageHandler;
import com.xk.server.interfaces.ISession;

public class InterceptorManager {
	private static List<IMessageHandler> inteceptors = new ArrayList<IMessageHandler>();
	
	public static void addInteceptor(IMessageHandler inteceptor) {
		inteceptors.add(inteceptor);
		Collections.sort(inteceptors, new Comparator<IMessageHandler>() {

			@Override
			public int compare(IMessageHandler o1, IMessageHandler o2) {
				return o1.getWeight().compareTo(o2.getWeight());
			}
		});
	}
	
	public static boolean intercept(PackageInfo info, ISession session) {
		for(IMessageHandler interceptor : inteceptors) {
			if(interceptor.handleMsg(info, session)) {
				System.out.println(interceptor.getClass() + " stop message : " + info);
				return true;
			}
		}
		return false;
	}
	

}
