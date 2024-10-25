package com.wywta.study_redis;

import com.wywta.study_redis.config.RedisRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String QUEUE = "QUEUE";

//    @Autowired
//    private RedisRepository redisRepository;

    @DisplayName("redisTemplate test")
    @Test
    void redisTemplateStringTest() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String key = "name";
        valueOperations.set(key, "wywta");

        String value = valueOperations.get(key);
        Assertions.assertEquals(value, "wywta");
    }

    @Test
    void testStrings() {
        // given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        // when
        valueOperations.set(key, "hello");

        // then
        String value = valueOperations.get(key);
        assertThat(value).isEqualTo("hello");
    }

    @DisplayName("rightPush test")
    @Test
    void rightPushTest() {

        String data = "data";

        ListOperations ops = redisTemplate.opsForList();
        ops.rightPush(QUEUE, data);


    }


    public void lpush(String queue, String data) {
        ListOperations ops = redisTemplate.opsForList();
        ops.leftPush(QUEUE, data);
    }
}
