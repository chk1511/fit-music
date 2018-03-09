package com.fit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fit.entity.Music;

@Service
public class MusicService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public MusicService() {
		
	}
	
	public MusicService(MongoTemplate mongo) {
		mongoTemplate = mongo;
	}
	
	public Map<String, Object> musicList(Integer page) {
		
		// 기본 페이지는 1
//		page = (page == null ? 1 : page.intValue());
//		
//		int startPage = 0;
//		int 
//		
//		Criteria criteria = new Criteria();
//		
//		Query query = new Query();
//		query.limit(15);
//		query.skip(0);
//		
//		Map<String, Object> list= mongoTemplate.find(query, Music.class).limit(15);
		
//		Map<String, Object> list = new HashMap<String, Object>();
//		List<Music> list = mongoTemplate.findAll(Music.class);
		
//		List<Music> list = mongoTemplate.find(query, Music.class).limit(10);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	/**
	 * 음악 추가
	 * @param input
	 */
	public String create(Music input) {
		mongoTemplate.insert(input);
		return input.getId();
	}
	
	/**
	 * 음악 조회
	 * @param id
	 * @return
	 */
	public Music findOne(String id) {
		Music data = mongoTemplate.findById(id, Music.class);
		return data;
	}
}
