package com.scaff.tools.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ElasticacheServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by chenwen on 16/10/18.
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${redis.cache.timeout:7200}")
    private Long cacheTimeout;

    @Bean(name = {"redisProperties"})
    @ConditionalOnMissingBean
    public RedisProperties redisProperties() {
        return new RedisProperties();
    }

    /**
     * 可能会有这样一种情况，当你创建多个具有相同类型的 bean 时，并且想要用一个属性只为它们其中的一个进行装配，在这种情况下，你可以使用 @Qualifier 注释和 @Autowired 注释通过指定哪一个真正的 bean 将会被装配来消除混乱。
     */
    @Autowired
    @Qualifier("redisProperties")
    private RedisProperties properties;

    @Bean
    public KeyGenerator wiselyKeyGenerator(){
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };

    }

    @Bean
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        RedissonSpringCacheManager redissonSpringCacheManager = new RedissonSpringCacheManager(redissonClient());
        return redissonSpringCacheManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedissonClient redissonClient() {
        Config config = new Config();

        log.info("redisConfig = {}", properties);

        //sentinel
        if (properties.getSentinel() != null) {
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(properties.getSentinel().getMaster());
            properties.getSentinel().getNodes();
            sentinelServersConfig.addSentinelAddress(properties.getSentinel().getNodes().split(","));
            sentinelServersConfig.setDatabase(properties.getDatabase());
            if (properties.getPassword() != null) {
                sentinelServersConfig.setPassword(properties.getPassword());
            }
        } else if (properties.isAws()){
            ElasticacheServersConfig elasticacheServersConfig = config.useElasticacheServers();
            elasticacheServersConfig.addNodeAddress("redis://"+properties.getHost() + ":" + properties.getPort());
            elasticacheServersConfig.setDatabase(properties.getDatabase());
        } else { //single server
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress("redis://"+properties.getHost() + ":" + properties.getPort());
            singleServerConfig.setDatabase(properties.getDatabase());
            if (properties.getPassword() != null) {
                singleServerConfig.setPassword(properties.getPassword());
            }
        }
        return Redisson.create(config);
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new MyCacheResolver();
    }

    public class MyCacheResolver implements CacheResolver {

        @Autowired
        private CacheManager cacheManager;

        @Override
        public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
            List<Cache> caches = new ArrayList<>();
            for(String cacheName : context.getOperation().getCacheNames()) {
                caches.add(cacheManager.getCache(cacheName));
            }
            return caches;
        }
    }
}
