package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;

    // ── Search Results ────────────────────────────────────
    @FindBy(css = "div.product-item")
    private List<WebElement> productItems;

    @FindBy(css = "div.no-result")
    private WebElement noResultMessage;

    @FindBy(css = "div.warning")
    private WebElement warningMessage;

    // ── Product Links ─────────────────────────────────────
    @FindBy(css = "h2.product-title a")
    private List<WebElement> productTitles;

    // ── Constructor ───────────────────────────────────────
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ── Verification ──────────────────────────────────────
    public int getProductCount() {
        return productItems.size();
    }

    public boolean hasResults() {
        return productItems.size() > 0;
    }

    public String getNoResultMessage() {
        return noResultMessage.getText().trim();
    }

    public String getWarningMessage() {
        return warningMessage.getText().trim();
    }

    public boolean isNoResultDisplayed() {
        try {
            return noResultMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWarningDisplayed() {
        try {
            return warningMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ── Actions ───────────────────────────────────────────
    public void clickFirstProduct() {
        if (!productTitles.isEmpty()) {
            productTitles.get(0).click();
        }
    }

    public String getFirstProductTitle() {
        if (!productTitles.isEmpty()) {
            return productTitles.get(0).getText().trim();
        }
        return "";
    }
}
