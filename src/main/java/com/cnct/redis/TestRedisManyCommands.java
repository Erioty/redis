package com.cnct.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 演示各种不同数据类型的运用方式。
 */
public class TestRedisManyCommands {
    JedisPool pool;
    Jedis jedis;

    @Before
    public void setUp(){
        jedis = new Jedis("localhost");
    }

    /**
     * Redis存储初级的字符串
     * CRUD
     */
    @Test
    public void testBasicString(){
        //往"name"这个key中设置"zhouyi"这个值
        jedis.set("name", "zhouyi");
        System.out.println(jedis.get("name"));

        //追加值
        jedis.append("name", "yi");
        System.out.println(jedis.get("name"));

        //覆盖值
        jedis.set("name", "liuzhiwen");
        System.out.println(jedis.get("name"));

        //删除键
        jedis.del("name");
        System.out.println(jedis.get("name"));

        /*
        同时设置多个键值对
        mset相当于：
         jedis.set("name", "Lili");
         jedis.set("age", "gender");
         jedis.set("gender", "female");
         */
        jedis.mset("name","Lili","age","18","gender","female");
        System.out.println(jedis.mget("name","age","gender"));
    }

    /**
     * jedis操作map
     */
    @Test
    public void testMap(){
        Map<String, String> user = new HashMap<String, String>();
        //给map对象赋值
        user.put("name", "Mi");
        user.put("pwd", "123456");
        user.put("email", "Mi@123.com");
        user.put("tel", "15894153651");
        //将赋值好后的map对象存到"user"这个key中
        jedis.hmset("user", user);

        /*
        从对象中取值
        第一个参数是存入redis中map对象的key，此map对象是值
        第二个参数是放入map中的对象的key，后面的key是可变参数
         */
        List<String> tel = jedis.hmget("user", "tel");
        System.out.println(tel);
        List<String> info = jedis.hmget("user", "name", "pwd");
        System.out.println(info);
    }

    /**
     * jedis操作list
     */
    @Test
    public void testList(){
        //开始前先移除所有内容
        jedis.del("java framework");
        //jedis.llen获取长度
        System.out.println(jedis.llen("java framework"));
        //jedis.lrange获取范围内的内容 第一个参数是key,第二个参数是开始位置，第三个参数是结束位置，-1表示获取所有
        System.out.println(jedis.lrange("java framework",0,-1));
        //先存入3条数据
        jedis.lpush("java framework", "java");
        jedis.lpush("java framework", "oracle");
        jedis.lpush("java framework", "php");
        System.out.println(jedis.llen("java framework"));
        System.out.println(jedis.lrange("java framework",1,2));
    }

    /**
     * jedis操作Set(无序)
     */
    @Test
    public void testSet(){
        //添加
        jedis.sadd("sname", "Tom");
        jedis.sadd("sname", "Lili");
        jedis.sadd("sname", "Mary");
        jedis.sadd("sname", "Jack");
        //jedis.smembers 获取所有加入的value
        System.out.println(jedis.smembers("sname"));
        //移除值
        jedis.srem("sname", "Tom");
        System.out.println(jedis.smembers("sname"));
        //判断"Tom"是否是sname中包含的值
        System.out.println(jedis.sismember("sname", "Tom"));
        //jedis.srandmember随机获取一个值
        System.out.println(jedis.srandmember("sname"));
        //返回集合中的元素个数
        System.out.println(jedis.scard("sname"));
    }


    @Test
    public void test() throws InterruptedException {
       //返回当前库中所有的key
        System.out.println(jedis.keys("*"));

        //返回当前库中包含"name"的key
        System.out.println(jedis.keys("*name"));

        //删除key为name的键 删除成功返回1，删除失败或键不存在返回0
        System.out.println(jedis.del("name"));

        //返回给定key的有效时间，-1表示永远有效
        System.out.println(jedis.ttl("sname"));

        //指定key的存活时间，单位为秒
        jedis.setex("timekey", 8, "min");
        System.out.println(jedis.ttl("timekey"));
        //Thread.sleep(5000);//睡眠5秒
        //System.out.println(jedis.ttl("timekey"));

        //检查key是否存在
        System.out.println(jedis.exists("xxx"));

        //重命名key
        System.out.println(jedis.rename("timekey","tkey"));

        //修改名字后，再次获取为null
        System.out.println(jedis.get("timekey"));

        System.out.println(jedis.get("tkey"));

    }


    @Test
    public void testSort(){
        //jedis排序
        //先清除数据，再加入
        jedis.del("xx");
        jedis.rpush("xx", "1");
        jedis.rpush("xx", "7");
        jedis.rpush("xx", "5");
        jedis.rpush("xx", "9");
        jedis.rpush("xx", "3");
        System.out.println(jedis.lrange("xx",0,-1));
        System.out.println(jedis.sort("xx"));//排序 默认升序
        System.out.println(jedis.lrange("xx",0,-1));

    }
}
