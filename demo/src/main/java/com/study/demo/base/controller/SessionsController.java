package com.study.demo.base.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.demo.constant.GlobalConstant;
import com.study.demo.entity.User;
import com.study.demo.utils.bean.SerializeUtil;
import com.study.demo.vo.OnlineUserVO;
import com.study.demo.vo.ResultVO;

@RestController
@RequestMapping("/api/sessions")
public class SessionsController {

	@Autowired
	private SessionDAO sessionDAO;

	@SuppressWarnings("rawtypes")
	@GetMapping("/online")
	public ResultVO getOnlineLoginNames() {
		List<OnlineUserVO> onlineUsers = new ArrayList<OnlineUserVO>();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		Iterator it = sessions.iterator();
		while (it.hasNext()) {
			Session session = (Session) it.next();
			Object obj = session.getAttribute(GlobalConstant.SESSION_AUTH_LOGIN_USER);
			User user = (User) SerializeUtil.unserialize(((byte[]) obj));
			if (user != null) {
				OnlineUserVO vo = new OnlineUserVO();
				vo.setSessionId(session.getId().toString());
				vo.setUsername(user.getUsername());
				vo.setIp(session.getHost());
				vo.setLoginTime(session.getStartTimestamp());
				vo.setLastVisitTime(session.getLastAccessTime());
				onlineUsers.add(vo);
			}

		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", onlineUsers);
		data.put("total", onlineUsers.size());
		return new ResultVO(data);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/kickedout/{username}")
	public ResultVO kickedout(@PathVariable(value = "username") String username) {
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		Iterator it = sessions.iterator();
		while (it.hasNext()) {
			Session session = (Session) it.next();
			Object obj = session.getAttribute(GlobalConstant.SESSION_AUTH_LOGIN_USER);
			User user = (User) SerializeUtil.unserialize(((byte[]) obj));
			if (user.getUsername().equals(username)) {
				sessionDAO.delete(session);
				break;
			}
		}
		return new ResultVO();
	}
}
