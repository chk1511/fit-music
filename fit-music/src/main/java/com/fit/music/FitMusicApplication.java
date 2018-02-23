package com.fit.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fit.*" })
public class FitMusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitMusicApplication.class, args);
	}
}
