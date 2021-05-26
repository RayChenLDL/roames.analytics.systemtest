package com.roames.test.base;
/*
 * Author: Ray.Chen@roames.com.au
 * 
 * Function: FUGRO Roames Portal Base Page Class. 
 * 				Every task class should inherit from this base class.
 */

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.basics.Settings;
import org.testng.Reporter;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.constant.LocatorType;
import com.roames.test.objects.InsightTabLeftNavLocators;
import com.roames.test.objects.TopNavLocators;
import com.roames.test.objects.datagrid.LeftNavLocators;
import com.roames.test.utilities.ExcelReader;
import com.roames.test.utilities.ExtentManager;
import com.roames.test.utilities.JSWaiter;
import com.roames.test.utilities.TestcaseUtilities;
import com.roames.test.utilities.TestcaseUtilitiesAPI;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;

public class PageBase {
	
	// The top navigation page should be included in any page
	public static ThreadLocal<AjaxElementLocatorFactory> factory = new ThreadLocal<AjaxElementLocatorFactory>();
	public static ThreadLocal<LeftNavLocators> leftNav  = new ThreadLocal<LeftNavLocators>();
	
	public PageBase() {
		
		if (getFactory() == null) {
			setFactory(new AjaxElementLocatorFactory(TestcaseBase.getDriver(),Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait"))));	
		}
		
		if (getLeftNav() == null) {
			setLeftNav(new LeftNavLocators());
		}
				
		PageFactory.initElements(getFactory(), getLeftNav());
	}
	
	/**
	 * Click the web element, normally for button or link
	 * @param element
	 * @param logging
	 * @throws InterruptedException 
	 */
	public void click(WebElement element, boolean logging) throws InterruptedException {

		if (logging) {
			/*
			TestcaseBase.log.debug("Clicking on an Element : "+element);
			TestcaseBase.getExtentTest().log(LogStatus.INFO, "Clicking on : " + element);
			
			if(TestcaseUtilities.getPDFTestReport() != null) {
				TestcaseBase.getPDFTestReport().logTestResult(LogStatus.INFO.toString(), "Clicking on : " + element);
			}	
			*/
			
			logTestResult(LogStatus.INFO, "Clicking on : " + element);
		}
		
		element.click();
	}
	
	/**
	 * Type text into the web element, normally for text box
	 * @param element
	 * @param value
	 * @param logging
	 */
	public void type(WebElement element, String value, boolean logging) {
		
		if (logging) {
			/*
			TestcaseBase.log.debug("Typing in an Element : "+element+" entered value as : "+value);		
			TestcaseBase.getExtentTest().log(LogStatus.INFO, "Typing in : " + element + " entered value as " + value);
			
			if(TestcaseUtilities.getPDFTestReport() != null) {
				TestcaseBase.getPDFTestReport().logTestResult(LogStatus.INFO.toString(), "Typing in : " + element + " entered value as " + value);
			}
			*/
			
			logTestResult(LogStatus.INFO, "Typing in : " + element + " entered value as " + value);
		}

		element.clear();
		element.sendKeys(value);
	}

	/**
	 * Close the current browser
	 */
	public static void logoutPortal(){		
			TestcaseBase.getDriver().quit();
			//TestcaseBase.driver = null;
			TestcaseBase.setDriver(null);
	}
	
	/**
	 * Capture the screen shot as required
	 */
	public void captureScreen() {
		System.setProperty("org.uncommons.reportng.escape-output","false");
		//try {
			TestcaseUtilities.captureScreenshot();
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}

		TestcaseBase.getExtentTest().log(LogStatus.INFO, TestcaseBase.getExtentTest().addScreenCapture(TestcaseUtilities.getScreenshotName()));	
		
		if(TestcaseUtilities.getPDFTestReport() != null) {
			TestcaseBase.getPDFTestReport().insertImg(TestcaseUtilities.getScreenshotPath() + TestcaseUtilities.getScreenshotName());	
		}	
		
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+TestcaseUtilities.getScreenshotName()+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+TestcaseUtilities.getScreenshotName()+"><img src="+TestcaseUtilities.getScreenshotName()+" height=200 width=200></img></a>");
	}
	
	/**
	 * To wait for the web page fully loaded
	 */
    public void pageSync() { 
    	
    	
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wd) {
                //this will tell if page is loaded
                return "complete".equals(((JavascriptExecutor) wd).executeScript("return document.readyState"));                
            }
        };

        //wait for page complete
        //TestcaseUtilities.wait.until(pageLoadCondition); 
    	
    	// Instead using normal Selenium wait, using NGWebDriver to wait for Angular requests to be processed
        //TestcaseBase.ngWebDriver.waitForAngularRequestsToFinish();
        
        JSWaiter jsWaiter = new JSWaiter();
        
        jsWaiter.setDriver(TestcaseUtilities.getDriver());
        jsWaiter.waitUntilAngularReady();
    }   
    
    /**
     * For the page with data grid inside, wait until the "Loading" Icon to disappear
     * @throws InterruptedException
     */
    public void waitForDataGridLoadingIconDisappear() throws InterruptedException {
    	waitForDataGridLoadingIconDisappear("");    	
    }
    
    
    /**
     * For the page with data grid inside, wait until the "Loading" Icon to disappear
     */
    public void waitForDataGridLoadingIconDisappear(String iconText) throws InterruptedException {
		
		String xpathDataGridLoadingIcon;		
		WebElement dataGridLoadingIcon;
		boolean isDataGridLoadingIconShowing = true;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date startTime;
		
		if(iconText.equals("")) {
			 xpathDataGridLoadingIcon = "//span[text()='Loading']";
		}else {
			xpathDataGridLoadingIcon = "//span[text()='" + iconText + "']";
		}
		
		try {
			startTime = format.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));

			TestcaseBase.getDriver().manage().timeouts().implicitlyWait(1000,TimeUnit.MILLISECONDS);
			do {

				try {
					dataGridLoadingIcon = TestcaseUtilities.getDriver().findElement(By.xpath(xpathDataGridLoadingIcon));
					Thread.sleep(2000);
					System.out.println(new Timestamp(System.currentTimeMillis()) + " // Thread(" + getThreadValue(TestcaseUtilities.driver.get()) + ")@_@ Waiting for 'Loading' Icon to disappear...");

					// Check if timeout - 3 minutes (180 seconds)
					Date endTime = format.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
					long duration = (endTime.getTime() - startTime.getTime()) / 1000; 

					if (duration > 180) {
						isDataGridLoadingIconShowing = false;
						System.out.println("Timeout of waiting for Data Grids 'Loading' after 3 minutes.");

						// Print the browser console log
						LogEntries logEntries = TestcaseBase.getDriver().manage().logs().get(LogType.BROWSER);
						for (LogEntry entry : logEntries) {
							logTestResult(LogStatus.INFO, new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
						}						

						throw new InterruptedException("Timeout of waiting for Data Grids 'Loading' after 3 minutes.");
					}
				}catch(NoSuchElementException e) {
					isDataGridLoadingIconShowing = false;
				}
			}
			while (isDataGridLoadingIconShowing);

			TestcaseBase.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);

		} 
		catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	} 
    
    /**
     * To check if the web element is displayed on the screen
     * @param element
     * @return
     */
    public boolean isElementDisplayed(WebElement element) {
    	
    	//Set the timeout to something low
        TestcaseBase.getDriver().manage().timeouts().implicitlyWait(1000,TimeUnit.MILLISECONDS);        
        boolean result = true;
        
        try {        	
             if (element.isDisplayed()) {
     			result = true;
             }else {
      			result = false;}
            } 
        catch(NoSuchElementException e) {
      			result = false;
        }
        
        TestcaseBase.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
        return result;
    }
    
    /**
     * Verify if the element is displayed given xpath
     * @param xpath
     * @param seconds
     * @return
     * @throws InterruptedException
     */
    public boolean isElementDisplayed(String xpath, int seconds) throws InterruptedException {
        try {
        	TestcaseBase.getDriver().manage().timeouts().implicitlyWait(seconds*1000, TimeUnit.MILLISECONDS);
        	TestcaseBase.getDriver().findElement(By.xpath(xpath));
        	return true;              
          } catch(Exception e) {
            return false;
          }finally{
        	  TestcaseBase.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
          }
    }    
    
    public boolean isElementNotDisplayed(WebElement element) {
    	
    	//Set the timeout to something low
        TestcaseBase.getDriver().manage().timeouts().implicitlyWait(1000,TimeUnit.MILLISECONDS);        
        boolean result = true;
        
        try {        	
             if (!element.isDisplayed()) {
     			result = true;
             }else {
      			result = false;}
            } 
        catch(Exception e) {
      			result = false;
        }
        
        TestcaseBase.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
        return result;
    }
    
    /**
     * To check if the web element is displayed on the screen with time windows given
     * @param element
     * @param seconds
     * @return
     * @throws InterruptedException
     */
    public boolean isElementDisplayed(WebElement element, int seconds) throws InterruptedException {
        try {
        	TestcaseUtilities.getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);

        	return element.isDisplayed();          	
             
          } catch(NoSuchElementException e) {
             return false;
          }finally{
        	 TestcaseUtilities.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
          }
    }
    

    public boolean isElementEnabled(WebElement element, int seconds) throws InterruptedException {
        try {
        	TestcaseUtilities.getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);

        	return element.isEnabled();          	
             
          } catch(NoSuchElementException e) {
             return false;
          }finally{
        	 TestcaseUtilities.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
          }
    }
    
    public void isElementDisplayed(WebDriver driver, WebElement element, int timeOut) {
    	WebDriverWait wait = new WebDriverWait(driver, timeOut);
    	//wait.until(ExpectedConditions.elementToBeClickable(element));
    	
    	}
    
    /**
     * Dynamically find the sidebar heading as the heading name is different from env to env
     * @param headingName
     * @return
     */
    public WebElement findSidebarItemHeading(String headingName) {
      return TestcaseUtilities.getDriver().findElement(By.xpath("//div[text()='" + headingName + "']"));
    }
    
    /**
     * Dynamically find the sub heading as the sub heading name is different from env to env
     * @param subheadingName
     * @return
     */
    public WebElement findSidebarItemSubHeading(String subheadingName) {
        return TestcaseUtilities.getDriver().findElement(By.cssSelector("a[title='" + subheadingName + "'][class='list-group-item no-padding sidebar-item']"));
      }
    
    
    public boolean isElementPresent(By locator)
    {
        //Set the timeout to something low
        TestcaseBase.getDriver().manage().timeouts().implicitlyWait(1000,TimeUnit.MILLISECONDS);

        try
        {
        	TestcaseBase.getDriver().findElement(locator);
            //If element is found set the timeout back and return true
        	TestcaseBase.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
            return true;
        }
        catch (NoSuchElementException e)
        {
            //If element isn't found, set the timeout and return false
        	TestcaseBase.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
            return false;
        }
    }
    
    public boolean isElementPresent(By locator, int second)
    {
        //Set the timeout to something low
        TestcaseBase.getDriver().manage().timeouts().implicitlyWait(second*1000,TimeUnit.MILLISECONDS);

        try
        {
        	TestcaseBase.getDriver().findElement(locator);
            //If element is found set the timeout back and return true
        	TestcaseBase.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
            return true;
        }
        catch (NoSuchElementException e)
        {
            //If element isn't found, set the timeout and return false
        	TestcaseBase.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
            return false;
        }
    }
    
	// ============ ThreadLocal For factory ========
	public static void setFactory(AjaxElementLocatorFactory ajaxFactory){		
		factory.set(ajaxFactory);
	}

	public static AjaxElementLocatorFactory getFactory(){		
		return factory.get();
	}

	// ============ ThreadLocal For leftNav ========
	public static void setLeftNav(LeftNavLocators leftN){		
		leftNav.set(leftN);
	}

	public static LeftNavLocators getLeftNav(){		
		return leftNav.get();
	}
	
	public String getThreadValue(Object value){		
		String text = value.toString();
		String[] nextText = text.split(" ");
		String text2 = nextText[nextText.length-1].replace("(", "").replace(")", "");
		String[] newText2 = text2.split("-");
		String reqText = newText2[newText2.length-1];
		return reqText;
	}
	
	/**
	 * Log test result into the reports
	 * @param testStatus
	 * @param testDesc
	 */
	public void logTestResult(LogStatus testStatus, String testDesc)
	{
		TestcaseUtilities.getExtentTest().log(testStatus, testDesc);
		TestcaseUtilities.log.info(testDesc);
		System.out.println(testStatus + ": " + testDesc);

		if(TestcaseUtilities.getPDFTestReport() != null) {
			TestcaseUtilities.getPDFTestReport().logTestResult(testStatus.toString(), testDesc);
		}
	}
	
	public void sikuliClick(String patternName) throws FindFailed, InterruptedException {

		String imageFolder = System.getProperty("user.dir") + "\\src\\test\\resources\\sikulixObjects";
		ImagePath.setBundlePath(imageFolder);
		Screen screen = new Screen();
		// Creating Object of Pattern class and specify the path of specified images
		Pattern pattern = new Pattern(patternName);

		// Initialization of driver object 
		Settings.Highlight= true;

		screen.wait(pattern, 20); 
		screen.click(pattern);
		Thread.sleep(2000);		 
	}
	
	public void sikuliClickText(String searchText) throws FindFailed, InterruptedException {

		String imageFolder = System.getProperty("user.dir") + "\\src\\test\\resources\\sikulixObjects";
		ImagePath.setBundlePath(imageFolder);
		Screen screen = new Screen();

		// Initialization of driver object 
		Settings.Highlight= true;

		Region region = Region.create(0,0,1920,1080);
		region.findText(searchText).click();
		Thread.sleep(2000);		 
	}
	
	/**
	 * Return locator BY string which is defined in the ***locator class
	 * @param obj
	 * @param fieldName
	 * @param byType
	 * @return
	 */
	public String getBy(Object obj, String fieldName, LocatorType byType) {
		
		String fullByString, byString;
		try {
			fullByString = (new Annotations(obj.getClass().getDeclaredField(fieldName)).buildBy()).toString();
			
			switch (byType) {
				case XPATH: 
					byString = fullByString.substring(fullByString.indexOf("xpath") + 7, fullByString.length());
					break;
				case CSS: 
					byString = fullByString.substring(fullByString.indexOf("selector") + 10, fullByString.length());
					break;
				default:
					byString = fullByString;
					break;
			}
		} 
		catch (NoSuchFieldException e) { byString = "ERROR";}
		
		return byString;
	}
	
	public void clickToShowTableHeaderMenu(String columnName) throws InterruptedException {
		
		String xpathSelector_tripleHorizontalBarsOuter = "//span[@class='ag-header-cell-text'][text()='" + columnName + "']/../../span[@class='ag-header-icon ag-header-cell-menu-button']";		
		
		WebElement tripleHorizontalBarsOuter = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_tripleHorizontalBarsOuter));
		
		tripleHorizontalBarsOuter.click();
		pageSync();		
		
		TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Click to show table header menu for column: " + columnName);
		TestcaseUtilities.log.info("Click to show table header menu for column: " + columnName);
		captureScreen();
	}
	
}
