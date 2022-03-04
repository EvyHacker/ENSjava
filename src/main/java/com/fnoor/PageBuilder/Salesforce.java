package com.fnoor.PageBuilder;

import com.fnoor.FundraisingPageDriver;
import com.fnoor.SFpageFields;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import static com.fnoor.SFpageFields.SF_SERVICE_URL;

public class Salesforce {

    static FundraisingPageDriver page = new FundraisingPageDriver();
    static WebDriver driver;
    static SFpageFields sf;

    @Parameters({"browser"})
    @BeforeClass(alwaysRun=true)

    public void setUp(String browser) throws MalformedURLException {

        driver = page.createInstance(browser);
        sf = PageFactory.initElements(driver, SFpageFields.class);
    }

//    @AfterClass(alwaysRun = true)
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

        @Parameters({"SFlogin"})
        @Test(groups = { "sf" })
        public static void SFlogin() throws InterruptedException, IOException {

            driver.get(SF_SERVICE_URL);

            sf.sfLogin();
            sf.searchENapp("Engaging Networks Connector");
            Assert.assertTrue(driver.getCurrentUrl().contains("lightning/n/engaging__Control_Panel"),
                    "You are not in EN Connector");

        }


}
