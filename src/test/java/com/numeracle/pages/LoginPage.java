package com.numeracle.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cynnent.pages.BasePage;

public class LoginPage extends BasePage {
    private static final Logger log = LogManager.getLogger(LoginPage.class);

    @FindBy(xpath = "//input[@type='text']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//span[text()='LOGIN']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) {
        enterText(usernameField, username, "Username Field", "Input");
        log.info("Entered username: {}", username);
    }

    public void enterPassword(String password) {
        enterText(passwordField, password, "Password Field", "Input");
        log.info("Entered password");
    }

    public void clickLoginButton() {
        clickElement(loginButton, "Login Button", "Button");
        log.info("Clicked login button");
    }

    // Other methods specific to the Login page
}
