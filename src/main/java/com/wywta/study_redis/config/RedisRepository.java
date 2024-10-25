package com.wywta.study_redis.config;

import com.wywta.study_redis.util.NullConverterUtil;
import org.springframework.data.redis.core.*;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisRepository extends RedisRepositoryBase {

    public RedisTemplate getRedisTemplate(RedisServerNames serverNames) {

        switch (serverNames) {
            // redis queue
            case QUEUE_CHAT:
                return redisQueueChatTemplate;
        }
        return null;
    }

    /*******************************
     * Redis Template Method
     ******************************
     * @return*/
    public String set(RedisServerNames serverName, String key, String val) {
        ValueOperations<String, Object> ops = getRedisTemplate(serverName).opsForValue();
        ops.set(key, val);
        return key;
    }

    public String set(RedisServerNames serverName, String key, String val, long expireSeconds) {
        ValueOperations<String, Object> ops = getRedisTemplate(serverName).opsForValue();
        ops.set(key, val, expireSeconds, TimeUnit.SECONDS);
        return key;
    }

    public String get(RedisServerNames serverName, String key) {
        ValueOperations<String, Object> ops = getRedisTemplate(serverName).opsForValue();
        return (String) ops.get(key);
    }

    public <T> T get(RedisServerNames serverName, String key, T defaultValue) {
        ValueOperations ops = getRedisTemplate(serverName).opsForValue();
        Object result = ops.get(key);
        return NullConverterUtil.convertTo(result, defaultValue);
    }

    public String lpop(RedisServerNames serverName, String queue) {
        ListOperations ops = getRedisTemplate(serverName).opsForList();
        return (String) ops.leftPop(queue);
    }

    public List<String> lpop(RedisServerNames serverName, String queue, int popCount) {
        ListOperations ops = getRedisTemplate(serverName).opsForList();
        return ops.leftPop(queue, popCount);
    }

    public List<String> range(RedisServerNames serverName, String key) {
        ListOperations ops = getRedisTemplate(serverName).opsForList();
        return ops.range(key, 0, -1);
    }

    public List<String> range(RedisServerNames serverName, String key, int from, int to) {
        ListOperations ops = getRedisTemplate(serverName).opsForList();
        return ops.range(key, from, to);
    }

    public Set zrange(RedisServerNames serverName, String key, int order) {
        ZSetOperations ops = getRedisTemplate(serverName).opsForZSet();
        if (order == 1) {
            return ops.range(key, 0, -1);
        }
        return ops.reverseRange(key, 0, -1);
    }

    public Long zrank(RedisServerNames serverName, String key, String value) {
        ZSetOperations ops = getRedisTemplate(serverName).opsForZSet();
        return ops.rank(key, value);
    }


    public void rpush(RedisServerNames serverName, String queue, String data) {
        ListOperations ops = getRedisTemplate(serverName).opsForList();
        ops.rightPush(queue, data);
    }

    public void rpushAll(RedisServerNames serverName, String queue, List<String> data) {
        ListOperations ops = getRedisTemplate(serverName).opsForList();
        ops.rightPushAll(queue, data);
    }

    public void lpush(RedisServerNames serverName, String queue, String data) {
        ListOperations ops = getRedisTemplate(serverName).opsForList();
        ops.leftPush(queue, data);
    }

    public boolean isMember(RedisServerNames serverName, String key, String val) {
        SetOperations<String, String> ops = getRedisTemplate(serverName).opsForSet();
        return ops.isMember(key, val);
    }

    public void sadd(RedisServerNames serverName, String key, String val) {
        SetOperations<String, String> ops = getRedisTemplate(serverName).opsForSet();
        ops.add(key, val);
    }

    public void sadd(RedisServerNames serverName, String key, List<String> values) {
        List ops = getRedisTemplate(serverName).executePipelined(
                (RedisCallback<Object>) connection -> {
                    for (String value : values) {
                        connection.sAdd(value.getBytes());
                    }
                    return null;
                });
    }

    public void expire(RedisServerNames serverName, String key, Duration val) {
        RedisOperations<String, Object> ops = getRedisTemplate(serverName);
        ops.expire(key, val);
    }

    public String hget(RedisServerNames serverName, String key, String hashKey) {
        HashOperations<String, Object, Object> hashOperations = getRedisTemplate(serverName).opsForHash();
        String data = (String) hashOperations.get(key, hashKey);
        return data;
    }

    public void hset(RedisServerNames serverName, String key, String hashKey, String value) {
        HashOperations<String, Object, Object> hashOperations = getRedisTemplate(serverName).opsForHash();
        hashOperations.put(key, hashKey, value);
    }

    public void hdel(RedisServerNames serverName, String key, String hashKey) {
        HashOperations<String, Object, Object> hashOperations = getRedisTemplate(serverName).opsForHash();
        hashOperations.delete(key, hashKey);
    }

    public Set sMember(RedisServerNames serverName, String key) {
        SetOperations<String, Object> ops = getRedisTemplate(serverName).opsForSet();
        return ops.members(key);
    }

    public Long increment(RedisServerNames serverName, String key) {
        ValueOperations<String, Object> ops = getRedisTemplate(serverName).opsForValue();
        return ops.increment(key);
    }

    public Long increment(RedisServerNames serverName, String key, int val) {
        ValueOperations<String, Object> ops = getRedisTemplate(serverName).opsForValue();
        return ops.increment(key, val);
    }

    public void setBit(RedisServerNames serverName, String key, String offset, boolean value) {
        ValueOperations<String, Object> ops = getRedisTemplate(serverName).opsForValue();
        ops.setBit(key, Integer.parseInt(offset), value);
    }

    public boolean getBit(RedisServerNames serverName, String key, String offset) {
        ValueOperations<String, Object> ops = getRedisTemplate(serverName).opsForValue();
        boolean result = ops.getBit(key, Integer.parseInt(offset));
        return result;
    }

    public boolean del(RedisServerNames serverName, String key) {
        RedisOperations<String, Object> ops = getRedisTemplate(serverName);
        return ops.delete(key);
    }

    public byte[] getBytes(RedisServerNames serverName, String key) {
        return getRedisTemplate(serverName).getConnectionFactory().getConnection().get(key.getBytes());
    }

    public Long srem(RedisServerNames serverName, String key, String value) {
        SetOperations<String, String> ops = getRedisTemplate(serverName).opsForSet();
        return ops.remove(key, value);
    }

    public Long llen(RedisServerNames serverName, String key) {
        ListOperations ops = getRedisTemplate(serverName).opsForList();
        Long length = ops.size(key);
        return NullConverterUtil.convertTo(length, 0L);
    }
}
