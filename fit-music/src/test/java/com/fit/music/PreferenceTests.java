package com.fit.music;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.json.JSONArray;
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
	
	@Test
	public void create() {
		try {
			
			JSONObject json1 = new JSONObject();
			json1.put("musicId", "5aa65e2f78bb4d94ac0837d9");
			json1.put("score", "5");
			
			JSONObject json2 = new JSONObject();
			json2.put("musicId", "5aa65de578bb4d94ac0837d8");
			json2.put("score", "5");
			
			JSONArray arr = new JSONArray();
			arr.put(json1);
			arr.put(json2);
			
			MockHttpServletRequestBuilder builder = post("/preference_create")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.sessionAttr("loginId", "dev")
			.content(arr.toString());
			
			mockMvc.perform(builder)
			.andExpect(status().isOk());		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}