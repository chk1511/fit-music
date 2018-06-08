package com.fit.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fit.entity.User;
import com.fit.service.JoinService;

@RestController
public class JoinController {
	
	@Autowired
	JoinService joinService;

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