package com.project.ticketBooking.Selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class LoginTest_Sel {
    private WebDriver webDriver;
    private String homePageURL = "http://localhost:4200/";

    @Before
    public void setUp() throws Exception {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.navigate().to(homePageURL);
    }

    @Test
    public void loginNoUsernameAndPassword() throws Exception {
       WebElement loginButton = webDriver.findElement(By.id("login-button"));
       loginButton.click();
       WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
       wait.until(ExpectedConditions.urlToBe("http://localhost:4200/login"));

    }


}
