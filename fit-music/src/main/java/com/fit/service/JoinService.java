package com.fit.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fit.entity.User;

@Service
public class JoinService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public JoinService() {
	}
	
	public JoinService(MongoTemplate mongo) {
		mongoTemplate = mongo;
	}
	
	/**
	 * 아이디 중복검사
	 * @param input
	 * @return
	 */
	public Boolean idCheck(String id){
		
		User user = mongoTemplate.findById(id, User.class);
		
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
		
		User user = mongoTemplate.findById(input.getId(), User.class);
		
		if(user != null){
			error = "같은 아이디가 존재합니다.";
			map.put("error", error);
		}else{
			mongoTemplate.insert(input);
			map.put("user", input);
		}
		return map;
	}
}