package com.joonhyuk.Subject.commerce.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  public void setValues(String key, String data) {
    ValueOperations<String, Object> values = redisTemplate.opsForValue();
    values.set(key, data);
  }
}
