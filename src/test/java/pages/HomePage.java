package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver driver;

    // ── Header Links ──────────────────────────────────────
    @FindBy(className = "ico-register")
    private WebElement registerLink;

    @FindBy(className = "ico-login")
    private WebElement loginLink;

    @FindBy(className = "ico-account")
    private WebElement myAccountLink;

    @FindBy(className = "ico-logout")
    private WebElement logoutLink;

    // ── Search ────────────────────────────────────────────
    @FindBy(id = "small-searchterms")
    private WebElement searchBox;

    @FindBy(css = "button.search-box-button")
    private WebElement searchButton;

    // ── Cart ──────────────────────────────────────────────
    @FindBy(className = "ico-cart")
    private WebElement cartLink;

    // ── Constructor ───────────────────────────────────────
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ── Navigation Methods ────────────────────────────────
    public void clickRegister() {
        registerLink.click();
    }

    public void clickLogin() {
        loginLink.click();
    }

    public void clickLogout() {
        logoutLink.click();
    }

    public void clickMyAccount() {
        myAccountLink.click();
    }

    public void clickCart() {
        cartLink.click();
    }

    // ── Search Methods ────────────────────────────────────
    public void searchProduct(String keyword) {
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchButton.click();
    }

    // ── Verification Methods ──────────────────────────────
    public boolean isLoggedIn() {
        try {
            return myAccountLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoggedOut() {
        try {
            return registerLink.isDisplayed() && loginLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCartCount() {
        return cartLink.getText();
    }
}
