package com.fit.service;

import static org.mockito.Matchers.intThat;

import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fit.entity.Music;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

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
}