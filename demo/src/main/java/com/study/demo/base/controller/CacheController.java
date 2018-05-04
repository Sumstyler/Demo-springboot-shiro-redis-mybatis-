package com.study.demo.base.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.demo.config.support.UserRealm;
import com.study.demo.vo.ResultVO;

@RestController
@RequestMapping("/api/cache")
public class CacheController {
	@Resource
	private UserRealm userRealm;

	@GetMapping("/clear")
	public ResultVO clearCached() {
		userRealm.clearCached();
		return new ResultVO();
	}

	@GetMapping("/clearAll")
	public ResultVO clearAllShiroCached() {
		userRealm.clearAllCached();
		return new ResultVO();
	}
}
