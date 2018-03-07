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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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

import com.fit.config.MongoConfig;
import com.fit.entity.User;
import com.fit.rest.JoinController;
import com.fit.rest.LoginController;
import com.fit.rest.MainController;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = { "com.fit.*" })
@ContextConfiguration(locations="/WEB-INF/dataSource-context.xml")
//@ContextConfiguration(classes = { MongoConfig.class })
public class FitMusicApplicationTests {

	@InjectMocks
	private MainController mainController;
	private MockMvc mainMockMvc;
	
	@InjectMocks
	private LoginController loginController;
	private MockMvc loginMockMvc;
	
	@InjectMocks
	private JoinController joinController;
	private MockMvc joinMockMvc;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void setup() {
		loginController = new LoginController(mongoTemplate);
		joinController = new JoinController(mongoTemplate);
		
		mainMockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
		loginMockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
		joinMockMvc = MockMvcBuilders.standaloneSetup(joinController).build();
		
		mongoTemplate.findAllAndRemove(new Query(), User.class);
		this.join_action();
	}

	@Test
	public void index() {

		try {
			mainMockMvc.perform(get("/"))
			.andExpect(redirectedUrl("main"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void main() {

		try {
			mainMockMvc.perform(get("/main"))
			.andExpect(view().name("/front/main"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void login() {

		try {
			loginMockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("/front/login"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void join() {

		try {
			joinMockMvc.perform(get("/join"))
			.andExpect(status().isOk())
			.andExpect(view().name("/front/join"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
			
			joinMockMvc.perform(builder)
			.andExpect(status().isOk())
			.andExpect(content().json(map.toString()));
			
			User user = mongoTemplate.findById("test", User.class);
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
			
			User user = mongoTemplate.findById(json.getString("inputId"), User.class);
			assertNotNull(user);
			assertThat(user.getPassword(), is(json.getString("inputPass")));
			
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("error", null);
			map1.put("user", user);
			
			loginMockMvc.perform(builder)
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
			
			loginMockMvc.perform(builder2)
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
			
			loginMockMvc.perform(builder3)
			.andExpect(content().json(map3.toString()))
			.andExpect(status().isOk());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void logout(){
		try {
			loginMockMvc.perform(get("/logout"))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}