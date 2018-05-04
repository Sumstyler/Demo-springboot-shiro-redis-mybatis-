package com.study.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.demo.constant.GlobalConstant;
import com.study.demo.constant.PageConstant;
import com.study.demo.constant.ValidEnum;
import com.study.demo.dao.RoleDao;
import com.study.demo.dao.UserDao;
import com.study.demo.dao.UserRoleDao;
import com.study.demo.entity.Role;
import com.study.demo.entity.User;
import com.study.demo.entity.UserRole;
import com.study.demo.service.IUserService;
import com.study.demo.utils.bean.ApplicationUtil;
import com.study.demo.vo.PageVO;
import com.study.demo.vo.RoleVO;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
@CacheConfig(cacheNames = "users")
public class UserService implements IUserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings({ "rawtypes" })
	public List<User> list(Map whereMap) {
		return userDao.list(whereMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageVO page(PageVO page, Map whereMap) {
		whereMap.put(PageConstant.PAGE_START, page.getStart());
		whereMap.put(PageConstant.PAGE_SIZE, page.getPageSize());
		int total = userDao.total(whereMap);
		List<User> rows = new ArrayList<User>();
		if (total > 0) {
			rows = list(whereMap);
		}
		page.setRows(rows);
		page.setTotal(total);
		return page;
	}

	@Override
	@Cacheable
	public User getOne(Long id) {
		System.out.println("调用getOne方法");
		return userDao.getOne(id);
	}

	@Override
	public int save(User entity) {
		Long id = entity.getId();
		User u = ApplicationUtil.getCurrentUser();
		if (id == null) {
			String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
			entity.setSalt(salt);
			SimpleHash hash = new SimpleHash(GlobalConstant.HASH_ALGORITHM_NAME, entity.getTel().substring(5),
					entity.getUsername() + salt, GlobalConstant.HASH_ITERATIONS);
			entity.setPassword(hash.toHex());
			entity.setCreater(u.getId());
			return userDao.insert(entity);
		} else {
			entity.setUpdater(u.getId());
			return userDao.update(entity);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public User getUserByUserName(String username) {
		Map whereMap = new HashMap();
		whereMap.put("username", username);
		whereMap.put("valid", ValidEnum.Y.toString());
		List<User> list = userDao.list(whereMap);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<RoleVO> relateRole(Long uid) {
		List<RoleVO> result = new ArrayList<RoleVO>();
		Map whereMap = new HashMap();
		whereMap.put("userId", uid);
		List<UserRole> userRoles = userRoleDao.list(whereMap);
		whereMap.clear();
		whereMap.put("valid", ValidEnum.Y.toString());
		List<Role> roles = roleDao.list(whereMap);
		for (Role role : roles) {
			RoleVO roleVO = new RoleVO();
			roleVO.setId(role.getId());
			roleVO.setName(role.getName());
			for (UserRole ur : userRoles) {
				if (ur.getRoleId().equals(role.getId())) {
					roleVO.setChecked(true);
				}
			}
			result.add(roleVO);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public int delete(Long id) {
		int result = userDao.delete(id);
		Map whereMap = new HashMap();
		whereMap.put("userId", id);
		userRoleDao.deleteByMap(whereMap);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public Boolean relateRole(Long uid, Long[] roleIds) {
		// 删除原有关联关系
		Map whereMap = new HashMap();
		whereMap.put("userId", uid);
		userRoleDao.deleteByMap(whereMap);
		// 创建新的关联关系
		for (Long roleId : roleIds) {
			UserRole entity = new UserRole();
			entity.setUserId(uid);
			entity.setRoleId(roleId);
			userRoleDao.insert(entity);
		}
		return true;
	}
}
