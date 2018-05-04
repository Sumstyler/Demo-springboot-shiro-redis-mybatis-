package com.study.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.demo.entity.RolePermission;

@Mapper
@SuppressWarnings("rawtypes")
public interface RolePermissionDao {

	RolePermission getOne(Long id);

	List<RolePermission> list(Map map);

	int total(Map map);

	int insert(RolePermission entity);

	int update(RolePermission entity);

	int delete(Long id);

	int deleteByMap(Map map);
}
