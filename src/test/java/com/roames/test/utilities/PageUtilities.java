package com.roames.test.utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.objects.custom.GridPageDataTable;

public class PageUtilities extends PageBase{
	
	/**
	 * To remove the active filter
	 * @param filterName: the full text of the filter
	 * @throws InterruptedException 
	 */
	public void removeActiveFilter(String filterName) throws InterruptedException {
		
		String xpathLittleCross = "//mat-chip//span[contains(text(),'" + filterName + "')]/../i";		
		WebElement littleCross = TestcaseUtilities.getDriver().findElement(By.xpath(xpathLittleCross));
		littleCross.click();
		pageSync();	
		Thread.sleep(3000);
		captureScreen();	
		
		TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Active filter: " + filterName + " has been removed.");
		TestcaseUtilities.log.info("Active filter: " + filterName + " has been removed.");
	}
	
	

	/**
	 * Click the 3 horizontal bars on the column given to show the pop up menu
	 * @param columnName: column which you would invoke the pop up menu for
	 * @throws InterruptedException
	 */
	public void clickToShowTableHeaderMenu(String columnName) throws InterruptedException {
		
		String xpathSelector_tripleHorizontalBarsOuter = "//span[@class='ag-header-cell-text'][text()='" + columnName + "']/../../span[@class='ag-header-icon ag-header-cell-menu-button']";		
		
		WebElement tripleHorizontalBarsOuter = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_tripleHorizontalBarsOuter));
		
		
		tripleHorizontalBarsOuter.click();
		pageSync();		
		
		TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Click to show table header menu for column: " + columnName);
		TestcaseUtilities.log.info("Click to show table header menu for column: " + columnName);
		captureScreen();
	}
	
	
	public boolean checkIfToastErrExists() throws InterruptedException {
		
		String xpathToastError = "//div[@class='toast toast-error']//div[@class='toast-message']";		
		//WebElement toastError = TestcaseUtilities.driver.findElement(By.xpath(xpathToastError));

		//if (toastError.isDisplayed()){
		if (isElementPresent(By.xpath(xpathToastError))) {
			captureScreen();		
			WebElement toastError = TestcaseUtilities.getDriver().findElement(By.xpath(xpathToastError));
			TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, "Some error on the page: " + toastError.getText());
			TestcaseUtilities.log.error("Some error on the page: " + toastError.getText());
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * To check if the page top filter exists.
	 * @param filterName
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyPresenceOfActiveFilter(String filterName) throws InterruptedException {
		
		String xpathLittleCross = "//span[@class='MuiChip-label'][contains(text(),'" + filterName + "')]";
		captureScreen();
		
		if (isElementPresent(By.xpath(xpathLittleCross))) {	
			logTestResult(LogStatus.PASS, "Page top fitler '" + filterName + "' is shown.");
			return true;
		}else {
			logTestResult(LogStatus.FAIL, "Page top fitler '" + filterName + "' is NOT shown.");
			throw new InterruptedException("Page top fitler '" + filterName + "' is NOT shown.");
		}	
	}
	
	/**
	 * To check if the column has been pinned to the left
	 * @param colName
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyPresenceOfPinLeftedColumn(String colName) throws InterruptedException {
		
		String xpath_PinLeftedColumn = "//div[@class='ag-pinned-left-header']//span[@class='ag-header-cell-text'][text()='" + colName + "']";
		captureScreen();
		
		if (isElementPresent(By.xpath(xpath_PinLeftedColumn))) {					
			logTestResult(LogStatus.PASS, "The column '" + colName + "' has been pinned to left.");
			return true;
		}else {			
			logTestResult(LogStatus.FAIL, "The column '" + colName + "' has NOT been pinned to left.");
			return false;
		}	
	}	
	
//	public void clickToSortColumn(String columnName) throws InterruptedException {
//		
//		String xpathSelector_columnLabel = "//span[@class='ag-header-cell-text'][text()='" + columnName + "']";		
//		
//		WebElement columnLabel = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_columnLabel));
//				
//		columnLabel.click();
//		pageSync();		
//		waitForDataGridLoadingIconDisappear();		
//		captureScreen();
//	}
	
	
	/**
	 * Initiate the browser based on the configuration
	 * @throws MalformedURLException 
	 */
	public void initBrowser(ITestContext testContext){
		
		DesiredCapabilities cap=null;
		String seleniumHubHost = System.getProperty("HUB_HOST");
		//String seleniumHubHost = "192.168.99.100";
		
		String seleniumGrid = System.getProperty("SELENIUM_GRID");
		if (StringUtils.isEmpty(seleniumGrid)) {
			seleniumGrid = TestcaseBase.config.getProperty("selneium_grid").toString();
		}
		
		// 7 June 2019: To support cross browser testing
		// 7 April 2021: 
		// Browser value order:
		//                     1 - From Docker command
		//                     2 - From testNG.xml
		//                     3 - From Config.properties
		HashMap<String,String> parameters = new HashMap<String, String>(testContext.getCurrentXmlTest().getAllParameters());
		if (System.getProperty("browser") != null && !System.getProperty("browser").isEmpty()) {
			TestcaseBase.setBrowser(System.getProperty("browser"));
		}else{
			if(parameters.get("browser") != null) {
				TestcaseBase.setBrowser(parameters.get("browser"));
			}else {
				TestcaseBase.setBrowser(TestcaseBase.config.getProperty("browser").toString());
			}
		}
		
		this.logTestResult(LogStatus.INFO, "Test is running with browser: " + TestcaseBase.getBrowser());
		
		if (TestcaseBase.getDriver() == null) {		

			switch (TestcaseBase.getBrowser()) {

			// Browser FIREFOX
			case "firefox":							
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");

				Map<String, Object> prefs_firefox = new HashMap<String, Object>();
				prefs_firefox.put("profile.default_content_setting_values.notifications", 2);
				prefs_firefox.put("credentials_enable_service", false);
				prefs_firefox.put("profile.password_manager_enabled", false);
				FirefoxOptions options_firefox = new FirefoxOptions();
				options_firefox.addArguments("--disable-extensions");
				options_firefox.addArguments("--disable-infobars");		
				options_firefox.addArguments("--start-maximized"); 
				
				// Selenium Grid
				if(seleniumGrid.equalsIgnoreCase("local")){
					// Using Selenium Grid
					cap = DesiredCapabilities.firefox();
					cap.setBrowserName("firefox");
					cap.setPlatform(Platform.ANY);
					cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options_firefox);
					try {
						TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":4444/wd/hub"),cap));
					}catch(MalformedURLException e) {
						TestcaseBase.log.error(e.getStackTrace());
					}
				}else if(seleniumGrid.equalsIgnoreCase("aws")) {
					// Running Selenium Grid nodes on AWS
					cap = DesiredCapabilities.firefox();
					cap.setBrowserName("firefox");
					cap.setPlatform(Platform.ANY);
					cap.setCapability("uuid","RoamesTestAutomation");
					cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options_firefox);
					try {
						TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":4444/wd/hub"),cap));
					}catch(MalformedURLException e) {
						TestcaseBase.log.error(e.getStackTrace());
					}

				}else {
					TestcaseBase.setDriver(new FirefoxDriver(options_firefox));
				}	
				
				logTestResult(LogStatus.INFO,"Launching Browser Firefox");
				TestcaseBase.getDriver().manage().window().maximize();
				break;

				// Browser CHROME	
			case "chrome":					
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");

				Map<String, Object> prefs_chrome = new HashMap<String, Object>();
				prefs_chrome.put("profile.default_content_setting_values.notifications", 2);
				prefs_chrome.put("credentials_enable_service", false);
				prefs_chrome.put("profile.password_manager_enabled", false);
				ChromeOptions options_chrome = new ChromeOptions();
				options_chrome.setExperimentalOption("prefs", prefs_chrome);
				options_chrome.addArguments("--disable-extensions");
				options_chrome.addArguments("--disable-infobars");		
				options_chrome.addArguments("--start-maximized"); 
				options_chrome.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.IGNORE);

				// Selneium Grid
				if(seleniumGrid.equalsIgnoreCase("local")){
					// Using Selenium Grid
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(Platform.ANY);
					cap.setCapability(ChromeOptions.CAPABILITY, options_chrome);
					try {
						TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":4444/wd/hub"),cap));
					}catch(MalformedURLException e) {
						TestcaseBase.log.error(e.getStackTrace());
					}
				}else if(seleniumGrid.equalsIgnoreCase("aws")) {
					// Running Selenium Grid nodes on AWS
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(Platform.ANY);
					cap.setCapability("uuid","RoamesTestAutomation");
					cap.setCapability(ChromeOptions.CAPABILITY, options_chrome);
					try {
						TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":4444/wd/hub"),cap));
					}catch(MalformedURLException e) {
						TestcaseBase.log.error(e.getStackTrace());
					}

				}else {
					TestcaseBase.setDriver(new ChromeDriver(options_chrome));
				}				

				logTestResult(LogStatus.INFO,"Launching Browser Chrome.");
				break;

			// Browser IE
			case "ie":				
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				
				InternetExplorerOptions options_ie = new InternetExplorerOptions();
				cap = DesiredCapabilities.internetExplorer();
				options_ie.merge(cap);
				options_ie.requireWindowFocus();

				TestcaseBase.setDriver(new InternetExplorerDriver(options_ie));
				logTestResult(LogStatus.INFO,"Launching Browser IE.");
				//TestcaseBase.ngWebDriver = new NgWebDriver((InternetExplorerDriver)TestcaseBase.driver);
				break;

			// Browser EDGE	
			case "edge":
				EdgeOptions options_edge = new EdgeOptions();
				options_edge.setCapability("profile.default_content_setting_values.notifications", 2);
				options_edge.setCapability("credentials_enable_service", false);
				options_edge.setCapability("profile.password_manager_enabled", false);

				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\msedgedriver.exe");
								
				cap = DesiredCapabilities.edge();
				cap.setBrowserName("MicrosoftEdge");
				cap.setPlatform(Platform.ANY);
				cap.setCapability("uuid","RoamesTestAutomation");
				//cap.setCapability(EdgeOptions., options_edge);
				
				// Selenium Grid
				if(seleniumGrid.equalsIgnoreCase("local")){
					// Using Selenium Grid
					try {
						TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":4444/wd/hub"),cap));
					}catch(MalformedURLException e) {
						TestcaseBase.log.error(e.getStackTrace());
					}
				}else if(seleniumGrid.equalsIgnoreCase("aws")) {
					try {
						TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":4444/wd/hub"),cap));
					}catch(MalformedURLException e) {
						TestcaseBase.log.error(e.getStackTrace());
					}

				}else {
					TestcaseBase.setDriver(new EdgeDriver(options_edge));
				}	
				
				logTestResult(LogStatus.INFO,"Launching Browser MS Edge");
				TestcaseBase.getDriver().manage().window().maximize();
				break;

			// Browser Safari	
			case "Safari":
				logTestResult(LogStatus.INFO,"Safari is not supported at the time being, coming soon.");
				break;
				
			// Browser Opera - not working yet
			case "opera":
				System.setProperty("webdriver.opera.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\operadriver.exe");
				
				System.setProperty("opera.binary",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\operadriver.exe");
				
				OperaOptions options = new OperaOptions();
				//options.setBinary(System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\operadriver.exe");
				options.addArguments("--use-fake-ui-for-media-stream");
				options.addArguments("--use-fake-device-for-media-stream");
								
				cap = DesiredCapabilities.operaBlink();
				cap.setCapability(OperaOptions.CAPABILITY, options);
				cap.setBrowserName("Opera");
				cap.setPlatform(Platform.ANY);
				cap.setCapability("uuid","RoamesTestAutomation");
				cap.setAcceptInsecureCerts(true);
				cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
				
				
				// Selenium Grid
				if(seleniumGrid.equalsIgnoreCase("local")){
					// Using Selenium Grid
					try {
						TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":4444/wd/hub"),cap));
					}catch(MalformedURLException e) {
						TestcaseBase.log.error(e.getStackTrace());
					}
				}else if(seleniumGrid.equalsIgnoreCase("aws")) {
					try {
						TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":4444/wd/hub"),cap));
					}catch(MalformedURLException e) {
						TestcaseBase.log.error(e.getStackTrace());
					}

				}else {
					TestcaseBase.setDriver(new OperaDriver());
				}	
				
				logTestResult(LogStatus.INFO,"Launching Browser Opera");
				TestcaseBase.getDriver().manage().window().maximize();
				break;
			}			
		
					
			TestcaseBase.getDriver().get(TestcaseBase.config.getString("RoamesDataGridURL_" + TestcaseBase.testingEnv));
			
			//TestcaseBase.getDriver().manage().window().maximize();
			TestcaseBase.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS)
				.implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
				
			//TestcaseBase.wait = new WebDriverWait(TestcaseBase.driver, 180);
			TestcaseBase.setWait(new WebDriverWait(TestcaseBase.getDriver(), 180));
			
			//factory = new AjaxElementLocatorFactory(TestcaseBase.driver,Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")));
			setFactory(new AjaxElementLocatorFactory(TestcaseBase.getDriver(),Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait"))));
		}
	}
	
	/**
	 * Initiate the browser based on the env
	 * @throws MalformedURLException 
	 */
	public void initBrowser(String env){
		
		DesiredCapabilities cap=null;
		String seleniumHubHost = System.getProperty("HUB_HOST");
		
		if (TestcaseBase.getDriver() == null) {
		
			if(TestcaseBase.config.getProperty("browser").equals("firefox")){
				//TestcaseBase.driver = new FirefoxDriver();
				TestcaseBase.setDriver(new FirefoxDriver());
				TestcaseBase.log.debug("Launching Browser Firefox");
				//TestcaseBase.ngWebDriver = new NgWebDriver((FirefoxDriver)TestcaseBase.driver);
			}else if(TestcaseBase.config.getProperty("browser").equals("chrome")){
				
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
	
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-infobars");
	
				//TestcaseBase.driver = new ChromeDriver(options);
				// Using Selneium Grid or not
				if(TestcaseBase.config.getProperty("selneium_grid").equals("true")){
					// Using Selenium Grid
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(Platform.ANY);
					try {
						//TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"),cap));
						TestcaseBase.setDriver(new RemoteWebDriver(new URL("http://" + seleniumHubHost + ":4444/wd/hub"),cap));
					}catch(MalformedURLException e) {
						TestcaseBase.log.error(e.getStackTrace());
					}
				}else {
					TestcaseBase.setDriver(new ChromeDriver(options));
				}				
				
				TestcaseBase.log.debug("Launching Browser Chrome.");
				//TestcaseBase.ngWebDriver = new NgWebDriver((ChromeDriver)TestcaseBase.driver);
			}else if(TestcaseBase.config.getProperty("browser").equals("ie")){
				
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				
				//TestcaseBase.driver = new InternetExplorerDriver();
				TestcaseBase.setDriver(new InternetExplorerDriver());
				TestcaseBase.log.debug("Launching Browser IE.");
				//TestcaseBase.ngWebDriver = new NgWebDriver((InternetExplorerDriver)TestcaseBase.driver);
			}
					
			TestcaseBase.getDriver().get(TestcaseBase.config.getString("RoamesDataGridURL_" + env));
			
			TestcaseBase.getDriver().manage().window().maximize();
			TestcaseBase.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS)
				.implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
				
			//TestcaseBase.wait = new WebDriverWait(TestcaseBase.driver, 180);
			TestcaseBase.setWait(new WebDriverWait(TestcaseBase.getDriver(), 180));
			
			//factory = new AjaxElementLocatorFactory(TestcaseBase.driver,Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")));
			setFactory(new AjaxElementLocatorFactory(TestcaseBase.getDriver(),Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait"))));
		}
	}
}
