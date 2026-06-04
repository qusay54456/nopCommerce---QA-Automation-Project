package com.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * ProductPage - Represents an individual product details page, including the
 * "Add to cart" button and the success notification bar.
 */
public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────
    private final By productName        = By.cssSelector("div.product-name h1");
    private final By productPrice        = By.cssSelector("div.product-price span");
    private final By addToCartButton     = By.cssSelector("button.button-1.add-to-cart-button");
    private final By successNotification = By.cssSelector("div.bar-notification.success");
    private final By notificationText    = By.cssSelector("div.bar-notification.success p.content");
    private final By closeNotification   = By.cssSelector("span.close");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ── Getters ───────────────────────────────────────────────────────
    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName))
                   .getText().trim();
    }

    public String getProductPrice() {
        try {
            return driver.findElement(productPrice).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // ── Actions ───────────────────────────────────────────────────────
    public ProductPage clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        return this;
    }

    public String getNotificationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(notificationText))
                   .getText().trim();
    }

    public boolean isSuccessNotificationDisplayed() {
        try {
            return wait.until(ExpectedConditions
                    .visibilityOfElementLocated(successNotification)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void closeNotificationBar() {
        try {
            driver.findElement(closeNotification).click();
        } catch (Exception ignored) {
        }
    }
}
