package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;

public class LoginTest extends BaseTest {

    // ── Helper: register a fresh account and return credentials ──
    private String[] registerNewUser() {
        String email = "logintest" + System.currentTimeMillis() + "@test.com";
        String password = "Test@123";

        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registerUser("Login", "User", email, password);
        registerPage.clickContinue();

        // Logout after registration
        homePage = new HomePage(driver);
        homePage.clickLogout();

        return new String[]{email, password};
    }

    @Test(priority = 1, description = "TC-009: Login with valid credentials")
    public void testLoginWithValidCredentials() {
        String[] credentials = registerNewUser();

        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(credentials[0], credentials[1]);

        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLoggedIn(),
                "User should be logged in and 'My account' link should be visible");
    }

    @Test(priority = 2, description = "TC-010: Login with wrong password")
    public void testLoginWithWrongPassword() {
        String[] credentials = registerNewUser();

        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(credentials[0], "WrongPass999");

        Assert.assertTrue(loginPage.isLoginErrorDisplayed(),
                "Error message should be displayed for wrong password");

        String errorMsg = loginPage.getLoginErrorMessage();
        Assert.assertTrue(errorMsg.contains("Login was unsuccessful"),
                "Error should contain 'Login was unsuccessful' but got: " + errorMsg);
    }

    @Test(priority = 3, description = "TC-011: Login with non-existing email")
    public void testLoginWithNonExistingEmail() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("notexist" + System.currentTimeMillis() + "@test.com", "Test@123");

        Assert.assertTrue(loginPage.isLoginErrorDisplayed(),
                "Error message should be displayed for non-existing email");
    }

    @Test(priority = 4, description = "TC-012: Login with empty email and password")
    public void testLoginWithEmptyFields() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");
        loginPage.clickLogin();

        String error = loginPage.getEmailValidationError();
        Assert.assertFalse(error.isEmpty(),
                "Validation error should be displayed for empty email");
    }

    @Test(priority = 5, description = "TC-013: Logout from authenticated session")
    public void testLogout() {
        String[] credentials = registerNewUser();

        // Login
        HomePage homePage = new HomePage(driver);
        homePage.clickLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(credentials[0], credentials[1]);

        // Verify logged in
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLoggedIn(), "User should be logged in");

        // Logout
        homePage.clickLogout();

        // Verify logged out
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLoggedOut(),
                "'Register' and 'Log in' links should be visible after logout");
    }
}
