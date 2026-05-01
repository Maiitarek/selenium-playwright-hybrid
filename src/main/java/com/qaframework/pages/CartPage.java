package com.qaframework.pages;

import com.qaframework.drivers.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.stream.Collectors;

/** CartPage — Sauce Demo shopping cart. Dual-runner. */
public class CartPage extends BasePage {

    @FindBy(css = ".cart_item_label .inventory_item_name") private List<WebElement> cartItemNames;
    @FindBy(id = "checkout")          private WebElement checkoutButton;
    @FindBy(id = "continue-shopping") private WebElement continueShoppingButton;

    @Step("Is cart loaded")
    public boolean isLoaded() {
        return DriverFactory.isPlaywright() ? page.url().contains("cart") : driver.getCurrentUrl().contains("cart");
    }

    @Step("Get cart item count")
    public int getCartItemCount() {
        if (DriverFactory.isPlaywright()) return (int) page.locator(".cart_item").count();
        return driver.findElements(By.cssSelector(".cart_item")).size();
    }

    @Step("Get cart item names")
    public List<String> getCartItemNames() {
        if (DriverFactory.isPlaywright()) return page.locator(".cart_item_label .inventory_item_name").allTextContents();
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItemNames));
        return cartItemNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    @Step("Is cart empty")
    public boolean isCartEmpty() { return getCartItemCount() == 0; }

    @Step("Proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        if (DriverFactory.isPlaywright()) page.click("#checkout");
        else { wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)); checkoutButton.click(); }
        return new CheckoutPage();
    }

    @Step("Continue shopping")
    public InventoryPage continueShopping() {
        if (DriverFactory.isPlaywright()) page.click("#continue-shopping"); else continueShoppingButton.click();
        return new InventoryPage();
    }
}
