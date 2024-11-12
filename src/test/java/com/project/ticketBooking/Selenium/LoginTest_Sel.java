package com.project.ticketBooking.Selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
       WebElement continueButton = webDriver.findElement(By.id("login-button-2"));
       continueButton.click();
       WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
       Alert alert = wait.until(ExpectedConditions.alertIsPresent());
       //String alertText = alert.getText();
       assertNotNull(alert, "Alert không xuất hiện khi đăng nhập mà không nhập thông tin");
       alert.accept();
       //assertThrows(NoAlertPresentException.class, () -> webDriver.switchTo().alert());
    }

    @Test
    public void loginWithUs() throws Exception {}
    @After
    public void tearDown() throws Exception {
        try {
            Thread.sleep(2000);
            webDriver.close();
            webDriver.quit();
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e);
        }
    }


}
