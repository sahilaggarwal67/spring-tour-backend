package org.tour.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.tour.config.TestConfig;
import org.tour.constants.TestConstants;
import org.tour.model.FilterDTO;
import org.tour.service.TourService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TourControllerTest {

	@Mock
	private TourService tourService;

	@InjectMocks
	@Autowired
	private TourController tourController;

	List<String> tourNames = new ArrayList<>();
	List<String> filteredTourNames = new ArrayList<>();
	FilterDTO filterDTO = new FilterDTO();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		tourNames.add(TestConstants.TOUR1_NAME);
		tourNames.add(TestConstants.TOUR2_NAME);
		filteredTourNames.add(TestConstants.TOUR1_NAME);
		filterDTO.setFilter(TestConstants.TOUR_NAME_FILTER);
		mockServiceMethods();
	}

	@Test
	public void testRefreshToursNoFilter() {
		ResponseEntity<HttpStatus> httpStatusResponse = tourController.refreshTourList(null);
		assertNotNull(httpStatusResponse);
		assertEquals(HttpStatus.OK, httpStatusResponse.getStatusCode());
	}

	@Test
	public void testRefreshToursFilter() {
		ResponseEntity<HttpStatus> httpStatusResponse = tourController.refreshTourList(filterDTO);
		assertNotNull(httpStatusResponse);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpStatusResponse.getStatusCode());
	}

	@Test
	public void testTourListNoFilter() {
		List<String> tourList = tourController.getTourList(null);
		assertNotNull(tourList);
		assertEquals(tourList.size(), 2);
		assertEquals(tourList.contains(TestConstants.TOUR1_NAME), true);
		assertEquals(tourList.contains(TestConstants.TOUR2_NAME), true);
	}

	@Test
	public void testTourListFilter() {
		List<String> tourList = tourController.getTourList(TestConstants.TOUR_NAME_FILTER);
		assertNotNull(tourList);
		assertEquals(tourList.size(), 1);
		assertEquals(tourList.contains(TestConstants.TOUR1_NAME), true);
		assertEquals(tourList.contains(TestConstants.TOUR2_NAME), false);
	}

	public void mockServiceMethods() {
		when(tourService.refreshTours(null)).thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.OK));
		when(tourService.refreshTours(filterDTO))
				.thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR));
		when(tourService.fetchTourList(null)).thenReturn(tourNames);
		when(tourService.fetchTourList(TestConstants.TOUR_NAME_FILTER)).thenReturn(filteredTourNames);
	}
}
