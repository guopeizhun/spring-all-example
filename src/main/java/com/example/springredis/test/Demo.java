package com.example.springredis.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/6 9:44
 * @Description:
 */


@SpringBootTest
public class Demo {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void simple() {
//        Order order = new Order("123", new Date(), "123", "北京", "testuser", "123");
//        redisUtil.set();\
        System.out.println("--------------------------------");
//        String key = "order";
//        redisUtil.set(key, order);
//        System.out.println(redisUtil.get(key));
    }

    @Test
    public void setGeo(){
        Point point = new Point(116.41339, 39.91092);
        redisUtil.setGeo("cityGeo",point,"北京");
        Point point2 = new Point(118.68244, 24.87995);
        redisUtil.setGeo("cityGeo",point2,"泉州");
        System.out.println("--------------------------------------------");
        System.out.println(redisUtil.getDistance("cityGeo", "北京", "泉州")+"km");
    }
}
