package com.fit.music;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fit.rest.LoginController;

@RunWith(SpringRunner.class)
@EnableRedisHttpSession
@SpringBootTest(properties="classpath:application-test.properties")
@ComponentScan(basePackages = { "com.fit.*" })
public class FitMusicApplicationTests {

	@InjectMocks
	private LoginController loginController;
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}
	
	@Test
	public void index() {
		
		try {
			mockMvc.perform(get("/"))
				   .andExpect(redirectedUrl("login"));
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
				   .andExpect(view().name("/login"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			   
	}

}
