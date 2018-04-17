package com.fit.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fit.entity.Music;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

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
		int limit = 15;
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
	public Boolean create(HttpServletRequest request, Music input, MultipartFile file) {
		
		try {
			
			// 시대 값 넣어줌
			input.setPeriod(this.getPeriod(input.getReleaseDate()));
			
			int fileSize = 5 * 1024 * 1024;
			
			String rootPath = this.getClass().getResource("../../../").getPath();
			String savePath = "static/img/music/";
			String fileName = file.getOriginalFilename();
			String filePath = rootPath+savePath+fileName;
			
			File newFile = new File(filePath);
			
			// 파일이 이미 존재할 경우
			if(newFile.exists()){
				if (newFile.isDirectory()) {
	                throw new IOException("File '" + file + "' exists but is a directory");
	            }
	            if (newFile.canWrite() == false) {
	                throw new IOException("File '" + file + "' cannot be written to");
	            }
			}else{
				File parent = newFile.getParentFile();
	            if (parent != null) {
	                if (!parent.mkdirs() && !parent.isDirectory()) {
	                    throw new IOException("Directory '" + parent + "' could not be created");
	                }
	            }
	            file.transferTo(newFile);
			}
			
			input.setImgFileName(file.getOriginalFilename());
			input.setImgPath(newFile.getParent().trim());
			
//			MultipartRequest multi = new MultipartRequest(request, savePath, fileSize, "utf-8", new DefaultFileRenamePolicy());
//			input.setImgPath(newFile.getParent().trim()+newFile.getName().trim());
			
//			String imgPath = multi.getFilesystemName((String) multi.getFileNames().nextElement());
//			input.setImgPath(imgPath);
//			
			mongoTemplate.insert(input);
			
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}
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
	
	public WriteResult update(HttpServletRequest request, Music input, MultipartFile file){
		
		try {
			
			if(!file.getOriginalFilename().isEmpty()){

				String rootPath = this.getClass().getResource("../../../").getPath();
				String savePath = "static/img/music/";
				String fileName = file.getOriginalFilename();
				String filePath = rootPath+savePath+fileName;
				
				File newFile = new File(filePath);
				
				// 파일이 이미 존재할 경우
				if(!newFile.exists()){
					File parent = newFile.getParentFile();
		            if (parent != null) {
		                if (!parent.mkdirs() && !parent.isDirectory()) {
		                    throw new IOException("Directory '" + parent + "' could not be created");
		                }
		            }
		            file.transferTo(newFile);
				}
				input.setImgFileName(file.getOriginalFilename());
				input.setImgPath("/img/music/"+file.getOriginalFilename());
			}
			
			input.setPeriod(this.getPeriod(input.getReleaseDate()));
			
			Criteria criteria = new Criteria("id");
			criteria.is(input.getId());
			
			Query query = new Query(criteria);
			
			DBObject dbOjc = new BasicDBObject();
			mongoTemplate.getConverter().write(input, dbOjc);
			Update update = new Update().fromDBObject(dbOjc);
			
			return mongoTemplate.updateFirst(query, update, Music.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 발매날짜를 기준으로 시대 구분
	 * @param releaseDate
	 * @return
	 */
	private String getPeriod(Date releaseDate) {
		if(releaseDate != null){
			
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = transFormat.format(releaseDate);
			int dateInt = Integer.parseInt(dateStr.substring(0,4));
			String period;
			
			if(dateInt >= 2010){
				period = "2010";
			}else if(dateInt < 2010 && dateInt >= 2000){
				period = "2000";
			}else if(dateInt < 2000 && dateInt >= 1990){
				period = "1990";
			}else{
				period = "1980";
			}
			
			return period;
		}else{
			return null;
		}
	}
}
