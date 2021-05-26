package com.roames.test.tasks.portal;
/*
 * Author: Ray.Chen@roames.com.au
 * 
 * Function: FUGRO Roames Portal PortalInsightTabPage
 */

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import com.paulhammant.ngwebdriver.ByAngular;
import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.objects.DataGridPopupMenu;
import com.roames.test.objects.GridPageFooter;
import com.roames.test.objects.GridPageTopFunctions;
import com.roames.test.objects.HomeTabLocators;
import com.roames.test.objects.InsightVegetationMainLocators;
import com.roames.test.objects.InsightTabLeftNavLocators;
import com.roames.test.objects.LoginPageLocators;
import com.roames.test.objects.PopupWindowExportDataWarningLocators;
import com.roames.test.objects.TopNavLocators;
import com.roames.test.objects.custom.GridPageDataTable;
import com.roames.test.objects.custom.GridPageFavoriteDropDown;
import com.roames.test.objects.custom.GridPageTableHeaderColumnMenu;
import com.roames.test.objects.custom.GridPageToolPanel;
import com.roames.test.objects.custom.GridPageTopFilter;
import com.roames.test.utilities.CommonUtilities;
import com.roames.test.utilities.PageUtilities;
import com.roames.test.utilities.TestcaseUtilities;

public class InsightTabPage extends PageBase {	

	InsightTabLeftNavLocators leftNavInsight;
	InsightVegetationMainLocators objPortalInsightVegetationMain;
	GridPageFooter objGridPageFooter;
	GridPageTopFunctions objGridPageTopFunctions;
	DataGridPopupMenu objDataGridPopupMenu;
	PopupWindowExportDataWarningLocators objPopupWindowExportDataWarningLocators;	

	PageUtilities pu = new PageUtilities();

	/**
	 * Constructor
	 */
	public InsightTabPage(){
		
		leftNavInsight = new InsightTabLeftNavLocators();
		objPortalInsightVegetationMain = new InsightVegetationMainLocators();
		objGridPageFooter = new GridPageFooter();
		objGridPageTopFunctions = new GridPageTopFunctions();
		objDataGridPopupMenu = new DataGridPopupMenu();
		objPopupWindowExportDataWarningLocators = new PopupWindowExportDataWarningLocators();
		
		
		PageFactory.initElements(getFactory(), leftNavInsight);
		PageFactory.initElements(getFactory(), objPortalInsightVegetationMain);
		PageFactory.initElements(getFactory(), objGridPageFooter);
		PageFactory.initElements(getFactory(), objGridPageTopFunctions);
		PageFactory.initElements(getFactory(), objDataGridPopupMenu);
		PageFactory.initElements(getFactory(), objPopupWindowExportDataWarningLocators);
	}
	
	/**
	 * Go to "Network Maintenance Summary" data grid
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoNetworkMaintenence() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Network Maintenence"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("Network Maintenance Summary"), true);
		waitForDataGridLoadingIconDisappear();
		
		// If there is an error of loading data grid, abort the test
		if (pu.checkIfToastErrExists()) {			
			throw new InterruptedException("Error in loading data grid.");
		}
		
		// Restore to default view just in case there is rubbish filters set up by previous test
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		click(objGridPageTopFunctions.btnRestoreDefaultGrid, false);
		waitForDataGridLoadingIconDisappear();
		
		pageSync();
		Thread.sleep(3000);
		
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
	}
	
	/**
	 * Go to "Network Maintenance pole" data grid
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoNetworkMaintenencePole() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Network Maintenence"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("Network Maintenance Pole"), true);
		waitForDataGridLoadingIconDisappear();		
		
		// If there is an error of loading data grid, abort the test
		if (pu.checkIfToastErrExists()) {			
			throw new InterruptedException("Error in loading data grid.");
		}		
		
		// Restore to default view just in case there is rubbish filters set up by previous test
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		click(objGridPageTopFunctions.btnRestoreDefaultGrid, false);
		waitForDataGridLoadingIconDisappear();
		
		pageSync();
		Thread.sleep(3000);

		
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
	}
	
	/**
	 * Go to "Network Maintenance Span" data grid
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoNetworkMaintenenceSpan() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Network Maintenence"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("Network Maintenance Span"), true);
		waitForDataGridLoadingIconDisappear();
		
		// If there is an error of loading data grid, abort the test
		if (pu.checkIfToastErrExists()) {			
			throw new InterruptedException("Error in loading data grid.");			
			//throw new SkipException("Error in loading data grid.");
		}
		
		// Restore to default view just in case there is rubbish filters set up by previous test
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		click(objGridPageTopFunctions.btnRestoreDefaultGrid, false);
		waitForDataGridLoadingIconDisappear();
		
		pageSync();
		Thread.sleep(3000);		
		
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
	}
	
	/**
	 * Go to "Network Maintenance Span Defects" data grid
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoNetworkMaintenenceSpanDefects() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Network Maintenence"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("Network Maintenance Span Defects"), true);
		waitForDataGridLoadingIconDisappear();

		// If there is an error of loading data grid, abort the test
		if (pu.checkIfToastErrExists()) {			
			throw new InterruptedException("Error in loading data grid.");
		}
		
		// Restore to default view just in case there is rubbish filters set up by previous test
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		click(objGridPageTopFunctions.btnRestoreDefaultGrid, false);
		waitForDataGridLoadingIconDisappear();
		
		pageSync();
		Thread.sleep(3000);
		
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
	}
	
	/**
	 * Go to "Network Maintenance Pole Defects" data grid
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoNetworkMaintenencePoleDefects() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Network Maintenence"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("Network Maintenance Pole Defects"), true);
		waitForDataGridLoadingIconDisappear();
		
		// If there is an error of loading data grid, abort the test
		if (pu.checkIfToastErrExists()) {			
			throw new InterruptedException("Error in loading data grid.");
		}			
		
		// Restore to default view just in case there is rubbish filters set up by previous test
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		click(objGridPageTopFunctions.btnRestoreDefaultGrid, false);
		waitForDataGridLoadingIconDisappear();
		
		pageSync();
		Thread.sleep(3000);	
	
		
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
	}
	
	/**
	 * Go to "Network Maintenance Defect" data grid
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoNetworkMaintenenceDefect() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Network Maintenence"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("Network Maintenance Defect"), true);
		waitForDataGridLoadingIconDisappear();
		
		// If there is an error of loading data grid, abort the test
		if (pu.checkIfToastErrExists()) {			
			throw new InterruptedException("Error in loading data grid.");
		}		
		
		// Restore to default view just in case there is rubbish filters set up by previous test
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		click(objGridPageTopFunctions.btnRestoreDefaultGrid, false);
		waitForDataGridLoadingIconDisappear();
		
		pageSync();
		Thread.sleep(3000);
		
		
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
	}
	
	/**
	 * Go to "Vegetation Zones - High Priority" data grid
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoVZHighPriority() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Vegetation - EQ"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("Vegetation Zones - High Priority"), true);
		waitForDataGridLoadingIconDisappear();
		
		// If there is an error of loading data grid, abort the test
		if (pu.checkIfToastErrExists()) {			
			throw new InterruptedException("Error in loading data grid.");
		}	
		
		// Restore to default view just in case there is rubbish filters set up by previous test
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		click(objGridPageTopFunctions.btnRestoreDefaultGrid, false);
		waitForDataGridLoadingIconDisappear();
		
		pageSync();
		Thread.sleep(3000);	
		
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
	}
	
	/**
	 * Go to "Intrusion - High Priority" data grid
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoIntrusionHighPriority() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Vegetation - EQ"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("Intrusion - High Priority"), true);
		waitForDataGridLoadingIconDisappear();
		
		// If there is an error of loading data grid, abort the test
		if (pu.checkIfToastErrExists()) {			
			throw new InterruptedException("Error in loading data grid.");
		}
		
		// Restore to default view just in case there is rubbish filters set up by previous test
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		click(objGridPageTopFunctions.btnRestoreDefaultGrid, false);
		waitForDataGridLoadingIconDisappear();
		
		pageSync();
		Thread.sleep(3000);
		
		
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
	}

	/**
	 * Go to "Bay - High Priority" data grid
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoBayMaintenence() throws InterruptedException, InvalidKeyException, SecurityException{
	
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Vegetation - EQ"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("Bay - High Priority"), true);
		waitForDataGridLoadingIconDisappear();
		
		// If there is an error of loading data grid, abort the test
		if (pu.checkIfToastErrExists()) {			
			throw new InterruptedException("Error in loading data grid.");
		}	
		
		// Restore to default view just in case there is rubbish filters set up by previous test
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		click(objGridPageTopFunctions.btnRestoreDefaultGrid, false);
		waitForDataGridLoadingIconDisappear();
		
		pageSync();
		Thread.sleep(3000);
	
		
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
	}

	/**
	 * Go to "VZ MST Optimisation" page
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void gotoVZMSTOptimisation() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid =========================================
		
		click(findSidebarItemHeading("Applications"), true);
		pageSync();
		
		click(findSidebarItemSubHeading("VZ MST Optimisation"), true);
		waitForDataGridLoadingIconDisappear();
		pageSync();
		Thread.sleep(3000);
		captureScreen();
	}
	
	/**
	 * Sample Code
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void sampleCode() throws InterruptedException, InvalidKeyException, SecurityException{
		
		// ========================= Navigate to the data grid ==========================================
		
		click(findSidebarItemHeading("Vegetation"), true);	
		//click(findSidebarItemHeading("Jim"), true);
		//click(leftNavInsight.vegetation, true);		
		pageSync();
		
		click(findSidebarItemSubHeading("Intrusion - High Priority"), true);
		//click(findSidebarItemSubHeading("Intrusions"), true);
		//click(leftNavInsight.Intrusion, true);
		waitForDataGridLoadingIconDisappear();
		pageSync();
		//Thread.sleep(8000);
		TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objPortalInsightVegetationMain.chkCheckBoxes));
		captureScreen();
		
		//PageUtilities.clickToShowTableHeaderMenu("Cycle No");
		
		//PageUtilities.clickToShowTableHeaderMenu("Bay Int Max Height");
		
		// ===================================== Demo on manipulate the page top filter ====================================
		/*
		GridPageTopFilter clientNGDropDownMenu = new GridPageTopFilter("client_id","Client");
		List<WebElement> menuOptions = clientNGDropDownMenu.getAllMenuItems();
		for (int i=0;i<menuOptions.size();i++) {
			System.out.println(menuOptions.get(i).getText());
		}
		
		GridPageTopFilter programNGDropDownMenu = new GridPageTopFilter("program","VZ Program Type");
		List<WebElement> menuOptions2 = programNGDropDownMenu.getAllMenuItems();
		for (int i=0;i<menuOptions2.size();i++) {
			System.out.println(menuOptions2.get(i).getText());
		}
		
		clientNGDropDownMenu.selectByVisibleText("Energex");
		PageUtilities.removeActiveFilter("Client=Energex");
		
		clientNGDropDownMenu.addAllInList();
		clientNGDropDownMenu.removeAllInList();
		*/
		
		
		// ============================== Set checkbox filter ====================================
		
		//GridPageToolPanel toolPanel = new GridPageToolPanel();
		//toolPanel.checkLevel2Checkbox("Cycle No");
		
		//PageUtilities.clickToShowTableHeaderMenu("Cycle No");
		// Apply Filter
		//GridPageTableHeaderColumnMenu objGridPageTableHeaderColumnMenu = new GridPageTableHeaderColumnMenu();	
		//objGridPageTableHeaderColumnMenu.applyFilterByCheckValues("4,5,6");	
		//PageUtilities.removeActiveFilter("Cycle No=4,5,6");
		//Thread.sleep(3000);
		//toolPanel.uncheckLevel2Checkbox("Cycle No");
		
		
		// ============================== Set value filter ====================================
		
		//objGridPageTableHeaderColumnMenu.applyFilterByValue("Greater than", "780000");
		// Clear Filter
		//PageUtilities.clickToShowTableHeaderMenu("Bay Id");
		//objGridPageTableHeaderColumnMenu.clearFilter();
		
		// ==============================Test ngWebDriver ( it works while not much use for the framework) ============================		
		// Test for ngWebDriver open source tool, looks working well
		
		//List<WebElement> menuOptions = TestcaseUtilities.driver.findElements(ByAngular.repeater("item in menuItems"));
		
		//for (int i=0;i<menuOptions.size();i++) {
			//System.out.println(menuOptions.get(i).getText());
		//}
		
		// ==============================Test on the AG-Grid =========================================================================
		/*
		List<WebElement> rows = TestcaseUtilities.driver.findElements(By.xpath("//div[@class='ag-body-container']//div[@role='row'][@class='ag-row ag-row-no-focus ag-row-even ag-row-no-animation ag-row-level-0']"));
		for (int i=0;i<rows.size();i++) {
			System.out.println(rows.get(i).getText());
			
			System.out.println("-------");
		}
		*/		
		
		// ======================================================= Demo on TOOL PANEL =========================================
		/*
		GridPageToolPanel toolPanel = new GridPageToolPanel();
		toolPanel.checkLevel1Checkbox("Page Filter");
		toolPanel.checkLevel2Checkbox("Cycle No");
		
		
		PageUtilities.clickToShowTableHeaderMenu("Client");
		//GridPageTableHeaderColumnMenu objGridPageTableHeaderColumnMenu = new GridPageTableHeaderColumnMenu();	
		List<WebElement> allCheckBoxes = objGridPageTableHeaderColumnMenu.getAllFilterCheckboxes();
		
		for (int i=0;i<allCheckBoxes.size();i++) {
			System.out.println(allCheckBoxes.get(i).getText());
		}	
		
		toolPanel.uncheckLevel1Checkbox("Page Filter");
		toolPanel.uncheckLevel2Checkbox("Cycle No");
		
		*/
		
		//PageUtilities.clickToShowTableHeaderMenu("Bay Id");
		//objGridPageTableHeaderColumnMenu.autosizeThisColumn();
		
		//PageUtilities.clickToShowTableHeaderMenu("Bay Id");
		//objGridPageTableHeaderColumnMenu.autosizeAllColumn();
		
		//PageUtilities.clickToShowTableHeaderMenu("Bay Id");
		//objGridPageTableHeaderColumnMenu.resetColumns();
		
		//PageUtilities.clickToShowTableHeaderMenu("Bay Id");
		//objGridPageTableHeaderColumnMenu.showToolPanel();		
		
		//========================= favorite view =============================
		
		//GridPageFavoriteDropDown taskGridPageFavoriteDropDown = new GridPageFavoriteDropDown();
		//taskGridPageFavoriteDropDown.selectFavourite("Default");
		//taskGridPageFavoriteDropDown.createNewFavourite("Cycle456");	
		//taskGridPageFavoriteDropDown.updateFavourite("Sample_Favorite", "Sample_Favorite1");
		//taskGridPageFavoriteDropDown.deleteFavourite("Cycle456");
		
		//=========================Data Grid Table =============================
		/*
		GridPageDataTable objGridPageDataTable = new GridPageDataTable("Intrusion - High Priority");
		objGridPageDataTable.buildColumnNamesAndIDsMappingViaAPI();
		//System.out.println(objGridPageDataTable.getCellData(1, "VZ Name"));

		for (int i=0;i<3;i++) {			
			objGridPageDataTable.checkRow(i*2+1);
			Thread.sleep(1000);
		}

		for (int i=0;i<3;i++) {			
			objGridPageDataTable.uncheckRow(i*2+1);
			Thread.sleep(1000);
		}

		//System.out.println(objGridPageDataTable.getCellData(1, "VZ Name"));
		//System.out.println(objGridPageDataTable.getCellData(1, "Bay Site 1 Latitude"));
		
		*/
		getTotalRowNumberInDataGrid();

	}	
	
	/**
	 * verifyTextSearchDisplayed
	 * @throws InterruptedException
	 */
	public void verifyTextSearchDisplayed() throws InterruptedException{
		isElementDisplayed(leftNavInsight.txtSearch);
	}
	
	
	/**
	 * Get the total rows number at the footer
	 * @return
	 */
	public int getTotalRowNumberInDataGrid(){
		
		String labelViewingRowsInfo = objGridPageFooter.labelViewingRowsInfo.getText();		
		String rowsnumberInString = labelViewingRowsInfo.substring(labelViewingRowsInfo.indexOf("of ") + 3, labelViewingRowsInfo.indexOf(" rows"));
		rowsnumberInString = rowsnumberInString.replace(",", "").replace(" ", "");
		return Integer.valueOf(rowsnumberInString);
	}
	
	/**
	 * Compare all the values from page top filter and those values from the column header filter
	 * @param DataGridName - or view name
	 * @param Filter_DisplayName
	 * @param Filter_ngReflectName
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void checkValuesMappingBetweenPageTopAndColumnHeader(String DataGridName, String Filter_DisplayName, String Filter_ngReflectName) throws InterruptedException, InvalidKeyException, SecurityException{
		
		// Retrieve all the values from page top filter
		GridPageTopFilter objGridPageTopFilter = new GridPageTopFilter(Filter_ngReflectName, Filter_DisplayName);		
		String[] valuesOfPageTopFilter = null; //objGridPageTopFilter.getAllMenuItems();	
		
		// Need to bring the column into view before showing the column header menu
		GridPageDataTable objGridPageDataTable = new GridPageDataTable(DataGridName);
		objGridPageDataTable.bringColumnIntoView(Filter_DisplayName);
		
		// Retrieve all the values from column header filter
		pu.clickToShowTableHeaderMenu(Filter_DisplayName);		
		GridPageTableHeaderColumnMenu  objGridPageTableHeaderColumnMenu = new GridPageTableHeaderColumnMenu(Filter_DisplayName);
		String[] valuesOfColumnHeaderFilter = objGridPageTableHeaderColumnMenu.getAllFilterCheckboxesValues();
		
		ArrayList<String> valuesInPageTopFilterOnly = CommonUtilities.findValuesInArray1NotInArray2(valuesOfPageTopFilter, valuesOfColumnHeaderFilter);		
		ArrayList<String> valuesInColumnHeaderFilterOnly = CommonUtilities.findValuesInArray1NotInArray2(valuesOfColumnHeaderFilter, valuesOfPageTopFilter);
		
		// PASS if the page top filter fully matches column header filter
		if (valuesInPageTopFilterOnly.size()==0 && valuesInColumnHeaderFilterOnly.size()==0) {
			String allOptions = "";
			for(int i=0;i<valuesOfPageTopFilter.length;i++) {
				allOptions = allOptions + valuesOfPageTopFilter[i] + ",";
			}
			//TestcaseUtilities.getExtentTest().log(LogStatus.PASS, "Page top filter values and column header filter values match for '" + Filter_DisplayName + "'. They both have options: " + allOptions);
			//TestcaseUtilities.log.info("Page top filter values and column header filter values match for '" + Filter_DisplayName + "'. They both have options: " + allOptions);
			logTestResult(LogStatus.PASS, "Page top filter values and column header filter values match for '" + Filter_DisplayName + "'. They both have options: " + allOptions);
		}
		
		// List all those values in page top filter only
		if (valuesInPageTopFilterOnly.size() > 0){			
			String valuesDifference = "";
			for(int i=0;i<valuesInPageTopFilterOnly.size();i++) {
				valuesDifference = valuesDifference + valuesInPageTopFilterOnly.get(i) + ",";
			}
			
			//TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, Filter_DisplayName + ": the value(s) below is in page top filter only: " + valuesDifference + ".");
			//TestcaseUtilities.log.error(Filter_DisplayName + ": the value(s) below is in page top filter only: " + valuesDifference + ".");
			logTestResult(LogStatus.FAIL, Filter_DisplayName + ": the value(s) below is in page top filter only: " + valuesDifference + ".");
			throw new InterruptedException(Filter_DisplayName + ": the value(s) below is in page top filter only: " + valuesDifference + ".");			
		}
		
		// List all those values in column header filter only
		if (valuesInColumnHeaderFilterOnly.size() > 0){			
			String valuesDifference = "";
			for(int i=0;i<valuesInColumnHeaderFilterOnly.size();i++) {
				valuesDifference = valuesDifference + valuesInColumnHeaderFilterOnly.get(i) + ",";
			}
			
			//TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, Filter_DisplayName + ": the value(s) below is in column header filter only: " + valuesDifference + ".");
			//TestcaseUtilities.log.error(Filter_DisplayName + ": the value(s) below is in column header filter only: " + valuesDifference + ".");
			logTestResult(LogStatus.FAIL, Filter_DisplayName + ": the value(s) below is in column header filter only: " + valuesDifference + ".");
			throw new InterruptedException(Filter_DisplayName + ": the value(s) below is in column header filter only: " + valuesDifference + ".");
		}
	}
	
	/**
	 * Check the total intrusion numbers by comparing VZ grid and Intrusion grid - Single Row
	 * @param rowNumber
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void navFromVZtoIntrusionNVerifyIntrusionTotal(int rowNumber) throws InterruptedException, NumberFormatException, InvalidKeyException, SecurityException {
		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable("Vegetation Zones - High Priority");
		intrusionGridPageDataTable.checkRow(rowNumber - 1);
		captureScreen();
		
		int number_VZCPTotCount = 0;
		int number_VZLLTotCount = 0;
		int number_VZLLATotCount = 0;
		int number_VZLLA4TotCount = 0;
		int number_VZCLTotCount = 0;
		
		
		int totalIntrusionNumber_VZGrid = number_VZCPTotCount + number_VZLLTotCount + number_VZLLATotCount + number_VZLLA4TotCount + number_VZCLTotCount;
		
		Thread.sleep(1000);
		captureScreen();
		
		// Go to Intrusion grid to check the number
		click(objGridPageTopFunctions.btnIntrusions,false);
		waitForDataGridLoadingIconDisappear();
		captureScreen();
		
		int totalIntrusionNumber_IntrusionGrid = getTotalRowNumberInDataGrid();
		
		// Go back to VZ grid
		click(objGridPageTopFunctions.btnVegetationZones_HighPriority,false);
		
		// Check point
		if(totalIntrusionNumber_VZGrid == totalIntrusionNumber_IntrusionGrid) {
			//TestcaseUtilities.getExtentTest().log(LogStatus.PASS, "The intrusion numbers match between VZ grid and Intrusion Grid. They both have " + totalIntrusionNumber_IntrusionGrid + " intrusions.");
			//TestcaseUtilities.log.info("The intrusion numbers match between VZ grid and Intrusion Grid. They both have " + totalIntrusionNumber_IntrusionGrid + " intrusions.");
			logTestResult(LogStatus.PASS, "The intrusion numbers match between VZ grid and Intrusion Grid. They both have " + totalIntrusionNumber_IntrusionGrid + " intrusions.");
		}else {
			//TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, "The intrusion numbers does NOT match between VZ grid and Intrusion Grid. VZ grid has " + totalIntrusionNumber_VZGrid + " intrusions while intrusion grid has " + totalIntrusionNumber_IntrusionGrid + " intrusions.");
			//TestcaseUtilities.log.error("The intrusion numbers does NOT match between VZ grid and Intrusion Grid. VZ grid has " + totalIntrusionNumber_VZGrid + " intrusions while intrusion grid has " + totalIntrusionNumber_IntrusionGrid + " intrusions.");
			logTestResult(LogStatus.FAIL, "The intrusion numbers does NOT match between VZ grid and Intrusion Grid. VZ grid has " + totalIntrusionNumber_VZGrid + " intrusions while intrusion grid has " + totalIntrusionNumber_IntrusionGrid + " intrusions.");
			throw new InterruptedException("The intrusion numbers does NOT match between VZ grid and Intrusion Grid. VZ grid has " + totalIntrusionNumber_VZGrid + " intrusions while intrusion grid has " + totalIntrusionNumber_IntrusionGrid + " intrusions.");
		}		
	}
	
	/**
	 * Check the total intrusion numbers by comparing VZ grid and Intrusion grid - Multiple Rows
	 * @param rowNumber
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	
	
	
	


	

	
	/**
	 * Export the selected rows to file with the format given: CSV or Excel or KML
	 * @param DataGridName
	 * @param rowNumbers
	 * @param exportFormat
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws SecurityException 
	 * @throws InvalidKeyException 
	 */
	public void exportSelectedRows(String DataGridName, Integer[] rowNumbers, String exportFormat) throws InterruptedException, IOException, InvalidKeyException, SecurityException {
		
		// Delete the previous downloaded files if exist
		CommonUtilities.deleteDownloadedFilesIfExist();
		
		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable(DataGridName);		
		for(int i=0;i<rowNumbers.length;i++) {
			intrusionGridPageDataTable.checkRow(rowNumbers[i] - 1);
		}
		captureScreen();

		WebElement dataGridBody = TestcaseUtilities.getDriver().findElement(By.xpath(intrusionGridPageDataTable.xpathSelector_1st_row));
		Actions oAction = new Actions(TestcaseUtilities.getDriver());
		oAction.moveToElement(dataGridBody);
		oAction.contextClick(dataGridBody).build().perform(); 
		
		this.objDataGridPopupMenu.menuExportSelectedRows.click();
		
		String fileDownloaded = "";		
		switch(exportFormat) {
		   case "CSV" :
			   fileDownloaded = System.getProperty("user.home") + "/Downloads/export.csv";
			   this.objDataGridPopupMenu.submenuExportAsCSV.click();	
		      break; // 
		   
		   case "Excel" :
			  fileDownloaded = System.getProperty("user.home") + "/Downloads/export.xls";
			  this.objDataGridPopupMenu.submenuExportAsExcel.click();
		      break; // 
		      
		   case "KML" :
			   fileDownloaded = System.getProperty("user.home") + "/Downloads/export.kml";
			   this.objDataGridPopupMenu.submenuExportAsKML.click();
		      break; // 

		   default : // 
		}
				
		Thread.sleep(1000);
		click(objPopupWindowExportDataWarningLocators.btnDownload, false);
		
		this.waitForDataGridLoadingIconDisappear();
		Thread.sleep(5000);
		captureScreen();
		
		if(Files.exists(Paths.get(fileDownloaded))) { 
			//TestcaseUtilities.getExtentTest().log(LogStatus.PASS, "File '" + fileDownloaded + "' has been downloaded successfully.");
			//TestcaseUtilities.log.info("File '" + fileDownloaded + "' has been downloaded successfully.");
			logTestResult(LogStatus.PASS, "File '" + fileDownloaded + "' has been downloaded successfully.");
		}else {
			//TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, "Failed to download file '" + fileDownloaded + "'.");
			//TestcaseUtilities.log.error("Failed to download file '" + fileDownloaded + "'.");
			logTestResult(LogStatus.FAIL, "Failed to download file '" + fileDownloaded + "'.");
			throw new InterruptedException("Failed to download file '" + fileDownloaded + "'.");
			
		}		
	}	
	
	
	/**
	 * Export all rows to file with the format given: CSV or KML
	 * @param DataGridName
	 * @param exportFormat
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws SecurityException 
	 * @throws InvalidKeyException 
	 */
	public void exportAllRows(String DataGridName, String exportFormat) throws InterruptedException, IOException, InvalidKeyException, SecurityException {
		
		// Delete the previous downloaded files if exist
		CommonUtilities.deleteDownloadedFilesIfExist();
		
		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable(DataGridName);		

		WebElement dataGridBody = TestcaseUtilities.getDriver().findElement(By.xpath(intrusionGridPageDataTable.xpathSelector_1st_row));
		Actions oAction = new Actions(TestcaseUtilities.getDriver());
		oAction.moveToElement(dataGridBody);
		oAction.contextClick(dataGridBody).build().perform(); 
		
		this.objDataGridPopupMenu.menuExportAllRows.click();
		
		String fileDownloaded = "";		
		switch(exportFormat) {
		   case "CSV" :
			   fileDownloaded = System.getProperty("user.home") + "/Downloads/export.csv";
			   this.objDataGridPopupMenu.submenuExportAsCSV.click();	
		      break; // 
		   		      
		   case "KML" :
			   fileDownloaded = System.getProperty("user.home") + "/Downloads/export.kml";
			   this.objDataGridPopupMenu.submenuExportAsKML.click();
		      break; // 

		   default : // 
		}
		
		Thread.sleep(1000);
		captureScreen();
		click(objPopupWindowExportDataWarningLocators.btnDownload, false);

		this.waitForDataGridLoadingIconDisappear();		
		
		Thread.sleep(5000);
		captureScreen();
		
		if(Files.exists(Paths.get(fileDownloaded))) { 
			//TestcaseUtilities.getExtentTest().log(LogStatus.PASS, "File '" + fileDownloaded + "' has been downloaded successfully.");
			//TestcaseUtilities.log.info("File '" + fileDownloaded + "' has been downloaded successfully.");
			logTestResult(LogStatus.PASS, "File '" + fileDownloaded + "' has been downloaded successfully.");
		}else {
			//TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, "Failed to download file '" + fileDownloaded + "'.");
			//TestcaseUtilities.log.error("Failed to download file '" + fileDownloaded + "'.");
			logTestResult(LogStatus.FAIL, "Failed to download file '" + fileDownloaded + "'.");
			throw new InterruptedException("Failed to download file '" + fileDownloaded + "'.");
			
		}		
	}	
	
	/**
	 * Check the pagination function
	 * @throws InterruptedException
	 */
	public void checkPaginationAndInfiniteRoll() throws InterruptedException {
		
		// Have to put the locator here
		String xpath_labelPageInfo ="//div[@class='grid-buttons-wrapper ng-star-inserted']//span[contains(text(),'Page')]";
		
		// Switch to Pagination
		objGridPageTopFunctions.btnPageOrInfiniteScroll.click();
		waitForDataGridLoadingIconDisappear();
		Thread.sleep(1000);
		
		// Check if the pagination label shows
		if (isElementPresent(By.xpath(xpath_labelPageInfo))) {
			//TestcaseUtilities.getExtentTest().log(LogStatus.PASS, "Switched to Pagination view successfully.");
			//TestcaseUtilities.log.info("Switched to Pagination view successfully.");
			logTestResult(LogStatus.PASS, "Switched to Pagination view successfully.");
		}else {
			//TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, "Failed to switch to Pagination view.");
			//TestcaseUtilities.log.error("Failed to switch to Pagination view.");
			logTestResult(LogStatus.FAIL, "Failed to switch to Pagination view.");
			throw new InterruptedException("Failed to switch to Pagination view.");
		}
		
		captureScreen();		
		
		// Switch to Infinite Roll
		objGridPageTopFunctions.btnPageOrInfiniteScroll.click();
		waitForDataGridLoadingIconDisappear();
		Thread.sleep(1000);
		
		// Check if the pagination label disappears
		if (isElementPresent(By.xpath(xpath_labelPageInfo))) {
			//TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, "Failed to switch to Infinite Roll view.");
			//TestcaseUtilities.log.error("Failed to switch to Infinite Roll view.");
			logTestResult(LogStatus.FAIL, "Failed to switch to Infinite Roll view.");
			throw new InterruptedException("Failed to switch to Infinite Roll view.");
		}else {			
			//TestcaseUtilities.getExtentTest().log(LogStatus.PASS, "Switched to Infinite Roll view successfully.");
			//TestcaseUtilities.log.info("Switched to Infinite Roll view successfully.");
			logTestResult(LogStatus.PASS, "Switched to Infinite Roll view successfully.");
		}
		
		captureScreen();
	}
	
	public void hidePageTopFilter(String filterName) throws InterruptedException {
		
		// Have to put the locator here
		String xpath_pageTopFilter = "//mat-chip//span[contains(text(),'" + filterName + "')]";
		
		// Hide page top filters
		objGridPageTopFunctions.btnShowHideActiveFilters.click();
		Thread.sleep(1000);
		
		// Check if the active filters hides
		if (!isElementPresent(By.xpath(xpath_pageTopFilter))) {
			//TestcaseUtilities.getExtentTest().log(LogStatus.PASS, "Hided page top active filters successfully.");
			//TestcaseUtilities.log.info("Hided page top active filters successfully.");
			logTestResult(LogStatus.PASS, "Hided page top active filters successfully.");
		}else {
			//TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, "Failed to hide page top active filters.");
			//TestcaseUtilities.log.error("Failed to hide page top active filters.");
			logTestResult(LogStatus.FAIL, "Failed to hide page top active filters.");
			throw new InterruptedException("Failed to hide page top active filters.");
		}
		
		captureScreen();		
		
		// Show page top filters
		objGridPageTopFunctions.btnShowHideActiveFilters.click();
		Thread.sleep(1000);
		
		// Check if the active filters shows
		if (isElementPresent(By.xpath(xpath_pageTopFilter))) {
			//TestcaseUtilities.getExtentTest().log(LogStatus.PASS, "Showed page top active filters successfully.");
			//TestcaseUtilities.log.info("Showed page top active filters successfully.");
			logTestResult(LogStatus.PASS, "Showed page top active filters successfully.");
		}else {
			//TestcaseUtilities.getExtentTest().log(LogStatus.FAIL, "Failed to show page top active filters.");
			//TestcaseUtilities.log.error("Failed to show page top active filters.");
			logTestResult(LogStatus.FAIL, "Failed to show page top active filters.");
			throw new InterruptedException("Failed to show page top active filters.");
		}
		
		captureScreen();
	}
	
	


	

	
	

	
	/**
	 * 28 June 2019 - Ray Chen - Select all rows and navigate to new data grid
	 * @param DataGridName
	 * @param NavToDataGridName
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws SecurityException 
	 * @throws InvalidKeyException 
	 */
	public void selectAllRowsAndNavigateTo(String DataGridName, String NavToDataGridName) throws InterruptedException, IOException, InvalidKeyException, SecurityException {
				
		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable(DataGridName);		

		WebElement dataGridBody = TestcaseUtilities.getDriver().findElement(By.xpath(intrusionGridPageDataTable.xpathSelector_1st_row));
		Actions oAction = new Actions(TestcaseUtilities.getDriver());
		oAction.moveToElement(dataGridBody);
		oAction.contextClick(dataGridBody).build().perform(); 
		
		this.objDataGridPopupMenu.menuNavigateWithFilteredRows.click();
			
		switch(NavToDataGridName) {
		   case "Span" :
			   this.objDataGridPopupMenu.submenuNavigateWithFilteredRows_Span.click();	
		      break; // 
		   		      
		   case "Span Defects" :
			   this.objDataGridPopupMenu.submenuNavigateWithFilteredRows_SpanDefects.click();	
		      break; // 
		   
		   case "Pole" :
			   this.objDataGridPopupMenu.submenuNavigateWithFilteredRows_Pole.click();	
		      break; // 
		   		      
		   case "Pole Defects" :
			   this.objDataGridPopupMenu.submenuNavigateWithFilteredRows_PoleDefects.click();	
		      break; // 

		   default : // 
		}
		
		waitForDataGridLoadingIconDisappear();
		Thread.sleep(1000);
		captureScreen();
	}
	
}
