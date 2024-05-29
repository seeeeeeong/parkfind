package seeeeeeong.parkfind.domain.park.cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import seeeeeeong.parkfind.common.config.RedisTestContainerConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RedisTemplateTest extends RedisTestContainerConfig {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRedisTemplateStringOperations() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";
        String value = "hello";

        valueOperations.set(key, value);

        Object result = valueOperations.get(key);
        assertEquals(value, result);
    }

    @Test
    public void testRedisTemplateSetOperations() {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        String key = "setKey";

        setOperations.add(key, "h", "e", "l", "l", "o");

        Long size = setOperations.size(key);
        assertEquals(4, size);
    }

    @Test
    public void testRedisTemplateHashOperations() {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String key = "hashKey";

        hashOperations.put(key, "subKey", "value");

        Object result = hashOperations.get(key, "subKey");
        assertEquals("value", result);

        var entries = hashOperations.entries(key);
        assertTrue(entries.keySet().contains("subKey"));
        assertTrue(entries.values().contains("value"));

        Long size = hashOperations.size(key);
        assertEquals(entries.size(), size);
    }
}
