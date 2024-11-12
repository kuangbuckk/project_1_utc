package com.project.ticketBooking.controllers;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class OrganizationControllerTest {
    private WebDriver webDriver;

    @Before
    public void setUp() throws Exception {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


    }
}
