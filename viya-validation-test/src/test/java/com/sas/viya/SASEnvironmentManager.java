package com.sas.viya;

import static org.testng.Assert.assertEquals;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class SASEnvironmentManager extends BaseTest {
	
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
	
	@Test(priority=2, testName = "SASEnvironmentManagerValidateUsers", groups = {"functional"})
	public void CheckUsersAndGroups() throws Exception {
		driver.findElement(By.id("envmgrapp_appContainer_lfn_4_icn")).click();
        sleep(3000);
        driver.findElement(By.id("ev_identity_typeFilter-label")).click();
        driver.findElement(By.xpath("//ul/li[1]")).click();
        extentTest.info("Users Listed");
        sleep(3000);
        driver.findElement(By.id("ev_identity_typeFilter-label")).click();
        driver.findElement(By.xpath("//ul/li[2]")).click();
        extentTest.info("Groups Listed");
        sleep(3000);
        driver.findElement(By.id("ev_identity_typeFilter-label")).click();
        driver.findElement(By.xpath("//ul/li[3]")).click();
        extentTest.info("Custom Groups Listed");
        sleep(3000);
	}
	
	@Test(priority=3, testName = "SASEnvironmentManagerValidateValidationFolder", groups = {"functional"})
	public void ValidateValidationFolder() throws Exception {
		 driver.findElement(By.id("envmgrapp_appContainer_lfn_3_icn")).click();
         sleep(6000);
         driver.findElement(By.id("ContentSelectionPane-basic-cntntSelPaneListItemPrefix-ContentSelectionPane-basic-cntntSelPane-list0-2-imgNav")).click();
         sleep(4000);
         driver.findElement(By.id("ContentSelectionPane-basic-cntntSelPane-toolbar-newFolderButton-img")).click();
         char c = '\u0001'; // ASCII code 1 for Ctrl-A
         driver.findElement(By.cssSelector(".sasContentNavNewFolderInput")).sendKeys(Character.toString(c));
         Random rnd = new Random();
         int num = rnd.nextInt();
         String input = "Install Validaton" + Integer.toString(num);
         driver.findElement(By.cssSelector(".sasContentNavNewFolderInput")).sendKeys(input);
         driver.findElement(By.cssSelector(".sasContentNavNewFolderInput")).sendKeys(Keys.ENTER);
         extentTest.info("Validation Folder Created for storing Content");
         sleep(3000);
         driver.findElement(By.id("ContentSelectionPane-basic-cntntSelPane-toolbar-DeleteButton")).click();
         sleep(3000);
         extentTest.info("Validation Folder Created was Deleted");
	}
	
	@Test(priority=4, testName = "SASEnvironmentManagerValidateAccessServerPage", groups = {"functional"})
	public void AccessServerPage() throws Exception {
		 driver.findElement(By.id("envmgrapp_appContainer_lfn_2_icn")).click();
         sleep(6000);
         ElementEnabled(driver, driver.findElement(By.id("EVNextCasServersViewPage--sasev_casserver_list-rows-row0-col0")),10);
         sleep(5000);
         driver.findElement(By.id("EVNextCasServersViewPage--sasev_casserver_list-rows-row0-col0")).click();
         sleep(3000);
         sleep(2000);
         driver.findElement(By.id("EVNextCasServersViewPage--sasev_casserver_configurationButton-img")).click();
         driver.findElement(By.cssSelector("#\\__filter2 > .sapMITBContentArrow")).click();
         sleep(2000);
         extentTest.pass("Assertion is passed for CAS Server Page");
	}
	
	@Parameters("sampleDataPath")
	@Test(priority=5, testName = "SASEnvironmentManagerImportData", groups = {"functional"})
	public void ImportData(@Optional("C://Test//csv_file.csv") String sampleDataPath) throws Exception {
		driver.findElement(By.id("envmgrapp_appContainer_lfn_1_icn")).click();
		sleep(6000);
		driver.findElement(By.cssSelector("#\\__xmlview3--importTabFilter > .sapMITBContentArrow")).click();
        sleep(6000);
        driver.findElement(By.cssSelector("#\\__xmlview3--availableTabFilter > .sapMITBContentArrow")).click();
        sleep(5000);
        driver.findElement(By.cssSelector("#\\__xmlview3--importTabFilter > .sapMITBContentArrow")).click();
        driver.findElement(By.cssSelector("#\\__xmlview9--localFileListItem-content .sapMSLITitleOnly")).click();
        sleep(5000);
        //driver.findElement(By.id("__item787-unifiedmenu-txt")).click();
        driver.findElement(By.id("__xmlview9--localFileUploader-fu")).sendKeys(sampleDataPath);
        sleep(5000);
        driver.findElement(By.cssSelector("#\\__xmlview2--importSettings--replaceExistingButton-label > .sasMLabelText")).click();
        sleep(2000);
        driver.findElement(By.id("__xmlview2--importSelectedButton-BDI-content")).click();
        sleep(3000);
        driver.findElement(By.cssSelector("#\\__xmlview3--availableTabFilter > .sapMITBContentArrow")).click();
        var xpath = String.format("(//div[@id='__xmlview7--availableList-listContent']/ul/li)[1]");
        driver.findElement(By.xpath(xpath)).click();
        sleep(5000);
        extentTest.pass("Assertion is passed for Importing Sample Data in CAS");
	}
		
	@Parameters("sampleDemographicDataPath")
	@Test(priority=6, testName = "SASEnvironmentManagerImportDemographicData", groups = {"functional"})
	public void ImportDemographic(@Optional("C://Test//demographics.csv") String sampleDemographicDataPath) throws Exception {
		 driver.findElement(By.id("envmgrapp_appContainer_lfn_1_icn")).click();
		 sleep(3000);
		 driver.findElement(By.cssSelector("#\\__xmlview3--importTabFilter > .sapMITBContentArrow")).click();
         sleep(3000);
         driver.findElement(By.cssSelector("#\\__xmlview3--availableTabFilter > .sapMITBContentArrow")).click();
         sleep(3000);
         driver.findElement(By.cssSelector("#\\__xmlview3--importTabFilter > .sapMITBContentArrow")).click();
         driver.findElement(By.xpath("//button[@title='Add']")).click();
         sleep(3000);
         new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
 		 new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
 		 new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();
 		 new Actions(driver).sendKeys(Keys.ENTER).perform();
 		 new Actions(driver).sendKeys(Keys.ENTER).perform();
 		 sleep(5000);
         //driver.findElement(By.cssSelector("#\\__xmlview9--localFileListItem-content .sapMSLITitleOnly")).click();
         //driver.findElement(By.id("__item787-unifiedmenu-txt")).click();
         driver.findElement(By.id("__xmlview9--localFileUploader-fu")).sendKeys(sampleDemographicDataPath);
         sleep(5000);
         driver.findElement(By.cssSelector("#\\__xmlview2--importSettings--replaceExistingButton-label > .sasMLabelText")).click();
         sleep(2000);
         driver.findElement(By.id("__xmlview2--importSelectedButton-BDI-content")).click();
         sleep(3000);
         driver.findElement(By.cssSelector("#\\__xmlview3--availableTabFilter > .sapMITBContentArrow")).click();
         var xpath = String.format("(//div[@id='__xmlview7--availableList-listContent']/ul/li)[1]");
         driver.findElement(By.xpath(xpath)).click();
         sleep(5000);
         extentTest.pass("Assertion is passed for Importing Demographic Data in CAS");
	}
	
	@Test(priority=7, testName = "LogOut", groups = {"smoke", "functional"} )
	public void SignOut() {
		driver.findElement(By.id("envmgrapp_appContainer_banner-bannerOptionsMenuButton-firstInitialButton")).click();
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
