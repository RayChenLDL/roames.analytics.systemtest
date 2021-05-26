package com.roames.test.objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class googlePageLocators {
	
	@FindBy(name="q")
	public WebElement searchTextbox;

}
