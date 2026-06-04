package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private WebDriver driver;

    // ── Product Info ──────────────────────────────────────
    @FindBy(css = "div.product-name h1")
    private WebElement productName;

    @FindBy(css = "div.product-price span")
    private WebElement productPrice;

    // ── Add to Cart ───────────────────────────────────────
    @FindBy(id = "add-to-cart-button-1")
    private WebElement addToCartButton;

    @FindBy(css = "div.bar-notification.success")
    private WebElement successNotification;

    @FindBy(css = "div.bar-notification.success p.content")
    private WebElement notificationContent;

    @FindBy(css = "span.close")
    private WebElement closeNotification;

    // ── Constructor ───────────────────────────────────────
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ── Info Methods ──────────────────────────────────────
    public String getProductName() {
        return productName.getText().trim();
    }

    public String getProductPrice() {
        return productPrice.getText().trim();
    }

    // ── Actions ───────────────────────────────────────────
    public void clickAddToCart() {
        addToCartButton.click();
    }

    public void waitForNotification() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(successNotification));
    }

    public String getNotificationText() {
        waitForNotification();
        return notificationContent.getText().trim();
    }

    public boolean isSuccessNotificationDisplayed() {
        try {
            waitForNotification();
            return successNotification.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void closeNotification() {
        try {
            closeNotification.click();
        } catch (Exception e) {
            // notification may have auto-closed
        }
    }
}
