package com.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By registerLink = By.className("ico-register");
    private final By loginLink    = By.className("ico-login");
    private final By logoutLink   = By.className("ico-logout");
    private final By accountLink  = By.className("ico-account");
    private final By searchBox    = By.id("small-searchterms");
    private final By searchButton = By.cssSelector("button.button-1.search-box-button");
    private final By cartLink     = By.id("topcartlink");
    private final By cartQty      = By.className("cart-qty");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public RegisterPage goToRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
        return new RegisterPage(driver);
    }

    public LoginPage goToLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return new LoginPage(driver);
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public ShoppingCartPage goToCart() {
        try {
            WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(cartLink));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cart);
        } catch (Exception e) {
            driver.findElement(cartLink).click();
        }
        return new ShoppingCartPage(driver);
    }

    public SearchResultsPage searchProduct(String keyword) {
        WebElement box = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        box.clear();
        box.sendKeys(keyword);
        driver.findElement(searchButton).click();
        return new SearchResultsPage(driver);
    }

    public boolean isUserLoggedIn() {
        return isDisplayed(accountLink) || isDisplayed(logoutLink);
    }

    public boolean isUserLoggedOut() {
        return isDisplayed(registerLink) && isDisplayed(loginLink);
    }

    public String getCartQuantityText() {
        try { return driver.findElement(cartQty).getText().trim(); }
        catch (Exception e) { return ""; }
    }

    private boolean isDisplayed(By locator) {
        try { return driver.findElement(locator).isDisplayed(); }
        catch (Exception e) { return false; }
    }
}