package com.fit.music;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fit.entity.User;
import com.fit.rest.LoginController;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = { "com.fit.*" })
@ContextConfiguration(locations="/WEB-INF/dataSource-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FitMusicApplicationTests {

	private LoginController loginController;
	private MockMvc mockMvc;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void setup() {
		loginController = new LoginController(mongoTemplate);
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
		
		mongoTemplate.findAllAndRemove(new Query(), User.class);
		this.Join_action();
	}

	@Test
	public void index() {

		try {
			mockMvc.perform(get("/"))
			.andExpect(redirectedUrl("main"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void main() {

		try {
			mockMvc.perform(get("/main"))
			.andExpect(view().name("/jsp/front/main"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void login() {

		try {
			mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("/jsp/front/login"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void join() {

		try {
			mockMvc.perform(get("/join"))
			.andExpect(status().isOk())
			.andExpect(view().name("/jsp/front/join"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void Join_action() {

		try {
			
			MockHttpServletRequestBuilder builder = post("/join_action")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
					.param("inputId", "test")
					.param("inputName", "test")
					.param("inputPass", "test");
			
			mockMvc.perform(builder)
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("main"));
			
			User user = loginController.user_search("id", "test");
			assertNotNull(user);
			assertThat(user.getName(), is("test"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void login_action() {
		
		try {
			
			// 로그인 성공
			JSONObject json = new JSONObject();
			json.put("inputId", "test");
			json.put("inputPass", "test");
			
			MockHttpServletRequestBuilder builder = post("/loin_action")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(json.toString());
			
			User user = this.loginController.user_search("_id", json.getString("inputId"));
			assertNotNull(user);
			assertThat(user.getPassword(), is(json.getString("inputPass")));
			
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("error", null);
			map1.put("user", user);
			
			mockMvc.perform(builder)
			.andDo(print())
			.andExpect(content().json(map1.toString()))
			.andExpect(status().isOk());
			
			
			// 존재하지 않는 아이디
			JSONObject json2 = new JSONObject();
			json2.put("inputId", "1");
			json2.put("inputPass", "1");
			
			MockHttpServletRequestBuilder builder2 = post("/loin_action")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(json2.toString());
			
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("error", "아이디가 존재하지 않습니다.");
			
			mockMvc.perform(builder2)
			.andExpect(content().json(map2.toString()))
			.andExpect(status().isOk());
			
			// 일치하지 않는 비밀번호
			JSONObject json3 = new JSONObject();
			json3.put("inputId", "test");
			json3.put("inputPass", "1");
			
			MockHttpServletRequestBuilder builder3 = post("/loin_action")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(json3.toString());
			
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("error", "비밀번호가 일치하지 않습니다.");
			
			mockMvc.perform(builder3)
			.andExpect(content().json(map3.toString()))
			.andExpect(status().isOk());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}