package com.roames.test.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class AssetTypeBuildInAttribute_CustomisePopUp {	

	@FindBy(xpath="//button[@type='submit']")
	public WebElement btnSave;
	
	@FindBy(xpath="//a[@ng-click='$dismiss()']")
	public WebElement btnCancel;
	
	@FindBy(xpath="//a[@ng-click='revert()']")
	public WebElement btnRevert;
}
