package com.qaframework.pages;

import com.qaframework.drivers.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.stream.Collectors;

/** InventoryPage — Sauce Demo products page. Dual-runner. */
public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_item_name")    private List<WebElement> productNames;
    @FindBy(css = ".shopping_cart_badge")    private WebElement cartBadge;
    @FindBy(css = ".product_sort_container") private WebElement sortDropdown;

    @Step("Is inventory loaded")
    public boolean isLoaded() {
        return DriverFactory.isPlaywright() ? page.url().contains("inventory") : driver.getCurrentUrl().contains("inventory");
    }

    @Step("Get product count")
    public int getProductCount() {
        if (DriverFactory.isPlaywright()) return (int) page.locator(".inventory_item_name").count();
        wait.until(ExpectedConditions.visibilityOfAllElements(productNames));
        return productNames.size();
    }

    @Step("Get product names")
    public List<String> getProductNames() {
        if (DriverFactory.isPlaywright()) return page.locator(".inventory_item_name").allTextContents();
        wait.until(ExpectedConditions.visibilityOfAllElements(productNames));
        return productNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    @Step("Add first product to cart")
    public InventoryPage addFirstProductToCart() {
        if (DriverFactory.isPlaywright()) page.locator(".btn_inventory").first().click();
        else { List<WebElement> btns = driver.findElements(By.cssSelector(".btn_inventory")); if (!btns.isEmpty()) btns.get(0).click(); }
        return this;
    }

    @Step("Add product: {productName}")
    public InventoryPage addProductToCart(String productName) {
        String id = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        if (DriverFactory.isPlaywright()) page.locator("[data-test='" + id + "']").click();
        else driver.findElement(By.cssSelector("[data-test='" + id + "']")).click();
        return this;
    }

    @Step("Get cart count")
    public int getCartCount() {
        try {
            if (DriverFactory.isPlaywright()) return Integer.parseInt(page.locator(".shopping_cart_badge").textContent().trim());
            return Integer.parseInt(cartBadge.getText().trim());
        } catch (Exception e) { return 0; }
    }

    @Step("Sort by: {sortOption}")
    public InventoryPage sortProductsBy(String sortOption) {
        if (DriverFactory.isPlaywright()) page.selectOption(".product_sort_container", sortOption);
        else { wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)); new Select(sortDropdown).selectByValue(sortOption); }
        return this;
    }

    @Step("Go to cart")
    public CartPage goToCart() {
        if (DriverFactory.isPlaywright()) page.click(".shopping_cart_link");
        else driver.findElement(By.cssSelector(".shopping_cart_link")).click();
        return new CartPage();
    }
}
