# JRapidTesting Framework

A powerful and flexible Selenium-based testing framework for web applications, with built-in support for CRUD operations and authentication.

## Features

- **Multi-Browser Support**: Test your application across different browsers (Chrome, Firefox, Edge, Safari)
- **CRUD Testing**: Built-in support for Create, Read, Update, and Delete operations
- **Authentication**: Flexible authentication handling with configurable login/logout flows
- **Configurable**: Easy to configure and extend for different testing scenarios
- **Clean Architecture**: Well-structured, maintainable, and reusable code

## Framework Structure

```
src/
├── main/java/com/jrapidtesting/selenium/
│   ├── BaseSeleniumTest.java           # Base class for Selenium tests
│   └── crud/
│       ├── BaseCrudScenario.java       # Base class for CRUD operations
│       └── BaseAuthenticatedCrudScenario.java  # Base class for authenticated CRUD operations
└── test/java/com/jrapidtesting/selenium/
    └── crud/
        └── UserManagementTest.java     # Example test implementation
```

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- WebDriver executables for your target browsers

### Dependencies

Add the following dependencies to your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.x.x</version>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.x.x</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Usage

### Basic CRUD Testing

1. Create a test class extending `BaseCrudScenario`:

```java
public class YourTest extends BaseCrudScenario {
    @Override
    protected void setupTestData() {
        super.setupTestData();
        
        // Configure your test data
        testData.put("baseUrl", "http://your-app.com");
        testData.put("searchTerm", "test data");
        
        // Override locators if needed
        addButton = By.id("your-add-button");
        editButton = By.id("your-edit-button");
        // ... other locators
    }
    
    @Test
    public void testCreate() {
        navigateToCrudPage((String) testData.get("baseUrl"));
        
        Map<By, String> data = new HashMap<>();
        data.put(By.id("field1"), "value1");
        data.put(By.id("field2"), "value2");
        
        createRecord(data);
        assertTrue(verifyRecordExists("value1"));
    }
}
```

### Authenticated CRUD Testing

1. Create a test class extending `BaseAuthenticatedCrudScenario`:

```java
public class YourAuthTest extends BaseAuthenticatedCrudScenario {
    @Override
    protected void setupTestData() {
        super.setupTestData();
        
        // Configure authentication
        configureAuth(new AuthConfig(
            "username",
            "password",
            "http://your-app.com/login"
        )
        .withDashboardUrl("/dashboard")
        .withLoginSuccessUrl("/dashboard")
        .withLogoutSuccessUrl("/login"));
        
        // Configure authentication locators
        configureAuthLocators(new AuthLocators()
            .withUsernameInput(By.id("username-field"))
            .withPasswordInput(By.id("password-field"))
            .withLoginButton(By.id("login-button"))
            .withLogoutButton(By.id("logout-button")));
    }
}
```

### Browser Selection

You can specify the browser to use in several ways:

1. **Default Browser for All Tests**:
```java
@Override
protected void setupTestData() {
    setBrowserType(BrowserType.FIREFOX);
    super.setupTestData();
}
```

2. **Per-Test Browser Selection**:
```java
@Test
public void testWithChrome() {
    setBrowserType(BrowserType.CHROME);
    // Your test code
}
```

3. **Test with All Browsers**:
```java
@ParameterizedTest
@EnumSource(BrowserType.class)
public void testWithAllBrowsers(BrowserType browserType) {
    setBrowserType(browserType);
    // Your test code
}
```

## Key Features

### CRUD Operations

- `createRecord()`: Create new records
- `updateRecord()`: Update existing records
- `deleteRecord()`: Delete records
- `searchRecord()`: Search for records
- `verifyRecordExists()`: Verify record presence
- `verifyRecordContent()`: Verify record content

### Authentication

- `loginAsAdmin()`: Login with admin credentials
- `logout()`: Logout from the application
- `isLoggedIn()`: Check login status
- `ensureLoggedIn()`: Ensure user is logged in

### Browser Management

- Support for Chrome, Firefox, Edge, and Safari
- Easy browser selection and configuration
- Parameterized testing across browsers

## Best Practices

1. **Configuration**:
   - Keep test data in `setupTestData()`
   - Override locators in `setupTestData()`
   - Use meaningful test data keys

2. **Authentication**:
   - Configure authentication in `setupTestData()`
   - Use appropriate wait conditions
   - Handle session timeouts

3. **Browser Selection**:
   - Set default browser in `setupTestData()`
   - Override browser for specific tests when needed
   - Use parameterized tests for cross-browser testing

4. **Test Organization**:
   - Group related tests together
   - Use descriptive test names
   - Follow the Arrange-Act-Assert pattern