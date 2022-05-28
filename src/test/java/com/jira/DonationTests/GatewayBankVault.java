package com.jira.DonationTests;

import com.fnoor.FundraisingPageDriver;
import com.fnoor.PageFields;
import org.junit.AfterClass;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class GatewayBankVault {

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

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Parameters({"bankGatewaySecondaryStripeVisa"})
    @Test(groups = { "bank" })
    public static void bankGatewaySecondaryStripeVisa(String testId) throws InterruptedException, IOException {
        page.ensAuthTestVal();
        driver.get("https://test.engagingnetworks.app/page/14596/donate/1");

        fields.setFirstname("Bank");
        fields.setLastname("StripeVisa");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);
        fields.setAppealCode("appeal code test");
        fields.selectDonationAmt("Other");
        fields.selectDonationAmtOther("199.99");

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");

        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4242424242424242");
        fields.setCCV("123");

        fields.submit();
        Thread.sleep(2000);
        fields.waitForPageLoad();
        Assert.assertTrue("Urls are not the same",
                driver.getCurrentUrl().equals("https://test.engagingnetworks.app/page/14596/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("10563"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Stripe Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$199.99"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: visa"));

        page.getSupporterByEmail(FUNDRAISING_TEST="bankGatewaySecondaryStripeVisa", fields);
        page.getSupporterById(FUNDRAISING_TEST="bankGatewaySecondaryStripeVisa", fields);
    }

    @Parameters({"bankGatewaySecondaryStripeEnBank"})
    @Test(groups = { "bank" })
    public static void bankGatewaySecondaryStripeEnBank(String testId) throws InterruptedException, IOException {
        page.ensAuthTestVal();
        driver.get("https://test.engagingnetworks.app/page/14596/donate/1");

        fields.setFirstname("Bank");
        fields.setLastname("StripeEN");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);
        fields.setAppealCode("appeal code test");
        fields.selectDonationAmt("Other");
        fields.selectDonationAmtOther("199.99");

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");

        fields.selectPaymentType("en_bank");
        fields.submit();

        Thread.sleep(2000);
        Assert.assertTrue("Urls are not the same/didn't redirected to the page",
                driver.getCurrentUrl().equals("https://test.engagingnetworks.app/page/14627/donate/1"));

        fields.setFirstname("Bank");
        fields.setLastname("StripeEN");
//		Call the createEmail function
        fields.setEmailAddress(new_email);
        fields.setAppealCode("appeal code test");
        fields.selectDonationAmt("Other");
        fields.selectDonationAmtOther("19.99");

        fields.clickRecurringSinglePaymentchkbox();

        fields.setOtherAmt1("other amount test 1");
        fields.setOtherAmt2("other amount test 2");
        fields.setOtherAmt3("other amount test 3");
        fields.setOtherAmt4("other amount test 4");
        fields.setOtherAmt5("other amount test 5");
        fields.setOtherAmt6("other amount test 6");

        fields.selectPaymentType("en_bank");
        String new_bankAcc = fields.getACHNumberBank();
        fields.setBankAccNumber(new_bankAcc);
        String new_bankRout = fields.getACHNumberBank();
        fields.setBankRoutingNumber(new_bankRout);
        fields.setBankName("ENS_BANK");
        fields.setBankAccType("CHECKING");
        fields.submit();

        Thread.sleep(2000);
        fields.waitForPageLoad();
        Assert.assertTrue("Urls are not the same",
                driver.getCurrentUrl().equals("https://test.engagingnetworks.app/page/14627/donate/2"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("10616"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Bank Payment Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$19.99"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("RECUR_UNMANAGED"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: en_bank"));

        page.getSupporterByEmail(FUNDRAISING_TEST="bankGatewaySecondaryStripeEnBank", fields);
        page.getSupporterById(FUNDRAISING_TEST="bankGatewaySecondaryStripeEnBank", fields);
    }

    @Parameters({"bankGatewaySecondaryStripeEnBank"})
    @Test(groups = { "bank" })
    public static void bankGatewaySecondaryStripeBacs(String testId) throws InterruptedException, IOException {
        page.ensAuthTestVal();
        driver.get("https://test.engagingnetworks.app/page/14596/donate/1");

        fields.setFirstname("Bank");
        fields.setLastname("StripeEN");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);
        fields.setAppealCode("appeal code test");
        fields.selectDonationAmt("Other");
        fields.selectDonationAmtOther("199.99");

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");

        fields.selectPaymentType("bacs_debit");
        fields.submit();

        Thread.sleep(2000);
        Assert.assertTrue("Urls are not the same/didn't redirected to the page",
                driver.getCurrentUrl().equals("https://test.engagingnetworks.app/page/14627/donate/1"));

        fields.setFirstname("Bank");
        fields.setLastname("StripeEN");
//		Call the createEmail function
        fields.setEmailAddress(new_email);
        fields.setAppealCode("appeal code test");
        fields.selectDonationAmt("Other");
        fields.selectDonationAmtOther("19.99");

        fields.clickRecurringSinglePaymentchkbox();

        fields.setOtherAmt1("other amount test 1");
        fields.setOtherAmt2("other amount test 2");
        fields.setOtherAmt3("other amount test 3");
        fields.setOtherAmt4("other amount test 4");
        fields.setOtherAmt5("other amount test 5");
        fields.setOtherAmt6("other amount test 6");
        fields.selectPayCurrency("GBP");

        fields.selectPaymentType("bacs_debit");
        fields.submit();

        Thread.sleep(2000);
        fields.waitForPageLoad();
        Assert.assertTrue("Urls are not the same",
                driver.getCurrentUrl().contains("https://checkout.stripe.com/pay/cs_test"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("10616"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Bank Payment Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$19.99"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("RECUR_UNMANAGED"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: en_bank"));

        page.getSupporterByEmail(FUNDRAISING_TEST="bankGatewaySecondaryStripeBacs", fields);
        page.getSupporterById(FUNDRAISING_TEST="bankGatewaySecondaryStripeBacs", fields);
    }


}
