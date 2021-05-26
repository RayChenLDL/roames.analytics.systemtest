package com.roames.test.tasks;
/*
 * Author: Ray.Chen@roames.com.au
 * 
 * Function: googlePage sample to setup the selenium framework
 */

import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.objects.googlePageLocators;
import com.roames.test.utilities.TestcaseUtilities;

public class googlePage extends PageBase {		
		
		public googlePageLocators googlePage;
		
		public googlePage() {	
			
			// Using Selenium Page Object Factory
			// Need to initialize whatever object repository you need to manipulate on this page
			googlePage = new googlePageLocators();
			PageFactory.initElements(getFactory(), googlePage);	
		}		
		
		/**
		 * Do a google search based on the searching keyword provided. Then verify if the title of the result page starts with the keyword.
		 * @param kw - Searching Keyword
		 * @throws ConfigurationException
		 * @throws InterruptedException 
		 */
		public void googleSearchAndCheckPageTitle(String kw) throws ConfigurationException, InterruptedException{		
			
			// Type the searching keyword into the text box
			type(googlePage.searchTextbox, kw, true);	
			captureScreen();
			
			// Input Enter to search
			googlePage.searchTextbox.sendKeys(Keys.ENTER);
			
			// Verify if the title of the result page starts with the searching keyword
			String browserTitle = TestcaseBase.getDriver().getTitle();
			
			// Report pass if it is okay
			if (browserTitle.startsWith(kw)) {
				TestcaseUtilities.getExtentTest().log(LogStatus.PASS, "Page title starts with : '" + kw + "'.");
				TestcaseUtilities.log.info("Page title starts with : '" + kw + "'.");		
			}
			// otherwise report fail
			else {
				TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, "Page title starts with : '" + kw + "'.");
				TestcaseUtilities.log.error("Page title starts with : '" + kw + "'.");	
				throw new InterruptedException("Page title starts with : '" + kw + "'.");
			}
			
			captureScreen();			
	}
}
