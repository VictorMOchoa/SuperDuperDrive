package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    @FindBy(css = "#inputFirstName")
    private WebElement firstNameField;

    @FindBy(css = "#inputLastName")
    private WebElement lastNameField;

    @FindBy(css = "#inputUsername")
    private WebElement usernameField;

    @FindBy(css = "#inputPassword")
    private WebElement passwordField;

    @FindBy(css = "#signupSubmitBtn")
    private WebElement submitButton;

    private final WebDriver driver;

    public SignUpPage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void doSignUp(String firstName, String lastName, String username, String password) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + firstName + "';", firstNameField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + lastName + "';", lastNameField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", usernameField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", passwordField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
    }
}

