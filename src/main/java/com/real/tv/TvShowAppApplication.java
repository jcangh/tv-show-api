package com.real.tv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class TvShowAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TvShowAppApplication.class, args);
	}

}
