package com.fit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
	
	public List<Music> musicList(Integer page) {
		
//		Certificate certificate = new Ce
//		
//		Query query = new Query();
//		
//		Map<String, Object> list= mongoTemplate.find(query, entityClass).limit(15);
		
//		Map<String, Object> list = new HashMap<String, Object>();
		List<Music> list = mongoTemplate.findAll(Music.class);
		
		return list;
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
