package com.Crossbrowser;

import java.io.IOException;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class CrossBrowserTest {
	
	
	WebDriver driver;
	ExtentHtmlReporter htmlreport;
	ExtentReports extentreport;
	public ExtentTest test;
	
	@BeforeTest
	public void setupReportAndReport()
	{
		htmlreport=new ExtentHtmlReporter(System.getProperty("user.dir")+"/ExtentReport/CrossBrowserTest.html");
		htmlreport.config().setDocumentTitle("Automating CrossBroser testing");
		htmlreport.config().setTheme(Theme.DARK);
		htmlreport.config().setReportName("HomepageTest");
		extentreport=new ExtentReports();
		extentreport.attachReporter(htmlreport);
		extentreport.setSystemInfo("OS", "Windows 7");
		extentreport.setSystemInfo("Hostname", "admin-PC");
		extentreport.setSystemInfo("Tester Name", "Nitin");
		extentreport.setSystemInfo("Browser Name", "Chrome");
	}
	@Parameters("browser")
	@Test
	public void launchApp(String browsername)
	
	
	{
		
		test=extentreport.createTest("CrossBrowserTest");
		
		if(browsername.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "D:\\eclipse data\\Drivers\\Chrome Driver\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.get("https://www.google.com");
		}
		else if(browsername.equalsIgnoreCase("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "D:\\eclipse data\\Drivers\\FF\\geckodriver.exe");
			driver=new FirefoxDriver();
			driver.get("https://artoftesting.com/launching-browsers-in-selenium");
			String title=driver.getTitle();
			String actual_title="Google";
			Assert.assertEquals(title, actual_title);
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.close();
	}
	@AfterTest
	public void tearDownReport()
	{
		extentreport.flush();
	}
	
	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
            //String screenShotPath=screenshot.TakeScreenShotforExtent(driver, result.getName());
            //test.addScreenCaptureFromPath(screenShotPath);
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
        }
        else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }

}
}
