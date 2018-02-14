package com.fit.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@ComponentScan(basePackages = { "com.fit.*" })
public class FitMusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitMusicApplication.class, args);
	}
}
