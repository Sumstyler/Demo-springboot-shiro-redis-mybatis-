package com.study.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.demo.constant.GlobalConstant;
import com.study.demo.constant.PageConstant;
import com.study.demo.constant.ResourceTypeEnum;
import com.study.demo.constant.ValidEnum;
import com.study.demo.dao.PermissionDao;
import com.study.demo.dao.RolePermissionDao;
import com.study.demo.dao.UserRoleDao;
import com.study.demo.entity.Permission;
import com.study.demo.entity.RolePermission;
import com.study.demo.entity.UserRole;
import com.study.demo.service.IPermissionService;
import com.study.demo.vo.MenuVO;
import com.study.demo.vo.PageVO;

@Service
public class PermissionService implements IPermissionService {

	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RolePermissionDao rolePermissionDao;
	@Autowired
	private UserRoleDao userRoleDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Permission getOne(Long id) {
		return permissionDao.getOne(id);
	}

	@SuppressWarnings("rawtypes")
	public List<Permission> list(Map whereMap) {
		return permissionDao.list(whereMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageVO page(PageVO page, Map whereMap) {
		whereMap.put(PageConstant.PAGE_START, page.getStart());
		whereMap.put(PageConstant.PAGE_SIZE, page.getPageSize());
		int total = permissionDao.total(whereMap);
		List<Permission> rows = new ArrayList<Permission>();
		if (total > 0) {
			rows = list(whereMap);
		}
		page.setRows(rows);
		page.setTotal(total);
		return page;
	}

	@Override
	public int save(Permission entity) {
		Long id = entity.getId();
		if (id == null) {
			return permissionDao.insert(entity);
		} else {
			return permissionDao.update(entity);
		}
	}

	public List<MenuVO> menu(Long userId) {
		List<MenuVO> menus = new ArrayList<MenuVO>();
		List<Permission> permissions = getPermissionByUserid(userId, ResourceTypeEnum.MENU);
		Long bootNode = GlobalConstant.PERMISSION_ROOT_NODE_ID;
		for (Permission p : permissions) {
			if (bootNode.equals(p.getParentid())) {
				menus.add(buildTree(p.getId(), permissions));
			}
		}
		return menus;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Permission> getPermissionByUserid(Long userId, ResourceTypeEnum resourceType) {
		List<Permission> result = new ArrayList<Permission>();
		Map whereMap = new HashMap();
		// 获取用户与角色关系信息
		whereMap.put("userId", userId);
		List<UserRole> userRoles = userRoleDao.list(whereMap);
		if (userRoles.size() < 1) {
			return result;
		}
		List<Long> roleIds = new ArrayList<Long>();
		for (UserRole ur : userRoles) {
			roleIds.add(ur.getRoleId());
		}
		whereMap.clear();
		// 获取角色与权限信息
		whereMap.put("roleIds", roleIds);
		List<RolePermission> rolePermissions = rolePermissionDao.list(whereMap);
		if (rolePermissions.size() < 1) {
			return result;
		}
		List<Long> permissionIds = new ArrayList<Long>();
		for (RolePermission rp : rolePermissions) {
			permissionIds.add(rp.getPermissionId());
		}
		whereMap.clear();
		// 查询当前用户的权限菜单信息
		whereMap.put("permissionIds", permissionIds);
		whereMap.put("valid", ValidEnum.Y.toString());
		if (resourceType != null) {
			whereMap.put("type", resourceType.toString());
		}
		result = permissionDao.list(whereMap);
		return result;
	}

	private MenuVO buildTree(Long id, List<Permission> permissions) {
		MenuVO vo = new MenuVO();
		vo.setId(id);
		// 获取节点信息
		for (Permission p : permissions) {
			if (p.getId().equals(id)) {
				vo.setTitle(p.getName());
				vo.setPath(p.getPath());
				vo.setIcon(p.getIcon());
			}
		}

		List<MenuVO> childrens = new ArrayList<MenuVO>();
		for (Permission p : permissions) {
			if (id.equals(p.getParentid())) {
				MenuVO pvo = new MenuVO(p.getId(), p.getName(), p.getPath(), p.getIcon());
				childrens.add(pvo);
			}
		}
		for (MenuVO v : childrens) {
			MenuVO menuVO = buildTree(v.getId(), permissions);
			if (vo.getChild() == null) {
				vo.setChild(new ArrayList<MenuVO>());
			}
			vo.getChild().add(menuVO);
		}
		return vo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public int delete(Long id) {
		int result = permissionDao.delete(id);
		Map whereMap = new HashMap();
		whereMap.put("permissionId", id);
		rolePermissionDao.deleteByMap(whereMap);
		return result;
	}
}
