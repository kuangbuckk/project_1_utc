package com.project.ticketBooking;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TicketBookingApplicationTests {

	private WebDriver webDriver;
	private String homePageURL = "http://localhost:4200/";

	@Before
	public void setUp() {
		webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.get(homePageURL);
	}

	@Test
	void contextLoads() {
	}

}
