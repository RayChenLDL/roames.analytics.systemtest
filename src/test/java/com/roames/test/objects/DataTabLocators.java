package com.roames.test.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class DataTabLocators {	

	@FindBy(xpath="//div[@ng-model='asset_type_ui_select.value']")
	public WebElement lstAssetType;
}
