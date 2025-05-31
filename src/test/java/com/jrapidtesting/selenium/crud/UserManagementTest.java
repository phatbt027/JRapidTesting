package com.jrapidtesting.selenium.crud;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for user management functionality with authentication.
 * Extends BaseAuthenticatedCrudScenario to include login functionality.
 */
public class UserManagementTest extends BaseAuthenticatedCrudScenario {
    
    @Override
    protected void setupTestData() {
        // Override default browser type to Firefox
        setBrowserType(BrowserType.FIREFOX);
        
        super.setupTestData();
        
        // Configure authentication
        configureAuth(new AuthConfig(
            "admin",
            "admin123",
            "http://localhost:8080/login"
        )
        .withDashboardUrl("/admin/dashboard")
        .withLoginSuccessUrl("/admin/dashboard")
        .withLogoutSuccessUrl("/login"));
        
        // Configure authentication locators
        configureAuthLocators(new AuthLocators()
            .withUsernameInput(By.id("admin-username"))
            .withPasswordInput(By.id("admin-password"))
            .withLoginButton(By.id("admin-login-button"))
            .withLogoutButton(By.id("admin-logout-button")));
        
        // Initialize user management specific data
        testData.put("baseUrl", "http://localhost:8080/users");
        testData.put("username", "testuser");
        testData.put("email", "test@example.com");
        testData.put("role", "User");
        
        // Override default locators if needed
        addButton = By.id("add-user-button");
        editButton = By.className("edit-user-btn");
        deleteButton = By.className("delete-user-btn");
        saveButton = By.id("save-user-button");
        cancelButton = By.id("cancel-user-button");
        confirmDeleteButton = By.id("confirm-delete-user-button");
        searchInput = By.id("user-search-input");
        searchButton = By.id("user-search-button");
        tableRows = By.cssSelector("#user-table tbody tr");
    }
    
    /**
     * Test with Chrome browser (overrides the default Firefox)
     */
    @Test
    public void testWithChrome() {
        // Override browser type for this specific test
        setBrowserType(BrowserType.CHROME);
        testCreateUser();
    }
    
    /**
     * Test with Firefox browser (uses default from setupTestData)
     */
    @Test
    public void testWithFirefox() {
        // Uses Firefox (default from setupTestData)
        testCreateUser();
    }
    
    /**
     * Test with Edge browser (overrides the default Firefox)
     */
    @Test
    public void testWithEdge() {
        // Override browser type for this specific test
        setBrowserType(BrowserType.EDGE);
        testCreateUser();
    }
    
    /**
     * Test with Safari browser (overrides the default Firefox)
     */
    @Test
    public void testWithSafari() {
        // Override browser type for this specific test
        setBrowserType(BrowserType.SAFARI);
        testCreateUser();
    }
    
    /**
     * Test with multiple browsers using parameterized test
     */
    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void testWithAllBrowsers(BrowserType browserType) {
        // Override browser type for each test iteration
        setBrowserType(browserType);
        testCreateUser();
    }
    
    @Test
    public void testLoginRequired() {
        // Try to access user management page without login
        navigateToCrudPage((String) testData.get("baseUrl"));
        
        // Should be redirected to login page
        assertTrue(driver.getCurrentUrl().contains("/login"),
            "Should be redirected to login page when not authenticated");
    }
    
    @Test
    public void testCreateUser() {
        // Login and navigate to user management page
        navigateToCrudPage((String) testData.get("baseUrl"));
        
        // Prepare user data
        Map<By, String> userData = new HashMap<>();
        userData.put(By.id("username"), (String) testData.get("username"));
        userData.put(By.id("email"), (String) testData.get("email"));
        userData.put(By.id("role"), (String) testData.get("role"));
        
        // Create new user
        createRecord(userData);
        
        // Verify user was created
        assertTrue(verifyRecordExists((String) testData.get("username")),
            "User should be created successfully");
    }
    
    @Test
    public void testUpdateUser() {
        // Login and navigate to user management page
        navigateToCrudPage((String) testData.get("baseUrl"));
        
        // Prepare updated user data
        Map<By, String> updatedData = new HashMap<>();
        updatedData.put(By.id("email"), "updated@example.com");
        updatedData.put(By.id("role"), "Admin");
        
        // Update user
        updateRecord((String) testData.get("username"), updatedData);
        
        // Verify user was updated
        assertTrue(verifyRecordContent((String) testData.get("username"), "updated@example.com"),
            "User email should be updated");
        assertTrue(verifyRecordContent((String) testData.get("username"), "Admin"),
            "User role should be updated");
    }
    
    @Test
    public void testDeleteUser() {
        // Login and navigate to user management page
        navigateToCrudPage((String) testData.get("baseUrl"));
        
        // Delete user
        deleteRecord((String) testData.get("username"));
        
        // Verify user was deleted
        assertFalse(verifyRecordExists((String) testData.get("username")),
            "User should be deleted successfully");
    }
    
    @Test
    public void testSearchUser() {
        // Login and navigate to user management page
        navigateToCrudPage((String) testData.get("baseUrl"));
        
        // Search for user
        searchRecord((String) testData.get("username"));
        
        // Verify search results
        assertTrue(verifyRecordExists((String) testData.get("username")),
            "User should be found in search results");
    }
    
    @Test
    public void testCancelUserCreation() {
        // Login and navigate to user management page
        navigateToCrudPage((String) testData.get("baseUrl"));
        
        // Click add button
        clickAddButton();
        
        // Fill in some data
        Map<By, String> userData = new HashMap<>();
        userData.put(By.id("username"), "cancelleduser");
        userData.put(By.id("email"), "cancel@example.com");
        
        for (Map.Entry<By, String> entry : userData.entrySet()) {
            fillField(entry.getKey(), entry.getValue());
        }
        
        // Click cancel
        clickCancelButton();
        
        // Verify user was not created
        assertFalse(verifyRecordExists("cancelleduser"),
            "User should not be created when cancelled");
    }
    
    @Test
    public void testSessionTimeout() {
        // Login and navigate to user management page
        navigateToCrudPage((String) testData.get("baseUrl"));
        
        // Simulate session timeout (e.g., by clearing cookies)
        driver.manage().deleteAllCookies();
        
        // Try to perform an operation
        try {
            createRecord(new HashMap<>());
            fail("Should not be able to perform operations after session timeout");
        } catch (Exception e) {
            // Expected exception
            assertTrue(driver.getCurrentUrl().contains("/login"),
                "Should be redirected to login page after session timeout");
        }
    }
    
    @Test
    public void testLogout() {
        // Login and navigate to user management page
        navigateToCrudPage((String) testData.get("baseUrl"));
        
        // Perform logout
        logout();
        
        // Verify redirected to login page
        assertTrue(driver.getCurrentUrl().contains("/login"),
            "Should be redirected to login page after logout");
        
        // Try to access user management page again
        driver.get((String) testData.get("baseUrl"));
        
        // Should be redirected to login page
        assertTrue(driver.getCurrentUrl().contains("/login"),
            "Should not be able to access user management page after logout");
    }
} 