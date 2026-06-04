package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;

public class RegistrationTest extends BaseTest {

    // ── Helper: generate unique email to avoid conflicts ──
    private String generateEmail() {
        return "testuser" + System.currentTimeMillis() + "@test.com";
    }

    @Test(priority = 1, description = "TC-001: Register with valid data")
    public void testRegisterWithValidData() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registerUser("John", "Doe", generateEmail(), "Test@123");

        Assert.assertEquals(registerPage.getResultMessage(), "Your registration completed");
    }

    @Test(priority = 2, description = "TC-002: Register with already existing email")
    public void testRegisterWithExistingEmail() {
        // Step 1: Register a new account
        String email = generateEmail();
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registerUser("John", "Doe", email, "Test@123");
        registerPage.clickContinue();

        // Step 2: Logout
        homePage = new HomePage(driver);
        homePage.clickLogout();

        // Step 3: Try to register with the same email
        homePage.clickRegister();
        registerPage = new RegisterPage(driver);
        registerPage.registerUser("Jane", "Smith", email, "Test@456");

        Assert.assertEquals(registerPage.getExistingEmailError(), "The specified email already exists");
    }

    @Test(priority = 3, description = "TC-003: Register with empty First Name")
    public void testRegisterWithEmptyFirstName() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterLastName("Doe");
        registerPage.enterEmail(generateEmail());
        registerPage.enterPassword("Test@123");
        registerPage.enterConfirmPassword("Test@123");
        registerPage.clickRegister();

        Assert.assertEquals(registerPage.getFirstNameError(), "First name is required.");
    }

    @Test(priority = 4, description = "TC-004: Register with empty Last Name")
    public void testRegisterWithEmptyLastName() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterFirstName("John");
        registerPage.enterEmail(generateEmail());
        registerPage.enterPassword("Test@123");
        registerPage.enterConfirmPassword("Test@123");
        registerPage.clickRegister();

        Assert.assertEquals(registerPage.getLastNameError(), "Last name is required.");
    }

    @Test(priority = 5, description = "TC-005: Register with empty Email")
    public void testRegisterWithEmptyEmail() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterPassword("Test@123");
        registerPage.enterConfirmPassword("Test@123");
        registerPage.clickRegister();

        Assert.assertEquals(registerPage.getEmailError(), "Email is required.");
    }

    @Test(priority = 6, description = "TC-006: Register with invalid email format")
    public void testRegisterWithInvalidEmail() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("invalid-email-format");
        registerPage.enterPassword("Test@123");
        registerPage.enterConfirmPassword("Test@123");
        registerPage.clickRegister();

        Assert.assertEquals(registerPage.getEmailError(), "Wrong email");
    }

    @Test(priority = 7, description = "TC-007: Register with password shorter than 6 characters")
    public void testRegisterWithShortPassword() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail(generateEmail());
        registerPage.enterPassword("123");
        registerPage.enterConfirmPassword("123");
        registerPage.clickRegister();

        String error = registerPage.getPasswordError();
        Assert.assertTrue(error.contains("at least 6 characters"),
                "Expected password length error but got: " + error);
    }

    @Test(priority = 8, description = "TC-008: Register with mismatched passwords")
    public void testRegisterWithMismatchedPasswords() {
        HomePage homePage = new HomePage(driver);
        homePage.clickRegister();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail(generateEmail());
        registerPage.enterPassword("Test@123");
        registerPage.enterConfirmPassword("Different@456");
        registerPage.clickRegister();

        Assert.assertEquals(registerPage.getConfirmPasswordError(),
                "The password and confirmation password do not match.");
    }
}
