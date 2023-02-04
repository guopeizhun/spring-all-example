package com.example.springamqp.comsumer;

import com.example.springamqp.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/4 11:22
 * @Description:
 */

@Component
public class Comsumer {

//
//    @RabbitListener(queues = RabbitmqConfig.SIMPLE_QUEUE)
//    public void simpleComsumer(Map<String, Object> map) {
//        map.forEach((s, o) -> {
//            System.out.printf("key为%s,值为%s\n", s, o);
//        });
//    }

    @RabbitListener(queues = {RabbitmqConfig.FANOUT_QUEUE1})
    public void fanoutComsumer(Map<String, Object> map) {
        System.out.printf("comsumer1接收到%s\n", map);
    }

    @RabbitListener(queues = {RabbitmqConfig.FANOUT_QUEUE1})
    public void fanoutComsumer2(Map<String, Object> map) {
        System.out.printf("comsumer2接收到%s\n", map);
    }

    @RabbitListener(queues = RabbitmqConfig.SIMPLE_QUEUE)
    @SneakyThrows
    public void comsumerAck(Map<String, Object> map, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws InterruptedException {
        System.out.println("接收到值"+map);

           try{
               //模拟执行业务
               Thread.sleep(1000);
               //模拟发生异常
               int i = 1 / 0;
               channel.basicAck(deliveryTag,true);
           }catch (Exception e){
                   e.printStackTrace();
                   channel.basicNack(deliveryTag,true,true);
           }
    }

    @RabbitListener(queues = {RabbitmqConfig.TOPIC_QUEUE1})
    public void topicComsumer1(Map<String, Object> map) {
        System.out.printf("topicComsumer1%s\n", map);
    }
    @RabbitListener(queues = {RabbitmqConfig.TOPIC_QUEUE2})
    public void topicComsumer2(Map<String, Object> map) {
        System.out.printf("topicComsumer2%s\n", map);
    }
}
