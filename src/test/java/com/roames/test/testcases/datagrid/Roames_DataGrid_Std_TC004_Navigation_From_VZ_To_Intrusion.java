package com.roames.test.testcases.datagrid;

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
import com.roames.test.objects.custom.GridPageDataTable;
import com.roames.test.objects.custom.GridPageToolPanel;
import com.roames.test.tasks.googlePage;
import com.roames.test.tasks.datagrid.HomePage;
import com.roames.test.tasks.portal.InsightTabPage;
import com.roames.test.utilities.PageUtilities;
import com.roames.test.utilities.TestcaseUtilities;


public class Roames_DataGrid_Std_TC004_Navigation_From_VZ_To_Intrusion extends TestcaseBase {

	/*
	 * Author: Ray.Chen@roames.com.au
	 * 
	 */

	@BeforeTest
	public void preProcessingTestData(){
		super.setupTestcasename();	
	}

	@Test(dataProviderClass=TestcaseUtilities.class,dataProvider="dpJson")
	public void roames_DataGrid_Std_TC004_Navigation_From_VZ_To_Intrusion(Hashtable<String,String> data, ITestContext testContext) throws Exception {

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
		taskPortalHomePage.hideLeftNavigation();
		taskPortalHomePage.hideRighColumnstNavigation();

		// To check the intrusion numbers in BAY Grid and Intrusion Grid
		taskPortalHomePage.navFromVZtoIntrusionNVerifyIntrusionTotal(dataGridName, Integer.valueOf(data.get("rowNumber")));
	}	

	@AfterMethod
	public void tearDown(){
		if(TestcaseBase.driver!=null){
			TestcaseBase.getDriver().quit();
			TestcaseBase.setDriver(null);	
		}
	}
}

