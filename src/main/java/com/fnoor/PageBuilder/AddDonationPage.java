package com.fnoor.PageBuilder;


import com.fnoor.FundraisingPageDriver;
import com.fnoor.PageFields;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.fnoor.PageFields.ENLOGIN;

public class AddDonationPage {

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

@Parameters({"pageDonation"})
@Test(groups = { "page" })
    public static void pageDonation(String testId) throws InterruptedException, IOException {

        driver.navigate().to(ENLOGIN);
        fields.enLogin();
        fields.waitForPageLoad();
        Thread.sleep(2000);
        LocalDate date = LocalDate.now();

        fields.clickPages();
        fields.clickCreatePage();
        fields.clickCreateDonation();
        fields.clickCreateDonationNext();
        fields.addDPageName("IATS Donation Selenium");
        fields.clickAddGateway();
        fields.clickSearchGateway();
        fields.selectGateway("IATS NA");


//        //Validate IATSsingle transaction
//        fields.searchSupporter("pb_iatssingle_" + date.toString() + "@engagingnetworks.online");
//        Thread.sleep(2000);
//
//        // Validate supporter Details
//        fields.selectSupporter();
//        Thread.sleep(2000);
//        Assert.assertTrue("First name missing from supporter details", fields.getSupporterDetails().contains("IATS"));
//        Assert.assertTrue("Last name missing from supporter details", fields.getSupporterDetails().contains("Single"));
//        Assert.assertTrue("Address1 missing from supporter details", fields.getSupporterDetails().contains("1 Hilltop"));
//        Assert.assertTrue("City missing from supporter details", fields.getSupporterDetails().contains("Baltimore"));
//        Assert.assertTrue("Region name missing from supporter details", fields.getSupporterDetails().contains("MD"));
//        Assert.assertTrue("Country name missing from supporter details", fields.getSupporterDetails().contains("US"));
//        Assert.assertTrue("Postcode missing from supporter details", fields.getSupporterDetails().contains("20001"));
//
//        //Validate transaction details
//        fields.expendTransaction();
//        Thread.sleep(2000);
//        Assert.assertTrue("Transaction error, amount is incorrect or missing ",
//                fields.getTransactionDetails().contains("Amount 15 USD"));
//        Assert.assertTrue("Transaction error, status is incorrect or not present",
//                fields.getTransactionDetails().contains("success"));
//        Assert.assertTrue("Transaction error, gateway is incorrect or not present",
//                fields.getTransactionDetails().contains("IATS North America"));
//        Assert.assertTrue("Transaction error, payment type is incorrect or not present",
//                fields.getTransactionDetails().contains("VISA"));
//        Assert.assertTrue("Transaction error, transaction type is incorrect or not present",
//                fields.getTransactionDetails().contains("Recurring? N"));
//        Assert.assertTrue("Transaction error, campaign ID is missing or incorrect",
//                fields.getTransactionDetails().contains("3509"));
//
//        //Validate IATS NA Recurring transaction
//        fields.nextSupporter("pb_iatsrecurring_" + date.toString() + "@engagingnetworks.online");
//        Thread.sleep(2000);
//
//        // Validate supporter Details
//        fields.selectSupporter();
//        Thread.sleep(2000);
//        Assert.assertTrue("First name missing from supporter details", fields.getSupporterDetails().contains("IATS"));
//        Assert.assertTrue("Last name missing from supporter details", fields.getSupporterDetails().contains("Recurring"));
//        Assert.assertTrue("Address1 missing from supporter details", fields.getSupporterDetails().contains("1 Hilltop"));
//        Assert.assertTrue("City missing from supporter details", fields.getSupporterDetails().contains("Baltimore"));
//        Assert.assertTrue("Region name missing from supporter details", fields.getSupporterDetails().contains("MD"));
//        Assert.assertTrue("Country name missing from supporter details", fields.getSupporterDetails().contains("US"));
//        Assert.assertTrue("Postcode missing from supporter details", fields.getSupporterDetails().contains("20001"));
//
//        //Validate transaction details
//        fields.expendTransaction();
//        Thread.sleep(2000);
//        Assert.assertTrue("Transaction error, amount is incorrect or missing ",
//                fields.getTransactionDetails().contains("Amount 15 USD"));
//        Assert.assertTrue("Transaction error, status is incorrect or not present",
//                fields.getTransactionDetails().contains("success"));
//        Assert.assertTrue("Transaction error, gateway is incorrect or not present",
//                fields.getTransactionDetails().contains("IATS North America"));
//        Assert.assertTrue("Transaction error, payment type is incorrect or not present",
//                fields.getTransactionDetails().contains("VISA"));
//        Assert.assertTrue("Transaction error, transaction type is incorrect or not present",
//                fields.getTransactionDetails().contains("Recurring? Y"));
//        Assert.assertTrue("Transaction error, campaign ID is missing or incorrect",
//                fields.getTransactionDetails().contains("3510"));

    }
}
