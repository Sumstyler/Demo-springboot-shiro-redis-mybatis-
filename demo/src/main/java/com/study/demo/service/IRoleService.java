package com.study.demo.service;

import java.util.List;
import java.util.Map;

import com.study.demo.entity.Role;
import com.study.demo.vo.PageVO;
import com.study.demo.vo.PermissionVO;

public interface IRoleService {

	Role getOne(Long id);

	@SuppressWarnings("rawtypes")
	List<Role> list(Map whereMap);

	@SuppressWarnings("rawtypes")
	PageVO page(PageVO page, Map whereMap);

	int save(Role entity);

	int delete(Long id);

	PermissionVO relatePermission(Long id);

	Boolean relatePermission(Long id, Long[] permissionIds);
}
