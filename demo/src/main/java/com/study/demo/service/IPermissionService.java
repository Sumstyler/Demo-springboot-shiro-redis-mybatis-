package com.study.demo.service;

import java.util.List;
import java.util.Map;

import com.study.demo.constant.ResourceTypeEnum;
import com.study.demo.entity.Permission;
import com.study.demo.vo.MenuVO;
import com.study.demo.vo.PageVO;

public interface IPermissionService {

	Permission getOne(Long id);

	@SuppressWarnings("rawtypes")
	List<Permission> list(Map whereMap);

	@SuppressWarnings("rawtypes")
	PageVO page(PageVO page, Map whereMap);

	int save(Permission entity);

	List<MenuVO> menu(Long userId);

	int delete(Long id);

	List<Permission> getPermissionByUserid(Long userId, ResourceTypeEnum resourceType);
}
