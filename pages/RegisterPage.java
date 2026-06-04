package com.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * RegisterPage - Represents the nopCommerce customer registration form
 * (/register). All registration fields live on a single page.
 */
public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Form Field Locators ───────────────────────────────────────────
    private final By genderMale       = By.id("gender-male");
    private final By firstNameField   = By.id("FirstName");
    private final By lastNameField    = By.id("LastName");
    private final By emailField       = By.id("Email");
    private final By passwordField    = By.id("Password");
    private final By confirmPassField = By.id("ConfirmPassword");
    private final By registerButton   = By.id("register-button");

    // ── Result / Messages ─────────────────────────────────────────────
    private final By resultMessage    = By.className("result");
    private final By continueButton   = By.cssSelector("a.register-continue-button");

    // ── Field Validation Errors ───────────────────────────────────────
    private final By firstNameError   = By.id("FirstName-error");
    private final By lastNameError    = By.id("LastName-error");
    private final By emailError       = By.id("Email-error");
    private final By passwordError    = By.id("Password-error");
    private final By confirmPassError = By.id("ConfirmPassword-error");
    private final By existingEmailMsg = By.cssSelector("div.message-error li");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ── Field Entry Actions ───────────────────────────────────────────
    public RegisterPage selectMaleGender() {
        wait.until(ExpectedConditions.elementToBeClickable(genderMale)).click();
        return this;
    }

    public RegisterPage enterFirstName(String firstName) {
        type(firstNameField, firstName);
        return this;
    }

    public RegisterPage enterLastName(String lastName) {
        type(lastNameField, lastName);
        return this;
    }

    public RegisterPage enterEmail(String email) {
        type(emailField, email);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public RegisterPage enterConfirmPassword(String confirmPassword) {
        type(confirmPassField, confirmPassword);
        return this;
    }

    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    /** Convenience method: fills all required fields with valid data and submits. */
    public void registerNewUser(String firstName, String lastName,
                                String email, String password) {
        selectMaleGender();
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(password);
        clickRegister();
    }

    // ── Result / Error Getters ────────────────────────────────────────
    public String getResultMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(resultMessage))
                   .getText().trim();
    }

    public String getFirstNameError()      { return getText(firstNameError); }
    public String getLastNameError()       { return getText(lastNameError); }
    public String getEmailError()          { return getText(emailError); }
    public String getPasswordError()       { return getText(passwordError); }
    public String getConfirmPasswordError(){ return getText(confirmPassError); }
    public String getExistingEmailError()  { return getText(existingEmailMsg); }

    // ── Helpers ───────────────────────────────────────────────────────
    private void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    private String getText(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                       .getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}
