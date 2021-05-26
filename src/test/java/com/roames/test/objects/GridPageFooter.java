package com.roames.test.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GridPageFooter {
	
	@FindBy(xpath="//button[@class='insight-top-bar-btn mat-mini-fab mat-accent'][@ng-reflect-message='First Page']")
	public WebElement btnFirstPage;

	@FindBy(xpath="//button[@class='insight-top-bar-btn mat-mini-fab mat-accent'][@ng-reflect-message='Last Page']")
	public WebElement btnLastPage;
	
	@FindBy(xpath="//button[@class='insight-top-bar-btn mat-mini-fab mat-accent'][@ng-reflect-message='Previous Page']")
	public WebElement btnPreviousPage;

	@FindBy(xpath="//button[@class='insight-top-bar-btn mat-mini-fab mat-accent'][@ng-reflect-message='Next Page']")
	public WebElement btnNextPage;
	
	@FindBy(xpath="//div[@class='grid-buttons-wrapper ng-star-inserted']//span[contains(text(),'Page')]")
	public WebElement labelPageInfo;		
	
	@FindBy(xpath="//div[@class='ui-grid-footer']//span[contains(text(),'Viewing')]")
	public WebElement labelViewingRowsInfo;
}
