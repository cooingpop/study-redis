package com.wywta.study_redis.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    // Spring Data Redis는 Redis DB와의 연결을 위해 자바 Redis 오픈소스 라이브러리인 Lettuce와 Jedis를 활용한다.
    //Lettuce는 비동기로 동작해 Jedis에 비해 성능이 좋기도 하고, Lettuce 사용을 권장한다고 한다.
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    // Spring Data Redis가 제공하는 Redis의 데이터에 접근하는 방법에는
    // RedisTemplate 과 RedisRepositories가 있다.
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }
}
