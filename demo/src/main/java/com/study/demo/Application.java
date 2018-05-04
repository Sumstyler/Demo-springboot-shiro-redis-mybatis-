package com.study.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @Description: 启动类
 * @author xiewm
 * @date 2017年12月4日 下午2:21:38
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
