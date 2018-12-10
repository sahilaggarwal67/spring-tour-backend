package org.tour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tour.model.FilterDTO;
import org.tour.service.TourService;

@RestController
public class TourController {

	@Autowired
	TourService tourService;

	@GetMapping(value = "/tours")
	public List<String> getTourList(@RequestParam(required = false) String filter) {
		return tourService.fetchTourList(filter);
	}

	@PostMapping(value = "/tours/refresh")
	public ResponseEntity<HttpStatus> refreshTourList(@RequestBody(required = false) FilterDTO filterDTO) {
		return tourService.refreshTours(filterDTO);
	}

}
