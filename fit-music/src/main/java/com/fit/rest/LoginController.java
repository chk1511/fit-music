package com.fit.rest;

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
	private MongoTemplate mongoTemplete;
	
	@RequestMapping("/")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:login");
		
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsp/login");
		
		return mv;
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public ModelAndView joinView() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsp/join");
		
		return mv;
	}
	
	@RequestMapping(value="/join_action", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public Boolean joinAction(@RequestBody User input) {
		
		mongoTemplete.insert(input);
		
		return true;
	}
	
	@RequestMapping(value="/user_find", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public User findUser(@RequestParam String id) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		
		return mongoTemplete.findOne(query, User.class);
	}

	@RequestMapping(value="/login_action", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView loginAction(@RequestParam String inputId, @RequestParam String inputPass) {
		
		User user = mongoTemplete.findById(inputId, User.class);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		mv.setViewName("/main");
		
		return mv;
	}
}