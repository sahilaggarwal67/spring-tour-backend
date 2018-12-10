package org.tour.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.tour.config.TestConfig;
import org.tour.constants.TestConstants;
import org.tour.entity.Tour;
import org.tour.model.FilterDTO;
import org.tour.model.ToursList;
import org.tour.repository.TourRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TourServiceTest {

	@Autowired
	private TourRepository tourRepository;

	@Autowired
	private RestTemplate restTemplate;

	@InjectMocks
	@Autowired
	private TourService tourService;

	List<String> tourNames = new ArrayList<>();
	List<Tour> tourList = new ArrayList<>();
	List<String> filteredTourNames = new ArrayList<>();
	List<Tour> filteredTourList = new ArrayList<>();
	FilterDTO filterDTO = new FilterDTO();
	ToursList toursList = new ToursList();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		tourList.add(new Tour(TestConstants.TOUR1_ID, TestConstants.TOUR1_NAME, TestConstants.TOUR1_LONG_DESC));
		tourList.add(new Tour(TestConstants.TOUR2_ID, TestConstants.TOUR2_NAME, TestConstants.TOUR2_LONG_DESC));
		tourNames.add(TestConstants.TOUR1_NAME);
		tourNames.add(TestConstants.TOUR2_NAME);
		filteredTourList.add(new Tour(TestConstants.TOUR1_ID, TestConstants.TOUR1_NAME, TestConstants.TOUR1_LONG_DESC));
		filteredTourNames.add(TestConstants.TOUR1_NAME);
		filterDTO.setFilter(TestConstants.TOUR_NAME_FILTER);
		toursList.setTours(tourList);
		mockServiceMethods();
	}

	@Test
	public void testRefreshToursNoFilter() {
		ResponseEntity<HttpStatus> httpStatusResponse = tourService.refreshTours(null);
		assertNotNull(httpStatusResponse);
		assertEquals(HttpStatus.OK, httpStatusResponse.getStatusCode());
	}

	@Test
	public void testRefreshToursFilter() {
		ResponseEntity<HttpStatus> httpStatusResponse = tourService.refreshTours(filterDTO);
		assertNotNull(httpStatusResponse);
		assertEquals(HttpStatus.OK, httpStatusResponse.getStatusCode());
	}

	@Test
	public void testTourListNoFilter() {
		List<String> tourList = tourService.fetchTourList(null);
		assertNotNull(tourList);
		assertEquals(tourList.size(), 2);
		assertEquals(tourList.contains(TestConstants.TOUR1_NAME), true);
		assertEquals(tourList.contains(TestConstants.TOUR2_NAME), true);
	}

	@Test
	public void testTourListFilter() {
		List<String> tourList = tourService.fetchTourList(TestConstants.TOUR_NAME_FILTER);
		assertNotNull(tourList);
		assertEquals(tourList.size(), 1);
		assertEquals(tourList.contains(TestConstants.TOUR1_NAME), true);
		assertEquals(tourList.contains(TestConstants.TOUR2_NAME), false);
	}

	public void mockServiceMethods() {
		when(restTemplate.getForObject(Matchers.anyString(), Matchers.anyObject())).thenReturn(toursList);
		when(tourRepository.findAll()).thenReturn(tourList);
		when(tourRepository.findToursByNameLike(TestConstants.TOUR_NAME_FILTER)).thenReturn(filteredTourList);
	}
}
