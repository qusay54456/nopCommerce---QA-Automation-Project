package com.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * LoginPage - Represents the nopCommerce customer login form (/login).
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────
    private final By emailField      = By.id("Email");
    private final By passwordField   = By.id("Password");
    private final By loginButton     = By.cssSelector("button.button-1.login-button");
    private final By loginErrorBox   = By.cssSelector("div.message-error");
    private final By emailFieldError = By.id("Email-error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ── Actions ───────────────────────────────────────────────────────
    public LoginPage enterEmail(String email) {
        type(emailField, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    /** Convenience method: enters credentials and submits. */
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    // ── Verification ──────────────────────────────────────────────────
    public boolean isLoginErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions
                    .visibilityOfElementLocated(loginErrorBox)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoginErrorMessage() {
        try {
            return driver.findElement(loginErrorBox).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getEmailFieldError() {
        try {
            return driver.findElement(emailFieldError).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // ── Helper ────────────────────────────────────────────────────────
    private void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }
}
