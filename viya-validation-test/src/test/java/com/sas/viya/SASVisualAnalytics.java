package com.sas.viya;

import static org.testng.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SASVisualAnalytics extends BaseTest {
	
	@Parameters({"url","username","password"})
	@Test(priority=1, testName = "SASVisualAnalyticsNavigateURL", groups = {"smoke", "functional"})
	public void SignIn(@Optional("https://yourdomain/SASDrive") String url, @Optional("sasboot") String username, @Optional("Test") String password) throws Exception 
	{
		driver.get(url);
		sleep(3000);
		driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submitBtn")).click();
        sleep(3000);
        driver.findElement(By.id("sas-admin")).click();
        WaitUntilElementExists(driver, "//iframe[@id='VANextLogon_iframe']", 30);
        driver.findElement(By.id("VANextLogon_iframe"));
        driver.switchTo().frame(driver.findElement(By.id("VANextLogon_iframe")));

        WaitUntilElementExists(driver, "//div[@id='VANextLogon']", 400);
        if (WaitUntilElementExists(driver, "//button[@id='driveHelpOverlayDialog_0-closeXButton']", 30))
        {
            driver.findElement(By.xpath("//button[@id='driveHelpOverlayDialog_0-closeXButton']")).click();
            sleep(3000);
        }
        
        if (WaitUntilElementExists(driver, "//div[@id='sap-ui-static']/div[3]/footer/div/button[2]", 20))
        {
            driver.findElement(By.xpath("//div[@id='sap-ui-static']/div[3]/footer/div/button[2]")).click();
            sleep(3000);
        }
        
		extentTest.pass("Assertion is passed for SAS Visual Analytics navigation");
	}
	
	@Test(priority=2, testName = "SASVisualAnalyticsReport1", groups = {"functional"})
	public void Report1() throws Exception {
		new Actions(driver).sendKeys(Keys.ESCAPE).perform();
        sleep(6000);
        driver.findElement(By.xpath("//button[@title='Data']")).click();
        sleep(6000);
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys("CSV_FILE");
        sleep(6000);
        driver.findElement(By.xpath("//ul[@role='listbox']/li[1]/div[@class='sapMLIBContent']")).click();
        sleep(3000);
        driver.findElement(By.xpath("//button/span/span/bdi[text()='OK']")).click();
        sleep(3000);
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
        sleep(3000);
        driver.findElement(By.xpath("//button[@title='Data']")).click();
        sleep(3000);
        driver.findElement(By.xpath("//button[@title='Data']")).click();
        sleep(3000);
        driver.findElement(By.xpath("//div[@class='vanSidePanelContent']/div/div/div/div/div/section/div[2]")).click();
        new Actions(driver).doubleClick(driver.findElement(By.xpath("//div[@class='vanSidePanelContent']/div/div/div/div/div/section/div[2]"))).perform();
        sleep(3000);
        driver.findElement(By.xpath("//button[@title='Data']")).click();
        sleep(3000);
        driver.findElement(By.xpath("//div[@class='vanSidePanelContent']/div/div/div/div/div/section[4]/div[2]/div/ul/li[1]")).click();
        new Actions(driver).doubleClick(driver.findElement(By.xpath("//div[@class='vanSidePanelContent']/div/div/div/div/div/section[4]/div[2]/div/ul/li[1]"))).perform();
        sleep(3000);
        driver.findElement(By.xpath("//button[@id='vanApplicationToolbar-save']")).click();
        sleep(8000);

        driver.findElement(By.xpath("//footer/div/button")).click();
        sleep(3000);

        if(WaitUntilElementExists(driver, "//div[@role='alertdialog']", 100))
        {
        	driver.findElement(By.xpath("//div[@role='alertdialog']/footer/div/button")).click();
        }
        sleep(6000);
       
		extentTest.pass("Assertion is passed for SAS Visual Analytics Report1");
	}
	
	@Test(priority=3, testName = "SASVisualAnalyticsReport2", groups = {"functional"})
	public void Report2() throws Exception {
	 	 driver.findElement(By.xpath("//button[@id='vanApplicationToolbar-more']")).click();
         new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
         new Actions(driver).sendKeys(Keys.ENTER).perform();
         sleep(3000);
		 new Actions(driver).sendKeys(Keys.ESCAPE).perform();
         sleep(3000);

         driver.findElement(By.xpath("//button[@title='Data']")).click();
         sleep(6000);
         driver.findElement(By.xpath("//input[@type='search']")).sendKeys("DEMOGRAPHICS");
         sleep(6000);
         driver.findElement(By.xpath("//ul[@role='listbox']/li[1]/div[@class='sapMLIBContent'][1]")).click();
         sleep(3000);

         driver.findElement(By.xpath("//button/span/span/bdi[text()='OK']")).click();
         sleep(3000);
         new Actions(driver).sendKeys(Keys.ESCAPE).perform();
         sleep(6000);

         driver.findElement(By.xpath("//button[@title='Objects']")).click();
         sleep(8000);
         
         WaitUntilElementExists(driver, "//div[@id='__vanlist1']/ul[@role='listbox']/li[9]", 20);
       	 driver.findElement(By.xpath("//div[@id='__vanlist1']/ul[@role='listbox']/li[9]")).click();
       	 new Actions(driver).doubleClick(driver.findElement(By.xpath("//div[@id='__vanlist1']/ul[@role='listbox']/li[9]"))).perform();
         sleep(6000);

         driver.findElement(By.xpath("//button[@title='Data']")).click();
         sleep(6000);

         driver.findElement(By.xpath("//div[@id='__shade0']//section/div[2]/div/ul/li[1]")).click();
         new Actions(driver).doubleClick(driver.findElement(By.xpath("//div[@id='__shade0']//section/div[2]/div/ul/li[1]"))).perform();
         sleep(6000);

         //driver.findElement(By.xpath("//div[@role='toolbar']/button")).click();
         //sleep(3000);
         
         //if(WaitUntilElementExists(driver, "//button[@id='vanApplicationToolbar-save']", 20))
         //{
         //	 driver.findElement(By.xpath("//button[@id='vanApplicationToolbar-save']")).click();
         //	 sleep(6000);
         //}

         //driver.findElement(By.xpath("//footer/div/button[1]")).click();
         //sleep(6000);

         //if(WaitUntilElementExists(driver, "//div[@role='alertdialog']", 100))
         //{
         //	driver.findElement(By.xpath("//div[@role='alertdialog']/footer/div/button")).click();
         //}
         //sleep(6000);
         
         extentTest.pass("Assertion is passed for SAS Visual Analytics Report2");
	}
	
	@Test(priority=4, testName = "LogOut", groups = {"smoke", "functional"} )
	public void SignOut() {
		driver.findElement(By.id("VANextLogon_appContainer_banner-bannerOptionsMenuButton-firstInitialButton")).click();
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
