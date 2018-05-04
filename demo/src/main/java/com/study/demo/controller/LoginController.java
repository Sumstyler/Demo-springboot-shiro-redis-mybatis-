package com.study.demo.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.demo.constant.ApplicationEnum;
import com.study.demo.constant.GlobalConstant;
import com.study.demo.entity.User;
import com.study.demo.service.IUserService;
import com.study.demo.utils.bean.SerializeUtil;
import com.study.demo.vo.ResultVO;
import com.study.demo.vo.UserVO;

/**
 * @Description: 登录相关的Controller
 * @author xiewm
 * @date 2017年12月4日 下午2:40:11
 * 
 */
@RestController
@RequestMapping("/api/user/")
public class LoginController {

	@Autowired
	private IUserService userService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/login")
	public ResultVO login() {
		return new ResultVO(ApplicationEnum.UNLOGIN);
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 * @return ResponseEntity<Object>
	 */
	@PostMapping("/login")
	public ResultVO login(@RequestBody Map<String, String> map) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String username = map.get("username");
			String password = map.get("password");
			if (!subject.isAuthenticated()) {
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				subject.login(token);
				// 存放用户信息到session
				User user = userService.getUserByUserName(username);
				Subject currentUser = SecurityUtils.getSubject();
				Session session = currentUser.getSession();
				logger.info("写入当前用户到session中");
				session.setAttribute(GlobalConstant.SESSION_AUTH_LOGIN_USER, SerializeUtil.serialize(user));
				UserVO userInfo = new UserVO();
				userInfo.setName(user.getName());
				userInfo.setAvatar("https://avatars0.githubusercontent.com/u/16893554?v=3&s=240");
				return new ResultVO(true, ApplicationEnum.LOGIN_SUCCESS, userInfo);
			}
			return new ResultVO(true, ApplicationEnum.LOGIN_SUCCESS);
		} catch (AuthenticationException e) {
			logger.error(e.getMessage());
			return new ResultVO(ApplicationEnum.LOGIN_FAIL);
		}
	}

	/**
	 * 注销
	 * 
	 * @return void
	 */
	@GetMapping("/logout")
	public ResultVO logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return new ResultVO(true, ApplicationEnum.LOGOUT);
	}

	@GetMapping("/403")
	public String unauthorized() {
		return "403";
	}
}
