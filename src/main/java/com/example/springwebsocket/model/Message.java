package com.example.springwebsocket.model;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/1 13:52
 * @Description:
 */


public class Message {
  private String fromUser;
  private String  toUser;
  private String msg;
  private String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getFromUser() {
    return fromUser;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public String getToUser() {
    return toUser;
  }

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
