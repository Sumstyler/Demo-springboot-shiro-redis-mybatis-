package com.study.demo.service;

import java.util.List;
import java.util.Map;

import com.study.demo.entity.User;
import com.study.demo.vo.PageVO;
import com.study.demo.vo.RoleVO;

public interface IUserService {

	User getOne(Long id);

	User getUserByUserName(String username);

	@SuppressWarnings("rawtypes")
	List<User> list(Map whereMap);

	@SuppressWarnings("rawtypes")
	PageVO page(PageVO page, Map whereMap);

	int save(User entity);

	int delete(Long id);

	List<RoleVO> relateRole(Long uid);

	Boolean relateRole(Long uid, Long[] roleIds);
}
