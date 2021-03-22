package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(css="#inputUsername")
    private WebElement usernameField;

    @FindBy(css="#inputPassword")
    private WebElement passwordField;

    private final WebDriver driver;

    @FindBy(css="#loginSubmitBtn")
    private WebElement submitButton;

    public LoginPage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void doLogin(String username, String password) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", usernameField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", passwordField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
    }

}
