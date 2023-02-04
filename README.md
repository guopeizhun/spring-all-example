# 快速开始

打开项目的/src/main/java/com/example可以得到关于springboot整合其他框架的使用，操作简单，极易上手，

## SpringSession-Redis原理

SpringSession-Redis是为了解决分布式的session不共享问题，SpringSession的实现
是通过SessionRepositoryFilter拦截器来替换原来的request和response，使用SessionRepositoryRequestWrapper
和SessionRepositoryResponseWrapper将数据的保存和获取通过redis来实现,主要实现代码如下

```
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute(SESSION_REPOSITORY_ATTR, this.sessionRepository);
        SessionRepositoryFilter<S>.SessionRepositoryRequestWrapper wrappedRequest = new SessionRepositoryFilter.SessionRepositoryRequestWrapper(request, response);
        SessionRepositoryFilter.SessionRepositoryResponseWrapper wrappedResponse = new SessionRepositoryFilter.SessionRepositoryResponseWrapper(wrappedRequest, response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            wrappedRequest.commitSession();
        }

    }
```

-----------

## Spring websocket

(这些都是通过订阅通道进行，这里结合了点对点聊天完成，前端使用vue，文件为demo.zip)

### 1.1 点对点

后端使用该方法发送给订阅该主题的连接
```simpMessagingTemplate.convertAndSendToUser(topic,message)```
前端使用来进行通道的订阅
```stompClient.subscribe(topic,callbackFunctopm)```

### 1.2 广播

广播采用注解的方式，发送给所有订阅目标主题的连接

```
@MessageMapping("/welcome")
  @SendTo("/topic/welcome")
  public String sendAlert(String message){
    return message;
  }
  ```

### 1.3 前端

``` npm i vue-beautiful-chat```
``` npm install stompjs```
```npm install sockjs-client```

------------------

## SpringAMQP基本的使用

### 对于基本的三种交换机的使用以及队列的声明，都有相应的提及，包括ack的生产者确认和消费者的确认，具体的的丢弃消息未做处理，可以丢入死信队列让生产者消费记录失败消息

### 对于生产者与消费者，rabbbitMq主要队列如下

- 一对一，一个生产者对应一个消费者。
- 一对多，一个生产者对应多个消费者，多个消费者共同消费一个队列的消息
- 订阅模式（广播），需要每个消费者都有独自的队列，实现如下

```
 @Bean
    public FanoutExchange fanoutExchange(){
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE).build();
    }

    @Bean
    public Queue fanoutQueue1() {
        return QueueBuilder.nonDurable(FANOUT_QUEUE1).build();
    }

    @Bean
    public Queue fanoutQueue2() {
        return QueueBuilder.nonDurable(FANOUT_QUEUE2).build();
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }


```

- 路由模式，是订阅模式的升级，在订阅的基础上增加routingKey来匹配消费者

```
 @Bean
    public Binding simpleBinding() {
        return BindingBuilder.bind(simpleQueue()).to(simpleExchange()).with(SIMPLE_QUEUE);
    }
```

- 通配符模式,是在路由模式的升级，能够允许通配routingkey来匹配消费者,#代表能匹配一个或多个（topic.#->topic.work.order） ,\*代表能匹配一个(topic.*->topic.work)
