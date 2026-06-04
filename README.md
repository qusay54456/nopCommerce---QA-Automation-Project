# 🛒 nopCommerce QA Automation Suite

> End-to-end test automation framework for [demo.nopcommerce.com](https://demo.nopcommerce.com) built with Selenium WebDriver, Java, TestNG, and Page Object Model.

---

## 📊 Test Results Summary

| Metric | Value |
|--------|-------|
| ✅ Total Tests | 21 |
| 🟢 Passed | 18 |
| 🔴 Failed | 3 |
| ⏭️ Skipped | 0 |
| 📈 Pass Rate | **85.7%** |

---

## 🧪 Features Covered

| Module | Test Class | Description |
|--------|------------|-------------|
| 🔐 Registration | `RegistrationTests.java` | Valid/invalid registration scenarios |
| 🔑 Login | `LoginTests.java` | Login, logout, wrong credentials |
| 🔍 Search | `SearchTests.java` | Valid/invalid/empty search queries |
| 🛒 Cart | `CartTests.java` | Add, update, remove cart items |

---

## 🏗️ Project Structure

```
nopcommerce-automation-v2/
├── src/test/java/
│   ├── com.nopcommerce.base/
│   │   └── BaseTest.java              # WebDriver setup & teardown
│   ├── com.nopcommerce.pages/
│   │   ├── HomePage.java
│   │   ├── LoginPage.java
│   │   ├── ProductPage.java
│   │   ├── RegisterPage.java
│   │   ├── SearchResultsPage.java
│   │   └── ShoppingCartPage.java
│   ├── com.nopcommerce.tests/
│   │   ├── CartTests.java
│   │   ├── LoginTests.java
│   │   ├── RegistrationTests.java
│   │   └── SearchTests.java
│   └── com.nopcommerce.utilities/
│       └── TestDataGenerator.java
└── pom.xml
```

---

## 🛠️ Tech Stack

- **Language:** Java 11
- **Automation:** Selenium WebDriver 4
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Design Pattern:** Page Object Model (POM)
- **Browser:** Google Chrome
- **IDE:** IntelliJ IDEA

---

## ⚙️ Setup & Run

### Prerequisites
- Java JDK 11+
- Maven
- Google Chrome + ChromeDriver

### Clone the repo
```bash
git clone https://github.com/qusay54456/nopCommerce---QA-Automation-Project.git
cd nopCommerce---QA-Automation-Project
```

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=LoginTests
mvn test -Dtest=RegistrationTests
mvn test -Dtest=CartTests
mvn test -Dtest=SearchTests
```

---

## 📋 Test Cases

### 🔐 Registration Module
| TC ID | Title | Status |
|-------|-------|--------|
| TC-001 | Register with valid data | ✅ Pass |
| TC-002 | Register with existing email | ✅ Pass |
| TC-003 | Register with empty First Name | ✅ Pass |
| TC-004 | Register with empty Last Name | ✅ Pass |
| TC-005 | Register with empty Email | ✅ Pass |
| TC-006 | Register with invalid email format | ✅ Pass |
| TC-007 | Register with short password | ✅ Pass |
| TC-008 | Register with mismatched passwords | ✅ Pass |

### 🔑 Login Module
| TC ID | Title | Status |
|-------|-------|--------|
| TC-009 | Login with valid credentials | ✅ Pass |
| TC-010 | Login with wrong password | ✅ Pass |
| TC-011 | Login with non-existing email | ✅ Pass |
| TC-012 | Login with empty fields | ✅ Pass |
| TC-013 | Logout from authenticated session | ✅ Pass |

### 🔍 Search Module
| TC ID | Title | Status |
|-------|-------|--------|
| TC-014 | Search with valid keyword | ✅ Pass |
| TC-015 | Search with non-existing keyword | ✅ Pass |
| TC-016 | Search with empty keyword | ✅ Pass |
| TC-017 | Search with partial product name | ✅ Pass |

### 🛒 Cart Module
| TC ID | Title | Status |
|-------|-------|--------|
| TC-018 | Browse products by category | ✅ Pass |
| TC-019 | Add product to cart | ✅ Pass |
| TC-020 | Verify cart contains added product | ✅ Pass |
| TC-021 | Update product quantity | ✅ Pass |
| TC-022 | Remove product from cart | ✅ Pass |
| TC-023 | Verify cart count updates in header | ✅ Pass |

---

## 👤 Author

**Qusay Terawi**  
QA Engineer | Automation Testing  
🔗 [GitHub](https://github.com/qusay54456)

---

## 📄 License

This project is for educational and demonstration purposes.
