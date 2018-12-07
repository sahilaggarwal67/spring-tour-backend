package org.tour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	/**
	 * Bean registered to call rest apis.
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return new RestTemplate();
	}

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
}
