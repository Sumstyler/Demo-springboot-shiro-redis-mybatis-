package com.study.demo.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.demo.entity.User;
import com.study.demo.service.IUserService;
import com.study.demo.utils.bean.BeanUtils;
import com.study.demo.vo.PageVO;
import com.study.demo.vo.ResultVO;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private IUserService userService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询用户
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResultVO getUser(@PathVariable("id") Long id) {
		User user = userService.getOne(id);
		return new ResultVO(user);
	}

	/**
	 * 分页查找用户
	 * 
	 * @param pagesize
	 * @param pagenum
	 * @param user
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	@RequiresPermissions("user:manager")
	@PostMapping("/page/{pagesize}/{pagenum}")
	public ResultVO page(@PathVariable("pagesize") int pagesize, @PathVariable("pagenum") int pagenum,
			@RequestBody User user) {
		PageVO page = new PageVO(pagenum, pagesize);
		Map whereMap = BeanUtils.beanToMap(user);
		userService.page(page, whereMap);
		return new ResultVO(page);
	}

	/**
	 * 编辑用户
	 * 
	 * @param user
	 * @return
	 */
	@RequiresPermissions("user:save")
	@PostMapping("/save")
	public ResultVO save(@RequestBody User user) {
		userService.save(user);
		return new ResultVO();
	}

	/**
	 * 删除用户信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	@Transactional
	public ResultVO delete(@PathVariable("id") Long id) {
		userService.delete(id);
		return new ResultVO();
	}

	/**
	 * 关联角色 获取角色信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/relate/{id}")
	public ResultVO relateRole(@PathVariable("id") Long id) {
		Object data = userService.relateRole(id);
		return new ResultVO(data);
	}

	/**
	 * 保存关联角色信息
	 * 
	 * @param id
	 * @param roleIds
	 * @return
	 */
	@PostMapping("/relate/{id}")
	public ResultVO relateRoleInfo(@PathVariable("id") Long id, @RequestBody Long[] roleIds) {
		userService.relateRole(id, roleIds);
		return new ResultVO();
	}

}
