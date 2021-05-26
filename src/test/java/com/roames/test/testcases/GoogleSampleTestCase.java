package com.roames.test.testcases;

import java.util.Hashtable;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.roames.test.base.TestcaseBase;
import com.roames.test.tasks.googlePage;
import com.roames.test.utilities.SamplePageUtilities;
import com.roames.test.utilities.TestcaseUtilities;


public class GoogleSampleTestCase extends TestcaseBase {
	
	@BeforeTest
	public void testcaseInit(){
		super.setupTestcasename();
	}
	
	@Test(dataProviderClass=TestcaseUtilities.class,dataProvider="dpJson")
	public void googleTest(Hashtable<String,String> data) throws InterruptedException, ConfigurationException {
		
		setCurrentRow((int)Double.parseDouble(data.get("#")));
		
		// Skip current row if the RunFlag = 'N'
		if(data.get("RunFlag").equalsIgnoreCase("N")){			
			throw new SkipException("Skipping the test as the Run Flag is NO");						
		}
		
		// New a instance of class SamplePageUtilities
		SamplePageUtilities taskSamplePageUtilities = new SamplePageUtilities();
		
		// Open a New Browser and go to Google home page
		taskSamplePageUtilities.openGooglePage();
		
		// New a instance of class GooglePage
		googlePage googleP = new googlePage();
				
		// Search and check the title of result page
		googleP.googleSearchAndCheckPageTitle(data.get("kw"));		
	}	

	@AfterMethod
	public void tearDown(){
		if(TestcaseBase.driver!=null){
			TestcaseBase.getDriver().quit();
			TestcaseBase.setDriver(null);
		}
	}
}

