package com.nopcommerce.tests;

import com.nopcommerce.base.BaseTest;
import com.nopcommerce.pages.HomePage;
import com.nopcommerce.pages.SearchResultsPage;
import com.nopcommerce.utilities.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTests extends BaseTest {

    @Test(description = "TC_SEARCH_001 - Search with a valid product keyword",
          groups = {"regression", "smoke"})
    public void verifySearchWithValidKeyword() {
        SearchResultsPage results = new HomePage(driver)
                .searchProduct(TestDataGenerator.VALID_PRODUCT);

        Assert.assertTrue(results.hasResults(),
                "TC_SEARCH_001: No products returned for keyword '"
                        + TestDataGenerator.VALID_PRODUCT + "'");
    }

    @Test(description = "TC_SEARCH_002 - Search with a non-existing product keyword",
          groups = {"regression"})
    public void verifySearchWithInvalidKeyword() {
        SearchResultsPage results = new HomePage(driver)
                .searchProduct(TestDataGenerator.INVALID_PRODUCT);

        Assert.assertFalse(results.hasResults(),
                "TC_SEARCH_002: Products were returned for a non-existing keyword");
        Assert.assertTrue(results.isNoResultMessageDisplayed(),
                "TC_SEARCH_002: 'No results' message was not displayed");
    }

    @Test(description = "TC_SEARCH_003 - Search with an empty keyword",
          groups = {"regression"})
    public void verifySearchWithEmptyKeyword() {
        SearchResultsPage results = new HomePage(driver).searchProduct("");

        boolean pageLoadedCorrectly =
                !results.hasResults()
                || results.isNoResultMessageDisplayed()
                || results.isWarningDisplayed();

        Assert.assertTrue(pageLoadedCorrectly,
                "TC_SEARCH_003: Page did not behave correctly for empty search");
    }

    @Test(description = "TC_SEARCH_004 - Search with a partial product name",
          groups = {"regression"})
    public void verifySearchWithPartialKeyword() {
        SearchResultsPage results = new HomePage(driver)
                .searchProduct(TestDataGenerator.PARTIAL_PRODUCT);

        Assert.assertTrue(results.hasResults(),
                "TC_SEARCH_004: No products returned for partial keyword '"
                        + TestDataGenerator.PARTIAL_PRODUCT + "'");
    }
}