package com.sas.viya;

import static org.testng.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SASDrive extends BaseTest {
	
	@Parameters({"url","username","password"})
	@Test(priority=1, testName = "SASDriveNavigateURL", groups = {"smoke", "functional"})
	public void SignIn(@Optional("https://yourdomain/SASDrive") String url, @Optional("sasboot") String username, @Optional("Test") String password) throws Exception {
		driver.get(url);
		sleep(3000);
		driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submitBtn")).click();
        sleep(3000);
        driver.findElement(By.id("sas-admin")).click();
        
        WaitUntilElementExists(driver, "//iframe[@id='SASDrive_iframe']", 30);       
        driver.findElement(By.id("SASDrive_iframe"));
        driver.switchTo().frame(driver.findElement(By.id("SASDrive_iframe")));

        WaitUntilElementExists(driver, "//div[@id='SASDrive_appContainer']", 400);
        if (WaitUntilElementExists(driver, "//button[@id='driveHelpOverlayDialog_0-closeXButton']", 15))
        {
            driver.findElement(By.xpath("//button[@id='driveHelpOverlayDialog_0-closeXButton']")).click();
            sleep(3000);
        }              
		extentTest.pass("Assertion is passed for SAS Drive navigation");
	}
	
	@Test(priority=2, testName = "SignOut", groups = {"smoke", "functional"} )
	public void SignOut() {
		driver.findElement(By.id("SASDrive_appContainer_banner-bannerOptionsMenuButton-firstInitialButton")).click();
		new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
		new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
		new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
		new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
		new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
		new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
		new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
		new Actions(driver).sendKeys(Keys.ENTER).perform();
		sleep(3000);
	}
}
