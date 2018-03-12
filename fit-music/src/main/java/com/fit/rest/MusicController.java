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

@RestController
public class MusicController {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	MusicService musicService;

	public MusicController() {
		
	}
	
	public MusicController(MongoTemplate mongo) {
		// TODO Auto-generated constructor stub
		musicService = new MusicService(mongo);
	}
	
	@RequestMapping(value="/music_list", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView musicList(@RequestParam(name="page", required=false) Integer page) {
		
//		List<Music> list = musicService.musicList(page);
		Map<String, Object> map = musicService.musicList(page);
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("list", list);
		
		ModelAndView mv = new ModelAndView();
		mv.addAllObjects(map);
		mv.setViewName("/music/musicList");
		
		return mv;
	}
	
	@RequestMapping(value="/music_find/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView findOne(@PathVariable String id){
		
		Music data = musicService.findOne(id);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("data", data);
		mv.setViewName("redirect:music_list?page=1");
		
		return mv;
	}
	
	@RequestMapping(value="/music_write", method=RequestMethod.GET)
	public ModelAndView musicWrite(){
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/music/musicWrite");
		
		return mv;
	}
	
	@RequestMapping(value="/music_create", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Music create(@RequestBody Music input){
		return musicService.create(input);
	}
	
	
}
