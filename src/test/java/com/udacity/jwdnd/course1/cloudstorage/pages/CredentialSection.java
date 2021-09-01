package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object of Credential section in home.html for Selenium test.
 */
public class CredentialSection {

    private final WebDriver driver;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(xpath = "//*[@id='credentialUrlText']")
    private WebElement credentialUrlText;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSave")
    private WebElement credentialSaveButton;

    @FindBy(id = "credentialAdd")
    private WebElement credentialAddButton;

    @FindBy(id = "credentialEdit")
    private WebElement credentialEditButton;

    @FindBy(id = "credentialDelete")
    private WebElement credentialDeleteButton;

    public CredentialSection(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void goToCredentialsTab() {
        credentialsTab.click();
    }

    public void fillCredentialForm(String credentialUrl, String credentialUsername, String credentialPassword) {
        this.credentialUrl.sendKeys(credentialUrl);
        this.credentialUsername.sendKeys(credentialUsername);
        this.credentialPassword.sendKeys(credentialPassword);
    }

    public void clickSaveCredential() {
        credentialSaveButton.click();
    }

    public void clickAddCredential() {
        credentialAddButton.click();
    }

    public void clickEditCredential() {
        credentialEditButton.click();
    }

    public void clickDeleteCredential() {
        credentialDeleteButton.click();
    }

    public String getCredentialUrl() {
        return credentialUrl.getAttribute("value");
    }

    public String getCredentialUsername() {
        return credentialUsername.getAttribute("value");
    }

    public String getCredentialPassword() {
        return credentialPassword.getAttribute("value");
    }

    public String getUsernameText(String url) {
        return driver.findElement(By.xpath("//th[text()='" + url + "']/../td[@id='credentialUsernameText']")).getText();
    }

    public String getPasswordText(String url) {
        return driver.findElement(By.xpath("//th[text()='" + url + "']/../td[@id='credentialPasswordText']")).getText();
    }

    public String getUnencryptedPassword() { return this.credentialPassword.getAttribute("value");}

}