package com.xk.server.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xk.server.beans.PackageInfo;
import com.xk.server.cclogic.Position;
import com.xk.server.cclogic.Search;
import com.xk.server.interfaces.IClient;
import com.xk.server.interfaces.IRoom;
import com.xk.server.interfaces.ISession;
import com.xk.server.managers.RoomManager;
import com.xk.server.managers.SessionManager;
import com.xk.server.utils.JSONUtil;
import com.xk.server.utils.StringUtil;

/**
 * 
 * @author o-kui.xiao
 *
 */
public class CCRoom implements IRoom {
	
	/**
	 * 创建者/房主
	 */
	private String creator ;
	/**
	 * 类型，1，对战，2，人机
	 */
	private Integer type;
	private String name;
	private String id;
	private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	private List<String> members = new ArrayList<String>();
	
	@JsonIgnore
	private boolean destroied = false;
	
	@JsonIgnore
	private int version = 0;
	
	@JsonIgnore
	private Position pos = new Position();
	
	@JsonIgnore
	private Search search = new Search(pos, 12);
	
	@JsonIgnore
	private boolean p1Ready = false;
	
	@JsonIgnore
	private boolean p2Ready = false;
	
	@JsonIgnore
	private int turn = 1;//1 房主下棋，-1客人下棋
	
	@JsonIgnore
	private int level = 0;
	
	@JsonIgnore
	private List<PackageInfo> msgs = new ArrayList<PackageInfo>();
	/**
	 * 房间操作锁，任何房间更改必须保证同步
	 */
	@JsonIgnore
	private ReentrantReadWriteLock lock;
	
	public CCRoom(String creator, Integer type) {
		this.creator = creator;
		this.type = type;
		p2Ready = type == 2;//人机自动设置p2是准备的
		members.add(creator);
		id = StringUtil.createUID();
		lock = new ReentrantReadWriteLock();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> members() {
		lock.readLock().lock();
		List<String> result = Collections.unmodifiableList(members);
		lock.readLock().unlock();
		return result;
	}

	@Override
	public List<PackageInfo> messages(Integer last) {
		lock.readLock().lock();
		List<PackageInfo> result = Collections.unmodifiableList(msgs);
		lock.readLock().unlock();
		return result;
	}

	@Override
	public boolean handleMsg(PackageInfo info, ISession session) {
		if("join".equals(info.getType())) {
			if(type == 1) {
				join(info);
			}
		} else if("exitRoom".equals(info.getType())) {
			String from = info.getFrom();
			boolean leave = leaveRoom(from);
			if(leave) {
				destroy();
			}
		} else if("action".equals(info.getType())) {
			doAction(info);
		}
		return false;
	}
	
	/**
	 * 识别指令
	 * @param info
	 * @return
	 */
	private boolean doAction(PackageInfo info) {
		String msg = info.getMsg();
		Map<String, Object> cmdInfo = JSONUtil.fromJson(msg);
		String cmd = (String) cmdInfo.get("cmd");
		if(StringUtil.isBlank(cmd)) {
			return false;
		}
		boolean result = false;
		if("move".equals(cmd)) {
			result = move(cmdInfo, info);
		} else if("undo".equals(cmd)) {
			result = undo(cmdInfo, info);
		} else if("givein".equals(cmd)) {
			result = givein(cmdInfo, info);
		} else if("ready".equals(cmd)) {
			result = ready(cmdInfo, info);
		}
		return result;
	}
	
	/**
	 * 准备
	 * @param cmdInfo
	 * @param from
	 * @return 是否双方都准备完毕
	 */
	private boolean ready(Map<String, Object> cmdInfo,PackageInfo info) {
		lock.writeLock().lock();
		boolean result;
		try {
			if(destroied) {
				lock.writeLock().unlock();
				return false;
			}
			if((p1Ready && p2Ready) || !checkVersion(info)) {//都准备好了或者消息版本不对
				lock.writeLock().unlock();
				return false;
			}
			if(creator.equals(info.getFrom())) {//创建者准备
				p1Ready = true;
				if(type == 2) {//机器人自动加入房间，并准备
					if(members.size() == 1) {
						members.add(id);
						Robot robot = new Robot();
						RobotSession se = new RobotSession();
						SessionManager.createSession(id, se, robot);
						List<IClient> members = new ArrayList<IClient>();
						for(String client: this.members) {
							IClient x = SessionManager.getClient(client);
							x.setRoom(id);
							members.add(x);
						}
						Map<String, Object> obj = new HashMap<String, Object>();
						obj.put("id", id);
						obj.put("name", name);
						obj.put("creator", SessionManager.getClient(creator));
						obj.put("members", members);
						PackageInfo joined = new PackageInfo(id, JSONUtil.toJosn(obj), id, "join", info.getApp(), info.getVersion());
						joined.setVersion(++version);
						this.msgs.add(joined);
						for(String client : this.members) {
							info.setTo(client);
							SessionManager.getSession(client).sendMsg(joined);
						}
					}
					p2Ready = true;
				}
			} else {
				for(String client : members) {//加入者准备
					if(client.equals(info.getFrom())) {
						p2Ready = true;
						break;
					}
				}
			}
			result = false;
			if(p1Ready && p2Ready) {//通知客户端都准备完毕
				pos.fromFen(Position.STARTUP_FEN[0]);
				pos.changeSide();
				cmdInfo.put("fen", pos.toFen());
				result = true;
			}
			info.setMsg(JSONUtil.toJosn(cmdInfo));
			info.setVersion(++version);
			this.msgs.add(info);
			for(String member : members) {
				info.setTo(member);
				SessionManager.getSession(member).sendMsg(info);
			}
		} finally {
			lock.writeLock().unlock();
		}
		
		return result;
	}
	
	/**
	 * 下棋
	 * @param cmdInfo
	 * @param from
	 * @return
	 */
	private boolean move(Map<String, Object> cmdInfo, PackageInfo info) {
		lock.writeLock().lock();
		boolean result;
		try {
			if(destroied) {
				lock.writeLock().unlock();
				return false;
			}
			String from = info.getFrom();
			boolean turnRight = false;
			if(creator.equals(from)) {
				turnRight = turn == 1;
			} else {
				turnRight = turn != 1;
			}
			if(!turnRight || !checkVersion(info)) {
				lock.writeLock().unlock();
				return false;
			}
			result = false;
			int src = (int) cmdInfo.get("src");
			int dest = (int) cmdInfo.get("dest");
			int go = go(src, dest, cmdInfo);
			if(go >= 0) {
				info.setMsg(JSONUtil.toJosn(cmdInfo));
				info.setVersion(++version);
				this.msgs.add(info);
				for(String client : members) {
					info.setTo(client);
					SessionManager.getSession(client).sendMsg(info);
				}
				if(type == 2) {
					System.out.println("电脑下棋");
					long cur = System.currentTimeMillis();
					int mvLast = search.searchMain(1000 << (level << 1));
					System.out.println("cost time "+((System.currentTimeMillis()-cur)));
					int srcx = Position.FILE_X(Position.SRC(mvLast))-Position.FILE_LEFT;
					int srcy = Position.RANK_Y(Position.SRC(mvLast))-Position.RANK_TOP;
					int dstx = Position.FILE_X(Position.DST(mvLast))-Position.FILE_LEFT;
					int dsty = Position.RANK_Y(Position.DST(mvLast))-Position.RANK_TOP;
					int sqSrc=Position.COORD_XY(srcx + Position.FILE_LEFT, srcy + Position.RANK_TOP);
					int sqDst = Position.COORD_XY(dstx + Position.FILE_LEFT, dsty + Position.RANK_TOP);
//				int csrc = srcx*10+srcy;
//				int cdest = dstx*10+dsty;
					Map<String, Object> cinfo = new HashMap<String, Object>();
					cinfo.put("src", sqSrc);
					cinfo.put("dest", sqDst);
					cinfo.put("cmd", cmdInfo.get("cmd"));
					int cgo = go(sqSrc, sqDst, cinfo);
					if(cgo >= 0) {
						PackageInfo cmove = new PackageInfo(from, JSONUtil.toJosn(cinfo), name, info.getType(), info.getApp(), ++version);
						this.msgs.add(cmove);
						for(String client : members) {
							cmove.setTo(client);
							SessionManager.getSession(client).sendMsg(cmove);
						}
					}
				}
			}
		}finally {
			lock.writeLock().unlock();
		}
		
		return result;
	}
	
	private int go(int src, int dest,Map<String, Object> cmdInfo) {
		int result = -1;
		int mv=Position.MOVE(src, dest);
		if (pos.legalMove(mv)) {
			if (pos.makeMove(mv)) {
				int srcx = Position.FILE_X(Position.SRC(mv))-Position.FILE_LEFT;
				int srcy = Position.RANK_Y(Position.SRC(mv))-Position.RANK_TOP;
				int dstx = Position.FILE_X(Position.DST(mv))-Position.FILE_LEFT;
				int dsty = Position.RANK_Y(Position.DST(mv))-Position.RANK_TOP;
				int srcS = srcx * 10 + srcy;
				int destS = dstx * 10 + dsty;
				cmdInfo.put("src", srcS);
				cmdInfo.put("dest", destS);
				result = 0;
				cmdInfo.put("fen", pos.toFen());
				if (pos.captured()) {//吃棋子
					pos.setIrrev();
					cmdInfo.put("cap", "cap");
				}
				if(pos.inCheck()) {//将军
					cmdInfo.put("chk", "chk");
				}
				int vlRep = pos.repStatus(3);
				if( vlRep> 0) {//赖皮棋
					vlRep = (1 == turn ? pos.repValue(vlRep) : -pos.repValue(vlRep));
					int rst = (vlRep > Position.WIN_VALUE ? -1 :
						vlRep < -Position.WIN_VALUE ? 1 : 0);
					cmdInfo.put("rep", rst);
					result = 1;
					p1Ready = false;
					p2Ready = false;
				}
				if(pos.mvList.size() > 100) {//平局
					cmdInfo.put("peace", "peace");
					p1Ready = false;
					p2Ready = false;
				}
				if(pos.isMate()) {//将死
					cmdInfo.put("mat", "mat");
					p1Ready = false;
					p2Ready = false;
				}
				turn *= -1;
			}
		}
		return result;
	}
	
	/**
	 * 悔棋
	 * @param info
	 * @return
	 */
	private boolean undo(Map<String, Object> cmdInfo, PackageInfo info) {
		lock.writeLock().lock();
		boolean result;
		try {
			if(destroied) {
				lock.writeLock().unlock();
				return false;
			}
			
			result = false;
		} finally {
			lock.writeLock().unlock();
		}
		
		return result;
	}
	
	/**
	 * 认输
	 * @return
	 */
	private boolean givein(Map<String, Object> cmdInfo, PackageInfo info) {
		lock.writeLock().lock();
		try {
			if(destroied || !checkVersion(info)) {
				return false;
			}
			info.setVersion(++version);
			msgs.add(info);
			for(String client : members) {
				info.setTo(client);
				SessionManager.getSession(client).sendMsg(info);
			}
			p1Ready = false;
			p2Ready = false;
		}finally{
			lock.writeLock().unlock();
		}
		
		return true;
	}
	
	/**
	 * 检查消息版本
	 * @param info
	 * @return
	 */
	private boolean checkVersion(PackageInfo info) {
		boolean valied = info.getVersion() - version == 1;
		return valied;
	}
	
	/**
	 * 保持递增的版本号
	 * @param info
	 * @return
	 */
//	private PackageInfo keepVersion(PackageInfo info) {
//		lock.writeLock().lock();
//		info.setVersion(++version);
//		this.msgs.add(info);
//		lock.writeLock().unlock();
//		return info;
//	}
	
	/**
	 * 加入房间
	 * @param client
	 */
	private boolean join(PackageInfo info) {
		lock.writeLock().lock();
		try {
			if(!destroied) {
				String from = info.getFrom();
				SessionManager.getClient(from).setRoom(getId());
				if(members.size() == 1) {
					members.add(from);
					List<IClient> members = new ArrayList<IClient>();
					for(String client: this.members) {
						IClient x = SessionManager.getClient(client);
						x.setRoom(id);
						members.add(x);
					}
					Map<String, Object> obj = new HashMap<String, Object>();
					obj.put("id", id);
					obj.put("name", name);
					obj.put("creator", SessionManager.getClient(creator));
					obj.put("members", members);
					PackageInfo joined = new PackageInfo(from, JSONUtil.toJosn(obj), from, info.getType(), info.getApp(), info.getVersion());
					joined.setVersion(++version);
					this.msgs.add(joined);
					for(String client : this.members) {
						info.setTo(client);
						SessionManager.getSession(client).sendMsg(joined);
					}
				}else {
					PackageInfo joined = new PackageInfo(from, null, getId(), info.getType(), info.getApp(), info.getVersion());
					SessionManager.getSession(from).sendMsg(joined);
				}
			}
		} finally {
			lock.writeLock().unlock();
		}
		
		return true;
	}
	
	/**
	 * 退出房间
	 * @param client
	 * @return 房间是不是空了。。
	 */
	private boolean leaveRoom(String client) {
		lock.writeLock().lock();
		int size = -1;
		try {
			if(!destroied) {
				if(members.contains(client)) {
					members.remove(client);
				}
				if(creator.equals(client)) {//创建者退出。。。
					if(members.size() == 1) {
						creator = members.get(0);
					}
				}
			}
			size = members.size();
		} catch (Exception e) {
			lock.writeLock().unlock();
		}
		
		return size == 0 || type == 2;
	}
	

	@Override
	@JsonIgnore
	public Integer getWeight() {
		return 0;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public void destroy() {
		lock.writeLock().lock();
		try {
			creator = null;
			members.clear();
			msgs.clear();
			SessionManager.removeSession(id);
			RoomManager.destoryRoom(this, "cc");
			destroied = true;
		} catch (Exception e) {
			lock.writeLock().unlock();
		}
		
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}
	

}
