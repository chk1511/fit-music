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
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 기본 페이지는 1		
		int currentPage = (page == null ? 1 : page.intValue());
		int limit = 3;
		int startNum = (currentPage - 1) * limit + 1;
		int endNum = startNum + limit - 1;
		
		Query query = new Query();
		query.limit(limit);
		query.skip(startNum-1);
		
		List<Music> list = mongoTemplate.find(query, Music.class);
		long listCount = mongoTemplate.count(query, Music.class);
		
		// 가장 큰 페이지 번호 계산
		int maxPage = (int) ((double) listCount / limit + 0.95);
		// 시작하는 페이지 번호 계산
		int startPage = (((int) ((double) currentPage / 10 + 0.9)) - 1) * 10 + 1;
		// 마지막 페이지 번호 계산
		int endPage = maxPage;
		if (endPage > (startPage + 9)) {
			endPage = startPage + 9;
		}
		
		map.put("currentPage", currentPage);
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("list", list);
		map.put("maxPage", maxPage);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		
		return map;
	}
	
	/**
	 * 음악 추가
	 * @param input
	 */
	public Music create(Music input) {
		mongoTemplate.insert(input);
		return input;
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
