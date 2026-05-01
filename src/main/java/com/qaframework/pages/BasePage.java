package com.qaframework.pages;

import com.microsoft.playwright.Page;
import com.qaframework.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/** Base class for all Page Objects. Provides unified Selenium/Playwright access. */
public abstract class BasePage {
    protected WebDriver driver;
    protected Page page;
    protected WebDriverWait wait;
    protected static final String BASE_URL = System.getProperty("baseUrl", "https://www.saucedemo.com");
    protected static final int DEFAULT_WAIT = 10;

    public BasePage() {
        if (DriverFactory.isPlaywright()) {
            this.page = DriverFactory.getPlaywrightPage();
        } else {
            this.driver = DriverFactory.getSeleniumDriver();
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT));
            PageFactory.initElements(driver, this);
        }
    }

    protected void navigateTo(String url) {
        if (DriverFactory.isPlaywright()) page.navigate(url); else driver.get(url);
    }
    protected String getTitle() {
        return DriverFactory.isPlaywright() ? page.title() : driver.getTitle();
    }
    protected String getCurrentUrl() {
        return DriverFactory.isPlaywright() ? page.url() : driver.getCurrentUrl();
    }
}
