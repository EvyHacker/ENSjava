package com.fnoor.FundraisingTest;

import com.fnoor.FundraisingPageDriver;
import com.fnoor.PageFields;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PAYSAFE {

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

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Parameters({"paysafeSingle"})
    @Test(groups = { "paysafe" })
    public static void paysafeSingle(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/873/donate/1?mode=DEMO");

        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
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

        fields.selectPaymentType("Visa");
        fields.selectPayCurrency("USD");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4530910000012345");
        fields.setCCExpiry(new CharSequence[] {"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        fields.waitForPageLoad();
        Assert.assertTrue("Urls are not the same",
                myurl.equals("https://test.engagingnetworks.app/page/873/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("3529"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VI"));

        page.getSupporterByEmail(FUNDRAISING_TEST="paysafeSingle", fields);
        page.getSupporterById(FUNDRAISING_TEST="paysafeSingle", fields);
    }

    @Parameters({"paysafeRecurring"})
    @Test(groups = { "paysafe" })
    public static void paysafeRecurring(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/874/donate/1?mode=DEMO");

        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("Recurring");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.waitForPageLoad();
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

        fields.setRecurDay("23");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        LocalDate endDate = LocalDate.now().plusYears(1);
        fields.setRecurEndDate(dtf.format(endDate).toString());
        fields.setRecurFreq("MONTHLY");
        fields.setRecurCount("6");
        fields.setRecurPeriod("2");

        fields.selectPaymentType("Visa");
        fields.selectPayCurrency("USD");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4530910000012345");
        fields.setCCExpiry(new CharSequence[] {"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/874/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("3530"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VI"));

        page.getSupporterByEmail(FUNDRAISING_TEST="paysafeRecurring", fields);
        page.getSupporterById(FUNDRAISING_TEST="paysafeRecurring", fields);
    }


    @Parameters({"paysafeRecurringACH"})
    @Test(groups = { "paysafe" })
    public static void paysafeRecurringACH(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/849/donate/1?mode=DEMO");

        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("RecurringACH");
//		Call the createEmail function

        String new_email = fields.createEmailSeconds(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.waitForPageLoad();
        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        LocalDate endDate = LocalDate.now().plusYears(1);
        fields.setRecurEndDate(dtf.format(endDate).toString());
        fields.setRecurFreq("MONTHLY");

        fields.selectPaymentType("ACH");
        fields.setCCName("ACH");
        String new_bankAcc = fields.getACHNumberBank();
        fields.setBankAccNumber(new_bankAcc);
        fields.setBankRoutingNumber("052000113");

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same",
                myurl.equals("https://test.engagingnetworks.app/page/849/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("3531"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("BANK_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: ACH"));


        page.getSupporterByEmailSeconds(FUNDRAISING_TEST="paysafeRecurringACH", fields);
        page.getSupporterByIdSeconds(FUNDRAISING_TEST="paysafeRecurringACH", fields);
    }

    @Parameters({"paysafeRecurringBACS"})
    @Test(groups = { "paysafe" })
    public static void paysafeRecurringBACS(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/5626/donate/1?mode=DEMO");

        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("RecurringBacs");
//		Call the createEmail function

        String new_email = fields.createEmailSeconds(testId);
        fields.setEmailAddress(new_email);

        fields.waitForPageLoad();
        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.setPhoneNum("703-234-3434");
        fields.setCountry("GB");

        fields.submit();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dtfToday = DateTimeFormatter.ofPattern("dd");
        LocalDate todaysDate = LocalDate.now().plusDays(10);
        fields.setRecurDay(dtfToday.format(todaysDate));
        LocalDate startDate = LocalDate.now().plusDays(10);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        LocalDate endDate = LocalDate.now().plusYears(1);
        fields.setRecurEndDate(dtf.format(endDate).toString());
        fields.setRecurFreq("MONTHLY");

        String newBankAccount =  fields.getBecaNumberBank();
        fields.setBankAccNumber(newBankAccount);
        fields.setBankRoutingNumber("108800");

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same",
                myurl.equals("https://test.engagingnetworks.app/page/5626/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("4524"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        //Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("£5.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("GBP"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("BANK_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains(" TEST: BACS"));


       // page.getSupporterByEmailSeconds(FUNDRAISING_TEST="paysafeRecurringBACS", fields);
       // page.getSupporterByIdSeconds(FUNDRAISING_TEST="paysafeRecurringBACS", fields);
    }

    @Parameters({"paysafeRecurringSEPA"})
    @Test(groups = { "paysafe" })
    public static void paysafeRecurringSEPA(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/5627/donate/1?mode=DEMO");

        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("RecurringSepa");
//		Call the createEmail function

        String new_email = fields.createEmailSeconds(testId);
        fields.setEmailAddress(new_email);

        fields.waitForPageLoad();
        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.setPhoneNum("703-234-3434");
        fields.setCountry("GB");

        fields.submit();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/MM/yyyy");
        DateTimeFormatter dtfToday = DateTimeFormatter.ofPattern("d");
        LocalDate todaysDate = LocalDate.now();
        fields.setRecurDay(dtfToday.format(todaysDate));
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        LocalDate endDate = LocalDate.now().plusYears(1);
        fields.setRecurEndDate(dtf.format(endDate).toString());
        fields.setRecurFreq("MONTHLY");

        fields.setBankAccNumber("FI2112345600000785");
        String newRouting = fields.getRandomRoutingNumber();
        fields.setBankRoutingNumber(newRouting);

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same",
                myurl.equals("https://test.engagingnetworks.app/page/5627/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("4525"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("€5.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("EUR"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("BANK_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: SEPA"));


        page.getSupporterByEmailSeconds(FUNDRAISING_TEST="paysafeRecurringSEPA", fields);
        page.getSupporterByIdSeconds(FUNDRAISING_TEST="paysafeRecurringSEPA", fields);
    }

    @Parameters({"paysafeRecurringEFT"})
    @Test(groups = { "paysafe" })
    public static void paysafeRecurringEFT(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/5630/donate/1?mode=DEMO");

        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("RecurringEFT");
//		Call the createEmail function

        String new_email = fields.createEmailSeconds(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.waitForPageLoad();
        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        fields.setRecurFreq("MONTHLY");

       // fields.selectPayCurrency("CAD");
        fields.selectPaymentType("EFT");
        fields.setCCName("EFT");
        fields.setBankName("123");
        fields.selectBankAccType("SAVINGS");
        String new_bankAcc = fields.getEFTNumberBank();
        fields.setBankAccNumber(new_bankAcc);
        fields.setBankRoutingNumber("53120");

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Urls are not the same",
                myurl.equals("https://test.engagingnetworks.app/page/5630/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("4529"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("CAD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("BANK_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: EFT"));


        page.getSupporterByEmailSeconds(FUNDRAISING_TEST="paysafeRecurringEFT", fields);
        page.getSupporterByIdSeconds(FUNDRAISING_TEST="paysafeRecurringEFT", fields);
    }

    @Parameters({"paysafe3DSingle"})
    @Test(groups = { "paysafe" })
    public static void paysafe3DSingle(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/12868/donate/1?mode=DEMO");

        fields.selectDonationAmt("5");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("Single3D");
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
        fields.setCCNUmber("4000000000001091");
        fields.setCCExpiry(new CharSequence[] {"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        //      Validate resend code function

        Thread.sleep(200);
        driver.switchTo().frame("Cardinal-CCA-IFrame");
        WebElement donationAmount = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".challengeinfotext")));
        Assert.assertTrue("Donation amount is incorrect or not present" ,
                donationAmount.getText().contains("$5.00"));
        WebElement otp =  (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("challengeDataEntry")));
        otp.sendKeys("1234");

        WebElement submit = driver.findElement(By.cssSelector(".button.primary"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", submit);
        fields.waitForPageLoad();


        //		Assert that the payment was successful and the third page was reached
        driver.switchTo().defaultContent();
        fields.waitForPageLoad();
        Thread.sleep(2000);
        Assert.assertTrue("Urls are not the same",
                driver.getCurrentUrl().equals("https://test.engagingnetworks.app/page/12868/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$5.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VI"));

        page.getSupporterByEmail(FUNDRAISING_TEST="paysafe3DSingle", fields);
        page.getSupporterById(FUNDRAISING_TEST="paysafe3DSingle", fields);
    }

    @Parameters({"paysafe3DRecurring"})
    @Test(groups = { "paysafe" })
    public static void paysafe3DRecurring(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/12869/donate/1?mode=DEMO");

        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("199.99");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("Recurring3D");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();
        fields.waitForPageLoad();
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

        fields.setRecurDay("23");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        LocalDate endDate = LocalDate.now().plusYears(1);
        fields.setRecurEndDate(dtf.format(endDate).toString());
        fields.setRecurFreq("MONTHLY");
        fields.setRecurCount("6");
        fields.setRecurPeriod("2");

        fields.selectPaymentType("Visa");
        fields.selectPayCurrency("USD");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4000000000001091");
        fields.setCCExpiry(new CharSequence[] {"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        //      Validate 3D transaction

        Thread.sleep(200);
        driver.switchTo().frame("Cardinal-CCA-IFrame");
        WebElement donationAmount = driver.findElement(By.cssSelector(".challengeinfotext"));
        Assert.assertTrue("Donation amount is incorrect or not present" ,
                donationAmount.getText().contains("$199.99"));
        WebElement otp = driver.findElement(By.name("challengeDataEntry"));
        otp.sendKeys("1234");

        WebElement submit = driver.findElement(By.cssSelector(".button.primary"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", submit);
        fields.waitForPageLoad();

        //		Assert that the payment was successful and the third page was reached
        driver.switchTo().defaultContent();
        Thread.sleep(4000);
        Assert.assertTrue("Urls are not the same", driver.getCurrentUrl()
                .equals("https://test.engagingnetworks.app/page/12869/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("8612"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$199.99"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VI"));

        page.getSupporterByEmail(FUNDRAISING_TEST="paysafe3DRecurring", fields);
        page.getSupporterById(FUNDRAISING_TEST="paysafe3DRecurring", fields);
    }

    @Parameters({"paysafePrimaryPaypalCurrencyCAD"})
    @Test(groups = { "paysafe" })
    public static void paysafePrimaryPaypalCurrencyCAD(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/13536/donate/1?mode=DEMO");

        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("PaypalCurrencyCAD");
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
        fields.selectPayCurrency("CAD");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4530910000012345");
        fields.setCCExpiry(new CharSequence[] {"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        fields.waitForPageLoad();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/13536/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("9400"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("CAD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VI"));

        page.getSupporterByEmail(FUNDRAISING_TEST="paysafePrimaryPaypalCurrencyCAD", fields);
        page.getSupporterById(FUNDRAISING_TEST="paysafePrimaryPaypalCurrencyCAD", fields);
    }

    @Parameters({"paysafePrimaryPaypalCurrencyUSD"})
    @Test(groups = { "paysafe" })
    public static void paysafePrimaryPaypalCurrencyUSD(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/13536/donate/1?mode=DEMO");

        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("PaypalCurrencyUSD");
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
        fields.setCCNUmber("4530910000012345");
        fields.setCCExpiry(new CharSequence[] {"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        //		Assert that the payment was successful and the third page was reached
        String myurl = driver.getCurrentUrl();
        fields.waitForPageLoad();
        Assert.assertTrue("Urls are not the same", myurl.equals("https://test.engagingnetworks.app/page/13536/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("9400"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VI"));

        page.getSupporterByEmail(FUNDRAISING_TEST="paysafePrimaryPaypalCurrencyUSD", fields);
        page.getSupporterById(FUNDRAISING_TEST="paysafePrimaryPaypalCurrencyUSD", fields);
    }

    @Parameters({"paysafePrimaryPaypalPaymentCurrencyUSD"})
    @Test(groups = { "paysafe" })
    public static void paysafePrimaryPaypalPaymentCurrencyUSD(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/13536/donate/1?mode=DEMO");

        fields.selectDonationAmt("15");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("PaypalPaymentCurrencyUSD");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();

        fields.setAddress1("1 Hilltop");
        fields.setCity("Baltimore");
        fields.selectRegion("MD");
        fields.setPostCode("20001");
        fields.selectCountry("US");

        fields.selectPaymentType("Paypal");
        fields.selectPayCurrency("USD");

        fields.submit();

        fields.waitForPageLoadPayPal();
        fields.waitForURLToChange("https://www.sandbox.paypal.com/");
        //		Assert that the payment is redirected to Paypal page
        String myurl = driver.getCurrentUrl();
        Assert.assertTrue("Didn't redirect to Paypal", myurl.contains("https://www.sandbox.paypal.com/"));
//        fields.waitForPageLoad();
//        fields.logPaypal();
        fields.waitForPageLoad();
        fields.setPaypalEmail();
        fields.nextPayapl();
        fields.waitForPageLoad();
        fields.setPaypalPassword();
        fields.submitPaypal();
        fields.waitForPageLoadPayPal();
        Thread.sleep(8000);

        WebElement paypalContinue = (new WebDriverWait(driver, 400))
                .until(ExpectedConditions.presenceOfElementLocated
                        (By.id("payment-submit-btn")));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", paypalContinue);

        fields.waitForPageLoad();
        Thread.sleep(9000);

        //		Assert that the payment was successful and the third page was reached

        Assert.assertTrue("Urls are not the same", driver.getCurrentUrl()
                .equals("https://test.engagingnetworks.app/page/13536/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();

        Assert.assertTrue("Campaign ID not present", bodytext.contains("9400"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("PayPal Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: Paypal"));

        page.getSupporterByEmail(FUNDRAISING_TEST="paysafePrimaryPaypalPaymentCurrencyUSD", fields);
        page.getSupporterById(FUNDRAISING_TEST="paysafePrimaryPaypalPaymentCurrencyUSD", fields);
    }

    @Parameters({"paysafeSingleToRecurUpsale"})
    @Test(groups = { "paysafe" })
    public static void paysafeSingleToRecurUpsale(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/14532/donate/1");
        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("15.99");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("Upsel3D");
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
        fields.setCCNUmber("4000000000001091");
        fields.setCCExpiry(new CharSequence[] {"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        fields.validateUsellAmount("Thank you! Before we process your donation, will you start a 10.00 USD monthly gift instead?");
        fields.clickUpsellYes();

        //      Validate resend code function

        Thread.sleep(200);
        driver.switchTo().frame("Cardinal-CCA-IFrame");
        WebElement donationAmount = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".challengeinfotext")));
        Assert.assertTrue("Donation amount is incorrect or not present" ,
                donationAmount.getText().contains("$10.00"));
        WebElement otp =  (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("challengeDataEntry")));
        otp.sendKeys("1234");

        WebElement submit = driver.findElement(By.cssSelector(".button.primary"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", submit);
        fields.waitForPageLoad();


        //		Assert that the payment was successful and the third page was reached
        driver.switchTo().defaultContent();
        fields.waitForPageLoad();
        Thread.sleep(2000);
        Assert.assertTrue("Urls are not the same",
                driver.getCurrentUrl().equals("https://test.engagingnetworks.app/page/14532/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("10498"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$10.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VI"));

//		Get the details from the third page and Verify the fields


        page.getSupporterByEmail(FUNDRAISING_TEST = "paysafeSingleToRecurUpsale", fields);
        page.getSupporterById(FUNDRAISING_TEST = "paysafeSingleToRecurUpsale", fields);

    }

    @Parameters({"paysafe3DRecurringUpsell"})
    @Test(groups = { "paysafe" })
    public static void paysafe3DRecurringUpsell(String testId) throws InterruptedException, IOException {
        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/14533/donate/1?mode=DEMO");

        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("25.99");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("Recurring3Dupsell");
//		Call the createEmail function
        String new_email = fields.createEmail(testId);
        fields.setEmailAddress(new_email);

        fields.submit();
        fields.waitForPageLoad();
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

        fields.setRecurDay("23");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.now().plusDays(1);
        fields.setRecurStartDate(dtf.format(startDate).toString());
        LocalDate endDate = LocalDate.now().plusYears(1);
        fields.setRecurEndDate(dtf.format(endDate).toString());
        fields.setRecurFreq("MONTHLY");
        fields.setRecurCount("6");
        fields.setRecurPeriod("2");

        fields.selectPaymentType("Visa");
        fields.selectPayCurrency("USD");
        fields.setCCName("Unit Tester");
        fields.setCCNUmber("4000000000001091");
        fields.setCCExpiry(new CharSequence[] {"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        fields.validateUsellAmount("Thank you! Before we process your donation, will you make it a 25.00 USD monthly gift instead?");
        fields.clickUpsellYes();

        //      Validate 3D transaction

        Thread.sleep(200);
        driver.switchTo().frame("Cardinal-CCA-IFrame");
        WebElement donationAmount = driver.findElement(By.cssSelector(".challengeinfotext"));
        Assert.assertTrue("Donation amount is incorrect or not present" ,
                donationAmount.getText().contains("$25.00"));
        WebElement otp = driver.findElement(By.name("challengeDataEntry"));
        otp.sendKeys("1234");

        WebElement submit = driver.findElement(By.cssSelector(".button.primary"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", submit);
        fields.waitForPageLoad();

        //		Assert that the payment was successful and the third page was reached
        driver.switchTo().defaultContent();
        Thread.sleep(4000);
        Assert.assertTrue("Urls are not the same", driver.getCurrentUrl()
                .equals("https://test.engagingnetworks.app/page/14533/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("10499"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$25.00"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_RECURRING"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VI"));

        page.getSupporterByEmail(FUNDRAISING_TEST="paysafe3DRecurringUpsell", fields);
        page.getSupporterById(FUNDRAISING_TEST="paysafe3DRecurringUpsell", fields);
    }

    @Parameters({"paysafeSingleToRecurNoUpsale"})
    @Test(groups = { "paysafe" })
    public static void paysafeSingleToRecurNoUpsale(String testId) throws InterruptedException, IOException {

        page.ensAuthTest();
        driver.get("https://test.engagingnetworks.app/page/14532/donate/1");
        fields.selectDonationAmt("other");
        fields.selectDonationAmtOther("15.99");
        fields.selectTitle("Ms");
        fields.setFirstname("Paysafe");
        fields.setLastname("Upsel3D");
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
        fields.setCCNUmber("4000000000001091");
        fields.setCCExpiry(new CharSequence[] {"12", "2024"});
        fields.setCCV("123");

        fields.submit();

        fields.validateUsellAmount("Thank you! Before we process your donation, will you start a 10.00 USD monthly gift instead?");
        fields.clickUpsellNo();

        //      Validate resend code function

        Thread.sleep(200);
        driver.switchTo().frame("Cardinal-CCA-IFrame");
        WebElement donationAmount = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".challengeinfotext")));
        Assert.assertTrue("Donation amount is incorrect or not present" ,
                donationAmount.getText().contains("$15.99"));
        WebElement otp =  (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.name("challengeDataEntry")));
        otp.sendKeys("1234");

        WebElement submit = driver.findElement(By.cssSelector(".button.primary"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", submit);
        fields.waitForPageLoad();


        //		Assert that the payment was successful and the third page was reached
        driver.switchTo().defaultContent();
        fields.waitForPageLoad();
        Thread.sleep(2000);
        Assert.assertTrue("Urls are not the same",
                driver.getCurrentUrl().equals("https://test.engagingnetworks.app/page/14532/donate/3"));

        fields.getSupporterTaxID();

//		Get the details from the third page and Verify the fields
        String bodytext = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Campaign ID not present", bodytext.contains("10498"));
        Assert.assertTrue("Gateway details are incorrect/not present", bodytext.contains("Optimal Payments Gateway"));
        Assert.assertTrue("Donation Amount is incorrect/not present", bodytext.contains("$15.99"));
        Assert.assertTrue("Currency is incorrect/not present", bodytext.contains("USD"));
        Assert.assertTrue("Donation type is incorrect/not present", bodytext.contains("CREDIT_SINGLE"));
        Assert.assertTrue("CC type is incorrect/ not present", bodytext.contains("TEST: VI"));

//		Get the details from the third page and Verify the fields


        page.getSupporterByEmail(FUNDRAISING_TEST = "paysafeSingleToRecurNoUpsale", fields);
        page.getSupporterById(FUNDRAISING_TEST = "paysafeSingleToRecurNoUpsale", fields);

    }

}
