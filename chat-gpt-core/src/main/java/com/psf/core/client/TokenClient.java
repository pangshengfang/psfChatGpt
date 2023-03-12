package com.psf.core.client;

import com.psf.common.constant.enums.ErrorCodeMsg;
import com.psf.core.persistence.dao.TokenDao;
import com.psf.core.persistence.entity.TokenEntity;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class TokenClient {

    private static JedisPool jedisPool;

    private static final String TOKEN_KEY = "gpt_token";

    private static final int EXPIRE_TIME = 604800;

    private final TokenDao tokenDao;

    private TokenClient(TokenDao tokenDao) {
        // 初始化 Jedis 连接池
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(10);
        config.setMinIdle(1);
        config.setMaxWaitMillis(3000);
        jedisPool = new JedisPool(config, "localhost", 6379);
        this.tokenDao = tokenDao;
    }

    public String getToken() {
        try (Jedis jedis = jedisPool.getResource()) {
            String token = jedis.get(TOKEN_KEY);
            if (token == null) {
                // 生成新的 token
                token = generateToken();
                jedis.setex(TOKEN_KEY, EXPIRE_TIME, token);
            }
            return token;
        }
    }

    private String generateToken() {
        // 生成新的 token，可以使用 UUID 或其他方式生成唯一的字符串
        TokenEntity entity = tokenDao.findAll().stream().findFirst().orElseThrow(ErrorCodeMsg.BAD_REQUEST::newException);
        return entity.getToken();
    }
}
