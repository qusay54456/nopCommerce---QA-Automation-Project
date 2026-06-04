package com.nopcommerce.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final String BASE_URL = "https://demo.nopcommerce.com/";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);

        ((JavascriptExecutor) driver).executeScript(
                "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        navigateToHome();
    }

    protected void navigateToHome() {
        // Retry up to 3 times to bypass Cloudflare
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                driver.get(BASE_URL);
                new WebDriverWait(driver, Duration.ofSeconds(35)).until(d -> {
                    String title = d.getTitle();
                    return title != null
                            && !title.contains("Just a moment")
                            && !title.isEmpty()
                            && !title.equals("Attention Required!");
                });
                Thread.sleep(2000);
                // Verify the page actually loaded (has the register link)
                if (!driver.findElements(
                        org.openqa.selenium.By.className("ico-register")).isEmpty()
                    || !driver.findElements(
                        org.openqa.selenium.By.id("small-searchterms")).isEmpty()) {
                    return; // success
                }
            } catch (Exception e) {
                // try again
            }
            // wait before retry
            try { Thread.sleep(3000 * attempt); } catch (Exception ignored) {}
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}