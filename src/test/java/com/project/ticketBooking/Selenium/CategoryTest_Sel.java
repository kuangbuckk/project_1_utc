package com.project.ticketBooking.Selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryTest_Sel {
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
    public void getAllCategories_returnsListOfCategories() throws Exception {

        // Mở trang chứa danh sách danh mục
        webDriver.get("http://localhost:4200");

        // Lấy danh sách các phần tử có class là `category-item`
        List<WebElement> categoryItems = webDriver.findElements(By.className("category-item"));

        // Kiểm tra danh sách không rỗng
        assertTrue(categoryItems.size() > 0, "Danh sách danh mục không được rỗng");

        // So sánh số lượng danh mục mong đợi và thực tế (ví dụ mong đợi 2 danh mục)
        assertEquals(6, categoryItems.size(), "Số lượng danh mục không đúng");

        // Kiểm tra nội dung danh mục (tuỳ vào nội dung hiển thị mà điều chỉnh kiểm tra này)
        for (WebElement category : categoryItems) {
            assertTrue(category.getText().length() > 0, "Tên danh mục không được để trống");
        }
    }

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
