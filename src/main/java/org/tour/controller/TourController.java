package org.tour.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/tours")
public class TourController {

	@GetMapping
	public void getTourList() {
		
	}

	@GetMapping("/refresh")
	public void refreshTourList() {

	}

}
