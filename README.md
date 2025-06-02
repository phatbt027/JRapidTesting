# JRapidTesting

A Java-based Selenium testing framework for rapid test automation development. This framework provides a structured approach to creating and maintaining automated tests with a focus on reusability and maintainability.

## Features

- **Base Test Classes**
  - `BaseSeleniumTest`: Core Selenium functionality
  - `BaseCrudScenario`: Common CRUD operations
  - `BaseAuthenticatedCrudScenario`: Authentication and CRUD operations
  - `BaseLoginTest`: Login functionality testing

- **Common Components**
  - `CommonLocators`: Centralized management of web element locators
  - `AuthConfig`: Authentication configuration
  - `AuthLocators`: Authentication-related locators

- **Test Scenarios**
  - User Management Testing
  - Authentication Testing
  - CRUD Operations Testing

## Project Structure

```
src/
├── main/java/com/jrapidtesting/selenium/
│   ├── common/
│   │   └── CommonLocators.java
│   ├── crud/
│   │   ├── BaseCrudScenario.java
│   │   ├── BaseAuthenticatedCrudScenario.java
│   │   ├── AuthConfig.java
│   │   └── AuthLocators.java
│   └── auth/
│       └── BaseLoginTest.java
└── test/java/com/jrapidtesting/selenium/
    ├── crud/
    │   └── UserManagementTest.java
    └── auth/
        └── LoginTest.java
```

## Prerequisites

- Java 8 or higher
- Maven
- WebDriver for your target browsers (Chrome, Firefox, Edge, Safari)

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/JRapidTesting.git
   cd JRapidTesting
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

3. Configure your test environment:
   - Update `application.properties` with your test environment details
   - Configure browser drivers in your system PATH

## Usage

### Creating a New Test Class

1. Extend the appropriate base class:
   ```java
   public class MyTest extends BaseCrudScenario {
       // Your test implementation
   }
   ```

2. Configure locators:
   ```java
   @Override
   protected void setupTestData() {
       super.setupTestData();
       locators = new CommonLocators()
           .withAddButton(By.id("custom-add-button"))
           .withEditButton(By.className("custom-edit-btn"));
   }
   ```

3. Implement test methods:
   ```java
   @Test
   public void testCreateRecord() {
       Map<By, String> data = new HashMap<>();
       data.put(fieldLocator, "value");
       createRecord(data);
   }
   ```

### Running Tests

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=UserManagementTest
```

Run specific test method:
```bash
mvn test -Dtest=UserManagementTest#testCreateUser
```

## Best Practices

1. **Locator Management**
   - Use `CommonLocators` for shared locators
   - Keep page-specific locators in test classes
   - Use meaningful names for locator variables

2. **Test Data**
   - Store test data in `setupTestData()`
   - Use meaningful data values
   - Clean up test data after tests

3. **Assertions**
   - Use descriptive assertion messages
   - Verify both positive and negative scenarios
   - Check for element visibility and content

4. **Browser Support**
   - Test across multiple browsers
   - Use `@ParameterizedTest` for cross-browser testing
   - Handle browser-specific behaviors

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Selenium WebDriver
- JUnit 5
- Maven