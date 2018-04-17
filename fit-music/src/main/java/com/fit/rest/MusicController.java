package com.fit.rest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fit.entity.Music;
import com.fit.service.MusicService;
import com.mongodb.WriteResult;

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
		
		Music music = new Music();
		music.setReleaseDate(new Date());
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("music", music);
		mv.setViewName("/music/musicWrite");
		
		return mv;
	}
	
	@RequestMapping(value="/music_create", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public Boolean create(HttpServletRequest request, Music input, @RequestParam("file") MultipartFile file){		
		return musicService.create(request, input, file);
	}
	
	@RequestMapping(value="/music_update/{id}", method=RequestMethod.GET)
	public ModelAndView update(@PathVariable String id, @RequestParam(name="page", required=false) Integer page){
		
		Music data = musicService.findOne(id);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("data", data);
		mv.addObject("currentPage", page);
		mv.setViewName("/music/musicUpdate");
		
		return mv;
	}
	
	@RequestMapping(value="/music_update", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public WriteResult update(HttpServletRequest request, Music input, @RequestParam("file") MultipartFile file){
		return musicService.update(request, input, file);
	}
}
