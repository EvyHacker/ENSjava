package com.fnoor;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SFpageFields {

    static WebDriver driver;

    public SFpageFields(WebDriver driver) {this.driver = driver;
    }

    public static final String SF_SERVICE_URL = "https://politicalnetworks-dev-ed-dev-ed.my.salesforce.com";
    public static final String SF_USERNAME = "gaidarenko1241@gmail.com";
    public static final String SF_PASSWORD = "EasyPass@1241";


    /**       LOGIN      **/

    @FindBy(name = "username") WebElement sf_username;
    @FindBy(name = "pw") WebElement sf_pw;
    @FindBy(name = "Login") WebElement sf_login;

    /**       Navigation Panel      **/

    @FindBy(partialLinkText = "Control_Panel") WebElement sf_controlPanel;
    @FindBy(className = "slds-icon-waffle") WebElement sf_searchApp;
    @FindBy(xpath = "//input[starts-with(@id,\"input-\")]") WebElement sf_searchPar;
    @FindBy(xpath = "//class[contains(text(),\"Mapping Rules\")]") WebElement sf_ENmappRules;


    public void sfLogin(){
        sf_username.sendKeys(SF_USERNAME);
        sf_pw.sendKeys(SF_PASSWORD);
        sf_login.click();
    }

    public void searchENapp(String text){
        sf_searchApp.click();
        sf_searchPar.sendKeys(text);
        sf_searchPar.sendKeys(Keys.ENTER);


    }
}
