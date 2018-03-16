package com.fit.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fit.entity.Music;
import com.fit.service.MusicService;
import com.fit.service.PreferenceService;
import com.mongodb.WriteResult;

@RestController
public class PreferenceController {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	PreferenceService preferenceService;

	public PreferenceController() {
		
	}
	
	public PreferenceController(MongoTemplate mongo) {
		// TODO Auto-generated constructor stub
		preferenceService = new PreferenceService(mongo);
	}
	
	@RequestMapping(value="/preference", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView list(){
		
		List<Music> list = preferenceService.list();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("/preference/preferenceMake");
		
		return mv;
	}
}
