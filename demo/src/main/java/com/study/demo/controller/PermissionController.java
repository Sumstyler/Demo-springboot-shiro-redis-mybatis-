package com.study.demo.controller;

import java.util.List;
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

import com.study.demo.entity.Permission;
import com.study.demo.entity.User;
import com.study.demo.service.IPermissionService;
import com.study.demo.utils.bean.ApplicationUtil;
import com.study.demo.utils.bean.BeanUtils;
import com.study.demo.vo.MenuVO;
import com.study.demo.vo.PageVO;
import com.study.demo.vo.ResultVO;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

	@Autowired
	private IPermissionService permissionService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询角色
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResultVO getPermission(@PathVariable("id") Long id) {
		Permission permission = permissionService.getOne(id);
		return new ResultVO(permission);
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
			@RequestBody Permission permission) {
		PageVO page = new PageVO(pagenum, pagesize);
		Map whereMap = BeanUtils.beanToMap(permission);
		permissionService.page(page, whereMap);
		return new ResultVO(page);
	}

	/**
	 * 编辑角色
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/save")
	public ResultVO save(@RequestBody Permission permission) {
		permissionService.save(permission);
		return new ResultVO();
	}

	@GetMapping("/menu")
	public ResultVO getMenu() {
		// 获取当前登录用户
		User user = ApplicationUtil.getCurrentUser();
		List<MenuVO> result = permissionService.menu(user.getId());
		return new ResultVO(result);
	}

	@GetMapping("/delete/{id}")
	public ResultVO delete(@PathVariable("id") Long id) {
		permissionService.delete(id);
		return new ResultVO();
	}

}
