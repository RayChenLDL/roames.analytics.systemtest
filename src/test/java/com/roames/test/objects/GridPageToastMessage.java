package com.roames.test.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class GridPageToastMessage {		
	@FindBy(xpath="//div[contains(text(),'New favorite saved')]")
	public WebElement msgNewFavoriteSaved;
}
