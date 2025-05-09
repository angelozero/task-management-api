package com.angelozero.task.management.adapter.dataprovider;

import lombok.AllArgsConstructor;
import com.angelozero.task.management.usecase.gateway.CacheGateway;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class CacheByRedisDataProvider implements CacheGateway {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> void save(String key, T value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        var value = redisTemplate.opsForValue().get(key);

        return clazz.isInstance(value) ? (T) value : null;
    }
}
