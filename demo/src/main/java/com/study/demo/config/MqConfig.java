package com.study.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqConfig {
	final static String queueName = "hello";

	@Bean
	public Queue helloQueue() {
		return new Queue("hello");
	}
	
	@Bean
	public Queue userQueue() {
		return new Queue("user");
	}
	
	
}
