package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object of result.html for Selenium test.
 */
public class ResultPage {

    @FindBy(tagName = "a")
    private WebElement homeLink;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void goToHomeLink() {
        homeLink.click();
    }
}
