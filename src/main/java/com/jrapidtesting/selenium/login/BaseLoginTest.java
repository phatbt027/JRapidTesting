package com.jrapidtesting.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Base class for login testing functionality.
 * Extends BaseSeleniumTest to provide common login operations.
 */
public class BaseLoginTest extends BaseSeleniumTest {
    
    /**
     * Find and fill in the username field
     * @param usernameLocator The locator for the username field
     * @param username The username to enter
     */
    protected void enterUsername(By usernameLocator, String username) {
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(usernameLocator));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    /**
     * Get the current value of the username field
     * @param usernameLocator The locator for the username field
     * @return The current username value
     */
    protected String getUsernameValue(By usernameLocator) {
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(usernameLocator));
        return usernameField.getAttribute("value");
    }

    /**
     * Find and fill in the password field
     * @param passwordLocator The locator for the password field
     * @param password The password to enter
     */
    protected void enterPassword(By passwordLocator, String password) {
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(passwordLocator));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Click the login button
     * @param loginButtonLocator The locator for the login button
     */
    protected void clickLoginButton(By loginButtonLocator) {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        loginButton.click();
    }

    /**
     * Click the logout button/link
     * @param logoutButtonLocator The locator for the logout button
     */
    protected void logout(By logoutButtonLocator) {
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(logoutButtonLocator));
        logoutButton.click();
    }

    /**
     * Toggle the Remember Me checkbox
     * @param rememberMeLocator The locator for the Remember Me checkbox
     * @param shouldCheck true to check the box, false to uncheck
     */
    protected void toggleRememberMe(By rememberMeLocator, boolean shouldCheck) {
        WebElement rememberMeCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(rememberMeLocator));
        if (rememberMeCheckbox.isSelected() != shouldCheck) {
            rememberMeCheckbox.click();
        }
    }

    /**
     * Check if Remember Me is enabled
     * @param rememberMeLocator The locator for the Remember Me checkbox
     * @return true if Remember Me is checked, false otherwise
     */
    protected boolean isRememberMeEnabled(By rememberMeLocator) {
        WebElement rememberMeCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(rememberMeLocator));
        return rememberMeCheckbox.isSelected();
    }

    /**
     * Perform login with the given credentials
     * @param usernameLocator The locator for the username field
     * @param passwordLocator The locator for the password field
     * @param loginButtonLocator The locator for the login button
     * @param username The username to enter
     * @param password The password to enter
     */
    protected void login(By usernameLocator, By passwordLocator, By loginButtonLocator, 
                        String username, String password) {
        enterUsername(usernameLocator, username);
        enterPassword(passwordLocator, password);
        clickLoginButton(loginButtonLocator);
    }

    /**
     * Perform login with Remember Me option
     * @param usernameLocator The locator for the username field
     * @param passwordLocator The locator for the password field
     * @param loginButtonLocator The locator for the login button
     * @param rememberMeLocator The locator for the Remember Me checkbox
     * @param username The username to enter
     * @param password The password to enter
     * @param rememberMe Whether to enable Remember Me
     */
    protected void loginWithRememberMe(By usernameLocator, By passwordLocator, By loginButtonLocator,
                                      By rememberMeLocator, String username, String password, boolean rememberMe) {
        enterUsername(usernameLocator, username);
        enterPassword(passwordLocator, password);
        toggleRememberMe(rememberMeLocator, rememberMe);
        clickLoginButton(loginButtonLocator);
    }

    /**
     * Check if an error message is displayed
     * @param errorMessageLocator The locator for the error message element
     * @return true if the error message is displayed, false otherwise
     */
    protected boolean isErrorMessageDisplayed(By errorMessageLocator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(errorMessageLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the text of an error message
     * @param errorMessageLocator The locator for the error message element
     * @return The error message text
     */
    protected String getErrorMessage(By errorMessageLocator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(errorMessageLocator)).getText();
    }

    /**
     * Check if user is logged in by verifying a specific element that should be present after login
     * @param loggedInElementLocator The locator for an element that should be present when logged in
     * @return true if the element is present, false otherwise
     */
    protected boolean isLoggedIn(By loggedInElementLocator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(loggedInElementLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
} 