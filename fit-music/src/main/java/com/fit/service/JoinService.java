package com.fit.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.entity.User;
import com.fit.repository.UserRepository;

@Service
public class JoinService {
	
	@Autowired
	UserRepository userRepository ;
	
	/**
	 * 아이디 중복검사
	 * @param input
	 * @return
	 */
	public Boolean idCheck(String id){
		
		User user = userRepository.findOne(id);
		
		if(user != null){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 회원가입
	 * @param input
	 * @return
	 */
	public Map<String, Object> joinAction(User input){
		
		String error = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userRepository.findOne(input.getId());
		
		if(user != null){
			error = "같은 아이디가 존재합니다.";
			map.put("error", error);
		}else{
			// 처음 가입시 기본은 선호도조사 false 상태이다
			input.setPreference(false);
			
			userRepository.save(input);
			
			map.put("user", input);
		}
		return map;
	}
}