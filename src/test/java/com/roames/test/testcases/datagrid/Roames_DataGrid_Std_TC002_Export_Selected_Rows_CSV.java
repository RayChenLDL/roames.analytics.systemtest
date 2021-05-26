package com.roames.test.testcases.datagrid;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Hashtable;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.roames.test.base.PageBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.tasks.googlePage;
import com.roames.test.tasks.datagrid.HomePage;
import com.roames.test.tasks.portal.InsightTabPage;
import com.roames.test.utilities.CommonUtilities;
import com.roames.test.utilities.PageUtilities;
import com.roames.test.utilities.TestcaseUtilities;


public class Roames_DataGrid_Std_TC002_Export_Selected_Rows_CSV extends TestcaseBase {

	/*
	 * Author: Ray.Chen@roames.com.au
	 * 
	 */

	@BeforeTest
	public void preProcessingTestData(){
		super.setupTestcasename();	
	}

	@Test(dataProviderClass=TestcaseUtilities.class,dataProvider="dpJson")
	public void roames_DataGrid_Std_TC002_Export_Selected_Rows_CSV(Hashtable<String,String> data, ITestContext testContext) throws Exception {

		setCurrentRow((int)Double.parseDouble(data.get("#")));

		if(data.get("RunFlag").equalsIgnoreCase("N")){			
			throw new SkipException("Skipping the test as the Run Flag is NO");						
		}

		PageUtilities pu = new PageUtilities();
		pu.initBrowser(testContext);
		HomePage taskPortalHomePage = new HomePage();

		String client = data.get("Client_" + testingEnv);
		String dataGridName = data.get("DataGrid_Name_" + testingEnv);

		// Login Portal
		taskPortalHomePage.loginRoamesPortal();

		// Go to the grid
		taskPortalHomePage.gotoLeftNavTab(data.get("GridGroup_" + testingEnv));
		taskPortalHomePage.gotoGrid(client, dataGridName);

		// To check each individual rows defined in the test data file
		String rowNumbers = data.get("rowNumbers");
		String[] rowNumbersArrayString = rowNumbers.split(",");
		Integer[] rowNumbersArray = new Integer[rowNumbersArrayString.length];
		for(int i=0;i<rowNumbersArray.length;i++) {
			rowNumbersArray[i] = Integer.valueOf(rowNumbersArrayString[i]);
		}	

		String exportFileName = dataGridName + "_" + CommonUtilities.getTimestampInteger();

		switch(data.get("exportFormat")) {
			case "CSV" :
				exportFileName = exportFileName + ".csv";
				break; // 
	
			case "Excel" :
				exportFileName = exportFileName + ".xls";
				break; // 
	
			case "KML" :
				exportFileName = exportFileName + ".kml";
				break; 
	
			default :
		}

		// Export data to file
		taskPortalHomePage.exportSelectedRows(dataGridName, rowNumbersArray, data.get("exportFormat"), exportFileName);
	}	

	@AfterMethod
	public void tearDown(){
		if(TestcaseBase.driver!=null){
			TestcaseBase.getDriver().quit();
			TestcaseBase.setDriver(null);	
		}
	}
}

