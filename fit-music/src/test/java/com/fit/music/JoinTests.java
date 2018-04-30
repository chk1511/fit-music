package com.fit.music;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fit.entity.User;
import com.fit.rest.JoinController;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = { "com.fit.*" })
public class JoinTests {
	
	@InjectMocks
	private JoinController joinController;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(joinController).build();
	}
	
	@Test
	public void join() {
		try {
			mockMvc.perform(get("/id_check/test"))
			.andExpect(status().isOk())
			.andExpect(view().name("/front/join"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void idCheck() {
		
		try {
			mockMvc.perform(get("/id_check/test"))
			.andExpect(content().string("false"))
			.andExpect(status().isOk());
			
//			mockMvc.perform(get("/id_check/nothing"))
//			.andExpect(content().string("true"))
//			.andExpect(status().isOk());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void join_action() {

		try {
			
			JSONObject json = new JSONObject();
			json.put("id", "test");
			json.put("name", "test");
			json.put("password", "test");
			
			MockHttpServletRequestBuilder builder = post("/join_action")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(json.toString());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", json);
			
			mockMvc.perform(builder)
			.andExpect(status().isOk())
			.andExpect(content().json(map.toString()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}