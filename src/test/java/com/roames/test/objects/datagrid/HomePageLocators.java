package com.roames.test.objects.datagrid;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageLocators {
	
	@FindBy(xpath=("//span[text()='Columns']"))
	public WebElement btnColumns;	
}
