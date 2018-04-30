package com.fit.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.entity.Music;
import com.fit.entity.Preference;
import com.fit.entity.User;
import com.fit.repository.MusicRepository;
import com.fit.repository.PreferenceRepository;
import com.fit.repository.UserRepository;

@Service
public class PreferenceService {

	@Autowired
	PreferenceRepository preferneceRepository;
	
	@Autowired
	MusicRepository musicRepository;
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * Preference List
	 * @return
	 */
	public List<Music> list() {
		
		// 선호도조사 여부가 true 인 것 모두 선택
		List<Music> data = musicRepository.findByPreference(true);
		
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
				preferneceRepository.save(l);
			}
			
			// 선호도조사가 완료되면 사용자 정보에 완료정보 추가			
			User result = userRepository.updatePreference(loginId);
			
			if(result != null){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}