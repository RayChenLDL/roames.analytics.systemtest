package com.roames.test.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class AssetTabLocators {	

	@FindBy(xpath="//div[@class='panel-body']//p[contains(text(),'Manage attributes of assets')]")
	public WebElement imgAsset;
}
