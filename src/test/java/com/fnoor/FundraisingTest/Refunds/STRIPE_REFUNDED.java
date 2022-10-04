package com.fnoor.FundraisingTest.Refunds;

import com.fnoor.FundraisingPageDriver;
import com.fnoor.PageFields;
import com.fnoor.WebTestFactory;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestNG;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.fnoor.PageFields.ENLOGIN;


import java.time.LocalDate;

import static com.fnoor.PageFields.ENLOGIN;


public class STRIPE_REFUNDED {


    static FundraisingPageDriver page = new FundraisingPageDriver();
    static String FUNDRAISING_TEST;
    static WebDriver driver;
    static PageFields fields;

    @Parameters({"browser"})
    @BeforeClass(alwaysRun=true)
    public void setUp(String browser) throws MalformedURLException {

        driver = page.createInstance(browser);
        fields = PageFactory.initElements(driver, PageFields.class);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Parameters({"stripeIDEALsingleASNRefund"})
    @Test(groups = {"stripe"})
    public static void stripeIDEALsingleASNRefund() throws InterruptedException {

        driver.navigate().to(ENLOGIN);
        fields.enLogin();
        fields.waitForPageLoad();
        Thread.sleep(2000);
        LocalDate date = LocalDate.now();
        fields.searchSupporter("pb_stripeidealsingleasn_" + date.toString() + "@engagingnetworks.online");
        Thread.sleep(2000);

        // Validate supporter Details
        fields.selectSupporter();
        Thread.sleep(2000);
        Assert.assertTrue("First name missing from supporter details", fields.getSupporterDetails().contains("Stripe"));
        Assert.assertTrue("Last name missing from supporter details", fields.getSupporterDetails().contains("iDEALASN"));
        Assert.assertTrue("Address1 missing from supporter details", fields.getSupporterDetails().contains("1 Hilltop"));
        Assert.assertTrue("City missing from supporter details", fields.getSupporterDetails().contains("Baltimore"));
        Assert.assertTrue("Region name missing from supporter details", fields.getSupporterDetails().contains("MD"));
        Assert.assertTrue("Country name missing from supporter details", fields.getSupporterDetails().contains("US"));
        Assert.assertTrue("Postcode missing from supporter details", fields.getSupporterDetails().contains("20001"));

        // Submit a refund
        fields.expendSingleTransaction("FBS");
        Thread.sleep(2000);
//        fields.validateOriginalReceipt("Original receipt");
//        fields.validateReplacementReceipt("Replacement receipt");
//        fields.validateChangeTaxStatus("Change tax status");
        fields.refundTransaction("Refund donation");
        fields.sendRefundEmail();
        fields.refundTransactionAmount("10.00");
        fields.setRefundReceipt("Refund receipt");
        fields.setRefundTemplate("Default for Donation Refund (single and recurring)");
        fields.submitRefund();
        fields.confirmRefund("Are you sure you wish to refund this donation?");

        fields.waitForPageLoad();
        Thread.sleep(6000);
        fields.validateRefund("RFD");

        // Validate refund transaction
        fields.waitForPageLoad();
        Thread.sleep(4000);
        Assert.assertTrue("Transaction error, amount is incorrect or missing ",
                fields.getSingleTransactionDetails().contains("Amount -10 EUR"));
        Assert.assertTrue("Transaction error, status is incorrect or not present",
                fields.getSingleTransactionDetails().contains("success"));
        Assert.assertTrue("Transaction error, gateway is incorrect or not present",
                fields.getSingleTransactionDetails().contains("Stripe Gateway"));
        Assert.assertTrue("Transaction error, payment type is incorrect or not present",
                fields.getSingleTransactionDetails().contains("TEST: ideal"));

    }

}
