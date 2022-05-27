package com.crud.employee.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisCacheConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.user}")
    private String redisUser;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.timeout}")
    private Integer redisTimeOut;

    @Value("${redis.maximumActiveConnectionCount}")
    private Integer redisMaximumActiveConnectionCount;

    @Bean
    public JedisPool jedisPool() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisMaximumActiveConnectionCount);

        return new JedisPool(jedisPoolConfig, redisHost, redisPort, redisTimeOut, redisUser, redisPassword);
    }

}
