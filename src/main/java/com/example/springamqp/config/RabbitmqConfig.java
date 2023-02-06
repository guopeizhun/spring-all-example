//package com.example.springamqp.config;
//
//import org.springframework.amqp.core.*;
//
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.text.Bidi;
//
///**
// * @Author:letg(pz)
// * @Date: 2023/2/4 11:08
// * @Description:
// */
//
//
//@Configuration
//public class RabbitmqConfig {
//
//    public final static String SIMPLE_QUEUE = "simple.queue";
//    public final static String SIMPLE_EXCHANGE = "simple.exchange";
//
//    public final static String FANOUT_EXCHANGE = "fanout.exchange";
//    public final static String FANOUT_QUEUE1 = "fanout.queue1";
//    public final static String FANOUT_QUEUE2 = "fanout.queue2";
//
//    public final static String TOPIC_EXCHANGE = "topic.exchane";
//    public final static String TOPIC_QUEUE1 = "topic.queue1";
//    public final static String TOPIC_QUEUE2 = "topic.queue2";
//    /**
//     * directExchange  发送给与交换机绑定的routingkey相同的队列
//     * start
//     * @return
//     */
//    @Bean
//    public Queue simpleQueue() {
//        return QueueBuilder.nonDurable(SIMPLE_QUEUE).build();
//    }
//
//    @Bean
//    public DirectExchange simpleExchange() {
//        return ExchangeBuilder.directExchange(SIMPLE_EXCHANGE).build();
//    }
//
//    @Bean
//    public Binding simpleBinding() {
//        return BindingBuilder.bind(simpleQueue()).to(simpleExchange()).with(SIMPLE_QUEUE);
//    }
//    /**
//     * directExchange
//     * end
//     * @return
//     */
//
//    /**
//     * fanoutExchange 发送给所有与交换机绑定的队列
//     * start
//     * @return
//     */
//    @Bean
//    public FanoutExchange fanoutExchange(){
//        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE).build();
//    }
//
//    @Bean
//    public Queue fanoutQueue1() {
//        return QueueBuilder.nonDurable(FANOUT_QUEUE1).build();
//    }
//
//    @Bean
//    public Queue fanoutQueue2() {
//        return QueueBuilder.nonDurable(FANOUT_QUEUE2).build();
//    }
//
//    @Bean
//    public Binding fanoutBinding1() {
//        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
//    }
//    @Bean
//    public Binding fanoutBinding2() {
//        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
//    }
//
//
//    @Bean
//    public TopicExchange topicExchange(){
//        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).build();
//    }
//
//    @Bean
//    public Queue topicQueue1(){
//        return QueueBuilder.nonDurable(TOPIC_QUEUE1).build();
//    }
//    @Bean
//    public Queue topicQueue2(){
//        return QueueBuilder.nonDurable(TOPIC_QUEUE2).build();
//    }
//    @Bean
//    public Binding topicBinding1(){
//        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.work.order");
//    }
//    @Bean
//    public Binding topicBinding2(){
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.work.temporder");
//    }
//    @Bean
//    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }
//}
//
