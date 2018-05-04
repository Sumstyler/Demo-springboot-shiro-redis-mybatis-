package com.study.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.demo.entity.Role;
import com.study.demo.service.IRoleService;
import com.study.demo.utils.bean.BeanUtils;
import com.study.demo.vo.PageVO;
import com.study.demo.vo.PermissionVO;
import com.study.demo.vo.ResultVO;

@RestController
@RequestMapping("/api/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询角色
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResultVO getRole(@PathVariable("id") Long id) {
		Role role = roleService.getOne(id);
		return new ResultVO(role);
	}

	/**
	 * 分页查找角色
	 * 
	 * @param pagesize
	 * @param pagenum
	 * @param user
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/page/{pagesize}/{pagenum}")
	public ResultVO page(@PathVariable("pagesize") int pagesize, @PathVariable("pagenum") int pagenum,
			@RequestBody Role role) {
		PageVO page = new PageVO(pagenum, pagesize);
		Map whereMap = BeanUtils.beanToMap(role);
		roleService.page(page, whereMap);
		return new ResultVO(page);
	}

	/**
	 * 编辑角色
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/save")
	public ResultVO save(@RequestBody Role role) {
		roleService.save(role);
		return new ResultVO();
	}

	@GetMapping("/delete/{id}")
	public ResultVO delete(@PathVariable("id") Long id) {
		roleService.delete(id);
		return new ResultVO();
	}

	@GetMapping("/relate/{id}")
	public ResultVO relatePermission(@PathVariable("id") Long roleId) {
		PermissionVO result = roleService.relatePermission(roleId);
		return new ResultVO(result);
	}

	@PostMapping("/relate/{id}")
	public ResultVO relatePermission(@PathVariable("id") Long roleId, @RequestBody Long[] permissionIds) {
		roleService.relatePermission(roleId, permissionIds);
		return new ResultVO();
	}

}
