package com.example.springredis.test;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/6 9:44
 * @Description:
 */


@SpringBootTest
@Log4j2(topic = "c.test")
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
    public void setGeo() {
        Point point = new Point(116.41339, 39.91092);
        redisUtil.setGeo("cityGeo", point, "北京");
        Point point2 = new Point(118.68244, 24.87995);
        redisUtil.setGeo("cityGeo", point2, "泉州");
        System.out.println("--------------------------------------------");
        System.out.println(redisUtil.getDistance("cityGeo", "北京", "泉州") + "km");
    }

    @Test
    public void bitMapDemo() {
        String user = "user::1";
        //模拟一周签到
        //周一进行签到

        //计算一周连续签到
        redisUtil.setBitMap(user, 0);
        redisUtil.setBitMap(user, 1);
        redisUtil.setBitMap(user, 2);
        redisUtil.setBitMap(user, 4);
        List<Long> continueCount = redisUtil.getContinueCount(user);
        Long val = continueCount.get(0);
        int res = 0;
        int temp = 0;
        int lastDay = 7;
        for (int i = lastDay; i > 0; i--) {
            //如果经过左移和右移后一样，说明末尾为0
            if (val >> 1 << 1 == val) {
                if (temp > res) {
                    res = temp;
                    temp = 0;
                }
                if (res >= i) {
                    break;
                }
            } else {
                temp++;
            }
            //舍弃末位
            val >>= 1;
        }
        if (temp > res) {
            res = temp;
        }
        log.debug("连续签到的天数为{}天", res);

    }
}
