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
