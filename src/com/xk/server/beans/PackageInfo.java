package com.xk.server.beans;

import com.xk.server.utils.JSONUtil;

public class PackageInfo {
	private String to;
	private String msg;
	private String from;
	private String type;
	private String app;

	public PackageInfo() {
	}

	public PackageInfo(String to, String msg, String from, String type,
			String app) {
		this.to = to;
		this.msg = msg;
		this.from = from;
		this.type = type;
		setApp(app);
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApp() {
		return this.app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@Override
	public String toString() {
		return JSONUtil.toJosn(this);
	}
	
	
}