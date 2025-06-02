package com.jrapidtesting.selenium.crud;

/**
 * Configuration class for authentication settings.
 */
public class AuthConfig {
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

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getDashboardUrl() {
        return dashboardUrl;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }
} 