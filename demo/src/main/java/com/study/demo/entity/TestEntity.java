package com.study.demo.entity;

import org.hibernate.validator.constraints.Length;

public class TestEntity {
	@Length(min = 1,max = 5)
	private String username;
	private String password;
	private User user;
}
