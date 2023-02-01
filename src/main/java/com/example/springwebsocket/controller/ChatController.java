package com.example.springwebsocket.controller;

import com.example.springwebsocket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/1 13:50
 * @Description:
 */


@Controller
public class ChatController {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  /**
   * 点对点
   * @param message
   */
  @MessageMapping("/message")
  public void sendMessage(Message message){
    simpMessagingTemplate.convertAndSendToUser(message.getFromUser(),"/message/get/"+message.getToUser(),message.getMsg()+"$"+message.getType());
//    simpMessagingTemplate.convertAndSendToUser(message.getFromUser(),"/message/get",message.getMsg());
  }

  /**
   * 广播
   * @param message
   * @return
   */
  @MessageMapping("/welcome")
  @SendTo("/topic/welcome")
  public String sendAlert(String message){
    return message;
  }

}
