package com.roames.test.objects.custom;

import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.objects.GridPageTopFunctions;
import com.roames.test.tasks.api.ViewAPITask;
import com.roames.test.utilities.TestcaseUtilities;

public class GridPageDataTable extends PageBase{

	public Hashtable columnNameAndIdMapping = new Hashtable();
	public String viewName;
	//public String xpathSelector_1st_row = "//div[@class='ag-center-cols-viewport']//div[@role='row'][@row-index='1']//div[@class='ag-cell-wrapper']";
	public String xpathSelector_1st_row = "//div[@class='ag-pinned-left-cols-container']//div[@role='row'][@row-index='1']//input[@type='checkbox']";
	GridPageTopFunctions objGridPageTopFunctions;

	public GridPageDataTable(String viewName) throws InvalidKeyException, SecurityException{
		this.viewName = viewName;
		
		objGridPageTopFunctions = new GridPageTopFunctions();
		PageFactory.initElements(getFactory(), objGridPageTopFunctions);		
		this.buildColumnNamesAndIDsMappingViaAPI();
	}	
		
	/**
	 * By calling View API to build the data grid table column name and col-id mapping
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 * @throws InterruptedException
	 */
	public void buildColumnNamesAndIDsMappingViaAPI() throws InvalidKeyException, SecurityException {	
			ViewAPITask viewAPITask = new ViewAPITask();
			columnNameAndIdMapping = viewAPITask.retrieveDataGridNameAndColIDMapping(this.viewName);
	}
	
	/**
	 * Get the cell data with the rowNumber and ColumnName given
	 * @param rowNumber
	 * @param columnName
	 * @return
	 * @throws InterruptedException
	 * @throws SecurityException 
	 * @throws InvalidKeyException 
	 */
	public String getCellData(int rowNumber, String columnName) throws InterruptedException, InvalidKeyException, SecurityException {
						
		String cellDataValue = "";
		
		this.bringColumnIntoViewNew(columnName);
		
		WebElement dataGridCell;
		String  xpathSelector_dataGridCell = "//div[@role='row'][@row-index='" + (rowNumber - 1) + "']//div[@role='gridcell'][@col-id='" + columnNameAndIdMapping.get(columnName) + "']";
				
		try{
			dataGridCell = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_dataGridCell));
			cellDataValue = dataGridCell.getText();
		}

		catch(NoSuchElementException e1) {	
				return "Error: Can not find the cell. Rownumber: " + rowNumber + " and column name: " + columnName + "." + e1.getMessage();	
		}	
		return cellDataValue;		
	}
	
	/**
	 * To click the check box for the row given
	 * @param rowNumber
	 * @return
	 * @throws InterruptedException
	 */
	public void checkRow(int rowNumber) throws InterruptedException {
		
		WebElement rowCheckbox;
		WebElement rowCheckboxChecked;
		String xpathSelector_rowCheckbox;
		String xpathSelector_rowCheckboxChecked;
		
		//xpathSelector_rowCheckbox = "//div[@class='ag-center-cols-viewport']//div[@role='row'][@row-index='" + rowNumber + "']//div[@class='ag-labeled ag-label-align-right ag-checkbox ag-input-field']";
		
		
		xpathSelector_rowCheckbox = "//div[@class='ag-pinned-left-cols-container']//div[@role='row'][@row-index='" + rowNumber + "']//input[@type='checkbox']";
		
		
		//xpathSelector_rowCheckboxChecked = "//div[@class='ag-pinned-left-cols-viewport']//div[@role='row'][@row-index='" + rowNumber + "']//span[@class='ag-selection-checkbox']/span[1]";
		
		//rowCheckboxChecked = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_rowCheckboxChecked));	
		
		// If it is unchecked, then check it
		//if (rowCheckboxChecked.getAttribute("class").endsWith("hidden")) {
			
			rowCheckbox = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_rowCheckbox));			
			click(rowCheckbox, true);
			
			logTestResult(LogStatus.INFO, "Data Grid Row Number: " + rowNumber + " has been checked.");
		//}		
	}
	
	/**
	 * To uncheck the check box for the row given
	 * @param rowNumber
	 * @throws InterruptedException
	 */
	public void uncheckRow(int rowNumber) throws InterruptedException {
		
		WebElement rowCheckbox;
		WebElement rowCheckboxUnChecked;
		String xpathSelector_rowCheckbox;
		String xpathSelector_rowCheckboxUnChecked;
		
		xpathSelector_rowCheckbox = "//div[@class='ag-pinned-left-cols-viewport']//div[@role='row'][@row-index='" + rowNumber + "']//span[@class='ag-selection-checkbox']";
		xpathSelector_rowCheckboxUnChecked = "//div[@class='ag-pinned-left-cols-viewport']//div[@role='row'][@row-index='" + rowNumber + "']//span[@class='ag-selection-checkbox']/span[2]";
		
		rowCheckboxUnChecked = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_rowCheckboxUnChecked));	
		
		// If it is unchecked, then check it
		if (rowCheckboxUnChecked.getAttribute("class").endsWith("hidden")) {
			
			rowCheckbox = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_rowCheckbox));			
			rowCheckbox.click();
			
			logTestResult(LogStatus.INFO, "Data Grid Row Number: " + rowNumber + " has been unchecked.");
		}		
	}
	
	/**
	 * Bring the column which is not shown on the screen to view
	 * @param columnName
	 * @throws InterruptedException
	 * @throws SecurityException 
	 * @throws InvalidKeyException 
	 */
	public void bringColumnIntoView(String columnName) throws InterruptedException, InvalidKeyException, SecurityException {
		
		WebElement cellRow1ColTheMostRight;
		String xpathSelector_cellRow1ColTheMostRight = "//div[@class='ag-center-cols-container']//div[@role='row'][@row-index='0']//div[@aria-colindex='" + findTheMaxColIndex() + "']";
		
		String xpathSelector_tableHeaderColumn = "//span[@class='ag-header-cell-text'][text()='" + columnName + "']";
		boolean isColumnShown = isElementPresent(By.xpath(xpathSelector_tableHeaderColumn));				

		cellRow1ColTheMostRight = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_cellRow1ColTheMostRight));
				
		if (!isColumnShown) {
			// Put focus to the cell(1,1)
			cellRow1ColTheMostRight.click();
		
			//buildColumnNamesAndIDsMappingViaAPI();
					
			//for(int i=0;i<this.columnNameAndIdMapping.size();i++) {
			do {
				//cellRow1Col1.sendKeys(Keys.ARROW_RIGHT);
				Actions actions = new Actions(TestcaseUtilities.getDriver());
				actions.moveToElement(cellRow1ColTheMostRight);
				actions.click();
				// Send 2 of "ARROW_RIGHT" 
				Thread.sleep(500);
				actions.sendKeys(Keys.ARROW_RIGHT);
				Thread.sleep(500);
				actions.sendKeys(Keys.ARROW_RIGHT);
				Thread.sleep(500);
				actions.sendKeys(Keys.ARROW_RIGHT);
				
				actions.build().perform();
				
				Thread.sleep(1500);				
				
				if(isElementPresent(By.xpath(xpathSelector_tableHeaderColumn))) {isColumnShown = true;}
				
				xpathSelector_cellRow1ColTheMostRight = "//div[@class='ag-center-cols-container']//div[@role='row'][@row-index='0']//div[@aria-colindex='" + findTheMaxCompID() + "']";
				cellRow1ColTheMostRight = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_cellRow1ColTheMostRight));
				
				// For debuging the dynamic DOM
				//System.out.println(cellRow1Col1.getAttribute("col-id"));
			}while(!isColumnShown);
		}
	}
	
	/**
	 * Find the max attribute of "aria-colindex" which indicates the most right column
	 * @return
	 */
	private String findTheMaxColIndex() {
		
		String xpathSelector_row1CellsInView = "//div[@class='ag-header-container']//div[@role='row']/div";
		List<WebElement> row1CellsInView = TestcaseUtilities.getDriver().findElements(By.xpath(xpathSelector_row1CellsInView));
		int maxColindex = 0;		
		
		// Have to minus 2 as a few columns at the end are hidden
		for(int i=1;i<(row1CellsInView.size() - 3);i++) {			
			
			String xpathSelector_colInView = "//div[@class='ag-header-container']//div[@class='ag-header-row ag-header-row-column'][@role='row']/div[" + i + "]//span[@role='columnheader']";
			WebElement colInView  = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_colInView));
			int currColindex = Integer.valueOf(colInView.getAttribute("aria-colindex"));
			if (currColindex > maxColindex) {
				maxColindex = currColindex;
			}
		}
				
		return String.valueOf(maxColindex);
	}
	
	private String findTheMaxCompID() {
		
		String xpathSelector_row1CellsInView = "//div[@class='ag-body-container']//div[@role='row'][@row-index='0']/div";
		List<WebElement> row1CellsInView = TestcaseUtilities.getDriver().findElements(By.xpath(xpathSelector_row1CellsInView));
		int maxCompID = 0;		
		
		for(int i=0;i<row1CellsInView.size();i++) {
			int currCompID = Integer.valueOf(row1CellsInView.get(i).getAttribute("comp-id"));
			if (currCompID > maxCompID) {
				maxCompID = currCompID;
			}
		}
		
		return String.valueOf(maxCompID);
	}
	
	public void pinColumn(String columnName, String direction) {
		GridPageTableHeaderColumnMenu objGridPageTableHeaderColumnMenu = new GridPageTableHeaderColumnMenu(columnName);
		
		switch(direction) {
		   case "RIGHT" :
			  objGridPageTableHeaderColumnMenu.pinColumnRight();
		      break; // 
		   
		   case "LEFT" :
			  objGridPageTableHeaderColumnMenu.pinColumnLeft();
		      break; // 
		      
		   default : // 
		}		
	}
	
	/**
	 * Click column to sort
	 * @param columnName
	 * @throws InterruptedException
	 */
	public void clickToSortColumn(String columnName) throws InterruptedException {
		
		String xpathSelector_columnLabel = "//span[@class='ag-header-cell-text'][text()='" + columnName + "']";		
		
		WebElement columnLabel = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_columnLabel));
				
		columnLabel.click();
		pageSync();		
		waitForDataGridLoadingIconDisappear();		
		captureScreen();
	}
	
	/**
	 * To verify the sorting result on the column
	 * @param colName
	 * @param delimiter
	 * @param ascOrDesc
	 * @return
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public boolean verifyColumnSorting(String colName, String ngReflectName, String delimiter, String ascOrDesc) throws InterruptedException, InvalidKeyException, SecurityException {
		
		Double[] cellsValues = new Double[10];
		
		String cellsValuesString = "";
			
		//buildColumnNamesAndIDsMappingViaAPI();	
		
		for(int i=0;i<cellsValues.length;i++) {
			String cellValue = getCellData(i+1, colName).trim();	
			
			cellsValuesString = cellsValuesString + cellValue + ",";
			
			// If there is any delimiter inside,...
			if(!delimiter.equalsIgnoreCase("")) {
				cellsValues[i] = Double.valueOf(cellValue.substring(cellValue.indexOf(delimiter)+1, cellValue.length()));
			}else {
				cellsValues[i] = Double.valueOf(cellValue);
			}
		}
		
		boolean columnSorted = true;
		
		// Check the sorting		
		for(int i=0;i<cellsValues.length-1;i++) {			
			// If it is sorted by ASC
			if(ascOrDesc.equalsIgnoreCase("ASC") && (cellsValues[i] > cellsValues[i+1])) {
				columnSorted = false;
				break;
			}
			
			// If it is sorted by DESC
			if(ascOrDesc.equalsIgnoreCase("DESC") && (cellsValues[i] < cellsValues[i+1])) {
				columnSorted = false;
				break;
			}
		}
		
		// Reporting
		if(columnSorted) {
			logTestResult(LogStatus.PASS, "The column '" + colName + "' has been sorted by " + ascOrDesc + ". Please refer to the first " + cellsValues.length + " rows values:" + cellsValuesString);
		}else {
			logTestResult(LogStatus.FAIL,  "The column '" + colName + "' is NOT sorted by " + ascOrDesc + ". Please refer to the first " + cellsValues.length + " rows values:" + cellsValuesString);
			throw new InterruptedException("The column '" + colName + "' is NOT sorted by " + ascOrDesc + ". Please refer to the first " + cellsValues.length + " rows values:" + cellsValuesString);
		}

		return columnSorted;
	}
	
	/**
	 * 
	 * @param columnName
	 * @throws InterruptedException
	 * @throws InvalidKeyException
	 * @throws SecurityException
	 */
	public void bringColumnIntoViewNew(String columnName) throws InterruptedException, InvalidKeyException, SecurityException {
		
		WebElement firstCellToFocus;
		
		String xpathSelector_firstCellToFocus = "//div[@class='ag-center-cols-container']//div[@role='row'][@row-index='0']//div[@aria-colindex='" + findTheMaxColIndex() + "']";
		
		String xpathSelector_tableHeaderColumn = "//span[@class='ag-header-cell-text'][text()='" + columnName + "']";
		boolean isColumnShown = isElementPresent(By.xpath(xpathSelector_tableHeaderColumn));	
		
		int totalColNumber = columnNameAndIdMapping.size();
		firstCellToFocus = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_firstCellToFocus));
				
		if (!isColumnShown) {
			// Put focus to the cell
			firstCellToFocus.click();
					
			int currentPos = 2;
			do {
				Actions actions = new Actions(TestcaseUtilities.getDriver());
				
				// Move the focus 3 cells to the right 
				Thread.sleep(500);
				actions.sendKeys(Keys.ARROW_RIGHT);
				Thread.sleep(500);
				actions.sendKeys(Keys.ARROW_RIGHT);
				Thread.sleep(500);
				actions.sendKeys(Keys.ARROW_RIGHT);
				
				actions.build().perform();				
				Thread.sleep(1000);				
				
				if(isElementPresent(By.xpath(xpathSelector_tableHeaderColumn))) {isColumnShown = true;}				
				currentPos = currentPos + 3;

			}while(!isColumnShown && currentPos < totalColNumber);
		}
	}
}
