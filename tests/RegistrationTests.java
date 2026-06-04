package com.nopcommerce.tests;

import com.nopcommerce.base.BaseTest;
import com.nopcommerce.pages.HomePage;
import com.nopcommerce.pages.RegisterPage;
import com.nopcommerce.utilities.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTests extends BaseTest {

    @Test(description = "TC_REG_001 - Register with valid data",
          groups = {"regression", "smoke"})
    public void verifyRegisterWithValidData() {
        RegisterPage register = new HomePage(driver).goToRegister();
        register.registerNewUser("John", "Doe",
                TestDataGenerator.uniqueEmail(),
                TestDataGenerator.DEFAULT_PASSWORD);

        Assert.assertEquals(register.getResultMessage(), "Your registration completed",
                "TC_REG_001: Registration did not complete successfully");
    }

    @Test(description = "TC_REG_002 - Register with an already existing email",
          groups = {"regression"})
    public void verifyRegisterWithExistingEmail() {
        String email = TestDataGenerator.uniqueEmail();

        RegisterPage register = new HomePage(driver).goToRegister();
        register.registerNewUser("John", "Doe", email, TestDataGenerator.DEFAULT_PASSWORD);
        register.clickContinue();

        new HomePage(driver).clickLogout();
        navigateToHome();

        register = new HomePage(driver).goToRegister();
        register.registerNewUser("Jane", "Smith", email, "Test@456");

        Assert.assertEquals(register.getExistingEmailError(),
                "The specified email already exists",
                "TC_REG_002: Duplicate email was not rejected");
    }

    @Test(description = "TC_REG_003 - Register with empty First Name",
          groups = {"regression"})
    public void verifyRegisterWithEmptyFirstName() {
        RegisterPage register = new HomePage(driver).goToRegister();
        register.enterLastName("Doe")
                .enterEmail(TestDataGenerator.uniqueEmail())
                .enterPassword(TestDataGenerator.DEFAULT_PASSWORD)
                .enterConfirmPassword(TestDataGenerator.DEFAULT_PASSWORD)
                .clickRegister();

        Assert.assertEquals(register.getFirstNameError(), "First name is required.",
                "TC_REG_003: First name validation error not shown");
    }

    @Test(description = "TC_REG_004 - Register with empty Last Name",
          groups = {"regression"})
    public void verifyRegisterWithEmptyLastName() {
        RegisterPage register = new HomePage(driver).goToRegister();
        register.enterFirstName("John")
                .enterEmail(TestDataGenerator.uniqueEmail())
                .enterPassword(TestDataGenerator.DEFAULT_PASSWORD)
                .enterConfirmPassword(TestDataGenerator.DEFAULT_PASSWORD)
                .clickRegister();

        Assert.assertEquals(register.getLastNameError(), "Last name is required.",
                "TC_REG_004: Last name validation error not shown");
    }

    @Test(description = "TC_REG_005 - Register with empty Email",
          groups = {"regression"})
    public void verifyRegisterWithEmptyEmail() {
        RegisterPage register = new HomePage(driver).goToRegister();
        register.enterFirstName("John")
                .enterLastName("Doe")
                .enterPassword(TestDataGenerator.DEFAULT_PASSWORD)
                .enterConfirmPassword(TestDataGenerator.DEFAULT_PASSWORD)
                .clickRegister();

        Assert.assertEquals(register.getEmailError(), "Email is required.",
                "TC_REG_005: Email validation error not shown");
    }

    @Test(description = "TC_REG_006 - Register with invalid email format",
          groups = {"regression"})
    public void verifyRegisterWithInvalidEmail() {
        RegisterPage register = new HomePage(driver).goToRegister();
        register.enterFirstName("John")
                .enterLastName("Doe")
                .enterEmail("invalid-email-format")
                .enterPassword(TestDataGenerator.DEFAULT_PASSWORD)
                .enterConfirmPassword(TestDataGenerator.DEFAULT_PASSWORD)
                .clickRegister();

        String error = register.getEmailError();
        Assert.assertTrue(
                error.contains("valid email") || error.contains("Wrong email") || !error.isEmpty(),
                "TC_REG_006: Expected email validation error but got: '" + error + "'");
    }

    @Test(description = "TC_REG_007 - Register with password shorter than 6 characters",
          groups = {"regression"})
    public void verifyRegisterWithShortPassword() {
        RegisterPage register = new HomePage(driver).goToRegister();
        register.enterFirstName("John")
                .enterLastName("Doe")
                .enterEmail(TestDataGenerator.uniqueEmail())
                .enterPassword("123")
                .enterConfirmPassword("123")
                .clickRegister();

        String error = register.getPasswordError();
        Assert.assertTrue(error.contains("at least 6"),
                "TC_REG_007: Expected min-length error but got: '" + error + "'");
    }

    @Test(description = "TC_REG_008 - Register with mismatched passwords",
          groups = {"regression"})
    public void verifyRegisterWithMismatchedPasswords() {
        RegisterPage register = new HomePage(driver).goToRegister();
        register.enterFirstName("John")
                .enterLastName("Doe")
                .enterEmail(TestDataGenerator.uniqueEmail())
                .enterPassword("Test@123")
                .enterConfirmPassword("Different@456")
                .clickRegister();

        Assert.assertEquals(register.getConfirmPasswordError(),
                "The password and confirmation password do not match.",
                "TC_REG_008: Password mismatch error not shown");
    }
}