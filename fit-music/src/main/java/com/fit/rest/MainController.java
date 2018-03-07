package com.fit.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

	@RequestMapping("/")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:main");
		
		return mv;
	}
	
	@RequestMapping("/main")
	public ModelAndView main() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/front/main");
		
		return mv;
	}
}