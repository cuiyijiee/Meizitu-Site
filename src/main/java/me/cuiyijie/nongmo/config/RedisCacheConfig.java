//package me.cuiyijie.nongmo.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.io.Serializable;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableCaching
//public class RedisCacheConfig implements Serializable {
//
//    /**
//     * 申明缓存管理器，会创建一个切面（aspect）并触发Spring缓存注解的切点（pointcut）
//     * 根据类或者方法所使用的注解以及缓存的状态，这个切面会从缓存中获取数据，将数据添加到缓存之中或者从缓存中移除某个值
//     */
//
//    @Bean
//    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
//        // 创建一个模板类
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        // 将刚才的redis连接工厂设置到模板类中
//        template.setConnectionFactory(factory);
//        // 设置key的序列化器
//        template.setKeySerializer(new StringRedisSerializer());
//        // 设置value的序列化器
//        //使用Jackson 2，将对象序列化为JSON
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getJackson2JsonRedisSerializer();
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        return template;
//    }
//
//
//    /**
//     * 最新版，设置redis缓存过期时间
//     */
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
//                this.getRedisCacheConfigurationWithTtl(60), // 默认策略，未配置的 key 会使用这个
//                this.getRedisCacheConfigurationMap() // 指定 key 策略
//        );
//    }
//
//    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
//        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
//        //自定义设置缓存时间
//        redisCacheConfigurationMap.put(SysConstant.CacheKey.CATEGORY, this.getRedisCacheConfigurationWithTtl(60 * 60 * 24));
//        redisCacheConfigurationMap.put(SysConstant.CacheKey.ALBUM_PICTURE, this.getRedisCacheConfigurationWithTtl(60 * 60 * 2));
//        redisCacheConfigurationMap.put(SysConstant.CacheKey.RANDOM_ALBUM, this.getRedisCacheConfigurationWithTtl(5));
//        redisCacheConfigurationMap.put(SysConstant.CacheKey.POPULAR_ALBUM, this.getRedisCacheConfigurationWithTtl(5));
//        return redisCacheConfigurationMap;
//    }
//
//    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getJackson2JsonRedisSerializer();
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)).entryTtl(Duration.ofSeconds(seconds));
//        return redisCacheConfiguration;
//    }
//
//    private Jackson2JsonRedisSerializer<Object> getJackson2JsonRedisSerializer() {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        return jackson2JsonRedisSerializer;
//    }
//}