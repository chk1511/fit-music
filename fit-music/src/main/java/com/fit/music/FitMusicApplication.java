package com.fit.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fit.*" })
@ContextConfiguration(locations="/WEB-INF/dataSource-context.xml")
public class FitMusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitMusicApplication.class, args);
	}
}
