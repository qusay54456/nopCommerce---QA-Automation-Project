package com.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * ShoppingCartPage - Represents the shopping cart page (/cart), where the user
 * can view, update the quantity of, or remove products.
 */
public class ShoppingCartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────
    private final By cartRows        = By.cssSelector("table.cart tbody tr");
    private final By productNames    = By.cssSelector("td.product a.product-name");
    private final By unitPrices      = By.cssSelector("td.unit-price span.product-unit-price");
    private final By quantityInputs  = By.cssSelector("td.quantity input.qty-input");
    private final By subtotals       = By.cssSelector("td.subtotal span.product-subtotal");
    private final By removeCheckboxes= By.cssSelector("td.remove-from-cart input[type='checkbox']");
    private final By updateCartButton= By.cssSelector("button.update-cart-button, input[name='updatecart']");
    private final By emptyCartMessage= By.cssSelector("div.no-data, div.order-summary-content");

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ── Getters ───────────────────────────────────────────────────────
    public int getCartItemCount() {
        try {
            return driver.findElements(cartRows).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean hasProducts() {
        return getCartItemCount() > 0;
    }

    public String getFirstProductName() {
        List<WebElement> names = driver.findElements(productNames);
        return names.isEmpty() ? "" : names.get(0).getText().trim();
    }

    public String getFirstUnitPrice() {
        List<WebElement> prices = driver.findElements(unitPrices);
        return prices.isEmpty() ? "" : prices.get(0).getText().trim();
    }

    public String getFirstQuantity() {
        List<WebElement> qty = driver.findElements(quantityInputs);
        return qty.isEmpty() ? "" : qty.get(0).getAttribute("value");
    }

    // ── Actions ───────────────────────────────────────────────────────
    public void updateFirstQuantity(String newQuantity) {
        List<WebElement> qty = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(quantityInputs));
        WebElement field = qty.get(0);
        field.clear();
        field.sendKeys(newQuantity);
        clickUpdateCart();
    }

    public void removeFirstProduct() {
        List<WebElement> boxes = driver.findElements(removeCheckboxes);
        if (!boxes.isEmpty()) {
            boxes.get(0).click();
            clickUpdateCart();
        }
    }

    public void clickUpdateCart() {
        wait.until(ExpectedConditions.elementToBeClickable(updateCartButton)).click();
    }

    // ── Verification ──────────────────────────────────────────────────
    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }
}
