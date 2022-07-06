package com.sas.viya;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.base.Stopwatch;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public static WebDriver driver;
	public static String screenshotsSubFolderName;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;

	@BeforeTest
	public void setup(ITestContext context, @Optional("chrome") String browserName) {
		WebDriverManager.chromedriver().setup();
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		boolean isHeadless = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("headLess"));
		if(isHeadless)
		{
			desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
			driver = new ChromeDriver(getChromeOptions());
		}
		else
		{
			desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, getWinChromeOptions());
			driver = new ChromeDriver(getWinChromeOptions());
		}
		
		driver.manage().window().maximize();
		
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
		String device = capabilities.getBrowserName() + " " + capabilities.getVersion().substring(0, capabilities.getVersion().indexOf("."));
		String author = context.getCurrentXmlTest().getParameter("author");
		
		extentTest = extentReports.createTest(context.getName(), "Below is the Validation Report for " + context.getName() + " Test." );
		extentTest.assignAuthor(author);
		extentTest.assignDevice(device);
	}
	
	@AfterTest
	public void teardown() {
		driver.quit();
	}

	@BeforeSuite
	public void initialiseExtentReports() {
		ExtentSparkReporter sparkReporter_all = new ExtentSparkReporter("AllTests.html");
		sparkReporter_all.config().setReportName("All Tests report");
		
		ExtentSparkReporter sparkReporter_failed = new ExtentSparkReporter("FailedTests.html");
		sparkReporter_failed.filter().statusFilter().as(new Status[] {Status.FAIL}).apply();
		sparkReporter_failed.config().setReportName("Failure report");
		
		extentReports = new ExtentReports();
		extentReports.attachReporter(sparkReporter_all, sparkReporter_failed);
		
		extentReports.setSystemInfo("OS", System.getProperty("os.name"));
		extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
	}
	
	@AfterSuite
	public void generateExtentReports() throws Exception {
		extentReports.flush();
		Desktop.getDesktop().browse(new File("AllTests.html").toURI());
		Desktop.getDesktop().browse(new File("FailedTests.html").toURI());
	}
	
	@AfterMethod
	public void checkStatus(Method m, ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = null;
			screenshotPath = captureScreenshot(result.getTestContext().getName()+ "_" +result.getMethod().getMethodName()+".jpg");
			extentTest
			.fail(result.getThrowable())
			.fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		} else if(result.getStatus() == ITestResult.SUCCESS) {
			String testName = result.getTestContext().getName();
			if(!testName.equals("Kubernetes Cluster")) 
			{
				String screenshotPath = null;
				screenshotPath = captureScreenshot(result.getTestContext().getName()+ "_" +result.getMethod().getMethodName()+".jpg");
				extentTest
				.pass(m.getName() + " is passed")
				.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			}
		}
		
		extentTest.assignCategory(m.getAnnotation(Test.class).groups());
	}
	
	public static ChromeOptions getWinChromeOptions() {
		final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.addArguments("--ignore-certificate-errors");
        return chromeOptions;
	}
	
	public static ChromeOptions getChromeOptions() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.setExperimentalOption("useAutomationExtension", false);

        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--ignore-ssl-errors=yes");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--window-size=1580,1280");

        final HashMap<String, Object> prefs = new HashMap();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        return chromeOptions;
    }
	
	public String captureScreenshot(String fileName) {
		LocalDateTime myDateObj = LocalDateTime.now();
		if(screenshotsSubFolderName == null) {
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		    screenshotsSubFolderName = myDateObj.format(myFormatObj);
		}
		
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String formattedDate = myDateObj.format(myFormatObj);
	    String strUrl = driver.getCurrentUrl();
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File("./Screenshots/"+ screenshotsSubFolderName+"/"+fileName);
		try {
			FileUtils.copyFile(sourceFile, destFile);
			addTextWatermark(strUrl + " | " + formattedDate,destFile,destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Screenshot saved successfully");
		return destFile.getAbsolutePath();
	}
	
	public void sleep(long m)
	{
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean WaitUntilElementExists(WebDriver driver, String xpath, int timeout)
	{
		try
		{
		 WebDriverWait wait = new WebDriverWait (driver, timeout);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		 return true;
		}
		catch(TimeoutException t)
		{
			return false;
		}
	}
	
	public boolean ElementEnabled(WebDriver driver, WebElement element, int timeout)
	{
		 WebDriverWait wait = new WebDriverWait (driver, timeout);
		 wait.until(ExpectedConditions.visibilityOfAllElements(element));
		 return true;
	}
	
	public static void addTextWatermark(String text, File sourceImageFile, File destImageFile) {
	    try {
	        BufferedImage sourceImage = ImageIO.read(sourceImageFile);
	        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
	 
	        // initializes necessary graphic properties
	        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
	        g2d.setComposite(alphaChannel);
	        g2d.setColor(Color.BLACK);
	        g2d.setFont(new Font("Arial", Font.BOLD, 24));
	        FontMetrics fontMetrics = g2d.getFontMetrics();
	        Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);
	 
	        // calculates the coordinate where the String is painted
	        int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
	        int centerY = sourceImage.getHeight() - 150;
	        System.out.println(centerX);
	        System.out.println(centerY);
	 
	        // paints the textual watermark
	        g2d.drawString(text, 25, centerY);
	 
	        ImageIO.write(sourceImage, "png", destImageFile);
	        g2d.dispose();
	 
	        System.out.println("The tex watermark is added to the image.");
	 
	    } catch (IOException ex) {
	        System.err.println(ex);
	    }
	}
}