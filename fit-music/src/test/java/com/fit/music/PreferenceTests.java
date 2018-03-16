package com.fit.music;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fit.entity.Music;
import com.fit.rest.MusicController;
import com.fit.rest.PreferenceController;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = { "com.fit.*" })
@ContextConfiguration(locations="/WEB-INF/dataSource-context.xml")
public class PreferenceTests {
	
	@InjectMocks
	private PreferenceController preferenceController;
	private MockMvc mockMvc;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void setup() {
		preferenceController = new PreferenceController(mongoTemplate);
		mockMvc = MockMvcBuilders.standaloneSetup(preferenceController).build();
	}
	
	@Test
	public void list() {
		try {
			mockMvc.perform(get("/preference"))
				.andExpect(status().isOk())
				.andExpect(view().name("/preference/preferenceMake"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}