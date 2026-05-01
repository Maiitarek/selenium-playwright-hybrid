package com.qaframework.utils;

import com.qaframework.drivers.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;

/** Captures screenshots for Selenium and Playwright, attaches to Allure. */
public class ScreenshotUtil {
    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);

    public static void captureAndAttach(String name) {
        try {
            byte[] screenshot = DriverFactory.isPlaywright()
                ? DriverFactory.getPlaywrightPage().screenshot()
                : ((TakesScreenshot) DriverFactory.getSeleniumDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            log.warn("Screenshot failed: {}", e.getMessage());
        }
    }
}
