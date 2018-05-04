package com.study.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.demo.entity.Role;

@Mapper
@SuppressWarnings("rawtypes")
public interface RoleDao {

	Role getOne(Long id);

	List<Role> list(Map map);

	int total(Map map);

	int insert(Role entity);

	int update(Role entity);

	int delete(Long id);

}
