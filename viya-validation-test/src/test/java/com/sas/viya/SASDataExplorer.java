package com.sas.viya;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SASDataExplorer extends BaseTest {
	
	@Parameters({"url","username","password"})
	@Test(priority=1, testName = "SASEnvironmentManagerNavigateURL", groups = {"smoke","functional"})
	public void SignIn(@Optional("https://yourdomain/SASEnvironmentManager") String url, @Optional("sasboot") String username, @Optional("Test") String password) throws Exception {
		driver.get(url);
		sleep(3000);
		driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submitBtn")).click();
        sleep(3000);
        driver.findElement(By.id("sas-admin")).click();
        
        WaitUntilElementExists(driver, "//iframe[@id='envmgrapp_iframe']", 30);
        driver.findElement(By.id("envmgrapp_iframe"));
        driver.switchTo().frame(driver.findElement(By.id("envmgrapp_iframe")));
        
        if(WaitUntilElementExists(driver, "//div[@id='welcomeScreen']", 400))
        {
            WebElement element = driver.findElement(By.xpath("//div[@id='welcomeScreen']"));
            if (element != null)
            {
                driver.findElement(By.xpath("//div[@id='welcomeScreen']/div[2]/div/div/div/div/button")).click();
                sleep(3000);
                driver.findElement(By.xpath("//a[@id='skipLink']")).click();
                sleep(3000);
                driver.findElement(By.xpath("//div[@id='welcomeScreen']/div/div/div/div/div[5]/div[2]/button")).click();
                sleep(3000);
            }
        }
        else
        {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
		extentTest.pass("Assertion is passed for SAS Environment Manager Navigation");
	}
	
	@Test(priority=2, testName = "SASDataExplorerVerifyManageData", groups = {"functional"})
	public void VerifyManageData() throws Exception 
	{
		// Switch to the Menu from Env Manager
		driver.findElement(By.xpath("//button[@id='envmgrapp_appContainer_banner-appSwitcher']")).click();
		
		// Click to Manage Data
		driver.findElement(By.xpath("//ul/li[2]")).click();
		
		WaitUntilElementExists(driver, "//div[@id='data_explorer_ui']", 30);
		
		extentTest.pass("Manage Data verified");
	}
}
