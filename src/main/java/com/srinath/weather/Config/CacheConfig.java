package com.srinath.weather.Config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfig);
//        connectionFactory.setValidateConnection(true); // Fail fast on startup or failure
        connectionFactory.setTimeout(500);
        connectionFactory.setShutdownTimeout(500);
        return connectionFactory;
    }



    @Bean
    @Primary
    public CacheManager cacheManager(RedisCacheManager redisCacheManager,
                                     RedisTemplate<String, Object> redisTemplate) {
        return new ConditionalCacheManager(redisCacheManager, redisTemplate);
    }

    //    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .commandTimeout(Duration.ofSeconds(2))
//                .shutdownTimeout(Duration.ZERO)  // Immediate shutdown
//                .build();
//
//        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration();
//        return new LettuceConnectionFactory(serverConfig, clientConfig);
//    }


}
