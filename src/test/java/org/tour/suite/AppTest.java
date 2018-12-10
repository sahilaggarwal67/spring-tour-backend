package org.tour.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.tour.controller.TourControllerTest;
import org.tour.service.TourServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TourControllerTest.class, TourServiceTest.class })
public class AppTest {

}