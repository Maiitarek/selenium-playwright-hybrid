# Selenium + Playwright Hybrid Framework

[![Java](https://img.shields.io/badge/Java-11+-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://java.com)
[![Selenium](https://img.shields.io/badge/Selenium-4.x-43B02A?style=flat&logo=selenium&logoColor=white)](https://selenium.dev)
[![Playwright](https://img.shields.io/badge/Playwright-1.43-2EAD33?style=flat&logo=playwright&logoColor=white)](https://playwright.dev)
[![TestNG](https://img.shields.io/badge/TestNG-7.9-FF6C37?style=flat)](https://testng.org)
[![Allure](https://img.shields.io/badge/Allure-Report-orange?style=flat)](https://allurereport.org)

A production-grade Java e2e framework supporting **Selenium and Playwright as interchangeable runners** — same Page Objects, same tests, same Allure reports. Switch with one flag.

## Quick start

```bash
git clone https://github.com/Maiitarek/selenium-playwright-hybrid.git
cd selenium-playwright-hybrid

mvn clean test                                      # Selenium Chrome
mvn clean test -Drunner=playwright-chromium         # Playwright
mvn clean test -Drunner=selenium-chrome -Dheadless=true  # CI headless
```

## Runners

| Flag | Engine |
|---|---|
| `selenium-chrome` (default) | Selenium WebDriver |
| `selenium-firefox` | Selenium WebDriver |
| `playwright-chromium` | Playwright |
| `playwright-firefox` | Playwright |
| `playwright-webkit` | Playwright |

## Test scenarios — Sauce Demo

| Feature | Tests |
|---|---|
| Login | Valid, locked out, invalid, empty username |
| Inventory | Product count, add to cart, cart badge, sort |
| Checkout | Full e2e purchase, empty cart |

## Author

**Mai Ibrahim** — Senior SDET / QA Engineer  
[LinkedIn](https://www.linkedin.com/in/mai-tarek/) · [GitHub](https://github.com/Maiitarek)
