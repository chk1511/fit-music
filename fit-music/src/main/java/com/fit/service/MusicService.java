package com.fit.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fit.entity.Music;
import com.fit.entity.Preference;
import com.fit.repository.MusicRepository;

@Service
public class MusicService {

	@Autowired
	MusicRepository musicRepository;
	
	public Map<String, Object> musicList(Integer page) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 기본 페이지는 1		
		int currentPage = (page == null ? 1 : page.intValue());
		int limit = 15;
		int startNum = (currentPage - 1) * limit + 1;
		int endNum = startNum + limit - 1;
		
		PageRequest pageRequest = new PageRequest(currentPage, limit, null);
		Page<Music> list = musicRepository.findByPage(pageRequest);
		
		int listCount  = list.getSize();
		
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
	 * 추천 음악 리스트 가져오는 로직
	 * @return
	 */
//	public Map<String, Object> recommendation(HttpServletRequest req, Integer page) {
//		
//		// 페이징 처리
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		HttpSession session = req.getSession();
//		String loginId = (String) session.getAttribute("loginId");
//		
//		if(loginId == null){
//			return null;
//		}
//		
//		// 1. 사용자가 고른 음악리스트 선별
//		Criteria criteria = new Criteria("user_id");
//		criteria.is(loginId);
//		
//		Query query = new Query(criteria);
//		query.fields().include("music_id");
//		
//		List<Preference> data = mongoTemplate.find(query, Preference.class);
//		
//		// 2. 사용자가 고른 음악을 고른 다른 사용자들을 추출
//		BasicDBObject obj = new BasicDBObject();
//		for(Preference p : data){
//			obj.put("music_id", new BasicDBObject("$eq", p.getMusicId()));
//		}
//		
//		List<Preference> user = mongoTemplate.find(query, Preference.class);
//		
//		map.put("user", user);
//		
//		return map;
//	}
	
	/**
	 * 음악 추가
	 * @param input
	 */
	public Music create(HttpServletRequest request, Music input, MultipartFile file) {
		
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
			Music result = musicRepository.save(input);
			
			return result;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
	
	/**
	 * 음악 조회
	 * @param id
	 * @return
	 */
	public Music findOne(int id) {
		Music data = musicRepository.findOne(id);
		return data;
	}
	
	public Boolean update(HttpServletRequest request, Music input, MultipartFile file){
		
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
			
			mongoTemplate.updateFirst(query, update, Music.class);
			
			musicRepository.update
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
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
