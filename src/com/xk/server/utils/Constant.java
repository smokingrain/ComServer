package com.xk.server.utils;

import com.xk.server.beans.HConnection;
import com.xk.server.beans.Rooms;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.mina.core.session.IoSession;

public class Constant
{
  public static Map<Long, IoSession> clients = new ConcurrentHashMap<Long, IoSession>();
  public static Map<Long, HConnection> hps = new ConcurrentHashMap<Long, HConnection>();
  public static Map<String, Rooms> roompool = new ConcurrentHashMap<String, Rooms>();
  public static Map<String, List<Rooms>> typedroompool = new ConcurrentHashMap<String, List<Rooms>>();
  public static Map<Long, Rooms> users = new ConcurrentHashMap<Long, Rooms>();
}