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


public class Roames_DataGrid_Std_TC006_Favourite_PinColumn extends TestcaseBase {

	/*
	 * Author: Ray.Chen@roames.com.au
	 * 
	 */

	@BeforeTest
	public void preProcessingTestData(){
		super.setupTestcasename();	
	}

	@Test(dataProviderClass=TestcaseUtilities.class,dataProvider="dpJson")
	public void roames_DataGrid_Std_TC006_Favourite_PinColumn(Hashtable<String,String> data, ITestContext testContext) throws Exception {

		setCurrentRow((int)Double.parseDouble(data.get("#")));

		if(data.get("RunFlag").equalsIgnoreCase("N")){			
			throw new SkipException("Skipping the test as the Run Flag is NO");						
		}

		PageUtilities pu = new PageUtilities();
		pu.initBrowser(testContext);
		HomePage taskPortalHomePage = new HomePage();

		String client = data.get("Client_" + testingEnv);
		String dataGridName = data.get("DataGrid_Name_" + testingEnv);
		String PinLeft_column = data.get("PinLeft_column_" + testingEnv);

		// Login Portal
		taskPortalHomePage.loginRoamesPortal();

		// Go to the grid		
		taskPortalHomePage.gotoLeftNavTab(data.get("GridGroup_" + testingEnv));
		taskPortalHomePage.gotoGrid(client, dataGridName);
		taskPortalHomePage.hideLeftNavigation();
		taskPortalHomePage.hideRighColumnstNavigation();

		// Pin Column before creating new favorite
		pu.clickToShowTableHeaderMenu(PinLeft_column);
		GridPageTableHeaderColumnMenu objGridPageTableHeaderColumnMenu = new GridPageTableHeaderColumnMenu(PinLeft_column);	
		objGridPageTableHeaderColumnMenu.pinColumnLeft();

		// Create new favorite
		GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();

		// Generate the favorite name dynamically
		String favoriteName = PinLeft_column.replaceAll(" ", "") + "_" + String.valueOf(CommonUtilities.getRandomNumber(100,1000));
		taskGridPageFavoriteDropDown.createNewFavourite(favoriteName);

		// Clear Current Favourite
		taskGridPageFavoriteDropDown.clearCurrentFavourite();

		// Select the new Favourite and Check
		taskGridPageFavoriteDropDown.selectFavourite(favoriteName);
		Thread.sleep(2000);	

		// To check if the column has been pinned left
		pu.verifyPresenceOfPinLeftedColumn(PinLeft_column);

		// Clean up
		taskGridPageFavoriteDropDown.deleteFavourite(favoriteName);	
	}	

	@AfterMethod
	public void tearDown(){
		if(TestcaseBase.driver!=null){
			PageBase.logoutPortal();	
		}
	}
}

