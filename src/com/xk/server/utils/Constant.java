package com.xk.server.utils;

import com.xk.server.beans.HConnection;
import com.xk.server.beans.Rooms;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.mina.core.session.IoSession;

public class Constant
{
  public static Map<String, IoSession> clients = new ConcurrentHashMap<String, IoSession>();
  public static Map<String, HConnection> hps = new ConcurrentHashMap<String, HConnection>();
  public static Map<String, Rooms> roompool = new ConcurrentHashMap<String, Rooms>();
  public static Map<String, List<Rooms>> typedroompool = new ConcurrentHashMap<String, List<Rooms>>();
  public static Map<String, Rooms> users = new ConcurrentHashMap<String, Rooms>();
}