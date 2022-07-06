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


public class SASStudio extends BaseTest {
	
	@Parameters({"url","username","password"})
	@Test(priority=1, testName = "SASStudioNavigateURL", groups = {"smoke", "functional"})
	public void SignIn(@Optional("https://yourdomain/SASDrive") String url, @Optional("sasboot") String username, @Optional("Test") String password) throws Exception {
		driver.get(url);
		sleep(3000);
		driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submitBtn")).click();
        sleep(3000);
        driver.findElement(By.id("sas-admin")).click();
        WaitUntilElementExists(driver, "//iframe[@id='sasstudio_iframe']", 100);
        driver.findElement(By.id("sasstudio_iframe"));
        driver.switchTo().frame(driver.findElement(By.id("sasstudio_iframe")));

        WaitUntilElementExists(driver, "//div[@id='sasstudio']", 400);
        sleep(3000);
        WaitUntilElementExists(driver, "//div[@id='SASStudio--newMenuButton']", 300);
		extentTest.pass("Assertion is passed for SAS Studio Navigation");
	}
	
	@Test(priority=2, testName = "SASStudioCodeExecution1", groups = {"functional"})
	public void CodeExecution1() throws Exception {
		 driver.findElement(By.id("SASStudio--newMenuButton-internalBtn-BDI-content")).click();
         driver.findElement(By.id("SASStudio--newProgramStandardMenuItem-unifiedmenu-txt")).click();
         sleep(3000);
         driver.findElement(By.cssSelector(".sce-sas .textviewContent")).click();
         driver.findElement(By.cssSelector(".sce-sas .textviewContent")).sendKeys("proc print data=sashelp.class;run;");
         sleep(3000);
         driver.findElement(By.id("__jsview6--program1--codeSubmitButton1")).click();
         sleep(6000);
		 extentTest.pass("Assertion is passed for Code Execution Part 1");
	}
	
	@Test(priority=3, testName = "SASStudioCodeExecution2", groups = {"functional"})
	public void CodeExecution2() throws Exception {
		 driver.findElement(By.id("SASStudio--newMenuButton-internalBtn-BDI-content")).click();
	     driver.findElement(By.id("SASStudio--newProgramStandardMenuItem-unifiedmenu-txt")).click();
	     sleep(3000);
	     driver.findElement(By.cssSelector(".sce-sas .textviewContent")).click();
	     driver.findElement(By.cssSelector(".sce-sas .textviewContent")).sendKeys("cas; caslib _all_ assign;");
	     sleep(6000);
	     driver.findElement(By.id("__jsview6--program2--codeSubmitButton1")).click();
	     sleep(6000);
		 extentTest.pass("Assertion is passed for Code Execution Part 2");
	}
	
	@Test(priority=4, testName = "SASStudioCodeExecution3", groups = {"functional"})
	public void CodeExecution3() throws Exception {
		driver.findElement(By.id("SASStudio--newMenuButton-internalBtn-BDI-content")).click();
        driver.findElement(By.id("SASStudio--newProgramStandardMenuItem-unifiedmenu-txt")).click();
        sleep(3000);
        driver.findElement(By.cssSelector(".sce-sas .textviewContent")).click();
        driver.findElement(By.cssSelector(".sce-sas .textviewContent")).sendKeys("cas; caslib _all_ assign; libname locallib \"!SASROOT/samples/base\"; proc casutil; load data=locallib.hmeq outcaslib=casuser casout=\"hmeq\" replace; run;");
        sleep(6000);
        driver.findElement(By.id("__jsview6--program3--codeSubmitButton1")).click();
		extentTest.pass("Assertion is passed for Code Execution Part 3");
	}
	
	@Test(priority=5, testName = "SASStudioCodeExecution4", groups = {"functional"})
	public void CodeExecution4() throws Exception {
		 driver.findElement(By.id("SASStudio--newMenuButton-internalBtn-BDI-content")).click();
         driver.findElement(By.id("SASStudio--newProgramStandardMenuItem-unifiedmenu-txt")).click();
         sleep(3000);
         driver.findElement(By.cssSelector(".sce-sas .textviewContent")).click();
         driver.findElement(By.cssSelector(".sce-sas .textviewContent")).sendKeys("proc print data=casuser.hmeq(obs=10); run;");
         sleep(6000);
         driver.findElement(By.id("__jsview6--program4--codeSubmitButton1")).click();
		 extentTest.pass("Assertion is passed for Code Execution Part 4");
	}
	
	@Test(priority=6, testName = "SASStudioCodeExecution5", groups = {"functional"})
	public void CodeExecution5() throws Exception {
		 driver.findElement(By.id("SASStudio--newMenuButton-internalBtn-BDI-content")).click();
         driver.findElement(By.id("SASStudio--newProgramStandardMenuItem-unifiedmenu-txt")).click();
         sleep(3000);
         driver.findElement(By.cssSelector(".sce-sas .textviewContent")).click();
         driver.findElement(By.cssSelector(".sce-sas .textviewContent")).sendKeys("cas; caslib _all_ assign; proc casutil; load data=sashelp.prdsale outcaslib=public casout=\"prdsale\" replace; run;");
         sleep(6000);
         driver.findElement(By.id("__jsview6--program5--codeSubmitButton1")).click();
		 extentTest.pass("Assertion is passed for Code Execution Part 5");
	}
	
	@Test(priority=7, testName = "LogOut", groups = {"smoke", "functional"} )
	public void SignOut() {
		driver.findElement(By.id("sasstudio_appContainer_banner-bannerOptionsMenuButton-firstInitialButton")).click();
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
