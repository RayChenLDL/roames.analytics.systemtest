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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.objects.custom.GridPageDataTable;

public class PageUtilities_RoamesWorld extends PageBase{
	
	/**
	 * Initiate the browser based on the configuration
	 * @throws MalformedURLException 
	 */
	public void initBrowser(){
		
		DesiredCapabilities cap=null;
		String seleniumHubHost = System.getProperty("HUB_HOST");
		
		String seleniumGrid = System.getProperty("SELENIUM_GRID");
		if (StringUtils.isEmpty(seleniumGrid)) {
			seleniumGrid = TestcaseBase.config.getProperty("selneium_grid").toString();
		}
		
		
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
				options.addArguments("--start-maximized"); 
	
				//TestcaseBase.driver = new ChromeDriver(options);
				// Using Selneium Grid or not
				if(seleniumGrid.equalsIgnoreCase("local")){
					// Using Selenium Grid
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(Platform.ANY);
					cap.setCapability(ChromeOptions.CAPABILITY, options);
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
					cap.setCapability(ChromeOptions.CAPABILITY, options);
					try {
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
					
			TestcaseBase.getDriver().get(TestcaseBase.config.getString("RoamesWorldURL_" + TestcaseBase.testingEnv));
			
			//TestcaseBase.getDriver().manage().window().maximize();
			TestcaseBase.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS)
				.implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
				
			//TestcaseBase.wait = new WebDriverWait(TestcaseBase.driver, 180);
			TestcaseBase.setWait(new WebDriverWait(TestcaseBase.getDriver(), 180));
			
			//factory = new AjaxElementLocatorFactory(TestcaseBase.driver,Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")));
			setFactory(new AjaxElementLocatorFactory(TestcaseBase.getDriver(),Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait"))));
		}
	}
	
}
