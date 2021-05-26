package com.roames.test.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GridPageTopFunctions {
	
	//@FindBy(xpath="//button/span[text()='Edit Favorite']")
	//@FindBy(xpath="//button//i[@class='favorite-icon-edit icon-pencil']")
	@FindBy(xpath="//button[@type='button'][@title='Edit current view']")
	public WebElement btnEditFavourite;
	
	//@FindBy(xpath="//button/span[text()='Add Favorite']")
	@FindBy(xpath="//button//i[@class='favorite-icon-add icon-add_circle_outline']")
	public WebElement btnCreateNewFavourite;

	@FindBy(xpath="//button[@ng-reflect-message='Restore Default Grid']")
	public WebElement btnRestoreDefaultGrid;
	
	@FindBy(xpath="//button[@ng-reflect-message='Refresh Data']")
	public WebElement btnRefreshData;
	
	@FindBy(xpath="//button[@ng-reflect-message='Show / Hide Active Filters']")
	public WebElement btnShowHideActiveFilters;

	@FindBy(xpath="//button[@ng-reflect-message='Columns']")
	public WebElement btnColumns;
	
	@FindBy(xpath="//button[@ng-reflect-message='Pages or Infinite Scroll']")
	public WebElement btnPageOrInfiniteScroll;
	
	@FindBy(xpath="//button[@class='roames-world-toggle insight-top-bar-btn mat-mini-fab mat-accent ng-star-inserted']")
	public WebElement btnRoamesWorldLink;
	
	@FindBy(xpath="//mat-button-toggle[@ng-reflect-message='Show only the current cycle']")
	public WebElement btnCurrentCycle;
	
	@FindBy(xpath="//mat-button-toggle[@ng-reflect-message='Show all cycles']")
	public WebElement btnAllCycles;
	
	@FindBy(xpath="//button[@class='insight-top-bar-nav-btn-current button-spacer mat-button ng-star-inserted']//span[text()='Intrusions']")
	public WebElement btnIntrusions;
	
	@FindBy(xpath="//button[@class='insight-top-bar-nav-btn-current button-spacer mat-button ng-star-inserted']//span[text()='Bays']")
	public WebElement btnBays;
	
	@FindBy(xpath="//button[@class='insight-top-bar-nav-btn-previous button-spacer mat-button ng-star-inserted']//span[text()='Vegetation Zones - High Priority']")
	public WebElement btnVegetationZones_HighPriority;
	
	@FindBy(xpath="//button[@class='insight-top-bar-nav-btn-previous button-spacer mat-button ng-star-inserted']//span[text()='Bay - High Priority']")
	public WebElement btnBay_HighPriority;
	
	@FindBy(xpath="//button[@class='insight-top-bar-nav-btn-current button-spacer mat-button ng-star-inserted']//span[text()='Defect']")
	public WebElement btnNetworkM_Defect;
	
	@FindBy(xpath="//button[@class='insight-top-bar-nav-btn-current button-spacer mat-button ng-star-inserted']//span[text()='Pole']")
	public WebElement btnNetworkM_Pole;
	
	@FindBy(xpath="//button[@class='insight-top-bar-nav-btn-current button-spacer mat-button ng-star-inserted']//span[text()='Span']")
	public WebElement btnNetworkM_Span;	
	
	@FindBy(xpath="//button[@class='insight-top-bar-nav-btn-current button-spacer mat-button ng-star-inserted']//span[text()='Span Defects']")
	public WebElement btnNetworkM_SpanDefects;
	
	@FindBy(xpath="//button[@class='insight-top-bar-nav-btn-current button-spacer mat-button ng-star-inserted']//span[text()='Pole Defects']")
	public WebElement btnNetworkM_PoleDefects;
}
