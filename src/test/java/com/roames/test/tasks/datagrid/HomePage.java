package com.roames.test.tasks.datagrid;
/*
 * Author: Ray.Chen@roames.com.au
 * 
 * 
 * Function: FUGRO Roames Portal PortalHomePage
 */

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.constant.LocatorType;
import com.roames.test.objects.datagrid.LoginPageLocators;
import com.roames.test.objects.PopupWindowExportDataWarningLocators;
import com.roames.test.objects.custom.GridPageDataTable;
import com.roames.test.objects.custom.GridPageTableHeaderColumnMenu;
import com.roames.test.objects.custom.GridPageTopFilter;
import com.roames.test.objects.datagrid.DataGridPopupMenu;
import com.roames.test.objects.datagrid.GridPageFooter;
import com.roames.test.objects.datagrid.GridPageTop;
import com.roames.test.objects.datagrid.HomePageLocators;
import com.roames.test.objects.datagrid.LeftNavLocators;
import com.roames.test.utilities.CommonUtilities;
import com.roames.test.utilities.TestcaseUtilities;

public class HomePage extends PageBase {

	LoginPageLocators objLoginPage;	
	LeftNavLocators objLeftNavLocators;
	DataGridPopupMenu objDataGridPopupMenu;
	PopupWindowExportDataWarningLocators objPopupWindowExportDataWarningLocators;
	HomePageLocators objHomePageLocators;
	GridPageFooter objGridPageFooter;
	GridPageTop objGridPageTop;

	public HomePage(){	

		objLoginPage = new LoginPageLocators();
		objLeftNavLocators = new LeftNavLocators();
		objDataGridPopupMenu = new DataGridPopupMenu();
		objPopupWindowExportDataWarningLocators = new PopupWindowExportDataWarningLocators();
		objHomePageLocators = new HomePageLocators();
		objGridPageFooter = new GridPageFooter();
		objGridPageTop = new GridPageTop();

		PageFactory.initElements(getFactory(), objLoginPage);
		PageFactory.initElements(getFactory(), objLeftNavLocators);
		PageFactory.initElements(getFactory(), objDataGridPopupMenu);
		PageFactory.initElements(getFactory(), objPopupWindowExportDataWarningLocators);
		PageFactory.initElements(getFactory(), objHomePageLocators);
		PageFactory.initElements(getFactory(), objGridPageFooter);
		PageFactory.initElements(getFactory(), objGridPageTop);
	}

	/**
	 * Login with Roames test id
	 * @return
	 * @throws InterruptedException
	 */
	public HomePage loginRoamesDataGridWithSSO() throws InterruptedException{

		try {

			String userEmail = TestcaseUtilities.config.getString("RoamesDataGridUserID_" + TestcaseUtilities.testingEnv);
			String userEmailPrefix = userEmail.substring(0, userEmail.indexOf("@"));
			String userPassword =  TestcaseBase.encrypter.decrypt(TestcaseUtilities.config.getString("RoamesDataGridPWD_" + TestcaseUtilities.testingEnv));

			TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objLoginPage.userEmail));

			type(objLoginPage.userEmail, userEmail, true);
			captureScreen();

			click(objLoginPage.btnLogin, false);

			TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objLoginPage.txtEmailPrefix));

			// Input email prefix if there is not
			String emailActual = objLoginPage.txtEmailPrefix.getAttribute("value");

			if ((emailActual.contains("@") && (emailActual.length() < userEmail.length())) || ((!emailActual.contains("@")) && (emailActual.trim() == ""))){
				if (emailActual.length() < userEmail.length()) {
					type(objLoginPage.txtEmailPrefix,userEmailPrefix, true);
					captureScreen();
				}			
			}

			click(objLoginPage.btnEmailNext, true);

			TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objLoginPage.password));
			type(objLoginPage.password, userPassword, false);
			captureScreen();
			click(objLoginPage.btnPasswordNext, true);

			pageSync();

			// To quite the test if Google Verify page appears
			if(isElementPresent(By.xpath("//input[@id='phoneNumberId']"), 3)){	
				Thread.sleep(3000);
				captureScreen();
				throw new SkipException("Skipping the test as Google Verify Page appears.");						
			}

			TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(getLeftNav().btnSignOut));
			Thread.sleep(3000);

			assertTrue(getLeftNav().btnSignOut.isDisplayed());
			captureScreen();
		}catch(Exception e) {
			e.printStackTrace();
		}

		return this;
	}	

	/**
	 * Login with gmail test id
	 * @return
	 * @throws InterruptedException
	 */
	public HomePage loginRoamesPortal() throws InterruptedException{

		try {
			String userEmail = TestcaseUtilities.config.getString("RoamesDataGridUserID_" + TestcaseUtilities.testingEnv);
			String userPassword = TestcaseBase.encrypter.decrypt(TestcaseUtilities.config.getString("RoamesDataGridPWD_" + TestcaseUtilities.testingEnv));

			TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(objLoginPage.userEmail));

			type(objLoginPage.userEmail, userEmail, true);
			type(objLoginPage.password, userPassword, false);
			captureScreen();

			click(objLoginPage.btnLogin, false);		

			TestcaseUtilities.getWait().until(ExpectedConditions.visibilityOf(getLeftNav().btnSignOut));
			Thread.sleep(3000);

			assertTrue(getLeftNav().btnSignOut.isDisplayed());
			captureScreen();
		}catch(Exception e) {
			e.printStackTrace();
		}

		return this;
	}	

	public void gotoGrid(String menuName, String subMenuName) throws InterruptedException, SecurityException{

		click(findSidebarItemHeading(menuName), true);
		pageSync();

		click(findSidebarItemHeading(subMenuName), true);
		waitForDataGridLoadingIconDisappear();	

		pageSync();
		Thread.sleep(2000);	

		captureScreen();
	}

	public void hideLeftNavigation() throws InterruptedException, SecurityException{
		click(objLeftNavLocators.btnHideMenu, true);
		Thread.sleep(500);	
	}

	public void hideRighColumnstNavigation() throws InterruptedException, SecurityException{
		click(objHomePageLocators.btnColumns, true);
		Thread.sleep(500);	
	}

	public void testPageTopFilter(String filterName) throws InterruptedException, SecurityException{

		GridPageTopFilter clientGridPageTopFilter = new GridPageTopFilter("", filterName);

		clientGridPageTopFilter.getAllMenuItems();

		//clientGridPageTopFilter.selectByVisibleText("Georgia Power");
	}

	public void exportSelectedRows(String DataGridName, Integer[] rowNumbers, String exportFormat, String fileName) throws InterruptedException, IOException, InvalidKeyException, SecurityException {

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

		click(objDataGridPopupMenu.menuExportSelectedRows, true);

		String fileDownloaded = System.getProperty("user.home") + "/Downloads/" + fileName;

		switch(exportFormat) {
		case "CSV" :			
			click(objDataGridPopupMenu.submenuExportAsCSV, true);
			break; // 

		case "Excel" :
			click(objDataGridPopupMenu.submenuExportAsExcel, true);
			break; // 

		case "KML" :
			click(objDataGridPopupMenu.submenuExportAsKML, true);
			break; // 

		default : // 
		}
		
		this.waitForDataGridLoadingIconDisappear("Loading...");

		// Input file name
		type(objPopupWindowExportDataWarningLocators.txtFileName, fileName, true);
		click(objPopupWindowExportDataWarningLocators.btnDownload, true);

		this.waitForDataGridLoadingIconDisappear();
		Thread.sleep(5000);
		captureScreen();

		if(Files.exists(Paths.get(fileDownloaded))) { 
			logTestResult(LogStatus.PASS, "File '" + fileDownloaded + "' has been downloaded successfully.");
		}else {
			logTestResult(LogStatus.FAIL, "Failed to download file '" + fileDownloaded + "'.");
			throw new InterruptedException("Failed to download file '" + fileDownloaded + "'.");

		}		
	}	

	public void exportAllRows(String DataGridName, String exportFormat, String fileName) throws InterruptedException, IOException, InvalidKeyException, SecurityException {

		// Delete the previous downloaded files if exist
		CommonUtilities.deleteDownloadedFilesIfExist();

		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable(DataGridName);	

		WebElement dataGridBody = TestcaseUtilities.getDriver().findElement(By.xpath(intrusionGridPageDataTable.xpathSelector_1st_row));
		Actions oAction = new Actions(TestcaseUtilities.getDriver());
		oAction.moveToElement(dataGridBody);
		oAction.contextClick(dataGridBody).build().perform(); 

		click(objDataGridPopupMenu.menuExportAllRows, true);

		String fileDownloaded = System.getProperty("user.home") + "/Downloads/" + fileName;

		switch(exportFormat) {
		case "CSV" :			
			click(objDataGridPopupMenu.submenuExportAsCSV, true);
			break; // 

		case "Excel" :
			//this.objDataGridPopupMenu.submenuExportAsExcel.click();
			break; // 

		case "KML" :
			click(objDataGridPopupMenu.submenuExportAsKML, true);
			//this.objDataGridPopupMenu.submenuExportAsKML.click();
			break; // 

		default : // 
		}

		// Click button "YES" to confirm
		click(objPopupWindowExportDataWarningLocators.btnOK, true);
		Thread.sleep(3000);
		
		this.waitForDataGridLoadingIconDisappear("Loading...");

		// Input file name
		type(objPopupWindowExportDataWarningLocators.txtFileName, fileName, true);
		click(objPopupWindowExportDataWarningLocators.btnDownload, true);

		Thread.sleep(3000);
		captureScreen();

		if(Files.exists(Paths.get(fileDownloaded))) { 
			logTestResult(LogStatus.PASS, "File '" + fileDownloaded + "' has been downloaded successfully.");
		}else {
			logTestResult(LogStatus.FAIL, "Failed to download file '" + fileDownloaded + "'.");
			throw new InterruptedException("Failed to download file '" + fileDownloaded + "'.");

		}		
	}	

	public void checkValuesMappingBetweenPageTopAndColumnHeader(String DataGridName, String Filter_DisplayName, String Filter_ngReflectName) throws InterruptedException, SecurityException, InvalidKeyException{

		// Retrieve all the values from page top filter
		GridPageTopFilter objGridPageTopFilter = new GridPageTopFilter(Filter_ngReflectName, Filter_DisplayName);		
		String[] valuesOfPageTopFilter = objGridPageTopFilter.getAllMenuItems();	

		// Need to bring the column into view before showing the column header menu
		GridPageDataTable objGridPageDataTable = new GridPageDataTable(DataGridName);
		objGridPageDataTable.bringColumnIntoView(Filter_DisplayName);

		// Retrieve all the values from column header filter
		clickToShowTableHeaderMenu(Filter_DisplayName);		
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

			logTestResult(LogStatus.PASS, "Page top filter values and column header filter values match for '" + Filter_DisplayName + "'. They both have options: " + allOptions);
		}

		// List all those values in page top filter only
		if (valuesInPageTopFilterOnly.size() > 0){			
			String valuesDifference = "";
			for(int i=0;i<valuesInPageTopFilterOnly.size();i++) {
				valuesDifference = valuesDifference + valuesInPageTopFilterOnly.get(i) + ",";
			}

			logTestResult(LogStatus.FAIL, Filter_DisplayName + ": the value(s) below is in page top filter only: " + valuesDifference + ".");
			throw new InterruptedException(Filter_DisplayName + ": the value(s) below is in page top filter only: " + valuesDifference + ".");			
		}

		// List all those values in column header filter only
		if (valuesInColumnHeaderFilterOnly.size() > 0){			
			String valuesDifference = "";
			for(int i=0;i<valuesInColumnHeaderFilterOnly.size();i++) {
				valuesDifference = valuesDifference + valuesInColumnHeaderFilterOnly.get(i) + ",";
			}

			logTestResult(LogStatus.FAIL, Filter_DisplayName + ": the value(s) below is in column header filter only: " + valuesDifference + ".");
			throw new InterruptedException(Filter_DisplayName + ": the value(s) below is in column header filter only: " + valuesDifference + ".");
		}
	}


	/**
	 * To verify these 2 filters respect each other across page top filter & column header filter
	 * @param DataGridName
	 * @param Filter1_DisplayName
	 * @param Filter2_DisplayName
	 * @param values
	 * @throws InterruptedException
	 * @throws SecurityException
	 * @throws InvalidKeyException
	 */
	public void filtersRespectBetweenPageTopAndColumnHeader(String DataGridName, String Filter1_DisplayName, String Filter1_ngReflectName, String Filter2_DisplayName, String Filter2_ngReflectName, String[] values) throws InterruptedException, SecurityException, InvalidKeyException{

		// Retrieve all the values from page top filter
		GridPageTopFilter objGridPageTopFilter1 = new GridPageTopFilter(Filter1_ngReflectName, Filter1_DisplayName);
		GridPageTopFilter objGridPageTopFilter2 = new GridPageTopFilter(Filter2_ngReflectName, Filter2_DisplayName);		

		objGridPageTopFilter1.selectMultipleOptionsByVisibleText(values);
		
		// Proper sleep to get Filter2 refreshed
		Thread.sleep(2000);

		String [] options_filter2_pagetop = objGridPageTopFilter2.getAllMenuItems();

		ArrayList<String> valuesInMasterOnly = CommonUtilities.findValuesInArray1NotInArray2(values, options_filter2_pagetop);		
		ArrayList<String> valuesInFilter2Only_pageTop = CommonUtilities.findValuesInArray1NotInArray2(options_filter2_pagetop, values);

		// PASS if the page top filter fully matches column header filter
		if (valuesInMasterOnly.size()==0 && valuesInFilter2Only_pageTop.size()==0) {
			String allOptions = "";
			for(int i=0;i<values.length;i++) {
				allOptions = allOptions + values[i] + ",";
			}

			logTestResult(LogStatus.PASS, "Page top filter '" + Filter2_DisplayName + "' has been reflected with page top filter '" + Filter1_DisplayName + "'. They both have options: " + allOptions);
		}else {
			logTestResult(LogStatus.FAIL, "Page top filter '" + Filter2_DisplayName + "' has NOT been reflected with page top filter '" + Filter1_DisplayName + "'.");
			throw new InterruptedException("Page top filter '" + Filter2_DisplayName + "' has NOT been reflected with page top filter '" + Filter1_DisplayName + "'.");
		}

		// To check the column head filter1
		clickToShowTableHeaderMenu(Filter1_DisplayName);		
		GridPageTableHeaderColumnMenu  objGridPageTableHeaderColumnMenu1 = new GridPageTableHeaderColumnMenu(Filter1_DisplayName);
		String[] options_filter1_columnHeader = objGridPageTableHeaderColumnMenu1.getAllCheckedValuesNoScrollDown();

		valuesInMasterOnly = CommonUtilities.findValuesInArray1NotInArray2(values, options_filter1_columnHeader);		
		ArrayList<String> valuesInFilter1Only_columnHeader1  = CommonUtilities.findValuesInArray1NotInArray2(options_filter1_columnHeader, values);

		// PASS if the page top filter fully matches column header filter
		if (valuesInMasterOnly.size()==0 && valuesInFilter1Only_columnHeader1.size()==0) {
			String allOptions = "";
			for(int i=0;i<values.length;i++) {
				allOptions = allOptions + values[i] + ",";
			}

			logTestResult(LogStatus.PASS, "Column header filter '" + Filter1_DisplayName + "' has been reflected with page top filter '" + Filter1_DisplayName + "'. They both have options: " + allOptions);
		}else {
			logTestResult(LogStatus.FAIL, "Column header filter '" + Filter1_DisplayName + "' has NOT been reflected with page top filter '" + Filter1_DisplayName + "'.");
			throw new InterruptedException("Column header filter '" + Filter1_DisplayName + "' has NOT been reflected with page top filter '" + Filter1_DisplayName + "'.");
		}

		// Click filter tab to hide the column menu
		objGridPageTableHeaderColumnMenu1.clickFilterTab();

		Thread.sleep(3000);

		// To check the column head filter2
		clickToShowTableHeaderMenu(Filter2_DisplayName);		
		GridPageTableHeaderColumnMenu  objGridPageTableHeaderColumnMenu2 = new GridPageTableHeaderColumnMenu(Filter2_DisplayName);

		// Click 3Bars tab
		objGridPageTableHeaderColumnMenu2.click3BarsTab();

		String[] options_filter2_columnHeader = objGridPageTableHeaderColumnMenu2.getAllFilterCheckboxesValues();

		valuesInMasterOnly = CommonUtilities.findValuesInArray1NotInArray2(values, options_filter2_columnHeader);		
		ArrayList<String> valuesInFilter1Only_columnHeader2  = CommonUtilities.findValuesInArray1NotInArray2(options_filter2_columnHeader, values);

		// PASS if the page top filter fully matches column header filter
		if (valuesInMasterOnly.size()==0 && valuesInFilter1Only_columnHeader2.size()==0) {
			String allOptions = "";
			for(int i=0;i<values.length;i++) {
				allOptions = allOptions + values[i] + ",";
			}

			logTestResult(LogStatus.PASS, "Column header filter '" + Filter2_DisplayName + "' has been reflected with page top filter '" + Filter1_DisplayName + "'. They both have options: " + allOptions);
		}else {
			logTestResult(LogStatus.FAIL, "Column header filter '" + Filter2_DisplayName + "' has NOT been reflected with page top filter '" + Filter1_DisplayName + "'.");
			throw new InterruptedException("Column header filter '" + Filter2_DisplayName + "' has NOT been reflected with page top filter '" + Filter1_DisplayName + "'.");
		}

		objGridPageTopFilter1.removeActiveFilter();
	}


	/**
	 * 
	 * @param Filter_DisplayName
	 * @param Filter_ngReflectName
	 * @param hasBLANKValue
	 * @throws InterruptedException
	 */
	public void checkPageTopFilter(String Filter_DisplayName, String Filter_ngReflectName, boolean hasBLANKValue) throws InterruptedException{

		int rowsTotalNumnberB4Filter = getTotalRowNumberInDataGrid();
		int rowsTotalNumnberAfterFilter = 0;

		GridPageTopFilter clientNGDropDownMenu = new GridPageTopFilter(Filter_ngReflectName, Filter_DisplayName);

		String[] menuOptionsString = clientNGDropDownMenu.getAllMenuItems();	

		for (int i=0;i<menuOptionsString.length;i++) {
			System.out.println(menuOptionsString[i]);
			String optionValue = menuOptionsString[i];
			clientNGDropDownMenu.selectSingleOptionByVisibleText(optionValue);

			waitForDataGridLoadingIconDisappear();
			Thread.sleep(2000);

			rowsTotalNumnberAfterFilter = rowsTotalNumnberAfterFilter + getTotalRowNumberInDataGrid();			
			clientNGDropDownMenu.removeActiveFilter();			
		}

		if (rowsTotalNumnberB4Filter == rowsTotalNumnberAfterFilter) {logTestResult(LogStatus.PASS, "Page top filter : " + Filter_DisplayName + " test passed. Displayed total:" + rowsTotalNumnberB4Filter + " rows both for before & after filter applied.");
		}
		else {logTestResult(LogStatus.FAIL, "Page top filter : " + Filter_DisplayName + " test Failed. Displayed total:" + rowsTotalNumnberB4Filter + " rows before / " + rowsTotalNumnberAfterFilter+ " rows after filter applied.");
		throw new InterruptedException("Page top filter : " + Filter_DisplayName + " test Failed. Displayed total:" + rowsTotalNumnberB4Filter + " rows before / " + rowsTotalNumnberAfterFilter+ " rows after filter applied.");
		}
	}

	/**
	 * Get the total row number
	 * @return
	 * @throws InterruptedException 
	 */
	public int getTotalRowNumberInDataGrid() throws InterruptedException{


		showPagination1();

		String labelViewingRowsInfo = objGridPageFooter.labelPageInfo.getText();		
		String rowsnumberInString = labelViewingRowsInfo.substring(labelViewingRowsInfo.indexOf("of ") + 3, labelViewingRowsInfo.length());
		rowsnumberInString = rowsnumberInString.replace(",", "").replace(" ", "");
		return Integer.valueOf(rowsnumberInString);
	}

	public void showPagination() throws InterruptedException{

		click(objGridPageFooter.btnPagination, true);
		Thread.sleep(1000);
		waitForDataGridLoadingIconDisappear();
	}

	public void showPagination1() throws InterruptedException{
		String xpathDataGridLoadingIcon = this.getBy(objGridPageFooter, "labelPageInfo", LocatorType.XPATH);		
		WebElement dataGridLoadingIcon;

		try {
			TestcaseBase.getDriver().manage().timeouts().implicitlyWait(1000,TimeUnit.MILLISECONDS);
			dataGridLoadingIcon = TestcaseUtilities.getDriver().findElement(By.xpath(xpathDataGridLoadingIcon));
		} 
		catch(NoSuchElementException e) {
			click(objGridPageFooter.btnPagination, true);
			Thread.sleep(1000);
			waitForDataGridLoadingIconDisappear();
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			TestcaseBase.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(TestcaseUtilities.config.getString("implicit.wait")), TimeUnit.SECONDS);
		}
	}

	/**
	 * 
	 * @param rowNumber
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void navFromVZtoBAYNVerifyBAYTotal(String gridName, int rowNumber) throws InterruptedException, NumberFormatException, InvalidKeyException, SecurityException {
		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable(gridName);
		intrusionGridPageDataTable.checkRow(rowNumber - 1);
		captureScreen();

		int number_VZCaptBayCount = 0;

		// Numbers in VZ data grid
		//intrusionGridPageDataTable.buildColumnNamesAndIDsMappingViaAPI();
		String bayNumber = intrusionGridPageDataTable.getCellData(rowNumber, "MA Capt Bay Ct").trim();		
		if(!bayNumber.equalsIgnoreCase("")) {
			number_VZCaptBayCount = Integer.valueOf(bayNumber);
		}

		Thread.sleep(1000);
		captureScreen();

		// Go to BAY grid to check the number
		click(objGridPageTop.btn_Navigation_To_Bays,true);
		waitForDataGridLoadingIconDisappear();
		captureScreen();

		int totalBAYNumber_BAYGrid = getTotalRowNumberInDataGrid();


		// Check point
		if(number_VZCaptBayCount == totalBAYNumber_BAYGrid) {
			logTestResult(LogStatus.PASS, "The bay numbers match between VZ grid and BAY Grid. They both have " + totalBAYNumber_BAYGrid + " bays.");
		}else {
			logTestResult(LogStatus.FAIL, "The bay numbers does NOT match between VZ grid and BAY Grid. VZ grid has " + number_VZCaptBayCount + " bays while BAY grid has " + totalBAYNumber_BAYGrid + " bays.");
			throw new InterruptedException("The bay numbers does NOT match between VZ grid and BAY Grid. VZ grid has " + number_VZCaptBayCount + " bays while BAY grid has " + totalBAYNumber_BAYGrid + " bays.");
		}	
	}
	
	/**
	 * To check "In range" for the column header filter
	 * @param columnName
	 * @param fromVal
	 * @param toVal
	 * @throws InterruptedException
	 * @throws SecurityException 
	 * @throws InvalidKeyException 
	 */
	public void checkCloumnHeaderFilter(String dataGridName, String columnName, String fromVal, String toVal) throws InterruptedException, InvalidKeyException, SecurityException{

		// Need to bring the column into view before showing the column header menu
		GridPageDataTable objGridPageDataTable = new GridPageDataTable(dataGridName);
		objGridPageDataTable.bringColumnIntoViewNew(columnName);
		
		clickToShowTableHeaderMenu(columnName);

		// Test "In Range"
		GridPageTableHeaderColumnMenu  objGridPageTableHeaderColumnMenu1 = new GridPageTableHeaderColumnMenu(columnName);
		click(objGridPageTableHeaderColumnMenu1.menutabFilter, true);
		objGridPageTableHeaderColumnMenu1.setFilterInRange(fromVal, toVal);
		Thread.sleep(500);
		this.waitForDataGridLoadingIconDisappear();
		
		String cellVal = objGridPageDataTable.getCellData(1, columnName);
		
		if (Double.valueOf(cellVal) > Integer.valueOf(fromVal) && Double.valueOf(cellVal) < Integer.valueOf(toVal) ) {
			logTestResult(LogStatus.PASS, "Row 1 value of column '" + columnName + "' is: " + cellVal + " which is in the range of (" + fromVal + ", " +  toVal + ").");
		}
		else {
			logTestResult(LogStatus.FAIL, "Row 1 value of column '" + columnName + "' is: " + cellVal + " which is NOT in the range of (" + fromVal + ", " +  toVal + ").");
			throw new InterruptedException("Row 1 value of column '" + columnName + "' is: " + cellVal + " which is NOT in the range of (" + fromVal + ", " +  toVal + ").");
		}
				
		// Test "Equals"
		objGridPageTableHeaderColumnMenu1.setFilter_SingleOp("Equals", fromVal);
		Thread.sleep(500);
		this.waitForDataGridLoadingIconDisappear();
		
		cellVal = objGridPageDataTable.getCellData(1, columnName);
		
		if (Double.valueOf(cellVal).equals(Double.valueOf(fromVal)) ) {
			logTestResult(LogStatus.PASS, "Row 1 value of column '" + columnName + "' is: " + cellVal + " which is equal to (" + fromVal + ").");
		}
		else {
			logTestResult(LogStatus.FAIL, "Row 1 value of column '" + columnName + "' is: " + cellVal + "which is NOT equal to (" + fromVal + ").");
			throw new InterruptedException("Row 1 value of column '" + columnName + "' is: " + cellVal + " which is NOT equal to (" + fromVal + ").");
		}

		// Test "Less Than"
		objGridPageTableHeaderColumnMenu1.setFilter_SingleOp("Less than", fromVal);
		Thread.sleep(500);
		this.waitForDataGridLoadingIconDisappear();

		cellVal = objGridPageDataTable.getCellData(1, columnName);

		if (Double.valueOf(cellVal) < Double.valueOf(fromVal) ) {
			logTestResult(LogStatus.PASS, "Row 1 value of column '" + columnName + "' is: " + cellVal + " which is Less Than (" + fromVal + ").");
		}
		else {
			logTestResult(LogStatus.FAIL, "Row 1 value of column '" + columnName + "' is: " + cellVal + "which is NOT Less Than (" + fromVal + ").");
			throw new InterruptedException("Row 1 value of column '" + columnName + "' is: " + cellVal + " which is NOT Less Than (" + fromVal + ").");
		}

		// Test "Greater Than"
		objGridPageTableHeaderColumnMenu1.setFilter_SingleOp("Greater than", fromVal);
		Thread.sleep(500);
		this.waitForDataGridLoadingIconDisappear();

		cellVal = objGridPageDataTable.getCellData(1, columnName);

		if (Double.valueOf(cellVal) > Double.valueOf(fromVal) ) {
			logTestResult(LogStatus.PASS, "Row 1 value of column '" + columnName + "' is: " + cellVal + " which is Greater Than (" + fromVal + ").");
		}
		else {
			logTestResult(LogStatus.FAIL, "Row 1 value of column '" + columnName + "' is: " + cellVal + "which is NOT Greater Than (" + fromVal + ").");
			throw new InterruptedException("Row 1 value of column '" + columnName + "' is: " + cellVal + " which is NOT Greater Than (" + fromVal + ").");
		}
	}	

	public void gotoLeftNavTab(String tabName) throws InterruptedException, SecurityException{

		switch(tabName) {
		case "Asset Model" :
			click(objLeftNavLocators.btnAssetModel, true);
			break; // 

		case "Vegetation" :
			click(objLeftNavLocators.btnVegetation, true);
			break; // 

		case "Others" :
			click(objLeftNavLocators.btnOtherGrids, true);
			break; 

		default :
		}

		waitForDataGridLoadingIconDisappear();	

		pageSync();
		Thread.sleep(1000);	

		captureScreen();
	}
	
	/**
	 * 
	 * @param gridName
	 * @param rowNumber
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void navFromAMtoPoleNVerifyPoleTotal(String gridName, int rowNumber) throws InterruptedException, NumberFormatException, InvalidKeyException, SecurityException {
		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable(gridName);
		intrusionGridPageDataTable.checkRow(rowNumber - 1);
		captureScreen();

		int number_AMCaptPoleCount = 0;

		// Numbers in AssetModel data grid
		String poleNumber = intrusionGridPageDataTable.getCellData(rowNumber, "MA Capt Pole Ct").trim();		
		if(!poleNumber.equalsIgnoreCase("")) {
			number_AMCaptPoleCount = Integer.valueOf(poleNumber);
		}

		Thread.sleep(1000);
		captureScreen();

		// Go to Pole grid to check the number
		click(objGridPageTop.btn_Navigation_To_Poles,true);
		waitForDataGridLoadingIconDisappear();
		captureScreen();

		int totalPoleNumber_PoleGrid = getTotalRowNumberInDataGrid();


		// Check point
		if(number_AMCaptPoleCount == totalPoleNumber_PoleGrid) {
			logTestResult(LogStatus.PASS, "The pole numbers match between Asset Model grid and Pole Grid. They both have " + totalPoleNumber_PoleGrid + " poles.");
		}else {
			logTestResult(LogStatus.FAIL, "The pole numbers does NOT match between Asset Model grid and Pole Grid. Asset Model grid has " + number_AMCaptPoleCount + " poles while POLE grid has " + totalPoleNumber_PoleGrid + " poles.");
			throw new InterruptedException("The pole numbers does NOT match between Asset Model grid and Pole Grid. Asset Model grid has " + number_AMCaptPoleCount + " poles while POLE grid has " + totalPoleNumber_PoleGrid + " poles.");
		}	
	}
	
	/**
	 * Span total should be no less than "MA Capt Bay Ct"
	 * @param gridName
	 * @param rowNumber
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void navFromAMtoSpanNVerifySpanTotal(String gridName, int rowNumber) throws InterruptedException, NumberFormatException, InvalidKeyException, SecurityException {
		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable(gridName);
		intrusionGridPageDataTable.checkRow(rowNumber - 1);
		captureScreen();

		int number_AMCaptBayCount = 0;

		// Numbers in AssetModel data grid
		String bayNumber = intrusionGridPageDataTable.getCellData(rowNumber, "MA Capt Bay Ct").trim();		
		if(!bayNumber.equalsIgnoreCase("")) {
			number_AMCaptBayCount = Integer.valueOf(bayNumber);
		}

		Thread.sleep(1000);
		captureScreen();

		// Go to Span grid to check the number
		click(objGridPageTop.btn_Navigation_To_Spans,true);
		waitForDataGridLoadingIconDisappear();
		captureScreen();

		int totalSpanNumber_SpanGrid = getTotalRowNumberInDataGrid();

		// Check point
		if(number_AMCaptBayCount <= totalSpanNumber_SpanGrid) {
			logTestResult(LogStatus.PASS, "The span numbers is no less than bay number: Spans(" + totalSpanNumber_SpanGrid + ") vs. Bays(" + number_AMCaptBayCount + ")");
		}else {
			logTestResult(LogStatus.FAIL, "The span numbers is LESS THAN bay number: Spans(" + totalSpanNumber_SpanGrid + ") vs. Bays(" + number_AMCaptBayCount + ")");
			throw new InterruptedException("The span numbers is LESS THAN bay number: Spans(" + totalSpanNumber_SpanGrid + ") vs. Bays(" + number_AMCaptBayCount + ")");
		}	
	}
	
	/**
	 * 
	 * @param gridName
	 * @param rowNumber
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void navFromVZtoIntrusionNVerifyIntrusionTotal(String gridName, int rowNumber) throws InterruptedException, NumberFormatException, InvalidKeyException, SecurityException {
		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable(gridName);
		intrusionGridPageDataTable.checkRow(rowNumber - 1);
		captureScreen();

		int number_VZCaptBayCount = 0;

		// Numbers in VZ data grid
		String bayNumber = intrusionGridPageDataTable.getCellData(rowNumber, "MA Capt Bay Ct").trim();	
		if(!bayNumber.equalsIgnoreCase("")) {
			number_VZCaptBayCount = Integer.valueOf(bayNumber);
		}

		Thread.sleep(1000);
		captureScreen();

		// Go to Intrusion grid to check the number
		click(objGridPageTop.btn_Navigation_To_Intrusions,true);
		waitForDataGridLoadingIconDisappear();
		captureScreen();

		int totalIntrusionNumber_IntrusionGrid = getTotalRowNumberInDataGrid();


		// Check point
		if(number_VZCaptBayCount <= totalIntrusionNumber_IntrusionGrid) {
			logTestResult(LogStatus.PASS, "The intrusion numbers is no less than bay number: Intrusions(" + totalIntrusionNumber_IntrusionGrid + ") vs. Bays(" + number_VZCaptBayCount + ")");
		}else {
			logTestResult(LogStatus.FAIL, "The intrusion numbers is LESS THAN bay number: Intrusions(" + totalIntrusionNumber_IntrusionGrid + ") vs. Bays(" + number_VZCaptBayCount + ")");
			throw new InterruptedException("The intrusion numbers is LESS THAN bay number: Intrusions(" + totalIntrusionNumber_IntrusionGrid + ") vs. Bays(" + number_VZCaptBayCount + ")");
		}	
	}
	
	/**
	 * 
	 * @param gridName
	 * @param rowNumber
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void navFromVZBaytoIntrusionNVerifyIntrusionTotal(String gridName, int rowNumber) throws InterruptedException, NumberFormatException, InvalidKeyException, SecurityException {
		GridPageDataTable intrusionGridPageDataTable = new GridPageDataTable(gridName);
		intrusionGridPageDataTable.checkRow(rowNumber - 1);
		captureScreen();

		int number_IntrusionCount = 0;

		// Numbers in VZ data grid
		String intrusionNumber = intrusionGridPageDataTable.getCellData(rowNumber, "Bay Int Ct").trim();	
		if(!intrusionNumber.equalsIgnoreCase("")) {
			number_IntrusionCount = Integer.valueOf(intrusionNumber);
		}

		Thread.sleep(1000);
		captureScreen();

		// Go to Intrusion grid to check the number
		click(objGridPageTop.btn_Navigation_To_Intrusions,true);
		waitForDataGridLoadingIconDisappear();
		captureScreen();

		int totalIntrusionNumber_IntrusionGrid = getTotalRowNumberInDataGrid();


		// Check point
		if(number_IntrusionCount == totalIntrusionNumber_IntrusionGrid) {
			logTestResult(LogStatus.PASS, "The intrusion numbers matches from both grids. Grid Bay(" + number_IntrusionCount + ") vs. Grid Intrusion(" + totalIntrusionNumber_IntrusionGrid + ")");
		}else {
			logTestResult(LogStatus.FAIL, "The intrusion numbers DOES NOT match from both grids. Grid Bay(" + number_IntrusionCount + ") vs. Grid Intrusion(" + totalIntrusionNumber_IntrusionGrid + ")");
			throw new InterruptedException("The intrusion numbers DOES NOT match from both grids. Grid Bay(" + number_IntrusionCount + ") vs. Grid Intrusion(" + totalIntrusionNumber_IntrusionGrid + ")");
		}	
	}
}
