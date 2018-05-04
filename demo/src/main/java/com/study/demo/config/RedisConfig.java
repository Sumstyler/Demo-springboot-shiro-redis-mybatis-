package com.study.demo.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.study.demo.config.support.RedisObjectSerializer;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// 单位默认为s
		cacheManager.setDefaultExpiration(1800);
		return cacheManager;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(new StringRedisSerializer());
		// 修改值的序列化方法
		template.setValueSerializer(new RedisObjectSerializer());

		/*
		 * Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new
		 * Jackson2JsonRedisSerializer<>( Object.class); ObjectMapper om = new
		 * ObjectMapper(); om.setVisibility(PropertyAccessor.ALL,
		 * JsonAutoDetect.Visibility.ANY);
		 * om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		 * jackson2JsonRedisSerializer.setObjectMapper(om);
		 * template.setValueSerializer(jackson2JsonRedisSerializer);
		 */

		return template;
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object o, Method method, Object... objects) {
				StringBuffer sb = new StringBuffer();
				sb.append(o.getClass().getName());
				sb.append(method.getName());
				for (Object obj : objects) {
					if (obj != null)
						sb.append(obj.toString());
				}
				System.out.println("生成key keyGenerator=" + sb.toString());
				return sb.toString();
			}
		};
	}

}
