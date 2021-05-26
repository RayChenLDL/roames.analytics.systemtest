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
import com.roames.test.objects.custom.GridPageFavoriteDropDown;
import com.roames.test.objects.custom.GridPageTableHeaderColumnMenu;
import com.roames.test.objects.custom.GridPageTopFilter;
import com.roames.test.tasks.googlePage;
import com.roames.test.tasks.datagrid.HomePage;
import com.roames.test.tasks.portal.InsightTabPage;
import com.roames.test.utilities.CommonUtilities;
import com.roames.test.utilities.PageUtilities;
import com.roames.test.utilities.TestcaseUtilities;


public class Roames_DataGrid_Std_TC006_Favourite_Filter extends TestcaseBase {

	/*
	 * Author: Ray.Chen@roames.com.au
	 * 
	 */

	@BeforeTest
	public void preProcessingTestData(){
		super.setupTestcasename();	
	}

	@Test(dataProviderClass=TestcaseUtilities.class,dataProvider="dpJson")
	public void roames_DataGrid_Std_TC006_Favourite_Filter(Hashtable<String,String> data, ITestContext testContext) throws Exception {

		setCurrentRow((int)Double.parseDouble(data.get("#")));

		if(data.get("RunFlag").equalsIgnoreCase("N")){			
			throw new SkipException("Skipping the test as the Run Flag is NO");						
		}

		PageUtilities pu = new PageUtilities();
		pu.initBrowser(testContext);
		HomePage taskPortalHomePage = new HomePage();

		String client = data.get("Client_" + testingEnv);
		String dataGridName = data.get("DataGrid_Name_" + testingEnv);
		String filter_column = data.get("filter_column_" + testingEnv);	
		String filter_ngReflectName = data.get("Filter_ngReflectName_" + testingEnv);
		String value1 = data.get("Value1_for_Filters_" + testingEnv);
		String value2 = data.get("Value2_for_Filters_" + testingEnv);
		String value3 = data.get("Value3_for_Filters_" + testingEnv);

		String PinLeft_column = data.get("PinLeft_column_" + testingEnv);

		String[] options = {value1,value2};
		String[] options_ext = {value3};

		// Login Portal
		taskPortalHomePage.loginRoamesPortal();

		// Go to the grid		
		taskPortalHomePage.gotoLeftNavTab(data.get("GridGroup_" + testingEnv));
		taskPortalHomePage.gotoGrid(client, dataGridName);
		taskPortalHomePage.hideLeftNavigation();
		taskPortalHomePage.hideRighColumnstNavigation();

		// Apply Filter before creating new favorite		
		GridPageTopFilter objGridPageTopFilter = new GridPageTopFilter(filter_ngReflectName, filter_column);
		objGridPageTopFilter.selectMultipleOptionsByVisibleText(options);

		// Create 1st new favorite
		GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();

		// Generate the favorite name dynamically
		String favoriteName_1st = filter_column.replaceAll(" ", "") + "_" + String.valueOf(CommonUtilities.getRandomNumber(100,1000));
		taskGridPageFavoriteDropDown.createNewFavourite(favoriteName_1st);

		// Clear Current Favourite
		taskGridPageFavoriteDropDown.clearCurrentFavourite();

		// Create 2nd new favorite
		// Pin Column before creating new favorite
		pu.clickToShowTableHeaderMenu(PinLeft_column);
		GridPageTableHeaderColumnMenu objGridPageTableHeaderColumnMenu = new GridPageTableHeaderColumnMenu(PinLeft_column);	
		objGridPageTableHeaderColumnMenu.pinColumnLeft();
		
		// Generate the favorite name dynamically
		String favoriteName_2nd = PinLeft_column.replaceAll(" ", "") + "_" + String.valueOf(CommonUtilities.getRandomNumber(100,1000));
		taskGridPageFavoriteDropDown.createNewFavourite(favoriteName_2nd);
		
		// Delete 2nd new favorite
		taskGridPageFavoriteDropDown.deleteFavourite(favoriteName_2nd);
		Thread.sleep(2000);	

		// Select the 1st new Favourite and Check
		taskGridPageFavoriteDropDown.selectFavourite(favoriteName_1st);
		Thread.sleep(3000);	

		// To check the filter for the 1st new favorite
		String pageTopFilter = filter_column + " is \"" + value1 + " or " + value2 + "\"";
		pu.verifyPresenceOfActiveFilter(pageTopFilter);		

		// Delete 1st new favorite
		taskGridPageFavoriteDropDown.deleteFavourite(favoriteName_1st);	
	}	

	@AfterMethod
	public void tearDown(){
		if(TestcaseBase.driver!=null){
			PageBase.logoutPortal();	
		}
	}
}

