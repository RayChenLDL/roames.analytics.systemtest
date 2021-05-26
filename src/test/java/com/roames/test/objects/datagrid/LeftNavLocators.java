package com.roames.test.objects.datagrid;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeftNavLocators {
	
	@FindBy(xpath=("//div[@class='icon icon-pole']"))
	public WebElement btnAssetModel;
	
	@FindBy(xpath=("//div[@class='icon icon-park_black_24dp']"))
	public WebElement btnVegetation;
	
	@FindBy(xpath=("//div[@class='icon icon-exchange']"))
	public WebElement btnOtherGrids;
	
	@FindBy(xpath=("//div[@class='signout']"))
	public WebElement btnSignOut;	
	
	@FindBy(xpath=("//div[@class='logo__toggle']"))
	public WebElement btnHideMenu;

}
