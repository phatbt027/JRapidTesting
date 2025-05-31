package com.jrapidtesting.selenium.crud;

import com.jrapidtesting.selenium.BaseSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.BeforeEach;
import java.util.Map;
import java.util.HashMap;

/**
 * Base class for authenticated CRUD scenario testing.
 * Extends BaseCrudScenario to add authentication functionality.
 */
public abstract class BaseAuthenticatedCrudScenario extends BaseCrudScenario {
    
    // Authentication configuration
    protected static class AuthConfig {
        private String username;
        private String password;
        private String loginUrl;
        private String dashboardUrl;
        private String loginSuccessUrl;
        private String logoutSuccessUrl;
        
        public AuthConfig(String username, String password, String loginUrl) {
            this.username = username;
            this.password = password;
            this.loginUrl = loginUrl;
            this.dashboardUrl = "/dashboard";
            this.loginSuccessUrl = "/dashboard";
            this.logoutSuccessUrl = "/login";
        }
        
        public AuthConfig withDashboardUrl(String dashboardUrl) {
            this.dashboardUrl = dashboardUrl;
            return this;
        }
        
        public AuthConfig withLoginSuccessUrl(String loginSuccessUrl) {
            this.loginSuccessUrl = loginSuccessUrl;
            return this;
        }
        
        public AuthConfig withLogoutSuccessUrl(String logoutSuccessUrl) {
            this.logoutSuccessUrl = logoutSuccessUrl;
            return this;
        }
    }
    
    // Login-related locators
    protected static class AuthLocators {
        private By usernameInput;
        private By passwordInput;
        private By loginButton;
        private By logoutButton;
        
        public AuthLocators() {
            this.usernameInput = By.id("username");
            this.passwordInput = By.id("password");
            this.loginButton = By.id("login-button");
            this.logoutButton = By.id("logout-button");
        }
        
        public AuthLocators withUsernameInput(By usernameInput) {
            this.usernameInput = usernameInput;
            return this;
        }
        
        public AuthLocators withPasswordInput(By passwordInput) {
            this.passwordInput = passwordInput;
            return this;
        }
        
        public AuthLocators withLoginButton(By loginButton) {
            this.loginButton = loginButton;
            return this;
        }
        
        public AuthLocators withLogoutButton(By logoutButton) {
            this.logoutButton = logoutButton;
            return this;
        }
    }
    
    // Configuration instances
    protected AuthConfig authConfig;
    protected AuthLocators authLocators;
    
    @Override
    protected void setupTestData() {
        super.setupTestData();
        
        // Initialize default authentication configuration
        authConfig = new AuthConfig(
            "admin",
            "admin123",
            "http://localhost:8080/login"
        );
        
        // Initialize default authentication locators
        authLocators = new AuthLocators();
        
        // Add authentication data to test data map
        testData.put("adminUsername", authConfig.username);
        testData.put("adminPassword", authConfig.password);
        testData.put("loginUrl", authConfig.loginUrl);
    }
    
    /**
     * Configure authentication settings
     * @param config The authentication configuration
     */
    protected void configureAuth(AuthConfig config) {
        this.authConfig = config;
        testData.put("adminUsername", config.username);
        testData.put("adminPassword", config.password);
        testData.put("loginUrl", config.loginUrl);
    }
    
    /**
     * Configure authentication locators
     * @param locators The authentication locators
     */
    protected void configureAuthLocators(AuthLocators locators) {
        this.authLocators = locators;
    }
    
    /**
     * Login with configured credentials
     */
    protected void loginAsAdmin() {
        navigateToCrudPage(authConfig.loginUrl);
        
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(authLocators.usernameInput));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(authLocators.passwordInput));
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(authLocators.loginButton));
        
        usernameField.clear();
        usernameField.sendKeys(authConfig.username);
        
        passwordField.clear();
        passwordField.sendKeys(authConfig.password);
        
        loginBtn.click();
        
        // Wait for login to complete
        wait.until(ExpectedConditions.urlContains(authConfig.loginSuccessUrl));
    }
    
    /**
     * Logout from the application
     */
    protected void logout() {
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(authLocators.logoutButton));
        logoutBtn.click();
        
        // Wait for logout to complete
        wait.until(ExpectedConditions.urlContains(authConfig.logoutSuccessUrl));
    }
    
    /**
     * Navigate to CRUD page with authentication
     * @param url The URL of the CRUD page
     */
    @Override
    protected void navigateToCrudPage(String url) {
        // First login
        loginAsAdmin();
        
        // Then navigate to the CRUD page
        super.navigateToCrudPage(url);
    }
    
    /**
     * Verify if user is logged in
     * @return true if logged in, false otherwise
     */
    protected boolean isLoggedIn() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(authLocators.logoutButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Ensure user is logged in before performing operations
     */
    protected void ensureLoggedIn() {
        if (!isLoggedIn()) {
            loginAsAdmin();
        }
    }
    
    /**
     * Create a new record with authentication check
     * @param data Map of field locators and values
     */
    @Override
    protected void createRecord(Map<By, String> data) {
        ensureLoggedIn();
        super.createRecord(data);
    }
    
    /**
     * Update an existing record with authentication check
     * @param searchContent The content to find the record
     * @param data Map of field locators and new values
     */
    @Override
    protected void updateRecord(String searchContent, Map<By, String> data) {
        ensureLoggedIn();
        super.updateRecord(searchContent, data);
    }
    
    /**
     * Delete a record with authentication check
     * @param searchContent The content to find the record
     */
    @Override
    protected void deleteRecord(String searchContent) {
        ensureLoggedIn();
        super.deleteRecord(searchContent);
    }
    
    /**
     * Search for a record with authentication check
     * @param searchTerm The term to search for
     */
    @Override
    protected void searchRecord(String searchTerm) {
        ensureLoggedIn();
        super.searchRecord(searchTerm);
    }
} 