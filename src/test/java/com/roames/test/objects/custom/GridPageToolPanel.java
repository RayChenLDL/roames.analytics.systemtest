package com.roames.test.objects.custom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.objects.AssetTabLocators;
import com.roames.test.objects.GridPageTopFunctions;
import com.roames.test.utilities.TestcaseUtilities;

public class GridPageToolPanel extends PageBase{
	
	private GridPageTopFunctions objGridPageTopFunctions;

	public GridPageToolPanel(){
		
		objGridPageTopFunctions = new GridPageTopFunctions();
		PageFactory.initElements(getFactory(), objGridPageTopFunctions);
	}
	
	/**
	 * Check the level 1 check box in the tool panel
	 * @param checkboxName
	 * @throws InterruptedException
	 */
	public void checkLevel1Checkbox(String checkboxName) throws InterruptedException{
		
		WebElement level1Checkbox;
		String xpathSelector_level1Checkbox;
		
		xpathSelector_level1Checkbox = "//span[@class='ag-column-select-column-label'][text()='" + checkboxName + "']/..//input/..";
		
		level1Checkbox = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_level1Checkbox));	
		
		// If it is unchecked, then check it
		if (!level1Checkbox.getAttribute("class").endsWith("checked")) {						
			click(level1Checkbox, true);			
			logTestResult(LogStatus.INFO, "Tool Panel Level 1 checkbox: " + checkboxName + " have been checked.");
			Thread.sleep(500);
		}
	}	
	
	/**
	 * Uncheck level 1 checkbox in the tool panel
	 * @param checkboxName
	 * @throws InterruptedException
	 */
	public void uncheckLevel1Checkbox(String checkboxName) throws InterruptedException{
		
		WebElement level1Checkbox;
		WebElement level1CheckboxUnchecked;
		String xpathSelector_level1Checkbox;
		String xpathSelector_level1CheckboxUnchecked;
		
		// Click to show the tool panel
		click(objGridPageTopFunctions.btnColumns,false);
		Thread.sleep(1000);
		
		xpathSelector_level1Checkbox = "//span[@class='ag-column-select-column-group-label'][text()='" + checkboxName + " ']";
		xpathSelector_level1CheckboxUnchecked = "//span[@class='ag-column-select-column-group-label'][text()='" + checkboxName + " ']/../span/*[2]";
		
		level1CheckboxUnchecked = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_level1CheckboxUnchecked));	
		
		// If it is unchecked, then check it
		if (level1CheckboxUnchecked.getAttribute("class").endsWith("hidden")) {
			
			level1Checkbox = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_level1Checkbox));			
			click(level1Checkbox, true);
			
			//TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Tool Panel Level 1 checkbox: " + checkboxName + " have been unchecked.");
			//TestcaseUtilities.log.info("Tool Panel Level 1 checkbox: " + checkboxName + " have been unchecked.");
			logTestResult(LogStatus.INFO, "Tool Panel Level 1 checkbox: " + checkboxName + " have been unchecked.");
		}		
		
		// Click to hide the tool panel
		click(objGridPageTopFunctions.btnColumns,false);
		Thread.sleep(500);
	}	
	
	/**
	 * Check the level 2 checkbox in the tool panel
	 * @param checkboxName
	 * @throws InterruptedException
	 */
	public void checkLevel2Checkbox(String checkboxName) throws InterruptedException{
		
		WebElement level2Checkbox;
		WebElement level2CheckboxChecked;
		String xpathSelector_level2Checkbox;
		String xpathSelector_level2CheckboxChecked;
		
		// Click to show the tool panel
		click(objGridPageTopFunctions.btnColumns,false);
		Thread.sleep(1000);
		
		xpathSelector_level2CheckboxChecked = "//span[@class='ag-column-select-label'][text()='" + checkboxName + "']/../span/*[1]";
		xpathSelector_level2Checkbox = "//span[@class='ag-column-select-label'][text()='" + checkboxName + "']";
		
		level2CheckboxChecked = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_level2CheckboxChecked));	
		
		// If it is unchecked, then check it
		if (level2CheckboxChecked.getAttribute("class").endsWith("hidden")) {
			
			level2Checkbox = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_level2Checkbox));			
			click(level2Checkbox, true);
			
			//TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Tool Panel Level 2 checkbox: " + checkboxName + " have been checked.");
			//TestcaseUtilities.log.info("Tool Panel Level 2 checkbox: " + checkboxName + " have been checked.");
			logTestResult(LogStatus.INFO, "Tool Panel Level 2 checkbox: " + checkboxName + " have been checked.");
		}		
		
		// Click to hide the tool panel
		click(objGridPageTopFunctions.btnColumns,false);
		Thread.sleep(500);
	}	
	
	/**
	 * Uncheck level 2 checkbox in the tool panel
	 * @param checkboxName
	 * @throws InterruptedException
	 */
	public void uncheckLevel2Checkbox(String checkboxName) throws InterruptedException{
		
		WebElement level2Checkbox;
		WebElement level2CheckboxUnchecked;
		String xpathSelector_level2Checkbox;
		String xpathSelector_level2CheckboxUnchecked;
		
		// Click to show the tool panel
		click(objGridPageTopFunctions.btnColumns,false);
		Thread.sleep(1000);
		
		xpathSelector_level2Checkbox = "//span[@class='ag-column-select-label'][text()='" + checkboxName + "']";
		xpathSelector_level2CheckboxUnchecked = "//span[@class='ag-column-select-label'][text()='" + checkboxName + "']/../span/*[2]";
		
		level2CheckboxUnchecked = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_level2CheckboxUnchecked));	
		
		// If it is unchecked, then check it
		if (level2CheckboxUnchecked.getAttribute("class").endsWith("hidden")) {
			
			level2Checkbox = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_level2Checkbox));			
			click(level2Checkbox, true);
			
			//TestcaseUtilities.getExtentTest().log(LogStatus.INFO, "Tool Panel Level 2 checkbox: " + checkboxName + " have been unchecked.");
			//TestcaseUtilities.log.info("Tool Panel Level 2 checkbox: " + checkboxName + " have been unchecked.");
			logTestResult(LogStatus.INFO, "Tool Panel Level 2 checkbox: " + checkboxName + " have been unchecked.");
		}		
		
		// Click to hide the tool panel
		click(objGridPageTopFunctions.btnColumns,false);
		Thread.sleep(500);
	}
}
