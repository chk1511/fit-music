package com.fit.music;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import init.Init;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = { "com.fit.*" })
@ContextConfiguration(locations="/WEB-INF/dataSource-context.xml")
public class InitTests {
	
	@Autowired
	private Init init;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void setup() {
		
		init = new Init(mongoTemplate);
	}
	
	@Test
	public void music() {
		init.music();
		
	}
}