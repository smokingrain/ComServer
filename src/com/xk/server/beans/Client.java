package com.xk.server.beans;

public class Client {
	private Long cid = -2L;// 玩家id
	private String cname = "asdfsd";// 玩家名
	private String roomid = "sdf";// 所在房间id
	private Integer computer = 2;// 电脑对战局数
	private Integer duizhan = 2;// 玩家对战局数
	private Integer cwin = 1;// 电脑对战赢了局数
	private Integer dwin = 1;// 玩家对战赢了局数

	public Long getCid() {
		return this.cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getRoomid() {
		return this.roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public Integer getComputer() {
		return this.computer;
	}

	public void setComputer(Integer computer) {
		this.computer = computer;
	}

	public Integer getDuizhan() {
		return this.duizhan;
	}

	public void setDuizhan(Integer duizhan) {
		this.duizhan = duizhan;
	}

	public Integer getCwin() {
		return this.cwin;
	}

	public void setCwin(Integer cwin) {
		this.cwin = cwin;
	}

	public Integer getDwin() {
		return this.dwin;
	}

	public void setDwin(Integer dwin) {
		this.dwin = dwin;
	}
}