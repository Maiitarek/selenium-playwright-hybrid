package com.qaframework.pages;

import com.qaframework.drivers.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** LoginPage — Sauce Demo login screen. Dual-runner (Selenium + Playwright). */
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")           private WebElement usernameField;
    @FindBy(id = "password")            private WebElement passwordField;
    @FindBy(id = "login-button")        private WebElement loginButton;
    @FindBy(css = "[data-test='error']") private WebElement errorMessage;

    @Step("Open login page")
    public LoginPage open() { navigateTo(BASE_URL); return this; }

    @Step("Enter username")
    public LoginPage enterUsername(String username) {
        if (DriverFactory.isPlaywright()) page.fill("#user-name", username);
        else { wait.until(ExpectedConditions.visibilityOf(usernameField)); usernameField.clear(); usernameField.sendKeys(username); }
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        if (DriverFactory.isPlaywright()) page.fill("#password", password);
        else { passwordField.clear(); passwordField.sendKeys(password); }
        return this;
    }

    @Step("Login as {username}")
    public InventoryPage loginAs(String username, String password) {
        enterUsername(username); enterPassword(password);
        if (DriverFactory.isPlaywright()) page.click("#login-button"); else loginButton.click();
        return new InventoryPage();
    }

    @Step("Attempt login with invalid credentials")
    public LoginPage loginWithInvalidCredentials(String username, String password) {
        enterUsername(username); enterPassword(password);
        if (DriverFactory.isPlaywright()) page.click("#login-button"); else loginButton.click();
        return this;
    }

    @Step("Get error message")
    public String getErrorMessage() {
        if (DriverFactory.isPlaywright()) return page.locator("[data-test='error']").textContent();
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }

    @Step("Is error displayed")
    public boolean isErrorDisplayed() {
        if (DriverFactory.isPlaywright()) return page.locator("[data-test='error']").isVisible();
        try { wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']"))); return true; }
        catch (Exception e) { return false; }
    }
}
