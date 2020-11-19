package com.example.springboot_jsp_shiro.shiro.cache;

import com.example.springboot_jsp_shiro.utils.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

public class RedisCache<K, V> implements Cache<K, V> {
    @Override
    public V get(K k) throws CacheException {
        System.out.println("get key:" + k);
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        //将k转成string序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return (V)redisTemplate.opsForValue().get(k.toString());
    }

    @Override
    public V put(K k, V v) throws CacheException {
        System.out.println("put key:" + k);
        System.out.println("put value:" + v);
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        //将k转成string序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(k.toString(), v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
