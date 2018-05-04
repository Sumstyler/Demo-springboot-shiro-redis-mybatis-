package com.study.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: TODO(描述类的作用)
 * @author xiewm
 * @date 2017年12月4日 下午3:58:32
 * 
 */
@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/index")
	public String index() {
		return "index";
	}
}
