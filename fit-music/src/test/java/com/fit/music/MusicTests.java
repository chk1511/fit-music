package com.fit.music;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fit.entity.Music;
import com.fit.rest.MusicController;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = { "com.fit.*" })
@ContextConfiguration(locations="/WEB-INF/dataSource-context.xml")
public class MusicTests {
	
	@InjectMocks
	private MusicController musicController;
	private MockMvc mockMvc;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void setup() {
		
		musicController = new MusicController(mongoTemplate);
		mockMvc = MockMvcBuilders.standaloneSetup(musicController).build();
	}
	
	@Test
	public void musicList() {
		try {
			mockMvc.perform(get("/music_list?page=1"))
				.andExpect(status().isOk())
				.andExpect(view().name("/music/musicList"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void musicCreate() {
		try {
			
			String date = "2016-11-10";
			Date releaseDate = Date.valueOf(date);
			
			Music input = new Music();
			input.setAlbumTitle("LIFE IS BEAUTY FULL");
			input.setReleaseDate(releaseDate);
			input.setSinger("문문 (MoonMoon)");
			input.setSongTitle("비행운");
			
			String id = musicController.create(input);
			assertNotNull(id);
			
			mockMvc.perform(get("/music_find/"+id))
				.andExpect((model().attributeExists("data")))
				.andExpect(redirectedUrl("music_list?page=1"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}