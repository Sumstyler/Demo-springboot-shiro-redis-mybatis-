package com.study.demo;

import com.study.demo.config.support.RedisObjectSerializer;
import com.study.demo.entity.User;

public class SerializerTest {

	public static void main(String[] args) {
		User user = new User();
		user.setAge("132");
		RedisObjectSerializer serializer = new RedisObjectSerializer();
		byte[] bytes = serializer.serialize(user);
		User anUser = (User) serializer.deserialize(bytes);
		System.out.println(anUser.getAge());
		System.out.println(user instanceof User);

	}

}
