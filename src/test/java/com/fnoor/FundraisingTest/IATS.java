package com.fnoor.FundraisingTest;

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

public class IATS {

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

    @Parameters({"IATSSingle"})
    @Test(groups = { "IATS" })
    public static void IATSSingle(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/841/donate/1?mode=DEMO");
        fields.waitForPageLoad();
        Thread.sleep(2000);
        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("Single");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.setOtherAmt1("other amount test1");
        fields.setOtherAmt2("other amount test2");
        fields.setOtherAmt3("other amount test3");
        fields.setOtherAmt4("other amount test4");
        fields.setAppealCode("appeal code test");
        fields.setDirectMyGift("direct gift test");
        fields.setAdditionalComments("additional comments");
        fields.setTaxDeductible("Y");

        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

//		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/841/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("3509"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("VISA"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSSingle", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSSingle", fields);

    }

    @Parameters({"IATSRecurring"})
    @Test(groups = { "IATS" })
    public static void IATSRecurring(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/861/donate/1?mode=DEMO");

        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("Recurring");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.setRecurDay("23");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        LocalDate endDate = LocalDate.now().plusYears(1);
        fields.setRecurEndDate(dtf.format(endDate).toString());
        fields.setRecurFreq("MONTHLY");

        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/861/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("3510"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("VISA"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSRecurring", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSRecurring", fields);
    }

    @Parameters({"IATSACHSingle"})
    @Test(groups = { "IATS" })
    public static void IATSACHSingle(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/13706/donate/1");

        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("ACH_Single");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.setRegion("MD");
        fields.setPostCode("20001");
        fields.setCountry("US");

        fields.submit();

        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("1.00");
        fields.selectPaymentType("ACHEFT");
        //fields.selectPayCurrency("GBP");
        fields.setCCName("Unit Tester");
        fields.selectBankAccType("CHECKING");
        fields.setBankAccNumber("12345678");
        fields.setBankRoutingNumber("111111111");
        fields.submit();


        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same",
                myurl.equals("https://test.engagingnetworks.app/page/13706/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("9580"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$1.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("BANK_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("ACHEFT"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSACHSingle", fields);
        page.getSupporterByEmailIATSChecking(FUNDRAISING_TEST = "IATSACHSingle", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSACHSingle", fields);

    }


    @Parameters({"IATSACHRecurring"})
    @Test(groups = { "IATS" })
    public static void IATSACHRecurring(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/5724/donate/1?ea.tracking.id=Google&mode=DEMO");

        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("ACH_Recurring");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.setRegion("MD");
        fields.setPostCode("20001");
        fields.setCountry("US");

        fields.submit();

        fields.setRecurDay("23");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        LocalDate endDate = LocalDate.now().plusYears(1);
        fields.setRecurEndDate(dtf.format(endDate).toString());
        fields.setRecurFreq("MONTHLY");

        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("1.00");
        fields.selectPaymentType("ACHEFT");
        fields.selectPayCurrency("GBP");
        fields.setCCName("Unit Tester");
        fields.selectBankAccType("SAVING");
        fields.setBankAccNumber("1234567");
        fields.setBankRoutingNumber("000000000");

        fields.submit();


        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/5724/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("4542"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$1.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("BANK_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("ACHEFT"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSACHRecurring", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSACHRecurring", fields);
        page.getSupporterByEmailIATSSaving(FUNDRAISING_TEST = "IATSACHRecurring", fields);

    }

    @Parameters({"IATSACHRecurPaymenttypelogic"})
    @Test(groups = { "IATS" })
    public static void IATSACHRecurPaymenttypelogic(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/5725/donate/1?mode=DEMO");

        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("1.00");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("RecurringLogic");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.selectPaymentType("Visa");
        fields.selectPayCurrency("USD");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        // verify that transaction fails

        Assert.assertTrue(driver.getCurrentUrl().equals("https://test.engagingnetworks.app/page/5725/donate/2?val"));

        fields.selectPaymentType("ACHEFT");
        fields.selectPayCurrency("USD");
        fields.setCCName("Unit Tester");
        fields.selectBankAccType("CHECKING");
        fields.setBankAccNumber("1234567");
        fields.setBankRoutingNumber("000000000");


        // fields.clickRecurringSinglePaymentchkbox();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        fields.setRecurFreq("MONTHLY");

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/5725/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("4543"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$1.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("BANK_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("ACHEFT"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSACHRecurPaymenttypelogic", fields);
        page.getSupporterByEmailIATSChecking(FUNDRAISING_TEST = "IATSACHRecurPaymenttypelogic", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSACHRecurPaymenttypelogic", fields);

    }

    @Parameters({"IATSSingleOtherAUD"})
    @Test(groups = { "IATS" })
    public static void IATSSingleOtherAUD(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/13534/donate/1");
        fields.waitForPageLoad();
        Thread.sleep(2000);
        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("SingleOtherAUD");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.selectOther1("EastCoast");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

//		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/13534/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("9398"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("AUD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VISA"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSSingleOtherAUD", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSSingleOtherAUD", fields);

    }

    @Parameters({"IATSSingleOtherCAD"})
    @Test(groups = { "IATS" })
    public static void IATSSingleOtherCAD(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/13534/donate/1");
        fields.waitForPageLoad();
        Thread.sleep(2000);
        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("SingleOtherCAD");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.selectOther1("WestCoast");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

//		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/13534/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("9398"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("CAD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VISA"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSSingleOtherCAD", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSSingleOtherCAD", fields);

    }

    @Parameters({"IATSSingleOtherUSD"})
    @Test(groups = { "IATS" })
    public static void IATSSingleOtherUSD(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/13534/donate/1");
        fields.waitForPageLoad();
        Thread.sleep(2000);
        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("SingleOtherUSD");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.selectOther1("");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

//		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/13534/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("9398"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("VISA"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSSingleOtherUSD", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSSingleOtherUSD", fields);

    }

    @Parameters({"IATSSingleCurrencyBasedUSD"})
    @Test(groups = { "IATS" })
    public static void IATSSingleCurrencyBasedUSD(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/13535/donate/1");
        fields.waitForPageLoad();
        Thread.sleep(2000);
        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("SingleCurrencyBasedUSD");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

//		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/13535/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("9399"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("VISA"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSSingleCurrencyBasedUSD", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSSingleCurrencyBasedUSD", fields);

    }


    @Parameters({"IATSSingleCurrencyBadGatewayCAD"})
    @Test(groups = { "IATS" })
    public static void IATSSingleCurrencyBadGatewayCAD(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/13535/donate/1");
        fields.waitForPageLoad();
        Thread.sleep(2000);
        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("IATSSingleCurrencyBadGatewayCAD");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");
        fields.selectPayCurrency("CAD");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

//		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/13535/donate/2?val"));

        String alert = driver.findElement(By.xpath("//li[@class='en__error']")).getText();
        Assert.assertTrue("There is no alerts on the page",
                alert.contains("This transaction has failed as there has been an error in processing your payment."));

    }

    @Parameters({"IATSSingleToRecurUpsale"})
    @Test(groups = { "IATS" })
    public static void IATSSingleToRecurUpsale(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/14492/donate/1");
        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("15.99");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("SingleUpsell");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.setOtherAmt1("other amount test1");
        fields.setOtherAmt2("other amount test2");
        fields.setOtherAmt3("other amount test3");
        fields.setOtherAmt4("other amount test4");
        fields.setAppealCode("appeal code test");
        fields.setDirectMyGift("direct gift test");
        fields.setAdditionalComments("additional comments");
        fields.setTaxDeductible("Y");

        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        fields.validateUsellAmount("Thank you! Before we process your donation, will you start a 10.00 USD monthly gift instead?");
        fields.clickUpsellYes();

//		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/14492/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("10458"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$10.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("VISA"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSSingleToRecurUpsale", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSSingleToRecurUpsale", fields);

    }

    @Parameters({"IATSSingleToRecurNoUpsale"})
    @Test(groups = { "IATS" })
    public static void IATSSingleToRecurNoUpsale(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/14492/donate/1");
        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("15.99");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("SingleUpsell");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.setOtherAmt1("other amount test1");
        fields.setOtherAmt2("other amount test2");
        fields.setOtherAmt3("other amount test3");
        fields.setOtherAmt4("other amount test4");
        fields.setAppealCode("appeal code test");
        fields.setDirectMyGift("direct gift test");
        fields.setAdditionalComments("additional comments");
        fields.setTaxDeductible("Y");

        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        fields.validateUsellAmount("Thank you! Before we process your donation, will you start a 10.00 USD monthly gift instead?");
        fields.clickUpsellNo();

//		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/14492/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("10458"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.99"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("VISA"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSSingleToRecurNoUpsale", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSSingleToRecurNoUpsale", fields);

    }

    @Parameters({"IATSRecurringUpsell"})
    @Test(groups = { "IATS" })
    public static void IATSRecurringUpsell(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/14494/donate/1?mode=DEMO");

        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("25.99");
        fields.selectTitle("Ms");
        fields.setFirstname("IATS");
        fields.setLastname("Recurring");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.setRecurDay("23");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        LocalDate endDate = LocalDate.now().plusYears(1);
        fields.setRecurEndDate(dtf.format(endDate).toString());
        fields.setRecurFreq("MONTHLY");

        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4222222222222220");
        fields.setCCExpiry(new CharSequence[]{"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        fields.validateUsellAmount("Thank you! Before we process your donation, will you make it a 25 USD monthly gift instead?");
        fields.clickUpsellYes();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/14494/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("10460"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("IATS North America"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$25.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("VISA"));

        page.getSupporterByEmail(FUNDRAISING_TEST = "IATSRecurringUpsell", fields);
        page.getSupporterById(FUNDRAISING_TEST = "IATSRecurringUpsell", fields);
    }

    @Test(groups = { "IATS" })
    public static void IATSvalidateTransaction() throws InterruptedException{

        driver.navigate().to(ENLOGIN);
        fields.enLogin();
        fields.waitForPageLoad();
        Thread.sleep(2000);
        LocalDate date = LocalDate.now();

        //Validate IATSsingle transaction
        fields.searchSupporter("pb_IATSsingle_" + date.toString() + "@engagingnetworks.online");
        Thread.sleep(2000);

        // Validate supporter Details
        fields.selectSupporter();
        Thread.sleep(2000);
        Assert.assertTrue("First name missing from supporter details", fields.getSupporterDetails().contains("IATS"));
        Assert.assertTrue("Last name missing from supporter details", fields.getSupporterDetails().contains("Single"));
        Assert.assertTrue("Address1 missing from supporter details", fields.getSupporterDetails().contains("1 Hilltop"));
        Assert.assertTrue("City missing from supporter details", fields.getSupporterDetails().contains("Baltimore"));
        Assert.assertTrue("Region name missing from supporter details", fields.getSupporterDetails().contains("MD"));
        Assert.assertTrue("Country name missing from supporter details", fields.getSupporterDetails().contains("US"));
        Assert.assertTrue("Postcode missing from supporter details", fields.getSupporterDetails().contains("20001"));

        //Validate transaction details
       fields.expendTransaction();
       Thread.sleep(2000);
        Assert.assertTrue("Transaction error, amount is incorrect or missing ",
                fields.getTransactionDetails().contains("Amount 15 USD"));
        Assert.assertTrue("Transaction error, status is incorrect or not present",
                fields.getTransactionDetails().contains("success"));
        Assert.assertTrue("Transaction error, gateway is incorrect or not present",
                fields.getTransactionDetails().contains("IATS North America"));
        Assert.assertTrue("Transaction error, payment type is incorrect or not present",
                fields.getTransactionDetails().contains("VISA"));
        Assert.assertTrue("Transaction error, transaction type is incorrect or not present",
                fields.getTransactionDetails().contains("Recurring? N"));
        Assert.assertTrue("Transaction error, campaign ID is missing or incorrect",
                fields.getTransactionDetails().contains("3509"));

        //Validate IATS NA Recurring transaction
        fields.nextSupporter("pb_IATSrecurring_" + date.toString() + "@engagingnetworks.online");
        Thread.sleep(2000);

        // Validate supporter Details
        fields.selectSupporter();
        Thread.sleep(2000);
        Assert.assertTrue("First name missing from supporter details", fields.getSupporterDetails().contains("IATS"));
        Assert.assertTrue("Last name missing from supporter details", fields.getSupporterDetails().contains("Recurring"));
        Assert.assertTrue("Address1 missing from supporter details", fields.getSupporterDetails().contains("1 Hilltop"));
        Assert.assertTrue("City missing from supporter details", fields.getSupporterDetails().contains("Baltimore"));
        Assert.assertTrue("Region name missing from supporter details", fields.getSupporterDetails().contains("MD"));
        Assert.assertTrue("Country name missing from supporter details", fields.getSupporterDetails().contains("US"));
        Assert.assertTrue("Postcode missing from supporter details", fields.getSupporterDetails().contains("20001"));

        //Validate transaction details
        fields.expendTransaction();
        Thread.sleep(2000);
        Assert.assertTrue("Transaction error, amount is incorrect or missing ",
                fields.getTransactionDetails().contains("Amount 15 USD"));
        Assert.assertTrue("Transaction error, status is incorrect or not present",
                fields.getTransactionDetails().contains("success"));
        Assert.assertTrue("Transaction error, gateway is incorrect or not present",
                fields.getTransactionDetails().contains("IATS North America"));
        Assert.assertTrue("Transaction error, payment type is incorrect or not present",
                fields.getTransactionDetails().contains("VISA"));
        Assert.assertTrue("Transaction error, transaction type is incorrect or not present",
                fields.getTransactionDetails().contains("Recurring? Y"));
        Assert.assertTrue("Transaction error, campaign ID is missing or incorrect",
                fields.getTransactionDetails().contains("3510"));

    }
}
