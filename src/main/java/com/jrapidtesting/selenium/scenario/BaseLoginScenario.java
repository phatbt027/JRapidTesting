package com.jrapidtesting.selenium.scenario;

import com.jrapidtesting.selenium.BaseLoginTest;
import org.openqa.selenium.By;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for login test scenarios.
 * Provides a framework for running test scenarios with steps.
 */
public abstract class BaseLoginScenario extends BaseLoginTest {
    
    protected List<TestStep> steps;
    protected TestContext context;
    
    @BeforeEach
    public void setupScenario() {
        // Initialize Chrome browser
        initChromeDriver();
        // Initialize steps list
        steps = new ArrayList<>();
        // Initialize test context
        context = new TestContext();
        // Setup scenario
        setupScenarioSteps();
    }
    
    @AfterEach
    public void tearDownScenario() {
        // Cleanup resources
        cleanup();
    }
    
    /**
     * Abstract method to be implemented by concrete scenario classes
     * to define their test steps
     */
    protected abstract void setupScenarioSteps();
    
    /**
     * Add a step to the scenario
     * @param step The test step to add
     */
    protected void addStep(TestStep step) {
        steps.add(step);
    }
    
    /**
     * Run the scenario
     */
    protected void runScenario() {
        for (TestStep step : steps) {
            step.execute(context);
        }
    }
    
    /**
     * Test context class to store data between steps
     */
    protected static class TestContext {
        private String username;
        private String password;
        private boolean rememberMe;
        private String expectedErrorMessage;
        private String actualErrorMessage;
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getUsername() {
            return username;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setRememberMe(boolean rememberMe) {
            this.rememberMe = rememberMe;
        }
        
        public boolean isRememberMe() {
            return rememberMe;
        }
        
        public void setExpectedErrorMessage(String expectedErrorMessage) {
            this.expectedErrorMessage = expectedErrorMessage;
        }
        
        public String getExpectedErrorMessage() {
            return expectedErrorMessage;
        }
        
        public void setActualErrorMessage(String actualErrorMessage) {
            this.actualErrorMessage = actualErrorMessage;
        }
        
        public String getActualErrorMessage() {
            return actualErrorMessage;
        }
    }
    
    /**
     * Interface for test steps
     */
    protected interface TestStep {
        void execute(TestContext context);
    }
    
    /**
     * Common test steps that can be reused across scenarios
     */
    protected class CommonSteps {
        protected final By usernameField;
        protected final By passwordField;
        protected final By loginButton;
        protected final By rememberMeCheckbox;
        protected final By errorMessage;
        protected final By dashboard;
        protected final By logoutButton;
        
        public CommonSteps(By usernameField, By passwordField, By loginButton,
                         By rememberMeCheckbox, By errorMessage, By dashboard, By logoutButton) {
            this.usernameField = usernameField;
            this.passwordField = passwordField;
            this.loginButton = loginButton;
            this.rememberMeCheckbox = rememberMeCheckbox;
            this.errorMessage = errorMessage;
            this.dashboard = dashboard;
            this.logoutButton = logoutButton;
        }
        
        public TestStep navigateToLoginPage(String url) {
            return context -> navigateTo(url);
        }
        
        public TestStep enterUsername() {
            return context -> enterUsername(usernameField, context.getUsername());
        }
        
        public TestStep enterPassword() {
            return context -> enterPassword(passwordField, context.getPassword());
        }
        
        public TestStep toggleRememberMe() {
            return context -> toggleRememberMe(rememberMeCheckbox, context.isRememberMe());
        }
        
        public TestStep clickLogin() {
            return context -> clickLoginButton(loginButton);
        }
        
        public TestStep verifyLoginSuccess() {
            return context -> assertTrue(isLoggedIn(dashboard), "User should be logged in and see the dashboard");
        }
        
        public TestStep verifyErrorMessage() {
            return context -> {
                assertTrue(isErrorMessageDisplayed(errorMessage), "Error message should be displayed");
                assertEquals(context.getExpectedErrorMessage(), getErrorMessage(errorMessage),
                    "Error message should match expected message");
            };
        }
        
        public TestStep logout() {
            return context -> logout(logoutButton);
        }
        
        public TestStep verifyUsernamePersistence() {
            return context -> {
                String persistedUsername = getUsernameValue(usernameField);
                if (context.isRememberMe()) {
                    assertEquals(context.getUsername(), persistedUsername,
                        "Username should be persisted when Remember Me is enabled");
                } else {
                    assertTrue(persistedUsername.isEmpty(),
                        "Username should not be persisted when Remember Me is disabled");
                }
            };
        }
    }
} 