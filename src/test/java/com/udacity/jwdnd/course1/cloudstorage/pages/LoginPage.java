package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object of login.html for Selenium test.
 */
public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "loginButton")
    private WebElement submitButton;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id = "logout-msg")
    private WebElement logoutMessage;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String password) {
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.submitButton.click();
    }

    public void goToSignupLink() {
        signupLink.click();
    }

}
