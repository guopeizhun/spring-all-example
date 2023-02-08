package com.example.springredis.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/6 9:45
 * @Description:
 */


@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void set(String k, Object v) {
        redisTemplate.opsForValue().set(k, v);
    }

    public void setExpire(String k, Object v, Long timeout) {
        redisTemplate.opsForValue().set(k, v);
        redisTemplate.expire(k, timeout, TimeUnit.MINUTES);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setGeo(String k, Point p, String k1) {
        redisTemplate.opsForGeo().add(k, p, k1);
    }

    public double getDistance(String k, String k1, String k2) {
        GeoResults<RedisGeoCommands.GeoLocation<Object>> radius = redisTemplate.opsForGeo().radius("", "", new Distance(1.1, RedisGeoCommands.DistanceUnit.KILOMETERS));

        return redisTemplate.opsForGeo().distance(k, k1, k2, RedisGeoCommands.DistanceUnit.KILOMETERS).getValue();
    }


    public void setBitMap(String key, int offset) {
        redisTemplate.opsForValue().setBit(key, offset, true);
    }

    public boolean getBitMap(String key, int offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    public long bitCount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes())
        );
    }

    public List<Long> getContinueCount(String key) {
        return redisTemplate.opsForValue().bitField(key, BitFieldSubCommands.create()
                .get(BitFieldSubCommands.BitFieldType.unsigned(7)).valueAt(0));
    }


}
