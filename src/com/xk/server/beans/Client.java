package com.xk.server.beans;

public class Client {
	private Long cid = -2L;// ���id
	private String cname = "asdfsd";// �����
	private String roomid = "sdf";// ���ڷ���id
	private Integer computer = 2;// ���Զ�ս����
	private Integer duizhan = 2;// ��Ҷ�ս����
	private Integer cwin = 1;// ���Զ�սӮ�˾���
	private Integer dwin = 1;// ��Ҷ�սӮ�˾���

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