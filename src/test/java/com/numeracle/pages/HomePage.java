package com.numeracle.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cynnent.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomePage extends BasePage {
    private Logger log = LogManager.getLogger(HomePage.class);
    
    @FindBy(xpath = "//div[@class='ui-toast-message-content']/div")
    private WebElement toasterMessage;
    
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getLoginConfirmationMessage() {
        String message = getElementText(toasterMessage);
        log.info("Retrieved login confirmation message: {}", message);
        return message;
    }
}
