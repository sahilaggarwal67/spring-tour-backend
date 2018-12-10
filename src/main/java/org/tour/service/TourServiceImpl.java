package org.tour.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.tour.entity.Tour;
import org.tour.model.FilterDTO;
import org.tour.model.ToursList;
import org.tour.repository.TourRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class TourServiceImpl implements TourService {

	private final RestTemplate restTemplate;

	private final TourRepository tourRepository;

	@Value("${tour.remoteUrl:https://s3-eu-west-1.amazonaws.com/pocketguide/_test/store_en.v2.gz}")
	private String remoteUrl;

	@Autowired
	public TourServiceImpl(RestTemplate restTemplate, TourRepository tourRepository) {
		this.restTemplate = restTemplate;
		this.tourRepository = tourRepository;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public ResponseEntity<HttpStatus> refreshTours(FilterDTO filterDTO) {
		ToursList toursList = null;
		try {
			toursList = restTemplate.getForObject(remoteUrl, ToursList.class);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (null == toursList || CollectionUtils.isEmpty(toursList.getTours())) {
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		List<Integer> filteredTourIds = new ArrayList<>();
		if (null != filterDTO && !StringUtils.isEmpty(filterDTO.getFilter())) {
			toursList
					.setTours(
							toursList.getTours().stream()
									.filter(tour -> tour.getName().toLowerCase()
											.contains(filterDTO.getFilter().toLowerCase()))
									.collect(Collectors.toList()));
			toursList.getTours().forEach(tour -> filteredTourIds.add(tour.getId()));
		}
		if (!filteredTourIds.isEmpty()) {
			List<Tour> toursToBeDeleted = tourRepository.findAll(filteredTourIds);
			tourRepository.deleteInBatch(toursToBeDeleted);
		} else {
			tourRepository.deleteAllInBatch();
		}
		tourRepository.save(toursList.getTours());
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@Override
	public List<String> fetchTourList(String filter) {
		List<Tour> tours = null;
		if (!StringUtils.isEmpty(filter)) {
			tours = tourRepository.findToursByNameLike(filter);
		} else {
			tours = tourRepository.findAll();
		}
		List<String> tourNames = new ArrayList<>();
		tours.forEach(tour -> tourNames.add(tour.getName()));
		return tourNames;
	}

}
