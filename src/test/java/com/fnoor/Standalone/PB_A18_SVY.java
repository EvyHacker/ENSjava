package com.fnoor.Standalone;

import com.fnoor.FundraisingPageDriver;
import com.fnoor.PageFields;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static com.fnoor.PageFields.*;

public class PB_A18_SVY {

    static FundraisingPageDriver page = new FundraisingPageDriver();
    static String FUNDRAISING_TEST;
    public static WebDriver driver;
    static PageFields fields;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun=true)
    public void setUp(String browser) throws MalformedURLException {
        driver = page.createInstance(browser);
        fields = PageFactory.initElements(driver, PageFields.class);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Parameters({"surveyOPTinQuestionsResponse"})
    @Test(groups = {"standalone"})
    public static void surveyOPTinQuestionsResponse(String testId) throws InterruptedException, IOException {
        page.ensAuthTestVal();
        driver.get("https://test.engagingnetworks.app/page/13351/survey/1?mode=DEMO");

//        driver.get(WEBMAIL);
//        fields.enEmailLogin();

        fields.selectQuestionResponse("Beef");
        fields.selectQuestionResponseAnimal("Dogs");
        fields.chooseQuestionResponseSocial();
        fields.questionResponseHobby("Testing");
        fields.questionResponseMarital("Single");
        String new_email = fields.createEmail(testId);
        fields.clearEmail();
        fields.setEmailAddress(new_email);
        fields.submit();

        Assert.assertTrue(driver.getCurrentUrl().equals("https://test.engagingnetworks.app/page/13351/survey/2"));
        fields.selectResponseHearAboutUs("Radio");
        fields.selectResponseAnimal("None");
        fields.selectResponseColor("Other");
        fields.chooseResponseSMSoptIn();
        fields.chooseResponseMandatoryOptIn();
        fields.questionResponseProfile("Test profile input");
        fields.chooseResponsePreCheckedOptIn("N");
        fields.chooseResponsePreProfileOptIn();
        fields.chooseResponseNewSFoptIn();
        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        Assert.assertTrue("Urls are not the same", driver.getCurrentUrl()
                .equals("https://test.engagingnetworks.app/page/13899/action/1?chain"));
//
        page.getSupporterByEmail(FUNDRAISING_TEST="surveyOPTinQuestionsResponse", fields);
        //page.getQuestionResponseByID(FUNDRAISING_TEST="surveyOPTinQuestionsResponse", fields);

        Assert.assertTrue("Campaign ID not present", supporterEmail.contains("@engagingnetworks"));
       // Assert.assertTrue("How dod you hear about us answer doesn't match", how_did_you_hear_about_us.equals("radio"));

    }
}
