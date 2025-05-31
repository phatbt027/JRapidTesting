package com.jrapidtesting.selenium.crud;

import com.jrapidtesting.selenium.BaseSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Base class for CRUD scenario testing.
 * Provides common functionality for Create, Read, Update, and Delete operations.
 */
public abstract class BaseCrudScenario extends BaseSeleniumTest {
    
    // Common locators - can be overridden by subclasses
    protected By addButton = By.id("add-button");
    protected By editButton = By.id("edit-button");
    protected By deleteButton = By.id("delete-button");
    protected By saveButton = By.id("save-button");
    protected By cancelButton = By.id("cancel-button");
    protected By confirmDeleteButton = By.id("confirm-delete-button");
    protected By searchInput = By.id("search-input");
    protected By searchButton = By.id("search-button");
    protected By tableRows = By.cssSelector("table tbody tr");
    
    // Test data storage
    protected Map<String, Object> testData;
    
    // Default browser type
    protected BrowserType browserType = BrowserType.CHROME;
    
    @BeforeEach
    public void setup() {
        // Initialize browser
        initDriver(browserType);
        // Initialize test data
        testData = new HashMap<>();
        // Setup test data
        setupTestData();
    }
    
    @AfterEach
    public void tearDown() {
        // Cleanup resources
        cleanup();
    }
    
    /**
     * Abstract method to be implemented by subclasses to setup test data
     */
    protected abstract void setupTestData();
    
    /**
     * Set the browser type for the test
     * @param browserType The type of browser to use
     */
    protected void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }
    
    /**
     * Navigate to the CRUD page
     * @param url The URL of the CRUD page
     */
    protected void navigateToCrudPage(String url) {
        navigateTo(url);
    }
    
    /**
     * Click the add button to create a new record
     */
    protected void clickAddButton() {
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addButton));
        addBtn.click();
    }
    
    /**
     * Fill in a form field
     * @param fieldLocator The locator for the field
     * @param value The value to enter
     */
    protected void fillField(By fieldLocator, String value) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(fieldLocator));
        field.clear();
        field.sendKeys(value);
    }
    
    /**
     * Click the save button
     */
    protected void clickSaveButton() {
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveBtn.click();
    }
    
    /**
     * Click the cancel button
     */
    protected void clickCancelButton() {
        WebElement cancelBtn = wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        cancelBtn.click();
    }
    
    /**
     * Search for a record
     * @param searchTerm The term to search for
     */
    protected void searchRecord(String searchTerm) {
        WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
        searchField.clear();
        searchField.sendKeys(searchTerm);
        
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchBtn.click();
    }
    
    /**
     * Get all table rows
     * @return List of table row elements
     */
    protected List<WebElement> getTableRows() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));
    }
    
    /**
     * Find a row by its content
     * @param content The content to search for
     * @return The matching row element, or null if not found
     */
    protected WebElement findRowByContent(String content) {
        List<WebElement> rows = getTableRows();
        for (WebElement row : rows) {
            if (row.getText().contains(content)) {
                return row;
            }
        }
        return null;
    }
    
    /**
     * Click the edit button for a specific row
     * @param row The row element
     */
    protected void clickEditButton(WebElement row) {
        WebElement editBtn = row.findElement(editButton);
        editBtn.click();
    }
    
    /**
     * Click the delete button for a specific row
     * @param row The row element
     */
    protected void clickDeleteButton(WebElement row) {
        WebElement deleteBtn = row.findElement(deleteButton);
        deleteBtn.click();
    }
    
    /**
     * Confirm deletion
     */
    protected void confirmDelete() {
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteButton));
        confirmBtn.click();
    }
    
    /**
     * Verify a record exists in the table
     * @param content The content to verify
     * @return true if the record exists, false otherwise
     */
    protected boolean verifyRecordExists(String content) {
        return findRowByContent(content) != null;
    }
    
    /**
     * Create a new record
     * @param data Map of field locators and values
     */
    protected void createRecord(Map<By, String> data) {
        clickAddButton();
        for (Map.Entry<By, String> entry : data.entrySet()) {
            fillField(entry.getKey(), entry.getValue());
        }
        clickSaveButton();
    }
    
    /**
     * Update an existing record
     * @param searchContent The content to find the record
     * @param data Map of field locators and new values
     */
    protected void updateRecord(String searchContent, Map<By, String> data) {
        WebElement row = findRowByContent(searchContent);
        if (row != null) {
            clickEditButton(row);
            for (Map.Entry<By, String> entry : data.entrySet()) {
                fillField(entry.getKey(), entry.getValue());
            }
            clickSaveButton();
        }
    }
    
    /**
     * Delete a record
     * @param searchContent The content to find the record
     */
    protected void deleteRecord(String searchContent) {
        WebElement row = findRowByContent(searchContent);
        if (row != null) {
            clickDeleteButton(row);
            confirmDelete();
        }
    }
    
    /**
     * Verify a record's content
     * @param searchContent The content to find the record
     * @param expectedContent The expected content to verify
     * @return true if the content matches, false otherwise
     */
    protected boolean verifyRecordContent(String searchContent, String expectedContent) {
        WebElement row = findRowByContent(searchContent);
        return row != null && row.getText().contains(expectedContent);
    }
} 