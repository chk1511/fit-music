package com.fit.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fit.entity.User;
import com.fit.service.JoinService;
import com.fit.service.LoginService;

@RestController
public class JoinController {
	
	@Autowired
	JoinService joinService;
	
	public JoinController() {
		
	}
	
	public JoinController(MongoTemplate mongo) {
		// TODO Auto-generated constructor stub
		joinService = new JoinService(mongo);
	}

	@RequestMapping("/join")
	public ModelAndView join() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/front/join");
		
		return mv;
	}
	
	@RequestMapping(value="/id_check/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Boolean idCheck(@PathVariable String id) {
		return joinService.idCheck(id);
	}
	
	@RequestMapping(value="/join_action", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> joinAction(@RequestBody User input) {
		Map<String, Object> result = joinService.joinAction(input);
		return result;
	}
	
}