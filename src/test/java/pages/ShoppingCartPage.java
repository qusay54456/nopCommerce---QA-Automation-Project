package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ShoppingCartPage {

    private WebDriver driver;

    // ── Cart Table ────────────────────────────────────────
    @FindBy(css = "table.cart tbody tr")
    private List<WebElement> cartRows;

    @FindBy(css = "td.product a.product-name")
    private List<WebElement> productNames;

    @FindBy(css = "td.unit-price span.product-unit-price")
    private List<WebElement> unitPrices;

    @FindBy(css = "td.quantity input.qty-input")
    private List<WebElement> quantityInputs;

    @FindBy(css = "td.subtotal span.product-subtotal")
    private List<WebElement> subtotals;

    // ── Remove ────────────────────────────────────────────
    @FindBy(css = "td.remove-from-cart input[type='checkbox']")
    private List<WebElement> removeCheckboxes;

    // ── Buttons ───────────────────────────────────────────
    @FindBy(id = "updatecart")
    private WebElement updateCartButton;

    // ── Empty Cart ────────────────────────────────────────
    @FindBy(css = "div.no-data")
    private WebElement emptyCartMessage;

    // ── Order Total ───────────────────────────────────────
    @FindBy(css = "span.value-summary strong")
    private WebElement orderTotal;

    // ── Constructor ───────────────────────────────────────
    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ── Info Methods ──────────────────────────────────────
    public int getCartItemCount() {
        return cartRows.size();
    }

    public String getFirstProductName() {
        if (!productNames.isEmpty()) {
            return productNames.get(0).getText().trim();
        }
        return "";
    }

    public String getFirstUnitPrice() {
        if (!unitPrices.isEmpty()) {
            return unitPrices.get(0).getText().trim();
        }
        return "";
    }

    public String getFirstQuantity() {
        if (!quantityInputs.isEmpty()) {
            return quantityInputs.get(0).getAttribute("value");
        }
        return "";
    }

    public String getFirstSubtotal() {
        if (!subtotals.isEmpty()) {
            return subtotals.get(0).getText().trim();
        }
        return "";
    }

    public String getOrderTotal() {
        return orderTotal.getText().trim();
    }

    // ── Actions ───────────────────────────────────────────
    public void updateQuantity(int index, String newQty) {
        WebElement qtyInput = quantityInputs.get(index);
        qtyInput.clear();
        qtyInput.sendKeys(newQty);
    }

    public void clickUpdateCart() {
        updateCartButton.click();
    }

    public void removeFirstProduct() {
        if (!removeCheckboxes.isEmpty()) {
            removeCheckboxes.get(0).click();
            clickUpdateCart();
        }
    }

    // ── Verification ──────────────────────────────────────
    public boolean isCartEmpty() {
        try {
            return emptyCartMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasProducts() {
        return cartRows.size() > 0;
    }
}
