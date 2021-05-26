package com.roames.test.objects.custom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.utilities.TestcaseUtilities;

public class GridPageTopFilter extends PageBase{
	
	public WebElement arrowDropDown;
	public WebElement arrowCollapse;
	public WebElement txtSearch;
	public WebElement singleMenuItem;
	public List<WebElement> allMenuItems;
	
	private String xpathSelector_arrowDropDown;
	private String xpathSelector_arrowCollapse;
	private String xpathSelector_txtSearch;
	private String xpathSelector_singleMenuItem;
	private String xpathSelector_allMenuItems;
	
	private String dropDownMenuName;
	private String ngReflectName;
	
	private String muiID;

	public GridPageTopFilter(String Filter_ngReflectName, String dropDownMenuName){
		
		this.dropDownMenuName = dropDownMenuName;
		this.ngReflectName = Filter_ngReflectName;
		
		this.xpathSelector_arrowDropDown = "//div[@role='combobox']//Label[text()='" + dropDownMenuName + "']/..//button[@title='Open']/span[@class='MuiIconButton-label']";
		this.xpathSelector_arrowCollapse = "//div[@role='combobox']//Label[text()='" + dropDownMenuName + "']/..//button[@title='Close']/span[@class='MuiIconButton-label']";
		this.xpathSelector_txtSearch ="//div[@role='combobox']//Label[text()='" + dropDownMenuName + "']/..//input[@type='text']";
		this.xpathSelector_allMenuItems = "//li[@role='option']";
				
		this.arrowDropDown = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_arrowDropDown));
		
		this.txtSearch = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_txtSearch));
		this.muiID = txtSearch.getAttribute("id").trim();
		logTestResult(LogStatus.INFO, "MUI id of Page top filter of '" + dropDownMenuName + "' : '" + muiID + "'.");
		

		// Set up the xpath based on muiID
		this.xpathSelector_allMenuItems = "//li[@role='option'][contains(@id,'" + muiID + "')]";
		
		//this.txtSearch = TestcaseUtilities.driver.findElement(By.xpath(xpathSelector_txtSearch));
		//this.linkAddAllInList = TestcaseUtilities.driver.findElement(By.xpath(xpathSelector_linkAddAllInList));
		//this.linkClearFilter = TestcaseUtilities.driver.findElement(By.xpath(xpathSelector_linkClearFilter));		
	}
	
	/*
	 * Select specific item from the dropdown menu
	 */
	public void selectSingleOptionByVisibleText(String menuItem) throws InterruptedException{
		
		TestcaseUtilities.getWait().until(ExpectedConditions.elementToBeClickable(arrowDropDown));
		click(this.arrowDropDown, true);
		Thread.sleep(500);

		this.xpathSelector_singleMenuItem = "//ul[@role='listbox']/li[text()='" + menuItem + "']";
		this.singleMenuItem = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_singleMenuItem));	
		click(singleMenuItem, true);
		pageSync();	
		Thread.sleep(1000);
		captureScreen();
		
		arrowCollapse = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_arrowCollapse));
		click(this.arrowCollapse, true);
		
		logTestResult(LogStatus.INFO, "Menu item : " + dropDownMenuName + "/" + menuItem + " has been selected.");			
	}
	
	public void selectMultipleOptionsByVisibleText(String[] menuItems) throws InterruptedException{
		
		TestcaseUtilities.getWait().until(ExpectedConditions.elementToBeClickable(arrowDropDown));
		click(this.arrowDropDown, true);
		Thread.sleep(500);
		String allOptions = "";
		
		for (int i=0;i<menuItems.length;i++) {			
			allOptions = allOptions + menuItems[i] + ",";
			this.xpathSelector_singleMenuItem = "//ul[@role='listbox']/li[text()='" + menuItems[i] + "']";
			this.singleMenuItem = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_singleMenuItem));	
			click(singleMenuItem, true);
			pageSync();	
			Thread.sleep(1000);
		}

		captureScreen();
		
		arrowCollapse = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_arrowCollapse));
		click(this.arrowCollapse, true);
		
		logTestResult(LogStatus.INFO, "Menu item : " + dropDownMenuName + "/" + allOptions + " has been selected.");			
	}
	
		
	/**
	 * 
	 * @param menuItem
	 * @throws InterruptedException
	 */
	public void selectByFindByVisibleText(String menuItem) throws InterruptedException{
		
		TestcaseUtilities.getWait().until(ExpectedConditions.elementToBeClickable(arrowDropDown));
		this.arrowDropDown.click();
		Thread.sleep(500);
		
		this.txtSearch = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_txtSearch));
		type(this.txtSearch, menuItem, true);
		Thread.sleep(500);

		this.xpathSelector_singleMenuItem = "//ss-multiselect-dropdown[@ng-reflect-name='" + ngReflectName + "']//a//span[text()='" + menuItem + "']";
		this.singleMenuItem = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_singleMenuItem));	
		singleMenuItem.click();
		pageSync();	
		Thread.sleep(3000);
		captureScreen();
		
		logTestResult(LogStatus.INFO, "Menu item : " + dropDownMenuName + "/" + menuItem + " has been selected.");
		
		// click again to hide the drop down menu
		TestcaseUtilities.getWait().until(ExpectedConditions.elementToBeClickable(arrowDropDown));
		this.arrowDropDown.click();
		Thread.sleep(500);
	}
	
	/**
	 * Get all the options for the current page top filter
	 * @return
	 * @throws InterruptedException
	 */
	
	public String[] getAllMenuItems() throws InterruptedException{
		
		TestcaseUtilities.getWait().until(ExpectedConditions.elementToBeClickable(arrowDropDown));
		click(this.arrowDropDown, true);
		Thread.sleep(1500);
		TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Show all items in page top filter : '" + dropDownMenuName + "'.");
		captureScreen();
		this.allMenuItems = TestcaseUtilities.getDriver().findElements(By.xpath(xpathSelector_allMenuItems));
				
		String[] menuOptionsString = new String[allMenuItems.size()];		
		
		logTestResult(LogStatus.INFO, "All the options from Page Top Filter '" + this.dropDownMenuName + "':");
		
		for (int i=0;i<allMenuItems.size();i++) {
			menuOptionsString[i] = allMenuItems.get(i).getText();
			logTestResult(LogStatus.INFO, menuOptionsString[i]);	
		}
		
		this.arrowCollapse = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_arrowCollapse));	
		click(this.arrowCollapse, true);
		return menuOptionsString;
	}
	
	/**
	 * Remove the active page top filter
	 * @throws InterruptedException
	 */
	public void removeActiveFilter() throws InterruptedException {
		
		String xpathLittleCross = "//div[@role='button']//span[contains(text(),'" + this.dropDownMenuName + " is " + "')]/../*[name()='svg']";		
		WebElement littleCross = TestcaseUtilities.getDriver().findElement(By.xpath(xpathLittleCross));
		littleCross.click();
		pageSync();	
		Thread.sleep(3000);
		captureScreen();	
		
		logTestResult(LogStatus.INFO, "Active filter: " + this.dropDownMenuName + " has been removed.");
	}
	
}
