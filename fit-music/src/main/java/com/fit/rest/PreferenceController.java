package com.fit.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fit.entity.Music;
import com.fit.entity.Preference;
import com.fit.service.PreferenceService;

@RestController
public class PreferenceController {
	
	@Autowired
	PreferenceService preferenceService;
	
	@RequestMapping(value="/preference", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView list(){
		
		List<Music> list = preferenceService.list();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("/preference/preferenceMake");
		
		return mv;	
	}
	
	@RequestMapping(value="/preference_create", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Boolean create(HttpServletRequest req, @RequestBody List<Preference> list) {
		return preferenceService.create(req, list);
	}
}
