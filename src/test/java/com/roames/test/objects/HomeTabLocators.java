package com.roames.test.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class HomeTabLocators {	

	//@FindBy(css="span:contains('Recent Publication Events')") // this is JQuery not CSS
	@FindBy(xpath="//span[contains(text(), 'Recent Publication Events')]")
	public WebElement labelRecentPublicationEvents;
}
