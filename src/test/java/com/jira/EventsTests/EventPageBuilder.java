package com.jira.EventsTests;

import com.fnoor.FundraisingPageDriver;
import com.fnoor.PageFields;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.fnoor.PageFields.ENLOGIN;

public class EventPageBuilder {

    static FundraisingPageDriver page = new FundraisingPageDriver();
    static String FUNDRAISING_TEST;
    public static WebDriver driver;
    static PageFields fields;

    @Parameters({"browser"})
    @BeforeClass(alwaysRun=true)
    public void setUp(String browser) throws MalformedURLException {
        driver = page.createInstance(browser);
        fields = PageFactory.initElements(driver, PageFields.class);
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Parameters({"EventsTicketSpesChars"})
    @Test(groups = { "eventBuilder" })
    public static void EventsTicketSpesChars(String testId) throws InterruptedException, IOException {

        driver.navigate().to(ENLOGIN);
        fields.enLogin();
        fields.waitForPageLoad();
        Thread.sleep(2000);
        LocalDate date = LocalDate.now();

        driver.navigate().to("https://test.engagingnetworks.app/index.html#pages/14675/admin");

        //Validate IATSsingle transaction
        fields.clickTicketsSettings();
        fields.validateTicketNameChars("str2 + ");
        Thread.sleep(2000);
    }

}
