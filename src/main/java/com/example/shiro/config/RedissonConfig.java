package com.example.shiro.config;
import com.example.shiro.utils.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 配置类
 * @author: tanxuhui
 * @create: 2019-07-18 10:33
 */
//@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;


    @Value("${spring.redis.database}")
    private int database;

    @Bean
    public RedissonClient redissonClient(){
        //设置系统代码
        RedissonUtil.setSysCode("TIEBEI");

        Config config = new Config();
        config.useSingleServer()
                .setDatabase(database)
                .setAddress("redis://" + host + ":" + port)
                .setPassword(password);
        config.setCodec(JsonJacksonCodec.INSTANCE);
        Redisson.create(config).getLock("");
        return Redisson.create(config);
    }

}