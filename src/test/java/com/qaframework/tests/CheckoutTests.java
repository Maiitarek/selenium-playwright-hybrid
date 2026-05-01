package com.qaframework.tests;

import com.qaframework.hooks.BaseTest;
import com.qaframework.pages.CartPage;
import com.qaframework.pages.CheckoutPage;
import com.qaframework.pages.InventoryPage;
import com.qaframework.pages.LoginPage;
import com.qaframework.utils.TestData;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Sauce Demo E2E") @Feature("Checkout")
public class CheckoutTests extends BaseTest {

    @Test(description = "Complete full purchase flow")
    @Story("Full checkout") @Severity(SeverityLevel.CRITICAL)
    public void testCompleteCheckoutFlow() {
        InventoryPage inv = new LoginPage().open().loginAs(TestData.STANDARD_USER, TestData.PASSWORD);
        Assert.assertTrue(inv.isLoaded());
        inv.addProductToCart(TestData.PRODUCT_BACKPACK);
        Assert.assertEquals(inv.getCartCount(), 1);
        CartPage cart = inv.goToCart();
        Assert.assertTrue(cart.isLoaded());
        Assert.assertEquals(cart.getCartItemCount(), 1);
        CheckoutPage checkout = cart.proceedToCheckout();
        Assert.assertTrue(checkout.isLoaded());
        checkout.fillCheckoutInfo(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.POSTAL_CODE)
                .clickContinue().clickFinish();
        Assert.assertTrue(checkout.isOrderComplete());
        Assert.assertTrue(checkout.getOrderCompleteText().contains("Thank you"));
    }

    @Test(description = "Cart is empty by default")
    @Story("Empty cart") @Severity(SeverityLevel.NORMAL)
    public void testCartIsEmptyByDefault() {
        InventoryPage inv = new LoginPage().open().loginAs(TestData.STANDARD_USER, TestData.PASSWORD);
        CartPage cart = inv.goToCart();
        Assert.assertTrue(cart.isLoaded());
        Assert.assertTrue(cart.isCartEmpty());
    }
}
