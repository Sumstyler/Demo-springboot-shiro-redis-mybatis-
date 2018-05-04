package com.study.demo.utils.bean;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.study.demo.constant.GlobalConstant;
import com.study.demo.entity.User;

public class ApplicationUtil {

	public static User getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		Object obj = session.getAttribute(GlobalConstant.SESSION_AUTH_LOGIN_USER);
		User u = (User) SerializeUtil.unserialize(((byte[]) obj));
		return u;
	}

	public static String getCurrentUsername() {
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		return username;
	}

	public static String getUsername(Session session) {
		Object obj = session.getAttribute(GlobalConstant.SESSION_AUTH_LOGIN_USER);
		User u = (User) SerializeUtil.unserialize(((byte[]) obj));
		if (u == null) {
			return "";
		} else {
			return u.getUsername();
		}
	}
}
