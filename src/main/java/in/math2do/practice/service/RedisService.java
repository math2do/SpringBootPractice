package in.math2do.practice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  public <T> T get(String key, Class<T> entityClass) {
    var redis = redisTemplate.opsForValue();
    Object o = redis.get(key);
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(o.toString(), entityClass);
    } catch (JsonProcessingException e) {
      log.error("Exception:", e);
    }
    return null;
  }

  public void set(String key, Object val, Long ttl) {
    var redis = redisTemplate.opsForValue();
    try {
      redis.set(key, val, ttl, TimeUnit.SECONDS);
    } catch (Exception e) {
      log.error("Exception:", e);
    }
  }
}
