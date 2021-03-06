package com.study.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.demo.entity.Permission;

@Mapper
@SuppressWarnings("rawtypes")
public interface PermissionDao {

	Permission getOne(Long id);

	List<Permission> list(Map map);

	int total(Map map);

	int insert(Permission entity);

	int update(Permission entity);

	int delete(Long id);
}
