package com.jrapidtesting.selenium.crud;

import org.openqa.selenium.By;

/**
 * Class containing locators for authentication-related elements.
 */
public class AuthLocators {
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

    // Getters
    public By getUsernameInput() {
        return usernameInput;
    }

    public By getPasswordInput() {
        return passwordInput;
    }

    public By getLoginButton() {
        return loginButton;
    }

    public By getLogoutButton() {
        return logoutButton;
    }
} 