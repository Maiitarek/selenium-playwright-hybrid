package com.qaframework.hooks;

import com.qaframework.drivers.DriverFactory;
import com.qaframework.utils.ScreenshotUtil;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/** BaseTest — driver lifecycle + screenshot on failure. */
public class BaseTest {
    @BeforeMethod(alwaysRun = true)
    public void setUp() { DriverFactory.initDriver(); }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE)
            ScreenshotUtil.captureAndAttach("Failure — " + result.getName());
        DriverFactory.quitDriver();
    }
}
