package com.oyenavneet.playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

//for demo hand-on - to stop spring-boot to load all package and convert into beans
// - scanBasePackages - instruct sprint-boot to scan only given package like sec01 etc.


@SpringBootApplication(scanBasePackages = "com.oyenavneet.playground.${sec}")
@EnableR2dbcRepositories(basePackages = "com.oyenavneet.playground.${sec}")
public class WebfluxPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxPlaygroundApplication.class, args);
	}

}
