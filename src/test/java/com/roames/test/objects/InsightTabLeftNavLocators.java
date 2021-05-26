package com.roames.test.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InsightTabLeftNavLocators {
	
	@FindBy(css="sidebar-item-heading[title='Vegetation - EQ']")
	public WebElement vegetation;
	
	@FindBy(css="sidebar-item-heading[title='Applications']")
	public WebElement Applications;

	@FindBy(css="a[title='Intrusion - High Priority']")
	public WebElement Intrusion;
		
	@FindBy(css="a[title='Vegetation Zones - High Priority']")
	public WebElement VegetationZones;
	
	@FindBy(css="a[title='Bay - High Priority']")
	public WebElement Bay;
	
	@FindBy(css="a[title='VZ MST Optimisation']")
	public WebElement VZ_MST_Optimisation_Tool;
	
	@FindBy(xpath="//div[@class='main-tab-content']//input[@ng-model='search']")
	public WebElement txtSearch;
}
