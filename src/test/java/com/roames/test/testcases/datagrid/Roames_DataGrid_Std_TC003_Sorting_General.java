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


public class Roames_DataGrid_Std_TC003_Sorting_General extends TestcaseBase {

	/*
	 * Author: Ray.Chen@roames.com.au
	 * 
	 */

	@BeforeTest
	public void preProcessingTestData(){
		super.setupTestcasename();	
	}

	@Test(dataProviderClass=TestcaseUtilities.class,dataProvider="dpJson")
	public void roames_DataGrid_Std_TC003_Sorting_General(Hashtable<String,String> data, ITestContext testContext) throws Exception {

		setCurrentRow((int)Double.parseDouble(data.get("#")));

		if(data.get("RunFlag").equalsIgnoreCase("N")){			
			throw new SkipException("Skipping the test as the Run Flag is NO");						
		}

		PageUtilities pu = new PageUtilities();
		pu.initBrowser(testContext);
		HomePage taskPortalHomePage = new HomePage();

		String client = data.get("Client_" + testingEnv);
		String dataGridName = data.get("DataGrid_Name_" + testingEnv);
		String filter1 = data.get("Filter1_Name_" + testingEnv);
		String filter1_ngReflectName = data.get("Filter1_ngReflectName_" + testingEnv);
		String filter2 = data.get("Filter2_Name_" + testingEnv);
		String filter2_ngReflectName = data.get("Filter2_ngReflectName_" + testingEnv);
		String value1 = data.get("Value1_for_Filters_" + testingEnv);
		String value2 = data.get("Value2_for_Filters_" + testingEnv);		

		// Login Portal
		taskPortalHomePage.loginRoamesPortal();

		// Go to the grid
		//taskPortalHomePage.gotoGrid("Georgia Power", "Intrusions - High Priority");

		taskPortalHomePage.gotoLeftNavTab(data.get("GridGroup_" + testingEnv));
		taskPortalHomePage.gotoGrid(client, dataGridName);
		taskPortalHomePage.hideLeftNavigation();
		taskPortalHomePage.hideRighColumnstNavigation();
		
		String sorting_column = data.get("sorting_column_" + testingEnv);
		String sorting_column_ngReflectName = data.get("sorting_column_ngReflectName_" + testingEnv);
		String sorting_ascOrDesc = data.get("sorting_ascOrDesc_" + testingEnv);
		String sorting_delimiter = data.get("sorting_delimiter_" + testingEnv);

		// Need to make sure the column is in the view before sorting it
		GridPageDataTable taskGridPageDataTable = new GridPageDataTable(dataGridName);
		taskGridPageDataTable.bringColumnIntoView(sorting_column);

		// Apply sorting
		taskGridPageDataTable.clickToSortColumn(sorting_column);

		// Click twice if it is DESC
		if("DESC".equalsIgnoreCase(sorting_ascOrDesc)) {
			taskGridPageDataTable.clickToSortColumn(sorting_column);
		}		

		// To check the sorting
		//pu.verifyColumnSorting(dataGridName, sorting_column, sorting_delimiter, sorting_ascOrDesc);
		taskGridPageDataTable.verifyColumnSorting(sorting_column, sorting_column_ngReflectName, sorting_delimiter, sorting_ascOrDesc);
	}	

	@AfterMethod
	public void tearDown(){
		if(TestcaseBase.driver!=null){
			TestcaseBase.getDriver().quit();
			TestcaseBase.setDriver(null);	
		}
	}
}

