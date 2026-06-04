package com.nopcommerce.tests;

import com.nopcommerce.base.BaseTest;
import com.nopcommerce.pages.HomePage;
import com.nopcommerce.pages.ProductPage;
import com.nopcommerce.pages.SearchResultsPage;
import com.nopcommerce.pages.ShoppingCartPage;
import com.nopcommerce.utilities.TestDataGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class CartTests extends BaseTest {

    private void addProductToCart() {
        navigateToHome();
        SearchResultsPage results = new HomePage(driver)
                .searchProduct(TestDataGenerator.VALID_PRODUCT);
        ProductPage product = results.openFirstProduct();
        product.clickAddToCart();
        product.isSuccessNotificationDisplayed();
        product.closeNotificationBar();
        try { Thread.sleep(2000); } catch (Exception ignored) {}
    }

    private ShoppingCartPage goToCartSafely() {
        // Navigate directly to cart URL — most reliable method
        driver.get("https://demo.nopcommerce.com/cart");
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(
                                By.cssSelector("table.cart")),
                        ExpectedConditions.presenceOfElementLocated(
                                By.cssSelector("div.order-summary-content"))
                ));
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        return new ShoppingCartPage(driver);
    }

    @Test(description = "TC_CART_001 - Add a product to the cart",
          groups = {"regression", "smoke"})
    public void verifyAddProductToCart() {
        navigateToHome();
        SearchResultsPage results = new HomePage(driver)
                .searchProduct(TestDataGenerator.VALID_PRODUCT);
        ProductPage product = results.openFirstProduct();
        product.clickAddToCart();

        Assert.assertTrue(product.isSuccessNotificationDisplayed(),
                "TC_CART_001: Success notification did not appear");
        Assert.assertTrue(product.getNotificationMessage().contains("shopping cart"),
                "TC_CART_001: Notification did not mention the shopping cart");
    }

    @Test(description = "TC_CART_002 - Cart contains the added product",
          groups = {"regression"})
    public void verifyCartContents() {
        addProductToCart();
        ShoppingCartPage cart = goToCartSafely();

        Assert.assertTrue(cart.hasProducts(),
                "TC_CART_002: Cart should contain at least one product");
        Assert.assertEquals(cart.getFirstQuantity(), "1",
                "TC_CART_002: Default product quantity should be 1");
    }

    @Test(description = "TC_CART_003 - Update product quantity in the cart",
          groups = {"regression"})
    public void verifyUpdateCartQuantity() {
        addProductToCart();
        ShoppingCartPage cart = goToCartSafely();
        cart.updateFirstQuantity("3");

        Assert.assertEquals(cart.getFirstQuantity(), "3",
                "TC_CART_003: Quantity should be updated to 3");
    }

    @Test(description = "TC_CART_004 - Remove a product from the cart",
          groups = {"regression"})
    public void verifyRemoveProductFromCart() {
        addProductToCart();
        ShoppingCartPage cart = goToCartSafely();

        Assert.assertTrue(cart.hasProducts(),
                "TC_CART_004: Pre-condition failed — cart is empty");

        cart.removeFirstProduct();

        Assert.assertTrue(cart.isCartEmpty(),
                "TC_CART_004: Cart should be empty after removing the product");
    }
}