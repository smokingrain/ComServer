package com.xk.server.beans;

public class Rooms
{
  private Client creater;
  private String name;
  private String id;
  private Client client;
  private String createTime;

  public Client getCreater()
  {
    return this.creater;
  }

  public void setCreater(Client creater) {
    this.creater = creater;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Client getClient() {
    return this.client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public String getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}