package com.roames.test.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class DriveTabLocators {	

	@FindBy(xpath="//div[@class='main-tab-content']//span[contains(text(), 'Create Drive')]")
	public WebElement btnCreateDrive;	

	@FindBy(xpath="//div[@class='main-tab-content']//span[contains(text(), 'Actions')]")
	public WebElement btnActions;
}
