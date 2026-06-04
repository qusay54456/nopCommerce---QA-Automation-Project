package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchResultsPage;
import pages.ShoppingCartPage;

public class CartTest extends BaseTest {

    // ── Helper: search and add first product to cart ──────
    private void searchAndAddToCart(String keyword) {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(keyword);

        SearchResultsPage searchResults = new SearchResultsPage(driver);
        searchResults.clickFirstProduct();

        ProductPage productPage = new ProductPage(driver);
        productPage.clickAddToCart();
        productPage.waitForNotification();
    }

    @Test(priority = 1, description = "TC-019: Add product to cart from product page")
    public void testAddProductToCart() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("Apple MacBook");

        SearchResultsPage searchResults = new SearchResultsPage(driver);
        searchResults.clickFirstProduct();

        ProductPage productPage = new ProductPage(driver);
        productPage.clickAddToCart();

        Assert.assertTrue(productPage.isSuccessNotificationDisplayed(),
                "Success notification should appear after adding product to cart");

        String notif = productPage.getNotificationText();
        Assert.assertTrue(notif.contains("shopping cart"),
                "Notification should mention shopping cart but got: " + notif);
    }

    @Test(priority = 2, description = "TC-020: Verify cart contains the added product")
    public void testVerifyCartContents() {
        searchAndAddToCart("Apple MacBook");

        // Navigate to cart
        HomePage homePage = new HomePage(driver);
        homePage.clickCart();

        ShoppingCartPage cartPage = new ShoppingCartPage(driver);
        Assert.assertTrue(cartPage.hasProducts(),
                "Cart should contain at least one product");

        String productName = cartPage.getFirstProductName();
        Assert.assertTrue(productName.toLowerCase().contains("apple macbook"),
                "Cart should contain Apple MacBook but found: " + productName);

        Assert.assertEquals(cartPage.getFirstQuantity(), "1",
                "Default quantity should be 1");
    }

    @Test(priority = 3, description = "TC-021: Update product quantity in cart")
    public void testUpdateCartQuantity() {
        searchAndAddToCart("Apple MacBook");

        HomePage homePage = new HomePage(driver);
        homePage.clickCart();

        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        // Update quantity to 2
        cartPage.updateQuantity(0, "2");
        cartPage.clickUpdateCart();

        // Verify updated
        cartPage = new ShoppingCartPage(driver);
        Assert.assertEquals(cartPage.getFirstQuantity(), "2",
                "Quantity should be updated to 2");
    }

    @Test(priority = 4, description = "TC-022: Remove product from cart")
    public void testRemoveProductFromCart() {
        searchAndAddToCart("Apple MacBook");

        HomePage homePage = new HomePage(driver);
        homePage.clickCart();

        ShoppingCartPage cartPage = new ShoppingCartPage(driver);
        Assert.assertTrue(cartPage.hasProducts(),
                "Cart should have products before removal");

        // Remove product
        cartPage.removeFirstProduct();

        // Verify cart is empty
        cartPage = new ShoppingCartPage(driver);
        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removing the product");
    }

    @Test(priority = 5, description = "TC-023: Verify cart count in header updates")
    public void testCartCountUpdates() {
        HomePage homePage = new HomePage(driver);

        // Check initial cart count
        String initialCount = homePage.getCartCount();
        Assert.assertTrue(initialCount.contains("(0)"),
                "Initial cart count should be 0");

        // Add product to cart
        homePage.searchProduct("Apple MacBook");

        SearchResultsPage searchResults = new SearchResultsPage(driver);
        searchResults.clickFirstProduct();

        ProductPage productPage = new ProductPage(driver);
        productPage.clickAddToCart();
        productPage.waitForNotification();

        // Verify cart count updated
        homePage = new HomePage(driver);
        String updatedCount = homePage.getCartCount();
        Assert.assertTrue(updatedCount.contains("(1)"),
                "Cart count should be 1 after adding product but got: " + updatedCount);
    }
}
