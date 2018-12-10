package org.tour.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tour.model.FilterDTO;

public interface TourService {

	/**
	 * Method to refresh tour list in database from remote json endpoint
	 * 
	 * @param filterDTO
	 *            Containing filter criteria
	 * @return {@link ResponseEntity}
	 */
	public ResponseEntity<HttpStatus> refreshTours(FilterDTO filterDTO);

	/**
	 * Method to fetch tour list from database based on filtering criteria
	 * 
	 * @param filter
	 *            filter criteria
	 * @return list of tour names
	 */
	public List<String> fetchTourList(String filter);
}
