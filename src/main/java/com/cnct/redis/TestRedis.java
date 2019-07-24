package com.cnct.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;


public class TestRedis {


    /**
     * 使用第三方jar包 jedis 访问redis服务
     */
    @Test
    public void test1(){
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.set("hero", "tom");
        String value = jedis.get("hero");
        System.out.println(value);
    }
}
