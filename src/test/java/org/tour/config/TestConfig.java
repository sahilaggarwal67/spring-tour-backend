package org.tour.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.tour.repository.TourRepository;

@Configuration
@ComponentScan(basePackages = { "org.tour.controller", "org.tour.service" })
public class TestConfig {

	@Bean
	public TourRepository tourRepository() {
		return Mockito.mock(TourRepository.class);
	}

	@Bean
	public RestTemplate restTemplate() {
		return Mockito.mock(RestTemplate.class);
	}
}
