package com.qaframework.tests;

import com.qaframework.hooks.BaseTest;
import com.qaframework.pages.InventoryPage;
import com.qaframework.pages.LoginPage;
import com.qaframework.utils.TestData;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Sauce Demo E2E") @Feature("Login")
public class LoginTests extends BaseTest {

    @Test(description = "Valid user logs in successfully")
    @Story("Happy path") @Severity(SeverityLevel.CRITICAL)
    public void testValidLoginRedirectsToInventory() {
        InventoryPage page = new LoginPage().open().loginAs(TestData.STANDARD_USER, TestData.PASSWORD);
        Assert.assertTrue(page.isLoaded(), "Expected inventory page after login");
    }

    @Test(description = "Locked out user sees error")
    @Story("Locked out") @Severity(SeverityLevel.CRITICAL)
    public void testLockedOutUserSeesError() {
        LoginPage page = new LoginPage().open().loginWithInvalidCredentials(TestData.LOCKED_OUT_USER, TestData.PASSWORD);
        Assert.assertTrue(page.isErrorDisplayed());
        Assert.assertTrue(page.getErrorMessage().contains("locked out"));
    }

    @Test(description = "Invalid credentials show error")
    @Story("Invalid credentials") @Severity(SeverityLevel.NORMAL)
    public void testInvalidCredentialsShowError() {
        LoginPage page = new LoginPage().open().loginWithInvalidCredentials(TestData.INVALID_USER, TestData.INVALID_PASSWORD);
        Assert.assertTrue(page.isErrorDisplayed());
        Assert.assertTrue(page.getErrorMessage().contains("do not match"));
    }

    @Test(description = "Empty username shows error")
    @Story("Validation") @Severity(SeverityLevel.MINOR)
    public void testEmptyUsernameShowsError() {
        LoginPage page = new LoginPage().open().loginWithInvalidCredentials(TestData.EMPTY_STRING, TestData.PASSWORD);
        Assert.assertTrue(page.isErrorDisplayed());
    }
}
