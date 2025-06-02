package com.jrapidtesting.selenium.common;

import org.openqa.selenium.By;

/**
 * Class containing common locators used across different test scenarios.
 * Provides a centralized place to manage and reuse locators.
 */
public class CommonLocators {
    // Common button locators
    private By addButton = By.id("add-button");
    private By editButton = By.id("edit-button");
    private By deleteButton = By.id("delete-button");
    private By saveButton = By.id("save-button");
    private By cancelButton = By.id("cancel-button");
    private By confirmDeleteButton = By.id("confirm-delete-button");
    private By searchButton = By.id("search-button");
    
    // Common input locators
    private By searchInput = By.id("search-input");
    
    // Common table locators
    private By tableRows = By.cssSelector("table tbody tr");
    
    // Common message locators
    private By successMessage = By.className("success-message");
    private By errorMessage = By.className("error-message");
    private By warningMessage = By.className("warning-message");
    
    // Common navigation locators
    private By homeLink = By.id("home-link");
    private By backLink = By.id("back-link");
    private By nextLink = By.id("next-link");
    
    // Getters
    public By getAddButton() {
        return addButton;
    }
    
    public By getEditButton() {
        return editButton;
    }
    
    public By getDeleteButton() {
        return deleteButton;
    }
    
    public By getSaveButton() {
        return saveButton;
    }
    
    public By getCancelButton() {
        return cancelButton;
    }
    
    public By getConfirmDeleteButton() {
        return confirmDeleteButton;
    }
    
    public By getSearchButton() {
        return searchButton;
    }
    
    public By getSearchInput() {
        return searchInput;
    }
    
    public By getTableRows() {
        return tableRows;
    }
    
    public By getSuccessMessage() {
        return successMessage;
    }
    
    public By getErrorMessage() {
        return errorMessage;
    }
    
    public By getWarningMessage() {
        return warningMessage;
    }
    
    public By getHomeLink() {
        return homeLink;
    }
    
    public By getBackLink() {
        return backLink;
    }
    
    public By getNextLink() {
        return nextLink;
    }
    
    // Builder methods for customizing locators
    public CommonLocators withAddButton(By addButton) {
        this.addButton = addButton;
        return this;
    }
    
    public CommonLocators withEditButton(By editButton) {
        this.editButton = editButton;
        return this;
    }
    
    public CommonLocators withDeleteButton(By deleteButton) {
        this.deleteButton = deleteButton;
        return this;
    }
    
    public CommonLocators withSaveButton(By saveButton) {
        this.saveButton = saveButton;
        return this;
    }
    
    public CommonLocators withCancelButton(By cancelButton) {
        this.cancelButton = cancelButton;
        return this;
    }
    
    public CommonLocators withConfirmDeleteButton(By confirmDeleteButton) {
        this.confirmDeleteButton = confirmDeleteButton;
        return this;
    }
    
    public CommonLocators withSearchButton(By searchButton) {
        this.searchButton = searchButton;
        return this;
    }
    
    public CommonLocators withSearchInput(By searchInput) {
        this.searchInput = searchInput;
        return this;
    }
    
    public CommonLocators withTableRows(By tableRows) {
        this.tableRows = tableRows;
        return this;
    }
    
    public CommonLocators withSuccessMessage(By successMessage) {
        this.successMessage = successMessage;
        return this;
    }
    
    public CommonLocators withErrorMessage(By errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
    
    public CommonLocators withWarningMessage(By warningMessage) {
        this.warningMessage = warningMessage;
        return this;
    }
    
    public CommonLocators withHomeLink(By homeLink) {
        this.homeLink = homeLink;
        return this;
    }
    
    public CommonLocators withBackLink(By backLink) {
        this.backLink = backLink;
        return this;
    }
    
    public CommonLocators withNextLink(By nextLink) {
        this.nextLink = nextLink;
        return this;
    }
} 