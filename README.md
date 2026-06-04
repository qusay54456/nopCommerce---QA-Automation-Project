# nopCommerce QA Automation Project

Automated UI test suite for the [nopCommerce Demo Store](https://demo.nopcommerce.com)
built with Selenium WebDriver, Java, and TestNG using the Page Object Model (POM).

## Tech Stack
- Java 11
- Selenium WebDriver 4.27
- TestNG 7.10
- Maven
- WebDriverManager (auto driver setup)

## Project Structure
```
src/test/java/com/nopcommerce/
├── base/      BaseTest (WebDriver setup/teardown + Cloudflare bypass)
├── pages/     Page Object classes (HomePage, RegisterPage, LoginPage,
│              SearchResultsPage, ProductPage, ShoppingCartPage)
├── tests/     TestNG test classes (RegistrationTests, LoginTests,
│              SearchTests, CartTests)
└── utilities/ TestDataGenerator (dynamic test data)
```

## Workflows Covered
1. User Registration & Login  (RegistrationTests, LoginTests)
2. Product Search & Shopping Cart  (SearchTests, CartTests)

## How to Run
- In Eclipse: right-click `testng.xml` -> Run As -> TestNG Suite
- Via Maven: `mvn clean test`

## Test Report
After running, open: `test-output/index.html`
