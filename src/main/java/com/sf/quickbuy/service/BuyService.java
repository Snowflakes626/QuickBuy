package com.sf.quickbuy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.quickbuy.bean.BuyRecord;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BuyService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public boolean canVisit(String item, int limitTime, int limitNum){
        /**
         * 1、先将不在窗口内的请求移除
         * 2、在统计目前集合内请求数
         * 3、如果请求数小于限流数则将该请求放入集合并返回1
         */
        String lusScript = "redis.call('zremrangeByScore', KEYS[1], 0, ARGV[1])\n" +
                "local res = redis.call('zcard', KEYS[1])\n" +
                "if (res == nil) or (res < tonumber(ARGV[3]))\n" +
                "then redis.call('zadd', KEYS[1], ARGV[2], ARGV[2])\n" +
                "return 1\n" +
                "else\n" +
                "return 0\n" +
                "end\n";
      //不保证原子性 限流不一定成功
        long curTime = System.currentTimeMillis();
        //在zset里存入请求 并将请求时间作为权重实现按请求时间自动排序
        redisTemplate.opsForZSet().add(item, curTime, curTime);
        //移除时间范围外的请求
        redisTemplate.opsForZSet().removeRangeByScore(item, 0, curTime - limitTime * 1000);
        //统计时间范围内的请求个数
        Long count = redisTemplate.opsForZSet().size(item);
        System.out.println("5秒内申请数：" + count);
        //统一设置所有请求的超时时间
        redisTemplate.expire(item, limitTime, TimeUnit.SECONDS);
        if (count > limitNum)
            System.out.println("超出流量范围！");
        return limitNum >= count;
        /*String key = item;
        long curTime = System.currentTimeMillis();
        String[] args = new String[3];
        args[0] = String.valueOf(curTime - limitTime * 1000);
        args[1] = String.valueOf(curTime);
        args[2] = String.valueOf(limitNum);
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<String>();
        redisScript.setScriptText(lusScript);
        //调用lua脚本，请注意传入的参数
        Object luaResult = redisTemplate.execute((RedisConnection connection) -> connection.eval(
                redisScript.getScriptAsString().getBytes(),
                ReturnType.INTEGER,
                1,
                key.getBytes(),
                args.toString().getBytes()));
        if(luaResult.toString().equals("0")){
            System.out.println("超出流量限制！");
        }
        return luaResult.toString().equals("1");*/

    }

    public String buy(String item, String person) throws JsonProcessingException {
        //如果itemnum >= 1 执行原子操作（num减一  成功者信息add）
        String luaScript = "local person = ARGV[1]\n" +
                "local item = KEYS[1] \n" +
                "local left = tonumber(redis.call('get',item)) \n" +
                "if (left >= 1) \n" +
                "then redis.call('decrby',item,1) \n" +
                "redis.call('rpush','personList',person) \n" +
                "return 1 \n" +
                "else \n" +
                "return 0\n" +
                "end\n" +
                "\n" ;
        String key = item + "num";
        String args = person;
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<String>();
        redisScript.setScriptText(luaScript);
        //调用lua脚本，请注意传入的参数
        Object luaResult = redisTemplate.execute((RedisConnection connection) -> connection.eval(
                redisScript.getScriptAsString().getBytes(),
                ReturnType.INTEGER,
                1,
                key.getBytes(),
                args.getBytes()));
        //如果秒杀成功 向消息队列发消息，异步插入到数据库
        if(luaResult.toString().equals("1")){
            BuyRecord buyRecord = new BuyRecord();
            buyRecord.setItem(item);
            buyRecord.setPerson(person);
            ObjectMapper objectMapper = new ObjectMapper();
            String msg = objectMapper.writeValueAsString(buyRecord);
            rabbitTemplate.convertAndSend("myExchange", "key", msg);
        }
        return luaResult.toString();

    }

}
