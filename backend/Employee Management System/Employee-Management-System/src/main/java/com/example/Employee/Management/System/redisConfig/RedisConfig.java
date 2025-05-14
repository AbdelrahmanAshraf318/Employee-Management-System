package com.example.Employee.Management.System.redisConfig;

import com.example.Employee.Management.System.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig
{
    @Bean
    public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, User> tpl = new RedisTemplate<>();
        tpl.setConnectionFactory(cf);

        // write keys as plain strings
        tpl.setKeySerializer(new StringRedisSerializer());
        tpl.setHashKeySerializer(new StringRedisSerializer());

        // write values as JSON
        Jackson2JsonRedisSerializer<User> ser =
                new Jackson2JsonRedisSerializer<>(User.class);
        tpl.setValueSerializer(ser);
        tpl.setHashValueSerializer(ser);

        return tpl;
    }
}
