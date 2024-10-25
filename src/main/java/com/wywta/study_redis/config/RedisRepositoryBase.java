package com.wywta.study_redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class RedisRepositoryBase {
    @Autowired
    protected RedisTemplate redisQueueChatTemplate;
}
