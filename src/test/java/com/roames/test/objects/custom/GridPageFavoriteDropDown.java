package com.roames.test.objects.custom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.relevantcodes.extentreports.LogStatus;
import com.roames.test.base.PageBase;
import com.roames.test.base.TestcaseBase;
import com.roames.test.constant.LocatorType;
import com.roames.test.objects.AssetTabLocators;
import com.roames.test.objects.GridPageManageFavourite;
import com.roames.test.objects.GridPageToastMessage;
import com.roames.test.objects.GridPageTopFunctions;
import com.roames.test.utilities.TestcaseUtilities;

public class GridPageFavoriteDropDown extends PageBase{
	
	WebElement dropdownArrow;
	String xpathSelector_dropdownArrow = "//label[text()='Favorites']/..//button[@class='MuiButtonBase-root MuiIconButton-root MuiAutocomplete-popupIndicator']";
	
	WebElement crossClear;
	String xpathSelector_crossClear = "//div/label[text()='Favorites']/..//button[@title='Clear']";
	
	WebElement favouriteBox;
	String xpathSelector_favouriteBox = "//div/label[text()='Favorites']/..";
	
	GridPageTopFunctions objGridPageTopFunctions;
	GridPageManageFavourite objGridPageManageFavourite;
	GridPageToastMessage objGridPageToastMessage;

	public GridPageFavoriteDropDown(){
		objGridPageTopFunctions = new GridPageTopFunctions();
		objGridPageManageFavourite = new GridPageManageFavourite();
		objGridPageToastMessage = new GridPageToastMessage();
		PageFactory.initElements(getFactory(), objGridPageTopFunctions);
		PageFactory.initElements(getFactory(), objGridPageManageFavourite);
		PageFactory.initElements(getFactory(), objGridPageToastMessage);
	}	

	/**
	 * Select Favorite view with the view name given
	 * @param favouriteName
	 * @throws InterruptedException
	 */
	public void selectFavourite(String favouriteName) throws InterruptedException {
		
		WebElement favourite;
		String xpathSelector_favourite = "//div[@class='MuiPaper-root MuiAutocomplete-paper MuiPaper-elevation1 MuiPaper-rounded']//li[text()='" + favouriteName + "']";

		// Click to show all the favorites
		dropdownArrow = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_dropdownArrow));
		TestcaseUtilities.getWait().until(ExpectedConditions.elementToBeClickable(dropdownArrow));
		dropdownArrow.click();
		
		// select the favourite based on the name given
		favourite = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_favourite));
		click(favourite, true);		
		Thread.sleep(500);

		waitForDataGridLoadingIconDisappear();
		
		logTestResult(LogStatus.PASS, "Favourite view: " + favouriteName + " has been selected.");
	}		
	
	/**
	 * Create a new Favourite
	 * @param favouriteName
	 * @throws InterruptedException
	 */
	public void createNewFavourite(String favouriteName) throws InterruptedException {
		
		click(objGridPageTopFunctions.btnCreateNewFavourite, true);		
		type(objGridPageManageFavourite.txtFavouriteName, favouriteName, true);
		Thread.sleep(2000);

		/*
		 * To fix the issue sometimes it failed to create the favourite with the name given.
		 * Keep input the favouriteName until it is put correctly.
		 */
		boolean isFavouriteNameCorrect = false;
		
		do {
			if(objGridPageManageFavourite.txtFavouriteName.getAttribute("value").equalsIgnoreCase(favouriteName)) {
				isFavouriteNameCorrect = true;				
			}else {
				objGridPageManageFavourite.txtFavouriteName.clear();
				Thread.sleep(1000);
				type(objGridPageManageFavourite.txtFavouriteName, favouriteName, true);
			}
		} while(!isFavouriteNameCorrect);
		
		// FIX END
		
		captureScreen();
		click(objGridPageManageFavourite.btnSubmit, true);
	
		Thread.sleep(1000);
				
		logTestResult(LogStatus.PASS, "New Favourite view: " + favouriteName + " has been created.");
		
		captureScreen();
	}	
	
	/**
	 * Delete a Favourite
	 * @param favouriteName
	 * @throws InterruptedException
	 */
	public void deleteFavourite(String favouriteName) throws InterruptedException {
		
		selectFavourite(favouriteName);
		
		//WebElement element1 = TestcaseBase.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath(getBy(objGridPageTopFunctions, "btnEditFavourite", LocatorType.XPATH))));
		//element1.click();
		//Thread.sleep(6000);

		click(objGridPageTopFunctions.btnEditFavourite, true);
		captureScreen();
		click(objGridPageManageFavourite.btnDelete, true);
	
		Thread.sleep(1000);
				
		logTestResult(LogStatus.PASS, "Favourite: " + favouriteName + " has been deleted.");
	}	
	
	public void updateFavourite(String favouriteName, String newFavouriteName) throws InterruptedException {
		
		selectFavourite(favouriteName);
		
		click(objGridPageTopFunctions.btnEditFavourite, true);		
		captureScreen();
		
		// If new name is different from old name
		if(!(newFavouriteName.equals(favouriteName))) {
			objGridPageManageFavourite.txtFavouriteName.clear();
			Thread.sleep(2000);
			type(objGridPageManageFavourite.txtFavouriteName, newFavouriteName, true);
			Thread.sleep(1000);
		}
		
		captureScreen();
		click(objGridPageManageFavourite.btnSubmit, true);
			
		Thread.sleep(1000);
		captureScreen();
				
		logTestResult(LogStatus.PASS, "Favourite: " + favouriteName + " has been renamed to: " + newFavouriteName + ".");
	}	
	
	/**
	 * Remove current Favourite
	 * @throws InterruptedException
	 */
	public void clearCurrentFavourite() throws InterruptedException {
		
		// Hover to the Favourite box so that to be able to click the "Clear"
		Actions action = new Actions(TestcaseBase.getDriver());
		favouriteBox = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_favouriteBox));
		action.moveToElement(favouriteBox).build().perform();
		
		crossClear = TestcaseUtilities.getDriver().findElement(By.xpath(xpathSelector_crossClear));
		click(crossClear, true);		
		this.waitForDataGridLoadingIconDisappear();
		logTestResult(LogStatus.PASS, "Current Favourite is removed.");
	}
}
