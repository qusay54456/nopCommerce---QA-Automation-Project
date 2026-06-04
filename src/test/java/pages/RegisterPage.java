package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {

    private WebDriver driver;

    // ── Gender ────────────────────────────────────────────
    @FindBy(id = "gender-male")
    private WebElement genderMale;

    @FindBy(id = "gender-female")
    private WebElement genderFemale;

    // ── Personal Details ──────────────────────────────────
    @FindBy(id = "FirstName")
    private WebElement firstNameField;

    @FindBy(id = "LastName")
    private WebElement lastNameField;

    @FindBy(id = "Email")
    private WebElement emailField;

    // ── Password ──────────────────────────────────────────
    @FindBy(id = "Password")
    private WebElement passwordField;

    @FindBy(id = "ConfirmPassword")
    private WebElement confirmPasswordField;

    // ── Button ────────────────────────────────────────────
    @FindBy(id = "register-button")
    private WebElement registerButton;

    // ── Success Message ───────────────────────────────────
    @FindBy(className = "result")
    private WebElement resultMessage;

    // ── Error Messages ────────────────────────────────────
    @FindBy(id = "FirstName-error")
    private WebElement firstNameError;

    @FindBy(id = "LastName-error")
    private WebElement lastNameError;

    @FindBy(id = "Email-error")
    private WebElement emailError;

    @FindBy(id = "Password-error")
    private WebElement passwordError;

    @FindBy(id = "ConfirmPassword-error")
    private WebElement confirmPasswordError;

    @FindBy(css = "div.message-error li")
    private WebElement existingEmailError;

    // ── Continue Button (after success) ───────────────────
    @FindBy(css = "a.register-continue-button")
    private WebElement continueButton;

    // ── Constructor ───────────────────────────────────────
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ── Actions ───────────────────────────────────────────
    public void selectGenderMale() {
        genderMale.click();
    }

    public void selectGenderFemale() {
        genderFemale.click();
    }

    public void enterFirstName(String firstName) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(confirmPassword);
    }

    public void clickRegister() {
        registerButton.click();
    }

    public void clickContinue() {
        continueButton.click();
    }

    public void registerUser(String firstName, String lastName,
                             String email, String password) {
        selectGenderMale();
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(password);
        clickRegister();
    }

    // ── Verification ──────────────────────────────────────
    public String getResultMessage() {
        return resultMessage.getText().trim();
    }

    public String getFirstNameError() {
        return firstNameError.getText().trim();
    }

    public String getLastNameError() {
        return lastNameError.getText().trim();
    }

    public String getEmailError() {
        return emailError.getText().trim();
    }

    public String getPasswordError() {
        return passwordError.getText().trim();
    }

    public String getConfirmPasswordError() {
        return confirmPasswordError.getText().trim();
    }

    public String getExistingEmailError() {
        return existingEmailError.getText().trim();
    }
}
