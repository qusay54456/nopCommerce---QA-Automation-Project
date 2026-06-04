package com.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SearchResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productItems  = By.cssSelector("div.item-box");
    private final By productTitles = By.cssSelector("h2.product-title a");
    private final By noResultMsg   = By.cssSelector("div.no-result");
    private final By warningMsg    = By.cssSelector("div.warning");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public int getProductCount() {
        try {
            return driver.findElements(productItems).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean hasResults() {
        return getProductCount() > 0;
    }

    public boolean isNoResultMessageDisplayed() {
        return isDisplayed(noResultMsg);
    }

    public boolean isWarningDisplayed() {
        return isDisplayed(warningMsg);
    }

    public String getNoResultMessage() {
        try { return driver.findElement(noResultMsg).getText().trim(); }
        catch (Exception e) { return ""; }
    }

    public com.nopcommerce.pages.ProductPage openFirstProduct() {
        List<org.openqa.selenium.WebElement> titles =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productTitles));
        titles.get(0).click();
        return new com.nopcommerce.pages.ProductPage(driver);
    }

    private boolean isDisplayed(By locator) {
        try { return driver.findElement(locator).isDisplayed(); }
        catch (Exception e) { return false; }
    }
}