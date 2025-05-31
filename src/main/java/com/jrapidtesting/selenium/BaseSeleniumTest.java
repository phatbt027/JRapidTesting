package com.jrapidtesting.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Base class for Selenium tests with support for multiple browsers.
 */
public class BaseSeleniumTest {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    /**
     * Supported browser types
     */
    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE,
        SAFARI
    }
    
    /**
     * Initialize the WebDriver with the specified browser type
     * @param browserType The type of browser to use
     */
    protected void initDriver(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            case SAFARI:
                driver = new SafariDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        
        // Configure WebDriver
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    /**
     * Navigate to a URL
     * @param url The URL to navigate to
     */
    protected void navigateTo(String url) {
        driver.get(url);
    }
    
    /**
     * Clean up resources
     */
    protected void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
} 