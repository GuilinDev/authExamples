package com.guilin.idempotenceendpoint.service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * Concatenated prefix of Redis Key
     */
    private static final String IDEMPOTENT_TOKEN_PREFIX = "idempotent_token:";

    /**
     * Create Token and save into Redis，return the Token
     *
     * @param value used to validate value stored in Redis
     * @return Token string
     */
    public String generateToken(String value) {
        // Instantiate Token
        String token = UUID.randomUUID().toString();
        // set Redis entry Key
        String key = IDEMPOTENT_TOKEN_PREFIX + token;
        // save Token into Redis，set expiration time to 2 minutes
        redisTemplate.opsForValue().set(key, value, 2, TimeUnit.MINUTES);
        // return Token
        return token;
    }

    /**
     * Verify Token Correctness
     *
     * @param token token String
     * @param value used to validate value stored in Redis
     * @return true: token is valid; false: token is invalid
     */
    public boolean validToken(String token, String value) {
        // Set Lua script, Key[1] is the Key of Redis, Key[2] is the Value of Redis
        String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        // Concatenated prefix of Redis Key
        String key = IDEMPOTENT_TOKEN_PREFIX + token;

        Long result = redisTemplate.execute(redisScript, Arrays.asList(key, value));

        // Verify the result
        if (result != null && result != 0L) {
            log.info("Verify token={},key={},value={} Successfully", token, key, value);
            return true;
        }
        log.info("Verify token={},key={},value={} Failed", token, key, value);
        return false;
    }

}
