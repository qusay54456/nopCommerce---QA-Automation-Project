package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;

public class SearchTest extends BaseTest {

    @Test(priority = 1, description = "TC-014: Search with valid product keyword")
    public void testSearchWithValidKeyword() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("Apple MacBook");

        SearchResultsPage searchResults = new SearchResultsPage(driver);
        Assert.assertTrue(searchResults.hasResults(),
                "Search results should contain products for 'Apple MacBook'");
        Assert.assertTrue(searchResults.getProductCount() > 0,
                "At least one product should be found");
    }

    @Test(priority = 2, description = "TC-015: Search with non-existing product keyword")
    public void testSearchWithInvalidKeyword() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("xyzproductnotexist123");

        SearchResultsPage searchResults = new SearchResultsPage(driver);
        Assert.assertFalse(searchResults.hasResults(),
                "No products should be found for non-existing keyword");
        Assert.assertTrue(searchResults.isNoResultDisplayed(),
                "No results message should be displayed");
    }

    @Test(priority = 3, description = "TC-016: Search with empty keyword")
    public void testSearchWithEmptyKeyword() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("");

        SearchResultsPage searchResults = new SearchResultsPage(driver);

        // nopCommerce shows a warning for short search terms
        boolean warningShown = searchResults.isWarningDisplayed() || searchResults.isNoResultDisplayed();
        Assert.assertTrue(warningShown,
                "A warning or no-result message should be displayed for empty search");
    }

    @Test(priority = 4, description = "TC-017: Search with partial product name")
    public void testSearchWithPartialKeyword() {
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct("MacBook");

        SearchResultsPage searchResults = new SearchResultsPage(driver);
        Assert.assertTrue(searchResults.hasResults(),
                "Search results should contain products for partial keyword 'MacBook'");
    }
}
