package com.xk.server.beans;

public class PackageInfo {
	private Long to;
	private String msg;
	private Long from;
	private String type;
	private String app;

	public PackageInfo() {
	}

	public PackageInfo(Long to, String msg, Long from, String type,
			String app) {
		this.to = to;
		this.msg = msg;
		this.from = from;
		this.type = type;
		setApp(app);
	}

	public Long getTo() {
		return this.to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getFrom() {
		return this.from;
	}

	public void setFrom(Long from) {
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
}