package com.study.demo;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroTest {

	public static void main(String[] args) {
		String username = "user";
		String tel = "15989305930";
		String password = tel.substring(5);

		String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
		System.out.println(salt);
		SimpleHash hash = new SimpleHash("SHA1", password, username + salt, 2);
		System.out.println(hash.toHex());

	}
}
