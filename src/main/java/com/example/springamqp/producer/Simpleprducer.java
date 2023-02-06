package com.example.springamqp.producer;

//import com.example.springamqp.config.RabbitmqConfig;
import org.junit.jupiter.api.Test;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.UUID;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/4 11:07
 * @Description:
 */

@SpringBootTest
public class Simpleprducer {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Test
    public void producer() {
        for (int i = 0; i < 10000; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("orderId", 1);
//            rabbitTemplate.convertAndSend(RabbitmqConfig.FANOUT_EXCHANGE, "", map);
        }
    }

    /**
     * 生产者防止消息发送到mq失败，使用异步确认ack
     */
    @Test
    public void producerAck() {
        //生产者异步ack

        for (int i = 0; i < 1000000; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("orderId", 1);
//            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//            correlationData.getFuture().addCallback(result -> {
//                if (result.isAck()) {
//                    System.out.printf("消息%s发布成功", correlationData.getId());
//                } else {
//                    System.out.printf("消息%s发布失败", correlationData.getId());
//                }
//
//            }, ex -> {
//                System.out.println("发生异常");
//                ex.printStackTrace();
//            });
//            rabbitTemplate.convertAndSend(RabbitmqConfig.FANOUT_EXCHANGE, "", map, correlationData);
        }
    }

    /**
     * 消费者进行ack
     */
    @Test
    public void producerAndComsumerAck() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Object data = "hello";
        map.put("retryNum", 3);
        map.put("data", data);
//        rabbitTemplate.convertAndSend(RabbitmqConfig.SIMPLE_EXCHANGE, RabbitmqConfig.SIMPLE_QUEUE, map);
    }

    @Test
    public void topicProducer() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", "data");
        String pattern = "topic.#";
//        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, pattern, map);
    }

}
