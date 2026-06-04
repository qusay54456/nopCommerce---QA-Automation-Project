package com.nopcommerce.tests;

import com.nopcommerce.base.BaseTest;
import com.nopcommerce.pages.HomePage;
import com.nopcommerce.pages.LoginPage;
import com.nopcommerce.pages.RegisterPage;
import com.nopcommerce.utilities.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private String[] registerFreshUser() {
        String email    = TestDataGenerator.uniqueEmail("login");
        String password = TestDataGenerator.DEFAULT_PASSWORD;

        RegisterPage register = new HomePage(driver).goToRegister();
        register.registerNewUser("Login", "User", email, password);
        register.clickContinue();

        new HomePage(driver).clickLogout();
        navigateToHome();

        return new String[]{email, password};
    }

    @Test(description = "TC_LOGIN_001 - Login with valid credentials",
          groups = {"regression", "smoke"})
    public void verifyLoginWithValidCredentials() {
        String[] creds = registerFreshUser();

        LoginPage login = new HomePage(driver).goToLogin();
        login.login(creds[0], creds[1]);

        Assert.assertTrue(new HomePage(driver).isUserLoggedIn(),
                "TC_LOGIN_001: User should be logged in after valid login");
    }

    @Test(description = "TC_LOGIN_002 - Login with wrong password",
          groups = {"regression"})
    public void verifyLoginWithWrongPassword() {
        String[] creds = registerFreshUser();

        LoginPage login = new HomePage(driver).goToLogin();
        login.login(creds[0], "WrongPass999");

        Assert.assertTrue(login.isLoginErrorDisplayed(),
                "TC_LOGIN_002: Error should appear for wrong password");
        Assert.assertTrue(login.getLoginErrorMessage().contains("Login was unsuccessful"),
                "TC_LOGIN_002: Expected 'Login was unsuccessful' message");
    }

    @Test(description = "TC_LOGIN_003 - Login with non-existing email",
          groups = {"regression"})
    public void verifyLoginWithNonExistingEmail() {
        LoginPage login = new HomePage(driver).goToLogin();
        login.login(TestDataGenerator.uniqueEmail("notexist"),
                    TestDataGenerator.DEFAULT_PASSWORD);

        Assert.assertTrue(login.isLoginErrorDisplayed(),
                "TC_LOGIN_003: Error should appear for non-existing email");
    }

    @Test(description = "TC_LOGIN_004 - Login with empty email and password",
          groups = {"regression"})
    public void verifyLoginWithEmptyFields() {
        LoginPage login = new HomePage(driver).goToLogin();
        login.clickLogin();

        Assert.assertFalse(login.getEmailFieldError().isEmpty(),
                "TC_LOGIN_004: Validation error should appear for empty email");
    }

    @Test(description = "TC_LOGIN_005 - Logout from an authenticated session",
          groups = {"regression"})
    public void verifyLogout() {
        String[] creds = registerFreshUser();

        LoginPage login = new HomePage(driver).goToLogin();
        login.login(creds[0], creds[1]);

        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isUserLoggedIn(),
                "TC_LOGIN_005: Pre-condition failed — user not logged in");

        home.clickLogout();

        Assert.assertTrue(new HomePage(driver).isUserLoggedOut(),
                "TC_LOGIN_005: User should be logged out");
    }
}