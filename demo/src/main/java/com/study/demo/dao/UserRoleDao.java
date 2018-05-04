package com.study.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.demo.entity.UserRole;

@Mapper
@SuppressWarnings("rawtypes")
public interface UserRoleDao {

	UserRole getOne(Long id);

	List<UserRole> list(Map map);

	int total(Map map);

	int insert(UserRole entity);

	int update(UserRole entity);

	int delete(Long id);

	int deleteByMap(Map map);
}
