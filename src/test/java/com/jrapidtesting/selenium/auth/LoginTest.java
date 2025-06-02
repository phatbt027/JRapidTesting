package com.jrapidtesting.selenium.auth;

import com.jrapidtesting.selenium.crud.AuthConfig;
import com.jrapidtesting.selenium.crud.AuthLocators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for login functionality.
 * Extends BaseLoginTest to test specific login scenarios.
 */
public class LoginTest extends BaseLoginTest {
    
    @BeforeEach
    @Override
    protected void setupTestData() {
        super.setupTestData();
        
        // Configure test-specific authentication settings
        AuthConfig testConfig = new AuthConfig(
            "testuser",
            "testpass",
            "http://localhost:8080/login"
        ).withLoginSuccessUrl("/dashboard")
         .withLogoutSuccessUrl("/login");
        
        configureAuth(testConfig);
        
        // Configure test-specific locators
        AuthLocators testLocators = new AuthLocators()
            .withUsernameInput(By.id("username"))
            .withPasswordInput(By.id("password"))
            .withLoginButton(By.id("login-button"))
            .withLogoutButton(By.id("logout-button"));
            
        configureAuthLocators(testLocators);
    }
    
    @Test
    @DisplayName("Should successfully login with valid credentials")
    public void testValidLogin() {
        // Given
        String validUsername = "testuser";
        String validPassword = "testpass";
        
        // When
        login(validUsername, validPassword);
        
        // Then
        assertTrue(isLoggedIn(), "User should be logged in with valid credentials");
        assertTrue(driver.getCurrentUrl().contains(authConfig.getLoginSuccessUrl()),
            "Should be redirected to dashboard after successful login");
    }
    
    @Test
    @DisplayName("Should fail login with invalid username")
    public void testInvalidUsername() {
        // Given
        String invalidUsername = "wronguser";
        String validPassword = "testpass";
        
        // When
        login(invalidUsername, validPassword);
        
        // Then
        assertFalse(isLoggedIn(), "User should not be logged in with invalid username");
        assertTrue(driver.getCurrentUrl().contains(authConfig.getLoginUrl()),
            "Should remain on login page after failed login");
    }
    
    @Test
    @DisplayName("Should fail login with invalid password")
    public void testInvalidPassword() {
        // Given
        String validUsername = "testuser";
        String invalidPassword = "wrongpass";
        
        // When
        login(validUsername, invalidPassword);
        
        // Then
        assertFalse(isLoggedIn(), "User should not be logged in with invalid password");
        assertTrue(driver.getCurrentUrl().contains(authConfig.getLoginUrl()),
            "Should remain on login page after failed login");
    }
    
    @Test
    @DisplayName("Should fail login with empty credentials")
    public void testEmptyCredentials() {
        // Given
        String emptyUsername = "";
        String emptyPassword = "";
        
        // When
        login(emptyUsername, emptyPassword);
        
        // Then
        assertFalse(isLoggedIn(), "User should not be logged in with empty credentials");
        assertTrue(driver.getCurrentUrl().contains(authConfig.getLoginUrl()),
            "Should remain on login page after failed login");
    }
    
    @Test
    @DisplayName("Should successfully logout after login")
    public void testLogoutAfterLogin() {
        // Given
        login();
        assertTrue(isLoggedIn(), "User should be logged in before logout");
        
        // When
        logout();
        
        // Then
        assertFalse(isLoggedIn(), "User should be logged out");
        assertTrue(driver.getCurrentUrl().contains(authConfig.getLogoutSuccessUrl()),
            "Should be redirected to login page after logout");
    }
    
    @Test
    @DisplayName("Should maintain session after page refresh")
    public void testSessionPersistence() {
        // Given
        login();
        assertTrue(isLoggedIn(), "User should be logged in initially");
        
        // When
        driver.navigate().refresh();
        
        // Then
        assertTrue(isLoggedIn(), "User should remain logged in after page refresh");
    }
    
    @Test
    @DisplayName("Should handle special characters in credentials")
    public void testSpecialCharactersInCredentials() {
        // Given
        String specialUsername = "test@user.com";
        String specialPassword = "Test@123!";
        
        // When
        login(specialUsername, specialPassword);
        
        // Then
        assertFalse(isLoggedIn(), "User should not be logged in with special characters");
        assertTrue(driver.getCurrentUrl().contains(authConfig.getLoginUrl()),
            "Should remain on login page after failed login");
    }
} 