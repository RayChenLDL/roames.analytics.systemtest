package com.roames.test.objects.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.utilities.TestcaseUtilities;

public class GridPageTableHeaderColumnMenu extends PageBase{

	public String columnHeaderName;

	// 3 Bars Tab
	public WebElement menutab3Bars;	
	private String xpathSelector_menutab3Bars = "//div[@class='ag-tabs-header ag-menu-header']//span[@class='ag-icon ag-icon-menu']";

	public WebElement menuPinColumn;
	private String xpathSelector_menuPinColumn = "//div[@class='ag-tabs-body ag-menu-body']//span[text()='Pin Column']";

	public WebElement menuPinColumnPinLeft;
	private String xpathSelector_menuPinColumnPinLeft = "//div[@class='ag-menu-option']//span[text()='Pin Left']";

	public WebElement menuPinColumnPinRight;
	private String xpathSelector_menuPinColumnPinRight = "//div[@class='ag-menu-option']//span[text()='Pin Right']";

	public WebElement menuPinColumnNoPin;
	private String xpathSelector_menuPinColumnNoPin = "//div[@class='ag-menu-option']//span[text()='No Pin']";

	public WebElement menuAutosizeThisColumn;
	private String xpathSelector_menuAutosizeThisColumn = "//div[@class='ag-menu-option']//span[text()='Autosize This Column']";

	public WebElement menuAutosizeAllColumns;
	private String xpathSelector_menuAutosizeAllColumns = "//div[@class='ag-menu-option']//span[text()='Autosize All Columns']";

	public WebElement menuResetColumns;
	private String xpathSelector_menuResetColumns = "//div[@class='ag-menu-option']//span[text()='Reset Columns']";

	// Filter Tab
	public WebElement menutabFilter;
	private String xpathSelector_menutabFilter = "//div[@class='ag-tabs-header ag-menu-header']//span[@class='ag-icon ag-icon-filter']";

	public WebElement menutabFilterSearchTextbox;
	private String xpathSelector_menutabFilterSearchTextbox = "//div[@class='ag-mini-filter ag-labeled ag-label-align-left ag-text-field ag-input-field']//input[@type='text']";

	public WebElement menutabFilterCheckboxSelectAll;
	private String xpathSelector_menutabFilterCheckboxSelectAll = "//div[@class='ag-filter-header-container']//span[text()='(Select All)']";

	public List<WebElement> allFilterCheckboxes;
	// To exclude "Select All"
	private String xpathSelector_allFilterCheckboxes = "//div[@class='ag-set-filter-list']//div[@class='ag-virtual-list-item ag-filter-virtual-list-item']";
	
	public WebElement selectDropDownArrow;
	private String xpathSelector_selectDropDownArrow = "//div[@class='ag-filter-select ag-labeled ag-label-align-left ag-select']//span[@class='ag-icon ag-icon-small-down']";

	public WebElement txtFrom;
	private String xpathSelector_txtFrom = "//input[@class='ag-input-field-input ag-number-field-input'][@placeholder='From']";
	
	public WebElement txtTo;
	private String xpathSelector_txtTo = "//input[@class='ag-input-field-input ag-number-field-input'][@placeholder='To']";	
	
	public WebElement txtVal;
	private String xpathSelector_txtVal ="//div[@ref='eValueFrom1']//input[@class='ag-input-field-input ag-number-field-input'][@placeholder='Filter...']";

	// Columns Tab
	public WebElement menutabColumns;
	private String xpathSelector_menutabColumns = "//div[@class='ag-tabs-header ag-menu-header']//span[@class='ag-icon ag-icon-columns']";	

	// More objects needs to be defined for Columns Tab


	public GridPageTableHeaderColumnMenu(String columnHeaderName){

		this.columnHeaderName = columnHeaderName;

		// 3 Horizontal bars menu tab
		this.menutab3Bars = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menutab3Bars));		

		// filter menu tab
		this.menutabFilter = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menutabFilter));

		// columns menu tab
		this.menutabColumns = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menutabColumns));
	}	

	public void click3BarsTab() throws InterruptedException {
		click(this.menutab3Bars, true);
	}
	
	public void clickFilterTab() throws InterruptedException {
		this.menutabFilter = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menutabFilter));
		click(this.menutabFilter, true);
	}

	public void clickColumnsTab() {		
		this.menutabColumns.click();
	}

	public void pinColumnLeft() {		

		this.menuPinColumn = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menuPinColumn));
		this.menuPinColumn.click();	
		this.menuPinColumnPinLeft = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menuPinColumnPinLeft));
		this.menuPinColumnPinLeft.click();
	}

	public void pinColumnRight() {		
		this.menuPinColumn = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menuPinColumn));
		this.menuPinColumn.click();	
		this.menuPinColumnPinRight = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menuPinColumnPinRight));
		this.menuPinColumnPinRight.click();
	}

	public void pinColumnNo() {		
		this.menuPinColumn = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menuPinColumn));
		this.menuPinColumn.click();	
		this.menuPinColumnNoPin = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menuPinColumnNoPin));
		this.menuPinColumnNoPin.click();
	}	

	public void autosizeThisColumn() throws InterruptedException {		
		this.menuAutosizeThisColumn = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menuAutosizeThisColumn));
		this.menuAutosizeThisColumn.click();	
		Thread.sleep(1000);
	}	

	public void autosizeAllColumn() throws InterruptedException {		
		this.menuAutosizeAllColumns = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menuAutosizeAllColumns));
		this.menuAutosizeAllColumns.click();	
		Thread.sleep(1000);
	}	

	public void resetColumns() throws InterruptedException {		
		this.menuResetColumns = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_menuResetColumns));
		this.menuResetColumns.click();	
		Thread.sleep(1000);
	}	

	/**
	 * To get all the checkbox options for the current filter
	 * @return
	 * @throws InterruptedException
	 */
	public List<WebElement> getAllFilterCheckboxes() throws InterruptedException{
		this.menutabFilter.click();
		Thread.sleep(500);		
		this.allFilterCheckboxes = TestcaseUtilities.getDriver().findElements(By.xpath(xpathSelector_allFilterCheckboxes));	
		return this.allFilterCheckboxes;
	}

	/**
	 * To get the string array for all the checkbox options
	 * @return
	 * @throws InterruptedException
	 */
	public String[] getAllFilterCheckboxesValues() throws InterruptedException{
		this.menutabFilter.click();
		Thread.sleep(500);		
		TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Show all items in column header filter : '" + this.columnHeaderName + "'.");
		captureScreen();
		this.allFilterCheckboxes = TestcaseUtilities.getDriver().findElements(By.xpath(xpathSelector_allFilterCheckboxes));
		boolean hasBlanks = false;
		String[] allCheckboxesValues;
		ArrayList<String> result = new ArrayList<String>();

		// To see if "(Blanks)" is one option
		for (int i=0;i<allFilterCheckboxes.size();i++) {
			if("(Blanks)".equalsIgnoreCase(allFilterCheckboxes.get(i).getText())) {
				hasBlanks = true;
				break;
			}
		}

		// Exclude "(Blanks)"
		if (hasBlanks) {	

			for (int i=0;i<allFilterCheckboxes.size() - 1;i++) {
				result.add(allFilterCheckboxes.get(i + 1).getText());
			}
		}else {

			for (int i=0;i<allFilterCheckboxes.size();i++) {
				result.add(allFilterCheckboxes.get(i).getText());
			}
		}

		// Only page down if there are more than 2 options
		if (allFilterCheckboxes.size() > 2) {
			// If the options is more than 1 page, need to page down and add more options
			boolean reachedBottom = false;
			String currentLastOption = allFilterCheckboxes.get(allFilterCheckboxes.size()-2).getText();

			// Keep paging down until reach the bottom of the options
			do {					
				Actions actions = new Actions(TestcaseUtilities.getDriver());		
				actions.moveToElement(allFilterCheckboxes.get(allFilterCheckboxes.size()-2));
				actions.click();
				actions.pause(java.time.Duration.ofSeconds(1));
				actions.sendKeys(Keys.PAGE_DOWN);
				actions.build().perform();
				Thread.sleep(2000);
				waitForDataGridLoadingIconDisappear();

				this.allFilterCheckboxes = TestcaseUtilities.getDriver().findElements(By.xpath(xpathSelector_allFilterCheckboxes));
				String newLastOption = allFilterCheckboxes.get(allFilterCheckboxes.size()-2).getText();

				if(currentLastOption.equals(newLastOption)) {
					reachedBottom = true;
				}else {			
					for (int i=0;i<allFilterCheckboxes.size();i++) {

						// If it is new option, then add into the result
						if(!result.contains(allFilterCheckboxes.get(i).getText())) {
							result.add(allFilterCheckboxes.get(i).getText());
						}
						currentLastOption = newLastOption;					
					}
				}

			}while (!reachedBottom);
		}

		// Convert the result from arrayList to String[]
		allCheckboxesValues = new String[result.size()];
		
		logTestResult(LogStatus.INFO, "All the options from column header '" + this.columnHeaderName + "':");
		
		for (int i=0;i<result.size();i++) {			
			allCheckboxesValues[i] = result.get(i);
			logTestResult(LogStatus.INFO, allCheckboxesValues[i]);			
		}

		return allCheckboxesValues;
	}	
	
	
	/**
	 * To get the string array for all the checkbox options
	 * @return
	 * @throws InterruptedException
	 */
	public String[] getAllCheckedValuesNoScrollDown() throws InterruptedException{
		this.menutabFilter.click();
		Thread.sleep(500);		
		TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Show all items in column header filter : '" + this.columnHeaderName + "'.");
		captureScreen();
		this.allFilterCheckboxes = TestcaseUtilities.getDriver().findElements(By.xpath(xpathSelector_allFilterCheckboxes));
		boolean hasBlanks = false;
		String[] allCheckboxesValues;
		ArrayList<String> result = new ArrayList<String>();

		// To see if "(Blanks)" is one option
		for (int i=0;i<allFilterCheckboxes.size();i++) {
			if("(Blanks)".equalsIgnoreCase(allFilterCheckboxes.get(i).getText())) {
				hasBlanks = true;
				break;
			}
		}		
		
		// Retrieve all the checkboxes again from lower level to see if it is checked
		String xpathSelector_allCheckboxes = "//div[@class='ag-set-filter-list']//div[@class='ag-virtual-list-item ag-filter-virtual-list-item']//div[@ref='eWrapper'][@role='presentation']";
		List<WebElement> allCheckboxes = TestcaseUtilities.getDriver().findElements(By.xpath(xpathSelector_allCheckboxes));
		
		// Exclude "(Blanks)"
		if (hasBlanks) {
			for (int i=0;i<allFilterCheckboxes.size() - 1;i++) {
				
				// Add to the result if it is checked
				if(allCheckboxes.get(i+1).getAttribute("class").endsWith("ag-checked"))
				{
					result.add(allFilterCheckboxes.get(i+1).getText());
				}
			}
		}else {
			for (int i=0;i<allFilterCheckboxes.size();i++) {
				
				// Add to the result if it is checked				
				if(allCheckboxes.get(i).getAttribute("class").endsWith("ag-checked"))
				{
					result.add(allFilterCheckboxes.get(i).getText());
				}
			}
		}

		// Convert the result from arrayList to String[]
		allCheckboxesValues = new String[result.size()];
		
		logTestResult(LogStatus.INFO, "All the checked options from column header '" + this.columnHeaderName + "':");
		
		for (int i=0;i<result.size();i++) {			
			allCheckboxesValues[i] = result.get(i);
			logTestResult(LogStatus.INFO, allCheckboxesValues[i]);			
		}

		return allCheckboxesValues;
	}
	
	/**
	 * Select Filter Operation
	 * @param menuOption
	 * @throws InterruptedException
	 */
	private void setFilterOp(String menuOption) throws InterruptedException {
		
		// Click Filter tab
		//click(this.menutabFilter, true);
		
		this.selectDropDownArrow = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_selectDropDownArrow));
		click(this.selectDropDownArrow, true);	
		Thread.sleep(500);
		
		WebElement option = TestcaseUtilities.getDriver().findElement(By.xpath("//span[text()='" + menuOption + "']"));
		click (option, true);
		Thread.sleep(500);
	}
	
	/**
	 * Set up "In range" for column head filter
	 * @param fromVal
	 * @param toVal
	 * @throws InterruptedException
	 */
	public void setFilterInRange(String fromVal, String toVal) throws InterruptedException {		
		setFilterOp("In range");
		
		txtFrom = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_txtFrom));
		type(txtFrom, fromVal, true);
		Thread.sleep(500);
		
		txtTo = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_txtTo));
		type(txtTo, toVal, true);
		Thread.sleep(500);
		
		// Click filter tab to hide the page top filter
		//click(this.menutabFilter, true);
	}
	
	public void setFilter_SingleOp(String op, String val) throws InterruptedException {		
		setFilterOp(op);
		
		txtVal = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_txtVal));
		type(txtVal, val, true);
		Thread.sleep(500);		
		
		// Click filter tab to hide the page top filter
		//click(this.menutabFilter, true);
	}
}
