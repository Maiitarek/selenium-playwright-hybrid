package com.qaframework.drivers;

import com.microsoft.playwright.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * DriverFactory — routes to Selenium or Playwright based on -Drunner system property.
 * Default: selenium-chrome
 * Options: selenium-chrome | selenium-firefox | playwright-chromium | playwright-firefox | playwright-webkit
 */
public class DriverFactory {

    private static final ThreadLocal<WebDriver> seleniumDriver = new ThreadLocal<>();
    private static final ThreadLocal<Page> playwrightPage = new ThreadLocal<>();
    private static final ThreadLocal<Playwright> playwrightInstance = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserInstance = new ThreadLocal<>();

    public static final String RUNNER = System.getProperty("runner", "selenium-chrome");

    public static WebDriver getSeleniumDriver() { return seleniumDriver.get(); }
    public static Page getPlaywrightPage() { return playwrightPage.get(); }

    public static void initSeleniumDriver() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (RUNNER.equalsIgnoreCase("selenium-firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions opts = new FirefoxOptions();
            if (headless) opts.addArguments("-headless");
            seleniumDriver.set(new FirefoxDriver(opts));
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions opts = new ChromeOptions();
            if (headless) opts.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
            seleniumDriver.set(new ChromeDriver(opts));
        }
    }

    public static void quitSeleniumDriver() {
        if (seleniumDriver.get() != null) { seleniumDriver.get().quit(); seleniumDriver.remove(); }
    }

    public static void initPlaywrightDriver() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        Playwright pw = Playwright.create();
        playwrightInstance.set(pw);
        BrowserType.LaunchOptions opts = new BrowserType.LaunchOptions().setHeadless(headless);
        Browser browser;
        switch (RUNNER.toLowerCase()) {
            case "playwright-firefox": browser = pw.firefox().launch(opts); break;
            case "playwright-webkit":  browser = pw.webkit().launch(opts);  break;
            default:                   browser = pw.chromium().launch(opts);
        }
        browserInstance.set(browser);
        playwrightPage.set(browser.newPage());
    }

    public static void quitPlaywrightDriver() {
        if (playwrightPage.get() != null)      { playwrightPage.get().close();      playwrightPage.remove(); }
        if (browserInstance.get() != null)     { browserInstance.get().close();     browserInstance.remove(); }
        if (playwrightInstance.get() != null)  { playwrightInstance.get().close();  playwrightInstance.remove(); }
    }

    public static boolean isPlaywright() { return RUNNER.toLowerCase().startsWith("playwright"); }

    public static void initDriver()  { if (isPlaywright()) initPlaywrightDriver();  else initSeleniumDriver(); }
    public static void quitDriver()  { if (isPlaywright()) quitPlaywrightDriver();  else quitSeleniumDriver(); }
}
