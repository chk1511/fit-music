package com.fit.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fit.entity.User;

@RestController
public class LoginController {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public LoginController() {
		
	}
	
	public LoginController(MongoTemplate mongo) {
		// TODO Auto-generated constructor stub
		mongoTemplate = mongo;
	}
	
	@RequestMapping("/")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:main");
		
		return mv;
	}
	
	@RequestMapping("/main")
	public ModelAndView main() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsp/front/main");
		
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsp/front/login");
		
		return mv;
	}
	
	@RequestMapping("/join")
	public ModelAndView join() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsp/front/join");
		
		return mv;
	}
	
	@RequestMapping(value="/join_action", method=RequestMethod.POST)
	public ModelAndView join_action(HttpServletRequest request) {
		
		User user = new User();
		user.setId(request.getParameter("inputId"));
		user.setName(request.getParameter("inputName"));
		user.setPassword(request.getParameter("inputPass"));

		mongoTemplate.insert(user);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		mv.setViewName("redirect:main");
		
		return mv;
	}
	
	@RequestMapping(value="/loin_action", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> loin_action(HttpServletRequest request, @RequestParam String inputId, @RequestParam String inputPass) {
		
		HttpSession session =request.getSession();
		Map<String, Object> map = null;
		String error = null;
		
		User user = this.user_search("_id", inputId);
		
		if(user != null){
			if(user.getPassword().equals(inputPass)){
				error = "비밀번호가 일치하지 않습니다.";
			}else{
				// 로그인 성공
			    session.setAttribute("user", user);
				session.setAttribute("loginId", user.getId());
				session.setAttribute("loginName", user.getName());
				session.setAttribute("loginPhone", user.getPassword());
				
				map.put("user", user);
			}
		}else{
			error = "아이디가 존재하지 않습니다.";
		}
		map.put("error", error);
		
		return map;
	}
	
	public User user_search(String key, String value){
		
		Criteria criteria = new Criteria(key);
		criteria.is(value);
		
		User user = mongoTemplate.findOne(new Query(criteria), User.class);
		
		return user;
	}
}