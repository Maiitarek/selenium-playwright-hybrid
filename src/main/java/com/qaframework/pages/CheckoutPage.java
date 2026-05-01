package com.qaframework.pages;

import com.qaframework.drivers.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** CheckoutPage — Sauce Demo checkout flow. Dual-runner. */
public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")      private WebElement firstNameField;
    @FindBy(id = "last-name")       private WebElement lastNameField;
    @FindBy(id = "postal-code")     private WebElement postalCodeField;
    @FindBy(id = "continue")        private WebElement continueButton;
    @FindBy(id = "finish")          private WebElement finishButton;
    @FindBy(css = ".complete-header") private WebElement orderCompleteHeader;

    @Step("Is checkout loaded")
    public boolean isLoaded() {
        return DriverFactory.isPlaywright() ? page.url().contains("checkout") : driver.getCurrentUrl().contains("checkout");
    }

    @Step("Fill checkout info")
    public CheckoutPage fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        if (DriverFactory.isPlaywright()) {
            page.fill("#first-name", firstName); page.fill("#last-name", lastName); page.fill("#postal-code", postalCode);
        } else {
            wait.until(ExpectedConditions.visibilityOf(firstNameField));
            firstNameField.sendKeys(firstName); lastNameField.sendKeys(lastName); postalCodeField.sendKeys(postalCode);
        }
        return this;
    }

    @Step("Click continue")
    public CheckoutPage clickContinue() {
        if (DriverFactory.isPlaywright()) page.click("#continue"); else continueButton.click();
        return this;
    }

    @Step("Click finish")
    public CheckoutPage clickFinish() {
        if (DriverFactory.isPlaywright()) page.click("#finish");
        else { wait.until(ExpectedConditions.elementToBeClickable(finishButton)); finishButton.click(); }
        return this;
    }

    @Step("Get order complete text")
    public String getOrderCompleteText() {
        if (DriverFactory.isPlaywright()) return page.locator(".complete-header").textContent();
        wait.until(ExpectedConditions.visibilityOf(orderCompleteHeader));
        return orderCompleteHeader.getText();
    }

    @Step("Is order complete")
    public boolean isOrderComplete() {
        return DriverFactory.isPlaywright() ? page.url().contains("checkout-complete") : driver.getCurrentUrl().contains("checkout-complete");
    }
}
