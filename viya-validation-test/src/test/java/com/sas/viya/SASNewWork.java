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

public class SASNewWork extends BaseTest {
	
	@Parameters({"url","username","password"})
	@Test(priority=1, testName = "SASDriveNavigateURL", groups = {"smoke", "functional"})
	public void SignIn(@Optional("https://www.sas.com/") String url, @Optional("sasboot") String username, @Optional("Test") String password) throws Exception {
		driver.get(url);
		sleep(3000);
		System.out.println(driver.getTitle());
		String inputTitle=driver.getTitle();
		String expectedTitle="SAS: Analytics, Artificial Intelligence and Data Management | SAS";
		Assert.assertEquals(inputTitle, expectedTitle);
		
		extentTest.pass("Assertion is passed for SAS Website Load.");
	}
}
