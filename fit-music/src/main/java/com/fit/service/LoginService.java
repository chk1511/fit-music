package com.fit.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.entity.User;
import com.fit.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	JoinService joinService;
	
	/**
	 * 로그인
	 * @param request
	 * @param input
	 * @return
	 */
	public Map<String, Object> loginAction(HttpServletRequest request, User input){
		
		HttpSession session =request.getSession();
		Map<String, Object> map = new HashMap<String, Object>();
		String error = null;
		
		User user = userRepository.findOne(input.getId());
		
		if(user != null){
			if(user.getPassword().equals(input.getPassword())){
				// 로그인 성공
			    session.setAttribute("user", user);
				session.setAttribute("loginId", user.getId());
				session.setAttribute("loginName", user.getName());
				session.setAttribute("loginPhone", user.getPassword());
				map.put("user", user);
			}else{
				// 로그인 실패
				error = "비밀번호가 일치하지 않습니다.";
			}
		}else{
			error = "아이디가 존재하지 않습니다.";
		}
		map.put("error", error);
		
		return map;
	}
	
	/**
	 * 로그아웃
	 * @param request
	 */
	public void logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
	}
}
