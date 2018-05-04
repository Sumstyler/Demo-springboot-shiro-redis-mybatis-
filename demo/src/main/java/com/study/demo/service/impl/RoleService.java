package com.study.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.demo.constant.GlobalConstant;
import com.study.demo.constant.PageConstant;
import com.study.demo.constant.ValidEnum;
import com.study.demo.dao.PermissionDao;
import com.study.demo.dao.RoleDao;
import com.study.demo.dao.RolePermissionDao;
import com.study.demo.dao.UserRoleDao;
import com.study.demo.entity.Permission;
import com.study.demo.entity.Role;
import com.study.demo.entity.RolePermission;
import com.study.demo.entity.User;
import com.study.demo.service.IRoleService;
import com.study.demo.utils.bean.ApplicationUtil;
import com.study.demo.vo.PageVO;
import com.study.demo.vo.PermissionVO;
import com.study.demo.vo.TreeVO;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RolePermissionDao rolePermissionDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Role getOne(Long id) {
		return roleDao.getOne(id);
	}

	@SuppressWarnings({ "rawtypes" })
	public List<Role> list(Map whereMap) {
		return roleDao.list(whereMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageVO page(PageVO page, Map whereMap) {
		whereMap.put(PageConstant.PAGE_START, page.getStart());
		whereMap.put(PageConstant.PAGE_SIZE, page.getPageSize());
		int total = roleDao.total(whereMap);
		List<Role> rows = new ArrayList<Role>();
		if (total > 0) {
			rows = list(whereMap);
		}
		page.setRows(rows);
		page.setTotal(total);
		return page;
	}

	@Override
	public int save(Role entity) {
		Long id = entity.getId();
		User user = ApplicationUtil.getCurrentUser();
		if (id == null) {
			entity.setCreater(user.getId());
			return roleDao.insert(entity);
		} else {
			entity.setUpdater(user.getId());
			return roleDao.update(entity);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PermissionVO relatePermission(Long roleId) {
		Map whereMap = new HashMap();
		whereMap.put("roleId", roleId);
		List<RolePermission> rolePermissions = rolePermissionDao.list(whereMap);
		List<Long> checks = new ArrayList<Long>();
		for (RolePermission rp : rolePermissions) {
			checks.add(rp.getPermissionId());
			// 如果包含的有子节点被勾选 则父节点不再视为选中
		}
		whereMap.clear();
		whereMap.put("valid", ValidEnum.Y.toString());
		List<Permission> permissions = permissionDao.list(whereMap);
		TreeVO treeVO = buildTree(GlobalConstant.PERMISSION_ROOT_NODE_ID, permissions);
		PermissionVO result = new PermissionVO();
		result.setTree(treeVO);
		result.setChecks(checks);
		return result;
	}

	private TreeVO buildTree(Long id, List<Permission> permissions) {
		TreeVO vo = new TreeVO();
		vo.setId(id);
		// 获取节点信息
		for (Permission p : permissions) {
			if (p.getId().equals(id)) {
				vo.setLabel(p.getName());
				break;
			}
		}

		List<TreeVO> childrens = new ArrayList<TreeVO>();
		for (Permission p : permissions) {
			if (id.equals(p.getParentid())) {
				TreeVO pvo = new TreeVO(p.getId(), p.getName());
				childrens.add(pvo);
			}
		}
		for (TreeVO v : childrens) {
			TreeVO treeVO = buildTree(v.getId(), permissions);
			vo.getChildren().add(treeVO);
		}
		return vo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public int delete(Long id) {
		int result = roleDao.delete(id);
		Map whereMap = new HashMap();
		whereMap.put("roleId", id);
		userRoleDao.deleteByMap(whereMap);
		rolePermissionDao.deleteByMap(whereMap);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public Boolean relatePermission(Long id, Long[] permissionIds) {
		// 删除原有关联关系
		Map whereMap = new HashMap();
		whereMap.put("roleId", id);
		rolePermissionDao.deleteByMap(whereMap);
		// 新增新的关联关系
		for (Long pId : permissionIds) {
			RolePermission rp = new RolePermission();
			rp.setRoleId(id);
			rp.setPermissionId(pId);
			rolePermissionDao.insert(rp);
		}

		return true;
	}
}
