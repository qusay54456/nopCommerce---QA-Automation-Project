package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    // ── Fields ────────────────────────────────────────────
    @FindBy(id = "Email")
    private WebElement emailField;

    @FindBy(id = "Password")
    private WebElement passwordField;

    // ── Button ────────────────────────────────────────────
    @FindBy(css = "button.login-button")
    private WebElement loginButton;

    // ── Error Messages ────────────────────────────────────
    @FindBy(css = "div.message-error")
    private WebElement loginErrorMessage;

    @FindBy(id = "Email-error")
    private WebElement emailValidationError;

    // ── Constructor ───────────────────────────────────────
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ── Actions ───────────────────────────────────────────
    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    // ── Verification ──────────────────────────────────────
    public String getLoginErrorMessage() {
        return loginErrorMessage.getText().trim();
    }

    public String getEmailValidationError() {
        return emailValidationError.getText().trim();
    }

    public boolean isLoginErrorDisplayed() {
        try {
            return loginErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
