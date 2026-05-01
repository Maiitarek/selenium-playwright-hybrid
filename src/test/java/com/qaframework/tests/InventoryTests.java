package com.qaframework.tests;

import com.qaframework.hooks.BaseTest;
import com.qaframework.pages.CartPage;
import com.qaframework.pages.InventoryPage;
import com.qaframework.pages.LoginPage;
import com.qaframework.utils.TestData;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

@Epic("Sauce Demo E2E") @Feature("Inventory")
public class InventoryTests extends BaseTest {

    private InventoryPage login() {
        return new LoginPage().open().loginAs(TestData.STANDARD_USER, TestData.PASSWORD);
    }

    @Test(description = "Inventory shows 6 products")
    @Story("Products") @Severity(SeverityLevel.CRITICAL)
    public void testInventoryShowsAllProducts() {
        Assert.assertEquals(login().getProductCount(), TestData.TOTAL_PRODUCT_COUNT);
    }

    @Test(description = "Product names are visible")
    @Story("Products") @Severity(SeverityLevel.NORMAL)
    public void testProductNamesVisible() {
        List<String> names = login().getProductNames();
        Assert.assertFalse(names.isEmpty());
        Assert.assertTrue(names.contains(TestData.PRODUCT_BACKPACK));
    }

    @Test(description = "Add to cart increments badge")
    @Story("Add to cart") @Severity(SeverityLevel.CRITICAL)
    public void testAddProductIncrementsCartBadge() {
        InventoryPage inv = login();
        inv.addFirstProductToCart();
        Assert.assertEquals(inv.getCartCount(), 1);
    }

    @Test(description = "Added product appears in cart")
    @Story("Add to cart") @Severity(SeverityLevel.CRITICAL)
    public void testAddedProductAppearsInCart() {
        InventoryPage inv = login();
        inv.addProductToCart(TestData.PRODUCT_BACKPACK);
        CartPage cart = inv.goToCart();
        Assert.assertTrue(cart.isLoaded());
        Assert.assertEquals(cart.getCartItemCount(), 1);
        Assert.assertTrue(cart.getCartItemNames().contains(TestData.PRODUCT_BACKPACK));
    }

    @Test(description = "Products sort A to Z")
    @Story("Sort") @Severity(SeverityLevel.NORMAL)
    public void testSortProductsAZ() {
        InventoryPage inv = login();
        inv.sortProductsBy(TestData.SORT_AZ);
        Assert.assertEquals(inv.getProductNames().get(0), TestData.PRODUCT_BACKPACK);
    }
}
