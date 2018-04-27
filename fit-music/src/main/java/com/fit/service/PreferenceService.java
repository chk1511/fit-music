package com.fit.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fit.entity.Music;
import com.fit.entity.Preference;
import com.fit.entity.User;

@Service
public class PreferenceService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public PreferenceService() {
		
	}
	
	public PreferenceService(MongoTemplate mongo) {
		mongoTemplate = mongo;
	}
	
	/**
	 * Preference List
	 * @return
	 */
	public List<Music> list() {
		
		// 선호도조사 여부가 true 인 것 모두 선택
		Criteria criteria = new Criteria("preferenceTf");
		criteria.is(true);
		
		Query query = new Query(criteria);
		
		List<Music> data = mongoTemplate.find(query, Music.class);
		
		return data;
	}
	
	/**
	 * Create Preference
	 * @param req
	 * @param list
	 * @return
	 */
	public Boolean create(HttpServletRequest req, List<Preference> list) {
		
		HttpSession session = req.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		if(loginId != null){
			
			for( Preference l : list){
				l.setUserId(loginId);
				mongoTemplate.insert(l);
			}
			
			// 선호도조사가 완료되면 사용자 정보에 완료정보 추가
			Criteria criteria = new Criteria("id");
			criteria.is(loginId);
			
			Query query = new Query(criteria);
			
			Update update = new Update();
			update.set("preference_tf", true);
			
			mongoTemplate.updateFirst(query, update, User.class);
			
			return true;
		}else{
			return false;
		}
	}
}