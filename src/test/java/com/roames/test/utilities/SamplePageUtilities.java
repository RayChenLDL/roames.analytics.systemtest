package com.roames.test.utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.roames.test.base.PageBase;
import com.roames.test.base.TestcaseBase;

public class SamplePageUtilities extends PageBase{
	
	/**
	 * Open a new browser and go to google home page
	 * @throws ConfigurationException
	 */
	public void openGooglePage() throws ConfigurationException{		
		DesiredCapabilities cap=null;
		String seleniumHubHost = System.getProperty("HUB_HOST");

		String seleniumGrid = System.getProperty("SELENIUM_GRID");
		if (StringUtils.isEmpty(seleniumGrid)) {
			seleniumGrid = TestcaseBase.config.getProperty("selneium_grid").toString();
		}

		if (TestcaseBase.getDriver() == null) {

			if(TestcaseBase.config.getProperty("browser").equals("firefox")){
				TestcaseBase.setDriver(new FirefoxDriver());
				TestcaseBase.log.debug("Launching Browser Firefox");
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
			}else if(TestcaseBase.config.getProperty("browser").equals("ie")){

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");

				TestcaseBase.setDriver(new InternetExplorerDriver());
				TestcaseBase.log.debug("Launching Browser IE.");
			}

			TestcaseBase.getDriver().get("http://www.google.com.au/");

			TestcaseBase.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS)
			.implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);

			TestcaseBase.setWait(new WebDriverWait(TestcaseBase.getDriver(), 180));

			setFactory(new AjaxElementLocatorFactory(TestcaseBase.getDriver(),Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait"))));
		}
	}
}
